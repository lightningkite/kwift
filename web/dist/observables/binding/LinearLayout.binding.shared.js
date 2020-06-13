"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
// Generated by Khrysalis TypeScript converter
// File: observables/binding/LinearLayout.binding.shared.kt
// Package: com.lightningkite.khrysalis.observables.binding
const ObservableProperty_ext_shared_1 = require("../ObservableProperty.ext.shared");
const DisposeCondition_actual_1 = require("../../rx/DisposeCondition.actual");
const StandardObservableProperty_shared_1 = require("../StandardObservableProperty.shared");
const LinearLayout_actual_1 = require("../../views/LinearLayout.actual");
const Align_shared_1 = require("../../views/geometry/Align.shared");
const Kotlin_1 = require("Kotlin");
const iterable_operator_1 = require("iterable-operator");
//! Declares com.lightningkite.khrysalis.observables.binding.LinearLayoutBoundSubview
class LinearLayoutBoundSubview {
    constructor(view, property) {
        this.view = view;
        this.property = property;
    }
}
//! Declares com.lightningkite.khrysalis.observables.binding.bind>android.widget.LinearLayout
function androidWidgetLinearLayoutBind(this_, data, defaultValue, makeView) {
    const existingViews = [];
    DisposeCondition_actual_1.ioReactivexDisposablesDisposableUntil(ObservableProperty_ext_shared_1.comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy(data, undefined, undefined, (value) => {
        //Fix view count
        const excessViews = existingViews.length - value.length;
        if (excessViews > 0) {
            //remove views
            for (const iter of new Kotlin_1.NumberRange(1, excessViews)) {
                const old = existingViews.splice((existingViews.length - 1), 1)[0];
                this_.removeChild(old.view);
            }
        }
        else if (existingViews.length < value.length) {
            //add views
            for (const iter of new Kotlin_1.NumberRange(1, (-excessViews))) {
                const prop = new StandardObservableProperty_shared_1.StandardObservableProperty(defaultValue, undefined);
                const view = makeView(prop);
                this_.appendChild(LinearLayout_actual_1.androidWidgetLinearLayoutParams(this_, undefined, undefined, undefined, undefined, undefined, undefined, Align_shared_1.AlignPair.Companion.INSTANCE.centerFill, undefined)(view));
                existingViews.push(new LinearLayoutBoundSubview(view, prop));
            }
        }
        //Update views
        for (const index of iterable_operator_1.range(0, value.length - 1)) {
            existingViews[index].property.value = value[index];
        }
    }), DisposeCondition_actual_1.getAndroidViewViewRemoved(this_));
}
exports.androidWidgetLinearLayoutBind = androidWidgetLinearLayoutBind;