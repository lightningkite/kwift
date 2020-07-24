// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: observables/ObservableProperty.ext.shared.kt
// Package: com.lightningkite.khrysalis.observables
import { comLightningkiteKhrysalisObservablesObservablePropertyWithWrite } from './WriteAddedObservableProperty.shared'
import { ObservableProperty } from './ObservableProperty.shared'
import { EqualOverrideSet } from '../KotlinCollections'
import { comLightningkiteKhrysalisObservablesObservablePropertyMap } from './TransformedObservableProperty.shared'
import { Observable, SubscriptionLike, concat as rxConcat, of as rxOf } from 'rxjs'
import { map as rxMap } from 'rxjs/operators'
import { MutableObservableProperty } from './MutableObservableProperty.shared'

//! Declares com.lightningkite.khrysalis.observables.observable>com.lightningkite.khrysalis.observables.ObservableProperty<kotlin.Any>
export function getComLightningkiteKhrysalisObservablesObservablePropertyObservable<T>(this_: ObservableProperty<T>): Observable<T> { return rxConcat(rxOf(this_.value), this_.onChange); }

//! Declares com.lightningkite.khrysalis.observables.observableNN>com.lightningkite.khrysalis.observables.ObservableProperty<kotlin.Any>
export function getComLightningkiteKhrysalisObservablesObservablePropertyObservableNN<T>(this_: ObservableProperty<T>): Observable<T> { return rxConcat(rxOf(this_.value), this_.onChange).pipe(rxMap((it: (any | null)): (any | null) => it)); }

//! Declares com.lightningkite.khrysalis.observables.onChangeNN>com.lightningkite.khrysalis.observables.ObservableProperty<kotlin.Any>
export function getComLightningkiteKhrysalisObservablesObservablePropertyOnChangeNN<T>(this_: ObservableProperty<T>): Observable<T> { return this_.onChange.pipe(rxMap((it: (any | null)): (any | null) => it)); }


//! Declares com.lightningkite.khrysalis.observables.subscribeBy>com.lightningkite.khrysalis.observables.ObservableProperty<kotlin.Any>
export function comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy<T>(this_: ObservableProperty<T>, onError:  ((a: any) => void) = (it: any): void => {
        it.printStackTrace()
}, onComplete:  (() => void) = (): void => {}, onNext:  ((a: T) => void) = (it: (any | null)): void => {}): SubscriptionLike { return getComLightningkiteKhrysalisObservablesObservablePropertyObservable(this_).subscribe((boxed: (any | null)): void => {
            onNext(boxed)
}, onError, onComplete); }

//! Declares com.lightningkite.khrysalis.observables.includes
export function includes<E>(collection: MutableObservableProperty<Set<E>>, element: E): MutableObservableProperty<boolean> {
    return comLightningkiteKhrysalisObservablesObservablePropertyWithWrite<boolean>(comLightningkiteKhrysalisObservablesObservablePropertyMap<Set<(any | null)>, boolean>(collection, (it: Set<(any | null)>): boolean => it.has(element)), (it: boolean): void => {
            if (it) {
                collection.value = new EqualOverrideSet([...collection.value, element]);
            } else {
                collection.value = new EqualOverrideSet([...collection.value].filter(x => x !== element));
            }
    });
}

