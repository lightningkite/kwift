// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: views/SelectDateRangeMonthCVD.shared.kt
// Package: com.lightningkite.khrysalis.views
// FQImport: kotlin.Boolean TS Boolean
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.dayBackgroundEnd TS dayBackgroundEnd
// FQImport: com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD.drawDay.day TS day
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty.value TS value
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.dayBackgroundStart TS dayBackgroundStart
// FQImport: com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD.dayPaint TS dayPaint
// FQImport: com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD.start TS start
// FQImport: com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD.measure.width TS width
// FQImport: com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD.measure.displayMetrics TS displayMetrics
// FQImport: com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD.draggingStart TS draggingStart
// FQImport: com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD.drawDay.outer TS outer
// FQImport: com.lightningkite.khrysalis.views.MonthCVD TS MonthCVD
// FQImport: android.graphics.Paint.textSize TS setAndroidGraphicsPaintTextSize
// FQImport: com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD.selectedPaint TS selectedPaint
// FQImport: android.graphics.RectF TS RectF
// FQImport: com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD.measure.height TS height
// FQImport: com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD.drawDay.canvas TS canvas
// FQImport: com.lightningkite.khrysalis.time.DateAlone TS DateAlone
// FQImport: android.util.DisplayMetrics TS DisplayMetrics
// FQImport: android.graphics.Paint.textSize TS getAndroidGraphicsPaintTextSize
// FQImport: com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD.onTouchDown.day TS day
// FQImport: kotlin.Int.Companion.MIN_VALUE TS MIN_VALUE
// FQImport: kotlin.Int.Companion.MAX_VALUE TS MAX_VALUE
// FQImport: com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD.onTouchDown.endInclusiveValue TS endInclusiveValue
// FQImport: com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD.endInclusive TS endInclusive
// FQImport: com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD.onTouchMove.day TS day
// FQImport: com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD.onTouchMove TS onTouchMove
// FQImport: com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD.selectedDayPaint TS selectedDayPaint
// FQImport: com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD.onTouchDown.startValue TS startValue
// FQImport: com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD.onTouchUp.day TS day
// FQImport: com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD.onTouchMove.obs TS obs
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.dayBackground TS dayBackground
// FQImport: com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD.onTouchMove.endInclusiveValue TS endInclusiveValue
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.measure TS measure
// FQImport: com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD.onTap.day TS day
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.dayBackgroundMid TS dayBackgroundMid
// FQImport: com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD.startedDraggingOn TS startedDraggingOn
// FQImport: com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD.onTouchMove.startValue TS startValue
// FQImport: android.graphics.Paint TS Paint
// FQImport: com.lightningkite.khrysalis.time.DateAlone.comparable TS comparable
// FQImport: com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD.drawDay.showingMonth TS showingMonth
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.day TS day
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty TS MutableObservableProperty
// FQImport: com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD.drawDay.inner TS inner
import { MutableObservableProperty } from './../observables/MutableObservableProperty.shared'
import { DateAlone } from './../time/DateAlone.actual'
import { Paint } from './draw/Paint.actual'
import { MonthCVD } from './MonthCVD.shared'

//! Declares com.lightningkite.khrysalis.views.SelectDateRangeMonthCVD
export class SelectDateRangeMonthCVD extends MonthCVD {
    public constructor() { super(); }
    public generateAccessibilityView(): (HTMLElement | null){ return null; }
    
    public draggingStart: Boolean;
    
    public start: MutableObservableProperty<(DateAlone | null)>;
    
    public endInclusive: MutableObservableProperty<(DateAlone | null)>;
    
    
    
    public readonly selectedDayPaint: Paint;
    
    public readonly selectedPaint: Paint;
    
    
    public measure(width: number, height: number, displayMetrics: DisplayMetrics): void{
        .measure(width, height, displayMetrics);
        setAndroidGraphicsPaintTextSize(this.selectedDayPaint, getAndroidGraphicsPaintTextSize(this.dayPaint));
    }
    
    public readonly drawDay_dateAlone: DateAlone;
    
