// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: observables/binding/TextView.binding.actual.kt
// Package: com.lightningkite.khrysalis.observables.binding
// FQImport: com.lightningkite.khrysalis.rx.until TS ioReactivexDisposablesDisposableUntil
// FQImport: com.lightningkite.khrysalis.observables.binding.bindText.transform TS transform
// FQImport: com.lightningkite.khrysalis.observables.binding.bindText.observable TS observable
// FQImport: android.widget.TextView.visibility TS setAndroidWidgetTextViewVisibility
// FQImport: com.lightningkite.khrysalis.observables.binding.bindString.observable TS observable
// FQImport: android.content.res.Resources.getString TS getString
// FQImport: com.lightningkite.khrysalis.observables.ObservableProperty TS ObservableProperty
// FQImport: com.lightningkite.khrysalis.observables.binding.bindText.<anonymous>.value TS value
// FQImport: com.lightningkite.khrysalis.views.StringResource TS StringResource
// FQImport: com.lightningkite.khrysalis.observables.subscribeBy TS comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy
// FQImport: com.lightningkite.khrysalis.observables.binding.bindStringRes.observable TS observable
// FQImport: com.lightningkite.khrysalis.observables.binding.bindString.<anonymous>.value TS value
// FQImport: com.lightningkite.khrysalis.rx.removed TS getAndroidViewViewRemoved
// FQImport: android.widget.TextView.resources TS getAndroidWidgetTextViewResources
// FQImport: com.lightningkite.khrysalis.observables.binding.bindText.T TS T
// FQImport: com.lightningkite.khrysalis.observables.binding.bindStringRes.<anonymous>.value TS value
import { comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy } from './../ObservableProperty.ext.shared'
import { getAndroidViewViewRemoved, ioReactivexDisposablesDisposableUntil } from './../../rx/DisposeCondition.actual'
import { ObservableProperty } from './../ObservableProperty.shared'
import { SubscriptionLike } from 'rxjs'
import { StringResource } from './../../views/ResourceTypes.actual'

//! Declares com.lightningkite.khrysalis.observables.binding.bindString
export function androidWidgetTextViewBindString(this_: HTMLElement, observable: ObservableProperty<string>): SubscriptionLike{
    ioReactivexDisposablesDisposableUntil(comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy(observable, undefined, undefined, (value) => {
                this_.innerText = value
    }), getAndroidViewViewRemoved(this_));
}

//! Declares com.lightningkite.khrysalis.observables.binding.bindStringRes
export function androidWidgetTextViewBindStringRes(this_: HTMLElement, observable: ObservableProperty<(StringResource | null)>): SubscriptionLike{
    ioReactivexDisposablesDisposableUntil(comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy(observable, undefined, undefined, (value) => {
                setAndroidWidgetTextViewVisibility(this_, value === null ? "gone" : "visible");
                if (!(value === null)) {
                    this_.innerText = getAndroidWidgetTextViewResources(this_).getString(value);
                }
    }), getAndroidViewViewRemoved(this_));
}


//! Declares com.lightningkite.khrysalis.observables.binding.bindText
export function androidWidgetTextViewBindText<T>(this_: HTMLElement, observable: ObservableProperty<T>, transform: (a: T) => string): SubscriptionLike{
    ioReactivexDisposablesDisposableUntil(comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy(observable, undefined, undefined, (value) => {
                this_.innerText = transform(value)
    }), getAndroidViewViewRemoved(this_));
}

