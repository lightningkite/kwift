package com.lightningkite.khrysalis.swift

import org.jetbrains.kotlin.psi.KtTypeAlias

fun KtTypeAlias.swift(out: SwiftFileEmitter){
    swiftVisibility(out)
    out.append(" typealias ")
    swiftId(out)
    this.typeParameterList?.swift(out)
    out.append(" = ")
    this.getTypeReference()
}