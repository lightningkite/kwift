// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: observables/ConstantObservableProperty.shared.kt
// Package: com.lightningkite.khrysalis.observables
// FQImport: com.lightningkite.khrysalis.observables.ConstantObservableProperty.underlyingValue TS underlyingValue
// FQImport: com.lightningkite.khrysalis.observables.ConstantObservableProperty.T TS T
// FQImport: com.lightningkite.khrysalis.observables.ObservableProperty TS ObservableProperty
import { ObservableProperty } from './ObservableProperty.shared'
import { Observable, NEVER as rxNEVER } from 'rxjs'

//! Declares com.lightningkite.khrysalis.observables.ConstantObservableProperty
export class ConstantObservableProperty<T> extends ObservableProperty<any> {
    public readonly underlyingValue: T;
    public constructor(underlyingValue: T) {
        super();
        this.underlyingValue = underlyingValue;
        this.onChange = rxNEVER;
    }
    
    public readonly onChange: Observable<T> = rxNEVER;
    
    //! Declares com.lightningkite.khrysalis.observables.ConstantObservableProperty.value
    public get value(): T { return this.underlyingValue; }
    
}

