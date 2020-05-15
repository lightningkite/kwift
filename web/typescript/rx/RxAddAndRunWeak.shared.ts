// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: rx/RxAddAndRunWeak.shared.kt
// Package: com.lightningkite.khrysalis.rx
// FQImport: kotlin.Boolean TS Boolean
// FQImport: com.lightningkite.khrysalis.rx.addWeak.disp TS disp
// FQImport: com.lightningkite.khrysalis.rx.add.listener TS listener
// FQImport: com.lightningkite.khrysalis.rx.add.disp TS disp
// FQImport: com.lightningkite.khrysalis.rx.addWeak.A TS A
// FQImport: com.lightningkite.khrysalis.rx.add.disposable TS disposable
// FQImport: com.lightningkite.khrysalis.rx.addWeak.weakA TS weakA
// FQImport: com.lightningkite.khrysalis.rx.solvePrivateDisposal SKIPPED due to same file
// FQImport: com.lightningkite.khrysalis.rx.addWeak.weakB TS weakB
// FQImport: com.lightningkite.khrysalis.rx.addWeak.referenceC TS referenceC
// FQImport: com.lightningkite.khrysalis.rx.addWeak.referenceA TS referenceA
// FQImport: com.lightningkite.khrysalis.rx.addWeak.<anonymous>.c TS c
// FQImport: com.lightningkite.khrysalis.rx.addWeak.<anonymous>.item TS item
// FQImport: com.lightningkite.khrysalis.rx.solvePrivateDisposal.items TS items
// FQImport: android.view.View TS View
// FQImport: com.lightningkite.khrysalis.rx.addWeak.weakC TS weakC
// FQImport: com.lightningkite.khrysalis.rx.until TS ioReactivexDisposablesDisposableUntil
// FQImport: com.lightningkite.khrysalis.rx.solvePrivateDisposal.item TS item
// FQImport: com.lightningkite.khrysalis.rx.addWeak.<anonymous>.b TS b
// FQImport: com.lightningkite.khrysalis.rx.addWeak.Element TS Element
// FQImport: com.lightningkite.khrysalis.rx.addWeak.listener TS listener
// FQImport: com.lightningkite.khrysalis.rx.add.Element TS Element
// FQImport: com.lightningkite.khrysalis.rx.solvePrivateDisposal TS ioReactivexDisposablesDisposableSolvePrivateDisposal
// FQImport: com.lightningkite.khrysalis.rx.addWeak.C TS C
// FQImport: com.lightningkite.khrysalis.rx.add.<anonymous>.item TS item
// FQImport: com.lightningkite.khrysalis.rx.addWeak.<anonymous>.a TS a
// FQImport: com.lightningkite.khrysalis.rx.addWeak.disposable TS disposable
// FQImport: com.lightningkite.khrysalis.rx.removed TS getAndroidViewViewRemoved
// FQImport: com.lightningkite.khrysalis.rx.addWeak.B TS B
// FQImport: com.lightningkite.khrysalis.rx.addWeak.referenceB TS referenceB
import { Observable, SubscriptionLike } from 'rxjs'
import { getAndroidViewViewRemoved, ioReactivexDisposablesDisposableUntil } from './DisposeCondition.actual'

//! Declares com.lightningkite.khrysalis.rx.solvePrivateDisposal
export function ioReactivexDisposablesDisposableSolvePrivateDisposal(this_SolvePrivateDisposal: SubscriptionLike, items: Array<any>){
    for (const item of items) {
        (() => {if(item instanceof View){
                    return ioReactivexDisposablesDisposableUntil(this_SolvePrivateDisposal, getAndroidViewViewRemoved(item));
        }})()
    }
}

//! Declares com.lightningkite.khrysalis.rx.add
export function ioReactivexObservableAdd<Element extends any>(this_Add: Observable<Element>, listener:  (a: Element) => Boolean): SubscriptionLike{
    let disposable: (SubscriptionLike | null) = null;
    
    const disp = this_Add.subscribe(undefined, (item) => (() => {if (this.listener(item)) {
                    const temp112 = disposable;
                    if(temp112 !== null) temp112.unsubscribe();
    }})(), undefined);
    
    disposable = disp;
    return disp;
}

//! Declares com.lightningkite.khrysalis.rx.addWeak
export function ioReactivexObservableAddWeak<A extends object, Element extends any>(this_AddWeak: Observable<Element>, referenceA: A, listener:  (a: A, b: Element) => void): SubscriptionLike{
    let disposable: (SubscriptionLike | null) = null;
    
    const weakA: (A | null);
    
    const disp = this_AddWeak.subscribe(undefined, (item) => {
            const a = weakA;
            
            (() => {if (!(a.equals(null))) {
                        this.listener(a, item);
                    } else {
                        const temp114 = disposable;
                        if(temp114 !== null) temp114.unsubscribe();
            }})()
    }, undefined);
    
    disposable = disp;
    ioReactivexDisposablesDisposableSolvePrivateDisposal(disp, [referenceA]);
    return disp;
}

//! Declares com.lightningkite.khrysalis.rx.addWeak
export function ioReactivexObservableAddWeak<A extends object, B extends object, Element extends any>(this_AddWeak: Observable<Element>, referenceA: A, referenceB: B, listener:  (a: A, b: B, c: Element) => void): SubscriptionLike{
    let disposable: (SubscriptionLike | null) = null;
    
    const weakA: (A | null);
    
    const weakB: (B | null);
    
    const disp = this_AddWeak.subscribe(undefined, (item) => {
            const a = weakA;
            
            const b = weakB;
            
            (() => {if (!(a.equals(null)) && !(b.equals(null))) {
                        this.listener(a, b, item);
                    } else {
                        const temp116 = disposable;
                        if(temp116 !== null) temp116.unsubscribe();
            }})()
    }, undefined);
    
    disposable = disp;
    ioReactivexDisposablesDisposableSolvePrivateDisposal(disp, [referenceA, referenceB]);
    return disp;
}


//! Declares com.lightningkite.khrysalis.rx.addWeak
export function ioReactivexObservableAddWeak<A extends object, B extends object, C extends object, Element extends any>(this_AddWeak: Observable<Element>, referenceA: A, referenceB: B, referenceC: C, listener:  (a: A, b: B, c: C, d: Element) => void): SubscriptionLike{
    let disposable: (SubscriptionLike | null) = null;
    
    const weakA: (A | null);
    
    const weakB: (B | null);
    
    const weakC: (C | null);
    
    const disp = this_AddWeak.subscribe(undefined, (item) => {
            const a = weakA;
            
            const b = weakB;
            
            const c = weakC;
            
            (() => {if (!(a.equals(null)) && !(b.equals(null)) && !(c.equals(null))) {
                        this.listener(a, b, c, item);
                    } else {
                        const temp118 = disposable;
                        if(temp118 !== null) temp118.unsubscribe();
            }})()
    }, undefined);
    
    ioReactivexDisposablesDisposableSolvePrivateDisposal(disp, [referenceA, referenceB, referenceC]);
    disposable = disp;
    return disp;
}

