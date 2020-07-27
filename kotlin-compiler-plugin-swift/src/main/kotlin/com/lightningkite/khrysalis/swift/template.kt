package com.lightningkite.khrysalis.swift

import com.lightningkite.khrysalis.generic.*
import com.lightningkite.khrysalis.swift.replacements.Template
import com.lightningkite.khrysalis.swift.replacements.TemplatePart
import com.lightningkite.khrysalis.util.AnalysisExtensions
import com.lightningkite.khrysalis.util.forEachBetween
import org.jetbrains.kotlin.psi.*


fun <T : Any> PartialTranslatorByType<SwiftFileEmitter, Unit, Any>.ContextByType<T>.dedup(
    requireWrapping: Boolean = false,
    action: DeDupEmitter.() -> Unit
) {
    val emitter = DeDupEmitter(this.partialTranslator as SwiftTranslator)
    action(emitter)
    val dedup = emitter.dedupNecessary
    if (dedup && requireWrapping) {
        -"run {"
    }
    emitter.finish { -it }
    if (dedup && requireWrapping) {
        -"}"
    }
}

fun <T : Any> PartialTranslatorByType<SwiftFileEmitter, Unit, Any>.ContextByType<T>.emitTemplate(
    requiresWrapping: Boolean,
    template: Template,
    prefix: Any? = null,
    receiver: Any? = null,
    dispatchReceiver: Any? = receiver,
    extensionReceiver: Any? = receiver,
    value: Any? = null,
    allParameters: Any? = null,
    operatorToken: Any? = null,
    parameter: (TemplatePart.Parameter) -> Any? = { value },
    typeParameter: (TemplatePart.TypeParameter) -> Any? = { null },
    parameterByIndex: (TemplatePart.ParameterByIndex) -> Any? = { value },
    typeParameterByIndex: (TemplatePart.TypeParameterByIndex) -> Any? = { null }
) {
    dedup(requiresWrapping) {
        -prefix
        fun onParts(list: List<TemplatePart>) {
            loop@ for (part in list) {
                when (part) {
                    is TemplatePart.Text -> -part.string
                    TemplatePart.Receiver -> deduplicateEmit(receiver)
                    TemplatePart.DispatchReceiver -> deduplicateEmit(dispatchReceiver)
                    TemplatePart.ExtensionReceiver -> deduplicateEmit(extensionReceiver)
                    TemplatePart.Value -> deduplicateEmit(value)
                    TemplatePart.AllParameters -> -allParameters
                    TemplatePart.OperatorToken -> -operatorToken
                    is TemplatePart.Parameter -> deduplicateEmit(parameter(part))
                    is TemplatePart.ParameterByIndex -> deduplicateEmit(parameterByIndex(part))
                    is TemplatePart.LambdaParameterContents -> {
                        -"\n"
                        val item = when (val p = part.pointer) {
                            is TemplatePart.ParameterByIndex -> parameterByIndex(p)
                            is TemplatePart.Parameter -> parameter(p)
                            else -> continue@loop
                        }
                        if (item is KtLambdaExpression) {
                            part.paramMap.zip(item.valueParameters.map { it.name }.takeUnless { it.isEmpty() } ?: listOf("it")).forEach {
                                -"let "
                                -it.second
                                -" = "
                                onParts(it.first)
                                -"\n"
                            }
                            -item.bodyExpression
                        } else {
                            -item
                            -"("
                            part.paramMap.forEachBetween(forItem = {
                                onParts(it)
                            }, between = {
                                -", "
                            })
                            - ")"
                        }
                        -"\n"
                    }
                    is TemplatePart.TypeParameter -> -typeParameter(part)
                    is TemplatePart.TypeParameterByIndex -> -typeParameterByIndex(part)
                    is TemplatePart.Import -> out.addImport(part)
                }
            }
        }
        onParts(template.parts)
    }
}

fun <T : Any> PartialTranslatorByType<SwiftFileEmitter, Unit, Any>.ContextByType<T>.emitTemplate(
    template: Template,
    receiver: Any? = null,
    dispatchReceiver: Any? = receiver,
    extensionReceiver: Any? = receiver,
    value: Any? = null,
    allParameters: Any? = null,
    operatorToken: Any? = null,
    parameter: (TemplatePart.Parameter) -> Any? = { value },
    typeParameter: (TemplatePart.TypeParameter) -> Any? = { null },
    parameterByIndex: (TemplatePart.ParameterByIndex) -> Any? = { value },
    typeParameterByIndex: (TemplatePart.TypeParameterByIndex) -> Any? = { null }
) {
    for (part in template.parts) {
        when (part) {
            is TemplatePart.Text -> -part.string
            TemplatePart.Receiver -> -receiver
            TemplatePart.DispatchReceiver -> -dispatchReceiver
            TemplatePart.ExtensionReceiver -> -extensionReceiver
            TemplatePart.Value -> -value
            TemplatePart.AllParameters -> -allParameters
            TemplatePart.OperatorToken -> -operatorToken
            is TemplatePart.Parameter -> -parameter(part)
            is TemplatePart.ParameterByIndex -> -parameterByIndex(part)
            is TemplatePart.TypeParameter -> -typeParameter(part)
            is TemplatePart.TypeParameterByIndex -> -typeParameterByIndex(part)
            is TemplatePart.Import -> out.addImport(part)
            is TemplatePart.LambdaParameterContents -> { }
        }
    }
}

class DeDupEmitter(val swift: SwiftTranslator) {
    val deduplicated = HashMap<Any, String>()
    val toEmit = ArrayList<Any>()
    fun deduplicate(item: Any) {
        if (when (item) {
                is String -> item.all { it.isLetterOrDigit() }
                is KtExpression -> with(swift) { item.isSimple() }
                else -> false
            }
        ) return
        val name = "temp${uniqueNumber.getAndIncrement()}"
        deduplicated[item] = name
    }

    fun deduplicateEmit(item: Any?) {
        if (item == null) return
        deduplicate(item)
        toEmit.add(item)
    }

    operator fun Any?.unaryMinus() {
        if (this == null) return
        toEmit.add(this)
    }

    operator fun Any?.unaryPlus() {
        if (this == null) return
        toEmit.add(this)
    }

    val dedupNecessary: Boolean
        get() = deduplicated.any {
            val key = it.key
            toEmit.count { it == key } > 1
        }

    fun finish(parentEmit: (Any) -> Unit) = with(swift){
        val used = deduplicated.filterKeys { key ->
            toEmit.count { it == key } > 1
        }
        for ((item, name) in used) {
//            println("Deduping ${(item as? KtExpression)?.text ?: item.toString()}, which is of type ${(item as? KtExpression)?.resolvedExpressionTypeInfo?.type}")
            if((item as? KtExpression)?.resolvedExpressionTypeInfo?.type?.requiresMutable() == true){
                parentEmit("var ")
            } else {
                parentEmit("let ")
            }
            parentEmit(name)
            parentEmit(" = ")
            parentEmit(item)
            parentEmit("\n")
        }
        for (item in toEmit) {
            used[item]?.let { name ->
                parentEmit(name)
            } ?: parentEmit(item)
        }
        for ((item, name) in used.filter { (it.key as? KtExpression)?.resolvedExpressionTypeInfo?.type?.requiresMutable() == true } ) {
            parentEmit(item)
            parentEmit(" = ")
            parentEmit(name)
            parentEmit("\n")
        }
    }
}