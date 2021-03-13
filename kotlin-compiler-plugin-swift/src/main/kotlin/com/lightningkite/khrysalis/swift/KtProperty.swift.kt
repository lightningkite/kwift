package com.lightningkite.khrysalis.swift

import com.lightningkite.khrysalis.abstractions.VirtualFunction
import com.lightningkite.khrysalis.analysis.*
import com.lightningkite.khrysalis.replacements.requiresMutable
import com.lightningkite.khrysalis.util.withReceiverScope
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.ClassKind
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.containingClassOrObject
import org.jetbrains.kotlin.psi.psiUtil.isExtensionDeclaration
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameOrNull
import org.jetbrains.kotlin.types.isNullable

private fun KtExpression.isGuard() = (this as? KtBinaryExpression)?.let {
    it.operationToken == KtTokens.ELVIS && (it.right is KtReturnExpression || it.right is KtContinueExpression || it.right is KtBreakExpression || it.right is KtThrowExpression)
} == true

private fun KtProperty.delegateType(): String? = ((this.delegateExpression as? KtCallExpression)?.calleeExpression as? KtNameReferenceExpression)?.resolvedReferenceTarget?.fqNameOrNull()
    ?.asString()

fun KtProperty.swift(out: SwiftFileEmitter){
    when {
        this.receiverTypeReference != null
                && this.resolvedProperty?.worksAsSwiftConstraint() == true
                && this.containingClassOrObject == null -> swiftCaseExtension(out)
        this.resolvedProperty?.swiftFunctionGetName != null -> swiftCaseDoubleExtension(out)
        this.parent?.let { it as? KtClassBody }?.parent?.let { it as? KtClass }?.isInterface() == true -> swiftCaseInterface(out)
        hasModifier(KtTokens.ABSTRACT_KEYWORD) -> swiftCaseAbstract(out)
        this.isLocal && this.initializer?.isGuard() == true -> swiftCaseLocalWithGuard(out)
        delegateType() == "com.lightningkite.butterfly.weak" -> swiftCaseWeak(out)
        this.initializer == null && this.getter != null -> swiftCaseVirtual(out)
        this.resolvedProperty?.hasSwiftBacking == true -> swiftCasePartial(out)
        else -> swiftCasePlain(out)
    }
}

fun KtProperty.swiftCaseVirtual(out: SwiftFileEmitter){
    if (this.isMember) {
        if (this.resolvedProperty?.hasSwiftOverride == true) {
            out.append("override ")
        }
    }
    if (this.isMember || (this.isTopLevel && !this.isExtensionDeclaration())) {
        out.append((this.swiftVisibility()?.toString() ?: "public"))
        out.append(" ")
    }
    out.append("var ")
    swiftId(out)
    swiftEmitTypeRequired(out)
    out.append(" {\n")
    this.getter?.swift(out)
    this.setter?.swift(out)
    out.append("}")
}

private fun KtProperty.swiftCaseDoubleExtension(out: SwiftFileEmitter){
    withReceiverScope(this.resolvedProperty!!) {
        if (this.isMember || this.isTopLevel) {
            swiftVisibility(out)
            out.append(" ")
        }
        -VirtualFunction(
            this.resolvedProperty!!.swiftFunctionGetName!!,
            resolvedFunction = null,
            typeParameters = this.typeParameters,
            valueParameters = listOfNotNull(this.receiverTypeReference?.let { listOf("_ this: ", it) }),
            returnType = this.typeReference ?: this.resolvedProperty?.type!!,
            body = this.getter?.bodyExpression
        )
        out.append("\n")
        if (this.setter != null) {
            if (this.isMember || this.isTopLevel) {
                swiftVisibility(out)
                out.append(" ")
            }
            -VirtualFunction(
                this.resolvedProperty!!.swiftFunctionSetName!!,
                resolvedFunction = null,
                typeParameters = this.typeParameters,
                valueParameters = listOfNotNull(
                    this.receiverTypeReference?.let { listOf("_ this: ", it) },
                    listOf(
                        "_ ",
                        this.setter!!.parameter!!.nameIdentifier,
                        ": ",
                        this.typeReference ?: this.resolvedProperty?.type!!
                    )
                ),
                returnType = "Void",
                body = this.setter?.bodyExpression
            )
        }
    }
}

