// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: views/SelectMultipleDatesMonthCVD.shared.kt
// Package: com.lightningkite.khrysalis.views
import { DisplayMetrics } from './DisplayMetrics.actual'
import { EqualOverrideSet, iterFirstOrNull } from '../KotlinCollections'
import { CalendarDrawing, MonthCVD } from './MonthCVD.shared'
import { Paint } from './draw/Paint.actual'
import { filter as iterFilter, some as iterSome, toArray as iterToArray } from '../kotlin/lazyOp'
import { comLightningkiteKhrysalisTimeDateAloneSet } from '../time/DateAlone.shared'
import { SubscriptionLike } from 'rxjs'
import { DateAlone } from '../time/DateAlone.actual'
import { copyDateAloneMod, dateAloneModRelative } from '../time/Date.actual'
import { safeEq } from '../Kotlin'
import { StandardObservableProperty } from '../observables/StandardObservableProperty.shared'
import { runOrNull } from '../kotlin/Language'
import { RectF } from './geometry/RectF.actual'
import { ioReactivexDisposablesDisposableForever } from '../rx/DisposeCondition.actual'

//! Declares com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD
export class SelectMultipleDatesMonthCVD extends MonthCVD {
    public constructor() {
        super();
        this.dates = new StandardObservableProperty<Set<DateAlone>>(new EqualOverrideSet([]), undefined);
        this.selectedDayPaint = new Paint();
        this.selectedPaint = new Paint();
        const it_350 = iterFirstOrNull(this.dates.value);
        if (it_350 !== null) { 
            this.currentMonthObs.value = copyDateAloneMod(it_350, Date.prototype.setDate, 1);
        };
        ioReactivexDisposablesDisposableForever<SubscriptionLike>(this.dates.onChange.subscribe( (value: Set<DateAlone>): void => {
                    this?.invalidate();
        }, undefined, undefined));
        this.drawDay_dateAlone = new DateAlone(0, 0, 0);
        this.adding = false;
    }
    
    public generateAccessibilityView(): (HTMLElement | null) { 
        return null; 
    }
    
    public readonly dates: StandardObservableProperty<Set<DateAlone>>;
    
    public readonly selectedDayPaint: Paint;
    
    public readonly selectedPaint: Paint;
    
    
    
    
    public measure(width: number, height: number, displayMetrics: DisplayMetrics): void {
        super.measure(width, height, displayMetrics);
        this.selectedDayPaint.textSize = this.dayPaint.textSize;
    }
    
    public readonly drawDay_dateAlone: DateAlone;
    
    public drawDay(canvas: CanvasRenderingContext2D, showingMonth: DateAlone, day: DateAlone, displayMetrics: DisplayMetrics, outer: RectF, inner: RectF): void {
        if (this.dates.value.has(day)) {
            const leftDate = dateAloneModRelative(comLightningkiteKhrysalisTimeDateAloneSet(this.drawDay_dateAlone, day), Date.prototype.getDate, Date.prototype.setDate, (-1));
            
            const left = this.dates.value.has(leftDate);
            
            const rightDate = dateAloneModRelative(comLightningkiteKhrysalisTimeDateAloneSet(this.drawDay_dateAlone, day), Date.prototype.getDate, Date.prototype.setDate, 1);
            
            const right = this.dates.value.has(rightDate);
            
            
            if ((!left) && (!right)){
                CalendarDrawing.INSTANCE.dayBackground(canvas, inner, this.selectedPaint);
            } else if ((!left) && right){
                CalendarDrawing.INSTANCE.dayBackgroundStart(canvas, inner, outer, this.selectedPaint);
            } else if (left && (!right)){
                CalendarDrawing.INSTANCE.dayBackgroundEnd(canvas, inner, outer, this.selectedPaint);
            } else if (left && right){
                CalendarDrawing.INSTANCE.dayBackgroundMid(canvas, inner, outer, this.selectedPaint);
            } else {
                CalendarDrawing.INSTANCE.dayBackground(canvas, inner, this.selectedPaint);
            }
            CalendarDrawing.INSTANCE.day(canvas, showingMonth, day, inner, this.selectedDayPaint);
        } else {
            CalendarDrawing.INSTANCE.day(canvas, showingMonth, day, inner, this.dayPaint);
        }
    }
    
    public onTap(day: DateAlone): void {
        this.adding = (!iterSome(this.dates.value, (it: DateAlone): boolean => safeEq(day, it)));
        this.onTouchMoveDate(day);
    }
    
    public adding: boolean;
    
    public onTouchDownDate(day: DateAlone): boolean {
        this.adding = (!iterSome(this.dates.value, (it: DateAlone): boolean => safeEq(day, it)));
        this.onTouchMoveDate(day);
        return true;
    }
    
    public onTouchMoveDate(day: DateAlone): boolean {
        if (this.adding) {
            if ((!iterSome(this.dates.value, (it: DateAlone): boolean => safeEq(day, it)))) {
                this.dates.value = new EqualOverrideSet([...this.dates.value, day]);
            }
        } else {
            this.dates.value = new EqualOverrideSet(iterToArray(iterFilter(this.dates.value, (it: DateAlone): boolean => !safeEq(it, day))));
        }
        return true;
    }
    
    public onTouchUpDate(day: DateAlone): boolean {
        return true;
    }
}

