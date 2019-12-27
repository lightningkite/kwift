//Package: com.lightningkite.kwift.views.shared
//Converted using Kwift2

import Foundation



open class MonthCVD: CustomViewDelegate {
    
    
    
    override public func generateAccessibilityView() -> View?  {
        return nil
    }
    private var _currentMonth: DateAlone
    public var currentMonth: DateAlone {
        get {
            return _currentMonth
        }
        set(value) {
            _currentMonth = value.copy(day: 1)
            customView?.invalidate()
        }
    }
    public var labelFontSp: Float
    public var dayFontSp: Float
    public var internalPaddingDp: Float
    public var dayCellMarginDp: Float
    private var internalPadding: Float
    private var dayLabelHeight: Float
    private var dayCellHeight: Float
    private var dayCellWidth: Float
    private var dayCellMargin: Float
    private var _currentOffset: Float
    public var currentOffset: Float {
        get {
            return _currentOffset
        }
        set(value) {
            _currentOffset = value
            customView?.postInvalidate()
        }
    }
    private var dragStartX: Float
    private var lastOffset: Float
    private var lastOffsetTime: Int64
    private var DRAGGING_NONE: Int32
    private var draggingId: Int32
    
    public func animateNextMonth() -> Void {
        currentMonth.setAddMonthOfYear(1)
        currentOffset = Float(1)
    }
    
    public func animatePreviousMonth() -> Void {
        currentMonth.setAddMonthOfYear(-1)
        currentOffset = -Float(1)
    }
    public var labelPaint: Paint
    public var dayPaint: Paint
    private var calcMonth: DateAlone
    
    public func dayAtPixel(x: Float, y: Float, existing: DateAlone?  = nil) -> DateAlone?  {
        if y < dayLabelHeight {
            return nil
        }
        var columnRaw = ( x / dayCellWidth - dayCellWidth * currentOffset * 7 ).toInt()
        var column = columnRaw.floorMod(7)
        var monthOffset = columnRaw.floorDiv(7)
        var row = ( ( y - dayLabelHeight ) / dayCellHeight ).toInt()
        if row < 0 || row > 5 {
            return nil
        }
        if column < 0 || column > 6 {
            return nil
        }
        return dayAt(calcMonth.set(currentMonth).setAddMonthOfYear(monthOffset), row, column, existing ?? DateAlone(0, 0, 0))
    }
    public func dayAtPixel(_ x: Float, _ y: Float, _ existing: DateAlone?  = nil) -> DateAlone?  {
        return dayAtPixel(x: x, y: y, existing: existing)
    }
    
    public func dayAt(month: DateAlone, row: Int32, column: Int32, existing: DateAlone = DateAlone(0, 0, 0)) -> DateAlone {
        return existing.set(month).setDayOfMonth(1).setDayOfWeek(1).setAddDayOfMonth(row * 7 + column)
    }
    public func dayAt(_ month: DateAlone, _ row: Int32, _ column: Int32, _ existing: DateAlone = DateAlone(0, 0, 0)) -> DateAlone {
        return dayAt(month: month, row: row, column: column, existing: existing)
    }
    
    public func measure(width: Float, height: Float, displayMetrics: DisplayMetrics) -> Void {
        internalPadding = displayMetrics.density * internalPaddingDp
        dayCellMargin = displayMetrics.density * dayCellMarginDp
        labelPaint.textSize = labelFontSp * displayMetrics.scaledDensity
        dayPaint.textSize = dayFontSp * displayMetrics.scaledDensity
        dayLabelHeight = labelPaint.textSize * Float(1.5) + internalPadding * 2
        dayCellWidth = width / Float(7)
        dayCellHeight = ( height - dayLabelHeight ) / Float(6)
    }
    public func measure(_ width: Float, _ height: Float, _ displayMetrics: DisplayMetrics) -> Void {
        return measure(width: width, height: height, displayMetrics: displayMetrics)
    }
    private var calcMonthB: DateAlone
    
    override public func draw(canvas: Canvas, width: Float, height: Float, displayMetrics: DisplayMetrics) -> Void {
        measure(width, height, displayMetrics)
        if currentOffset > Float(0) {
            drawMonth(canvas, ( currentOffset - Float(1) ) * width, calcMonthB.set(currentMonth).setAddMonthOfYear(-1), displayMetrics)
            drawMonth(canvas, currentOffset * width, currentMonth, displayMetrics)
        } else if currentOffset < Float(0) {
            drawMonth(canvas, ( currentOffset + Float(1) ) * width, calcMonthB.set(currentMonth).setAddMonthOfYear(1), displayMetrics)
            drawMonth(canvas, currentOffset * width, currentMonth, displayMetrics)
        } else {
            drawMonth(canvas, currentOffset * width, currentMonth, displayMetrics)
        }
    }
    override public func draw(_ canvas: Canvas, _ width: Float, _ height: Float, _ displayMetrics: DisplayMetrics) -> Void {
        return draw(canvas: canvas, width: width, height: height, displayMetrics: displayMetrics)
    }
    private var drawDate: DateAlone
    private var rectForReuse: RectF
    private var rectForReuseB: RectF
    
