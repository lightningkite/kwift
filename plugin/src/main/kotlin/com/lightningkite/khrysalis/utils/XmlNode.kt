package com.lightningkite.khrysalis.utils

import com.lightningkite.khrysalis.layout.Styles
import org.w3c.dom.Node
import java.io.File
import java.lang.Exception
import javax.xml.parsers.DocumentBuilderFactory

class XmlNode(
    val element: Node,
    val styles: Styles,
    val directory: File,
    val additionalAttributes: Map<String, String> = mapOf()
) {
    val name get() = element.nodeName
    val attributes: Map<String, String> by lazy {
        if (!element.hasAttributes()) return@lazy mapOf<String, String>()
        val map = HashMap<String, String>()
        element.attributes.let { att ->
            (0 until att.length).associateTo(map) { att.item(it).let { it.nodeName to it.nodeValue } }
        }
        val style = map["style"]?.removePrefix("@style/")?.let { styleName ->
            styles[styleName]
        }
        if (style != null) {
            for ((k, v) in style) {
                if (!map.containsKey(k)) {
                    map[k] = v
                }
            }
        }
        element.attributes.let { att ->
            (0 until att.length).associateTo(map) { att.item(it).let { it.nodeName to it.nodeValue } }
        }
        additionalAttributes + map
    }
    val children by lazy {
        element.childNodes.let { att ->
            (0 until att.length).asSequence()
                .map { att.item(it) }
                .filter { it.nodeType == Node.ELEMENT_NODE }
                .map {
                    if(it.nodeName == "include"){
                        try {
                            val filename = it.attributes
                                ?.getNamedItem("layout")
                                ?.nodeValue
                                ?.removePrefix("@layout/")
                                ?.plus(".xml")
                                ?: return@map XmlNode(it, styles, directory)
                            val node =
                                read(File(directory, filename), styles)
                            XmlNode(it, styles, directory, node.attributes)
                        } catch(e:Exception){
                            e.printStackTrace()
                            XmlNode(it, styles, directory)
                        }
                    } else {
                        XmlNode(it, styles, directory)
                    }
                }
                .toList()
        }
    }


    companion object {
        fun read(file: File, styles: Styles): XmlNode {
            val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file)
            return XmlNode(document.documentElement, styles, file.parentFile)
        }
    }
}


fun XmlNode.attributeAsString(key: String): String? {
    val raw = attributes[key] ?: return null
    return when {
        raw.startsWith("@string/") -> "ResourcesStrings.${raw.removePrefix("@string/").camelCase()}"
        else -> "\"$raw\""
    }
}
fun XmlNode.attributeAsStringKotlin(key: String): String? {
    val raw = attributes[key] ?: return null
    return when {
        raw.startsWith("@string/") -> "ResourcesStrings.${raw.removePrefix("@string/").camelCase()}"
        else -> "\"${raw.replace("$", "\\$")}\""
    }
}
fun XmlNode.attributeAsGravityKotlin(key: String): Int? {
    val raw = attributes[key]?.split('|') ?: return null
    var gravity = 0
    if(raw.contains("center"))
        gravity = gravity or 0x00000011
    if(raw.contains("center_horizontal"))
        gravity = gravity or 0x00000001
    if(raw.contains("left"))
        gravity = gravity or 0x00000003
    if(raw.contains("right"))
        gravity = gravity or 0x00000005
    if(raw.contains("center_vertical"))
        gravity = gravity or 0x00000010
    if(raw.contains("top"))
        gravity = gravity or 0x00000030
    if(raw.contains("bottom"))
        gravity = gravity or 0x00000050
    return gravity
}
object SafePaddingFlags {
    const val NONE: Int = 0
    const val TOP: Int = 1
    const val RIGHT: Int = 2
    const val BOTTOM: Int = 4
    const val LEFT: Int = 8
    const val ALL: Int = 15
}
fun XmlNode.attributeAsEdgeFlagsKotlin(key: String): Int? {
    val raw = attributes[key]?.split('|') ?: return null
    var gravity = 0
    if(raw.contains("center"))
        gravity = gravity or SafePaddingFlags.ALL
    if(raw.contains("all"))
        gravity = gravity or SafePaddingFlags.ALL
    if(raw.contains("center_horizontal"))
        gravity = gravity or SafePaddingFlags.LEFT or SafePaddingFlags.RIGHT
    if(raw.contains("horizontal"))
        gravity = gravity or SafePaddingFlags.LEFT or SafePaddingFlags.RIGHT
    if(raw.contains("left"))
        gravity = gravity or SafePaddingFlags.LEFT
    if(raw.contains("right"))
        gravity = gravity or SafePaddingFlags.RIGHT
    if(raw.contains("center_vertical"))
        gravity = gravity or SafePaddingFlags.TOP or SafePaddingFlags.BOTTOM
    if(raw.contains("vertical"))
        gravity = gravity or SafePaddingFlags.TOP or SafePaddingFlags.BOTTOM
    if(raw.contains("top"))
        gravity = gravity or SafePaddingFlags.TOP
    if(raw.contains("bottom"))
        gravity = gravity or SafePaddingFlags.BOTTOM
    return gravity
}

fun XmlNode.attributeAsInt(key: String): String? {
    val raw = attributes[key] ?: return null
    return raw.filter { it.isDigit() || it == '-' }.toIntOrNull()?.toString()
}

fun XmlNode.attributeAsDimension(key: String): String? {
    val raw = attributes[key] ?: return null
    return when {
        raw.startsWith("@dimen/") -> "ResourcesDimensions.${raw.removePrefix("@dimen/").camelCase()}"
        else -> raw.filter { it.isDigit() || it == '.' || it == '-' }.toIntOrNull()?.toString()
    }
}
fun XmlNode.attributeAsDrawable(key: String): String? {
    val raw = attributes[key] ?: return null
    return when {
        raw.startsWith("@drawable/") -> "ResourcesDrawables.${raw.removePrefix("@drawable/").camelCase()}"
        else -> null
    }
}
fun XmlNode.attributeAsLayer(key: String, forView: String = "nil"): String? = attributeAsDrawable(key)?.plus("($forView)")
fun XmlNode.attributeAsBoolean(key: String): Boolean? {
    val raw = attributes[key] ?: return null
    return when(raw.toLowerCase()) {
        "true" -> true
        "false" -> false
        else -> null
    }
}
fun XmlNode.attributeAsDouble(key: String): Double? {
    val raw = attributes[key] ?: return null
    return when {
        else -> raw.filter { it.isDigit() || it == '.' || it == '-' }.toDoubleOrNull()
    }
}

fun XmlNode.attributeAsImage(key: String): String? {
    val raw = attributes[key] ?: return null
    return when {
        raw.startsWith("@drawable/") -> "UIImage(named: \"${raw.removePrefix("@drawable/")}\") ?? ${attributeAsLayer(key, "view")}.toImage()"
        raw.startsWith("@mipmap/") -> "UIImage(named: \"${raw.removePrefix("@mipmap/")}\")"
        else -> null
    }
}


fun XmlNode.attributeAsColor(key: String): String? {
    val raw = attributes[key] ?: return null
    return when {
        raw.startsWith("@color/") -> {
            val colorName = raw.removePrefix("@color/")
            "ResourcesColors.${colorName.camelCase()}"
        }
        raw.startsWith("@android:color/") -> {
            val colorName = raw.removePrefix("@android:color/")
            "ResourcesColors.${colorName.camelCase()}"
        }
        raw.startsWith("#") -> {
            raw.hashColorToUIColor()
        }
        else -> "UIColor.black"
    }
}
