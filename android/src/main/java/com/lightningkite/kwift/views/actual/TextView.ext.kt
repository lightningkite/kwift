package com.lightningkite.kwift.views.actual

import android.widget.TextView
import android.widget.ToggleButton

var TextView.textResource: Int
    get() = 0
    set(value) { setText(value) }
var TextView.textString: String
    get() = text.toString()
    set(value) { setText(value) }

fun TextView.setColor(color: ColorResource) {
    setTextColor(resources.getColor(color))
}


var ToggleButton.textResource: Int
    get() = 0
    set(value) {
        setText(value)
        textOn = resources.getString(value)
        textOff = resources.getString(value)
    }
var ToggleButton.textString: String
    get() = text.toString()
    set(value) {
        setText(value)
        textOn = value
        textOff = value
    }