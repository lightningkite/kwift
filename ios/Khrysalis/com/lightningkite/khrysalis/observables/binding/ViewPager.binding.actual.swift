//Stub file made with Khrysalis 2 (by Lightning Kite)
import Foundation
import UIKit


//--- ViewPager.bind(List<T>, MutableObservableProperty<Int>, (T)->View)
public extension UICollectionView {
    func bind<T>(_ items: Array<T>, _ showIndex: MutableObservableProperty<Int32>, _ makeView: @escaping (T) -> View) -> Void {
        bind(
            count: items.size,
            spacing: 0,
            makeView: { index in
                makeView(items[index])
            }
        )
        bindIndex(showIndex)
    }
    func bind<T>(items: Array<T>, showIndex: MutableObservableProperty<Int32>, makeView: @escaping (T) -> View) -> Void {
        return bind(items, showIndex, makeView)
    }

    func bindRefresh(_ loading: ObservableProperty<Bool>, _ onRefresh: @escaping () -> Void) {
        return bindRefresh(loading: loading, onRefresh: onRefresh)
    }
    func bindRefresh(loading: ObservableProperty<Bool>, onRefresh: @escaping () -> Void) {
        let control = UIRefreshControl()
        control.addAction(for: .valueChanged, action: onRefresh)
        if #available(iOS 10.0, *) {
            refreshControl = control
        } else {
            addSubview(control)
        }
        loading.subscribeBy { (value) in
            if value {
                this.beginRefreshing()
            } else {
                this.endRefreshing()
            }
        }.until(control.removed)
    }

    var currentIndex: Int? {
        return self.indexPathForItem(at: CGPoint(x: self.contentOffset.x + self.bounds.size.width / 2, y: self.contentOffset.y + self.bounds.size.height / 2))?.row
    }

    func bindIndex(_ index: MutableObservableProperty<Int32>){
        var suppress = false
        index.subscribeBy { value in
            guard !suppress else { return }
            this.scrollToItem(at: IndexPath(row: Int(value), section: 0), at: .centeredHorizontally, animated: true)
        }.until(self.removed)
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.1, execute: {
            self.scrollToItem(at: IndexPath(row: Int(index.value), section: 0), at: .centeredHorizontally, animated: false)
        })
        self.whenScrolled { [weak index] newIndex in
            suppress = true
            index?.value = newIndex
            suppress = false
        }
    }

    func whenScrolledToEnd(action: @escaping ()->Void) {
        if let delegate = delegate as? HasAtEnd {
            delegate.setAtEnd(action: action)
        }
    }

    func whenScrolled(action: @escaping (_ index: Int32)->Void) {
        if var delegate = delegate as? HasAtPosition {
            delegate.atPosition = action
        }
    }

    func bind<T>(
        data: ObservableProperty<[T]>,
        defaultValue: T,
        spacing: CGFloat = 0,
        makeView: @escaping (ObservableProperty<T>) -> UIView
    ) {
        register(CustomUICollectionViewCell.self, forCellWithReuseIdentifier: "main-cell")
        let boundDataSource = CollectionBoundDataSource(source: data, defaultValue: defaultValue, spacing: spacing, makeView: makeView)
        dataSource = boundDataSource
        delegate = boundDataSource
        retain(as: "boundDataSource", item: boundDataSource)

        var previouslyEmpty = data.value.isEmpty
        data.subscribeBy { value in
            let emptyNow = data.value.isEmpty
            this.reloadData()
            if previouslyEmpty && !emptyNow {
                var at = ScrollPosition.top
                if let layout = this.collectionViewLayout as? UICollectionViewFlowLayout {
                    switch layout.scrollDirection {
                    case .vertical:
                        at = .top
                    case .horizontal:
                        at = .left
                    }
                }
                this.scrollToItem(at: IndexPath(item: 0, section: 0), at: at, animated: true)
            }
            previouslyEmpty = emptyNow
        }.until(self.removed)
    }

    func bind(
        count: Int32,
        spacing: CGFloat = 0,
        makeView: @escaping (_ index: Int32) -> UIView
    ) {
        register(CustomUICollectionViewCell.self, forCellWithReuseIdentifier: "main-cell")
        let boundDataSource = CollectionSimpleDataSource(count: count, spacing: spacing, makeView: makeView)
        dataSource = boundDataSource
        delegate = boundDataSource
        retain(as: "boundDataSource", item: boundDataSource)
    }
}

