// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: views/MonthCVD.shared.kt
// Package: com.lightningkite.khrysalis.views
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.drawMonth.day TS day
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.measure.width TS width
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.labelPaint TS labelPaint
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.dragStartY TS dragStartY
// FQImport: android.graphics.Paint.textSize TS textSize
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.onTouchDown.id TS id
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.lastOffsetTime TS lastOffsetTime
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.label TS label
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.label.inner TS inner
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.onTouchDown.<anonymous>.it TS it
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.dayBackgroundStart.outer TS outer
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.calcMonthB TS calcMonthB
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.label.paint TS paint
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.dayBackgroundEnd.paint TS paint
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.dayPaint TS dayPaint
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.dayBackgroundMid.canvas TS canvas
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.dayBackgroundStart.canvas TS canvas
// FQImport: com.lightningkite.khrysalis.views.MonthCVD._currentOffset TS _currentOffset
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.draw.displayMetrics TS displayMetrics
// FQImport: com.lightningkite.khrysalis.time.DateAlone.day TS day
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.dayAtPixel.existing TS existing
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.lastOffset TS lastOffset
// FQImport: android.graphics.RectF.width TS width
// FQImport: android.graphics.RectF.inset TS inset
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.label.dayOfWeek TS dayOfWeek
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.draw.canvas TS canvas
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.drawDay TS drawDay
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.measure TS measure
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.dayAt.row TS row
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.dayCellWidth TS dayCellWidth
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.drawDay.canvas TS canvas
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.onTouchMove.height TS height
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.dayAtPixel.row TS row
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.onTouchMove.y TS y
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.label.text TS text
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.drawDay.showingMonth TS showingMonth
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.drawDay.outer TS outer
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.dayBackgroundStart.paint TS paint
// FQImport: android.graphics.RectF.centerX TS centerX
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.dayBackgroundEnd.outer TS outer
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.drawDate TS drawDate
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.drawMonth.width TS width
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.dayAt TS dayAt
// FQImport: android.graphics.RectF.right TS right
// FQImport: com.lightningkite.khrysalis.views.CustomViewDelegate TS CustomViewDelegate
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.onTouchUp.x TS x
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.dayCellHeight TS dayCellHeight
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.dayAtPixel.columnDrag TS columnDrag
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.dayAtPixel.columnRaw TS columnRaw
// FQImport: android.graphics.RectF TS RectF
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.currentOffset TS currentOffset
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.calcMonth TS calcMonth
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.onTouchUp.weighted TS weighted
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.measure.height TS height
// FQImport: android.graphics.RectF.height TS height
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.dayAtPixel TS dayAtPixel
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.dayBackgroundMid.paint TS paint
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.dayBackgroundEnd.inner TS inner
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.drawMonth.month TS month
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.<set-currentOffset>.value TS value
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.rectForReuse TS rectForReuse
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.internalPadding TS internalPadding
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.onTouchMove.width TS width
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.onTap TS onTap
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.draw.width TS width
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.customView TS customView
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.onTouchDown TS onTouchDownDate
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.draggingId TS draggingId
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.onTouchUp.id TS id
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.onTouchDown.y TS y
// FQImport: com.lightningkite.khrysalis.views.draw.drawTextCentered>android.graphics.Canvas TS androidGraphicsCanvasDrawTextCentered
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.dragEnabled TS dragEnabled
// FQImport: android.util.DisplayMetrics.scaledDensity TS scaledDensity
// FQImport: android.util.DisplayMetrics.density TS density
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.dayCellMarginDp TS dayCellMarginDp
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.drawDay.day TS day
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.dayBackgroundMid.outer TS outer
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.currentMonthObs TS currentMonthObs
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.drawMonth TS drawMonth
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.DRAGGING_NONE TS DRAGGING_NONE
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.sizeThatFitsHeight.width TS width
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.drawMonth.displayMetrics TS displayMetrics
// FQImport: com.lightningkite.khrysalis.time.DateAlone.month TS month
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.internalPaddingDp TS internalPaddingDp
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.dayBackground.inner TS inner
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.drawMonth.col TS col
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.onTouchMove.id TS id
// FQImport: android.graphics.RectF.set TS set
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.dayBackgroundEnd.canvas TS canvas
// FQImport: android.graphics.Paint.color TS color
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.labelFontSp TS labelFontSp
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.dayBackgroundMid.inner TS inner
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.dayAtPixel.column TS column
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.drawLabel TS drawLabel
// FQImport: kotlin.Int.toString TS toString
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.dayBackground.paint TS paint
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.day.paint TS paint
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.day.canvas TS canvas
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.currentMonth TS currentMonth
// FQImport: android.graphics.RectF.centerY TS centerY
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.onTouchMove.<anonymous>.it TS it
// FQImport: com.lightningkite.khrysalis.time.DateAlone TS DateAlone
// FQImport: android.util.DisplayMetrics TS DisplayMetrics
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.onTouchMove TS onTouchMoveDate
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.onTouchDown.day TS day
// FQImport: com.lightningkite.khrysalis.time.DateAlone.year TS year
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.onTouchDown.width TS width
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.dayBackgroundStart.inner TS inner
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.dragStartX TS dragStartX
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.dayAtPixel.y TS y
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.drawMonth.xOffset TS xOffset
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.isTap TS isTap
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing TS CalendarDrawing
// FQImport: android.graphics.RectF.left TS left
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.dayLabelHeight TS dayLabelHeight
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.drawMonth.canvas TS canvas
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty TS MutableObservableProperty
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.label.canvas TS canvas
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.drawMonth.row TS row
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.dayBackground.canvas TS canvas
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty.value TS value
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.dayAt.existing TS existing
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.onTouchDown.x TS x
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.dayAt.column TS column
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.dayAt.month TS month
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.onTouchUp.<anonymous>.it TS it
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.dayCellMargin TS dayCellMargin
// FQImport: com.lightningkite.khrysalis.time.TimeNames TS TimeNames
// FQImport: com.lightningkite.khrysalis.floorDiv>kotlin.Int TS kotlinIntFloorDiv
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.draw.height TS height
// FQImport: android.graphics.RectF.top TS top
// FQImport: com.lightningkite.khrysalis.time.set>com.lightningkite.khrysalis.time.DateAlone TS comLightningkiteKhrysalisTimeDateAloneSet
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.day.originalColor TS originalColor
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.drawLabel.dayOfWeek TS dayOfWeek
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.drawLabel.canvas TS canvas
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.measure.displayMetrics TS displayMetrics
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.onTouchDown.height TS height
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.day.date TS date
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.dayAtPixel.x TS x
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.onTouchUp TS onTouchUpDate
// FQImport: android.graphics.RectF.bottom TS bottom
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.rectForReuseB TS rectForReuseB
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.day.month TS month
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.dayAtPixel.columnRawBeforeDrag TS columnRawBeforeDrag
// FQImport: com.lightningkite.khrysalis.observables.MutableObservableProperty.update TS update
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.dayAtPixel.monthOffset TS monthOffset
// FQImport: com.lightningkite.khrysalis.floorMod>kotlin.Int TS kotlinIntFloorMod
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.onTouchUp.y TS y
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.dayFontSp TS dayFontSp
// FQImport: com.lightningkite.khrysalis.time.TimeNames.shortWeekdayName TS shortWeekdayName
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.day.myPaint TS myPaint
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.onTouchMove.x TS x
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.day.inner TS inner
// FQImport: android.graphics.Paint TS Paint
// FQImport: com.lightningkite.khrysalis.views.CalendarDrawing.day TS day
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.drawLabel.inner TS inner
// FQImport: com.lightningkite.khrysalis.views.MonthCVD.<set-currentMonth>.value TS value
import { dateAloneMod, dateAloneModRelative } from '../time/Date.actual'
import { DisplayMetrics } from './DisplayMetrics.actual'
import { kotlinIntFloorDiv, kotlinIntFloorMod } from '../Math.shared'
import { TimeNames } from '../time/TimeNames.actual'
import { Paint } from './draw/Paint.actual'
import { comLightningkiteKhrysalisTimeDateAloneSet } from '../time/DateAlone.shared'
import { androidGraphicsCanvasDrawTextCentered } from './draw/Canvas.actual'
import { MutableObservableProperty } from '../observables/MutableObservableProperty.shared'
import { NumberRange } from '../Kotlin'
import { DateAlone } from '../time/DateAlone.actual'
import { CustomViewDelegate } from './CustomViewDelegate.shared'
import { applyAlphaToColor } from './Colors.actual'
import { customViewInvalidate } from './CustomView.actual'
import { pathFromLTRB } from './draw/Path.actual'

