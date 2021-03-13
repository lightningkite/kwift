package com.lightningkite.khrysalis.swift

import org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner

fun PsiNameIdentifierOwner.swiftId(out: SwiftFileEmitter){
    out.append(text.safeSwiftIdentifier())
}