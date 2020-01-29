//Package: com.lightningkite.khrysalis.observables
//Converted using Khrysalis2

import Foundation
import RxSwift
import RxRelay



public class ConstantObservableProperty<T>: ObservableProperty<T> {
    
    public var underlyingValue: T
    
    override public var onChange: Observable<Box<T>> { get { return _onChange } set(value) { _onChange = value } }
    override public var value: T {
        get {
            return underlyingValue
        }
    }
    
    public init(underlyingValue: T) {
        self.underlyingValue = underlyingValue
        self._onChange = Observable.never()
        super.init()
    }
    convenience public init(_ underlyingValue: T) {
        self.init(underlyingValue: underlyingValue)
    }
    private var _onChange: Observable<Box<T>>
}
 