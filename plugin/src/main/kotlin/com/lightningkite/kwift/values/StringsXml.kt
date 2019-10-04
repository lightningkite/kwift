package com.lightningkite.kwift.values

import com.lightningkite.kwift.utils.XmlNode
import com.lightningkite.kwift.utils.camelCase
import java.io.File


fun File.readXMLStrings(): Map<String, String> {
    return XmlNode.read(this, mapOf())
        .children
        .asSequence()
        .filter { it.name == "string" }
        .associate {
            (it.attributes["name"] ?: "noname") to it.element.textContent
        }
}

fun Map<String, String>.writeXMLStringsTranslation(base: Map<String, String>, locale: String): String {
    return buildString {
        appendln("/*")
        appendln("Translation to locale $locale")
        appendln("Automatically written by Kwift")
        appendln("*/")
        appendln("")
        for((key, value) in this@writeXMLStringsTranslation.entries){
            val baseString = (base[key] ?: error("No matching key in base language"))
                .replace("\\'", "'")
                .replace("\\$", "$")
                .replace(Regex("\n *"), " ")
            val fixedString = value
                .replace("\\'", "'")
                .replace("\\$", "$")
                .replace(Regex("\n *"), " ")
            appendln("\"$baseString\" = \"$fixedString\";")
        }
    }
}

fun Map<String, String>.writeXMLStrings(): String {
    return buildString {
        appendln("//")
        appendln("// ResourcesStrings.swift")
        appendln("// Created by Kwift")
        appendln("//")
        appendln("")
        appendln("import Foundation")
        appendln("import UIKit")
        appendln("import Kwift")
        appendln("")
        appendln("")
        appendln("public enum ResourcesStrings {")
        for((key, value) in this@writeXMLStrings.entries){
            val fixedString = value
                .replace("\\'", "'")
                .replace("\\$", "$")
                .replace(Regex("\n *"), " ")
            appendln("    static let ${key.camelCase()} = NSLocalizedString(\"$fixedString\", comment: \"$key\")")
        }
        appendln("}")
    }
}
