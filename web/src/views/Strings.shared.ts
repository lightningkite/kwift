// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: views/Strings.shared.kt
// Package: com.lightningkite.khrysalis.views
import { vsprintf } from 'sprintf-js'
import { checkIsInterface, tryCastInterface } from '../Kotlin'

//! Declares com.lightningkite.khrysalis.views.ViewString
export interface ViewString {
    
    get(dependency: Window): string 
}


//! Declares com.lightningkite.khrysalis.views.ViewStringRaw
export class ViewStringRaw implements ViewString {
    public static implementsInterfaceComLightningkiteKhrysalisViewsViewString = true;
    public readonly _string: string;
    public constructor(_string: string) {
        this._string = _string;
    }
    
    public get(dependency: Window): string { return this._string; }
}

//! Declares com.lightningkite.khrysalis.views.ViewStringResource
export class ViewStringResource implements ViewString {
    public static implementsInterfaceComLightningkiteKhrysalisViewsViewString = true;
    public readonly resource: string;
    public constructor(resource: string) {
        this.resource = resource;
    }
    
    public get(dependency: Window): string { return this.resource; }
}

//! Declares com.lightningkite.khrysalis.views.ViewStringTemplate
export class ViewStringTemplate implements ViewString {
    public static implementsInterfaceComLightningkiteKhrysalisViewsViewString = true;
    public readonly template: ViewString;
    public readonly _arguments: Array<any>;
    public constructor(template: ViewString, _arguments: Array<any>) {
        this.template = template;
        this._arguments = _arguments;
    }
    
    public get(dependency: Window): string {
        const templateResolved = this.template.get(dependency);
        
        const fixedArguments = this._arguments.map((it: any): any => (tryCastInterface<ViewString>(it, "ComLightningkiteKhrysalisViewsViewString"))?.get(dependency) ?? it);
        
        return vsprintf(templateResolved, fixedArguments);
    }
}

//! Declares com.lightningkite.khrysalis.views.ViewStringComplex
export class ViewStringComplex implements ViewString {
    public static implementsInterfaceComLightningkiteKhrysalisViewsViewString = true;
    public readonly getter:  ((a: Window) => string);
    public constructor(getter:  ((a: Window) => string)) {
        this.getter = getter;
    }
    
    public get(dependency: Window): string { return this.getter(dependency); }
}

//! Declares com.lightningkite.khrysalis.views.ViewStringList
export class ViewStringList implements ViewString {
    public static implementsInterfaceComLightningkiteKhrysalisViewsViewString = true;
    public readonly parts: Array<ViewString>;
    public readonly separator: string;
    public constructor(parts: Array<ViewString>, separator: string = `\n`) {
        this.parts = parts;
        this.separator = separator;
    }
    
    public get(dependency: Window): string {
        return this.parts.map((it: ViewString): string => it.get(dependency)).join(this.separator);
    }
}

//! Declares com.lightningkite.khrysalis.views.joinToViewString>kotlin.collections.List<com.lightningkite.khrysalis.views.ViewString>
export function kotlinCollectionsListJoinToViewString(this_: Array< ViewString>, separator: string = `\n`): ViewString {
    if (this_.length === 1) {
        return this_[0];
    }
    return new ViewStringList(this_, separator);
}

//! Declares com.lightningkite.khrysalis.views.toDebugString>com.lightningkite.khrysalis.views.ViewString
export function comLightningkiteKhrysalisViewsViewStringToDebugString(this_: ViewString): string {
    const thing = this_;
    
    if (thing instanceof ViewStringRaw) {
        return thing._string
    } else if (thing instanceof ViewStringResource) {
        return thing.resource.toString()
    } else if (thing instanceof ViewStringTemplate) {
        return comLightningkiteKhrysalisViewsViewStringToDebugString(thing.template) + "(" + thing._arguments.map((it: any): string => ((): string => {if (checkIsInterface<ViewString>(it, "ComLightningkiteKhrysalisViewsViewString")) return comLightningkiteKhrysalisViewsViewStringToDebugString(it); else return `${it}`;})()).join(", ") + ")"
    } else if (thing instanceof ViewStringList) {
        return thing.parts.map((it: ViewString): string => comLightningkiteKhrysalisViewsViewStringToDebugString(it)).join(thing.separator)
    } else if (thing instanceof ViewStringComplex) {
        return `<Complex string ${thing}>`
    } else  {
        return "Unknown"
    }
}

