package com.lightningkite.khrysalis.swift

import com.lightningkite.khrysalis.util.forEachBetween
import com.lightningkite.khrysalis.util.fqNameWithoutTypeArgs
import org.jetbrains.kotlin.builtins.functions.FunctionClassDescriptor
import org.jetbrains.kotlin.builtins.isFunctionType
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.js.descriptorUtils.getJetTypeFqName
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.types.*
import org.jetbrains.kotlin.types.checker.NewCapturedTypeConstructor
import org.jetbrains.kotlin.types.typeUtil.*

private val primitiveTypes = setOf(
    "kotlin.Byte",
    "kotlin.Short",
    "kotlin.Int",
    "kotlin.Long",
    "kotlin.UByte",
    "kotlin.UShort",
    "kotlin.UInt",
    "kotlin.ULong",
    "kotlin.Float",
    "kotlin.Double",
    "kotlin.Char",
    "kotlin.String",
    "kotlin.Boolean",
    "kotlin.Unit",
    "kotlin.Any"
)

fun KotlinType.isPrimitive() = fqNameWithoutTypeArgs in primitiveTypes

val partOfParameterLocal = ThreadLocal<Int>()
var writingParameter: Int
    get() = partOfParameterLocal.get() ?: 0
    set(value) {
        partOfParameterLocal.set(value)
    }

fun KotlinType.worksAsSwiftConstraint(): Boolean {
    if (this.fqNameWithoutTypeArgs == "kotlin.Any") return false
    return when (this) {
        is WrappedType -> false
        is SimpleType -> true
        is FlexibleType -> false //could work later?
    } && this.arguments.all { it.type.arguments.isEmpty() && !(it.type.constructor.declarationDescriptor is TypeParameterDescriptor && it.type.isMarkedNullable) }
}

fun CallableDescriptor.worksAsSwiftConstraint(): Boolean {
    return extensionReceiverParameter?.type?.worksAsSwiftConstraint() != false
}

val knownTypeParameterNames = mapOf(
    "kotlin.sequences.Sequence" to listOf("Element"),
    "kotlin.collections.Iterable" to listOf("Element"),
    "kotlin.collections.Collection" to listOf("Element"),
    "kotlin.collections.List" to listOf("Element"),
    "kotlin.Array" to listOf("Element"),
    "kotlin.collections.Map" to listOf("Key", "Value")
)
