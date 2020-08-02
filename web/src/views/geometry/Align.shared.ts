// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: views/geometry/Align.shared.kt
// Package: com.lightningkite.khrysalis.views.geometry
import { safeEq } from '../../Kotlin'

//! Declares com.lightningkite.khrysalis.views.geometry.Align
export class Align {
    private constructor(name: string) {
        this.name = name;
    }
    
    public static start = new Align("start");
    public static center = new Align("center");
    public static end = new Align("end");
    public static fill = new Align("fill");
    
    private static _values: Array<Align> = [Align.start, Align.center, Align.end, Align.fill];
    public static values(): Array<Align> { return Align._values; }
    public readonly name: string;
    public static valueOf(name: string): Align { return (Align as any)[name]; }
    public toString(): string { return this.name }
    public toJSON(): string { return this.name }
}

//! Declares com.lightningkite.khrysalis.views.geometry.AlignPair
export class AlignPair {
    public readonly horizontal: Align;
    public readonly vertical: Align;
    public constructor(horizontal: Align, vertical: Align) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }
    public hashCode(): number {
        let hash = 17;
        hash = 31 * hash + this.horizontal?.hashCode() ?? 0;
        hash = 31 * hash + this.vertical?.hashCode() ?? 0;
        return hash;
    }
    public equals(other: any): boolean { return other instanceof AlignPair && safeEq(this.horizontal, other.horizontal) && safeEq(this.vertical, other.vertical) }
    public toString(): string { return `AlignPair(horizontal = ${this.horizontal}, vertical = ${this.vertical})` }
    public copy(horizontal: Align = this.horizontal, vertical: Align = this.vertical): AlignPair { return new AlignPair(horizontal, vertical); }
    
    
    
    
}
export namespace AlignPair {
    //! Declares com.lightningkite.khrysalis.views.geometry.AlignPair.Companion
    export class Companion {
        private constructor() {
            this.center = new AlignPair(Align.center, Align.center);
            this.fill = new AlignPair(Align.fill, Align.fill);
            this.topLeft = new AlignPair(Align.start, Align.start);
            this.topCenter = new AlignPair(Align.center, Align.start);
            this.topFill = new AlignPair(Align.fill, Align.start);
            this.topRight = new AlignPair(Align.end, Align.start);
            this.centerLeft = new AlignPair(Align.start, Align.center);
            this.centerCenter = new AlignPair(Align.center, Align.center);
            this.centerFill = new AlignPair(Align.fill, Align.center);
            this.centerRight = new AlignPair(Align.end, Align.center);
            this.fillLeft = new AlignPair(Align.start, Align.fill);
            this.fillCenter = new AlignPair(Align.center, Align.fill);
            this.fillFill = new AlignPair(Align.fill, Align.fill);
            this.fillRight = new AlignPair(Align.end, Align.fill);
            this.bottomLeft = new AlignPair(Align.start, Align.end);
            this.bottomCenter = new AlignPair(Align.center, Align.end);
            this.bottomFill = new AlignPair(Align.fill, Align.end);
            this.bottomRight = new AlignPair(Align.end, Align.end);
        }
        public static INSTANCE = new Companion();
        
        public readonly center: AlignPair;
        
        public readonly fill: AlignPair;
        
        
        public readonly topLeft: AlignPair;
        
        public readonly topCenter: AlignPair;
        
        public readonly topFill: AlignPair;
        
        public readonly topRight: AlignPair;
        
        public readonly centerLeft: AlignPair;
        
        public readonly centerCenter: AlignPair;
        
        public readonly centerFill: AlignPair;
        
        public readonly centerRight: AlignPair;
        
        public readonly fillLeft: AlignPair;
        
        public readonly fillCenter: AlignPair;
        
        public readonly fillFill: AlignPair;
        
        public readonly fillRight: AlignPair;
        
        public readonly bottomLeft: AlignPair;
        
        public readonly bottomCenter: AlignPair;
        
        public readonly bottomFill: AlignPair;
        
        public readonly bottomRight: AlignPair;
        
    }
}

