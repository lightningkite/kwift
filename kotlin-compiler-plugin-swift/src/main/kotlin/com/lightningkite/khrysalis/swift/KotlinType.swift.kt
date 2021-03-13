package com.lightningkite.khrysalis.swift

import com.lightningkite.khrysalis.replacements.TemplateRendering
import com.lightningkite.khrysalis.replacements.emitTemplateNoDedup
import com.lightningkite.khrysalis.replacements.replacements
import com.lightningkite.khrysalis.util.forEachBetween
import org.jetbrains.kotlin.builtins.functions.FunctionClassDescriptor
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.types.KotlinType

fun KotlinType.swift(out: SwiftFileEmitter) {
    when {
        replacements.getType(this) != null -> {
            val type = this
            val rule = replacements.getType(type)!!
            val baseType = type.constructor
            val typeParametersByName = type.arguments.withIndex()
                .associate { (index, item) -> baseType.parameters[index].name.asString() to item }
            out.emitTemplateNoDedup(rule.template, object: TemplateRendering {
                override fun typeParameter(name: String) {
                    typeParametersByName[name]!!.swift(out)
                }
                override fun typeParameterByIndex(index: Int) {
                    type.arguments.get(index).swift(out)
                }
            })
            if (type.isMarkedNullable) {
                out.append('?')
            }
        }
        else -> {
            when (val desc = this.constructor.declarationDescriptor) {
                is FunctionClassDescriptor -> {
                    if (this.isMarkedNullable) {
                        out.append('(')
                    }
                    if (writingParameter > 0 && this.annotations.let {
                            it.hasAnnotation(FqName("com.lightningkite.butterfly.Escaping")) || it.hasAnnotation(FqName("com.lightningkite.butterfly.escaping"))
                        }) {
                        out.append("@escaping ")
                    }
                    out.append('(')
                    writingParameter++
                    this.arguments.dropLast(1).forEachBetween(
                        forItem = { typeProjection -> typeProjection.swift(out) },
                        between = { out.append(", ") }
                    )
                    writingParameter--
                    out.append(") -> ")
                    this.arguments.last().swift(out)
                    if (this.isMarkedNullable) {
                        out.append(')')
                    }
                }
                is ClassDescriptor -> {
                    var current: ClassDescriptor = desc
                    val items = arrayListOf(current)
                    while (!current.swiftTopLevelMessedUp) {
                        current = current.containingDeclaration as? ClassDescriptor ?: break
                        items += current
                    }
                    items.asReversed().forEachBetween(
                        forItem = { out.append(it.swiftTopLevelName) },
                        between = { out.append('.' )}
                    )
                    this.arguments.takeUnless { it.isEmpty() }?.let {
                        out.append('<')
                        it.forEachBetween(
                            forItem = { it.swift(out) },
                            between = { out.append(", " )}
                        )
                        out.append('>')
                    }
                }
                is TypeParameterDescriptor -> {
                    out.append(desc.name.asString())
                }
                else -> {
                    println("What is this? ${desc?.let { it::class.qualifiedName }}")
                }
            }
            if (this.isMarkedNullable) {
                out.append('?')
            }
        }
    }
}

fun KotlinType.swiftBasic(out: SwiftFileEmitter) {
    when {
        replacements.getType(this) != null -> {
            val type = this
            val rule = replacements.getType(type)!!
            out.emitTemplateNoDedup(rule.template, object: TemplateRendering {})
            if (type.isMarkedNullable) {
                out.append('?')
            }
        }
        else -> {
            when (val desc = this.constructor.declarationDescriptor) {
                is ClassDescriptor -> {
                    var current: ClassDescriptor = desc
                    val items = arrayListOf(current)
                    while (!current.swiftTopLevelMessedUp) {
                        current = current.containingDeclaration as? ClassDescriptor ?: break
                        items += current
                    }
                    items.asReversed().forEachBetween(
                        forItem = { out.append(it.swiftTopLevelName) },
                        between = { out.append('.' )}
                    )
                }
                is TypeParameterDescriptor -> {
                    out.append(desc.name.asString())
                }
                else -> {
                    println("What is this? ${desc?.let { it::class.qualifiedName }}")
                }
            }
            if (this.isMarkedNullable) {
                out.append('?')
            }
        }
    }
}