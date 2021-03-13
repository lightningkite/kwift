package com.lightningkite.khrysalis.swift

import com.lightningkite.khrysalis.analysis.resolvedAnnotation
import com.lightningkite.khrysalis.analysis.resolvedReferenceTarget
import com.lightningkite.khrysalis.analysis.resolvedType
import com.lightningkite.khrysalis.replacements.TemplateRendering
import com.lightningkite.khrysalis.replacements.emitTemplateNoDedup
import com.lightningkite.khrysalis.replacements.replacements
import com.lightningkite.khrysalis.util.forEachBetween
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.*

fun KtTypeElement.swift(out: SwiftFileEmitter){
    when(this){
        is KtFunctionType -> swift(out)
        is KtNullableType -> swift(out)
        is KtSelfType -> out.append("Self")
        is KtUserType -> swift(out)
    }
}

fun KtNullableType.swift(out: SwiftFileEmitter) {
    val parens = this.children.any { (it as? LeafPsiElement)?.elementType == KtTokens.LPAR }
    if(parens) out.append('(')
    this.innerType!!.swift(out)
    if(parens) out.append(')')
    out.append('?')
}

fun KtUserType.swift(out: SwiftFileEmitter){
    val type = this.referenceExpression?.resolvedReferenceTarget
    when {
        type != null && replacements.getType(type) != null -> {
            val rule = replacements.getType(type)!!
            val declaredParams = when (type) {
                is ClassDescriptor -> type.declaredTypeParameters
                is TypeAliasDescriptor -> type.declaredTypeParameters
                else -> listOf()
            }
            val typeParametersByName = this.typeArguments.withIndex()
                .associate { (index, item) -> declaredParams[index].name.asString() to item }
            out.emitTemplateNoDedup(rule.template, object: TemplateRendering {
                override fun typeParameter(name: String) {
                    typeParametersByName[name]!!.swift(out)
                }
                override fun typeParameterByIndex(index: Int) {
                    this@swift.typeArguments[index].swift(out)
                }
            })
        }
        type != null && type is ClassDescriptor && type.swiftTopLevelMessedUp -> {
            out.append(type.swiftTopLevelName)
            this.typeArgumentList?.swift(out)
        }
        else -> {
            out.append(this.text)
        }
    }
}

fun KtTypeReference.swift(out: SwiftFileEmitter) {
    if(writingParameter > 0 && annotationEntries
            .any {
                it.resolvedAnnotation?.fqName?.asString()
                    ?.equals("com.lightningkite.butterfly.escaping", true) == true
            }
                && parentOfType<KtParameter>() != null) {
        out.append("@escaping ")
    }
    this.typeElement!!.swift(out)
}

fun KtFunctionType.swift(out: SwiftFileEmitter) {
    out.append("(")
    writingParameter++
    listOfNotNull(receiverTypeReference?.let { null to it }).plus(parameters.map { it.nameIdentifier to it.typeReference })
        .forEachBetween(
            forItem = {
                it.second!!.swift(out)
            },
            between = { out.append(", ") }
        )
    writingParameter--
    out.append(") -> ")
    returnTypeReference?.swift(out) ?: out.append("Void")
}

fun KtTypeElement.swiftBasic(out: SwiftFileEmitter){
    when(this){
        is KtFunctionType -> out.append("Function")
        is KtNullableType -> swiftBasic(out)
        is KtSelfType -> out.append("Self")
        is KtUserType -> swiftBasic(out)
    }
}

fun KtNullableType.swiftBasic(out: SwiftFileEmitter) {
    this.innerType!!.swift(out)
}

fun KtUserType.swiftBasic(out: SwiftFileEmitter){
    val type = this.referenceExpression?.resolvedReferenceTarget
    when {
        type != null && replacements.getTypeRef(type) != null -> {
            val rule = replacements.getTypeRef(type)!!
            out.emitTemplateNoDedup(rule.template, object: TemplateRendering {})
        }
        type != null && replacements.getType(type) != null -> {
            val rule = replacements.getType(type)!!
            out.emitTemplateNoDedup(rule.template, object: TemplateRendering {})
        }
        type != null && type is ClassDescriptor && type.swiftTopLevelMessedUp -> {
            out.append(type.swiftTopLevelName)
        }
        else -> {
            out.append(this.text)
        }
    }
}

fun KtTypeReference.swiftBasic(out: SwiftFileEmitter) {
    this.typeElement!!.swiftBasic(out)
}

fun KtTypeProjection.swift(out: SwiftFileEmitter){
    when (projectionKind) {
        KtProjectionKind.IN -> typeReference!!.swift(out)
        KtProjectionKind.OUT -> typeReference!!.swift(out)
        KtProjectionKind.STAR -> out.append("Any")
        KtProjectionKind.NONE -> typeReference!!.swift(out)
    }
}

fun KtTypeArgumentList.swift(out: SwiftFileEmitter) {
    out.append('<')
    this.arguments.forEachBetween(
        forItem = { it.swift(out) },
        between = { out.append(", ") }
    )
    out.append('>')
}