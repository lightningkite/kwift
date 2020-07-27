//
//  UITextView.placeholder.swift
//  Lifting Generations
//
//  Created by Joseph Ivie on 4/1/19.
//  Copyright © 2019 Joseph Ivie. All rights reserved.
//

import UIKit


public extension UITextView {
    var placeholder: String {
        get {
            return text
        }
        set(value) {
            text = value
        }
    }
    var attributedPlaceholder: NSAttributedString {
        get {
            return NSAttributedString(string: text)
        }
        set(value) {
            text = value.string
        }
    }
    func setLeftPaddingPoints(_ amount:CGFloat){
        self.textContainerInset.left = amount
    }
    func setRightPaddingPoints(_ amount:CGFloat) {
        self.textContainerInset.right = amount
    }
    var numberOfLines: Int {
        get { return 0 }
        set(value) { }
    }
    
    func addDismissButton(){
        var toolbar = UIToolbar()
        toolbar.barStyle = UIBarStyle.default
        toolbar.isTranslucent = true
        toolbar.sizeToFit()
        let doneButton = UIBarButtonItem(title: "Done", style: UIBarButtonItem.Style.plain, until: removed) { [weak self] in
            self?.resignFirstResponder()
        }
        let spaceButton = UIBarButtonItem(barButtonSystemItem: UIBarButtonItem.SystemItem.flexibleSpace, target: nil, action: nil)
        toolbar.setItems([ spaceButton, doneButton], animated: false)
        inputAccessoryView = toolbar
    }
    var baselineAdjustment: UIBaselineAdjustment {
        get { return .alignBaselines }
        set(value){}
    }

    func underlineLayer(boldColor: UIColor, hintColor: UIColor? = nil) -> CALayer {
        let view = self
        let layer = CALayer()
        let bold = CALayer()
        bold.backgroundColor = boldColor.cgColor
        let light = CALayer()
        light.backgroundColor = (hintColor ?? boldColor).cgColor

        layer.addOnStateChange(view) { [unowned layer] state in
            layer.sublayers?.forEach { $0.removeFromSuperlayer() }
            if state.contains(.focused) {
                layer.addSublayer(bold)
            } else {
                layer.addSublayer(light)
            }
        }

        view.onLayoutSubviews.startWith(view).addWeak(referenceA: layer) { [unowned bold, unowned light] layer, view in
            layer.frame = view.bounds
            bold.frame.origin.x = 0
            bold.frame.origin.y = layer.bounds.height - 2
            bold.bounds.size.width = layer.bounds.width
            bold.bounds.size.height = 2
            light.frame.origin.x = 0
            light.frame.origin.y = layer.bounds.height - 1
            light.bounds.size.width = layer.bounds.width
            light.bounds.size.height = 1
        }

        return layer
    }
}