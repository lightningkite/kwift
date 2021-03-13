package com.lightningkite.khrysalis.swift

import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtModifierListOwner
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier


fun KtModifierListOwner.swiftVisibility(out: SwiftFileEmitter) {
    when {
        this.hasModifier(KtTokens.ABSTRACT_KEYWORD) ||
                this.hasModifier(KtTokens.OPEN_KEYWORD) -> out.append("open")
        else -> this.visibilityModifier()?.text?.let { out.append(it) } ?: out.append("public")
    }
}