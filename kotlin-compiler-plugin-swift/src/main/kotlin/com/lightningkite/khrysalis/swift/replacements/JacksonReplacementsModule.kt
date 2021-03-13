package com.lightningkite.khrysalis.swift.replacements

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.lightningkite.khrysalis.replacements.Import
import com.lightningkite.khrysalis.replacements.JacksonReplacementsModule
import com.lightningkite.khrysalis.swift.replacements.xib.AttKind
import com.lightningkite.khrysalis.swift.replacements.xib.AttPath
import com.lightningkite.khrysalis.swift.replacements.xib.XibTypedValue

class SwiftJacksonReplacementsModule() : JacksonReplacementsModule() {
    init {
        addDeserializer(AttPath::class.java, object: StdDeserializer<AttPath>(AttPath::class.java) {
            override fun deserialize(p: JsonParser, ctxt: DeserializationContext): AttPath {
                when(p.currentToken) {
                    JsonToken.VALUE_STRING -> {
                        val text = p.text
                        return AttPath(text)
                    }
                    else -> throw IllegalArgumentException("REEEEEEEE got ${p.currentToken}")
                }
            }
        })
        addDeserializer(XibTypedValue::class.java, object: StdDeserializer<XibTypedValue>(XibTypedValue::class.java) {
            override fun deserialize(p: JsonParser, ctxt: DeserializationContext): XibTypedValue {
                when(p.currentToken) {
                    JsonToken.START_OBJECT -> {
                        val obj = p.readValueAsTree<ObjectNode>()
                        return XibTypedValue(
                            value = obj["value"].asText(),
                            type = AttKind.parse(obj["type"].asText())
                        )
                    }
                    else -> {
                        return XibTypedValue(p.valueAsString)
                    }
                }
            }
        })
        addDeserializer(AttKind::class.java, object: StdDeserializer<AttKind>(AttKind::class.java) {
            override fun deserialize(p: JsonParser, ctxt: DeserializationContext): AttKind {
                return AttKind.parse(p.valueAsString)
            }
        })
    }

    override fun parseImports(node: JsonNode): List<Import> {
        return (node as ArrayNode).map { SwiftImport(it.asText()) }
    }
}