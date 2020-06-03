// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: observables/binding/SeekBar.binding.actual.kt
// Package: com.lightningkite.khrysalis.observables.binding
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty.value TS value
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.endInclusive TS endInclusive
// FQImport: android.widget.SeekBar.progress TS progress
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.<anonymous>.value TS value
// FQImport: android.widget.SeekBar.OnSeekBarChangeListener TS SeekBarOnSeekBarChangeListener
// FQImport: com.lightningkite.khrysalis.observables.subscribeBy>com.lightningkite.khrysalis.observables.ObservableProperty<kotlin.Any> TS comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.start TS start
// FQImport: android.widget.SeekBar.incrementProgressBy TS incrementProgressBy
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.suppress TS suppress
// FQImport: android.widget.SeekBar.max TS max
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.<no name provided>.onProgressChanged.p1 TS p1
// FQImport: android.widget.SeekBar.setOnSeekBarChangeListener TS setOnSeekBarChangeListener
// FQImport: com.lightningkite.khrysalis.rx.removed>android.view.View TS getAndroidViewViewRemoved
// FQImport: com.lightningkite.khrysalis.rx.until>io.reactivex.disposables.Disposable TS ioReactivexDisposablesDisposableUntil
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.observable TS observable
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty TS MutableObservableProperty
import { comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy } from './../ObservableProperty.ext.shared'
import { MutableObservableProperty } from './../MutableObservableProperty.shared'
import { getAndroidViewViewRemoved, ioReactivexDisposablesDisposableUntil } from './../../rx/DisposeCondition.actual'

//! Declares com.lightningkite.khrysalis.observables.binding.bind>android.widget.SeekBar
export function androidWidgetSeekBarBind(this_: HTMLInputElement, start: number, endInclusive: number, observable: MutableObservableProperty<number>): void{
    this_.max = endInclusive - start;
    this_.incrementProgressBy(1);
    
    let suppress = false;
    
    ioReactivexDisposablesDisposableUntil(comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy(observable, undefined, undefined, (value) => {
                if (!suppress) {
                    suppress = true;
                    this_.progress = value + start;
                    suppress = false;
                }
    }), getAndroidViewViewRemoved(this_));
    this_.setOnSeekBarChangeListener(new class Anon implements SeekBarOnSeekBarChangeListener {
            public static implementsInterfaceAndroidWidgetSeekBarOnSeekBarChangeListener = true;
            public constructor() {
            }
            
            onProgressChanged(p0: (HTMLInputElement | null), p1: number, p2: boolean): void{
                if (!suppress) {
                    suppress = true;
                    observable.value = p1 + start;
                    suppress = false;
                }
            }
            
            onStartTrackingTouch(p0: (HTMLInputElement | null)): void{
            }
            
            onStopTrackingTouch(p0: (HTMLInputElement | null)): void{
            }
    }());
    
}

