//Package: com.lightningkite.khrysalis.observables
//Converted using Khrysalis2

import Foundation
import RxSwift
import RxRelay



public class CombineObservableProperty<T, A, B>: ObservableProperty<T> {
    
    public var observableA: ObservableProperty<A>
    public var observableB: ObservableProperty<B>
    public var combiner:  (A, B) -> T
    
    override public var value: T {
        get {
            return combiner(observableA.value, observableB.value)
        }
    }
    override public var onChange: Observable<Box<T>> {
        get {
            return observableA.onChange.startWith(Box.wrap(observableA.value)).combineLatest(observableB.onChange.startWith(Box.wrap(observableB.value))) { (a: Box<A>, b: Box<B>) in 
                boxWrap(self.combiner(a.value, b.value))
            }.skip(1)
        }
    }
    
    public init(observableA: ObservableProperty<A>, observableB: ObservableProperty<B>, combiner: @escaping (A, B) -> T) {
        self.observableA = observableA
        self.observableB = observableB
        self.combiner = combiner
        super.init()
    }
    convenience public init(_ observableA: ObservableProperty<A>, _ observableB: ObservableProperty<B>, _ combiner: @escaping (A, B) -> T) {
        self.init(observableA: observableA, observableB: observableB, combiner: combiner)
    }
}
 
 

extension ObservableProperty {
    public func combine<B, C>(other: ObservableProperty<B>, combiner: @escaping (T, B) -> C) -> ObservableProperty<C> {
        return CombineObservableProperty(self, other, combiner)
    }
    public func combine<B, C>(_ other: ObservableProperty<B>, _ combiner: @escaping (T, B) -> C) -> ObservableProperty<C> {
        return combine(other: other, combiner: combiner)
    }
}
 
