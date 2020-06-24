// Generated by Khrysalis TypeScript converter
// File: views/Colors.actual.kt
// Package: com.lightningkite.khrysalis.views

export function numberToColor(this_: number): string {
    const alpha = this_ >>> 24 & 0xFF;
    const withoutAlpha = this_ & 0x00FFFFFF;
    return `#${withoutAlpha.toString(16).padStart(6, "0")}${alpha.toString(16).padStart(2, "0")}`;
}

export function applyAlphaToColor(this_: string, desiredAlpha: number): string {
    const withoutAlpha = this_.slice(0, 6);
    return `#${withoutAlpha}${desiredAlpha.toString(16).padStart(2, "0")}`;
}

