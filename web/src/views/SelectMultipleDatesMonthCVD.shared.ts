// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: views/SelectMultipleDatesMonthCVD.shared.kt
// Package: com.lightningkite.khrysalis.views
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.dayBackgroundEnd TS dayBackgroundEnd
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.<init>.<anonymous>.it TS it
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty.value TS value
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.dayBackgroundStart TS dayBackgroundStart
// FQImport: com.lightningkite.khrysalis.observables.StandardObservableProperty.onChange TS onChange
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.drawDay.showingMonth TS showingMonth
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.onTouchDown.<anonymous>.it TS it
// FQImport: android.graphics.Paint.textSize TS textSize
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.selectedPaint TS selectedPaint
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.onTap.<anonymous>.it TS it
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.drawDay.left TS left
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.drawDay_dateAlone TS drawDay_dateAlone
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.onTouchMove TS onTouchMoveDate
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.drawDay.outer TS outer
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.drawDay.leftDate TS leftDate
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.currentMonthObs TS currentMonthObs
// FQImport: kotlin.collections.Set TS Set
// FQImport: com.lightningkite.khrysalis.views.MonthCVD TS MonthCVD
// FQImport: com.lightningkite.khrysalis.time.set>com.lightningkite.khrysalis.time.DateAlone TS comLightningkiteKhrysalisTimeDateAloneSet
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.measure.height TS height
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.onTouchMove.<anonymous>.it TS it
// FQImport: android.graphics.RectF TS RectF
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.drawDay.canvas TS canvas
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.dates TS dates
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.onTap.day TS day
// FQImport: com.lightningkite.khrysalis.time.DateAlone TS DateAlone
// FQImport: android.util.DisplayMetrics TS DisplayMetrics
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.onTouchMove.day TS day
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.drawDay.inner TS inner
// FQImport: com.lightningkite.khrysalis.rx.forever>io.reactivex.disposables.Disposable TS ioReactivexDisposablesDisposableForever
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.selectedDayPaint TS selectedDayPaint
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.measure.displayMetrics TS displayMetrics
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.adding TS adding
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.invalidate TS invalidate
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.onTouchDown.day TS day
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.dayPaint TS dayPaint
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.drawDay.day TS day
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.drawDay.rightDate TS rightDate
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.dayBackground TS dayBackground
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.measure TS measure
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing TS CalendarDrawing
// FQImport: com.lightningkite.khrysalis.observables.StandardObservableProperty TS StandardObservableProperty
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.dayBackgroundMid TS dayBackgroundMid
// FQImport: android.graphics.Paint TS Paint
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.drawDay.right TS right
// FQImport: com.lightningkite.khrysalis.observables.StandardObservableProperty.value TS value
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.day TS day
// FQImport: com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD.measure.width TS width
import { DisplayMetrics } from './DisplayMetrics.actual'
import { CalendarDrawing, MonthCVD } from './MonthCVD.shared'
import { filter as iterFilter, first as iterFirst, some as iterSome, toArray as iterToArray, toSet as iterToSet } from 'iterable-operator'
import { Paint } from './draw/Paint.actual'
import { comLightningkiteKhrysalisTimeDateAloneSet } from '../time/DateAlone.shared'
import { DateAlone } from '../time/DateAlone.actual'
import { copyDateAloneMod, dateAloneModRelative } from '../time/Date.actual'
import { StandardObservableProperty } from '../observables/StandardObservableProperty.shared'
import { RectF } from './geometry/RectF.actual'
import { ioReactivexDisposablesDisposableForever } from '../rx/DisposeCondition.actual'

//! Declares com.lightningkite.khrysalis.views.SelectMultipleDatesMonthCVD
export class SelectMultipleDatesMonthCVD extends MonthCVD {
    public constructor() {
        super();
        this.dates = new StandardObservableProperty(new Set([]), undefined);
        this.selectedDayPaint = Paint.constructor();
        this.selectedPaint = Paint.constructor();
        const temp289 = (iterFirst(this.dates.value) ?? null);
        if(temp289 !== null) ((it) => this.currentMonthObs.value = copyDateAloneMod(it, Date.prototype.setDate, 1))(temp289);
        ioReactivexDisposablesDisposableForever(this.dates.onChange.subscribe( (value) => {
                    this?.invalidate()
        }, undefined, undefined));
        this.drawDay_dateAlone = new DateAlone(0, 0, 0);
        this.adding = false;
    }
    
    public generateAccessibilityView(): (HTMLElement | null) { return null; }
    
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
            const leftDate = dateAloneModRelative(comLightningkiteKhrysalisTimeDateAloneSet(this.drawDay_dateAlone, day), Date.prototype.getDate, Date.prototype.setDate, -1);
            
            const left = this.dates.value.has(leftDate);
            
            const rightDate = dateAloneModRelative(comLightningkiteKhrysalisTimeDateAloneSet(this.drawDay_dateAlone, day), Date.prototype.getDate, Date.prototype.setDate, 1);
            
            const right = this.dates.value.has(rightDate);
            
            
            if (!left && !right){
                CalendarDrawing.INSTANCE.dayBackground(canvas, inner, this.selectedPaint);
            } else if (!left && right){
                CalendarDrawing.INSTANCE.dayBackgroundStart(canvas, inner, outer, this.selectedPaint);
            } else if (left && !right){
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
        this.adding = (!iterSome(this.dates.value, (it) => day.equals(it)));
        this.onTouchMoveDate(day);
    }
    
    public adding: boolean;
    
    public onTouchDownDate(day: DateAlone): boolean {
        this.adding = (!iterSome(this.dates.value, (it) => day.equals(it)));
        this.onTouchMoveDate(day);
        return true;
    }
    
    public onTouchMoveDate(day: DateAlone): boolean {
        if (this.adding) {
            if ((!iterSome(this.dates.value, (it) => day.equals(it)))) {
                this.dates.value = new Set([...this.dates.value, day]);
            }
        } else {
            this.dates.value = iterToSet(iterToArray(iterFilter(this.dates.value, (it) => !(it.equals(day)))));
        }
        return true;
    }
    
    public onTouchUpDate(day: DateAlone): boolean {
        return true;
    }
}

