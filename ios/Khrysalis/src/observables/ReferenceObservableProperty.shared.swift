// Generated by Khrysalis Swift converter - this file will be overwritten.
// File: observables/ReferenceObservableProperty.shared.kt
// Package: com.lightningkite.khrysalis.observables
import RxSwift
import Foundation

public class ReferenceObservableProperty<T> : MutableObservableProperty<T> {
    public var get:  () -> T
    public var set:  (T) -> Void
    public var event: Observable<T>
    public init(get: @escaping  () -> T, set: @escaping  (T) -> Void, event: Observable<T>) {
        self.get = get
        self.set = set
        self.event = event
        super.init()
    }
    
    
    override public var onChange: Observable<T> {
        get { return self.event }
    }
    override public var value: T {
        get { return self.get() }
        set(value) {
            self.set(value)
        }
    }
    override public func update() -> Void {
        //do nothing
    }
}

