//Stub file made with Khrysalis 2 (by Lightning Kite)
import Foundation
import Alamofire
import AlamofireImage
import Photos
import AVKit
import MapKit
import EventKitUI
import DKImagePickerController


//--- ViewDependency
public class ViewDependency: NSObject {
    public unowned let parentViewController: UIViewController
    public init(_ parentViewController: UIViewController){
        self.parentViewController = parentViewController
    }
    //--- ViewDependency.getString(StringResource)
    public func getString(_ reference: StringResource) -> String {
        return reference
    }
    //--- ViewDependency.getColor(ColorResource)
    public func getColor(_ reference: ColorResource) -> UIColor {
        return reference
    }
    //--- ViewDependency.displayMetrics
    public var displayMetrics: DisplayMetrics {
        return DisplayMetrics(
            density: Float(UIScreen.main.scale),
            scaledDensity: Float(UIScreen.main.scale),
            widthPixels: Int32(UIScreen.main.bounds.width * UIScreen.main.scale),
            heightPixels: Int32(UIScreen.main.bounds.height * UIScreen.main.scale)
        )
    }

    //--- ViewDependency.share(String, String? , String? , Image? )
    public func share(_ shareTitle: String, _ message: String? = nil, _ url: String? = nil, _ image: Image? = nil) -> Void {
        var items: Array<Any> = []
        if let message = message {
            items.add(message)
        }
        if let url = url, let fixed = URL(string: url) {
            items.add(fixed)
        }
        if let image = image {
            TODO()
        }
        let vc = UIActivityViewController(activityItems: items, applicationActivities: nil)
        self.parentViewController.present(vc, animated: true, completion: nil)
    }
    public func share(shareTitle: String, message: String? = nil, url: String? = nil, image: Image? = nil) -> Void {
        return share(shareTitle, message, url, image)
    }

    //--- ViewDependency.openUrl(String)
    public func openUrl(_ url: String) -> Void {
        if let url = URL(string: url) {
            UIApplication.shared.open(url)
        }
    }
    public func openUrl(url: String) -> Void {
        return openUrl(url)
    }

    //--- ViewDependency.openMap(GeoCoordinate, String? , Float? )
    public func openMap(_ coordinate: GeoCoordinate, _ label: String? = nil, _ zoom: Float? = nil) -> Void {
        var options: Array<(String, ()->Void)> = [
            ("Apple Maps", {
                let mapItem = MKMapItem(placemark: MKPlacemark(coordinate: coordinate.toIos(), addressDictionary: nil))
                mapItem.name = label
                mapItem.openInMaps()
            })
        ]
        if UIApplication.shared.canOpenURL(URL(string: "comgooglemaps://")!) {
            options.add(("Google Maps", {
                var url = "string: comgooglemaps://?center=\(coordinate.latitude),\(coordinate.longitude)"
                if let zoom = zoom {
                    url += "&zoom=\(zoom)"
                }
                if let label = label {
                    url += "&q=\(label)"
                }
                UIApplication.shared.open(URL(string: url)!)
            }))
        }
        //TODO: Could add more options
        if options.count == 1 {
            options[0].1()
        } else {
            let optionsView = UIAlertController(title: "Open in Maps", message: nil, preferredStyle: .actionSheet)
            for option in options {
                optionsView.addAction(UIAlertAction(title: option.0, style: .default, handler: { (action) in
                    optionsView.dismiss(animated: true, completion: nil)
                    option.1()
                }))
            }
            self.parentViewController.present(optionsView, animated: true, completion: nil)
        }
    }
    public func openMap(coordinate: GeoCoordinate, label: String? = nil, zoom: Float? = nil) -> Void {
        return openMap(coordinate, label, zoom)
    }

    //--- ViewDependency.openEvent(String, String, String, Date, Date)
    public func openEvent(_ title: String, _ description: String, _ location: String, _ start: Date, _ end: Date) -> Void {
        let store = EKEventStore()
        store.requestAccess(to: .event) { (hasPermission, error) in
            if hasPermission {
                DispatchQueue.main.async {
                    let addController = EKEventEditViewController()
                    addController.eventStore = store
                    addController.editViewDelegate = self
                    let event = EKEvent(eventStore: store)
                    event.title = title
                    event.notes = description
                    event.location = location
                    event.startDate = start
                    event.endDate = end
                    addController.event = event
                    self.parentViewController.present(addController, animated: true, completion: nil)
                }
            }
        }
    }
    public func openEvent(title: String, description: String, location: String, start: Date, end: Date) -> Void {
        return openEvent(title, description, location, start, end)
    }

