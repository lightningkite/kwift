package com.lightningkite.khrysalis.generic

import com.lightningkite.khrysalis.utils.binaryInsertBy
import com.lightningkite.khrysalis.utils.forEachBetween
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.TerminalNode


class Translator(
    val rules: Array<out List<RuleOption>>,
    val tokens: Array<out List<RuleOption>>
) {
    val globals: HashMap<String, Any?> = HashMap()
    val directiveMacros: HashMap<String, DirectiveMacroDefinition> = HashMap()
    val parameters: HashMap<String, Any?> = HashMap()

    companion object {
        fun collect(sourceLanguage: SourceLanguage, sequence: Sequence<RuleOption>): Translator {
            val newRules = Array<ArrayList<RuleOption>>(sourceLanguage.ruleStrings.size) { ArrayList(0) }
            val newTokens = Array<ArrayList<RuleOption>>(sourceLanguage.tokenStrings.size) { ArrayList(0) }
            for (item in sequence) {
                when (item.type) {
                    Path.Type.Token -> newTokens[item.ruleIndex].binaryInsertBy(item) { -it.priority }
                    Path.Type.Rule -> newRules[item.ruleIndex].binaryInsertBy(item) { -it.priority }
                    Path.Type.Parent -> throw IllegalArgumentException()
                }
            }
            return Translator(newRules, newTokens)
        }
    }

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
}

data class RuleOption(
    val type: Path.Type,
    val ruleIndex: Int = 0,
    val condition: Condition? = null,
    val priority: Int = condition?.complexity ?: 0,
    val directives: List<Directive>
) {
    fun invoke(translator: Translator, context: ParseTree, out: Appendable, offset: Int = 0) {
        for (component in directives) {
            component.evaluate(translator, context, out, offset)
        }
    }
}

data class VirtualRule(
    val rule: String,
    val parts: Map<String, String>
)
