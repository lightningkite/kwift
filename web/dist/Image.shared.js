"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: Image.shared.kt
// Package: com.lightningkite.khrysalis
const Kotlin_1 = require("./Kotlin");
//! Declares com.lightningkite.khrysalis.Image
class Image {
    constructor() {
    }
}
exports.Image = Image;
//! Declares com.lightningkite.khrysalis.ImageReference
class ImageReference extends Image {
    constructor(uri) {
        super();
        this.uri = uri;
    }
    hashCode() {
        var _a, _b;
        let hash = 17;
        hash = (_b = 31 * hash + ((_a = this.uri) === null || _a === void 0 ? void 0 : _a.hashCode())) !== null && _b !== void 0 ? _b : 0;
        return hash;
    }
    equals(other) { return other instanceof ImageReference && this.uri === other.uri; }
    toString() { return `ImageReference(uri = ${this.uri})`; }
    copy(uri = this.uri) { return new ImageReference(uri); }
}
exports.ImageReference = ImageReference;
//! Declares com.lightningkite.khrysalis.ImageBitmap
class ImageImageBitmap extends Image {
    constructor(bitmap) {
        super();
        this.bitmap = bitmap;
    }
    hashCode() {
        var _a, _b;
        let hash = 17;
        hash = (_b = 31 * hash + ((_a = this.bitmap) === null || _a === void 0 ? void 0 : _a.hashCode())) !== null && _b !== void 0 ? _b : 0;
        return hash;
    }
    equals(other) { return other instanceof ImageImageBitmap && Kotlin_1.safeEq(this.bitmap, other.bitmap); }
    toString() { return `ImageImageBitmap(bitmap = ${this.bitmap})`; }
    copy(bitmap = this.bitmap) { return new ImageImageBitmap(bitmap); }
}
exports.ImageImageBitmap = ImageImageBitmap;
//! Declares com.lightningkite.khrysalis.ImageRaw
class ImageRaw extends Image {
    constructor(raw) {
        super();
        this.raw = raw;
    }
    hashCode() {
        var _a, _b;
        let hash = 17;
        hash = (_b = 31 * hash + ((_a = this.raw) === null || _a === void 0 ? void 0 : _a.hashCode())) !== null && _b !== void 0 ? _b : 0;
        return hash;
    }
    equals(other) { return other instanceof ImageRaw && Kotlin_1.safeEq(this.raw, other.raw); }
    toString() { return `ImageRaw(raw = ${this.raw})`; }
    copy(raw = this.raw) { return new ImageRaw(raw); }
}
exports.ImageRaw = ImageRaw;
//! Declares com.lightningkite.khrysalis.ImageRemoteUrl
class ImageRemoteUrl extends Image {
    constructor(url) {
        super();
        this.url = url;
    }
    hashCode() {
        let hash = 17;
        hash = 31 * hash + Kotlin_1.hashString(this.url);
        return hash;
    }
    equals(other) { return other instanceof ImageRemoteUrl && this.url === other.url; }
    toString() { return `ImageRemoteUrl(url = ${this.url})`; }
    copy(url = this.url) { return new ImageRemoteUrl(url); }
}
exports.ImageRemoteUrl = ImageRemoteUrl;
//! Declares com.lightningkite.khrysalis.asImage>kotlin.String
function kotlinStringAsImage(this_) { return new ImageRemoteUrl(this_); }
exports.kotlinStringAsImage = kotlinStringAsImage;
//! Declares com.lightningkite.khrysalis.asImage>android.net.Uri
function androidNetUriAsImage(this_) { return new ImageReference(this_); }
exports.androidNetUriAsImage = androidNetUriAsImage;
//! Declares com.lightningkite.khrysalis.asImage>android.graphics.Bitmap
function androidGraphicsBitmapAsImage(this_) { return new ImageImageBitmap(this_); }
exports.androidGraphicsBitmapAsImage = androidGraphicsBitmapAsImage;
//# sourceMappingURL=Image.shared.js.map