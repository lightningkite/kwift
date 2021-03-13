package com.lightningkite.khrysalis.swift

import com.lightningkite.khrysalis.analysis.actuallyCouldBeExpression
import com.lightningkite.khrysalis.analysis.resolvedCall
import com.lightningkite.khrysalis.analysis.resolvedExpressionTypeInfo
import com.lightningkite.khrysalis.analysis.resolvedReferenceTarget
import com.lightningkite.khrysalis.generic.FileEmitter
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.*
import com.lightningkite.khrysalis.generic.PartialTranslatorByType
import com.lightningkite.khrysalis.replacements.Template
import com.lightningkite.khrysalis.replacements.TemplatePart
import com.lightningkite.khrysalis.replacements.replacements
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.types.isNullable

enum class AccessMode(val resultAllowsOptionalOp: Boolean, val usesDot: Boolean = false) {
    PLAIN_DOT(resultAllowsOptionalOp = false, usesDot = true),
    QUEST_DOT(resultAllowsOptionalOp = false, usesDot = true),
    DIRECT_OPT(resultAllowsOptionalOp = true, usesDot = false),
    PAREN_OPT(resultAllowsOptionalOp = true, usesDot = false)
}

private fun getRuleTemplate(rule: KtQualifiedExpression): Template? {
    return when (val sel = rule.selectorExpression) {
        is KtCallExpression -> replacements.getCall(sel.resolvedCall!!)?.template
        is KtNameReferenceExpression -> (sel.resolvedReferenceTarget as? PropertyDescriptor)?.let {
            replacements.getGet(it, rule.receiverExpression.resolvedExpressionTypeInfo?.type)
        }?.template
        else -> null
    }
}

private fun getSelectorNullable(rule: KtQualifiedExpression): Boolean {
        return when (val sel = rule.selectorExpression) {
            is KtCallExpression -> sel.resolvedCall?.candidateDescriptor?.returnType?.isNullable() ?: true
            is KtNameReferenceExpression -> (sel.resolvedReferenceTarget as? PropertyDescriptor)?.type?.isNullable()
                ?: true
            else -> true
        }
    }

private fun getDirectlyNullable(rule: KtExpression): Boolean {
        return when (rule) {
            is KtQualifiedExpression -> getSelectorNullable(rule)
            else -> rule.resolvedExpressionTypeInfo?.type?.isNullable() == true
        }
    }

fun KtQualifiedExpression.getAccessMode(): AccessMode {
    if(this is KtDotQualifiedExpression) return AccessMode.PLAIN_DOT
    val ruleTemplate = getRuleTemplate(this)
    val templateIsThisDot =
        ruleTemplate != null && ruleTemplate.parts.getOrNull(0) is TemplatePart.Receiver && ruleTemplate.parts.getOrNull(
            1
        ).let { it is TemplatePart.Text && it.string.startsWith('.') }
    val receiverAccessMode =
        (receiverExpression as? KtQualifiedExpression)?.let { it.getAccessMode() }
    val receiverAllowsOptionalAction = receiverAccessMode?.resultAllowsOptionalOp ?: true

    return if (selectorExpression?.resolvedCall?.candidateDescriptor?.let { it as? FunctionDescriptor}?.swiftNameOverridden == null && (templateIsThisDot || ruleTemplate == null)) {
        if (receiverAllowsOptionalAction || getDirectlyNullable(receiverExpression)) {
            AccessMode.QUEST_DOT
        } else {
            AccessMode.PLAIN_DOT
        }
    } else {
        if (receiverAllowsOptionalAction) {
            AccessMode.DIRECT_OPT
        } else {
            AccessMode.PAREN_OPT
        }
    }
}

fun KtQualifiedExpression.nullWrapAction(
    out: SwiftFileEmitter,
    action: (()->Unit, AccessMode) -> Unit
) {
    val mode = this.getAccessMode()
    val rec = if (mode.resultAllowsOptionalOp) {
        val rec = "temp${uniqueNumber.getAndIncrement()}"
        if (this.actuallyCouldBeExpression) {
            if (mode == AccessMode.PAREN_OPT) out.append("(")
            this.receiverExpression.swift(out)
            if (mode == AccessMode.PAREN_OPT) out.append(")")
            if (hasNewlineBeforeAccess(this)) {
                out.append("\n")
            }
            if (getSelectorNullable(this)) {
                out.append(".flatMap { $rec in ")
            } else {
                out.append(".map { $rec in ")
            }
        } else {
            out.append("if let $rec = (")
            this.receiverExpression.swift(out)
            out.append(") {\n")
        }
        { -> out.append(rec) }
    } else { -> this.receiverExpression.swift(out) }
    action(rec, mode)
    if (mode.resultAllowsOptionalOp) {
        if (this.actuallyCouldBeExpression) {
            out.append(" ")
        } else {
            out.append("\n")
        }
        out.append("}")
    }
}

fun hasNewlineBeforeAccess(typedRule: KtQualifiedExpression): Boolean {
    return typedRule.allChildren
        .find { it is LeafPsiElement && (it.elementType == KtTokens.DOT || it.elementType == KtTokens.SAFE_ACCESS) }
        ?.prevSibling
        ?.let { it as? PsiWhiteSpace }
        ?.textContains('\n') == true
}

fun <T : KtQualifiedExpression> PartialTranslatorByType<SwiftFileEmitter, Unit, Any>.ContextByType<T>.insertNewlineBeforeAccess() {
    if (hasNewlineBeforeAccess(typedRule)) {
        -"\n"
    }
}