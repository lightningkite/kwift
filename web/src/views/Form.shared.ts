// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: views/Form.shared.kt
// Package: com.lightningkite.khrysalis.views
import { ViewString, ViewStringRaw, ViewStringResource, ViewStringTemplate, kotlinCollectionsListJoinToViewString } from './Strings.shared'
import { safeEq } from '../Kotlin'
import { showDialogAlert } from './showDialog.shared'
import { kotlinCharSequenceIsBlank } from '../kotlin/kotlin.text'
import { StandardObservableProperty } from '../observables/StandardObservableProperty.shared'
import { listFilterNotNull } from '../KotlinCollections'
import { MutableObservableProperty } from '../observables/MutableObservableProperty.shared'

//! Declares com.lightningkite.khrysalis.views.FormValidationError
export class FormValidationError {
    public readonly field: UntypedFormField;
    public readonly _string: ViewString;
    public constructor(field: UntypedFormField, _string: ViewString) {
        this.field = field;
        this._string = _string;
    }
}

//! Declares com.lightningkite.khrysalis.views.UntypedFormField
export interface UntypedFormField {
    
    readonly name: ViewString;
    
    readonly untypedObservable: any;
    
    readonly validation: ((a: UntypedFormField) => (ViewString | null));
    
    readonly error: StandardObservableProperty<(ViewString | null)>;
    
}


//! Declares com.lightningkite.khrysalis.views.FormField
export class FormField<T> implements UntypedFormField {
    public static implementsInterfaceComLightningkiteKhrysalisViewsUntypedFormField = true;
    public readonly name: ViewString;
    public readonly observable: MutableObservableProperty<T>;
    public readonly validation:  ((a: UntypedFormField) => (ViewString | null));
    public constructor(name: ViewString, observable: MutableObservableProperty<T>, validation:  ((a: UntypedFormField) => (ViewString | null))) {
        this.name = name;
        this.observable = observable;
        this.validation = validation;
        this.error = new StandardObservableProperty<(ViewString | null)>(null, undefined);
    }
    
    public readonly error: StandardObservableProperty<(ViewString | null)>;
    
    //! Declares com.lightningkite.khrysalis.views.FormField.value
    public get value(): T { return this.observable.value; }
    public set value(value: T) {
        this.observable.value = value;
    }
    
    //! Declares com.lightningkite.khrysalis.views.FormField.untypedObservable
    public get untypedObservable(): any { return this.observable; }
    
}

//! Declares com.lightningkite.khrysalis.views.Form
export class Form {
    public constructor() {
        this.fields = [];
    }
    
    
    
    
    public readonly fields: Array<UntypedFormField>;
    
    
    public field<T>(name: ViewString, defaultValue: T, validation:  ((a: FormField<T>) => (ViewString | null))): FormField<T> {
        const obs = new StandardObservableProperty<(any | null)>(defaultValue, undefined);
        
        const field = new FormField<(any | null)>(name, obs, (untypedField: UntypedFormField): (ViewString | null) => validation(untypedField as FormField<T>));
        
        this.fields.push(field);
        return field;
    }
    
    public fieldRes<T>(name: string, defaultValue: T, validation:  ((a: FormField<T>) => (ViewString | null))): FormField<T> { return this.field<(any | null)>(new ViewStringResource(name), defaultValue, validation); }
    
    public fieldFromProperty<T>(name: ViewString, property: MutableObservableProperty<T>, validation:  ((a: FormField<T>) => (ViewString | null))): FormField<T> {
        const field = new FormField<(any | null)>(name, property, (untypedField: UntypedFormField): (ViewString | null) => validation(untypedField as FormField<T>));
        
        this.fields.push(field);
        return field;
    }
    
    public fieldFromPropertyRes<T>(name: string, property: MutableObservableProperty<T>, validation:  ((a: FormField<T>) => (ViewString | null))): FormField<T> { return this.fieldFromProperty<(any | null)>(new ViewStringResource(name), property, validation); }
    
