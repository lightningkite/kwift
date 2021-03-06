package com.lightningkite.khrysalis.web.layout

import com.lightningkite.khrysalis.generic.PartialTranslator
import com.lightningkite.khrysalis.ios.layout.Styles
import com.lightningkite.khrysalis.typescript.replacements.AttributeReplacement
import com.lightningkite.khrysalis.typescript.replacements.Replacements
import com.lightningkite.khrysalis.typescript.replacements.Template
import com.lightningkite.khrysalis.typescript.replacements.TemplatePart
import com.lightningkite.khrysalis.utils.*
import com.lightningkite.khrysalis.web.asCssColor
import java.io.File
import java.lang.Appendable

class HtmlTranslator {

    var strings = mapOf<String, String>()
    var styles: Styles = mapOf()
    var outFolder: File = File(".")
    val replacements = Replacements()

    inner class ElementTranslator : PartialTranslator<ResultNode, Unit, XmlNode, String>() {
        override fun getIdentifier(rule: XmlNode): String = rule.name
        override fun emitDefault(identifier: String, rule: XmlNode, out: ResultNode): Unit {
            out.classes.add("butterfly-" + rule.name.kabobCase())
        }

        override fun translate(identifier: String, rule: XmlNode, out: ResultNode, afterPriority: Int) {
            super.translate(identifier, rule, out, afterPriority)
            rule.parts.forEach { attribute.translate(it, out) }
        }
    }

    inner class AttributeTranslator : PartialTranslator<ResultNode, Unit, XmlNode.Attribute, String>() {
        override fun getIdentifier(rule: XmlNode.Attribute): String = rule.type
        override fun emitDefault(identifier: String, rule: XmlNode.Attribute, out: ResultNode) {
            replacements.attributes[rule.type.substringAfter(':')]?.firstOrNull {
                it.on == null || rule.parent.name == it.on
            }?.let {
                it.effects.forEach {
                    val on = when (it.applyTo) {
                        AttributeReplacement.ApplyTo.Main -> out
                        AttributeReplacement.ApplyTo.Primary -> out.primary
                        AttributeReplacement.ApplyTo.Text -> out.text
                        AttributeReplacement.ApplyTo.ContainerNode -> out.containerNode
                    }
                    on.style += it.css.mapValues { it.value.use(rule.value) }
                    on.attributes += it.attributes.mapValues { it.value.use(rule.value) }
                }
            }
        }
    }

    val element = ElementTranslator()
    val attribute = AttributeTranslator()

    init {
        layout()
        commonAttributes()
        display()
        input()
    }


    fun emitFile(root: XmlNode, out: Appendable) {
        ResultNode().apply { element.translate(root, this) }.also { it.doPostProcess() }.emitHtml(out)
    }

    fun Template.use(value: String): String {
        return parts.joinToString("") {
            when (it) {
                is TemplatePart.Text -> it.string
                TemplatePart.Receiver -> "this"
                TemplatePart.DispatchReceiver -> "this"
                TemplatePart.ExtensionReceiver -> "this"
                TemplatePart.Value -> when{
                    value.startsWith("@") -> {
                        val type = value.drop(1).substringAfter(':').substringBefore('/')
                        val path = value.substringAfter('/').kabobCase()
                        when (type) {
                            "mipmap", "drawable" -> "drawable-$path"
                            "color" -> value.asCssColor() ?: "#000"
                            "string" -> strings[path] ?: ""
                            else -> value
                        }
                    }
                    value.startsWith("#") -> {
                        value.asCssColor() ?: ""
                    }
                    else -> value
                }
                else -> ""
            }
        }
    }
}

