// Generated by Khrysalis Swift converter - this file will be overwritten.
// File: views/SelectDateMonthCVD.shared.kt
// Package: com.lightningkite.khrysalis.views
import Foundation
import CoreGraphics

open class SelectDateMonthCVD : MonthCVD {
    override public init() {
        self.selected = StandardObservableProperty(underlyingValue: nil)
        self.selectedDayPaint = Paint()
        self.selectedPaint = Paint()
        super.init()
        //Necessary properties should be initialized now
        if let it = (self.selected.value) { 
            self.currentMonthObs.value = it.dayOfMonth(value: 1)
        }
        self.selected.onChange.subscribe(onNext:  { [weak self] (value: DateAlone?) -> Void in self?.invalidate() }, onError: nil, onCompleted: nil).forever()
    }
    
    override public func generateAccessibilityView() -> View? { return nil }
    
    public var selected: MutableObservableProperty<DateAlone?>
    
    
    
    public let selectedDayPaint: Paint
    public let selectedPaint: Paint
    
    override public func drawDay(canvas: Canvas, showingMonth: DateAlone, day: DateAlone, displayMetrics: DisplayMetrics, outer: CGRect, inner: CGRect) -> Void {
        if day == self.selected.value{
            CalendarDrawing.INSTANCE.dayBackground(canvas: canvas, inner: inner, paint: self.selectedPaint)
            CalendarDrawing.INSTANCE.day(canvas: canvas, month: showingMonth, date: day, inner: inner, paint: self.selectedDayPaint)
        } else {
            CalendarDrawing.INSTANCE.day(canvas: canvas, month: showingMonth, date: day, inner: inner, paint: self.dayPaint)
        }
    }
    
    override public func measure(width: CGFloat, height: CGFloat, displayMetrics: DisplayMetrics) -> Void {
        super.measure(width: width, height: height, displayMetrics: displayMetrics)
        self.selectedDayPaint.textSize = self.dayPaint.textSize
    }
    
    override public func onTap(day: DateAlone) -> Void {
        self.selected.value = day
    }
}


