//
//  KotlinNumbers.swift
//  KhrysalisTemplate
//
//  Created by Joseph Ivie on 9/5/19.
//  Copyright © 2019 Joseph Ivie. All rights reserved.
//

import Foundation

public extension Int8 {
    func toByte() -> Int8 { return Int8(self) }
    func toShort() -> Int16 { return Int16(self) }
    func toInt() -> Int32 { return Int32(self) }
    func toLong() -> Int64 { return Int64(self) }
    func toFloat() -> Float { return Float(self) }
    func toDouble() -> Double { return Double(self) }
    func toString() -> String { return String(describing: self) }
    var absoluteValue: Int8 { return abs(self) }
    static var MIN_VALUE: Self { return Self.min }
    static var MAX_VALUE: Self { return Self.max }
}
public extension Int16 {
    func toByte() -> Int8 { return Int8(self) }
    func toShort() -> Int16 { return Int16(self) }
    func toInt() -> Int32 { return Int32(self) }
    func toLong() -> Int64 { return Int64(self) }
    func toFloat() -> Float { return Float(self) }
    func toDouble() -> Double { return Double(self) }
    func toString() -> String { return String(describing: self) }
    var absoluteValue: Int16 { return abs(self) }
    static var MIN_VALUE: Self { return Self.min }
    static var MAX_VALUE: Self { return Self.max }
}
public extension Int32 {
    func toByte() -> Int8 { return Int8(self) }
    func toShort() -> Int16 { return Int16(self) }
    func toInt() -> Int32 { return Int32(self) }
    func toLong() -> Int64 { return Int64(self) }
    func toFloat() -> Float { return Float(self) }
    func toDouble() -> Double { return Double(self) }
    func toString() -> String { return String(describing: self) }
    var absoluteValue: Int32 { return abs(self) }
    static var MIN_VALUE: Self { return Self.min }
    static var MAX_VALUE: Self { return Self.max }
}
public extension Int64 {
    func toByte() -> Int8 { return Int8(self) }
    func toShort() -> Int16 { return Int16(self) }
    func toInt() -> Int32 { return Int32(self) }
    func toLong() -> Int64 { return Int64(self) }
    func toFloat() -> Float { return Float(self) }
    func toDouble() -> Double { return Double(self) }
    func toString() -> String { return String(describing: self) }
    var absoluteValue: Int64 { return abs(self) }
    static var MIN_VALUE: Self { return Self.min }
    static var MAX_VALUE: Self { return Self.max }
}
public extension Float {
    func toByte() -> Int8 { return Int8(self) }
    func toShort() -> Int16 { return Int16(self) }
    func toInt() -> Int32 { return Int32(self) }
    func toLong() -> Int64 { return Int64(self) }
    func toFloat() -> Float { return Float(self) }
    func toDouble() -> Double { return Double(self) }
    func toString() -> String { return String(describing: self) }
    var absoluteValue: Float { return abs(self) }
    static var MIN_VALUE: Self { return -Self.infinity }
    static var MAX_VALUE: Self { return Self.infinity }
    func roundToInt() -> Int32 { return Int32(rounded()) }
    func roundToLong() -> Int64 { return Int64(rounded()) }
}
public extension Double {
    func toByte() -> Int8 { return Int8(self) }
    func toShort() -> Int16 { return Int16(self) }
    func toInt() -> Int32 { return Int32(self) }
    func toLong() -> Int64 { return Int64(self) }
    func toFloat() -> Float { return Float(self) }
    func toDouble() -> Double { return Double(self) }
    func toString() -> String { return String(describing: self) }
    var absoluteValue: Double { return abs(self) }
    static var MIN_VALUE: Self { return -Self.infinity }
    static var MAX_VALUE: Self { return Self.infinity }
    func roundToInt() -> Int32 { return Int32(rounded()) }
    func roundToLong() -> Int64 { return Int64(rounded()) }
}
public extension String {
    func toByte() -> Int8 { return Int8(self)! }
    func toByteOrNull() -> Int8? { return Int8(self) }
    func toShort() -> Int16 { return Int16(self)! }
    func toShortOrNull() -> Int16? { return Int16(self) }
    func toInt() -> Int32 { return Int32(self)! }
    func toIntOrNull() -> Int32? { return Int32(self) }
    func toLong() -> Int64 { return Int64(self)! }
    func toLongOrNull() -> Int64? { return Int64(self) }
    func toFloat() -> Float { return Float(self)! }
    func toFloatOrNull() -> Float? { return Float(self) }
    func toDouble() -> Double { return Double(self)! }
    func toDoubleOrNull() -> Double? { return Double(self) }
}

public func % (left:Float, right:Float) -> Float {
    return left.truncatingRemainder(dividingBy: right)
}

public func % (left:Double, right:Double) -> Double {
    return left.truncatingRemainder(dividingBy: right)
}