"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
// Generated by Khrysalis TypeScript converter
// File: observables/binding/DateButton.binding.actual.kt
// Package: com.lightningkite.khrysalis.observables.binding
const ObservableProperty_ext_shared_1 = require("../ObservableProperty.ext.shared");
const Date_actual_1 = require("../../time/Date.actual");
const DisposeCondition_actual_1 = require("../../rx/DisposeCondition.actual");
const TimeAlone_actual_1 = require("../../time/TimeAlone.actual");
const DateAlone_actual_1 = require("../../time/DateAlone.actual");
//! Declares com.lightningkite.khrysalis.observables.binding.bind>com.lightningkite.khrysalis.views.android.DateButton
function comLightningkiteKhrysalisViewsAndroidDateButtonBind(this_, date) {
    let suppress = false;
    DisposeCondition_actual_1.ioReactivexDisposablesDisposableUntil(ObservableProperty_ext_shared_1.comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy(date, undefined, undefined, (it) => {
        if (suppress)
            return;
        suppress = true;
        const ta = Date_actual_1.getJavaUtilDateDateAlone(it);
        this_.value = DateAlone_actual_1.comLightningkiteKhrysalisTimeDateAloneIso8601(ta);
        suppress = false;
    }), DisposeCondition_actual_1.getAndroidViewViewRemoved(this_));
    this_.onchange = (e) => {
        if (suppress)
            return;
        suppress = true;
        try {
            const d = DateAlone_actual_1.DateAlone.Companion.INSTANCE.iso(this_.value);
            const current = date.value;
            Date_actual_1.javaUtilDateSet(current, d);
            date.value = current;
        }
        catch (e) {
            console.warn("Failed to parse " + this_.value);
            console.warn(e);
        }
        suppress = false;
    };
}
exports.comLightningkiteKhrysalisViewsAndroidDateButtonBind = comLightningkiteKhrysalisViewsAndroidDateButtonBind;
//! Declares com.lightningkite.khrysalis.observables.binding.bind>com.lightningkite.khrysalis.views.android.TimeButton
function comLightningkiteKhrysalisViewsAndroidTimeButtonBind(this_, date, minuteInterval = 1) {
    let suppress = false;
    DisposeCondition_actual_1.ioReactivexDisposablesDisposableUntil(ObservableProperty_ext_shared_1.comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy(date, undefined, undefined, (it) => {
        if (suppress)
            return;
        suppress = true;
        const ta = Date_actual_1.getJavaUtilDateTimeAlone(it);
        this_.value = TimeAlone_actual_1.comLightningkiteKhrysalisTimeTimeAloneIso8601(ta);
        suppress = false;
    }), DisposeCondition_actual_1.getAndroidViewViewRemoved(this_));
    this_.onchange = (e) => {
        if (suppress)
            return;
        suppress = true;
        try {
            const d = TimeAlone_actual_1.TimeAlone.Companion.INSTANCE.iso(this_.value);
            const current = date.value;
            Date_actual_1.javaUtilDateSet(current, d);
            date.value = current;
        }
        catch (e) {
            console.warn("Failed to parse " + this_.value);
            console.warn(e);
        }
        suppress = false;
    };
}
exports.comLightningkiteKhrysalisViewsAndroidTimeButtonBind = comLightningkiteKhrysalisViewsAndroidTimeButtonBind;
//! Declares com.lightningkite.khrysalis.observables.binding.bindDateAlone>com.lightningkite.khrysalis.views.android.DateButton
function comLightningkiteKhrysalisViewsAndroidDateButtonBindDateAlone(this_, date) {
    let suppress = false;
    DisposeCondition_actual_1.ioReactivexDisposablesDisposableUntil(ObservableProperty_ext_shared_1.comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy(date, undefined, undefined, (it) => {
        if (suppress)
            return;
        suppress = true;
        this_.value = DateAlone_actual_1.comLightningkiteKhrysalisTimeDateAloneIso8601(it);
        suppress = false;
    }), DisposeCondition_actual_1.getAndroidViewViewRemoved(this_));
    this_.onchange = (e) => {
        if (suppress)
            return;
        suppress = true;
        try {
            const d = DateAlone_actual_1.DateAlone.Companion.INSTANCE.iso(this_.value);
            date.value = d;
        }
        catch (e) {
            console.warn("Failed to parse " + this_.value);
            console.warn(e);
        }
        suppress = false;
    };
}
exports.comLightningkiteKhrysalisViewsAndroidDateButtonBindDateAlone = comLightningkiteKhrysalisViewsAndroidDateButtonBindDateAlone;
//! Declares com.lightningkite.khrysalis.observables.binding.bindTimeAlone>com.lightningkite.khrysalis.views.android.TimeButton
function comLightningkiteKhrysalisViewsAndroidTimeButtonBindTimeAlone(this_, date, minuteInterval = 1) {
    let suppress = false;
    DisposeCondition_actual_1.ioReactivexDisposablesDisposableUntil(ObservableProperty_ext_shared_1.comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy(date, undefined, undefined, (it) => {
        if (suppress)
            return;
        suppress = true;
        this_.value = TimeAlone_actual_1.comLightningkiteKhrysalisTimeTimeAloneIso8601(it);
        suppress = false;
    }), DisposeCondition_actual_1.getAndroidViewViewRemoved(this_));
    this_.onchange = (e) => {
        if (suppress)
            return;
        suppress = true;
        try {
            const d = TimeAlone_actual_1.TimeAlone.Companion.INSTANCE.iso(this_.value);
            date.value = d;
        }
        catch (e) {
            console.warn("Failed to parse " + this_.value);
            console.warn(e);
        }
        suppress = false;
    };
}
exports.comLightningkiteKhrysalisViewsAndroidTimeButtonBindTimeAlone = comLightningkiteKhrysalisViewsAndroidTimeButtonBindTimeAlone;
//# sourceMappingURL=DateButton.binding.actual.js.map