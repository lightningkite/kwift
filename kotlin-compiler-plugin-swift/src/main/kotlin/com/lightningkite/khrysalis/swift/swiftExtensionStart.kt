package com.lightningkite.khrysalis.swift

import com.lightningkite.khrysalis.abstractions.BasicType
import com.lightningkite.khrysalis.abstractions.KtUserTypeBasic
import com.lightningkite.khrysalis.analysis.resolvedType
import com.lightningkite.khrysalis.util.forEachBetween
import com.lightningkite.khrysalis.util.fqNameWithoutTypeArgs
import org.jetbrains.kotlin.descriptors.CallableDescriptor
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
import org.jetbrains.kotlin.descriptors.isFinalOrEnum
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.psi.KtTypeParameterList
import org.jetbrains.kotlin.psi.KtTypeReference
import org.jetbrains.kotlin.psi.KtUserType
import org.jetbrains.kotlin.types.typeUtil.isTypeParameter

fun swiftExtensionStart(
    out: SwiftFileEmitter,
    forDescriptor: CallableDescriptor,
    receiver: KtTypeReference?,
    typeParams: KtTypeParameterList?
) {
    out.append("extension ")
    val t = forDescriptor.extensionReceiverParameter!!.type
    val baseClass = t.constructor.declarationDescriptor as? ClassDescriptor
    receiver?.typeElement?.let { it as? KtUserType }?.swiftBasic(out) ?: t.swiftBasic(out)
    forDescriptor.annotations.findAnnotation(FqName("com.lightningkite.butterfly.SwiftExtensionWhere"))
        ?.let {
            val value = it.allValueArguments[Name.identifier("text")]!!.value as String
            if (value.isNotBlank()) {
                out.append(" where ")
                out.append(value)
            }
        } ?: t.arguments
        .mapIndexedNotNull { index, arg ->
            if (arg.type.isTypeParameter()) {
                val x = (arg.type.constructor.declarationDescriptor as? TypeParameterDescriptor)?.name?.asString()
                val match = typeParams?.parameters?.find { it.name == x }?.extendsBound
                    ?: return@mapIndexedNotNull null
                val type = match
                if (type.resolvedType?.constructor?.declarationDescriptor is TypeParameterDescriptor) return@mapIndexedNotNull null
                val swiftExactly = type.annotations.flatMap { it.entries }.find {
                    it.typeReference?.text?.endsWith("SwiftExactly", true) == true
                }?.valueArguments?.let {
                    it.firstOrNull()?.getArgumentExpression()?.text?.trim('"')
                        ?: knownTypeParameterNames[forDescriptor.extensionReceiverParameter?.type?.fqNameWithoutTypeArgs]?.getOrNull(index)
                        ?: "T"
                }
                val swiftDescendsFrom = type.annotations.flatMap { it.entries }.find {
                    it.typeReference?.text?.endsWith("SwiftDescendsFrom", true) == true
                }?.valueArguments?.let {
                    it.firstOrNull()?.getArgumentExpression()?.text?.trim('"')
                        ?: knownTypeParameterNames[forDescriptor.extensionReceiverParameter?.type?.fqNameWithoutTypeArgs]?.getOrNull(index)
                        ?: "T"
                }
                when {
                    swiftExactly != null -> {
                        listOf(swiftExactly, " == ", type)
                    }
                    swiftDescendsFrom != null -> {
                        listOf(swiftDescendsFrom, " : ", type)
                    }
                    else -> {
                        val name = x ?: baseClass?.declaredTypeParameters?.get(index)?.name?.asString()
                        val c = type.resolvedType?.constructor?.declarationDescriptor as? ClassDescriptor
                        if (c?.isFinalOrEnum == false) {
                            listOf(name, ": ", type)
                        } else {
                            listOf(name, " == ", type)
                        }
                    }
                }
            } else {
                val type = arg.type
                if (type.constructor.declarationDescriptor is TypeParameterDescriptor) return@mapIndexedNotNull null
                val swiftExactly = type.annotations.find {
                    it.fqName?.asString()?.endsWith("SwiftExactly", true) == true
                }?.allValueArguments?.entries?.let {
                    it.firstOrNull()?.value?.value as? String
                        ?: knownTypeParameterNames[forDescriptor.extensionReceiverParameter?.type?.fqNameWithoutTypeArgs]?.getOrNull(index)
                        ?: "T"
                }
                val swiftDescendsFrom = type.annotations.find {
                    it.fqName?.asString()?.endsWith("SwiftDescendsFrom", true) == true
                }?.allValueArguments?.entries?.let {
                    it.firstOrNull()?.value?.value as? String
                        ?: knownTypeParameterNames[forDescriptor.extensionReceiverParameter?.type?.fqNameWithoutTypeArgs]?.getOrNull(index)
                        ?: "T"
                }
                when {
                    swiftExactly != null -> {
                        listOf(swiftExactly, " == ", type)
                    }
                    swiftDescendsFrom != null -> {
                        listOf(swiftDescendsFrom, ": ", type)
                    }
                    else -> {
                        val name =
                            knownTypeParameterNames[forDescriptor.extensionReceiverParameter?.type?.fqNameWithoutTypeArgs]?.getOrNull(index)
                                ?: baseClass?.declaredTypeParameters?.get(index)?.name?.asString()
                        val c = type.constructor.declarationDescriptor as? ClassDescriptor
                        if (c?.isFinalOrEnum == false) {
                            listOf(name, ": ", type)
                        } else {
                            listOf(name, " == ", type)
                        }
                    }
                }
            }
        }
        .takeUnless { it.isEmpty() }
        ?.let {
            out.append(" where ")
            it.forEachBetween(
                forItem = { it.swift(out) },
                between = { out.append(", ") }
            )
        }
    out.append(" {")
}