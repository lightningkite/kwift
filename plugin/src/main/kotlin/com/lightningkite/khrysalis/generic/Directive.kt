package com.lightningkite.khrysalis.generic

import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.TerminalNode


interface Directive {
    fun evaluate(translator: Translator, context: Any?, rulePriority: Int, out: Appendable, offset: Int = 0)
    fun count(translator: Translator, context: Any?): Int = Int.MAX_VALUE
}

data class DirectiveEmit(val expression: Expression, val default: String? = null): Directive {
    override fun count(translator: Translator, context: Any?): Int {
        return if (default != null) Int.MAX_VALUE
        else expression.count(translator, context)
    }
    override fun evaluate(translator: Translator, context: Any?, rulePriority: Int, out: Appendable, offset: Int) {
        val result = expression.resolve(translator, context, offset)
        when(result) {
            is ParserRuleContext -> translator.convert(result, out)
            is TerminalNode -> translator.convert(result, out)
            null -> return
            else -> out.append(result.toString())
        }
    }
}

data class DirectiveBlock(val directives: List<Directive>): Directive {
    override fun count(translator: Translator, context: Any?): Int {
        return directives.asSequence().map { it.count(translator, context) }.min() ?: return 0
    }
    override fun evaluate(translator: Translator, context: Any?, rulePriority: Int, out: Appendable, offset: Int) {
        for (c in directives) {
            c.evaluate(translator, context, rulePriority, out, offset)
        }
    }
}

data class DirectiveRepeat(val subDirective: Directive, val times: Int? = null) : Directive {
    override fun evaluate(translator: Translator, context: Any?, rulePriority: Int, out: Appendable, offset: Int) {
        repeat(times ?: subDirective.count(translator, context)) { offsetC ->
            subDirective.evaluate(translator, context, rulePriority, out, offsetC)
        }
    }
}

data class DirectiveLoop(val paramName: String, val countExpression: Expression, val subDirective: Directive) : Directive {
    override fun evaluate(translator: Translator, context: Any?, rulePriority: Int, out: Appendable, offset: Int) {
        val previousValue = translator.parameters[paramName]
        repeat(countExpression.count(translator, context)) { offsetC ->
            translator.parameters[paramName] = offsetC
            subDirective.evaluate(translator, context, rulePriority, out)
        }
        translator.parameters[paramName] = previousValue
    }
}

data class DirectiveIf(val condition: Condition, val directive: Directive, val elseDirective: Directive? = null) : Directive {
    override fun evaluate(translator: Translator, context: Any?, rulePriority: Int, out: Appendable, offset: Int) {
        if(condition.evaluate(translator, context)) {
            directive.evaluate(translator, context, rulePriority, out, offset)
        } else {
            elseDirective?.evaluate(translator, context, rulePriority, out, offset)
        }
    }
}

data class DirectiveSetVariable(val property: SettableExpression, val value: Expression): Directive {
    override fun evaluate(translator: Translator, context: Any?, rulePriority: Int, out: Appendable, offset: Int) {
        property.set(value.resolve(translator, context, offset), translator, context, offset)
    }
}

object DirectivePass: Directive {
    override fun evaluate(translator: Translator, context: Any?, rulePriority: Int, out: Appendable, offset: Int) {
        val nextRule = when(context){
            is ParserRuleContext -> translator.rules[context.ruleIndex].asSequence().dropWhile { it.priority >= rulePriority }
            is TerminalNode -> translator.tokens[context.symbol.tokenIndex].asSequence().dropWhile { it.priority >= rulePriority }
            is VirtualRule -> translator.virtualRules[context.rule]?.asSequence()?.dropWhile { it.priority >= rulePriority } ?: emptySequence()
            else -> emptySequence()
        }
        nextRule.firstOrNull { it.condition?.evaluate(translator, context) != false }?.invoke(translator, context, out, offset)
    }
}

data class DirectiveMacro(val name: String, val arguments: Map<String, Expression>): Directive {
    override fun evaluate(translator: Translator, context: Any?, rulePriority: Int, out: Appendable, offset: Int) {
        val macro = translator.directiveMacros[name]!!
        val previousValues = arguments.keys.associate { it to translator.parameters[it] }
        for((key, value) in arguments){
            translator.parameters[key] = value
        }
        macro.evaluate(translator, context, rulePriority, out, offset)
        for((key, value) in previousValues){
            translator.parameters[key] = value
        }
    }
}

data class DirectiveMacroDefinition(val name: String, val directive: Directive)
