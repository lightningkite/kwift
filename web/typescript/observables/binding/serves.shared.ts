// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: observables/binding/serves.shared.kt
// Package: com.lightningkite.khrysalis.observables.binding
// FQImport: com.lightningkite.khrysalis.observables.binding.serves.<anonymous>.value TS value
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty.value TS value
// FQImport: com.lightningkite.khrysalis.observables.binding.serves.T TS T
// FQImport: com.lightningkite.khrysalis.rx.addWeak>io.reactivex.Observable<kotlin.Any> TS ioReactivexObservableAddWeak
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty.onChange TS onChange
// FQImport: com.lightningkite.khrysalis.rx.DisposeCondition TS DisposeCondition
// FQImport: com.lightningkite.khrysalis.observables.binding.serves.whilePresent TS whilePresent
// FQImport: com.lightningkite.khrysalis.observables.binding.serves.suppress TS suppress
// FQImport: com.lightningkite.khrysalis.observables.observable>com.lightningkite.khrysalis.observables.ObservableProperty<kotlin.Any> TS getComLightningkiteKhrysalisObservablesObservablePropertyObservable
// FQImport: com.lightningkite.khrysalis.observables.binding.serves.other TS other
// FQImport: com.lightningkite.khrysalis.observables.binding.serves.until TS until
// FQImport: com.lightningkite.khrysalis.rx.until>io.reactivex.disposables.Disposable TS ioReactivexDisposablesDisposableUntil
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty TS MutableObservableProperty
import { MutableObservableProperty } from './../MutableObservableProperty.shared'
import { ioReactivexDisposablesDisposableUntil } from './../../rx/DisposeCondition.actual'
import { ioReactivexObservableAddWeak } from './../../rx/RxAddAndRunWeak.shared'
import { getComLightningkiteKhrysalisObservablesObservablePropertyObservable } from './../ObservableProperty.ext.shared'
import { DisposeCondition } from './../../rx/DisposeCondition.shared'
import { SubscriptionLike } from 'rxjs'

//! Declares com.lightningkite.khrysalis.observables.binding.serves>com.lightningkite.khrysalis.observables.MutableObservableProperty<kotlin.Any>
export function comLightningkiteKhrysalisObservablesMutableObservablePropertyServes<T>(this_: MutableObservableProperty<T>, whilePresent: object, other: MutableObservableProperty<T>): SubscriptionLike{
    
    let suppress = false;
    
    
    ioReactivexObservableAddWeak(getComLightningkiteKhrysalisObservablesObservablePropertyObservable(other), whilePresent, (ignored, value) => {
            if (!suppress) {
                suppress = true;
                this_.value = value;
                suppress = false;
            }
    });
    
    ioReactivexObservableAddWeak(this_.onChange, whilePresent, (ignored, value) => {
            if (!suppress) {
                suppress = true;
                other.value = value;
                suppress = false;
            }
    });
}

//! Declares com.lightningkite.khrysalis.observables.binding.serves>com.lightningkite.khrysalis.observables.MutableObservableProperty<kotlin.Any>
export function comLightningkiteKhrysalisObservablesMutableObservablePropertyServes<T>(this_: MutableObservableProperty<T>, until: DisposeCondition, other: MutableObservableProperty<T>): SubscriptionLike{
    
    let suppress = false;
    
    
    ioReactivexDisposablesDisposableUntil(getComLightningkiteKhrysalisObservablesObservablePropertyObservable(other).subscribe((value) => {
                if (!suppress) {
                    suppress = true;
                    this_.value = value;
                    suppress = false;
                }
    }, undefined, undefined), until);
    
    ioReactivexDisposablesDisposableUntil(this_.onChange.subscribe((value) => {
                if (!suppress) {
                    suppress = true;
                    other.value = value;
                    suppress = false;
                }
    }, undefined, undefined), until);
}

