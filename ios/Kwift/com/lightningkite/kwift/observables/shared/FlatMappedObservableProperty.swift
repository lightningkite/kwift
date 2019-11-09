//Package: com.lightningkite.kwift.observables.shared
//Converted using Kwift2

import Foundation



public class FlatMappedObservableProperty<A, B>: ObservableProperty<B> {
    
    public var basedOn: ObservableProperty<A>
    public var transformation:  (A) -> ObservableProperty<B>
    
    override public var value: B {
        get {
            return transformation(basedOn.value).value
        }
    }
    override public var onChange: Event<B> {
        get {
            return FMOPEvent(self)
        }
    }
    
    public class FMOPEvent<A, B>: Event<B> {
        
        public var fmop: FlatMappedObservableProperty<A, B>
        
        
        override public func add(listener: @escaping (B) -> Bool) -> Close {
            var current: Close = fmop.transformation(fmop.basedOn.value).onChange.add(listener: listener)
            var closeA = self.fmop.basedOn.onChange.add{ (it) in 
                current.close()
                var new = self.fmop.transformation(self.fmop.basedOn.value)
                current = new.onChange.add(listener: listener)
                listener(new.value)
                return false
            }
            return Close{ () in 
                current.close()
                closeA.close()
            }
        }
        
        public init(fmop: FlatMappedObservableProperty<A, B>) {
            self.fmop = fmop
            super.init()
        }
        convenience public init(_ fmop: FlatMappedObservableProperty<A, B>) {
            self.init(fmop: fmop)
        }
    }
    
    public init(basedOn: ObservableProperty<A>, transformation: @escaping (A) -> ObservableProperty<B>) {
        self.basedOn = basedOn
        self.transformation = transformation
        super.init()
    }
    convenience public init(_ basedOn: ObservableProperty<A>, _ transformation: @escaping (A) -> ObservableProperty<B>) {
        self.init(basedOn: basedOn, transformation: transformation)
    }
}
 
 

extension ObservableProperty {
    public func flatMap<B>(transformation: @escaping (T) -> ObservableProperty<B>) -> FlatMappedObservableProperty<T, B> {
        return FlatMappedObservableProperty<T, B>(self, transformation)
    }
}
 