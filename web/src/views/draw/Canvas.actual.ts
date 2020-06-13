// Generated by Khrysalis TypeScript converter
// File: views/draw/Canvas.actual.kt
// Package: com.lightningkite.khrysalis.views.draw
import { Paint, getAndroidGraphicsPaintTextHeight } from './Paint.actual'
import { Align, AlignPair } from '../geometry/Align.shared'

//! Declares com.lightningkite.khrysalis.views.draw.drawTextCentered>android.graphics.Canvas
export function androidGraphicsCanvasDrawTextCentered(this_: CanvasRenderingContext2D, text: string, centerX: number, centerY: number, paint: Paint): void{
    this_.textBaseline = "middle"
    this_.textAlign = "center"
    paint.text(this_, text, centerX, centerY)
}
//! Declares com.lightningkite.khrysalis.views.draw.drawText>android.graphics.Canvas
export function androidGraphicsCanvasDrawText(this_: CanvasRenderingContext2D, text: string, x: number, y: number, gravity: AlignPair, paint: Paint): void{
    switch(gravity.vertical){
        case Align.start:
            this_.textBaseline = "top"
            break;
        case Align.center:
        case Align.fill:
            this_.textBaseline = "middle"
            break;
        case Align.end:
            this_.textBaseline = "bottom"
            break;
    }
    this_.textAlign = "center"
    paint.text(this_, text, x, y)
}

export function androidGraphicsCanvasDrawBitmap(this_: CanvasRenderingContext2D, bitmap: ImageBitmap, left: number, top: number, right: number, bottom: number): void{
    this_.drawImage(bitmap, left, top, right - left, bottom - top);
}


/*

CUSTOM DRAWING

- Custom view w/ touch interaction
- Calendar view custom callback - perhaps override on both sides, and it'd be OK?

MonthView - Can take custom renderer and touch handler
CalendarView - Can take MonthView generator, show specific months

*/
