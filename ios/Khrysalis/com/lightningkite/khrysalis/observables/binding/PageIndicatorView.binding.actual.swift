//Stub file made with Khrysalis 2 (by Lightning Kite)
import Foundation
import UIKit


//--- PageIndicatorView.bind(Int, MutableObservableProperty<Int>)
public extension UIPageControl {
    func bind(_ count: Int32, _ selected: MutableObservableProperty<Int32>) -> Void {
        self.numberOfPages = Int(count)
        var suppress = false
        selected.subscribeBy { value in
            guard !suppress else { return }
            suppress = true
            self.currentPage = Int(value)
            suppress = false
        }.until(self.removed)
        self.addAction(for: .valueChanged, action: {
            guard !suppress else { return }
            suppress = true
            selected.value = Int32(self.currentPage)
            suppress = false
        })
    }
    func bind(count: Int32, selected: MutableObservableProperty<Int32>) -> Void {
        return bind(count, selected)
    }
}
