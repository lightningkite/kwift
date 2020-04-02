package com.lightningkite.khrysalis.generic

import org.jetbrains.kotlin.KotlinParser
import org.junit.Assert.*
import org.junit.Test

class TranslatorTest {
    val ruleSource = """
        importHeader do "Not a kotlin import:\n" (identifier.simpleIdentifier "/")* "\n";
        importHeader
            when identifier.simpleIdentifier[0].text == "kotlin"
            do "Caught your kotlin import\n";
        importHeader
            when identifier.simpleIdentifier[5]
            do "Dude, that's a long import.\n"
    """.trimIndent()

    @Test fun simpleTest(){
        val translator = Translator.collect(SourceLanguage.kotlin, ruleSource.charStream().getRuleOptions(SourceLanguage.kotlin))
        println(translator.rules[KotlinParser.RULE_importHeader])
        val testFile = """
            package com.test
            
            import com.lightningkite.khrysalis.*
            import com.lightningkite.khrysalis.observable.binding.Thing
            import kotlin.IllegalStateException
            
            fun main(){
                println("Hello World!")
                println(1 + 2)
            }
        """.trimIndent()
        translator.convert(testFile.charStream().kotlin().kotlinFile(), System.out)
    }

}
