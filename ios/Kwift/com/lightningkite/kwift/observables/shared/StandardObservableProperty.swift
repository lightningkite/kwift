//Package: com.lightningkite.kwift.observables.shared
//Converted using Kwift2

import Foundation



public class StandardObservableProperty<T>: MutableObservableProperty<T> {
    
    public var underlyingValue: T
    
    override public var onChange: StandardEvent<T> { get { return _onChange } set(value) { _onChange = value } }
    override public var value: T {
        get {
            return underlyingValue
        }
        set(value) {
            underlyingValue = value
            onChange.invokeAll(value: value)
        }
    }
    
    override public func update() -> Void {
        onChange.invokeAll(value: value)
    }
    
    public init(underlyingValue: T) {
        self.underlyingValue = underlyingValue
        self._onChange = StandardEvent<T>()
        super.init()
    }
    convenience public init(_ underlyingValue: T) {
        self.init(underlyingValue: underlyingValue)
    }
    private var _onChange: StandardEvent<T>
}
 
