// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: observables/EventToObservableProperty.actual.kt
// Package: com.lightningkite.khrysalis.observables
// FQImport: com.lightningkite.khrysalis.observables.asObservablePropertyUnboxed.T TS T
// FQImport: com.lightningkite.khrysalis.observables.asObservablePropertyUnboxed.defaultValue TS defaultValue
// FQImport: com.lightningkite.khrysalis.observables.EventToObservableProperty TS EventToObservableProperty
// FQImport: com.lightningkite.khrysalis.observables.ObservableProperty TS ObservableProperty
import { ObservableProperty } from './ObservableProperty.shared'
import { EventToObservableProperty } from './EventToObservableProperty.shared'
import { Observable } from 'rxjs'

//! Declares com.lightningkite.khrysalis.observables.asObservablePropertyUnboxed
export function ioReactivexObservableAsObservablePropertyUnboxed<T>(this_: Observable<T>, defaultValue: T): ObservableProperty<T>{
    return new EventToObservableProperty<T>(defaultValue, this_);
}

