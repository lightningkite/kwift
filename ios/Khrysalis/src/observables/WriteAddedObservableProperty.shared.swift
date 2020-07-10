// Generated by Khrysalis Swift converter - this file will be overwritten.
// File: observables/WriteAddedObservableProperty.shared.kt
// Package: com.lightningkite.khrysalis.observables
import RxSwift
import Foundation

public class WriteAddedObservableProperty<A> : MutableObservableProperty<A> {
    public var basedOn: ObservableProperty<A>
    public var onWrite:  (A) -> Void
    public init(basedOn: ObservableProperty<A>, onWrite: @escaping  (A) -> Void) {
        self.basedOn = basedOn
        self.onWrite = onWrite
        super.init()
    }
    
    override public var value: A {
        get { return self.basedOn.value }
        set(value) {
            self.onWrite(value)
        }
    }
    override public var onChange: Observable<A> {
        get { return self.basedOn.onChange }
    }
    override public func update() -> Void {
        //Do nothing
    }
}

public extension ObservableProperty {
    func withWrite(onWrite: @escaping  (T) -> Void) -> MutableObservableProperty<T> {
        return WriteAddedObservableProperty(basedOn: self, onWrite: onWrite)
    }
}

