package com.lightningkite.khrysalis.ios.layout

import com.lightningkite.khrysalis.swift.safeSwiftIdentifier
import com.lightningkite.khrysalis.utils.XmlNode
import com.lightningkite.khrysalis.utils.camelCase
import java.io.File
import java.lang.StringBuilder

typealias Styles = Map<String, Map<String, String>>

fun File.translateLayoutXml(styles: Styles, converter: LayoutConverter = LayoutConverter.normal): String {

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

        appendln("    public func setup(dependency: ViewDependency) -> UIView {")
        append("        let view = ")
        conversion.construct(root)
        appendln()
        conversion.writeSetup(root)
        appendln("        xmlRoot = view")
        appendln("        return view")
        appendln("    }")
        appendln("    ")
        conversion.sublayouts.entries.forEach {
            appendln("public let ${it.key.safeSwiftIdentifier()}: ${it.value} = ${it.value}()")
        }
        conversion.delegateBindings.entries.forEach {
            appendln("public unowned var ${(it.key + "Delegate").safeSwiftIdentifier()}: ${it.value}!")
        }
        conversion.bindings.entries.forEach {
            appendln("public unowned var ${it.key.safeSwiftIdentifier()}: ${it.value}!")
        }
        appendln("    ")
        appendln("}")
    }

    return appendable.toString()
}
