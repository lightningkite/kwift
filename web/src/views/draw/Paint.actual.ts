// Generated by Khrysalis TypeScript converter
// File: views/draw/Paint.actual.kt
// Package: com.lightningkite.khrysalis.views.draw

/* SHARED DECLARATIONS
class Paint(){
    var flags: Int
    var color: ColorValue
    var strokeWidth: Float
    var alpha: Float
    var style: Style
    var textSize: Float
    var shader: ShaderValue
    var isAntiAlias: Boolean
    var isFakeBoldText: Boolean

    enum class Style {
        FILL, STROKE, FILL_AND_STROKE
    }

    fun measureText(text: String): Float
}
 */

//! Declares android.graphics.Paint
import {CanvasGradientMaker} from "./LinearGradient.actual";

export class Paint {
    color: string = "#000000";
    strokeWidth: number = 1.0;
    alpha: number = 1.0;
    style: Paint.Style = Paint.Style.FILL;
    textSize: number = 12.0;
    shader: CanvasGradientMaker | null = null;
    isAntiAlias: boolean = false;
    isFakeBoldText: boolean = false;

    apply(context: CanvasRenderingContext2D){
        if(this.shader){
            const gradient = this.shader(context);
            context.fillStyle = gradient;
            context.strokeStyle = gradient;
        } else {
            context.fillStyle = this.color;
            context.strokeStyle = this.color;
        }
        context.lineWidth = this.strokeWidth;
        context.globalAlpha = this.alpha;
        context.imageSmoothingEnabled = this.isAntiAlias;
    }
    complete(context: CanvasRenderingContext2D){
        this.apply(context);
        switch(this.style){
            case Paint.Style.FILL:
                context.fill();
                break;
            case Paint.Style.STROKE:
                context.stroke();
                break;
            case Paint.Style.FILL_AND_STROKE:
                context.fill();
                context.stroke();
                break;
        }
    }
    render(context: CanvasRenderingContext2D, path: Path2D){
        this.apply(context);
        switch(this.style){
            case Paint.Style.FILL:
                context.fill(path);
                break;
            case Paint.Style.STROKE:
                context.stroke(path);
                break;
            case Paint.Style.FILL_AND_STROKE:
                context.fill(path);
                context.stroke(path);
                break;
        }
    }
    text(context: CanvasRenderingContext2D, text: string, x: number, y: number, maxWidth?: number){
        this.apply(context);
        context.font = `${this.textSize}px ${this.isFakeBoldText ? "bold" : ""} sans-serif`;
        switch(this.style){
            case Paint.Style.FILL:
                context.fillText(text, x, y, maxWidth);
                break;
            case Paint.Style.STROKE:
                context.strokeText(text, x, y, maxWidth);
                break;
            case Paint.Style.FILL_AND_STROKE:
                context.fillText(text, x, y, maxWidth);
                context.strokeText(text, x, y, maxWidth);
                break;
        }
    }
}
export namespace Paint {
    export enum Style {
        FILL, STROKE, FILL_AND_STROKE
    }
}

//! Declares com.lightningkite.khrysalis.views.draw.textHeight
export function getAndroidGraphicsPaintTextHeight(this_: Paint): number { return 0; /*TODO*/ }


