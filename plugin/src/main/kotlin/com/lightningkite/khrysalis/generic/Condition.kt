package com.lightningkite.khrysalis.generic

import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTree


interface Condition {
    fun evaluate(translator: Translator, context: Any?): Boolean
    val complexity: Int get() = 100
}

data class ConditionExists(val expression: Expression) : Condition {
    override val complexity: Int get() = 100
    override fun evaluate(
        translator: Translator,
        context: Any?
    ): Boolean = expression.resolve(translator, context) != null
    override fun toString(): String = expression.toString()
}

data class ConditionEquals(val expression: Expression, val expression2: Expression) : Condition {
    override val complexity: Int get() = 150
    override fun evaluate(
        translator: Translator,
        context: Any?
    ): Boolean = expression.resolve(translator, context) == expression2.resolve(
        translator, context
    )
    override fun toString(): String = expression.toString() + " == " + expression2.toString()
}

data class ConditionAny(val conditions: List<Condition>) : Condition {
    override val complexity: Int get() = conditions.asSequence().map { it.complexity }.min() ?: 0
    override fun evaluate(
        translator: Translator,
        context: Any?
    ): Boolean = conditions.any { it.evaluate(translator, context) }
    override fun toString(): String = "and(" + conditions.joinToString() + ")"
}

data class ConditionAll(val conditions: List<Condition>) : Condition {
    override val complexity: Int get() = conditions.sumBy { it.complexity }
    override fun evaluate(
        translator: Translator,
        context: Any?
    ): Boolean = conditions.all { it.evaluate(translator, context) }
    override fun toString(): String = "or(" + conditions.joinToString() + ")"
}
