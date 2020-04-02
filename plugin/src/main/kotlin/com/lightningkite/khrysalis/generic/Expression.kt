package com.lightningkite.khrysalis.generic

import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.RuleContext
import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.TerminalNode


interface Expression {
    fun resolve(translator: Translator, context: ParseTree, offset: Int = 0): Any?
    fun count(
        translator: Translator,
        context: ParseTree
    ): Int = Int.MAX_VALUE
}

interface SettableExpression : Expression {
    fun set(value: Any?, translator: Translator, context: ParseTree, offset: Int = 0)
}

data class ConstantExpression(val value: Any?) : Expression {
    override fun resolve(
        translator: Translator,
        context: ParseTree,
        offset: Int
    ): Any? = value
}

data class PropertyOnRule(
    val on: Expression? = null,
    val property: String
) : SettableExpression {
    override fun resolve(
        translator: Translator,
        context: ParseTree,
        offset: Int
    ): Any? {
        if(on == null && property == "global") return translator.globals
        val ctx = if (on != null) on.resolve(translator, context, 0) else context
        return when (ctx) {
            is ParseTree -> {
                when(property){
                    "text" -> ctx.text
                    else -> null
                }
            }
            is Map<*, *> -> {
                ctx[property]
            }
            else -> null
        }
    }

    override fun set(value: Any?, translator: Translator, context: ParseTree, offset: Int) {
        val ctx = if (on != null) on.resolve(translator, context, 0) else context
        TODO()
    }

    override fun toString(): String {
        return (if (on != null) "$on->" else "") + property
    }
}

data class ParameterExpression(val key: String): Expression {
    override fun resolve(translator: Translator, context: ParseTree, offset: Int): Any? {
        TODO("Not yet implemented")
    }
}

data class GlobalVariable(
    val property: String
) : SettableExpression {
    override fun resolve(translator: Translator, context: ParseTree, offset: Int): Any? {
        return translator.globals[property]
    }

    override fun set(value: Any?, translator: Translator, context: ParseTree, offset: Int) {
        translator.globals[property] = value
    }
}

data class Path(
    val name: String,
    val type: Type,
    val ruleIndex: Int = 0,
    val index: Int = 0,
    val on: Expression? = null
) : Expression {
    enum class Type { Token, Rule, Parent }

    override fun resolve(
        translator: Translator,
        context: ParseTree,
        offset: Int
    ): ParseTree? {
        val target = if(on != null) on.resolve(translator, context, 0) else context
        return when(type) {
            Type.Token -> if(target !is ParserRuleContext ) null else target.children.asSequence().mapNotNull { it as? TerminalNode }.filter { it.symbol.type == -this.ruleIndex }.drop(index + offset).firstOrNull()
            Type.Parent -> context.parent
            Type.Rule -> if(target !is ParserRuleContext ) null else target.children.asSequence().mapNotNull { it as? ParserRuleContext }.filter { it.ruleIndex == this.ruleIndex }.drop(index + offset).firstOrNull()
        }
    }

    override fun count(
        translator: Translator,
        context: ParseTree
    ): Int {
        val target = if(on != null) on.resolve(translator, context, 0) else context
        return when(type) {
            Type.Token -> if(target !is ParserRuleContext ) 0 else target.children.asSequence().mapNotNull { it as? TerminalNode }.filter { it.symbol.type == -this.ruleIndex }.count() - index
            Type.Parent -> 1
            Type.Rule -> if(target !is ParserRuleContext ) 0 else target.children.asSequence().mapNotNull { it as? ParserRuleContext }.filter { it.ruleIndex == this.ruleIndex }.count() - index
        }
    }

    override fun toString(): String {
        return (if (on != null) "$on." else "") + name + (if (index != 0) "[$index]" else "")
    }
}

data class ElvisExpression(
    val options: List<Expression>
): Expression {
    override fun resolve(translator: Translator, context: ParseTree, offset: Int): Any? {
        return options.asSequence().mapNotNull { it.resolve(translator, context, offset) }.firstOrNull()
    }
}
