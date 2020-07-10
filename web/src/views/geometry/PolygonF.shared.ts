// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: views/geometry/PolygonF.shared.kt
// Package: com.lightningkite.khrysalis.views.geometry
import { NumberRange, safeEq } from '../../Kotlin'

//! Declares com.lightningkite.khrysalis.views.geometry.PolygonF
export class PolygonF {
    public readonly points: Array<DOMPoint>;
    public constructor(points: Array<DOMPoint>) {
        this.points = points;
    }
    public hashCode(): number {
        let hash = 17;
        hash = 31 * hash + this.points?.hashCode() ?? 0;
        return hash;
    }
    public equals(other: any): boolean { return other instanceof PolygonF && safeEq(this.points, other.points) }
    public toString(): string { return `PolygonF(points = ${this.points})` }
    public copy(points: Array<DOMPoint> = this.points) { return new PolygonF(points); }
    
    public contains(point: DOMPoint): boolean {
        let inside = false;
        
        const big = 1000;
        
        for (const index of (new NumberRange(0, this.points.length - 2 - 1))) {
            const a = this.points[index];
            
            const b = this.points[index + 1];
            
            const denom = -(big - point.x) * (b.y - a.y);
            
            if (denom === 0) continue;
            const ua = ((big - point.x) * (a.y - point.y)) / denom;
            
            const ub = ((b.x - a.x) * (a.y - point.y) - (b.y - a.y) * (a.x - point.x)) / denom;
            
            if (ua >= 0.0 && ua <= 1.0 && ub >= 0.0 && ub <= 1.0) {
                inside = !inside;
            }
        }
        return inside;
    }
}
