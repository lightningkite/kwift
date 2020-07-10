// Generated by Khrysalis Swift converter - this file will be overwritten.
// File: observables/ObservableProperty.weak.shared.kt
// Package: com.lightningkite.khrysalis.observables
import RxSwift
import Foundation

public extension ObservableProperty {
    func addAndRunWeak<A : AnyObject>(referenceA: A, listener: @escaping  (A, T) -> Void) -> Disposable { return self.observable.addWeak(referenceA: referenceA, listener: { (a: Any, value: Any) -> Void in listener(
                    a,
                    value
    ) }) }
}

public extension ObservableProperty {
    func addAndRunWeak<A : AnyObject, B : AnyObject>(referenceA: A, referenceB: B, listener: @escaping  (A, B, T) -> Void) -> Disposable { return self.observable.addWeak(referenceA: referenceA, referenceB: referenceB, listener: { (a: Any, b: Any, value: Any) -> Void in listener(
                    a,
                    b,
                    value
    ) }) }
}

public extension ObservableProperty {
    func addAndRunWeak<A : AnyObject, B : AnyObject, C : AnyObject>(referenceA: A, referenceB: B, referenceC: C, listener: @escaping  (A, B, C, T) -> Void) -> Disposable { return self.observable.addWeak(referenceA: referenceA, referenceB: referenceB, referenceC: referenceC, listener: { (a: Any, b: Any, c: Any, value: Any) -> Void in listener(
                    a,
                    b,
                    c,
                    value
    ) }) }
}