//! Declares com.lightningkite.khrysalis.views.MonthCVD
export class MonthCVD extends CustomViewDelegate {
    public constructor() { super(); }
    public generateAccessibilityView(): (HTMLElement | null) { return null; }
    
    public readonly currentMonthObs: MutableObservableProperty<DateAlone>;
    
    //! Declares com.lightningkite.khrysalis.views.MonthCVD.currentMonth
    public get currentMonth(): DateAlone { return this.currentMonthObs.value; }
    public set currentMonth(value: DateAlone) {
        this.currentMonthObs.value = value;
    }
    
    
    public dragEnabled: boolean;
    
    
    
    
    public labelFontSp: number;
    
    public dayFontSp: number;
    
    public internalPaddingDp: number;
    
    public dayCellMarginDp: number;
    
    private internalPadding: number;
    
    private dayLabelHeight: number;
    
    private dayCellHeight: number;
    
    private dayCellWidth: number;
    
    private dayCellMargin: number;
    
    
    private _currentOffset: number;
    
    //! Declares com.lightningkite.khrysalis.views.MonthCVD.currentOffset
    public get currentOffset(): number {
        return this._currentOffset;
    }
    public set currentOffset(value: number) {
        this._currentOffset = value;
        const temp145 = this.customView;
        if(temp145 !== null) customViewInvalidate(temp145);
    }
    
