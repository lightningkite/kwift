package com.lightningkite.khrysalis.typescript

import com.lightningkite.khrysalis.typescript.replacements.TemplatePart
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.PropertyDescriptor
import org.jetbrains.kotlin.js.descriptorUtils.getJetTypeFqName
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.toVisibility
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifierTypeOrDefault
import org.jetbrains.kotlin.psi.synthetics.SyntheticClassOrObjectDescriptor
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameSafe
import java.util.concurrent.atomic.AtomicInteger

val uniqueNumber = AtomicInteger(0)

fun TypescriptTranslator.registerVariable() {

    //Handle special case of completely virtual property
    handle<KtProperty>(
        condition = { typedRule.getter != null && (!typedRule.isVar || typedRule.setter != null) && typedRule.initializer == null },
        priority = 100,
        action = {
            -typedRule.getter!!
            -typedRule.setter
        }
    )

    handle<KtProperty> {
        if (typedRule.isMember) {
            -typedRule.visibilityModifierTypeOrDefault().toVisibility()
            -" "
            if (!typedRule.isVar) {
                -"readonly "
            }
        } else {
            if (typedRule.isVar) {
                -"let "
            } else {
                -"const "
            }
        }
        if (typedRule.getter != null || typedRule.setter != null) {
            -"_"
        }
        -typedRule.nameIdentifier
        typedRule.typeReference?.let {
            -": "
            -it
        }
        typedRule.initializer?.let {
            -" = "
            -it
        }
        -";\n"
        if (typedRule.getter != null || typedRule.setter != null) {
            typedRule.getter?.let {
                -it
            } ?: run {
                if (typedRule.isMember) {
                    -"get "
                    -typedRule.nameIdentifier
                    -"(): "
                    -(typedRule.typeReference ?: typedRule.resolvedVariable?.name) //TODO: Handle unimported type
                    -" { return "
                    -"this._"
                    -typedRule.nameIdentifier
                    -"; }\n"
                } else {
                    -"function get"
                    -typedRule.nameIdentifier?.text?.capitalize()
                    -"(): "
                    -(typedRule.typeReference ?: typedRule.resolvedVariable?.name) //TODO: Handle unimported type
                    -" { return "
                    -"_"
                    -typedRule.nameIdentifier
                    -"; }\n"
                }
            }
            if (typedRule.isVar) {
                typedRule.setter?.let {
                    -it
                } ?: run {
                    if (typedRule.isMember) {
                        -"set "
                        -typedRule.nameIdentifier
                        -"(value: "
                        -(typedRule.typeReference
                            ?: typedRule.resolvedVariable?.name) //TODO: Handle unimported type
                        -") { this._"
                        -typedRule.nameIdentifier
                        -" = value; }\n"
                    } else {
                        -"function set"
                        -typedRule.nameIdentifier?.text?.capitalize()
                        -"(value: "
                        -(typedRule.typeReference
                            ?: typedRule.resolvedVariable?.name) //TODO: Handle unimported type
                        -") { _"
                        -typedRule.nameIdentifier
                        -" = value; }\n"
                    }
                }
            }
        }
    }

    //extension getter/setter
    handle<KtPropertyAccessor>(
        condition = { typedRule.isGetter && typedRule.property.receiverTypeReference != null },
        priority = 8,
        action = {
            withReceiverScope(typedRule.property.fqName!!.asString()) { receiverName ->
                val resolved = typedRule.property.resolvedProperty!!
                -"function "
                -resolved.tsFunctionGetName
                -typedRule.property.typeParameterList
                -"("
                -receiverName
                -": "
                -typedRule.property.receiverTypeReference
                -"): "
                -(typedRule.property.typeReference
                    ?: typedRule.property.resolvedProperty!!.type.getJetTypeFqName(true))
                -" "
                typedRule.bodyExpression?.let {
                    -"{ return "
                    -it
                    -"; }"
                }
                -typedRule.bodyBlockExpression
                -"\n"
            }
        }
    )
    handle<KtPropertyAccessor>(
        condition = { typedRule.isSetter && typedRule.property.receiverTypeReference != null },
        priority = 9,
        action = {
            withReceiverScope(typedRule.property.fqName!!.asString()) { receiverName ->
                val resolved = typedRule.property.resolvedProperty!!
                -"function "
                -resolved.tsFunctionSetName
                -typedRule.property.typeParameterList
                -"("
                -receiverName
                -": "
                -typedRule.property.receiverTypeReference
                -", "
                -(typedRule.parameter?.nameIdentifier ?: -"value")
                -": "
                -(typedRule.property.typeReference ?: typedRule.property.resolvedProperty!!.type.getJetTypeFqName(true))
                -") "
                -typedRule.bodyBlockExpression
                -"\n"
            }
        }
    )

    //Member getter/setter
    handle<KtPropertyAccessor>(
        condition = { typedRule.isGetter && typedRule.property.isMember },
        priority = 4,
        action = {
            -typedRule.visibilityModifierTypeOrDefault().toVisibility()
            -" get "
            -typedRule.property.nameIdentifier
            -"(): "
            -(typedRule.property.typeReference
                ?: typedRule.property.resolvedProperty!!.type.getJetTypeFqName(true))
            -" "
            typedRule.bodyExpression?.let {
                -"{ return "
                -it
                -"; }"
            }
            -typedRule.bodyBlockExpression
            -"\n"
        }
    )
    handle<KtPropertyAccessor>(
        condition = { typedRule.isSetter && typedRule.property.isMember },
        priority = 3,
        action = {
            -typedRule.visibilityModifierTypeOrDefault().toVisibility()
            -" set "
            -typedRule.property.nameIdentifier
            -"("
            -(typedRule.parameter?.nameIdentifier ?: -"value")
            -": "
            -(typedRule.property.typeReference
                ?: typedRule.property.resolvedProperty!!.type.getJetTypeFqName(true))
            -") "
            -typedRule.bodyBlockExpression
            -"\n"
        }
    )

    //Global getter/setter
    handle<KtPropertyAccessor>(
        condition = { typedRule.isGetter },
        priority = 1,
        action = {
            -"function get"
            -typedRule.property.nameIdentifier!!.text.capitalize()
            -"(): "
            -(typedRule.property.typeReference
                ?: typedRule.property.resolvedProperty!!.type.getJetTypeFqName(true))
            -" "
            typedRule.bodyExpression?.let {
                -"{ return "
                -it
                -"; }"
            }
            -typedRule.bodyBlockExpression
            -"\n"
        }
    )
    handle<KtPropertyAccessor>(
        condition = { typedRule.isSetter },
        priority = 2,
        action = {
            -"function set"
            -typedRule.property.nameIdentifier!!.text.capitalize()
            -"("
            -(typedRule.parameter?.nameIdentifier ?: -"value")
            -": "
            -(typedRule.property.typeReference
                ?: typedRule.property.resolvedProperty!!.type.getJetTypeFqName(true))
            -") "
            -typedRule.bodyBlockExpression
            -"\n"
        }
    )

    //'field' access
    handle<KtNameReferenceExpression>(
        condition = { typedRule.text == "field" && typedRule.parentOfType<KtPropertyAccessor>() != null },
        priority = 1000,
        action = {
            val prop = typedRule.parentOfType<KtPropertyAccessor>()!!
            if (prop.property.isMember) {
                -"this."
            }
            -"_"
            -typedRule.parentOfType<KtPropertyAccessor>()!!.property.nameIdentifier
        }
    )

    //handle virtual property access
    handle<KtNameReferenceExpression>(
        condition = { (typedRule.resolvedReferenceTarget as? PropertyDescriptor)?.tsFunctionGetName != null },
        priority = 100,
        action = {
            val prop = typedRule.resolvedReferenceTarget as PropertyDescriptor
            -prop.tsFunctionGetName
            when {
                prop.extensionReceiverParameter == null -> -"()"
                else -> {
                    -'('
                    -(typedRule.getTsReceiver())
                    -')'
                }
            }
        }
    )
    handle<KtDotQualifiedExpression>(
        condition = { ((typedRule.selectorExpression as? KtNameReferenceExpression)?.resolvedReferenceTarget as? PropertyDescriptor)?.tsFunctionGetName != null },
        priority = 1000,
        action = {
            val nre = (typedRule.selectorExpression as KtNameReferenceExpression)
            val prop = nre.resolvedReferenceTarget as PropertyDescriptor
            -nre.getTsReceiver()
            -"."
            -prop.tsFunctionGetName
            -'('
            -typedRule.receiverExpression
            -')'
        }
    )
    handle<KtBinaryExpression>(
        condition = {
            val left = typedRule.left as? KtNameReferenceExpression ?: return@handle false
            (left.resolvedReferenceTarget as? PropertyDescriptor)?.tsFunctionSetName != null &&
                    when (typedRule.operationToken) {
                        KtTokens.EQ -> true
                        KtTokens.PLUSEQ -> true
                        KtTokens.MINUSEQ -> true
                        KtTokens.MULTEQ -> true
                        KtTokens.DIVEQ -> true
                        KtTokens.PERCEQ -> true
                        else -> false
                    }
        },
        priority = 100,
        action = {
            val left = (typedRule.left as KtNameReferenceExpression)
            val leftProp = left.resolvedReferenceTarget as PropertyDescriptor
            -leftProp.tsFunctionSetName
            when {
                leftProp.extensionReceiverParameter == null -> {
                    -'('
                }
                else -> {
                    -'('
                    -(left.getTsReceiver())
                    -", "
                }
            }
            if (typedRule.operationToken != KtTokens.EQ) {
                -leftProp.tsFunctionGetName
                -'('
                -(left.getTsReceiver())
                -')'
                when (typedRule.operationToken) {
                    KtTokens.PLUSEQ -> -" + "
                    KtTokens.MINUSEQ -> -" - "
                    KtTokens.MULTEQ -> -" * "
                    KtTokens.DIVEQ -> -" / "
                    KtTokens.PERCEQ -> -" % "
                }
            }
            -typedRule.right
            -')'
        }
    )
    handle<KtBinaryExpression>(
        condition = {
            val left = typedRule.left as? KtDotQualifiedExpression ?: return@handle false
            ((left.selectorExpression as? KtNameReferenceExpression)?.resolvedReferenceTarget as? PropertyDescriptor)?.tsFunctionGetName != null &&
                    when (typedRule.operationToken) {
                        KtTokens.EQ -> true
                        KtTokens.PLUSEQ -> true
                        KtTokens.MINUSEQ -> true
                        KtTokens.MULTEQ -> true
                        KtTokens.DIVEQ -> true
                        KtTokens.PERCEQ -> true
                        else -> false
                    }
        },
        priority = 1000,
        action = {
            val left = (typedRule.left as KtDotQualifiedExpression)
            val nre = (left.selectorExpression as KtNameReferenceExpression)
            val prop = nre.resolvedReferenceTarget as PropertyDescriptor
            -(nre.getTsReceiver())
            -"."
            -prop.tsFunctionSetName
            -'('
            -left.receiverExpression
            -", "
            if (typedRule.operationToken != KtTokens.EQ) {
                -nre.getTsReceiver()
                -"."
                -prop.tsFunctionGetName
                -'('
                -left.receiverExpression
                -')'
                when (typedRule.operationToken) {
                    KtTokens.PLUSEQ -> -" + "
                    KtTokens.MINUSEQ -> -" - "
                    KtTokens.MULTEQ -> -" * "
                    KtTokens.DIVEQ -> -" / "
                    KtTokens.PERCEQ -> -" % "
                }
            }
            -typedRule.right
            -')'
        }
    )

    //Getter actual overrides
    handle<KtNameReferenceExpression>(
        condition = {
            val pd = typedRule.resolvedReferenceTarget as? PropertyDescriptor ?: return@handle false
            replacements.getGet(pd) != null
        },
        priority = 10_000,
        action = {
            val pd = typedRule.resolvedReferenceTarget as PropertyDescriptor
            val rule = replacements.getGet(pd)!!
            rule.template.forEach { part ->
                when(part){
                    is TemplatePart.Text -> -part.string
                    TemplatePart.DispatchReceiver -> -typedRule.getTsReceiver()
                    TemplatePart.Value -> { }
                    TemplatePart.ExtensionReceiver -> {}
                    is TemplatePart.ParameterReceiver -> { }
                    is TemplatePart.TypeParameterReceiver -> { }
                }
            }
        }
    )
    handle<KtDotQualifiedExpression>(
        condition = {
            val nre = (typedRule.selectorExpression as? KtNameReferenceExpression) ?: return@handle false
            val pd = nre.resolvedReferenceTarget as? PropertyDescriptor ?: return@handle false
            replacements.getGet(pd) != null
        },
        priority = 10_000,
        action = {
            val nre = (typedRule.selectorExpression as KtNameReferenceExpression)
            val pd = nre.resolvedReferenceTarget as PropertyDescriptor
            val rule = replacements.getGet(pd)!!
            rule.template.forEach { part ->
                when(part){
                    is TemplatePart.Text -> -part.string
                    TemplatePart.DispatchReceiver -> -nre.getTsReceiver()
                    TemplatePart.ExtensionReceiver -> -typedRule.receiverExpression
                    TemplatePart.Value -> { }
                    is TemplatePart.ParameterReceiver -> { }
                    is TemplatePart.TypeParameterReceiver -> { }
                }
            }
        }
    )

    //Setter actual overrides
    handle<KtBinaryExpression>(
        condition = {
            val left = typedRule.left as? KtDotQualifiedExpression ?: return@handle false
            val pd = ((left.selectorExpression as? KtNameReferenceExpression)?.resolvedReferenceTarget as? PropertyDescriptor) ?: return@handle false
            replacements.getSet(pd) != null
        },
        priority = 10_001,
        action = {
            val left = typedRule.left as KtDotQualifiedExpression
            val nre = (left.selectorExpression as KtNameReferenceExpression)
            val pd = (nre.resolvedReferenceTarget as PropertyDescriptor)
            val rule = replacements.getSet(pd)!!
            rule.template.forEach { part ->
                when(part){
                    is TemplatePart.Text -> -part.string
                    TemplatePart.DispatchReceiver -> -nre.getTsReceiver()
                    TemplatePart.ExtensionReceiver -> -left.receiverExpression
                    TemplatePart.Value -> -typedRule.right
                    is TemplatePart.ParameterReceiver -> { }
                    is TemplatePart.TypeParameterReceiver -> { }
                }
            }
        }
    )
    handle<KtBinaryExpression>(
        condition = {
            val left = typedRule.left as? KtNameReferenceExpression ?: return@handle false
            val pd = (left?.resolvedReferenceTarget as? PropertyDescriptor) ?: return@handle false
            replacements.getSet(pd) != null
        },
        priority = 10_000,
        action = {
            val nre = typedRule.left as KtNameReferenceExpression
            val pd = (nre.resolvedReferenceTarget as PropertyDescriptor)
            val rule = replacements.getSet(pd)!!
            rule.template.forEach { part ->
                when(part){
                    is TemplatePart.Text -> -part.string
                    TemplatePart.DispatchReceiver -> -nre.getTsReceiver()
                    TemplatePart.ExtensionReceiver -> { }
                    TemplatePart.Value -> -typedRule.right
                    is TemplatePart.ParameterReceiver -> { }
                    is TemplatePart.TypeParameterReceiver -> { }
                }
            }
        }
    )
}

