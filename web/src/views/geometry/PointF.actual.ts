// Generated by Khrysalis TypeScript converter
// File: views/geometry/RectF.actual.kt
// Package: com.lightningkite.khrysalis.views.geometry

//! Declares android.graphics.PointF
export class PointF {
    x: number = 0.0;
    y: number = 0.0;

    constructor(data: DOMPointInit | DOMPoint)
    constructor(x: number, y: number)
    constructor()
    constructor(x?: (number | DOMPointInit | DOMPoint), y?: number) {
        if(y){
            this.x = x as number;
            this.y = y;
        } else if(x){
            this.x = (x as DOMPointInit).x
            this.y = (x as DOMPointInit).y
        }
    }
}