class CustomUICollectionViewCell: UICollectionViewCell {
    var obs: Any?
    var spacing: CGFloat = 0

    override init(frame: CGRect) {
        super.init(frame: frame)
    }

    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }

    override public func layoutSubviews() {
        super.layoutSubviews()
        contentView.frame = self.bounds.insetBy(dx: spacing, dy: spacing)
        for child in contentView.subviews {
            child.frame = contentView.bounds
            child.layoutSubviews()
        }
    }
}

protocol HasAtPosition {
    var atPosition: (Int32) -> Void { get set }
}

class CollectionBoundDataSource<T>: NSObject, UICollectionViewDataSource, UICollectionViewDelegate, HasAtEnd, HasAtPosition {
    var reversedDirection: Bool = false

    var source: ObservableProperty<[T]>
    let makeView: (ObservableProperty<T>) -> UIView
    let defaultValue: T
    var atEnd: () -> Void = {}
    let spacing: CGFloat

    init(source: ObservableProperty<[T]>, defaultValue: T, spacing: CGFloat, makeView: @escaping (ObservableProperty<T>) -> UIView) {
        self.source = source
        self.spacing = spacing
        self.makeView = makeView
        self.defaultValue = defaultValue
        super.init()
    }

    func setAtEnd(action: @escaping () -> Void) {
        self.atEnd = action
    }

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        let value = self.source.value
        let count = value.count
        return count
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        if indexPath.row >= (source.value.count) - 1 {
            atEnd()
        }
        let s = source.value
        let cell: CustomUICollectionViewCell = collectionView.dequeueReusableCell(withReuseIdentifier: "main-cell", for: indexPath) as! CustomUICollectionViewCell
        cell.spacing = self.spacing
        if cell.obs == nil {
            let obs = StandardObservableProperty(defaultValue)
            cell.obs = obs
            let new = makeView(obs)
            cell.contentView.addSubview(new)
        }
        if let obs = cell.obs as? StandardObservableProperty<T> {
            obs.value = s[indexPath.row]
        }
        return cell
    }

    var atPosition: (Int32) -> Void = { _ in }
    func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
        let collectionView = scrollView as! UICollectionView
        if let x = collectionView.currentIndex {
            atPosition(Int32(x))
        }
    }

}


class CollectionSimpleDataSource: NSObject, UICollectionViewDataSource, UICollectionViewDelegate, HasAtEnd, HasAtPosition {
    var reversedDirection: Bool = false

    var atPosition: (Int32) -> Void = { _ in }
    func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
        let collectionView = scrollView as! UICollectionView
        if let x = collectionView.currentIndex {
            atPosition(Int32(x))
        }
    }

    var count: Int32
    let makeView: (Int32) -> UIView
    var atEnd: () -> Void = {}
    let spacing: CGFloat

    init(count: Int32, spacing: CGFloat, makeView: @escaping (Int32) -> UIView) {
        self.count = count
        self.spacing = spacing
        self.makeView = makeView
        super.init()
    }

    func setAtEnd(action: @escaping () -> Void) {
        self.atEnd = action
    }

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return Int(count)
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        if indexPath.row >= count - 1 {
            atEnd()
        }
        let cell: CustomUICollectionViewCell = collectionView.dequeueReusableCell(withReuseIdentifier: "main-cell", for: indexPath) as! CustomUICollectionViewCell
        cell.spacing = self.spacing
        cell.contentView.subviews.forEach { $0.removeFromSuperview() }
        let new = makeView(Int32(indexPath.row))
        cell.contentView.addSubview(new)
        cell.setNeedsLayout()
        return cell
    }

}