    private dragStartX: number;
    
    private lastOffset: number;
    
    private lastOffsetTime: number;
    
    private readonly DRAGGING_NONE: number;
    
    private draggingId: number;
    
    
    public animateNextMonth(): void {
        dateAloneModRelative(this.currentMonthObs.value, Date.prototype.getMonth, Date.prototype.setMonth, 1);
        this.currentMonthObs.update();
        this.currentOffset = 1;
    }
    
    public animatePreviousMonth(): void {
        dateAloneModRelative(this.currentMonthObs.value, Date.prototype.getMonth, Date.prototype.setMonth, -1);
        this.currentMonthObs.update();
        this.currentOffset = -1;
    }
    
    public readonly labelPaint: Paint;
    
    public readonly dayPaint: Paint;
    
    
    
    
    private readonly calcMonth: DateAlone;
    
    
    public dayAtPixel(x: number, y: number, existing: (DateAlone | null) = null): (DateAlone | null) {
        if (y < this.dayLabelHeight) return null;
        //        val columnRaw = (x / dayCellWidth - (dayCellWidth + currentOffset) * 7).toInt()
        const columnRawBeforeDrag = x / this.dayCellWidth;
        
        const columnDrag = this.currentOffset * 7;
        
        const columnRaw = Math.floor((columnDrag + columnRawBeforeDrag));
        
        const column = kotlinIntFloorMod(columnRaw, 7);
        
        const monthOffset = kotlinIntFloorDiv(columnRaw, 7);
        
        const row = Math.floor(((y - this.dayLabelHeight) / this.dayCellHeight));
        
        if (row < 0 || row > 5) return null;
        if (column < 0 || column > 6) return null;
        return this.dayAt(dateAloneModRelative(comLightningkiteKhrysalisTimeDateAloneSet(this.calcMonth, this.currentMonth), Date.prototype.getMonth, Date.prototype.setMonth, monthOffset), row, column, existing ?? new DateAlone(0, 0, 0));
    }
    
    public dayAt(month: DateAlone, row: number, column: number, existing: DateAlone = new DateAlone(0, 0, 0)): DateAlone {
        return dateAloneModRelative(dateAloneMod(dateAloneMod(comLightningkiteKhrysalisTimeDateAloneSet(existing, month), Date.prototype.setDate, 1), function(newDay: number){
                    this as Date
                    const diff = newDay - this.getDay();
                    this.setDate(this.getDate() + diff);
            }, 1)
        , Date.prototype.getDate, Date.prototype.setDate, row * 7 + column);
    }
    
    public measure(width: number, height: number, displayMetrics: DisplayMetrics): void {
        this.internalPadding = displayMetrics.density * this.internalPaddingDp;
        this.dayCellMargin = displayMetrics.density * this.dayCellMarginDp;
        this.labelPaint.textSize = this.labelFontSp * displayMetrics.scaledDensity;
        this.dayPaint.textSize = this.dayFontSp * displayMetrics.scaledDensity;
        this.dayLabelHeight = this.labelPaint.textSize * 1.5 + this.internalPadding * 2;
        this.dayCellWidth = width / 7;
        this.dayCellHeight = (height - this.dayLabelHeight) / 6;
    }
    
