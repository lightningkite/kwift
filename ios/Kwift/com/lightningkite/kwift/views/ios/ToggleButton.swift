//
//  ToggleButton.swift
//  Klyp
//
//  Created by Joseph Ivie on 9/26/19.
//  Copyright © 2019 Klyp. All rights reserved.
//

import Foundation
import UIKit


public class ToggleButton: UIButtonWithLayer, CompoundButton {
    public var onCheckChanged: (Bool) -> Void = {_ in }
    public var isChecked: Bool {
        get{
            self.isOn
        }
        set(value){
            self.isOn = value
        }
    }
    
    public var textOn: String = "On"{
        didSet {
            syncText()
        }
    }
    
    public var textOff: String = "Off"{
        didSet {
            syncText()
        }
    }
    
    public var isOn: Bool = false {
        didSet {
            self.isSelected = isOn
            syncText()
            onCheckChanged(isOn)
        }
    }
    
    private func syncText(){
        if isOn{
            self.setTitle(textOn, for: UIControl.State.normal)
        } else{
            self.setTitle(textOff, for: UIControl.State.normal)
        }
    }
    
    override public init(frame: CGRect) {
        super.init(frame: frame)
        commonInit()
    }
    
    override public required init?(coder: NSCoder) {
        super.init(coder: coder)
        commonInit()
    }
    
    func commonInit(){
        onClick { [unowned self] in
            self.isOn = !self.isOn
        }
    }
}

public extension ToggleButton{
    override var textResource: String {
        get {
            return title(for: .normal) ?? ""
        }
        set(value) {
            self.textOn = value
            self.textOff = value
        }
    }
    
    override var textString: String {
        get{
            return title(for: .normal) ?? ""
        }
        set(value){
            self.textOn = value
            self.textOff = value
        }
    }
}
