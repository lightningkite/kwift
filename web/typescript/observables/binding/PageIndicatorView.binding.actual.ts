// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: observables/binding/PageIndicatorView.binding.actual.kt
// Package: com.lightningkite.khrysalis.observables.binding
// FQImport: com.lightningkite.khrysalis.rx.until TS ioReactivexDisposablesDisposableUntil
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.selected TS selected
// FQImport: com.lightningkite.khrysalis.observables.subscribeBy TS comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy
// FQImport: com.lightningkite.khrysalis.rx.removed TS getAndroidViewViewRemoved
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.<anonymous>.value TS value
// FQImport: com.rd.PageIndicatorView TS PageIndicatorView
// FQImport: count TS setComRdPageIndicatorViewCount
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty TS MutableObservableProperty
// FQImport: selection TS setComRdPageIndicatorViewSelection
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.count TS count
import { comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy } from './../ObservableProperty.ext.shared'
import { MutableObservableProperty } from './../MutableObservableProperty.shared'
import { getAndroidViewViewRemoved, ioReactivexDisposablesDisposableUntil } from './../../rx/DisposeCondition.actual'

//! Declares com.lightningkite.khrysalis.observables.binding.bind
export function comRdPageIndicatorViewBind(this_Bind: PageIndicatorView, count: number = 0, selected: MutableObservableProperty<number>){
    setComRdPageIndicatorViewCount(this_Bind, count);
    return ioReactivexDisposablesDisposableUntil(comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy(selected, undefined, undefined, (value) => setComRdPageIndicatorViewSelection(this_Bind, value)), getAndroidViewViewRemoved(this_Bind));
}

