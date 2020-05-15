// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: views/showDialog.shared.kt
// Package: com.lightningkite.khrysalis.views
// FQImport: com.lightningkite.khrysalis.views.showDialogEvent TS getShowDialogEvent
// FQImport: com.lightningkite.khrysalis.views.showDialogEvent SKIPPED due to same file
// FQImport: com.lightningkite.khrysalis.views.showDialog TS showDialog
// FQImport: com.lightningkite.khrysalis.views.DialogRequest TS DialogRequest
// FQImport: com.lightningkite.khrysalis.views.showDialog.message TS message
// FQImport: io.reactivex.subjects.PublishSubject.onNext TS onNext
// FQImport: com.lightningkite.khrysalis.views.lastDialog TS getLastDialog
// FQImport: com.lightningkite.khrysalis.views.showDialog.request TS request
// FQImport: com.lightningkite.khrysalis.views.showDialog SKIPPED due to same file
// FQImport: com.lightningkite.khrysalis.views.lastDialog SKIPPED due to same file
// FQImport: com.lightningkite.khrysalis.views.ViewString TS ViewString
// FQImport: com.lightningkite.khrysalis.views.DialogRequest SKIPPED due to same file
// FQImport: com.lightningkite.khrysalis.observables.StandardObservableProperty TS StandardObservableProperty
// FQImport: io.reactivex.subjects.PublishSubject TS PublishSubject
// FQImport: io.reactivex.subjects.PublishSubject.create TS create
// FQImport: com.lightningkite.khrysalis.observables.StandardObservableProperty.value TS value
import { StandardObservableProperty } from './../observables/StandardObservableProperty.shared'
import { ViewString } from './Strings.shared'

//! Declares com.lightningkite.khrysalis.views.lastDialog
export const _lastDialog = new StandardObservableProperty<(DialogRequest | null)>(null, undefined);
export function getLastDialog(): StandardObservableProperty<DialogRequest> { return _lastDialog; }

//! Declares com.lightningkite.khrysalis.views.showDialogEvent
export const _showDialogEvent: PublishSubject<DialogRequest> = PublishSubject.create();
export function getShowDialogEvent(): PublishSubject<DialogRequest> { return _showDialogEvent; }


//! Declares com.lightningkite.khrysalis.views.DialogRequest
export class DialogRequest {
    public readonly string: ViewString;
    public readonly confirmation: (() => void | null);
    public constructor( string: ViewString,  confirmation: (() => void | null) = null) {
        this.string = string;
        this.confirmation = confirmation;
    }
}

//! Declares com.lightningkite.khrysalis.views.showDialog
export function showDialog(request: DialogRequest){
    getLastDialog().value = request;
    getShowDialogEvent().onNext(request);
}

//! Declares com.lightningkite.khrysalis.views.showDialog
export function showDialog(message: ViewString){
    showDialog(new DialogRequest(message, undefined));
}