    open func drawMonth(canvas: Canvas, xOffset: Float, month: DateAlone, displayMetrics: DisplayMetrics) -> Void {
        
        for day in 1.toInt() ... 7.toInt() {
            var col = day - 1
            rectForReuse.set(xOffset + col.toFloat() * dayCellWidth - Float(0.01), -Float(0.01), xOffset + ( col.toFloat() + 1 ) * dayCellWidth + Float(0.01), dayLabelHeight + Float(0.01))
            rectForReuseB.set(rectForReuse)
            rectForReuse.inset(internalPadding, internalPadding)
            drawLabel(canvas, day, displayMetrics, rectForReuse, rectForReuseB)
        }
        
        for row in 0.toInt() ... 5.toInt() {
            
            for col in 0.toInt() ... 6.toInt() {
                var day = dayAt(month, row, col, drawDate)
                rectForReuse.set(xOffset + col.toFloat() * dayCellWidth - Float(0.01), dayLabelHeight + row.toFloat() * dayCellHeight - Float(0.01), xOffset + ( col.toFloat() + 1 ) * dayCellWidth + Float(0.01), dayLabelHeight + ( row.toFloat() + 1 ) * dayCellHeight + Float(0.01))
                rectForReuseB.set(rectForReuse)
                rectForReuse.inset(dayCellMargin, dayCellMargin)
                drawDay(canvas, month, day, displayMetrics, rectForReuse, rectForReuseB)
            }
        }
    }
    open func drawMonth(_ canvas: Canvas, _ xOffset: Float, _ month: DateAlone, _ displayMetrics: DisplayMetrics) -> Void {
        return drawMonth(canvas: canvas, xOffset: xOffset, month: month, displayMetrics: displayMetrics)
    }
    
    open func drawLabel(canvas: Canvas, dayOfWeek: Int32, displayMetrics: DisplayMetrics, outer: RectF, inner: RectF) -> Void {
        CalendarDrawing.label(canvas, dayOfWeek, inner, labelPaint)
    }
    open func drawLabel(_ canvas: Canvas, _ dayOfWeek: Int32, _ displayMetrics: DisplayMetrics, _ outer: RectF, _ inner: RectF) -> Void {
        return drawLabel(canvas: canvas, dayOfWeek: dayOfWeek, displayMetrics: displayMetrics, outer: outer, inner: inner)
    }
    
    open func drawDay(canvas: Canvas, showingMonth: DateAlone, day: DateAlone, displayMetrics: DisplayMetrics, outer: RectF, inner: RectF) -> Void {
        if day.month == showingMonth.month, day.year == showingMonth.year {
            CalendarDrawing.day(canvas, day, outer, dayPaint)
        } else {
            CalendarDrawing.dayFaded(canvas, day, outer, dayPaint)
            print("Draw faded: \(showingMonth.month) vs \(day.month)")
            print("Draw faded: \(showingMonth.year) vs \(day.year)")
        }
    }
    open func drawDay(_ canvas: Canvas, _ showingMonth: DateAlone, _ day: DateAlone, _ displayMetrics: DisplayMetrics, _ outer: RectF, _ inner: RectF) -> Void {
        return drawDay(canvas: canvas, showingMonth: showingMonth, day: day, displayMetrics: displayMetrics, outer: outer, inner: inner)
    }
    
    override public func onTouchDown(id: Int32, x: Float, y: Float, width: Float, height: Float) -> Bool {
        var day = dayAtPixel(x, y)
        
        if let it = (day) {
            if onTouchDown(it) {
                return true
            } 
        }
        dragStartX = x / width
        draggingId = id
        lastOffsetTime = System.currentTimeMillis()
        return true
    }
    override public func onTouchDown(_ id: Int32, _ x: Float, _ y: Float, _ width: Float, _ height: Float) -> Bool {
        return onTouchDown(id: id, x: x, y: y, width: width, height: height)
    }
    
    open func onTouchDown(day: DateAlone) -> Bool {
        return false
    }
    open func onTouchDown(_ day: DateAlone) -> Bool {
        return onTouchDown(day: day)
    }
    
