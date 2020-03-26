//Stub file made with Khrysalis 2 (by Lightning Kite)
import Foundation


//--- CompoundButton.bindSelect(T, MutableObservableProperty<T>)
public extension CompoundButton {
    func bindSelect<T: Equatable>(_ myValue: T, _ selected: MutableObservableProperty<T>) -> Void {
        selected.subscribeBy { ( value) in
            let shouldBeOn = value == myValue
            if self.isOn != shouldBeOn {
                self.isOn = shouldBeOn
            }
        }.until((self as! UIView).removed)
        self.onCheckChanged = { [weak self] value in
            if value && selected.value != myValue {
                selected.value = myValue
            } else if !value && selected.value == myValue  {
                self?.isOn = true
            }
        }
    }
    func bindSelect<T: Equatable>(value: T, observable: MutableObservableProperty<T>) -> Void {
        return bindSelect(value, observable)
    }
}

//--- CompoundButton.bindSelectNullable(T, MutableObservableProperty<T?>)
public extension CompoundButton {
    func bindSelectNullable<T: Equatable>(_ myValue: T, _ selected: MutableObservableProperty<T?>) -> Void {
        selected.subscribeBy { ( value) in
            let shouldBeOn = value == myValue
            if self.isOn != shouldBeOn {
                self.isOn = shouldBeOn
            }
        }.until((self as! UIView).removed)
        self.onCheckChanged = { [weak self] value in
            if value && selected.value != myValue {
                selected.value = myValue
            } else if !value && selected.value == myValue  {
                selected.value = nil
            }
        }
    }
    func bindSelectNullable<T: Equatable>(value: T, observable: MutableObservableProperty<T?>) -> Void {
        return bindSelectNullable(value, observable)
    }
}

//--- CompoundButton.bindSelectInvert(T, MutableObservableProperty<T?>)
public extension CompoundButton {
    func bindSelectInvert<T: Equatable>(_ myValue: T, _ selected: MutableObservableProperty<T?>) -> Void {
        var suppress:Bool = false
        selected.subscribeBy { ( value) in
            if !suppress{
                suppress = true
                let shouldBeOn = value == myValue || value == nil
                if self.isOn != shouldBeOn {
                    self.isOn = shouldBeOn
                }
                suppress = false
            }
        }.until((self as! UIView).removed)
        self.onCheckChanged = { [weak self] value in
            if !suppress{
                suppress = true
                if !value && selected.value == myValue {
                    selected.value = nil
                    self?.isOn = true
                } else if selected.value != myValue  {
                    selected.value = myValue
                    self?.isOn = true
                }
                suppress = false
            }
        }
    }
    func bindSelectInvert<T: Equatable>(value: T, observable: MutableObservableProperty<T?>) -> Void {
        return bindSelectInvert(value, observable)
    }
}

//--- CompoundButton.bind(MutableObservableProperty<Boolean>)
public extension CompoundButton {
    func bind(_ observable: MutableObservableProperty<Bool>) -> Void {
        observable.subscribeBy { ( value) in
            if self.isOn != value {
                self.isOn = value
            }
        }.until((self as! UIView).removed)
        self.onCheckChanged = { [weak self] value in
            if observable.value != self?.isOn {
                observable.value = self?.isOn ?? false
            }
        }
    }
    func bind(observable: MutableObservableProperty<Bool>) -> Void {
        return bind(observable)
    }
}
