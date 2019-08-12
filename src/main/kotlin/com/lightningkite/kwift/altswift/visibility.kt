package com.lightningkite.kwift.altswift

import org.jetbrains.kotlin.KotlinParser

fun KotlinParser.ModifierListContext?.visibilityString(): String {
    val v = this?.modifier()?.asSequence()?.mapNotNull { it.visibilityModifier() }?.firstOrNull()
    return when {
        v == null -> "public"
        v.INTERNAL() != null -> "internal"
        v.PRIVATE() != null -> "private"
        v.PROTECTED() != null -> "protected"
        v.PUBLIC() != null -> "public"
        else -> "public"
    }
}