    override public func onTouchMove(id: Int32, x: Float, y: Float, width: Float, height: Float) -> Bool {
        if draggingId == id {
            lastOffset = currentOffset
            lastOffsetTime = System.currentTimeMillis()
            currentOffset = ( x / width ) - dragStartX
        } else {
            
            if let it = (dayAtPixel(x, y)) {
                return onTouchMove(it) 
            }
        }
        return true
    }
    override public func onTouchMove(_ id: Int32, _ x: Float, _ y: Float, _ width: Float, _ height: Float) -> Bool {
        return onTouchMove(id: id, x: x, y: y, width: width, height: height)
    }
    
    open func onTouchMove(day: DateAlone) -> Bool {
        return false
    }
    open func onTouchMove(_ day: DateAlone) -> Bool {
        return onTouchMove(day: day)
    }
    
    override public func onTouchUp(id: Int32, x: Float, y: Float, width: Float, height: Float) -> Bool {
        if draggingId == id {
            var weighted = currentOffset + ( currentOffset - lastOffset ) * Float(200) / ( System.currentTimeMillis() - lastOffsetTime ).toFloat()
            if weighted > Float(0.5) {
                currentMonth.setAddMonthOfYear(-1)
                currentOffset -= 1
            } else if weighted < -Float(0.5) {
                currentMonth.setAddMonthOfYear(1)
                currentOffset += 1
            }
            draggingId = DRAGGING_NONE
        } else {
            
            if let it = (dayAtPixel(x, y)) {
                return onTouchUp(it) 
            }
        }
        return true
    }
    override public func onTouchUp(_ id: Int32, _ x: Float, _ y: Float, _ width: Float, _ height: Float) -> Bool {
        return onTouchUp(id: id, x: x, y: y, width: width, height: height)
    }
    
    open func onTouchUp(day: DateAlone) -> Bool {
        return false
    }
    open func onTouchUp(_ day: DateAlone) -> Bool {
        return onTouchUp(day: day)
    }
    
    override public func sizeThatFitsWidth(width: Float, height: Float) -> Float {
        return dayLabelHeight * Float(28)
    }
    override public func sizeThatFitsWidth(_ width: Float, _ height: Float) -> Float {
        return sizeThatFitsWidth(width: width, height: height)
    }
    
    override public func sizeThatFitsHeight(width: Float, height: Float) -> Float {
        return width * Float(6) / Float(7) + dayLabelHeight
    }
    override public func sizeThatFitsHeight(_ width: Float, _ height: Float) -> Float {
        return sizeThatFitsHeight(width: width, height: height)
    }
    
    override public init() {
        let _currentMonth: DateAlone = Date().dateAlone.setDayOfMonth(1)
        self._currentMonth = _currentMonth
        let labelFontSp: Float = Float(12)
        self.labelFontSp = labelFontSp
        let dayFontSp: Float = Float(16)
        self.dayFontSp = dayFontSp
        let internalPaddingDp: Float = Float(8)
        self.internalPaddingDp = internalPaddingDp
        let dayCellMarginDp: Float = Float(8)
        self.dayCellMarginDp = dayCellMarginDp
        let internalPadding: Float = Float(0)
        self.internalPadding = internalPadding
        let dayLabelHeight: Float = Float(0)
        self.dayLabelHeight = dayLabelHeight
        let dayCellHeight: Float = Float(0)
        self.dayCellHeight = dayCellHeight
        let dayCellWidth: Float = Float(0)
        self.dayCellWidth = dayCellWidth
        let dayCellMargin: Float = Float(0)
        self.dayCellMargin = dayCellMargin
        let _currentOffset: Float = Float(0)
        self._currentOffset = _currentOffset
        let dragStartX: Float = Float(0)
        self.dragStartX = dragStartX
        let lastOffset: Float = Float(0)
        self.lastOffset = lastOffset
        let lastOffsetTime: Int64 = 0
        self.lastOffsetTime = lastOffsetTime
        let DRAGGING_NONE: Int32 = -1
        self.DRAGGING_NONE = DRAGGING_NONE
        let draggingId: Int32 = DRAGGING_NONE
        self.draggingId = draggingId
        let labelPaint: Paint = Paint()
        self.labelPaint = labelPaint
        let dayPaint: Paint = Paint()
        self.dayPaint = dayPaint
        let calcMonth: DateAlone = DateAlone(1, 1, 1)
        self.calcMonth = calcMonth
        let calcMonthB: DateAlone = DateAlone(0, 0, 0)
        self.calcMonthB = calcMonthB
        let drawDate: DateAlone = DateAlone(1, 1, 1)
        self.drawDate = drawDate
        let rectForReuse: RectF = RectF()
        self.rectForReuse = rectForReuse
        let rectForReuseB: RectF = RectF()
        self.rectForReuseB = rectForReuseB
        super.init()
        self.labelPaint.isAntiAlias = true
        self.labelPaint.style = Paint.Style.FILL
        self.labelPaint.color = 0xFF808080.asColor()
        self.dayPaint.isAntiAlias = true
        self.dayPaint.style = Paint.Style.FILL
        self.dayPaint.color = 0xFF202020.asColor()
        animationFrame.addWeak(self) { (self, timePassed) in 
            if self.draggingId == DRAGGING_NONE, self.currentOffset != Float(0) {
                var newOffset = self.currentOffset * max(Float(0), ( Float(1) - Float(8) * timePassed ))
                var min = Float(0.001)
                if newOffset > min {
                    newOffset -= min
                } else if newOffset < -min {
                    newOffset += min
                } else  {
                    newOffset = Float(0)
                }
                self.currentOffset = newOffset
            }
        }
    }
}
 
 

