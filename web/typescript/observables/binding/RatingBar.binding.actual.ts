// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: observables/binding/RatingBar.binding.actual.kt
// Package: com.lightningkite.khrysalis.observables.binding
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty.value TS value
// FQImport: android.widget.RatingBar.OnRatingBarChangeListener TS RatingBarOnRatingBarChangeListener
// FQImport: com.lightningkite.khrysalis.observables.subscribeBy>com.lightningkite.khrysalis.observables.ObservableProperty<kotlin.Any> TS comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.<no name provided>.onRatingChanged.p0 TS p0
// FQImport: com.lightningkite.khrysalis.observables.binding.bindFloat.stars TS stars
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.stars TS stars
// FQImport: android.widget.RatingBar.setIsIndicator TS setIsIndicator
// FQImport: android.widget.RatingBar.onRatingBarChangeListener TS onRatingBarChangeListener
// FQImport: com.lightningkite.khrysalis.rx.until>io.reactivex.disposables.Disposable TS ioReactivexDisposablesDisposableUntil
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.observable TS observable
// FQImport: com.lightningkite.khrysalis.observables.binding.bindFloat.suppress TS suppress
// FQImport: android.widget.RatingBar.incrementProgressBy TS incrementProgressBy
// FQImport: android.widget.RatingBar.rating TS rating
// FQImport: android.widget.RatingBar.progress TS progress
// FQImport: com.lightningkite.khrysalis.observables.binding.bindFloat.observable TS observable
// FQImport: android.widget.RatingBar.numStars TS numStars
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.<anonymous>.value TS value
// FQImport: android.widget.RatingBar.max TS max
// FQImport: android.widget.RatingBar.stepSize TS stepSize
// FQImport: com.lightningkite.khrysalis.observables.ObservableProperty TS ObservableProperty
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.suppress TS suppress
// FQImport: com.lightningkite.khrysalis.observables.binding.bindFloat.<no name provided>.onRatingChanged.p1 TS p1
// FQImport: com.lightningkite.khrysalis.rx.removed>android.view.View TS getAndroidViewViewRemoved
// FQImport: com.lightningkite.khrysalis.observables.binding.bindFloat.<anonymous>.value TS value
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty TS MutableObservableProperty
import { comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy } from './../ObservableProperty.ext.shared'
import { MutableObservableProperty } from './../MutableObservableProperty.shared'
import { getAndroidViewViewRemoved, ioReactivexDisposablesDisposableUntil } from './../../rx/DisposeCondition.actual'
import { ObservableProperty } from './../ObservableProperty.shared'
import { SubscriptionLike } from 'rxjs'

//! Declares com.lightningkite.khrysalis.observables.binding.bind>android.widget.RatingBar
export function androidWidgetRatingBarBind(this_: HTML_Element, stars: number, observable: MutableObservableProperty<number>): void{
    this_.max = stars;
    this_.numStars = stars;
    this_.incrementProgressBy(1);
    
    let suppress = false;
    
    ioReactivexDisposablesDisposableUntil(comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy(observable, undefined, undefined, (value) => {
                if (!suppress) {
                    suppress = true;
                    this_.progress = value;
                    suppress = false;
                }
    }), getAndroidViewViewRemoved(this_));
    this_.onRatingBarChangeListener = new class Anon implements RatingBarOnRatingBarChangeListener {
        public static implementsInterfaceAndroidWidgetRatingBarOnRatingBarChangeListener = true;
        public constructor() {
        }
        
        onRatingChanged(p0: HTML_Element, p1: number, p2: boolean): void{
            if (!suppress) {
                suppress = true;
                observable.value = p0.progress;
                suppress = false;
            }
        }
    }();
    
}

//! Declares com.lightningkite.khrysalis.observables.binding.bind>android.widget.RatingBar
export function androidWidgetRatingBarBind(this_: HTML_Element, stars: number, observable: ObservableProperty<number>): SubscriptionLike{
    this_.max = stars;
    this_.numStars = stars;
    this_.setIsIndicator(true);
    
    ioReactivexDisposablesDisposableUntil(comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy(observable, undefined, undefined, (value) => {
                this_.progress = value
    }), getAndroidViewViewRemoved(this_));
}



//! Declares com.lightningkite.khrysalis.observables.binding.bindFloat>android.widget.RatingBar
export function androidWidgetRatingBarBindFloat(this_: HTML_Element, stars: number, observable: MutableObservableProperty<number>): void{
    this_.numStars = stars;
    this_.stepSize = 0.01;
    
    let suppress = false;
    
    ioReactivexDisposablesDisposableUntil(comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy(observable, undefined, undefined, (value) => {
                if (!suppress) {
                    suppress = true;
                    this_.rating = value;
                    suppress = false;
                }
    }), getAndroidViewViewRemoved(this_));
    this_.onRatingBarChangeListener = new class Anon implements RatingBarOnRatingBarChangeListener {
        public static implementsInterfaceAndroidWidgetRatingBarOnRatingBarChangeListener = true;
        public constructor() {
        }
        
        onRatingChanged(p0: HTML_Element, p1: number, p2: boolean): void{
            if (!suppress) {
                suppress = true;
                observable.value = p1;
                suppress = false;
            }
        }
    }();
    
}


//! Declares com.lightningkite.khrysalis.observables.binding.bindFloat>android.widget.RatingBar
export function androidWidgetRatingBarBindFloat(this_: HTML_Element, stars: number, observable: ObservableProperty<number>): SubscriptionLike{
    this_.numStars = stars;
    this_.setIsIndicator(true);
    
    ioReactivexDisposablesDisposableUntil(comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy(observable, undefined, undefined, (value) => {
                this_.rating = value
    }), getAndroidViewViewRemoved(this_));
}

