// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: views/ViewWithText.ext.actual.kt
// Package: com.lightningkite.khrysalis.views
// FQImport: android.widget.TextView.setTextColor TS setTextColor
// FQImport: textOn TS setAndroidWidgetToggleButtonTextOn
// FQImport: textOff TS setAndroidWidgetToggleButtonTextOff
// FQImport: android.widget.ToggleButton TS ToggleButton
// FQImport: android.content.res.Resources.getString TS getString
// FQImport: android.widget.ToggleButton.setText TS setText
// FQImport: android.widget.TextView TS TextView
// FQImport: com.lightningkite.khrysalis.views.<set-textResource>.value TS value
// FQImport: resources TS getAndroidWidgetTextViewResources
// FQImport: android.content.res.Resources.getColor TS getColor
// FQImport: text TS getAndroidWidgetToggleButtonText
// FQImport: android.widget.TextView.setText TS setText
// FQImport: resources TS getAndroidWidgetToggleButtonResources
// FQImport: com.lightningkite.khrysalis.views.ColorResource TS ColorResource
// FQImport: kotlin.CharSequence.toString TS toString
// FQImport: text TS getAndroidWidgetTextViewText
// FQImport: com.lightningkite.khrysalis.views.<set-textString>.value TS value
// FQImport: com.lightningkite.khrysalis.views.setColor.color TS color
import { ColorResource } from './ResourceTypes.actual'

//! Declares com.lightningkite.khrysalis.views.textResource
export function getAndroidWidgetTextViewTextResource(this_: TextView): number { return 0; }
export function setAndroidWidgetTextViewTextResource(this_: TextView, value: number) {
    this_.setText(value);
}


//! Declares com.lightningkite.khrysalis.views.textString
export function getAndroidWidgetTextViewTextString(this_: TextView): string { return getAndroidWidgetTextViewText(this_).toString(); }
export function setAndroidWidgetTextViewTextString(this_: TextView, value: string) {
    this_.setText(value);
}


//! Declares com.lightningkite.khrysalis.views.setColor
export function androidWidgetTextViewSetColor(this_: TextView, color: ColorResource){
    this_.setTextColor(getAndroidWidgetTextViewResources(this_).getColor(color));
}

//! Declares com.lightningkite.khrysalis.views.textResource
export function getAndroidWidgetToggleButtonTextResource(this_: ToggleButton): number { return 0; }
export function setAndroidWidgetToggleButtonTextResource(this_: ToggleButton, value: number) {
    this_.setText(value);
    setAndroidWidgetToggleButtonTextOn(this_, getAndroidWidgetToggleButtonResources(this_).getString(value));
    setAndroidWidgetToggleButtonTextOff(this_, getAndroidWidgetToggleButtonResources(this_).getString(value));
}

//! Declares com.lightningkite.khrysalis.views.textString
export function getAndroidWidgetToggleButtonTextString(this_: ToggleButton): string { return getAndroidWidgetToggleButtonText(this_).toString(); }
export function setAndroidWidgetToggleButtonTextString(this_: ToggleButton, value: string) {
    this_.setText(value);
    setAndroidWidgetToggleButtonTextOn(this_, value);
    setAndroidWidgetToggleButtonTextOff(this_, value);
}


