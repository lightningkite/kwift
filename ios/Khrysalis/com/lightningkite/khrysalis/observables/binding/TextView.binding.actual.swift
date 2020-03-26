//Stub file made with Khrysalis 2 (by Lightning Kite)
import Foundation
import UIKit


//--- TextView.bindString(ObservableProperty<String>)
public extension UILabel {
    func bindString(_ observable: ObservableProperty<String>) -> Void {
        observable.subscribeBy { ( value) in
            if this.textString != value {
                this.textString = value
            }
            this.superview?.setNeedsLayout()
        }.until(self.removed)
    }
    func bindString(observable: ObservableProperty<String>) -> Void {
        return bindString(observable)
    }
}
public extension UITextView {
    func bindString(_ observable: ObservableProperty<String>) {
        return bindString(observable: observable)
    }
    func bindString(observable: ObservableProperty<String>) {
        observable.subscribeBy { ( value) in
            if this.textString != value {
                this.textString = value
            }
            this.superview?.setNeedsLayout()
        }.until(self.removed)
    }
}
public extension UITextField {
    func bindString(_ observable: ObservableProperty<String>) {
        return bindString(observable: observable)
    }
    func bindString(observable: ObservableProperty<String>) {
        observable.subscribeBy { ( value) in
            if this.textString != value {
                this.textString = value
            }
            this.superview?.setNeedsLayout()
        }.until(self.removed)
    }
}
public extension UIButton {
    func bindString(_ observable: ObservableProperty<String>) {
        return bindString(observable: observable)
    }
    func bindString(observable: ObservableProperty<String>) {
        observable.subscribeBy { ( value) in
            if this.title(for: .normal) != value {
                this.textString = value
            }
            this.superview?.setNeedsLayout()
        }.until(self.removed)
    }
}

//--- TextView.bindStringRes(ObservableProperty<StringResource?>)
public extension UILabel {
    func bindStringRes(_ observable: ObservableProperty<StringResource?>) -> Void {
        observable.subscribeBy { ( value) in
            if let value = value {
                let localValue = NSLocalizedString(value, comment: "")
                if this.textString != localValue {
                    this.textString = localValue
                }
            } else {
                this.text = nil
            }
            this.superview?.setNeedsLayout()
        }.until(self.removed)
    }
    func bindStringRes(observable: ObservableProperty<StringResource?>) -> Void {
        return bindStringRes(observable)
    }
}
public extension UITextView {
    func bindStringRes(_ observableReference: ObservableProperty<StringResource?>) {
        return bindStringRes(observableReference: observableReference)
    }
    func bindStringRes(observableReference: ObservableProperty<StringResource?>) {
        observableReference.subscribeBy { ( value) in
            if let value = value {
                let localValue = NSLocalizedString(value, comment: "")
                if this.textString != localValue {
                    this.textString = localValue
                }
            } else {
                this.text = nil
            }
            this.superview?.setNeedsLayout()
        }.until(self.removed)
    }
}
public extension UITextField {
    func bindStringRes(_ observableReference: ObservableProperty<StringResource?>) {
        return bindStringRes(observableReference: observableReference)
    }
    func bindStringRes(observableReference: ObservableProperty<StringResource?>) {
        observableReference.subscribeBy { ( value) in
            if let value = value {
                let localValue = NSLocalizedString(value, comment: "")
                if this.textString != localValue {
                    this.textString = localValue
                }
            } else {
                this.text = nil
            }
            this.superview?.setNeedsLayout()
        }.until(self.removed)
    }
}
public extension UIButton {
    func bindStringRes(_ observableReference: ObservableProperty<StringResource?>) {
        return bindStringRes(observableReference: observableReference)
    }
    func bindStringRes(observableReference: ObservableProperty<StringResource?>) {
        observableReference.subscribeBy { ( value) in
            if let value = value {
                if this.title(for: .normal) != value {
                    this.textString = value
                }
            } else {
                this.textString = ""
            }
            this.superview?.setNeedsLayout()
        }.until(self.removed)
    }
}

//--- TextView.bindText(ObservableProperty<T>, (T)->String)
public extension UILabel {
    func bindText<T>(_ observable: ObservableProperty<T>, _ transform: @escaping (T) -> String) -> Void {
        observable.subscribeBy { ( value) in
            let textValue = transform(value)
            if this.textString != textValue {
                this.textString = textValue
            }
            this.superview?.setNeedsLayout()
        }.until(self.removed)
    }
    func bindText<T>(observable: ObservableProperty<T>, transform: @escaping (T) -> String) -> Void {
        return bindText(observable, transform)
    }
}
