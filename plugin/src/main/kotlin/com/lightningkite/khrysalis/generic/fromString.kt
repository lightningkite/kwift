package com.lightningkite.khrysalis.generic

import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CommonTokenStream
import org.jetbrains.kotlin.TranslationalLexer
import org.jetbrains.kotlin.TranslationalParser
import java.io.File

fun File.charStream() = org.antlr.v4.runtime.ANTLRFileStream(this.path)
fun String.charStream() = ANTLRInputStream(this)
fun CharStream.getRuleOptions(sourceLanguage: SourceLanguage): Sequence<RuleOption> {
    val file = TranslationalParser(CommonTokenStream(TranslationalLexer(this))).file()
    return file.fileEntry().asSequence()
        .mapNotNull { it.parserRuleOption() }
        .map {
            val (index, type) = sourceLanguage.getData[it.PathPart().text] ?: throw IllegalArgumentException("Unrecognized type ${it.PathPart().text}")
            val condition = it.condition()?.let {
                if(it.size == 1){
                    it[0].convert(sourceLanguage)
                } else {
                    ConditionAll(it.map { it.convert(sourceLanguage) })
                }
            }
            RuleOption(
                type = type,
                ruleIndex = index,
                condition = condition,
                priority = it.Index()?.text?.toInt() ?: condition?.complexity ?: 0,
                directives = it.directive().map { it.convert(sourceLanguage) }
            )
        }
}

fun TranslationalParser.ConditionContext.convert(sourceLanguage: SourceLanguage): Condition = conditionAll()?.convert(sourceLanguage)
    ?: conditionAny()?.convert(sourceLanguage)
    ?: conditionEquals()?.convert(sourceLanguage)
    ?: conditionExists()?.convert(sourceLanguage)
    ?: throw IllegalArgumentException()
fun TranslationalParser.ConditionExistsContext.convert(sourceLanguage: SourceLanguage): ConditionExists = ConditionExists(
    expression = this.expression().convert(sourceLanguage)
)
fun TranslationalParser.ConditionEqualsContext.convert(sourceLanguage: SourceLanguage): ConditionEquals = ConditionEquals(
    this.expression(0).convert(sourceLanguage),
    this.expression(1).convert(sourceLanguage)
)
fun TranslationalParser.ConditionAnyContext.convert(sourceLanguage: SourceLanguage): ConditionAny = ConditionAny(
    this.condition().map { it.convert(sourceLanguage) }
)
fun TranslationalParser.ConditionAllContext.convert(sourceLanguage: SourceLanguage): ConditionAll = ConditionAll(
    this.condition().map { it.convert(sourceLanguage) }
)
fun TranslationalParser.ExpressionContext.convert(sourceLanguage: SourceLanguage): Expression = parameterExpression()?.convert(sourceLanguage)
    ?: path()?.convert(sourceLanguage)
    ?: constantExpression()?.convert(sourceLanguage)
    ?: parenthesizedExpression()?.expression()?.convert(sourceLanguage)
    ?: elvisExpression()?.convert(sourceLanguage)
    ?: throw IllegalArgumentException()
fun TranslationalParser.ParameterExpressionContext.convert(sourceLanguage: SourceLanguage): ParameterExpression = ParameterExpression(
    this.PathPart().text
)
fun TranslationalParser.PathContext.convert(sourceLanguage: SourceLanguage): Expression {
    val parts = this.pathSection()
    val text = parts[0].PathPart().text
    val data = sourceLanguage.getData[text]
    var current: Expression = if(data != null)
        Path(
        name = text,
        type = data.type,
        ruleIndex = data.index,
        index = parts[0].Index()?.toString()?.toInt() ?: 0,
        on = null
    ) else PropertyOnRule(property = text)

    var index = 0
    parts.subList(1, parts.size).forEach { part ->
        index++
        @Suppress("NAME_SHADOWING") val text = part.PathPart().text
        @Suppress("NAME_SHADOWING") val data = sourceLanguage.getData[text]
        current = if(data != null)
            Path(
                name = text,
                type = data.type,
                ruleIndex = data.index,
                index = part.Index()?.toString()?.toInt() ?: 0,
                on = current
            ) else PropertyOnRule(property = text, on = current)
    }

    return current
}
fun TranslationalParser.ConstantExpressionContext.convert(sourceLanguage: SourceLanguage): ConstantExpression = ConstantExpression(
    value = this.Index()?.text?.toInt() ?: this.String()!!.text.trim('"').replace("\\n", "\n")
)
fun TranslationalParser.ElvisExpressionContext.convert(sourceLanguage: SourceLanguage): ElvisExpression = ElvisExpression(
    this.expression().map { it.convert(sourceLanguage) }
)
fun TranslationalParser.DirectiveContext.convert(sourceLanguage: SourceLanguage): Directive = this.expression()?.convert(sourceLanguage)?.let { DirectiveEmit(it) }
    ?: this.directiveBlock()?.convert(sourceLanguage)
    ?: this.directiveIf()?.convert(sourceLanguage)
    ?: this.directiveRepeat()?.convert(sourceLanguage)
    ?: this.directiveSet()?.convert(sourceLanguage)
    ?: throw IllegalArgumentException()
fun TranslationalParser.DirectiveBlockContext.convert(sourceLanguage: SourceLanguage): DirectiveBlock = DirectiveBlock(
    directive().map { it.convert(sourceLanguage) }
)
fun TranslationalParser.DirectiveRepeatContext.convert(sourceLanguage: SourceLanguage): DirectiveRepeat = DirectiveRepeat(
    directiveBlock().convert(sourceLanguage)
)
fun TranslationalParser.DirectiveIfContext.convert(sourceLanguage: SourceLanguage): DirectiveIf = DirectiveIf(
    this.condition().convert(sourceLanguage),
    this.directive(0).convert(sourceLanguage),
    this.directive(1)?.convert(sourceLanguage)
)
fun TranslationalParser.DirectiveSetContext.convert(sourceLanguage: SourceLanguage): DirectiveSetVariable = DirectiveSetVariable(
    this.expression(0).convert(sourceLanguage) as SettableExpression,
    this.expression(1).convert(sourceLanguage)
)
