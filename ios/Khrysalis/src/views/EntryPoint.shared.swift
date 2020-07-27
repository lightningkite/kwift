// Generated by Khrysalis Swift converter - this file will be overwritten.
// File: views/EntryPoint.shared.kt
// Package: com.lightningkite.khrysalis.views
import Foundation

public protocol EntryPoint {
    
    func handleDeepLink(schema: String, host: String, path: String, params: Dictionary<String, String>) -> Void 
    func onBackPressed() -> Bool 
    var mainStack: ObservableStack<ViewGenerator>? { get }
    
}
public extension EntryPoint {
    func handleDeepLink(schema: String, host: String, path: String, params: Dictionary<String, String>) -> Void {
        print("Empty handler; \(schema)://\(host)/\(path)/\(params)")
    }
    func onBackPressed() -> Bool { return false }
    var mainStack: ObservableStack<ViewGenerator>? {
        get { return nil }
    }
}

