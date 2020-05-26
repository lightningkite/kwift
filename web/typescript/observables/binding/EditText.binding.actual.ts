// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: observables/binding/EditText.binding.actual.kt
// Package: com.lightningkite.khrysalis.observables.binding
// FQImport: com.lightningkite.khrysalis.observables.binding.bindString.<no name provided>.onTextChanged.s TS s
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty.value TS value
// FQImport: com.lightningkite.khrysalis.observables.binding.bindDouble.<anonymous>.<anonymous>.it TS it
// FQImport: com.lightningkite.khrysalis.observables.binding.bindInteger.<no name provided>.onTextChanged.currentValue TS currentValue
// FQImport: com.lightningkite.khrysalis.observables.binding.bindDouble.<anonymous>.value TS value
// FQImport: com.lightningkite.khrysalis.observables.binding.bindString.observable TS observable
// FQImport: android.widget.EditText.addTextChangedListener TS addTextChangedListener
// FQImport: kotlin.Int.toString TS toString
// FQImport: com.lightningkite.khrysalis.observables.subscribeBy TS comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy
// FQImport: com.lightningkite.khrysalis.observables.binding.bindInteger.<anonymous>.currentValue TS currentValue
// FQImport: android.text.Editable.toString TS toString
// FQImport: com.lightningkite.khrysalis.observables.binding.bindInteger.observable TS observable
// FQImport: com.lightningkite.khrysalis.observables.binding.bindDouble.<anonymous>.currentValue TS currentValue
// FQImport: kotlin.CharSequence TS CharSequence
// FQImport: com.lightningkite.khrysalis.observables.binding.bindInteger.<anonymous>.<anonymous>.it TS it
// FQImport: com.lightningkite.khrysalis.rx.until TS ioReactivexDisposablesDisposableUntil
// FQImport: android.text.TextWatcher TS TextWatcher
// FQImport: com.lightningkite.khrysalis.observables.binding.bindInteger.<anonymous>.value TS value
// FQImport: com.lightningkite.khrysalis.observables.binding.bindDouble.observable TS observable
// FQImport: com.lightningkite.khrysalis.observables.binding.bindDouble.<no name provided>.onTextChanged.currentValue TS currentValue
// FQImport: kotlin.toString TS kotlinAnyToString
// FQImport: kotlin.Double.toString TS toString
// FQImport: com.lightningkite.khrysalis.rx.removed TS getAndroidViewViewRemoved
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty TS MutableObservableProperty
// FQImport: android.text.Editable TS Editable
import { comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy } from './../ObservableProperty.ext.shared'
import { parseFloatOrNull, parseIntOrNull, takeUnless } from 'Kotlin'
import { MutableObservableProperty } from './../MutableObservableProperty.shared'
import { getAndroidViewViewRemoved, ioReactivexDisposablesDisposableUntil } from './../../rx/DisposeCondition.actual'

//! Declares com.lightningkite.khrysalis.observables.binding.bindString
export function androidWidgetEditTextBindString(this_: HTMLInputElement, observable: MutableObservableProperty<string>): void{
    ioReactivexDisposablesDisposableUntil(comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy(observable, undefined, undefined, (value) => {
                if (!(observable.value === this_.value.toString())) {
                    this_.value = observable.value;
                }
    }), getAndroidViewViewRemoved(this_));
    this_.addTextChangedListener(new class Anon implements TextWatcher {
            public static implementsInterfaceAndroidTextTextWatcher = true;
            public static implementsInterfaceAndroidTextNoCopySpan = true;
            public constructor() {
            }
            
            afterTextChanged(s: (Editable | null)): void{}
            beforeTextChanged(s: (CharSequence | null), start: number, count: number, after: number): void{}
            onTextChanged(s: (CharSequence | null), start: number, before: number, count: number): void{
                if (!(observable.value === s)) {
                    observable.value = (kotlinAnyToString(s));
                }
            }
    }());
}

//! Declares com.lightningkite.khrysalis.observables.binding.bindInteger
export function androidWidgetEditTextBindInteger(this_: HTMLInputElement, observable: MutableObservableProperty<number>): void{
    ioReactivexDisposablesDisposableUntil(comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy(observable, undefined, undefined, (value) => {
                const currentValue = parseIntOrNull(undefined);
                
                if (!(value === currentValue)) {
                    this_.value = takeUnless(value, (it) => it === 0)?.toString() ?? "";
                }
    }), getAndroidViewViewRemoved(this_));
    this_.addTextChangedListener(new class Anon implements TextWatcher {
            public static implementsInterfaceAndroidTextTextWatcher = true;
            public static implementsInterfaceAndroidTextNoCopySpan = true;
            public constructor() {
            }
            
            afterTextChanged(s: (Editable | null)): void{}
            beforeTextChanged(s: (CharSequence | null), start: number, count: number, after: number): void{}
            onTextChanged(s: (CharSequence | null), start: number, before: number, count: number): void{
                const currentValue = parseIntOrNull(undefined) ?? 0;
                
                if (!(observable.value === currentValue)) {
                    observable.value = currentValue;
                }
            }
    }());
}



//! Declares com.lightningkite.khrysalis.observables.binding.bindDouble
export function androidWidgetEditTextBindDouble(this_: HTMLInputElement, observable: MutableObservableProperty<number>): void{
    ioReactivexDisposablesDisposableUntil(comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy(observable, undefined, undefined, (value) => {
                const currentValue = parseFloatOrNull(undefined);
                
                if (!(value === currentValue)) {
                    this_.value = takeUnless(value, (it) => it === 0.0)?.toString() ?? "";
                }
    }), getAndroidViewViewRemoved(this_));
    this_.addTextChangedListener(new class Anon implements TextWatcher {
            public static implementsInterfaceAndroidTextTextWatcher = true;
            public static implementsInterfaceAndroidTextNoCopySpan = true;
            public constructor() {
            }
            
            afterTextChanged(s: (Editable | null)): void{}
            beforeTextChanged(s: (CharSequence | null), start: number, count: number, after: number): void{}
            onTextChanged(s: (CharSequence | null), start: number, before: number, count: number): void{
                const currentValue = parseFloatOrNull(undefined) ?? 0.0;
                
                if (!(observable.value === currentValue)) {
                    observable.value = currentValue;
                }
            }
    }());
}

