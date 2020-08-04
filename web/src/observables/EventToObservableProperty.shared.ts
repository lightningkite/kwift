// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: observables/EventToObservableProperty.shared.kt
// Package: com.lightningkite.khrysalis.observables
import { ObservableProperty } from './ObservableProperty.shared'
import { Observable } from 'rxjs'
import { map as rxMap } from 'rxjs/operators'

//! Declares com.lightningkite.khrysalis.observables.EventToObservableProperty
export class EventToObservableProperty<T> extends ObservableProperty<T> {
    public value: T;
    public readonly wrapped: Observable<T>;
    public constructor(value: T, wrapped: Observable<T>) {
        super();
        this.value = value;
        this.wrapped = wrapped;
    }
    
    //! Declares com.lightningkite.khrysalis.observables.EventToObservableProperty.onChange
    public get onChange(): Observable<T> { return this.wrapped.pipe(rxMap((it: (T | null)): ((T | null) | null) => {
                    this.value = it;
                    return it;
    })); }
    
}
//! Declares com.lightningkite.khrysalis.observables.asObservableProperty>io.reactivex.Observable<kotlin.Any>
export function ioReactivexObservableAsObservableProperty<Element>(this_: Observable< Element>, defaultValue: Element): ObservableProperty<Element> {
    return new EventToObservableProperty<Element>(defaultValue, this_.pipe(rxMap((it: (Element | null)): ((Element | null) | null) => it)));
}

//! Declares com.lightningkite.khrysalis.observables.asObservablePropertyDefaultNull>io.reactivex.Observable<kotlin.Any>
export function ioReactivexObservableAsObservablePropertyDefaultNull<Element>(this_: Observable< Element>): ObservableProperty<(Element | null)> {
    return new EventToObservableProperty<(Element | null)>(null, this_.pipe(rxMap((it: (Element | null)): ((Element | null) | null) => it)));
}