    public check(): Array<FormValidationError> {
        return listFilterNotNull(this.fields.map((it: UntypedFormField): (FormValidationError | null) => {
                    const result = this.checkField(it);
                    
                    if (result !== null) {
                        return new FormValidationError(it, result);
                    } else {
                        return null;
                    }
        }));
    }
    
    public runOrDialog(action: (() => void)): void {
        const errors = this.check();
        
        if (errors.length !== 0) {
            showDialogAlert(kotlinCollectionsListJoinToViewString(errors.map((it: FormValidationError): ViewString => it._string), undefined));
        } else {
            action();
        }
    }
    
    public checkField(field: UntypedFormField): (ViewString | null) {
        const result = field.validation(field);
        
        field.error.value = result;
        return result;
    }
}
export namespace Form {
    //! Declares com.lightningkite.khrysalis.views.Form.Companion
    export class Companion {
        private constructor() {
            this.xIsRequired = new ViewStringRaw(`%1\$s is required.`);
            this.xMustMatchY = new ViewStringRaw(`%1\$s must match %2\$s.`);
        }
        public static INSTANCE = new Companion();
        
        public xIsRequired: ViewString;
        
        public xMustMatchY: ViewString;
        
    }
}

//! Declares com.lightningkite.khrysalis.views.required>com.lightningkite.khrysalis.views.FormField<kotlin.String>
export function comLightningkiteKhrysalisViewsFormFieldRequired(this_: FormField<string>): (ViewString | null) {
    if (kotlinCharSequenceIsBlank(this_.observable.value)) {
        return new ViewStringTemplate(Form.Companion.INSTANCE.xIsRequired, [this_.name]);
    } else {
        return null;
    }
}

//! Declares com.lightningkite.khrysalis.views.notNull>com.lightningkite.khrysalis.views.FormField<kotlin.Any>
export function comLightningkiteKhrysalisViewsFormFieldNotNull<T>(this_: FormField<T>): (ViewString | null) {
    if (this_.observable.value === null) {
        return new ViewStringTemplate(Form.Companion.INSTANCE.xIsRequired, [this_.name]);
    } else {
        return null;
    }
}

//! Declares com.lightningkite.khrysalis.views.notFalse>com.lightningkite.khrysalis.views.FormField<kotlin.Boolean>
export function comLightningkiteKhrysalisViewsFormFieldNotFalse(this_: FormField<boolean>): (ViewString | null) {
    if ((!this_.observable.value)) {
        return new ViewStringTemplate(Form.Companion.INSTANCE.xIsRequired, [this_.name]);
    } else {
        return null;
    }
}

//! Declares com.lightningkite.khrysalis.views.unless>com.lightningkite.khrysalis.views.ViewString
export function comLightningkiteKhrysalisViewsViewStringUnless(this_: ViewString, condition: boolean): (ViewString | null) {
    if (condition) {
        return null;
    } else {
        return this_;
    }
}


//! Declares com.lightningkite.khrysalis.views.matches>com.lightningkite.khrysalis.views.FormField<kotlin.Any>
export function comLightningkiteKhrysalisViewsFormFieldMatches<T extends any>(this_: FormField<T>, other: FormField<T>): (ViewString | null) {
    if (!safeEq(this_.observable.value, other.observable.value)) {
        return new ViewStringTemplate(Form.Companion.INSTANCE.xMustMatchY, [this_.name, other.name]);
    } else {
        return null;
    }
}

//object test {
    //    val form = Form()
    //
    //    val username = Field(ViewStringRaw("Username"), "") { it.required() ?: it.isEmail() ?: it.matches(otherField) }
    //    val password = Field(ViewStringRaw("Password"), "") { it.required() }
    //    val verifyPassword = Field(ViewStringRaw("Verify Password"), "") { it.required() ?: it.matches(password) }
//}

