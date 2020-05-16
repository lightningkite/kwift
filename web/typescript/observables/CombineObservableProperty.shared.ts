// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: observables/CombineObservableProperty.shared.kt
// Package: com.lightningkite.khrysalis.observables
// FQImport: com.lightningkite.khrysalis.observables.CombineObservableProperty.A TS A
// FQImport: com.lightningkite.khrysalis.observables.CombineObservableProperty.combiner TS combiner
// FQImport: com.lightningkite.khrysalis.observables.CombineObservableProperty TS CombineObservableProperty
// FQImport: com.lightningkite.khrysalis.observables.CombineObservableProperty.observableB TS observableB
// FQImport: com.lightningkite.khrysalis.observables.CombineObservableProperty.<get-onChange>.<anonymous>.a TS a
// FQImport: com.lightningkite.khrysalis.observables.ObservableProperty.onChange TS onChange
// FQImport: com.lightningkite.khrysalis.observables.CombineObservableProperty SKIPPED due to same file
// FQImport: com.lightningkite.khrysalis.observables.combine.C TS C
// FQImport: com.lightningkite.khrysalis.observables.ObservableProperty TS ObservableProperty
// FQImport: com.lightningkite.khrysalis.observables.CombineObservableProperty.T TS T
// FQImport: com.lightningkite.khrysalis.observables.combine.T TS T
// FQImport: com.lightningkite.khrysalis.observables.combine.combiner TS combiner
// FQImport: com.lightningkite.khrysalis.observables.CombineObservableProperty.observableA TS observableA
// FQImport: com.lightningkite.khrysalis.observables.CombineObservableProperty.B TS B
// FQImport: com.lightningkite.khrysalis.observables.combine.other TS other
// FQImport: com.lightningkite.khrysalis.observables.combine.B TS B
// FQImport: com.lightningkite.khrysalis.observables.CombineObservableProperty.<get-onChange>.<anonymous>.b TS b
// FQImport: com.lightningkite.khrysalis.observables.ObservableProperty.value TS value
import { map as rxMap, skip as rxSkip } from 'rxjs/operators'
import { ObservableProperty } from './ObservableProperty.shared'
import { Observable, combineLatest as rxCombineLatest, concat as rxConcat, of as rxOf } from 'rxjs'

//! Declares com.lightningkite.khrysalis.observables.CombineObservableProperty
export class CombineObservableProperty<T, A, B> extends ObservableProperty<any> {
    public readonly observableA: ObservableProperty<A>;
    public readonly observableB: ObservableProperty<B>;
    public readonly combiner:  (a: A, b: B) => T;
    public constructor(observableA: ObservableProperty<A>, observableB: ObservableProperty<B>, combiner:  (a: A, b: B) => T) {
        super();
        this.observableA = observableA;
        this.observableB = observableB;
        this.combiner = combiner;
    }
    
    //! Declares com.lightningkite.khrysalis.observables.CombineObservableProperty.value
    public get value(): T { return this.combiner(this.observableA.value, this.observableB.value); }
    
    //! Declares com.lightningkite.khrysalis.observables.CombineObservableProperty.onChange
    public get onChange(): Observable<T> { return rxCombineLatest([rxConcat(rxOf(this.observableA.value), this.observableA.onChange), rxConcat(rxOf(this.observableB.value), this.observableB.onChange)]).pipe(rxMap(x => ((a: A, b: B) => this.combiner(a, b))(x[0], x[1]))).pipe(rxSkip(0)); }
    
}

//! Declares com.lightningkite.khrysalis.observables.combine
export function comLightningkiteKhrysalisObservablesObservablePropertyCombine<T, B, C>(this_: ObservableProperty<T>, other: ObservableProperty<B>, combiner:  (a: T, b: B) => C): ObservableProperty<C>{
    return new CombineObservableProperty(this_, other, combiner);
}

