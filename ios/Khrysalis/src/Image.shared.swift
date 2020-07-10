// Generated by Khrysalis Swift converter - this file will be overwritten.
// File: Image.shared.kt
// Package: com.lightningkite.khrysalis
import Foundation

public class Image : KEquatable, KHashable, KStringable {
    public init() {
    }
}
public class ImageReference : Image, KDataClass {
    public var uri: Uri
    override public init(uri: Uri) {
        self.uri = uri
        super.init()
    }
    public func hash(into hasher: inout Hasher) {
        hasher.combine(uri)
    }
    public static func == (lhs: ImageReference, rhs: ImageReference) -> Bool { return lhs.uri == rhs.uri }
    public var description: String { return "ImageReference(uri = \(self.uri))" }
    public func copy(uri: Uri? = nil) -> ImageReference { return ImageReference(uri: uri ?? self.uri) }
}
public class ImageBitmap : Image, KDataClass {
    public var bitmap: Bitmap
    override public init(bitmap: Bitmap) {
        self.bitmap = bitmap
        super.init()
    }
    public func hash(into hasher: inout Hasher) {
        hasher.combine(bitmap)
    }
    public static func == (lhs: ImageBitmap, rhs: ImageBitmap) -> Bool { return lhs.bitmap == rhs.bitmap }
    public var description: String { return "ImageBitmap(bitmap = \(self.bitmap))" }
    public func copy(bitmap: Bitmap? = nil) -> ImageBitmap { return ImageBitmap(bitmap: bitmap ?? self.bitmap) }
}
public class ImageRaw : Image, KDataClass {
    public var raw: Data
    override public init(raw: Data) {
        self.raw = raw
        super.init()
    }
    public func hash(into hasher: inout Hasher) {
        hasher.combine(raw)
    }
    public static func == (lhs: ImageRaw, rhs: ImageRaw) -> Bool { return lhs.raw == rhs.raw }
    public var description: String { return "ImageRaw(raw = \(self.raw))" }
    public func copy(raw: Data? = nil) -> ImageRaw { return ImageRaw(raw: raw ?? self.raw) }
}
public class ImageRemoteUrl : Image, KDataClass {
    public var url: String
    override public init(url: String) {
        self.url = url
        super.init()
    }
    public func hash(into hasher: inout Hasher) {
        hasher.combine(url)
    }
    public static func == (lhs: ImageRemoteUrl, rhs: ImageRemoteUrl) -> Bool { return lhs.url == rhs.url }
    public var description: String { return "ImageRemoteUrl(url = \(self.url))" }
    public func copy(url: String? = nil) -> ImageRemoteUrl { return ImageRemoteUrl(url: url ?? self.url) }
}

public extension String {
    func asImage() -> Image { return ImageRemoteUrl(url: self) }
}
public extension Uri {
    func asImage() -> Image { return ImageReference(uri: self) }
}
public extension Bitmap {
    func asImage() -> Image { return ImageBitmap(bitmap: self) }
}

