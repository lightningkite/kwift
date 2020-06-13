// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: views/ViewGenerator.shared.kt
// Package: com.lightningkite.khrysalis.views
// FQImport: com.lightningkite.khrysalis.views.newEmptyView TS newEmptyView
// FQImport: com.lightningkite.khrysalis.views.ViewGenerator TS ViewGenerator
// FQImport: com.lightningkite.khrysalis.views.ViewGenerator.Default.generate.dependency TS dependency
import { newEmptyView } from './EmptyView.actual'

//! Declares com.lightningkite.khrysalis.views.ViewGenerator
export abstract class ViewGenerator {
    
    public abstract readonly title: string;
    
    
    public abstract generate(dependency: Window): HTMLElement 
    
    public static Default = class Default extends ViewGenerator {
        public constructor() {
            super();
        }
        
        //! Declares com.lightningkite.khrysalis.views.ViewGenerator.Default.title
        public get title(): string { return "Empty"; }
        
        
        public generate(dependency: Window): HTMLElement { return newEmptyView(dependency); }
        
    }
}
