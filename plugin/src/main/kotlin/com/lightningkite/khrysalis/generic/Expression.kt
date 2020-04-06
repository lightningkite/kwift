package com.lightningkite.khrysalis.generic

import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.RuleContext
import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.TerminalNode


interface Expression {
    fun resolve(translator: Translator, context: Any?, offset: Int = 0): Any?
    fun count(
        translator: Translator,
        context: Any?
    ): Int = Int.MAX_VALUE
}

interface SettableExpression : Expression {
    fun set(value: Any?, translator: Translator, context: Any?, offset: Int = 0)
}

data class ConstantExpression(val value: Any?) : Expression {
    override fun resolve(
        translator: Translator,
        context: Any?,
        offset: Int
    ): Any? = value

    override fun toString(): String = if (value is String) "\"$value\"" else value.toString()
}

data class ParameterExpression(val key: String) : Expression {
    override fun resolve(translator: Translator, context: Any?, offset: Int): Any? {
        return translator.parameters[key]
    }

    override fun toString(): String = "@$key"
}

data class Path(
    val name: String,
    val type: Type,
    val ruleIndex: Int = -1,
    val index: Int = 0,
    val on: Expression? = null
) : Expression {
    enum class Type { Token, Rule, Parent, Other }

    override fun resolve(
        translator: Translator,
        context: Any?,
        offset: Int
    ): Any? {
        if (on == null && name == "global") return translator.globals
        val target = if (on != null) on.resolve(translator, context, 0) else context
        val result = when (target) {
            is ParserRuleContext -> {
                return when (type) {
                    Type.Token -> target.children.asSequence().mapNotNull { it as? TerminalNode }
                        .filter { it.symbol.type == -this.ruleIndex }.drop(index + offset).firstOrNull()
                    Type.Parent -> target.parent
                    Type.Rule -> target.children.asSequence().mapNotNull { it as? ParserRuleContext }
                        .filter { it.ruleIndex == this.ruleIndex }.drop(index + offset).firstOrNull()
                    Type.Other -> when (name) {
                        "text" -> target.text
                        else -> null
                    }
                }
            }
            is VirtualRule -> {
                target[name]
            }
            is Map<*, *> -> {
                target[name]
            }
            else -> null
        }
        return if (result is List<*>)
            result[index]
        else result
    }

    override fun count(
        translator: Translator,
        context: Any?
    ): Int {
        val target = if (on != null) on.resolve(translator, context, 0) else context
        return when (target) {
            is ParserRuleContext -> when (type) {
                Type.Token -> target.children.asSequence()
                    .mapNotNull { it as? TerminalNode }.filter { it.symbol.type == -this.ruleIndex }.count() - index
                Type.Parent -> 1
                Type.Rule -> target.children.asSequence()
                    .mapNotNull { it as? ParserRuleContext }.filter { it.ruleIndex == this.ruleIndex }
                    .count() - index
                Type.Other -> when (name) {
                    "text" -> 1
                    else -> 0
                }
            }
            else -> resolve(translator, context, index).let { if (it is List<*>) it.size else if (it != null) 1 else 0 }
        }
    }

    override fun toString(): String {
        return (if (on != null) "$on." else "") + name + (if (index != 0) "[$index]" else "")
    }
}

data class ElvisExpression(
    val options: List<Expression>
) : Expression {
    override fun resolve(translator: Translator, context: Any?, offset: Int): Any? {
        return options.asSequence().mapNotNull { it.resolve(translator, context, offset) }.firstOrNull()
    }

    override fun toString(): String = "first(" + options.joinToString() + ")"
}
