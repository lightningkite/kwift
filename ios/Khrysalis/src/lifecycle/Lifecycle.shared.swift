// Generated by Khrysalis Swift converter - this file will be overwritten.
// File: lifecycle/Lifecycle.shared.kt
// Package: com.lightningkite.khrysalis.lifecycle
import RxSwift
import Foundation

public typealias Lifecycle = ObservableProperty<Bool>

public extension ObservableProperty where T == Bool {
    func and(other: ObservableProperty< Bool>) -> Lifecycle { return self.combine(other: other, combiner: { (a: Bool, b: Bool) -> Bool in a && b }) }
}
public extension ObservableProperty where T == Bool {
    func openCloseBinding<A : AnyObject>(target: A, `open`: @escaping  (A) -> Void, close: @escaping  (A) -> Void) -> Void {
        var lastValue = self.value
        if self.value {
            `open`(target)
        }
        self.addAndRunWeak(referenceA: target, listener: { (target: A, value: Bool) -> Void in 
                if lastValue, !value {
                    close(target)
                }
                if !lastValue, value {
                    `open`(target)
                }
                lastValue = value
        })
    }
}
public extension ObservableProperty where T == Bool {
    func openCloseBinding(`open`: @escaping  () -> Void, close: @escaping  () -> Void) -> Void {
        var lastValue = self.value
        if self.value {
            `open`()
        }
        let everlasting = self.observableNN.subscribeBy(onNext: { (value: Bool) -> Void in 
                if lastValue, !value {
                    close()
                }
                if !lastValue, value {
                    `open`()
                }
                lastValue = value
        })
    }
}

public extension ObservableProperty where T == Bool {
    func once() -> ObservableProperty<Bool> { return OnceObservableProperty(basedOn: self) }
}

private class OnceObservableProperty : ObservableProperty<Bool> {
    public var basedOn: ObservableProperty<Bool>
    public init(basedOn: ObservableProperty<Bool>) {
        self.basedOn = basedOn
        super.init()
    }
    
    override public var value: Bool {
        get { return self.basedOn.value }
    }
    override public var onChange: Observable<Bool> {
        get { return self.basedOn.onChange.take(1) }
    }
    
}

public extension ObservableProperty where T == Bool {
    func closeWhenOff(closeable: Disposable) -> Void {
        var listener: Disposable? = nil
        listener = self.observableNN.subscribe({ (it: Bool) -> Void in if !it {
                    closeable.dispose()
                    listener?.dispose()
        } })
    }
}

public let appInForeground = StandardObservableProperty(underlyingValue: false)

