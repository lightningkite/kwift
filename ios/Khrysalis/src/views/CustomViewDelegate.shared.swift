// Generated by Khrysalis Swift converter - this file will be overwritten.
// File: views/CustomViewDelegate.shared.kt
// Package: com.lightningkite.khrysalis.views
import Foundation

public class CustomViewDelegate : KEquatable, KHashable, KStringable {
    protected init() {
        self.customView = nil
        self.toDispose = []
        self.removed = DisposeCondition(call: { (it: Disposable) -> Void in self.toDispose.append(it) })
    }
    
    public var customView: CustomView?
    public func generateAccessibilityView() -> View? 
    public func draw(canvas: Canvas, width: CGFloat, height: CGFloat, displayMetrics: DisplayMetrics) -> Void 
    public func onTouchDown(id: Int, x: CGFloat, y: CGFloat, width: CGFloat, height: CGFloat) -> Bool { return false }
    public func onTouchMove(id: Int, x: CGFloat, y: CGFloat, width: CGFloat, height: CGFloat) -> Bool { return false }
    public func onTouchCancelled(id: Int, x: CGFloat, y: CGFloat, width: CGFloat, height: CGFloat) -> Bool { return false }
    public func onTouchUp(id: Int, x: CGFloat, y: CGFloat, width: CGFloat, height: CGFloat) -> Bool { return false }
    public func sizeThatFitsWidth(width: CGFloat, height: CGFloat) -> CGFloat { return width }
    public func sizeThatFitsHeight(width: CGFloat, height: CGFloat) -> CGFloat { return height }
    
    public func invalidate() -> Void { self.customView?.invalidate() }
    public func postInvalidate() -> Void { self.customView?.postInvalidate() }
    
    public var toDispose: Array<Disposable>
    public let removed: DisposeCondition
    public func dispose() -> Void {
        for item in (self.toDispose){
            item.dispose()
        }
    }
}