private fun KtProperty.swiftCasePlain(out: SwiftFileEmitter) {
    if (this.annotationEntries.any { it.typeReference?.text?.endsWith("weak") == true }) {
        out.append("weak ")
    }
    if (this.isMember || (this.isTopLevel && !this.isExtensionDeclaration())) {
        swiftVisibility(out)
        out.append(" ")
    }
    val isLateInit = this.hasModifier(KtTokens.LATEINIT_KEYWORD) || (this.isMember && this.initializer?.capturesSelf(
        this.containingClassOrObject?.resolvedClass
    ) == true)
    if (this.annotationEntries.any {
            it.resolvedAnnotation?.fqName?.asString()?.equals("com.lightningkite.butterfly.Unowned", true) == true
        }) {
        out.append("unowned ")
    }
    if (isLateInit || this.isVar || (this.resolvedProperty?.type?.requiresMutable()
            ?: this.resolvedVariable?.type?.requiresMutable()) == true
    ) {
        out.append("var ")
    } else {
        out.append("let ")
    }
    swiftId(out)
    this.typeReference?.let { type ->
        out.append(": ")
        if (isLateInit && this.resolvedProperty?.type?.isNullable() == false) {
            out.append('(')
            type.swift(out)
            out.append(")!")
        } else {
            type.swift(out)
        }
    } ?: this.resolvedProperty?.type?.let { type ->
        out.append(": ")
        if (isLateInit && this.resolvedProperty?.type?.isNullable() == false) {
            out.append('(')
            type.swift(out)
            out.append(")!")
        } else {
            type.swift(out)
        }
    }
    if (!this.isMember) {
        this.initializer?.let {
            out.append(" = ")
            it.swift(out)
        }
    }
}

private fun KtProperty.swiftCasePartial(out: SwiftFileEmitter) {
    val isLateInit = this.hasModifier(KtTokens.LATEINIT_KEYWORD) || (this.isMember && initializer?.capturesSelf(
        this.containingClassOrObject?.resolvedClass
    ) == true)
    if (this.isMember || (this.isTopLevel && !this.isExtensionDeclaration())) {
        swiftVisibility(out)
        out.append(" ")
    }
    if (this.annotationEntries.any {
            it.resolvedAnnotation?.fqName?.asString()
                ?.equals("com.lightningkite.butterfly.Unowned", true) == true
        }) {
        out.append("unowned ")
    }
    out.append("var _")
    swiftId(out)
    this.typeReference?.let {
        out.append(": ")
        if (isLateInit && this.resolvedProperty?.type?.isNullable() == false) {
            out.append('(')
            it.swift(out)
            out.append(")!")
        } else {
            it.swift(out)
        }
    } ?: run {
        if (this.isMember || this.initializer == null) {
            out.append(": ")
            this.resolvedProperty!!.type.swift(out)
        }
    }
    if (!this.isMember) {
        this.initializer?.let {
            out.append(" = ")
            it.swift(out)
        }
    }
    out.append("\n")
    if (this.isMember) {
        if (this.resolvedProperty?.hasSwiftOverride == true) {
            out.append("override ")
        }
    }
    if (this.isMember || (this.isTopLevel && !this.isExtensionDeclaration())) {
        swiftVisibility(out)
        out.append(" ")
    }
    out.append("var ")
    swiftId(out)
    swiftEmitTypeRequired(out)
    out.append(" {\n")
    this.getter?.swift(out) ?: run {
        out.append("get { return _")
        swiftId(out)
        out.append(" }")
        out.append("\n")
    }
    if (this.isVar || this.resolvedProperty?.type?.requiresMutable() == true) {
        this.setter?.swift(out) ?: run {
            out.append("set(value) { _")
            swiftId(out)
            out.append(" = value }")
            out.append("\n")
        }
    }
    out.append("}")
}

