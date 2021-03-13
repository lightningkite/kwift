package com.lightningkite.khrysalis.swift

import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtBinaryExpressionWithTypeRHS


fun KtBinaryExpressionWithTypeRHS.swift(out: SwiftFileEmitter){
    when(operationReference.getReferencedNameElementType()) {
        KtTokens.AS_SAFE -> {
            left.swift(out)
            out.append(" as? ")
            right!!.swift(out)
        }
        KtTokens.AS_KEYWORD -> {
            left.swift(out)
            out.append(" as! ")
            right!!.swift(out)
        }
    }
}