package com.lightningkite.khrysalis.generic

import com.lightningkite.khrysalis.utils.binaryInsertBy
import com.lightningkite.khrysalis.utils.forEachBetween
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.TerminalNode


class Translator(val sourceLanguage: SourceLanguage) {
    val rules: Array<ArrayList<RuleOption>> = Array(sourceLanguage.ruleStrings.size) { ArrayList<RuleOption>(0) }
    val tokens: Array<ArrayList<RuleOption>> = Array(sourceLanguage.tokenStrings.size) { ArrayList<RuleOption>(0) }
    val virtualRules = HashMap<String, List<RuleOption>>()
    val globals: HashMap<String, Any?> = HashMap()
    val directiveMacros: HashMap<String, Directive> = HashMap()
    val parameters: HashMap<String, Any?> = HashMap()

    fun convert(context: ParserRuleContext, out: Appendable) {
        rules[context.ruleIndex].firstOrNull {
            it.condition?.evaluate(this, context) != false
        }?.invoke(this, context, out) ?: run {
            context.children.forEachBetween(
                forItem = {
                    if (it is ParserRuleContext)
                        convert(it, out)
                    else
                        out.append(it.text)
                },
                between = {
                    out.append(' ')
                }
            )
        }
    }

    fun convert(context: TerminalNode, out: Appendable) {
        tokens[context.symbol.type].firstOrNull {
            it.condition?.evaluate(this, context) != false
        }?.invoke(this, context, out) ?: out.append(context.text)
    }

    fun convert(context: VirtualRule, out: Appendable) {
        virtualRules[context.rule]?.firstOrNull {
            it.condition?.evaluate(this, context) != false
        }?.invoke(this, context, out)
    }
}

data class RuleOption(
    val type: Path.Type,
    val ruleIndex: Int = 0,
    val condition: Condition? = null,
    val priority: Int = condition?.complexity ?: 0,
    val directives: List<Directive>
) {
    fun invoke(translator: Translator, context: Any?, out: Appendable, offset: Int = 0) {
        for (component in directives) {
            component.evaluate(translator, context, priority, out, offset)
        }
    }
}

interface VirtualRule {
    val rule: String
    operator fun get(key: String): Any?
}
