// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: time/ClockPartSize.shared.kt
// Package: com.lightningkite.khrysalis.time

//! Declares com.lightningkite.khrysalis.time.ClockPartSize
export class ClockPartSize {
    private constructor(name: string) {
        this.name = name;
    }
    public static None = new ClockPartSize("None");
    public static Short = new ClockPartSize("Short");
    public static Medium = new ClockPartSize("Medium");
    public static Long = new ClockPartSize("Long");
    public static Full = new ClockPartSize("Full");
    private static _values: Array<ClockPartSize> = [ClockPartSize.None, ClockPartSize.Short, ClockPartSize.Medium, ClockPartSize.Long, ClockPartSize.Full];
    public static values(): Array<ClockPartSize> { return ClockPartSize._values; }
    public readonly name: string;
    public static valueOf(name: string): ClockPartSize { return (ClockPartSize as any)[name]; }
    public toString(): string { return this.name }
}

