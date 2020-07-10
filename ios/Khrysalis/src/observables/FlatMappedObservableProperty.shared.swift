// Generated by Khrysalis Swift converter - this file will be overwritten.
// File: observables/FlatMappedObservableProperty.shared.kt
// Package: com.lightningkite.khrysalis.observables
import RxSwift
import Foundation

public class FlatMappedObservableProperty<A, B> : ObservableProperty<B> {
    public var basedOn: ObservableProperty<A>
    public var transformation:  (A) -> ObservableProperty<B>
    public init(basedOn: ObservableProperty<A>, transformation: @escaping  (A) -> ObservableProperty<B>) {
        self.basedOn = basedOn
        self.transformation = transformation
        super.init()
    }
    
    override public var value: B {
        get { return self.transformation(self.basedOn.value).value }
    }
    override public var onChange: Observable<B> {
        get { return self.basedOn.observable.switchMap(p0: { (it: Any) -> Observable<Any> in self.transformation(it).observable }).skip(p0: 1) }
    }
}

public extension ObservableProperty {
    func flatMap<B>(transformation: @escaping  (T) -> ObservableProperty<B>) -> FlatMappedObservableProperty<T, B> {
        return FlatMappedObservableProperty(basedOn: self, transformation: transformation)
    }
}

public class MutableFlatMappedObservableProperty<A, B> : MutableObservableProperty<B> {
    public var basedOn: ObservableProperty<A>
    public var transformation:  (A) -> MutableObservableProperty<B>
    public init(basedOn: ObservableProperty<A>, transformation: @escaping  (A) -> MutableObservableProperty<B>) {
        self.basedOn = basedOn
        self.transformation = transformation
        self.lastProperty = nil
        super.init()
    }
    
    override public var value: B {
        get { return self.transformation(self.basedOn.value).value }
        set(value) {
            self.transformation(self.basedOn.value).value = value
        }
    }
    
    public var lastProperty: MutableObservableProperty<B>?
    
    override public var onChange: Observable<B> {
        get { return self.basedOn.observable.switchMap(p0:  { (it: Any) -> Observable<Any> in 
                    let prop = self.transformation(it)
                    self.lastProperty = prop
                    return prop.observable
        }).skip(p0: 1) }
    }
    
    override public func update() -> Void {
        self.lastProperty?.update()
    }
}

public extension ObservableProperty {
    func flatMapMutable<B>(transformation: @escaping  (T) -> MutableObservableProperty<B>) -> MutableFlatMappedObservableProperty<T, B> {
        return MutableFlatMappedObservableProperty(basedOn: self, transformation: transformation)
    }
}

