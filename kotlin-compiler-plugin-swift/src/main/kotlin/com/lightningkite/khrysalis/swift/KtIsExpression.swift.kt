package com.lightningkite.khrysalis.swift

import com.lightningkite.khrysalis.analysis.resolvedType
import com.lightningkite.khrysalis.replacements.TemplateRendering
import com.lightningkite.khrysalis.replacements.emitTemplateNoDedup
import com.lightningkite.khrysalis.replacements.replacements
import org.jetbrains.kotlin.psi.KtIsExpression

fun KtIsExpression.swift(out: SwiftFileEmitter){
    if (this.isNegated) {
        out.append("!(")
    }
    this.leftHandSide.swift(out)
    out.append(" is ")
    this.typeReference!!.swift(out)
    this.typeReference?.resolvedType?.let { replacements.getType(it) }?.errorCondition?.let {
        out.append(" && ")
        out.emitTemplateNoDedup(it, object: TemplateRendering {
            override fun receiver() {
                leftHandSide.swift(out)
            }
        })
    }
    if (this.isNegated) {
        out.append(")")
    }
}