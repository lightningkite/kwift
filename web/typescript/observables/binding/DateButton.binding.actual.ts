// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: observables/binding/DateButton.binding.actual.kt
// Package: com.lightningkite.khrysalis.observables.binding
// FQImport: java.util.Date TS Date
// FQImport: com.lightningkite.khrysalis.time.dateAlone TS getJavaUtilDateDateAlone
// FQImport: com.lightningkite.khrysalis.time.DateAlone TS DateAlone
// FQImport: com.lightningkite.khrysalis.observables.binding.bindDateAlone.date TS date
// FQImport: com.lightningkite.khrysalis.rx.until TS ioReactivexDisposablesDisposableUntil
// FQImport: io.reactivex.subjects.PublishSubject.subscribe TS subscribe
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty.value TS value
// FQImport: com.lightningkite.khrysalis.time.TimeAlone TS TimeAlone
// FQImport: com.lightningkite.khrysalis.observables.binding.bindDateAlone.<anonymous>.it TS it
// FQImport: com.lightningkite.khrysalis.time.timeAlone TS getJavaUtilDateTimeAlone
// FQImport: com.lightningkite.khrysalis.views.android.DateButton.onDateEntered TS onDateEntered
// FQImport: com.lightningkite.khrysalis.observables.binding.bindTimeAlone.date TS date
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.minuteInterval TS minuteInterval
// FQImport: com.lightningkite.khrysalis.observables.subscribeBy TS comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy
// FQImport: com.lightningkite.khrysalis.rx.removed TS getAndroidViewViewRemoved
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.date TS date
// FQImport: com.lightningkite.khrysalis.views.android.TimeButton.onDateEntered TS onDateEntered
// FQImport: com.lightningkite.khrysalis.time.dateFrom TS dateFrom
// FQImport: com.lightningkite.khrysalis.views.android.TimeButton.minuteInterval TS minuteInterval
// FQImport: com.lightningkite.khrysalis.observables.binding.bindTimeAlone.<anonymous>.it TS it
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty TS MutableObservableProperty
// FQImport: com.lightningkite.khrysalis.views.android.TimeButton.date TS date
// FQImport: com.lightningkite.khrysalis.observables.binding.bindTimeAlone.minuteInterval TS minuteInterval
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.<anonymous>.it TS it
import { comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy } from './../ObservableProperty.ext.shared'
import { MutableObservableProperty } from './../MutableObservableProperty.shared'
import { getAndroidViewViewRemoved, ioReactivexDisposablesDisposableUntil } from './../../rx/DisposeCondition.actual'
import { dateFrom, getJavaUtilDateDateAlone, getJavaUtilDateTimeAlone } from './../../time/Date.actual'
import { DateAlone } from './../../time/DateAlone.actual'
import { SubscriptionLike } from 'rxjs'
import { TimeAlone } from './../../time/TimeAlone.actual'

//! Declares com.lightningkite.khrysalis.observables.binding.bind
export function comLightningkiteKhrysalisViewsAndroidDateButtonBind(this_: HTMLInputElement, date: MutableObservableProperty<Date>): SubscriptionLike{
    ioReactivexDisposablesDisposableUntil(comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy(date, undefined, undefined, (it) => {
                this_.valueAsDate = it
    }), getAndroidViewViewRemoved(this_));
    ioReactivexDisposablesDisposableUntil(this_.onDateEntered.subscribe((it) => {
                date.value = it
    }), getAndroidViewViewRemoved(this_));
}


//! Declares com.lightningkite.khrysalis.observables.binding.bind
export function comLightningkiteKhrysalisViewsAndroidTimeButtonBind(this_: HTMLInputElement, date: MutableObservableProperty<Date>, minuteInterval: number = 1): SubscriptionLike{
    this_.minuteInterval = minuteInterval;
    ioReactivexDisposablesDisposableUntil(comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy(date, undefined, undefined, (it) => {
                this_.date = it
    }), getAndroidViewViewRemoved(this_));
    ioReactivexDisposablesDisposableUntil(this_.onDateEntered.subscribe((it) => {
                date.value = it
    }), getAndroidViewViewRemoved(this_));
}


//! Declares com.lightningkite.khrysalis.observables.binding.bindDateAlone
export function comLightningkiteKhrysalisViewsAndroidDateButtonBindDateAlone(this_: HTMLInputElement, date: MutableObservableProperty<DateAlone>): SubscriptionLike{
    ioReactivexDisposablesDisposableUntil(comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy(date, undefined, undefined, (it) => {
                this_.valueAsDate = dateFrom(it, getJavaUtilDateTimeAlone(Date.constructor()), undefined)
    }), getAndroidViewViewRemoved(this_));
    ioReactivexDisposablesDisposableUntil(this_.onDateEntered.subscribe((it) => {
                date.value = getJavaUtilDateDateAlone(it)
    }), getAndroidViewViewRemoved(this_));
}


//! Declares com.lightningkite.khrysalis.observables.binding.bindTimeAlone
export function comLightningkiteKhrysalisViewsAndroidTimeButtonBindTimeAlone(this_: HTMLInputElement, date: MutableObservableProperty<TimeAlone>, minuteInterval: number = 1): SubscriptionLike{
    this_.minuteInterval = minuteInterval;
    ioReactivexDisposablesDisposableUntil(comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy(date, undefined, undefined, (it) => {
                this_.date = dateFrom(getJavaUtilDateDateAlone(Date.constructor()), it, undefined)
    }), getAndroidViewViewRemoved(this_));
    ioReactivexDisposablesDisposableUntil(this_.onDateEntered.subscribe((it) => {
                date.value = getJavaUtilDateTimeAlone(it)
    }), getAndroidViewViewRemoved(this_));
}