private fun KtProperty.swiftCaseWeak(out: SwiftFileEmitter) {
    out.append("weak ")
    if (this.isMember || (this.isTopLevel && !this.isExtensionDeclaration())) {
        swiftVisibility(out)
        out.append(" ")
    }
    out.append("var ")
    swiftId(out)
    if (this.isMember || this.initializer == null) swiftEmitTypeRequired(out)
    else swiftEmitType(out)

    this.delegateExpression?.let {
        out.append(" = ")
        (it as KtCallExpression).valueArguments.first().getArgumentExpression()!!.swift(out)
    }
}

private fun KtProperty.swiftCaseLocalWithGuard(out: SwiftFileEmitter) {
    out.append("guard ")
    if (this.isVar || (this.resolvedProperty?.type?.requiresMutable()
            ?: this.resolvedVariable?.type?.requiresMutable()) == true
    ) {
        out.append("var ")
    } else {
        out.append("let ")
    }
    swiftId(out)
    val type = this.typeReference
    type?.let {
        out.append(": ")
        it.swift(out)
    }
    val elvis = this.initializer as KtBinaryExpression
    out.append(" = ")
    elvis.left?.swift(out)
    out.append(" else { ")
    elvis.right?.swift(out)
    out.append(" }")
}

private fun KtProperty.swiftCaseAbstract(out: SwiftFileEmitter) {
    if (this.isMember) {
        if (this.resolvedProperty?.overriddenDescriptors
                ?.any { (it.containingDeclaration as? ClassDescriptor)?.kind != ClassKind.INTERFACE } == true
        ) {
            out.append("override ")
        }
    }
    if (this.isMember || (this.isTopLevel && !this.isExtensionDeclaration())) {
        out.append((this.swiftVisibility()?.toString() ?: "public"))
        out.append(" ")
    }
    out.append("var ")
    swiftId(out)
    swiftEmitTypeRequired(out)
    if (this.isVar) {
        out.append(" { get { TODO() } set { TODO() } }")
    } else {
        out.append(" { get { TODO() } }")
    }
}

private fun KtProperty.swiftCaseInterface(out: SwiftFileEmitter) {
    out.append("var ")
    swiftId(out)
    out.append(": ")
    typeReference!!.swift(out)
    if (this.isVar) {
        out.append(" { get set }")
    } else {
        out.append(" { get }")
    }
    out.append("\n")
    val tr = this
    val ktClassBody = this.parentOfType<KtClassBody>()!!
    if (this.getter != null || this.setter != null) {
        ktClassBody.addPostAction {
            out.append("\n")
            out.append("var ")
            swiftId(out)
            tr.typeReference?.let {
                out.append(": ")
                it.swift(out)
            } ?: run {
                out.append(": ")
                tr.resolvedProperty!!.type.swift(out)
            }
            out.append(" {\n")
            tr.getter?.swift(out)
            tr.setter?.swift(out)
            out.append("}")
        }
    }
}

private fun KtProperty.swiftCaseExtension(out: SwiftFileEmitter) {
    if (this.isMember || this.isTopLevel) {
        swiftVisibility(out)
        out.append(" ")
    }
    swiftExtensionStart(
        out,
        this.resolvedProperty!!,
        this.receiverTypeReference,
        this.typeParameterList
    )
    out.append('\n')
    swiftCaseVirtual(out)
    out.append("\n}")
}

private fun KtProperty.swiftEmitTypeRequired(out: SwiftFileEmitter) {
    this.typeReference?.let {
        out.append(": ")
        it.swift(out)
    } ?: run {
        out.append(": ")
        this.resolvedProperty!!.type.swift(out)
    }
}

private fun KtProperty.swiftEmitType(out: SwiftFileEmitter) {
    this.typeReference?.let {
        out.append(": ")
        it.swift(out)
    }
}