val PropertyDescriptor.tsFunctionGetName: String?
    get() = if (extensionReceiverParameter != null) "get" + extensionReceiverParameter!!
        .value
        .type
        .getJetTypeFqName(false)
        .split('.')
        .joinToString("") { it.capitalize() } +
            this.name.identifier.capitalize()
    else when (this.containingDeclaration) {
        is ClassDescriptor -> null
        is SyntheticClassOrObjectDescriptor -> null
        else -> if (this.accessors.all { it.isDefault }) null else "get" + this.name.identifier.capitalize()
    }
val PropertyDescriptor.tsFunctionSetName: String?
    get() = if (extensionReceiverParameter != null) "set" + extensionReceiverParameter!!
        .value
        .type
        .getJetTypeFqName(false)
        .split('.')
        .joinToString("") { it.capitalize() } +
            this.name.identifier.capitalize()
    else when (this.containingDeclaration) {
        is ClassDescriptor -> null
        is SyntheticClassOrObjectDescriptor -> null
        else -> if (this.accessors.all { it.isDefault }) null else "set" + this.name.identifier.capitalize()
    }

inline fun <reified T : PsiElement> PsiElement.parentOfType(): T? = parentOfType(T::class.java)
fun <T : PsiElement> PsiElement.parentOfType(type: Class<T>): T? =
    if (type.isInstance(this.parent)) type.cast(this.parent) else this.parent?.parentOfType(type)