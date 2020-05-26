// Generated by Khrysalis TypeScript converter
// File: views/draw/Canvas.actual.kt
// Package: com.lightningkite.khrysalis.views.draw
import { Align, AlignPair } from '../geometry/Align.shared'
import {getAndroidGraphicsPaintTextHeight, Paint} from './Paint.actual'

/* SHARED DECLARATIONS - see yml
typealias Canvas = Canvas

fun Canvas.clipRect(left: Float, top: Float, right: Float, bottom: Float)
fun Canvas.clipRect(rect: RectF)
fun Canvas.drawCircle(cx: Float, cy: Float, radius: Float, paint: Paint)
fun Canvas.drawRect(left: Float, top: Float, right: Float, bottom: Float, paint: Paint)
fun Canvas.drawRect(rect: RectF, paint: Paint)
fun Canvas.drawOval(left: Float, top: Float, right: Float, bottom: Float, paint: Paint)
fun Canvas.drawOval(rect: RectF, paint: Paint)
fun Canvas.drawRoundRect(left: Float, top: Float, right: Float, bottom: Float, rx: Float, ry: Float, paint: Paint)
fun Canvas.drawRoundRect(rect: RectF, rx: Float, ry: Float, paint: Paint)
fun Canvas.drawLine(x1: Float, y1: Float, x2: Float, y2: Float, paint: Paint)
fun Canvas.drawPath(path: Path, paint: Paint)
fun Canvas.save()
fun Canvas.restore()
fun Canvas.translate(dx: Float, dy: Float)
fun Canvas.scale(scaleX: Float, scaleY: Float)
fun Canvas.rotate(degrees: Float)

 */

//! Declares com.lightningkite.khrysalis.views.draw.drawTextCentered
export function androidGraphicsCanvasDrawTextCentered(this_: Canvas, text: string, centerX: number, centerY: number, paint: Paint): void{
    const textWidth = paint.measureText(text);
    
    const textHeightOffset = ((it) => it.ascent + it.descent)(getAndroidGraphicsPaintFontMetrics(paint)) / 2;
    
    this_.drawText(text, centerX - textWidth / 2, centerY - textHeightOffset, paint);
}
//! Declares com.lightningkite.khrysalis.views.draw.drawText
export function androidGraphicsCanvasDrawText(this_: Canvas, text: string, x: number, y: number, gravity: AlignPair, paint: Paint): void{
    const textWidth = paint.measureText(text);
    
    const textHeight = getAndroidGraphicsPaintTextHeight(paint);
    
    this_.drawText(text, (() => {switch(gravity.horizontal) {
                    case Align.start:
                    return x
                    break;
                    case Align.fill:
                    case Align.center:
                    return x - textWidth / 2
                    break;
                    case Align.end:
                    return x - textWidth
                    break;
                }
        })(), (() => {switch(gravity.vertical) {
                    case Align.start:
                    return y - getAndroidGraphicsPaintFontMetrics(paint).ascent
                    break;
                    case Align.fill:
                    case Align.center:
                    return y - ((it) => it.ascent + it.descent)(getAndroidGraphicsPaintFontMetrics(paint)) / 2
                    break;
                    case Align.end:
                    return y - getAndroidGraphicsPaintFontMetrics(paint).descent
                    break;
                }
    })(), paint);
}


//! Declares com.lightningkite.khrysalis.views.draw.tempRect
export let _tempRect: RectF = RectF.constructor();
export function getTempRect(): RectF { return _tempRect; }
export function setTempRect(value: RectF) { _tempRect = value; }

//! Declares com.lightningkite.khrysalis.views.draw.drawBitmap
export function androidGraphicsCanvasDrawBitmap(this_: Canvas, bitmap: Bitmap, left: number, top: number, right: number, bottom: number): void{
    getTempRect().left = left;
    getTempRect().top = top;
    getTempRect().right = right;
    getTempRect().bottom = bottom;
    this_.drawBitmap(bitmap, null, getTempRect(), null);
}


/*

CUSTOM DRAWING

- Custom view w/ touch interaction
- Calendar view custom callback - perhaps override on both sides, and it'd be OK?

MonthView - Can take custom renderer and touch handler
CalendarView - Can take MonthView generator, show specific months

*/

