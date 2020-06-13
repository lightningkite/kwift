"use strict";
var _a;
Object.defineProperty(exports, "__esModule", { value: true });
// Generated by Khrysalis TypeScript converter
// File: time/DateAlone.actual.kt
// Package: com.lightningkite.khrysalis.time
const Date_actual_1 = require("./Date.actual");
const ClockPartSize_shared_1 = require("./ClockPartSize.shared");
const Kotlin_1 = require("../Kotlin");
const TimeAlone_actual_1 = require("./TimeAlone.actual");
let usableDate = new Date();
//! Declares com.lightningkite.khrysalis.time.DateAlone
class DateAlone {
    constructor(year, month, day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
    hashCode() {
        let hash = 17;
        hash = 31 * hash + this.year;
        hash = 31 * hash + this.month;
        hash = 31 * hash + this.day;
        return hash;
    }
    equals(other) { return other instanceof DateAlone && this.year === other.year && this.month === other.month && this.day === other.day; }
    toString() { return `DateAlone(year = ${this.year}, month = ${this.month}, day = ${this.day})`; }
    copy(year = this.year, month = this.month, day = this.day) { return new DateAlone(year, month, day); }
    //! Declares com.lightningkite.khrysalis.time.DateAlone.monthInEra
    get monthInEra() { return this.year * 12 + this.month; }
    //! Declares com.lightningkite.khrysalis.time.DateAlone.comparable
    get comparable() { return this.year * 12 * 31 + this.month * 31 + this.day; }
    //! Declares com.lightningkite.khrysalis.time.DateAlone.dayOfWeek
    get dayOfWeek() {
        usableDate.setFullYear(this.year, this.month - 1, this.day);
        return usableDate.getDay() + 1;
    }
}
exports.DateAlone = DateAlone;
DateAlone.Companion = (_a = class Companion {
        constructor() {
            this.farPast = new DateAlone(-99999, 1, 1);
            this.farFuture = new DateAlone(99999, 12, 31);
            this.farPast = new DateAlone(-99999, 1, 1);
            this.farFuture = new DateAlone(99999, 12, 31);
        }
        now() { return Date_actual_1.getJavaUtilDateDateAlone(Date.constructor()); }
        iso(string) {
            const parts = string.split('-');
            const year = Kotlin_1.parseIntOrNull(parts[0]);
            if (year === null)
                return null;
            const month = Kotlin_1.parseIntOrNull(parts[1]);
            if (month === null)
                return null;
            let day = Kotlin_1.parseIntOrNull(parts[2]);
            if (day === null)
                day = 0;
            return new DateAlone(year, month, day);
        }
        fromMonthInEra(monthInEra) {
            return new DateAlone((monthInEra - 1) / 12, (monthInEra - 1) % 12 + 1, 1);
        }
    },
    _a.INSTANCE = new _a(),
    _a);
//! Declares com.lightningkite.khrysalis.time.setDayOfWeek
function comLightningkiteKhrysalisTimeDateAloneSetDayOfWeek(this_, value) {
    usableDate.setFullYear(this_.year, this_.month, this_.day);
    this_.year = usableDate.getFullYear();
    this_.month = usableDate.getMonth();
    this_.day = usableDate.getDate();
    return this;
}
exports.comLightningkiteKhrysalisTimeDateAloneSetDayOfWeek = comLightningkiteKhrysalisTimeDateAloneSetDayOfWeek;
//! Declares com.lightningkite.khrysalis.time.setAddDayOfWeek
function comLightningkiteKhrysalisTimeDateAloneSetAddDayOfWeek(this_, value) {
    usableDate.setFullYear(this_.year, this_.month, this_.day + value);
    this_.year = usableDate.getFullYear();
    this_.month = usableDate.getMonth();
    this_.day = usableDate.getDate();
    return this;
}
exports.comLightningkiteKhrysalisTimeDateAloneSetAddDayOfWeek = comLightningkiteKhrysalisTimeDateAloneSetAddDayOfWeek;
//! Declares com.lightningkite.khrysalis.time.dayOfWeek
function comLightningkiteKhrysalisTimeDateAloneDayOfWeek(this_, value) {
    return comLightningkiteKhrysalisTimeDateAloneSetDayOfWeek(this_.copy(), value);
}
exports.comLightningkiteKhrysalisTimeDateAloneDayOfWeek = comLightningkiteKhrysalisTimeDateAloneDayOfWeek;
//! Declares com.lightningkite.khrysalis.time.addDayOfWeek
function comLightningkiteKhrysalisTimeDateAloneAddDayOfWeek(this_, value) {
    return comLightningkiteKhrysalisTimeDateAloneSetAddDayOfWeek(this_.copy(), value);
}
exports.comLightningkiteKhrysalisTimeDateAloneAddDayOfWeek = comLightningkiteKhrysalisTimeDateAloneAddDayOfWeek;
//! Declares com.lightningkite.khrysalis.time.iso8601
function comLightningkiteKhrysalisTimeDateAloneIso8601(this_) {
    return `${this.year.toString().padStart(4, "0")}-${this.month.toString().padStart(2, "0")}-${this.day.toString().padStart(2, "0")}`;
}
exports.comLightningkiteKhrysalisTimeDateAloneIso8601 = comLightningkiteKhrysalisTimeDateAloneIso8601;
//! Declares com.lightningkite.khrysalis.time.formatYearless
function comLightningkiteKhrysalisTimeDateAloneFormatYearless(this_, clockPartSize) {
    let dateFormat = {};
    switch (clockPartSize) {
        case ClockPartSize_shared_1.ClockPartSize.None:
            break;
        case ClockPartSize_shared_1.ClockPartSize.Short:
            dateFormat.month = "numeric";
            dateFormat.day = "numeric";
            break;
        case ClockPartSize_shared_1.ClockPartSize.Medium:
            dateFormat.month = "short";
            dateFormat.day = "numeric";
            break;
        case ClockPartSize_shared_1.ClockPartSize.Long:
            dateFormat.month = "long";
            dateFormat.day = "numeric";
            break;
        case ClockPartSize_shared_1.ClockPartSize.Full:
            dateFormat.month = "long";
            dateFormat.day = "numeric";
            dateFormat.weekday = "long";
            dateFormat.era = "short";
            break;
    }
    return Date_actual_1.dateFrom(this_, TimeAlone_actual_1.TimeAlone.Companion.INSTANCE.noon, usableDate).toLocaleString(undefined, dateFormat);
}
exports.comLightningkiteKhrysalisTimeDateAloneFormatYearless = comLightningkiteKhrysalisTimeDateAloneFormatYearless;