    public drawDay(canvas: CanvasRenderingContext2D, showingMonth: DateAlone, day: DateAlone, displayMetrics: DisplayMetrics, outer: RectF, inner: RectF): void{
        if (day.equals(this.start.value) && (day.equals(this.endInclusive.value) || this.endInclusive.value.equals(null))){
            CalendarDrawing.INSTANCE.dayBackground(canvas, inner, this.selectedPaint);
            CalendarDrawing.INSTANCE.day(canvas, showingMonth, day, inner, this.selectedDayPaint);
        } else if (day.equals(this.start.value)){
            CalendarDrawing.INSTANCE.dayBackgroundStart(canvas, inner, outer, this.selectedPaint);
            CalendarDrawing.INSTANCE.day(canvas, showingMonth, day, inner, this.selectedDayPaint);
        } else if (day.equals(this.endInclusive.value)){
            CalendarDrawing.INSTANCE.dayBackgroundEnd(canvas, inner, outer, this.selectedPaint);
            CalendarDrawing.INSTANCE.day(canvas, showingMonth, day, inner, this.selectedDayPaint);
        } else if (day.comparable > (this.start.value?.comparable ?? Int.Companion.INSTANCE.MAX_VALUE) && day.comparable < (this.endInclusive.value?.comparable ?? Int.Companion.INSTANCE.MIN_VALUE)){
            CalendarDrawing.INSTANCE.dayBackgroundMid(canvas, inner, outer, this.selectedPaint);
            CalendarDrawing.INSTANCE.day(canvas, showingMonth, day, inner, this.selectedDayPaint);
        } else {
            CalendarDrawing.INSTANCE.day(canvas, showingMonth, day, inner, this.dayPaint);
        }
    }
    
    
    private startedDraggingOn: (DateAlone | null);
    
    
    public onTap(day: DateAlone): void{
        if (!(this.start.value.equals(null)) && this.start.value.equals(this.endInclusive.value) && day.comparable > this.start.value!!.comparable) {
            this.endInclusive.value = day;
        } else {
            this.start.value = day;
            this.endInclusive.value = day;
        }
    }
    
    public onTouchDown(day: DateAlone): Boolean{
        if (!(day.equals(this.start.value)) && !(day.equals(this.endInclusive.value))) {
            return false;
        }
        this.startedDraggingOn = day;
        //If on start/end - drag
        //If after, extend
        //If before, extend
        //If middle, collapse all
        const startValue = this.start.value;
        
        const endInclusiveValue = this.endInclusive.value;
        
        
        if (startValue.equals(null) || endInclusiveValue.equals(null)){
            this.start.value = day;
            this.endInclusive.value = day;
            this.draggingStart = false;
        } else if (day.equals(endInclusiveValue)){
            this.draggingStart = false;
        } else if (day.equals(startValue)){
            this.draggingStart = true;
        } else if (day.comparable > endInclusiveValue!!.comparable && startValue.equals(endInclusiveValue)){
            this.endInclusive.value = day;
            this.draggingStart = false;
        } else {
            this.start.value = day;
            this.endInclusive.value = day;
            this.draggingStart = false;
        }
        return true;
    }
    
    public onTouchMove(day: DateAlone): Boolean{
        const startValue = this.start.value;
        
        const endInclusiveValue = this.endInclusive.value;
        
        if (startValue.equals(null) || endInclusiveValue.equals(null)){
        } else if (this.draggingStart && day.comparable > endInclusiveValue!!.comparable){
            this.start.value = this.endInclusive.value;
            this.endInclusive.value = day;
            this.draggingStart = false;
            return true;
        } else if (this.draggingStart.not() && day.comparable < startValue!!.comparable){
            this.endInclusive.value = this.start.value;
            this.start.value = day;
            this.draggingStart = true;
            return true;
        }
        
        const obs: MutableObservableProperty<(DateAlone | null)> = this.draggingStart ? this.start : this.endInclusive;
        
        obs.value = day;
        return true;
    }
    
    public onTouchUp(day: DateAlone): Boolean{
        this.onTouchMove(day);
        return true;
    }
}

