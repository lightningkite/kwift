// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: views/SelectDateMonthCVD.shared.kt
// Package: com.lightningkite.khrysalis.views
// FQImport: com.lightningkite.khrysalis.time.DateAlone TS DateAlone
// FQImport: android.util.DisplayMetrics TS DisplayMetrics
// FQImport: com.lightningkite.khrysalis.views.SelectDateMonthCVD.measure.width TS width
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty.value TS value
// FQImport: com.lightningkite.khrysalis.views.SelectDateMonthCVD.drawDay.canvas TS canvas
// FQImport: com.lightningkite.khrysalis.views.SelectDateMonthCVD.onTap.day TS day
// FQImport: android.graphics.Paint.textSize TS getAndroidGraphicsPaintTextSize
// FQImport: com.lightningkite.khrysalis.views.SelectDateMonthCVD.drawDay.inner TS inner
// FQImport: com.lightningkite.khrysalis.views.SelectDateMonthCVD.dayPaint TS dayPaint
// FQImport: com.lightningkite.khrysalis.views.SelectDateMonthCVD.measure.height TS height
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.dayBackground TS dayBackground
// FQImport: com.lightningkite.khrysalis.views.SelectDateMonthCVD.selectedDayPaint TS selectedDayPaint
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.measure TS measure
// FQImport: com.lightningkite.khrysalis.views.SelectDateMonthCVD.drawDay.day TS day
// FQImport: com.lightningkite.khrysalis.views.MonthCVD TS MonthCVD
// FQImport: com.lightningkite.khrysalis.views.SelectDateMonthCVD.selected TS selected
// FQImport: com.lightningkite.khrysalis.views.SelectDateMonthCVD.drawDay.showingMonth TS showingMonth
// FQImport: android.graphics.Paint TS Paint
// FQImport: com.lightningkite.khrysalis.views.SelectDateMonthCVD.measure.displayMetrics TS displayMetrics
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.day TS day
// FQImport: android.graphics.Paint.textSize TS setAndroidGraphicsPaintTextSize
// FQImport: android.graphics.RectF TS RectF
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty TS MutableObservableProperty
// FQImport: com.lightningkite.khrysalis.views.SelectDateMonthCVD.selectedPaint TS selectedPaint
import { MutableObservableProperty } from './../observables/MutableObservableProperty.shared'
import { DateAlone } from './../time/DateAlone.actual'
import { Paint } from './draw/Paint.actual'
import { MonthCVD } from './MonthCVD.shared'

//! Declares com.lightningkite.khrysalis.views.SelectDateMonthCVD
export class SelectDateMonthCVD extends MonthCVD {
    public constructor() { super(); }
    public generateAccessibilityView(): (HTMLElement | null){ return null; }
    
    public selected: MutableObservableProperty<(DateAlone | null)>;
    
    
    
    
    public readonly selectedDayPaint: Paint;
    
    public readonly selectedPaint: Paint;
    
    
    public drawDay(canvas: CanvasRenderingContext2D, showingMonth: DateAlone, day: DateAlone, displayMetrics: DisplayMetrics, outer: RectF, inner: RectF): void{
        if (day.equals(this.selected.value)){
            CalendarDrawing.INSTANCE.dayBackground(canvas, inner, this.selectedPaint);
            CalendarDrawing.INSTANCE.day(canvas, showingMonth, day, inner, this.selectedDayPaint);
        } else {
            CalendarDrawing.INSTANCE.day(canvas, showingMonth, day, inner, this.dayPaint);
        }
    }
    
    public measure(width: number, height: number, displayMetrics: DisplayMetrics): void{
        .measure(width, height, displayMetrics);
        setAndroidGraphicsPaintTextSize(this.selectedDayPaint, getAndroidGraphicsPaintTextSize(this.dayPaint));
    }
    
    public onTap(day: DateAlone): void{
        this.selected.value = day;
    }
}

