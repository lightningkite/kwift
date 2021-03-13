package com.lightningkite.khrysalis.swift

import org.jetbrains.kotlin.psi.KtAnnotation
import org.jetbrains.kotlin.psi.KtAnnotationEntry

fun KtAnnotation.swift(out: SwiftFileEmitter){ /*skip*/ }
fun KtAnnotationEntry.swift(out: SwiftFileEmitter){ /*skip*/ }