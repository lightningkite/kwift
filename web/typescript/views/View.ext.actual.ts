// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: views/View.ext.actual.kt
// Package: com.lightningkite.khrysalis.views
// FQImport: android.view.View.background TS getAndroidViewViewBackground
// FQImport: com.lightningkite.khrysalis.views.onClick.disabledMilliseconds TS disabledMilliseconds
// FQImport: android.graphics.drawable.Drawable TS Drawable
// FQImport: com.lightningkite.khrysalis.views.<set-backgroundDrawable>.value TS value
// FQImport: com.lightningkite.khrysalis.views.setBackgroundColorResource.color TS color
// FQImport: java.lang.System TS System
// FQImport: java.lang.System.currentTimeMillis TS currentTimeMillis
// FQImport: com.lightningkite.khrysalis.views.onClick.lastActivated TS lastActivated
// FQImport: com.lightningkite.khrysalis.views.onLongClick.action TS action
// FQImport: com.lightningkite.khrysalis.views.ColorResource TS ColorResource
// FQImport: android.view.View.background TS setAndroidViewViewBackground
// FQImport: com.lightningkite.khrysalis.views.onClick.action TS action
// FQImport: com.lightningkite.khrysalis.views.<set-backgroundResource>.value TS value
import { ColorResource } from './ResourceTypes.actual'

//! Declares com.lightningkite.khrysalis.views.backgroundDrawable
export function getAndroidViewViewBackgroundDrawable(this_: HTMLElement): (Drawable | null) { return getAndroidViewViewBackground(this_); }
export function setAndroidViewViewBackgroundDrawable(this_: HTMLElement, value: (Drawable | null)) {
    setAndroidViewViewBackground(this_, value);
}


//! Declares com.lightningkite.khrysalis.views.backgroundResource
export function getAndroidViewViewBackgroundResource(this_: HTMLElement): number { return 0; }
export function setAndroidViewViewBackgroundResource(this_: HTMLElement, value: number) {
    this_.style.background = value;
}



//! Declares com.lightningkite.khrysalis.views.onClick
export function androidViewViewOnClick(this_: HTMLElement, action: () => void): void{
    this_.addEventListener("onclick", (_ev) => {
            500(_ev.target as HTMLElement)
    });
}

//! Declares com.lightningkite.khrysalis.views.onClick
export function androidViewViewOnClick(this_: HTMLElement, disabledMilliseconds: number, action: () => void): void{
    let lastActivated = System.currentTimeMillis();
    
    this_.addEventListener("onclick", (_ev) => {
            const it = _ev.target as HTMLElement;
            if (System.currentTimeMillis() - lastActivated > disabledMilliseconds) {
                action();
                lastActivated = System.currentTimeMillis();
            }
    });
}

//! Declares com.lightningkite.khrysalis.views.onLongClick
export function androidViewViewOnLongClick(this_: HTMLElement, action: () => void): void{
    this_.addEventListener("oncontextmenu", (_ev) => {
            const it = _ev.target as HTMLElement;
            action();; return true;
    });
}


//! Declares com.lightningkite.khrysalis.views.setBackgroundColorResource
export function androidViewViewSetBackgroundColorResource(this_: HTMLElement, color: ColorResource): void{
    this_.style.background = color;
}

