// Generated by Khrysalis Swift converter - this file will be overwritten.
// File: views/showDialog.shared.kt
// Package: com.lightningkite.khrysalis.views
import Foundation
import RxRelay

public let lastDialog = StandardObservableProperty(underlyingValue: nil)
public let showDialogEvent: Subject<DialogRequest> = new Subject()

public class DialogRequest {
    public var string: ViewString
    public var confirmation: (() -> Void)?
    public init(string: ViewString, confirmation: (() -> Void)? = nil) {
        self.string = string
        self.confirmation = confirmation
    }
}

public func showDialog(request: DialogRequest) -> Void {
    lastDialog.value = request
    showDialogEvent.onNext(p0: request)
}

public func showDialog(message: ViewString) -> Void {
    showDialog(request: DialogRequest(string: message))
}

