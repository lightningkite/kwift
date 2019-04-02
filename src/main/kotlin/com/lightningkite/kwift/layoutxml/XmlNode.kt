package com.lightningkite.kwift.layoutxml

import com.lightningkite.kwift.utils.camelCase
import org.w3c.dom.Node
import java.io.File
import java.lang.Exception
import javax.xml.parsers.DocumentBuilderFactory

class XmlNode(val element: Node, val styles: Styles, val directory: File) {
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
        map
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
                            read(File(directory, filename), styles)
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

fun XmlNode.attributeAsInt(key: String): String? {
    val raw = attributes[key] ?: return null
    return raw.filter { it.isDigit() }.toIntOrNull()?.toString()
}

fun XmlNode.attributeAsDimension(key: String): String? {
    val raw = attributes[key] ?: return null
    return when {
        raw.startsWith("@dimen/") -> "ResourcesDimensions.${raw.removePrefix("@dimen/").camelCase()}"
        else -> raw.filter { it.isDigit() }.toIntOrNull()?.toString()
    }
}

fun XmlNode.attributeAsImage(key: String): String? {
    val raw = attributes[key] ?: return null
    return when {
        raw.startsWith("@drawable/") -> "UIImage(named: \"${raw.removePrefix("@drawable/")}\")"
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