    private readonly calcMonthB: DateAlone;
    
    
    public draw(canvas: CanvasRenderingContext2D, width: number, height: number, displayMetrics: DisplayMetrics): void {
        this.measure(width, height, displayMetrics);
        if (this.currentOffset > 0) {
            //draw past month and current month
            this.drawMonth(canvas, (this.currentOffset - 1) * width, width, dateAloneModRelative(comLightningkiteKhrysalisTimeDateAloneSet(this.calcMonthB, this.currentMonth), Date.prototype.getMonth, Date.prototype.setMonth, -1), displayMetrics);
            this.drawMonth(canvas, this.currentOffset * width, width, this.currentMonth, displayMetrics);
        } else if (this.currentOffset < 0) {
            //draw future month and current month
            this.drawMonth(canvas, (this.currentOffset + 1) * width, width, dateAloneModRelative(comLightningkiteKhrysalisTimeDateAloneSet(this.calcMonthB, this.currentMonth), Date.prototype.getMonth, Date.prototype.setMonth, 1), displayMetrics);
            this.drawMonth(canvas, this.currentOffset * width, width, this.currentMonth, displayMetrics);
        } else {
            //Nice, it's exactly zero.  We can just draw one.
            this.drawMonth(canvas, this.currentOffset * width, width, this.currentMonth, displayMetrics);
        }
    }
    
    private drawDate: DateAlone;
    
    private readonly rectForReuse: RectF;
    
    private readonly rectForReuseB: RectF;
    
    public drawMonth(canvas: CanvasRenderingContext2D, xOffset: number, width: number, month: DateAlone, displayMetrics: DisplayMetrics): void {
        for (const day of new NumberRange(1, 7)) {
            const col = day - 1;
            
            this.rectForReuse.set(xOffset + col * this.dayCellWidth - 0.01, -0.01, xOffset + (col + 1) * this.dayCellWidth + 0.01, this.dayLabelHeight + 0.01);
            this.rectForReuseB.set(this.rectForReuse);
            this.rectForReuse.inset(this.internalPadding, this.internalPadding);
            this.drawLabel(canvas, day, displayMetrics, this.rectForReuse, this.rectForReuseB);
        }
        for (const row of new NumberRange(0, 5)) {
            for (const col of new NumberRange(0, 6)) {
                const day = this.dayAt(month, row, col, this.drawDate);
                
                this.rectForReuse.set(xOffset + col * this.dayCellWidth - 0.01, this.dayLabelHeight + row * this.dayCellHeight - 0.01, xOffset + (col + 1) * this.dayCellWidth + 0.01, this.dayLabelHeight + (row + 1) * this.dayCellHeight + 0.01);
                if (this.rectForReuse.left > width) {
                    continue;
                }
                if (this.rectForReuse.right < 0) {
                    continue;
                }
                this.rectForReuseB.set(this.rectForReuse);
                this.rectForReuse.inset(this.dayCellMargin, this.dayCellMargin);
                this.drawDay(canvas, month, day, displayMetrics, this.rectForReuseB, this.rectForReuse);
            }
        }
    }
    
    public drawLabel(canvas: CanvasRenderingContext2D, dayOfWeek: number, displayMetrics: DisplayMetrics, outer: RectF, inner: RectF): void {
        CalendarDrawing.INSTANCE.label(canvas, dayOfWeek, inner, this.labelPaint);
    }
    
    public drawDay(canvas: CanvasRenderingContext2D, showingMonth: DateAlone, day: DateAlone, displayMetrics: DisplayMetrics, outer: RectF, inner: RectF): void {
        CalendarDrawing.INSTANCE.day(canvas, showingMonth, day, outer, this.dayPaint);
    }
    
    public isTap: boolean;
    
    public dragStartY: number;
    
    public onTap(day: DateAlone): void {}
    
    public onTouchDownDate(day: DateAlone): boolean { return false; }
    public onTouchDown(id: number, x: number, y: number, width: number, height: number): boolean {
        const day = this.dayAtPixel(x, y, undefined);
        
        const temp200 = day;
        if(temp200 !== null) ((it) => {
                if (this.onTouchDownDate(it)) {
                    return true;
                }
        })(temp200);
        this.dragStartX = x / width;
        this.dragStartY = y / height;
        this.draggingId = id;
        this.lastOffsetTime = Date.now();
        this.isTap = true;
        
        return true;
    }
    
    public onTouchMoveDate(day: DateAlone): boolean { return false; }
    public onTouchMove(id: number, x: number, y: number, width: number, height: number): boolean {
        if (this.draggingId === id) {
            this.lastOffset = this.currentOffset;
            this.lastOffsetTime = Date.now();
            if (this.dragEnabled) {
                this.currentOffset = (x / width) - this.dragStartX;
                if (Math.abs((x / width - this.dragStartX)) > 0.05 || Math.abs((y / height - this.dragStartY)) > 0.05) {
                    this.isTap = false;
                }
            }
        } else {
            const temp209 = this.dayAtPixel(x, y, undefined);
            if(temp209 !== null) ((it) => {
                    return this.onTouchMoveDate(it)
            })(temp209);
        }
        return true;
    }
    
