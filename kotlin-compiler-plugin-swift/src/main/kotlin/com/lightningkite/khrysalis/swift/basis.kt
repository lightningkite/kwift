package com.lightningkite.khrysalis.swift

import com.lightningkite.khrysalis.generic.FileEmitter
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.TypeProjection

@Deprecated("Warning!  This should be more specific")
fun PsiElement.swift(out: SwiftFileEmitter): Unit = TODO()

@Deprecated("Warning!  This should be more specific")
fun TypeProjection.swift(out: SwiftFileEmitter): Unit = TODO()