    //--- ViewDependency.downloadDrawable(String, Int? , Int? , (Drawable?)->Unit)
    public func downloadDrawable(
        url: String,
        width: Int? = nil,
        height: Int? = nil,
        onResult: @escaping (Drawable?)->Void
    ) {
        downloadDrawable(url, width, height, onResult)
    }
    public func downloadDrawable(
        _ url: String,
        _ width: Int? = nil,
        _ height: Int? = nil,
        _ onResult: @escaping (Drawable?)->Void
    ) {
        Alamofire.request(url).responseImage(imageScale: 1) { response in
            if var image = response.value {
//                if let width = width, let height = height {
//                    image = image.af_imageAspectScaled(toFit: CGSize(width: width, height: height))
//                }
                onResult({ _ in CAImageLayer(image) })
            } else {
                onResult(nil)
            }
        }
    }

    //--- ViewDependency.checkedDrawable(Drawable, Drawable)
    public func checkedDrawable(
        checked: @escaping Drawable,
        normal: @escaping Drawable
    ) -> Drawable {
        return checkedDrawable(checked, normal)
    }
    public func checkedDrawable(
        _ checked: @escaping Drawable,
        _ normal: @escaping Drawable
    ) -> Drawable {
        return { view in
            let layer = CALayer()

            let checkedLayer = checked(view)
            let normalLayer = normal(view)

            layer.addOnStateChange(view) { [unowned layer] state in
                layer.sublayers?.forEach { $0.removeFromSuperlayer() }
                if state.contains(.selected) {
                    layer.addSublayer(checkedLayer)
                } else {
                    layer.addSublayer(normalLayer)
                }
            }
            layer.onResize.startWith(layer.bounds).addWeak(checkedLayer) { (checkedLayer, bounds) in
                checkedLayer.frame = bounds
            }
            layer.onResize.startWith(layer.bounds).addWeak(normalLayer) { (normalLayer, bounds) in
                normalLayer.frame = bounds
            }

            return layer
        }
    }

    //--- ViewDependency.setSizeDrawable(Drawable, Int, Int)
    public func setSizeDrawable(drawable: @escaping Drawable, width: Int, height: Int) -> Drawable {
        return setSizeDrawable(drawable, width, height)
    }
    public func setSizeDrawable(_ drawable: @escaping Drawable, _ width: Int, _ height: Int) -> Drawable {
        return { view in
            let existing = drawable(view)
            existing.resize(CGRect(x: 0, y: 0, width: width, height: height))
            return existing
        }
    }

    //--- ViewDependency image helpers
    private static let delegateExtension = ExtensionProperty<ViewDependency, ImageDelegate>()
    private var imageDelegate: ImageDelegate {
        if let existing = ViewDependency.delegateExtension.get(self) {
            return existing
        }
        let new = ImageDelegate()
        ViewDependency.delegateExtension.set(self, new)
        return new
    }

    //--- ViewDependency.requestImageGallery((Uri)->Unit)
    public func requestImageGallery(onResult: @escaping (Uri) -> Void) {
        if PHPhotoLibrary.authorizationStatus() == .authorized {
            self.requestImageGalleryRaw(onResult: onResult)
        } else {
            PHPhotoLibrary.requestAuthorization {_ in
                DispatchQueue.main.async {
                    self.requestImageGalleryRaw(onResult: onResult)
                }
            }
        }
    }
    private func requestImageGalleryRaw(onResult: @escaping (Uri) -> Void) {
        if UIImagePickerController.isSourceTypeAvailable(.savedPhotosAlbum){
            let imageDelegate = self.imageDelegate
            imageDelegate.onImagePicked = onResult
            imageDelegate.prepareGallery()
            self.parentViewController.present(imageDelegate.imagePicker, animated: true, completion: nil)
        }
    }

