// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: rx/RxExtensions.actual.kt
// Package: com.lightningkite.khrysalis.rx
// FQImport: kotlin.Boolean TS Boolean
// FQImport: com.lightningkite.khrysalis.post TS post
// FQImport: com.lightningkite.khrysalis.rx.combineLatest.OUT TS OUT
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty.value TS value
// FQImport: com.lightningkite.khrysalis.rx.mapNotNull.<anonymous>.it TS it
// FQImport: com.lightningkite.khrysalis.rx.combineLatest.R TS R
// FQImport: com.lightningkite.khrysalis.rx.working.observable TS observable
// FQImport: com.lightningkite.khrysalis.rx.combineLatest.observable TS observable
// FQImport: com.lightningkite.khrysalis.rx.combineLatest.Element TS Element
// FQImport: io.reactivex.functions.BiFunction TS BiFunction
// FQImport: com.lightningkite.khrysalis.rx.combineLatest.function TS function
// FQImport: com.lightningkite.khrysalis.Box.value TS value
// FQImport: com.lightningkite.khrysalis.rx.filterNotNull.Element TS Element
// FQImport: com.lightningkite.khrysalis.rx.mapNotNull.transform TS transform
// FQImport: com.lightningkite.khrysalis.rx.mapNotNull.Destination TS Destination
// FQImport: com.lightningkite.khrysalis.rx.filterNotNull.<anonymous>.it TS it
// FQImport: io.reactivex.Observable.combineLatest TS combineLatest
// FQImport: com.lightningkite.khrysalis.rx.working.Element TS Element
// FQImport: com.lightningkite.khrysalis.rx.mapNotNull.Element TS Element
// FQImport: com.lightningkite.khrysalis.rx.mapNotNull.<anonymous>.result TS result
// FQImport: io.reactivex.Observable TS Observable
// FQImport: com.lightningkite.khrysalis.Box TS Box
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty TS MutableObservableProperty
import { filter as rxFilter, finalize as rxFinalize, flatMap as rxFlatMap, map as rxMap } from 'rxjs/operators'
import { MutableObservableProperty } from './../observables/MutableObservableProperty.shared'
import { doOnSubscribe } from 'khrysalis/dist/Kotlin'
import { Observable, of as rxoF } from 'rxjs'
import { post } from './../delay.actual'

//! Declares com.lightningkite.khrysalis.rx.combineLatest
export function ioReactivexObservableCombineLatest<Element extends any, R extends any, OUT extends any>(this_CombineLatest: Observable<Element>, observable: Observable<R>, function: (a: Element, b: R) => OUT): Observable<OUT>{ return Observable.combineLatest(this_CombineLatest, observable, BiFunction(function)); }

//! Declares com.lightningkite.khrysalis.rx.filterNotNull
export function ioReactivexObservableFilterNotNull<Element>(this_FilterNotNull: Observable<Box<Element>>): Observable<Element>{ return rxMap((it) => it.value)(rxFilter((it) => !(it.value.equals(null)))(this_FilterNotNull)); }

//! Declares com.lightningkite.khrysalis.rx.mapNotNull
export function ioReactivexObservableMapNotNull<Element extends any, Destination extends any>(this_MapNotNull: Observable<Element>, transform: (a: Element) => (Destination | null)): Observable<Destination>{ return rxFlatMap((it) => {
            const result = this.transform(it);
            
            result.equals(null) ? rxOf() : rxOf(result)
})(this_MapNotNull); }

//! Declares com.lightningkite.khrysalis.rx.working
export function ioReactivexSingleWorking<Element extends any>(this_Working: Observable<Element>, observable: MutableObservableProperty<Boolean>): Observable<Element>{
    return rxFinalize(() => post(() => observable.value = false))(doOnSubscribe(this_Working, (it) => post(() => observable.value = true)));
}

