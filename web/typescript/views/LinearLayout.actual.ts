// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: views/LinearLayout.actual.kt
// Package: com.lightningkite.khrysalis.views
// FQImport: android.widget.LinearLayout.HORIZONTAL TS HORIZONTAL
// FQImport: android.widget.LinearLayout.LayoutParams.gravity TS gravity
// FQImport: android.view.Gravity.TOP TS TOP
// FQImport: android.widget.LinearLayout.orientation TS getAndroidWidgetLinearLayoutOrientation
// FQImport: com.lightningkite.khrysalis.views.geometry.Align TS Align
// FQImport: com.lightningkite.khrysalis.views.geometry.Align.fill TS fill
// FQImport: com.lightningkite.khrysalis.views.params.marginStart TS marginStart
// FQImport: com.lightningkite.khrysalis.views.params.weight TS weight
// FQImport: com.lightningkite.khrysalis.views.params.sizeY TS sizeY
// FQImport: android.view.ViewGroup.LayoutParams.MATCH_PARENT TS MATCH_PARENT
// FQImport: com.lightningkite.khrysalis.views.params.marginTop TS marginTop
// FQImport: com.lightningkite.khrysalis.views.params.marginBottom TS marginBottom
// FQImport: com.lightningkite.khrysalis.views.geometry.AlignPair.horizontal TS horizontal
// FQImport: com.lightningkite.khrysalis.views.geometry.Align.start TS start
// FQImport: android.widget.LinearLayout.LayoutParams.setMargins TS setMargins
// FQImport: com.lightningkite.khrysalis.views.params.d TS d
// FQImport: com.lightningkite.khrysalis.views.geometry.AlignPair TS AlignPair
// FQImport: android.view.Gravity TS Gravity
// FQImport: android.widget.LinearLayout.resources TS getAndroidWidgetLinearLayoutResources
// FQImport: android.view.Gravity.CENTER TS CENTER
// FQImport: com.lightningkite.khrysalis.views.geometry.Align.center TS center
// FQImport: com.lightningkite.khrysalis.views.params.gravity TS gravity
// FQImport: android.view.ViewGroup.LayoutParams.WRAP_CONTENT TS WRAP_CONTENT
// FQImport: android.view.Gravity.START TS START
// FQImport: com.lightningkite.khrysalis.views.geometry.AlignPair.vertical TS vertical
// FQImport: android.view.Gravity.END TS END
// FQImport: com.lightningkite.khrysalis.views.params.marginEnd TS marginEnd
// FQImport: android.widget.LinearLayout TS LinearLayout
// FQImport: android.view.Gravity.BOTTOM TS BOTTOM
// FQImport: com.lightningkite.khrysalis.views.geometry.Align.end TS end
// FQImport: android.widget.LinearLayout.LayoutParams TS LayoutParams
// FQImport: android.content.res.Resources.displayMetrics TS getAndroidContentResResourcesDisplayMetrics
// FQImport: android.util.DisplayMetrics.density TS density
// FQImport: com.lightningkite.khrysalis.views.params.align TS align
// FQImport: com.lightningkite.khrysalis.views.geometry.AlignPair.Companion.center TS center
// FQImport: com.lightningkite.khrysalis.views.params.sizeX TS sizeX
import { Align, AlignPair } from './geometry/Align.shared'
import { also } from 'Kotlin'

//! Declares com.lightningkite.khrysalis.views.params>android.widget.LinearLayout
export function androidWidgetLinearLayoutParams(this_: HTML_Element, sizeX: number = 0, sizeY: number = 0, marginStart: number = 0, marginEnd: number = 0, marginTop: number = 0, marginBottom: number = 0, gravity: AlignPair = AlignPair.Companion.INSTANCE.center, weight: number = 0): HTML_Element.LayoutParams{
    const d = getAndroidContentResResourcesDisplayMetrics(getAndroidWidgetLinearLayoutResources(this_)).density;
    
    const align = getAndroidWidgetLinearLayoutOrientation(this_) === LinearLayout.HORIZONTAL ? gravity.vertical : gravity.horizontal;
    
    
    return also((() => {if (getAndroidWidgetLinearLayoutOrientation(this_) === LinearLayout.HORIZONTAL) {
                    return LinearLayout.LayoutParams.constructorkotlinInt, kotlinInt, kotlinFloat(!(weight === 0) ? 0 : sizeX === 0 ? WRAP_CONTENT : Math.floor((sizeX * d)), align.equals(Align.fill) ? MATCH_PARENT : sizeY === 0 ? WRAP_CONTENT : Math.floor((sizeY * d)), weight);
                } else {
                    return LinearLayout.LayoutParams.constructorkotlinInt, kotlinInt, kotlinFloat(align.equals(Align.fill) ? MATCH_PARENT : sizeX === 0 ? WRAP_CONTENT : Math.floor((sizeX * d)), !(weight === 0) ? 0 : sizeY === 0 ? WRAP_CONTENT : Math.floor((sizeY * d)), weight);
        }})(), (this_1) => {
            this_1.gravity = (() => {switch(align) {
                        case Align.start:
                        return Gravity.TOP | Gravity.START
                        break;
                        case Align.center:
                        return Gravity.CENTER
                        break;
                        case Align.end:
                        return Gravity.BOTTOM | Gravity.END
                        break;
                        case Align.fill:
                        return Gravity.CENTER
                        break;
                    }
            })();
            this_1.setMargins(Math.floor((d * marginStart)), Math.floor((d * marginTop)), Math.floor((d * marginEnd)), Math.floor((d * marginBottom)));
    });
}