    public onTouchUpDate(day: DateAlone): boolean { return false; }
    public onTouchUp(id: number, x: number, y: number, width: number, height: number): boolean {
        if (this.draggingId === id) {
            if (this.isTap) {
                const temp211 = this.dayAtPixel(x, y, undefined);
                if(temp211 !== null) ((it) => {
                        this.onTap(it)
                })(temp211);
            } else if (this.dragEnabled) {
                const weighted = this.currentOffset + (this.currentOffset - this.lastOffset) * 200 / (Date.now() - this.lastOffsetTime);
                
                if (weighted > 0.5) {
                    //shift right one
                    dateAloneModRelative(this.currentMonthObs.value, Date.prototype.getMonth, Date.prototype.setMonth, -1);
                    this.currentMonthObs.update();
                    this.currentOffset = this.currentOffset - 1;
                } else if (weighted < -0.5) {
                    //shift left one
                    dateAloneModRelative(this.currentMonthObs.value, Date.prototype.getMonth, Date.prototype.setMonth, 1);
                    this.currentMonthObs.update();
                    this.currentOffset = this.currentOffset + 1;
                }
            }
            this.draggingId = this.DRAGGING_NONE;
        } else {
            const temp223 = this.dayAtPixel(x, y, undefined);
            if(temp223 !== null) ((it) => {
                    return this.onTouchUpDate(it)
            })(temp223);
        }
        return true;
    }
    
    
    public sizeThatFitsWidth(width: number, height: number): number {
        return this.dayLabelHeight * 28;
    }
    
    public sizeThatFitsHeight(width: number, height: number): number {
        return width * 6 / 7 + this.dayLabelHeight;
    }
}

//! Declares com.lightningkite.khrysalis.views.CalendarDrawing
export class CalendarDrawing {
    private constructor() {
    }
    public static INSTANCE = new CalendarDrawing();
    
    day(canvas: CanvasRenderingContext2D, month: DateAlone, date: DateAlone, inner: RectF, paint: Paint): void {
        if (date.month === month.month && date.year === month.year) {
            androidGraphicsCanvasDrawTextCentered(canvas, date.day.toString(), inner.centerX(), inner.centerY(), paint);
        } else {
            const originalColor = paint.color;
            
            let myPaint = paint;
            
            myPaint.color = applyAlphaToColor(paint.color, 64);
            androidGraphicsCanvasDrawTextCentered(canvas, date.day.toString(), inner.centerX(), inner.centerY(), myPaint);
            myPaint.color = originalColor;
        }
    }
    
    label(canvas: CanvasRenderingContext2D, dayOfWeek: number, inner: RectF, paint: Paint): void {
        const text = TimeNames.INSTANCE.shortWeekdayName(dayOfWeek);
        
        androidGraphicsCanvasDrawTextCentered(canvas, text, inner.centerX(), inner.centerY(), paint);
    }
    
    dayBackground(canvas: CanvasRenderingContext2D, inner: RectF, paint: Paint): void {
        canvas.arc(inner.centerX(), inner.centerY(), Math.min(inner.width() / 2, inner.height() / 2), 0, Math.PI * 2); paint.complete(canvas);
    }
    
    dayBackgroundStart(canvas: CanvasRenderingContext2D, inner: RectF, outer: RectF, paint: Paint): void {
        canvas.arc(inner.centerX(), inner.centerY(), Math.min(inner.width() / 2, inner.height() / 2), 0, Math.PI * 2); paint.complete(canvas);
        canvas.clip(pathFromLTRB(outer.centerX(), inner.top, outer.right, inner.bottom)); paint.complete(canvas);
    }
    
    dayBackgroundMid(canvas: CanvasRenderingContext2D, inner: RectF, outer: RectF, paint: Paint): void {
        canvas.clip(pathFromLTRB(outer.left, inner.top, outer.right, inner.bottom)); paint.complete(canvas);
    }
    
    dayBackgroundEnd(canvas: CanvasRenderingContext2D, inner: RectF, outer: RectF, paint: Paint): void {
        canvas.arc(inner.centerX(), inner.centerY(), Math.min(inner.width() / 2, inner.height() / 2), 0, Math.PI * 2); paint.complete(canvas);
        canvas.clip(pathFromLTRB(outer.left, inner.top, outer.centerX(), inner.bottom)); paint.complete(canvas);
    }
}


