// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: observables/ObservableProperty.shared.kt
// Package: com.lightningkite.khrysalis.observables
// FQImport: com.lightningkite.khrysalis.observables.ObservableProperty.T TS T
import { Observable } from 'rxjs'

//! Declares com.lightningkite.khrysalis.observables.ObservableProperty
export abstract class ObservableProperty<T> {
    protected constructor() {
    }
    
    public abstract readonly value: T;
    
    public abstract readonly onChange: Observable<T>;
    
}