public enum CalendarDrawing {
    
    static public func day(canvas: Canvas, date: DateAlone, inner: RectF, paint: Paint) -> Void {
        canvas.drawTextCentered(date.day.toString(), inner.centerX(), inner.centerY(), paint)
    }
    static public func day(_ canvas: Canvas, _ date: DateAlone, _ inner: RectF, _ paint: Paint) -> Void {
        return day(canvas: canvas, date: date, inner: inner, paint: paint)
    }
    
    static public func dayFaded(canvas: Canvas, date: DateAlone, inner: RectF, paint: Paint) -> Void {
        var originalColor = paint.color
         var myPaint = paint
        myPaint.color = paint.color.colorAlpha(64)
        canvas.drawTextCentered(date.day.toString(), inner.centerX(), inner.centerY(), myPaint)
        myPaint.color = originalColor
    }
    static public func dayFaded(_ canvas: Canvas, _ date: DateAlone, _ inner: RectF, _ paint: Paint) -> Void {
        return dayFaded(canvas: canvas, date: date, inner: inner, paint: paint)
    }
    
    static public func label(canvas: Canvas, dayOfWeek: Int32, inner: RectF, paint: Paint) -> Void {
        var text = TimeNames.shortWeekdayName(dayOfWeek)
        canvas.drawTextCentered(text, inner.centerX(), inner.centerY(), paint)
    }
    static public func label(_ canvas: Canvas, _ dayOfWeek: Int32, _ inner: RectF, _ paint: Paint) -> Void {
        return label(canvas: canvas, dayOfWeek: dayOfWeek, inner: inner, paint: paint)
    }
    
    static public func dayBackground(canvas: Canvas, inner: RectF, paint: Paint) -> Void {
        canvas.drawOval(inner, paint)
    }
    static public func dayBackground(_ canvas: Canvas, _ inner: RectF, _ paint: Paint) -> Void {
        return dayBackground(canvas: canvas, inner: inner, paint: paint)
    }
    
    static public func dayBackgroundStart(canvas: Canvas, inner: RectF, outer: RectF, paint: Paint) -> Void {
        canvas.drawOval(inner, paint)
        canvas.drawRect(outer.centerX(), inner.top, outer.right, inner.bottom, paint)
    }
    static public func dayBackgroundStart(_ canvas: Canvas, _ inner: RectF, _ outer: RectF, _ paint: Paint) -> Void {
        return dayBackgroundStart(canvas: canvas, inner: inner, outer: outer, paint: paint)
    }
    
    static public func dayBackgroundMid(canvas: Canvas, inner: RectF, outer: RectF, paint: Paint) -> Void {
        canvas.drawRect(outer.left, inner.top, outer.right, inner.bottom, paint)
    }
    static public func dayBackgroundMid(_ canvas: Canvas, _ inner: RectF, _ outer: RectF, _ paint: Paint) -> Void {
        return dayBackgroundMid(canvas: canvas, inner: inner, outer: outer, paint: paint)
    }
    
    static public func dayBackgroundEnd(canvas: Canvas, inner: RectF, outer: RectF, paint: Paint) -> Void {
        canvas.drawOval(inner, paint)
        canvas.drawRect(outer.left, inner.top, outer.centerX(), inner.bottom, paint)
    }
    static public func dayBackgroundEnd(_ canvas: Canvas, _ inner: RectF, _ outer: RectF, _ paint: Paint) -> Void {
        return dayBackgroundEnd(canvas: canvas, inner: inner, outer: outer, paint: paint)
    }
}
 
 
