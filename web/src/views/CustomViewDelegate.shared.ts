// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: views/CustomViewDelegate.shared.kt
// Package: com.lightningkite.khrysalis.views
// FQImport: android.util.DisplayMetrics TS DisplayMetrics
// FQImport: com.lightningkite.khrysalis.views.CustomViewDelegate.customView TS customView
// FQImport: com.lightningkite.khrysalis.views.CustomViewDelegate.sizeThatFitsHeight.height TS height
// FQImport: com.lightningkite.khrysalis.views.CustomViewDelegate.sizeThatFitsWidth.width TS width
import { DisplayMetrics } from './DisplayMetrics.actual'
import { customViewInvalidate } from './CustomView.actual'

//! Declares com.lightningkite.khrysalis.views.CustomViewDelegate
export abstract class CustomViewDelegate {
    
    public customView: (HTMLCanvasElement | null);
    
    public abstract generateAccessibilityView(): (HTMLElement | null) 
    public abstract draw(canvas: CanvasRenderingContext2D, width: number, height: number, displayMetrics: DisplayMetrics): void 
    public onTouchDown(id: number, x: number, y: number, width: number, height: number): boolean { return false; }
    public onTouchMove(id: number, x: number, y: number, width: number, height: number): boolean { return false; }
    public onTouchCancelled(id: number, x: number, y: number, width: number, height: number): boolean { return false; }
    public onTouchUp(id: number, x: number, y: number, width: number, height: number): boolean { return false; }
    public sizeThatFitsWidth(width: number, height: number): number { return width; }
    public sizeThatFitsHeight(width: number, height: number): number { return height; }
    
    public invalidate(): void { const temp138 = this.customView;
    if(temp138 !== null) customViewInvalidate(temp138); }
    public postInvalidate(): void { const temp139 = this.customView;
    if(temp139 !== null) customViewInvalidate(temp139); }
}
