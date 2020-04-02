package com.lightningkite.khrysalis.generic

import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CommonTokenStream
import org.jetbrains.kotlin.KotlinLexer
import org.jetbrains.kotlin.KotlinParser

class SourceLanguage(
    val ruleStrings: Array<String> = KotlinParser.ruleNames,
    val tokenStrings: Array<String> = KotlinLexer.ruleNames
) {
    companion object {
        val kotlin = SourceLanguage()
    }
    data class IndexAndType(
        val index: Int,
        val type: Path.Type
    )
    val getData = ruleStrings.withIndex().associate { it.value to IndexAndType(it.index,
        Path.Type.Rule
    ) } +
            tokenStrings.withIndex().associate { it.value to IndexAndType(it.index,
                Path.Type.Token
            ) } +
            Pair("parent", IndexAndType(0, Path.Type.Parent))
}

fun CharStream.kotlin() = KotlinParser(CommonTokenStream(KotlinLexer(this)))
