package com.lightningkite.khrysalis.ios.layout

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.lightningkite.khrysalis.android.layout.AndroidLayoutFile
import com.lightningkite.khrysalis.swift.safeSwiftIdentifier
import com.lightningkite.khrysalis.typescript.safeJsIdentifier
import com.lightningkite.khrysalis.utils.XmlNode
import com.lightningkite.khrysalis.utils.camelCase
import java.io.File
import java.lang.StringBuilder

typealias Styles = Map<String, Map<String, String>>

fun File.translateLayoutXml(android: AndroidLayoutFile, styles: Styles, converter: LayoutConverter = LayoutConverter.normal): String {

    val appendable = StringBuilder()
    val conversion = OngoingLayoutConversion(
        appendable = appendable,
        layoutsDirectory = this.parentFile,
        styles = styles,
        converter = converter
    )
    val root = XmlNode.read(this, styles, this.parentFile.resolve("../layout"))

    val name = this.nameWithoutExtension.camelCase().capitalize()

    with(appendable) {
        appendln("//")
        appendln("// ${name}Xml.swift")
        appendln("// Created by Khrysalis XML")
        appendln("//")
        appendln("")
        for(import in converter.imports) {
            appendln("import $import")
        }
        appendln("")
        appendln("public class ${name}Xml {")
        appendln("")
        appendln("    public unowned var xmlRoot: UIView!")

        appendln("    public func setup(dependency: ViewControllerAccess) -> UIView {")
        append("        let view = ")
        conversion.construct(root)
        appendln()
        conversion.writeSetup(root)
        appendln("        xmlRoot = view")
        appendln("        return view")
        appendln("    }")
        appendln("    ")
        android.bindings.values.forEach {
            val type = conversion.bindings[it.name] ?: converter.viewTypes[it.type]?.iosName
            if(it.optional){
                appendln("public var ${it.name.safeSwiftIdentifier()}: ${type}? = nil")
            } else {
                appendln("public var ${"_".plus(it.name).safeSwiftIdentifier()}: ${type}!")
                appendln("public var ${it.name.safeSwiftIdentifier()}: ${type} { get { return ${"_".plus(it.name).safeSwiftIdentifier()} } set(value){ ${"_".plus(it.name).safeSwiftIdentifier()} = value } }")
            }
        }
        android.delegateBindings.values.forEach {
            val type = conversion.delegateBindings[it.name]
            if(it.optional){
                appendln("public var ${(it.name + "Delegate").safeSwiftIdentifier()}: ${type}? = nil")
            } else {
                appendln("public var ${"_".plus(it.name).plus("Delegate").safeSwiftIdentifier()}: ${type}!")
                appendln("public var ${(it.name + "Delegate").safeSwiftIdentifier()}: ${type} { get { return ${"_".plus(it.name).plus("Delegate").safeSwiftIdentifier()} } set(value) { ${"_".plus(it.name).plus("Delegate").safeSwiftIdentifier()} = value } }")
            }
        }
        android.sublayouts.values.forEach {
            val type = conversion.sublayouts[it.name] ?: it.layoutXmlClass
            if(it.optional){
                if(conversion.sublayouts.containsKey(it.name)){
                    appendln("public let ${it.name.safeSwiftIdentifier()}: ${type}? = ${type}()")
                } else {
                    appendln("public let ${it.name.safeSwiftIdentifier()}: ${type}? = nil")
                }
            } else {
                appendln("public let ${it.name.safeSwiftIdentifier()}: ${type} = ${type}()")
            }
        }
        appendln("    ")
        appendln("}")
    }

    return appendable.toString()
}
