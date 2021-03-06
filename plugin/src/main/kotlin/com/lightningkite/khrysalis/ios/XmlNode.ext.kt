package com.lightningkite.khrysalis.ios

import com.lightningkite.khrysalis.swift.safeSwiftIdentifier
import com.lightningkite.khrysalis.utils.XmlNode
import com.lightningkite.khrysalis.utils.camelCase
import com.lightningkite.khrysalis.utils.hashColorToUIColor


fun XmlNode.attributeAsSwiftString(key: String): String? {
    val raw = allAttributes[key] ?: return null
    return when {
        raw.startsWith("@string/") -> "R.string.${raw.removePrefix("@string/").safeSwiftIdentifier()}"
        else -> "\"$raw\""
    }
}

fun XmlNode.attributeAsSwiftDimension(key: String): String? {
    val raw = allAttributes[key] ?: return null
    return when {
        raw.startsWith("@dimen/") -> "R.dimen.${raw.removePrefix("@dimen/").safeSwiftIdentifier()}"
        else -> raw.filter { it.isDigit() || it == '.' || it == '-' }.toDoubleOrNull()?.toString()
    }
}
fun XmlNode.attributeAsSwiftDrawable(key: String): String? {
    val raw = allAttributes[key] ?: return null
    return when {
        raw.startsWith("@drawable/") -> "R.drawable.${raw.removePrefix("@drawable/").safeSwiftIdentifier()}"
        else -> null
    }
}
fun XmlNode.attributeAsSwiftLayer(key: String, forView: String = "nil"): String? = attributeAsSwiftDrawable(key)?.plus(".makeLayer($forView)")

fun XmlNode.attributeAsSwiftImage(key: String): String? {
    val raw = allAttributes[key] ?: return null
    return when {
        raw.startsWith("@drawable/") -> "UIImage(named: \"${raw.removePrefix("@drawable/")}\") ?? ${attributeAsSwiftLayer(key, "view")}.toImage()"
        raw.startsWith("@mipmap/") -> "UIImage(named: \"${raw.removePrefix("@mipmap/")}\")"
        else -> null
    }
}


fun XmlNode.attributeAsSwiftColor(key: String): String? {
    val raw = allAttributes[key] ?: return null
    return when {
        raw.startsWith("@color/") -> {
            val colorName = raw.removePrefix("@color/")
            "R.color.${colorName.safeSwiftIdentifier()}"
        }
        raw.startsWith("@android:color/") -> {
            val colorName = raw.removePrefix("@android:color/")
            "R.color.${colorName.safeSwiftIdentifier()}"
        }
        raw.startsWith("#") -> {
            raw.hashColorToUIColor()
        }
        else -> "UIColor.black"
    }
}
