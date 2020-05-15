package com.lightningkite.khrysalis.typescript

import com.lightningkite.khrysalis.generic.line
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.js.descriptorUtils.getJetTypeFqName
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.containingClass
import org.jetbrains.kotlin.psi.synthetics.SyntheticClassOrObjectDescriptor
import org.jetbrains.kotlin.resolve.calls.callUtil.getResolvedCall
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameSafe
import org.jetbrains.kotlin.resolve.scopes.receivers.ExtensionReceiver
import org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitClassReceiver
import org.jetbrains.kotlin.types.typeUtil.isInterface

fun TypescriptTranslator.registerReceiver() {

    //Prepend 'this'
    handle<KtNameReferenceExpression>(
        condition = {
            if (typedRule.parent is KtDotQualifiedExpression) return@handle false
            if (typedRule.parent is KtSafeQualifiedExpression) return@handle false
            if((typedRule.parent as? KtCallExpression)?.parent is KtDotQualifiedExpression) return@handle false
            val resolved = typedRule.resolvedCall
            return@handle resolved?.dispatchReceiver != null
        },
        priority = 99,
        action = {
//            val resolved = typedRule.resolvedCall?.dispatchReceiver as? ImplicitClassReceiver
//            if(resolved != null){
//                collector?.report(CompilerMessageSeverity.INFO, "Receiver of ${typedRule.text} is ${resolved.type.getJetTypeFqName(false)} - ${resolved.classDescriptor.isCompanionObject}")
//            }
            -typedRule.getTsReceiver()
            -"."
            doSuper()
        }
    )

    handle<KtNameReferenceExpression>(
        condition = {
            if (typedRule.parent is KtDotQualifiedExpression) return@handle false
            if (typedRule.parent is KtSafeQualifiedExpression) return@handle false
            if((typedRule.parent as? KtCallExpression)?.parent is KtDotQualifiedExpression) return@handle false
            val resolved = typedRule.resolvedCall ?: return@handle false
            val targetDescriptor = resolved.dispatchReceiver?.type?.constructor?.declarationDescriptor as? ClassDescriptor ?: return@handle false
            return@handle resolved.dispatchReceiver != null
                    && targetDescriptor.isCompanionObject
                    && targetDescriptor != typedRule.containingClass()?.resolvedClass
        },
        priority = 100,
        action = {
            -typedRule.containingClass()?.nameIdentifier
            -".Companion.INSTANCE."
            -typedRule.getIdentifier()
        }
    )

    handle<KtThisExpression> {
        val fq = typedRule.resolvedCall?.resultingDescriptor?.containingDeclaration?.fqNameSafe?.asString()
        val entry = receiverStack.lastOrNull { it.fqName == fq }
        -(entry?.tsName ?: "this")
    }
}