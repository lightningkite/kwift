package com.lightningkite.kwift.drawables

import com.lightningkite.kwift.utils.*
import java.io.File
import java.lang.Appendable
import java.lang.Exception


fun convertDrawableXmls(
    resourcesFolder: File,
    swiftFolder: File
) {
    resourcesFolder.resolve("drawable").walkTopDown()
        .filter { it.extension == "xml" }
        .map { it to XmlNode.read(it, mapOf()) }
        .forEach { (file, it) ->
            val name = file.nameWithoutExtension.camelCase()
            val outputFile = swiftFolder.resolve("drawable").resolve(name + "Drawable.swift")
            println("$file -> $outputFile")
            try {
                outputFile.bufferedWriter().use { writer ->
                    writer.appendln("//Automatically created by Kwift")
                    writer.appendln("import UIKit")
                    writer.appendln("")
                    writer.appendln("extension ResourcesDrawables {")
                    writer.appendln("")
                    convertDrawableXml(name, it, writer)
                    writer.appendln("")
                    writer.appendln("}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
}

fun convertDrawableXml(name: String, node: XmlNode, out: Appendable) {
    when (node.name.toLowerCase()) {
        "selector" -> convertSelectorDrawable(
            name,
            node,
            out
        )
        "shape" -> convertShapeDrawable(
            name,
            node,
            out
        )
        "layer-list" -> convertLayerListDrawable(
            name,
            node,
            out
        )
        "vector" -> convertVectorDrawable(
            name,
            node,
            out
        )
    }
}
