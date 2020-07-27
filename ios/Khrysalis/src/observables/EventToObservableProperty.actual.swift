import RxSwift
import RxRelay

//--- Observable<T>.asObservablePropertyUnboxed(T)
extension Observable {
    public func asObservablePropertyNullable(defaultValue: Element) -> ObservableProperty<Element> {
        return EventToObservableProperty<Element>(value: defaultValue, wrapped: self)
    }
    public func asObservablePropertyNullable(_ defaultValue: Element) -> ObservableProperty<Element> {
        return asObservablePropertyNullable(defaultValue: defaultValue)
    }
}