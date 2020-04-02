package com.lightningkite.khrysalis.generic

import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.TerminalNode


interface Directive {
    fun evaluate(translator: Translator, context: ParseTree, out: Appendable, offset: Int = 0)
    fun count(translator: Translator, context: ParseTree): Int = Int.MAX_VALUE
}

data class DirectiveEmit(val expression: Expression, val default: String? = null): Directive {
    override fun count(translator: Translator, context: ParseTree): Int {
        return if (default != null) Int.MAX_VALUE
        else expression.count(translator, context)
    }
    override fun evaluate(translator: Translator, context: ParseTree, out: Appendable, offset: Int) {
        val result = expression.resolve(translator, context, offset)
        when(result) {
            is ParserRuleContext -> translator.convert(result, out)
            is TerminalNode -> translator.convert(result, out)
            else -> out.append(result.toString())
        }
    }
}

data class DirectiveBlock(val directives: List<Directive>): Directive {
    override fun count(translator: Translator, context: ParseTree): Int {
        return directives.asSequence().map { it.count(translator, context) }.min() ?: return 0
    }
    override fun evaluate(translator: Translator, context: ParseTree, out: Appendable, offset: Int) {
        for (c in directives) {
            c.evaluate(translator, context, out, offset)
        }
    }
}

data class DirectiveRepeat(val subDirective: Directive, val times: Int? = null) : Directive {
    override fun evaluate(translator: Translator, context: ParseTree, out: Appendable, offset: Int) {
        repeat(times ?: subDirective.count(translator, context)) { offsetC ->
            subDirective.evaluate(translator, context, out, offsetC)
        }
    }
}

data class DirectiveIf(val condition: Condition, val directive: Directive) : Directive {
    override fun evaluate(translator: Translator, context: ParseTree, out: Appendable, offset: Int) {
        if(condition.evaluate(translator, context)) {
            directive.evaluate(translator, context, out, offset)
        }
    }
}

data class DirectiveSetVariable(val property: SettableExpression, val value: Expression): Directive {
    override fun evaluate(translator: Translator, context: ParseTree, out: Appendable, offset: Int) {
        property.set(value.resolve(translator, context, offset), translator, context, offset)
    }
}
