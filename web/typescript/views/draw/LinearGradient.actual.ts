// Generated by Khrysalis TypeScript converter
// File: views/draw/LinearGradient.actual.kt
// Package: com.lightningkite.khrysalis.views.draw

//! Declares com.lightningkite.khrysalis.views.draw.newLinearGradient
export function newLinearGradient(
    x0: number,
    y0: number,
    x1: number,
    y1: number,
    colors: Array<string>,
    positions: Array<number>,
    tile: Shader.TileMode
): CanvasGradientMaker {
    return (ctx) => {
        let g = ctx.createLinearGradient(x0, y0, x1, y1);
        for(let i = 0; i < colors.length; i++){
            g.addColorStop(positions[i], colors[i]);
        }
        return g;
    }
}

export namespace Shader {
    export enum TileMode {
        REPEAT
    }
}

export type CanvasGradientMaker = (context: CanvasRenderingContext2D) => CanvasGradient