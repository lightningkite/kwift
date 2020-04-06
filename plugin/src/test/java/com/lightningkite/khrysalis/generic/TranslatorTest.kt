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
    val ruleSourceXml = """
        TextView do
            "<p style=\""
            textColor
            "\">"
            text
            "</p>"
            ;
        textColor do
            "color: "
            asColor
            ";"
        background
            when 
                asColor.colorSetResource
            do () //we'll set this up later in CSS
        background do
            "background: "
            asColor
            ";"
        asColor do
            color
            colorResource
            colorSetResource
        color do
            "#" text
        colorResource do
            "#" rawColor
        colorSetResource do
            "#" setty
            
    """.trimIndent()

    @Test fun simpleTest(){
        val translator = Translator(SourceLanguage.kotlin)
        translator.add(ruleSource.charStream())
        for(item in translator.rules[KotlinParser.RULE_importHeader]){
            println(item)
        }
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

        /*output looks like this:
        package com . test
        Not a kotlin import:
        com/lightningkite/khrysalis/
        Dude, that's a long import.
        Caught your kotlin import
        fun main ( ) {
        println ( " Hello World! " )
        println ( 1 + 2 )
        }
         */
    }

}
