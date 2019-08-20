package com.lightningkite.kwift.altswift

import com.lightningkite.kwift.utils.forEachBetween
import org.antlr.v4.runtime.ParserRuleContext
import org.jetbrains.kotlin.KotlinParser

fun SwiftAltListener.registerType() {
    handle<KotlinParser.SimpleUserTypeContext> { item ->
        val name = typeReplacements[item.simpleIdentifier().text] ?: item.simpleIdentifier().text
        direct.append(name)
        item.typeArguments()?.let {
            direct.append("<")
            it.typeProjection().forEachBetween(
                forItem = {
                    write(it.type())
                },
                between = {
                    direct.append(", ")
                }
            )
            direct.append(">")
        }
    }
    handle<KotlinParser.FunctionTypeParametersContext> { item ->
        direct.append('(')
        item.children.asSequence()
            .filter { it is KotlinParser.TypeContext || it is KotlinParser.ParameterContext }
            .forEachBetween(
                forItem = { write(it as ParserRuleContext) },
                between = { direct.append(", ") }
            )
        direct.append(')')
    }
    handle<KotlinParser.ParenthesizedTypeContext> { item -> defaultWrite(item, "") }
    handle<KotlinParser.ParenthesizedUserTypeContext> { item -> defaultWrite(item, "") }
    handle<KotlinParser.SingleAnnotationContext> { item ->
        if (item.unescapedAnnotation().text == "escaping") {
            val typeContext = item
                .parentIfType<KotlinParser.AnnotationContext>()
                ?.parentIfType<KotlinParser.TypeModifierContext>()
                ?.parentIfType<KotlinParser.TypeModifiersContext>()
                ?.parentIfType<KotlinParser.TypeContext>()
            if (typeContext?.getParentAnnotationTargetTypeContext() == filterEscapingAnnotation) {
                return@handle
            }
        }
        direct.append('@')
        write(item.unescapedAnnotation())
    }
}