// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: views/ViewGenerator.shared.kt
// Package: com.lightningkite.khrysalis.views
import { newEmptyView } from './EmptyView.actual'

//! Declares com.lightningkite.khrysalis.views.ViewGenerator
export abstract class ViewGenerator {
    protected constructor() {
    }
    
    public abstract readonly title: string;
    
    
    public abstract generate(dependency: Window): HTMLElement 
    
    
}
export namespace ViewGenerator {
    //! Declares com.lightningkite.khrysalis.views.ViewGenerator.Default
    export class Default extends ViewGenerator {
        public constructor() {
            super();
        }
        
        //! Declares com.lightningkite.khrysalis.views.ViewGenerator.Default.title
        public get title(): string { return "Empty"; }
        
        
        public generate(dependency: Window): HTMLElement { return newEmptyView(dependency); }
        
    }
}