    //--- ViewDependency.requestImagesGallery((List<Uri>)->Unit)
    public func requestImagesGallery(_ onResult: @escaping (Array<Uri>) -> Void) -> Void {
        if PHPhotoLibrary.authorizationStatus() == .authorized {
            self.requestImagesGalleryRaw(onResult: onResult)
        } else {
            PHPhotoLibrary.requestAuthorization {_ in
                DispatchQueue.main.async {
                    self.requestImagesGalleryRaw(onResult: onResult)
                }
            }
        }
    }
    private func requestImagesGalleryRaw(onResult: @escaping (Array<Uri>) -> Void) {
        let pickerController = DKImagePickerController()
        pickerController.didSelectAssets = { (assets: [DKAsset]) in
            print("didSelectAssets")
            print(assets)
            //Select Assets
            var result: Array<Uri> = []
            var remaining = assets.count
            print("Assets remaining: \(remaining)")
            for item in assets {
                getUrl(editedImage: nil, originalImage: nil, asset: item.originalAsset, onResult: { url in
                    remaining -= 1
                    print("Assets remaining: \(remaining)")
                    if let url = url {
                        result.add(url)
                    } else {
                        //... dunno how to handle error
                    }
                    if remaining == 0 {
                        print("Finish")
                        onResult(result)
                    }
                })
            }
        }
        self.parentViewController.present(pickerController, animated: true){}
//        let imagePicker = OpalImagePickerController()
//        self.parentViewController.presentOpalImagePickerController(imagePicker, animated: true,
//            select: { (assets) in
//                //Select Assets
//                var result: Array<Uri> = []
//                var remaining = assets.count
//                print("Assets remaining: \(remaining)")
//                for item in assets {
//                    getUrl(editedImage: nil, originalImage: nil, asset: item, onResult: { url in
//                        remaining -= 1
//                        print("Assets remaining: \(remaining)")
//                        if let url = url {
//                            result.add(url)
//                        } else {
//                            //... dunno how to handle error
//                        }
//                        if remaining == 0 {
//                            print("Finish")
//                            onResult(result)
//                            imagePicker.dismiss(animated: true, completion: nil)
//                        }
//                    })
//                }
//            }, cancel: {
//                //Cancel
//            })
    }

