package com.lightningkite.khrysalis.generic

import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTree


interface Condition {
    fun evaluate(translator: Translator, context: ParseTree): Boolean
    val complexity: Int get() = 100
}

data class ConditionExists(val expression: Expression) : Condition {
    override val complexity: Int get() = 100
    override fun evaluate(
        translator: Translator,
        context: ParseTree
    ): Boolean = expression.resolve(translator, context) != null
}

data class ConditionEquals(val expression: Expression, val expression2: Expression) : Condition {
    override val complexity: Int get() = 150
    override fun evaluate(
        translator: Translator,
        context: ParseTree
    ): Boolean = expression.resolve(translator, context) == expression2.resolve(
        translator, context
    )
}

data class ConditionAny(val conditions: List<Condition>) : Condition {
    override val complexity: Int get() = conditions.asSequence().map { it.complexity }.min() ?: 0
    override fun evaluate(
        translator: Translator,
        context: ParseTree
    ): Boolean = conditions.any { it.evaluate(translator, context) }
}

data class ConditionAll(val conditions: List<Condition>) : Condition {
    override val complexity: Int get() = conditions.sumBy { it.complexity }
    override fun evaluate(
        translator: Translator,
        context: ParseTree
    ): Boolean = conditions.all { it.evaluate(translator, context) }
}
