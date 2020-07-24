// Generated by Khrysalis TypeScript converter
// File: time/DateAlone.actual.kt
// Package: com.lightningkite.khrysalis.time
import { dateFrom, getJavaUtilDateDateAlone } from './Date.actual'
import { ClockPartSize } from './ClockPartSize.shared'
import {parseIntOrNull} from "../Kotlin";
import {TimeAlone} from "./TimeAlone.actual";

let usableDate = new Date();

//! Declares com.lightningkite.khrysalis.time.DateAlone
export class DateAlone {
    public year: number;
    public month: number;
    public day: number;
    public constructor(year: number, month: number, day: number) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
    public hashCode(): number {
        let hash = 17;
        hash = 31 * hash + this.year;
        hash = 31 * hash + this.month;
        hash = 31 * hash + this.day;
        return hash;
    }
    public equals(other: any): boolean { return other instanceof DateAlone && this.year === other.year && this.month === other.month && this.day === other.day }
    public toString(): string { return `DateAlone(year = ${this.year}, month = ${this.month}, day = ${this.day})` }
    public copy(year: number = this.year, month: number = this.month, day: number = this.day) { return new DateAlone(year, month, day); }

    public toJSON(): any {
        return comLightningkiteKhrysalisTimeDateAloneIso8601(this)
    }
    
    public static Companion = class Companion {
        private constructor() {
            this.farPast = new DateAlone(-99999, 1, 1);
            this.farFuture = new DateAlone(99999, 12, 31);
        }
        public static INSTANCE = new Companion();
        
        public now(): DateAlone{ return getJavaUtilDateDateAlone(new Date()); }
        public readonly farPast = new DateAlone(-99999, 1, 1);
        
        public readonly farFuture = new DateAlone(99999, 12, 31);
        
        public iso(string: string): (DateAlone | null){
            const parts = string.split('-');
            const year = parseIntOrNull(parts[0]);
            if(year === null) return null;
            const month = parseIntOrNull(parts[1]);
            if(month === null) return null;
            let day = parseIntOrNull(parts[2]);
            if(day === null) day = 0;
            return new DateAlone(year, month, day);
        }
        public fromMonthInEra(monthInEra: number): DateAlone{
            return new DateAlone((monthInEra - 1) / 12, (monthInEra - 1) % 12 + 1, 1);
        }
    }
    //! Declares com.lightningkite.khrysalis.time.DateAlone.monthInEra
    public get monthInEra(): number { return this.year * 12 + this.month; }
    
    //! Declares com.lightningkite.khrysalis.time.DateAlone.comparable
    public get comparable(): number { return this.year * 12 * 31 + this.month * 31 + this.day; }
    
    //! Declares com.lightningkite.khrysalis.time.DateAlone.dayOfWeek
    public get dayOfWeek(): number {
        usableDate.setFullYear(this.year, this.month - 1, this.day);
        return usableDate.getDay() + 1;
    }
    
}

//! Declares com.lightningkite.khrysalis.time.setDayOfWeek
export function comLightningkiteKhrysalisTimeDateAloneSetDayOfWeek(this_: DateAlone, value: number): DateAlone{
    usableDate.setFullYear(this_.year, this_.month, this_.day);
    this_.year = usableDate.getFullYear();
    this_.month = usableDate.getMonth();
    this_.day = usableDate.getDate();
    return this;
}

//! Declares com.lightningkite.khrysalis.time.setAddDayOfWeek
export function comLightningkiteKhrysalisTimeDateAloneSetAddDayOfWeek(this_: DateAlone, value: number): DateAlone{
    usableDate.setFullYear(this_.year, this_.month, this_.day + value);
    this_.year = usableDate.getFullYear();
    this_.month = usableDate.getMonth();
    this_.day = usableDate.getDate();
    return this;
}

//! Declares com.lightningkite.khrysalis.time.dayOfWeek
export function comLightningkiteKhrysalisTimeDateAloneDayOfWeek(this_: DateAlone, value: number): DateAlone {
    return comLightningkiteKhrysalisTimeDateAloneSetDayOfWeek(this_.copy(), value);
}
//! Declares com.lightningkite.khrysalis.time.addDayOfWeek
export function comLightningkiteKhrysalisTimeDateAloneAddDayOfWeek(this_: DateAlone, value: number): DateAlone {
    return comLightningkiteKhrysalisTimeDateAloneSetAddDayOfWeek(this_.copy(), value);
}

//! Declares com.lightningkite.khrysalis.time.iso8601
export function comLightningkiteKhrysalisTimeDateAloneIso8601(this_: DateAlone): string{
    return `${this.year.toString().padStart(4, "0")}-${this.month.toString().padStart(2, "0")}-${this.day.toString().padStart(2, "0")}`
}

//! Declares com.lightningkite.khrysalis.time.formatYearless
export function comLightningkiteKhrysalisTimeDateAloneFormatYearless(this_: DateAlone, clockPartSize: ClockPartSize): string{
    let dateFormat: Intl.DateTimeFormatOptions = {}

    switch (clockPartSize) {
        case ClockPartSize.None:
            break
        case ClockPartSize.Short:
            dateFormat.month = "numeric";
            dateFormat.day = "numeric";
            break
        case ClockPartSize.Medium:
            dateFormat.month = "short";
            dateFormat.day = "numeric";
            break
        case ClockPartSize.Long:
            dateFormat.month = "long";
            dateFormat.day = "numeric";
            break
        case ClockPartSize.Full:
            dateFormat.month = "long";
            dateFormat.day = "numeric";
            dateFormat.weekday = "long";
            dateFormat.era = "short";
            break
    }
    return dateFrom(this_, TimeAlone.Companion.INSTANCE.noon, usableDate).toLocaleString(undefined, dateFormat);
}

