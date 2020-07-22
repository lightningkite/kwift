// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: observables/ReferenceObservableProperty.shared.kt
// Package: com.lightningkite.khrysalis.observables
// FQImport: com.lightningkite.khrysalis.observables.ReferenceObservableProperty.T TS T
// FQImport: com.lightningkite.khrysalis.observables.ReferenceObservableProperty.event TS event
// FQImport: com.lightningkite.khrysalis.observables.ReferenceObservableProperty.set TS set
// FQImport: com.lightningkite.khrysalis.observables.ReferenceObservableProperty.<set-value>.value TS value
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty TS MutableObservableProperty
// FQImport: com.lightningkite.khrysalis.observables.ReferenceObservableProperty.get TS get
import { Observable } from 'rxjs'
import { MutableObservableProperty } from './MutableObservableProperty.shared'

//! Declares com.lightningkite.khrysalis.observables.ReferenceObservableProperty
export class ReferenceObservableProperty<T> extends MutableObservableProperty<T> {
    public readonly get:  () => T;
    public readonly set:  (a: T) => void;
    public readonly event: Observable<T>;
    public constructor(get:  () => T, set:  (a: T) => void, event: Observable<T>) {
        super();
        this.get = get;
        this.set = set;
        this.event = event;
    }
    
    
    //! Declares com.lightningkite.khrysalis.observables.ReferenceObservableProperty.onChange
    public get onChange(): Observable<T> { return this.event; }
    
    //! Declares com.lightningkite.khrysalis.observables.ReferenceObservableProperty.value
    public get value(): T { return this.get(); }
    public set value(value: T) {
        this.set(value);
    }
    
    public update(): void {
        //do nothing
    }
}