    //--- ViewDependency.requestImageCamera((Uri)->Unit)
    public func requestImageCamera(onResult: @escaping (Uri) -> Void) {
        DispatchQueue.main.async {
            if AVCaptureDevice.authorizationStatus(for: .video) == .authorized {
                AVCaptureDevice.requestAccess(for: .video) { granted in
                    DispatchQueue.main.async {
                        if granted {
                            if PHPhotoLibrary.authorizationStatus() == .authorized {
                                self.requestImageCameraRaw(onResult: onResult)
                            } else {
                                PHPhotoLibrary.requestAuthorization {_ in
                                    self.requestImageCameraRaw(onResult: onResult)
                                }
                            }
                        }
                    }
                }
            } else {
                AVCaptureDevice.requestAccess(for: .video) { granted in
                    DispatchQueue.main.async {
                        if granted {
                            if PHPhotoLibrary.authorizationStatus() == .authorized {
                                self.requestImageCameraRaw(onResult: onResult)
                            } else {
                                PHPhotoLibrary.requestAuthorization {_ in
                                    self.requestImageCameraRaw(onResult: onResult)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private func requestImageCameraRaw(onResult: @escaping (Uri) -> Void) {
        DispatchQueue.main.async {
            if UIImagePickerController.isSourceTypeAvailable(.camera){
                let imageDelegate = self.imageDelegate
                imageDelegate.onImagePicked = onResult
                imageDelegate.prepareCamera()
                self.parentViewController.present(imageDelegate.imagePicker, animated: true, completion: nil)
            }
        }
    }
}

//--- Image helpers

extension ViewDependency: EKEventEditViewDelegate {
    public func eventEditViewController(_ controller: EKEventEditViewController, didCompleteWith action: EKEventEditViewAction) {
        self.parentViewController.dismiss(animated: true) {[weak self] in
            
        }
    }
}

private class ImageDelegate : NSObject, UIImagePickerControllerDelegate, UINavigationControllerDelegate {

    var imagePicker = UIImagePickerController()
    var onImagePicked: ((Uri)->Void)? = nil

    func prepareGallery(){
        imagePicker.delegate = self
        imagePicker.sourceType = .photoLibrary
        imagePicker.allowsEditing = false
    }

    func prepareCamera(){
        imagePicker.delegate = self
        imagePicker.sourceType = .camera
        imagePicker.cameraCaptureMode = .photo
        imagePicker.cameraDevice = .front
        imagePicker.allowsEditing = false
    }

//    @objc public func handleResult(image: UIImage, didFinishSavingWithError error: NSError?, contextInfo: UnsafeMutableRawPointer?) {
//        if error == nil {
//            imagePicker.dismiss(animated: true, completion: {
//                image.file
////                self.onImagePicked?(URL(fileURLWithPath: path, isDirectory: false))
//                self.onImagePicked = nil
//            })
//        }
//    }

    public func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        if #available(iOS 11.0, *) {
            if let image = info[.imageURL] as? URL {
                print("Image retrieved directly using .imageURL")
                DispatchQueue.main.async {
                    picker.dismiss(animated: true, completion: {
                        self.onImagePicked?(image)
                        self.onImagePicked = nil
                    })
                }
                return
            }
        }
        
        getUrl(editedImage: info[.editedImage] as? UIImage, originalImage: info[.originalImage] as? UIImage, asset: info[.phAsset] as? PHAsset, onResult: { url in
            if let url = url {
                DispatchQueue.main.async {
                    picker.dismiss(animated: true, completion: {
                        self.onImagePicked?(url)
                        self.onImagePicked = nil
                    })
                }
            }
        })
    }
}

fileprivate func getUrl(editedImage: UIImage?, originalImage: UIImage?, asset: PHAsset?, onResult: @escaping (URL?)->Void) {
    if let editedImage = editedImage {
        if let url = editedImage.saveTemp() {
            print("Image retrieved using save due to edit")
            onResult(url)
        } else {
            print("Image retrieval failed")
            onResult(nil)
        }
    } else if let asset = asset {
        asset.getURL(completionHandler: { url in
            if let url = url {
                print("Image retrieved using asset")
                onResult(url)
            } else {
                //That failed, let's just save the image
                if let originalImage = originalImage, let url = originalImage.saveTemp() {
                    print("Image retrieved using save as backup")
                    onResult(url)
                } else {
                    print("Image retrieval failed")
                    onResult(nil)
                }
            }
        })
    } else {
        //That failed, let's just save the image
        if let originalImage = originalImage, let url = originalImage.saveTemp() {
            print("Image retrieved using save as backup")
            onResult(url)
        } else {
            print("Image retrieval failed")
            onResult(nil)
        }
    }
}

// save
extension UIImage {
    
    func saveTemp() -> URL? {
        let tempDirectoryUrl = URL(fileURLWithPath: NSTemporaryDirectory()).appendingPathComponent("temp-khrysalis-photos")
        guard let url2 = self.save(at: tempDirectoryUrl) else {
            return nil
        }
        print(url2)
        return url2
    }

    func save(at directory: FileManager.SearchPathDirectory,
              pathAndImageName: String,
              createSubdirectoriesIfNeed: Bool = true,
              compressionQuality: CGFloat = 1.0)  -> URL? {
        do {
        let documentsDirectory = try FileManager.default.url(for: directory, in: .userDomainMask,
                                                             appropriateFor: nil,
                                                             create: false)
        return save(at: documentsDirectory.appendingPathComponent(pathAndImageName),
                    createSubdirectoriesIfNeed: createSubdirectoriesIfNeed,
                    compressionQuality: compressionQuality)
        } catch {
            print("-- Error: \(error)")
            return nil
        }
    }

    func save(at url: URL,
              createSubdirectoriesIfNeed: Bool = true,
              compressionQuality: CGFloat = 1.0)  -> URL? {
        do {
            if createSubdirectoriesIfNeed {
                try FileManager.default.createDirectory(at: url.deletingLastPathComponent(),
                                                        withIntermediateDirectories: true,
                                                        attributes: nil)
            }
            guard let data = jpegData(compressionQuality: compressionQuality) else { return nil }
            try data.write(to: url)
            return url
        } catch {
            print("-- Error: \(error)")
            return nil
        }
    }
}

// load from path

extension UIImage {
    convenience init?(fileURLWithPath url: URL, scale: CGFloat = 1.0) {
        do {
            let data = try NSData(contentsOf: url)
            self.init(data: data! as Data, scale: scale)
        } catch {
            print("-- Error: \(error)")
            return nil
        }
    }
}

extension PHAsset {

    func getURL(completionHandler : @escaping ((_ responseURL : URL?) -> Void)){
        if self.mediaType == .image {
            let options: PHContentEditingInputRequestOptions = PHContentEditingInputRequestOptions()
            options.canHandleAdjustmentData = {(adjustmeta: PHAdjustmentData) -> Bool in
                return true
            }
            self.requestContentEditingInput(with: options, completionHandler: {(contentEditingInput: PHContentEditingInput?, info: [AnyHashable : Any]) -> Void in
                completionHandler(contentEditingInput!.fullSizeImageURL as URL?)
            })
        } else if self.mediaType == .video {
            let options: PHVideoRequestOptions = PHVideoRequestOptions()
            options.version = .original
            PHImageManager.default().requestAVAsset(forVideo: self, options: options, resultHandler: {(asset: AVAsset?, audioMix: AVAudioMix?, info: [AnyHashable : Any]?) -> Void in
                if let urlAsset = asset as? AVURLAsset {
                    let localVideoUrl: URL = urlAsset.url as URL
                    completionHandler(localVideoUrl)
                } else {
                    completionHandler(nil)
                }
            })
        }
    }
}
