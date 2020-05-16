// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: observables/TransformedMutableObservableProperty2.shared.kt
// Package: com.lightningkite.khrysalis.observables
// FQImport: com.lightningkite.khrysalis.observables.mapWithExisting.B TS B
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty.value TS value
// FQImport: com.lightningkite.khrysalis.observables.TransformedMutableObservableProperty2.A TS A
// FQImport: com.lightningkite.khrysalis.observables.TransformedMutableObservableProperty2.write TS write
// FQImport: com.lightningkite.khrysalis.observables.TransformedMutableObservableProperty2 TS TransformedMutableObservableProperty2
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty.update TS update
// FQImport: com.lightningkite.khrysalis.observables.TransformedMutableObservableProperty2.B TS B
// FQImport: com.lightningkite.khrysalis.observables.TransformedMutableObservableProperty2.<set-value>.value TS value
// FQImport: com.lightningkite.khrysalis.observables.mapWithExisting.T TS T
// FQImport: com.lightningkite.khrysalis.observables.mapWithExisting.write TS write
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty.onChange TS onChange
// FQImport: com.lightningkite.khrysalis.observables.mapWithExisting.read TS read
// FQImport: com.lightningkite.khrysalis.observables.TransformedMutableObservableProperty2.basedOn TS basedOn
// FQImport: com.lightningkite.khrysalis.observables.TransformedMutableObservableProperty2.read TS read
// FQImport: com.lightningkite.khrysalis.observables.TransformedMutableObservableProperty2.onChange.<anonymous>.it TS it
// FQImport: com.lightningkite.khrysalis.observables.TransformedMutableObservableProperty2 SKIPPED due to same file
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty TS MutableObservableProperty
import { Observable } from 'rxjs'
import { map as rxMap } from 'rxjs/operators'
import { MutableObservableProperty } from './MutableObservableProperty.shared'

//! Declares com.lightningkite.khrysalis.observables.TransformedMutableObservableProperty2
export class TransformedMutableObservableProperty2<A, B> extends MutableObservableProperty<any> {
    public readonly basedOn: MutableObservableProperty<A>;
    public readonly read:  (a: A) => B;
    public readonly write:  (a: A, b: B) => A;
    public constructor(basedOn: MutableObservableProperty<A>, read:  (a: A) => B, write:  (a: A, b: B) => A) {
        super();
        this.basedOn = basedOn;
        this.read = read;
        this.write = write;
        this.onChange = this.basedOn.onChange.pipe(rxMap((it) => this.read(it)));
    }
    
    public update(){
        this.basedOn.update();
    }
    
    //! Declares com.lightningkite.khrysalis.observables.TransformedMutableObservableProperty2.value
    public get value(): B {
        return this.read(this.basedOn.value);
    }
    public set value(value: B) {
        this.basedOn.value = this.write(this.basedOn.value, value);
    }
    
    public readonly onChange: Observable<B> = this.basedOn.onChange.pipe(rxMap((it) => this.read(it)));
    
}

//! Declares com.lightningkite.khrysalis.observables.mapWithExisting
export function comLightningkiteKhrysalisObservablesMutableObservablePropertyMapWithExisting<T, B>(this_: MutableObservableProperty<T>, read:  (a: T) => B, write:  (a: T, b: B) => T): MutableObservableProperty<B>{
    return new TransformedMutableObservableProperty2<T, B>(this_, read, write);
}

