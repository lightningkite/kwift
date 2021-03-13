package com.lightningkite.khrysalis.swift

import com.lightningkite.khrysalis.analysis.actuallyCouldBeExpression
import com.lightningkite.khrysalis.replacements.Template
import com.lightningkite.khrysalis.replacements.TemplatePart
import com.lightningkite.khrysalis.replacements.emitTemplate
import com.lightningkite.khrysalis.replacements.replacements
import org.jetbrains.kotlin.descriptors.PropertyDescriptor
import org.jetbrains.kotlin.psi.KtQualifiedExpression
import org.jetbrains.kotlin.types.KotlinType

fun PropertyDescriptor.swiftGet(
    out: SwiftFileEmitter,
    receiver: ()->Unit,
    receiverType: KotlinType?,
    expr: KtQualifiedExpression,
    safe: Boolean
) {
    when {
        replacements.getGet(this, receiverType) != null -> {
            val rule = replacements.getGet(this, receiverType)!!

            expr.nullWrapAction(out) { rec, mode ->
                emitTemplate(
                    requiresWrapping = expr.actuallyCouldBeExpression,
                    template = if (mode == AccessMode.QUEST_DOT)
                        Template(parts = rule.template.parts.toMutableList().apply {
                            this[1] = (this[1] as TemplatePart.Text).let { it.copy("?" + it.string) }
                        })
                    else rule.template,
                    receiver = rec,
                    dispatchReceiver = expr.getTsReceiver()
                )
            }
        }
        this.swiftFunctionGetName != null -> {
            expr.nullWrapAction(out){ rec, mode ->
                if (this.dispatchReceiverParameter != null) {
                    -expr.getTsReceiver()
                    out.append(".")
                }
                out.append(this.swiftFunctionGetName)
                out.append('(')
                rec()
                out.append(')')
            }
        }
        else -> {
            expr.nullWrapAction(out) { rec, mode ->
                rec()
                if (mode == AccessMode.QUEST_DOT) {
                    out.append("?.")
                } else {
                    out.append(".")
                }
                out.append(this.name.asString().safeSwiftIdentifier())
            }
        }
    }
}