package com.lightningkite.khrysalis.swift.replacements

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.lightningkite.khrysalis.util.*
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.js.descriptorUtils.getJetTypeFqName
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
import org.jetbrains.kotlin.synthetic.SyntheticJavaPropertyDescriptor
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.typeUtil.isTypeParameter
import org.jetbrains.kotlin.types.typeUtil.supertypes
import java.io.File
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class Replacements() {
    val functions: HashMap<String, TreeSet<FunctionReplacement>> = HashMap()
    val gets: HashMap<String, TreeSet<GetReplacement>> = HashMap()
    val sets: HashMap<String, TreeSet<SetReplacement>> = HashMap()
    val types: HashMap<String, TreeSet<TypeReplacement>> = HashMap()
    val typeRefs: HashMap<String, TreeSet<TypeRefReplacement>> = HashMap()
    val casts: HashMap<String, TreeSet<CastRule>> = HashMap()


    fun getCall(
        analysis: AnalysisExtensions,
        call: ResolvedCall<out CallableDescriptor>,
        descriptor: CallableDescriptor = call.candidateDescriptor,
        alreadyChecked: HashSet<CallableDescriptor> = HashSet()
    ): FunctionReplacement? {
        if (!alreadyChecked.add(descriptor)) return null
        val result = descriptor.fqNamesToCheck
            .flatMap {
                functions[it]?.asSequence() ?: sequenceOf()
            }
            .find {
                it.passes(
                    analysis = analysis,
                    call = call,
                    descriptor = descriptor
                )
            } ?: (descriptor as? CallableMemberDescriptor)?.allOverridden()
            ?.map {
                getCall(
                    analysis = analysis,
                    call = call,
                    descriptor = it,
                    alreadyChecked = alreadyChecked
                )
            }
            ?.firstOrNull()
        return result
    }

    fun getGet(propertyDescriptor: PropertyDescriptor, receiverType: KotlinType? = null): GetReplacement? =
        propertyDescriptor.fqNamesToCheck
            .flatMap {
                gets[it]?.asSequence() ?: sequenceOf()
            }
            .find {
                it.passes(propertyDescriptor, receiverType)
            } ?: propertyDescriptor.overriddenDescriptors.asSequence().map { getGet(it, receiverType) }.firstOrNull()
        ?: (propertyDescriptor as? SyntheticJavaPropertyDescriptor)?.getMethod?.let {
            val accessName = it.name.asString()
            val propName = propertyDescriptor.name.asString()
            val all = sequenceOf(it) + it.allOverridden()
            all.mapNotNull {
                gets[it.simpleFqName.replace(accessName, propName)]?.find {
                    it.passes(
                        propertyDescriptor,
                        receiverType
                    )
                }
                    ?: gets[it.simplerFqName.replace(accessName, propName)]?.find {
                        it.passes(
                            propertyDescriptor,
                            receiverType
                        )
                    }
            }.firstOrNull()
        }

    fun getGet(objectDescriptor: DeclarationDescriptor): GetReplacement? =
        gets[objectDescriptor.simpleFqName]?.firstOrNull()

    fun getSet(propertyDescriptor: PropertyDescriptor, receiverType: KotlinType? = null): SetReplacement? {
        return propertyDescriptor.fqNamesToCheck
            .flatMap {
                sets[it]?.asSequence() ?: sequenceOf()
            }
            .find {
                it.passes(propertyDescriptor, receiverType)
            } ?: propertyDescriptor.overriddenDescriptors.asSequence().map { getSet(it, receiverType) }.firstOrNull()
        ?: (propertyDescriptor as? SyntheticJavaPropertyDescriptor)?.setMethod?.let {
            val accessName = it.name.asString()
            val propName = propertyDescriptor.name.asString()
            val all = sequenceOf(it) + it.allOverridden()
            all.mapNotNull {
                sets[it.simpleFqName.replace(accessName, propName)]?.find {
                    it.passes(
                        propertyDescriptor,
                        receiverType
                    )
                }
                    ?: sets[it.simplerFqName.replace(accessName, propName)]?.find {
                        it.passes(
                            propertyDescriptor,
                            receiverType
                        )
                    }
            }.firstOrNull()
        }
    }

    fun getType(type: DeclarationDescriptor): TypeReplacement? =
        type.fqNamesToCheck
            .flatMap {
                types[it]?.asSequence() ?: sequenceOf()
            }
            .find {
                it.passes(type)
            }

    fun getType(type: KotlinType): TypeReplacement? =
        if (type.constructor.declarationDescriptor is TypeParameterDescriptor) null else types[type.fqNameWithoutTypeArgs]?.find { it.passes(type) }

    fun getTypeRef(type: KotlinType): TypeRefReplacement? =
        typeRefs[type.fqNameWithoutTypeArgs]?.find { it.passes(type) }

    fun getTypeRef(type: DeclarationDescriptor): TypeRefReplacement? =
        type.fqNamesToCheck
            .flatMap {
                typeRefs[it]?.asSequence() ?: sequenceOf()
            }
            .find {
                it.passes(type)
            }

    fun getImplicitCast(from: KotlinType, to: KotlinType): CastRule? {
        casts[from.fqNameWithoutTypeArgs + "->" + to.fqNameWithoutTypeArgs]
            ?.find { it.passes(from, to) }
            ?.let { return it }
        val detailedPossibilities = from.supertypes().filter { it.supertypes().contains(to) }
        for (d in detailedPossibilities) {
            casts[d.fqNameWithoutTypeArgs + "->" + to.fqNameWithoutTypeArgs]
                ?.find { it.passes(d, to) }
                ?.let { return it }
        }
        return null
    }

    fun getExplicitCast(from: KotlinType, to: KotlinType): CastRule? {
        casts[from.fqNameWithoutTypeArgs + "->" + to.fqNameWithoutTypeArgs]
            ?.find { it.passes(from, to) }
            ?.let { return it }
        val detailedPossibilities = to.supertypes().filter { it.supertypes().contains(from) }
        for (d in detailedPossibilities) {
            casts[d.fqNameWithoutTypeArgs + "->" + to.fqNameWithoutTypeArgs]
                ?.find { it.passes(from, d) }
                ?.let { return it }
        }
        return null
    }

    fun requiresMutable(type: KotlinType): Boolean = (sequenceOf(type) + type.supertypes().asSequence())
        .any { t -> types[t.fqNameWithoutTypeArgs]?.find { it.passes(t) }?.requiresMutable == true }

    companion object {
        val mapper: ObjectMapper = ObjectMapper(YAMLFactory())
            .registerModule(JacksonReplacementsModule())
            .registerModule(KotlinModule())
//            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    operator fun plusAssign(item: ReplacementRule) {
        when (item) {
            is FunctionReplacement -> functions.getOrPut(item.id) { TreeSet() }.merge(item)
            is GetReplacement -> gets.getOrPut(item.id) { TreeSet() }.merge(item)
            is SetReplacement -> sets.getOrPut(item.id) { TreeSet() }.merge(item)
            is TypeReplacement -> types.getOrPut(item.id) { TreeSet() }.merge(item)
            is TypeRefReplacement -> typeRefs.getOrPut(item.id) { TreeSet() }.merge(item)
        }
    }

    operator fun plusAssign(yaml: String) {
        mapper.readValue<List<ReplacementRule>>(yaml).forEach {
            this += it
        }
    }

    operator fun plusAssign(yaml: File) {
        mapper.readValue<List<ReplacementRule>>(yaml).forEach {
            this += it
        }
    }
}

