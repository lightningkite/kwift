package com.lightningkite.khrysalis.web.layout

import com.lightningkite.khrysalis.utils.AlignDirection
import com.lightningkite.khrysalis.utils.horizontalGravity
import com.lightningkite.khrysalis.utils.verticalGravity
import com.lightningkite.khrysalis.web.asCssColor
import java.util.concurrent.atomic.AtomicInteger

val idNumber = AtomicInteger(0)

internal fun HtmlTranslator.input() {
    element.handle("EditText"){
        val hasDrawable = rule.allAttributes["android:drawableLeft"] != null
                || rule.allAttributes["android:drawableTop"] != null
                || rule.allAttributes["android:drawableRight"] != null
                || rule.allAttributes["android:drawableBottom"] != null
        if(hasDrawable){
            val id = "id${idNumber.getAndIncrement()}"
            out.name = "label"
            out.attributes["for"] = id
            out.style["display"] = "flex"
            out.style["flex-direction"] = "row"
            val main = ResultNode("input").apply {
                attributes["type"] = "text"
                style["font-size"] = "12pt"
                style["flex-grow"] = "1"
                style["flex-shrink"] = "1"
                attributes["id"] = id
            }
            out.primary = main
            out.text = main
            out.contentNodes.add(main)
        } else {
            out.name = "input"
            out.attributes["type"] = "text"
            out.style["font-size"] = "12pt"
        }
    }

    element.handle("CheckBox"){
        val id = "id${idNumber.getAndIncrement()}"
        out.name = "label"
        out.attributes["for"] = id
        out.style["display"] = "flex"
        out.style["flex-direction"] = "row"
        out.style["align-items"] = rule.allAttributes["android:gravity"]?.let { verticalGravity(it)?.alignDirection }?.let {
            when(it){
                AlignDirection.START -> "flex-start"
                AlignDirection.CENTER -> "center"
                AlignDirection.END -> "flex-end"
            }
        } ?: "center"
        rule.allAttributes["android:gravity"]?.let { horizontalGravity(it)?.alignDirection }?.let {
            when(it){
                AlignDirection.START -> "flex-start"
                AlignDirection.CENTER -> "center"
                AlignDirection.END -> "flex-end"
            }
        }?.let { out.style["justify-content"] = it }
        val input = ResultNode("input").apply {
            if(rule.allAttributes["android:button"] == "@null") {
                style["display"] = "none"
            }
            attributes["id"] = id
            attributes["type"] = "checkbox"
            attributes["value"] = id
            if(rule.allAttributes["android:checked"] == "true") {
                attributes["checked"] = "true"
            }
        }
        out.primary = input
        out.contentNodes.add(input)
        val label = ResultNode("div").apply {
            style["flex-grow"] = "1"
        }
        out.contentNodes.add(label)
        out.text = label
        out.containerNode = label
    }

    element.handle("RadioButton"){
        val id = "id${idNumber.getAndIncrement()}"
        out.name = "label"
        out.attributes["for"] = id
        out.style["display"] = "flex"
        out.style["flex-direction"] = "row"
        out.style["align-items"] = rule.allAttributes["android:gravity"]?.let { verticalGravity(it)?.alignDirection }?.let {
            when(it){
                AlignDirection.START -> "flex-start"
                AlignDirection.CENTER -> "center"
                AlignDirection.END -> "flex-end"
            }
        } ?: "center"
        rule.allAttributes["android:gravity"]?.let { horizontalGravity(it)?.alignDirection }?.let {
            when(it){
                AlignDirection.START -> "flex-start"
                AlignDirection.CENTER -> "center"
                AlignDirection.END -> "flex-end"
            }
        }?.let { out.style["justify-content"] = it }
        val input = ResultNode("input").apply {
            if(rule.allAttributes["android:button"] == "@null") {
                style["display"] = "none"
            }
            attributes["id"] = id
            attributes["name"] = run {
                var current = out.parent
                while(current != null){
                    current.other["RadioGroupId"]?.let { it as? String }?.let {
                        return@run it
                    }
                    current = current.parent
                }
                return@run id
            }
            attributes["type"] = "radio"
            attributes["value"] = id
            if(rule.allAttributes["android:checked"] == "true") {
                attributes["checked"] = "true"
            }
        }
        out.primary = input
        out.contentNodes.add(input)
        val label = ResultNode("div").apply {
            style["flex-grow"] = "1"
        }
        out.contentNodes.add(label)
        out.text = label
        out.containerNode = label
    }

    element.handle("Switch"){
        val id = "id${idNumber.getAndIncrement()}"
        out.name = "label"
        out.attributes["for"] = id
        out.classes.add("khrysalis-switch")
        out.style["align-items"] = rule.allAttributes["android:gravity"]?.let { verticalGravity(it)?.alignDirection }?.let {
            when(it){
                AlignDirection.START -> "flex-start"
                AlignDirection.CENTER -> "center"
                AlignDirection.END -> "flex-end"
            }
        } ?: "center"
        rule.allAttributes["android:gravity"]?.let { horizontalGravity(it)?.alignDirection }?.let {
            when(it){
                AlignDirection.START -> "flex-start"
                AlignDirection.CENTER -> "center"
                AlignDirection.END -> "flex-end"
            }
        }?.let { out.style["justify-content"] = it }

        val label = ResultNode("div")
        out.text = label
        out.containerNode = label
        out.contentNodes.add(label)

        out.contentNodes.add(ResultNode("input").apply {
            attributes["type"] = "checkbox"
            attributes["id"] = id
            attributes["value"] = id
            if(rule.allAttributes["android:checked"] == "true") {
                attributes["checked"] = "true"
            }
        }.also {
            out.primary = it
        })

        out.contentNodes.add(ResultNode("div").apply {
            classes.add("khrysalis-switch-back")
            contentNodes.add(ResultNode("span").apply {
                rule.allAttributes["android:thumbTint"]?.asCssColor()?.let {
                    style["background-color"] = it
                }
                classes.add("khrysalis-switch-front")
            })
        })
    }
}