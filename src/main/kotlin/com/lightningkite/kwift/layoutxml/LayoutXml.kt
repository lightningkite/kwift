package com.lightningkite.kwift.layoutxml

import com.lightningkite.kwift.swift.retabSwift
import com.lightningkite.kwift.utils.camelCase
import java.io.File
import java.lang.StringBuilder

typealias Styles = Map<String, Map<String, String>>

fun File.translateLayoutXml(styles: Styles): String {

    for((styleKey, styleValue) in styles){
        println("STYLE ${styleKey}")
        for((k, v) in styleValue){
            println("    $k = $v")
        }
    }

    val node = XmlNode.read(this, styles)
    val name = this.nameWithoutExtension.camelCase().capitalize()
    val appendable = StringBuilder()
    ViewType.bindings.clear()
    ViewType.write(appendable, node)
    val vars = ViewType.bindings.entries.joinToString("\n") {
        "weak var ${it.key}: ${it.value}!"
    }
    return """
        //
        // ${name}Xml.swift
        // Created by Kwift XML
        //

        import UIKit
        import FlexLayout
        import PinLayout

        class ${name}Xml: XmlView {

            $vars

            init() {
                super.init(frame: .zero)
                let root = $appendable
                self.root = root
                addSubview(root)
            }

            required init?(coder aDecoder: NSCoder) {
                super.init(coder: aDecoder)
            }
        }
    """.trimIndent().retabSwift()
}