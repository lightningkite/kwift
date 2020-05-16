// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: Image.shared.kt
// Package: com.lightningkite.khrysalis
// FQImport: com.lightningkite.khrysalis.ImageRemoteUrl TS ImageRemoteUrl
// FQImport: com.lightningkite.khrysalis.ImageBitmap TS ImageBitmap
// FQImport: com.lightningkite.khrysalis.Image TS Image
// FQImport: com.lightningkite.khrysalis.Image SKIPPED due to same file
// FQImport: android.graphics.Bitmap TS Bitmap
// FQImport: android.net.Uri TS Uri
// FQImport: com.lightningkite.khrysalis.ImageReference TS ImageReference
// FQImport: com.lightningkite.khrysalis.ImageRemoteUrl SKIPPED due to same file
// FQImport: com.lightningkite.khrysalis.ImageReference SKIPPED due to same file
// FQImport: com.lightningkite.khrysalis.bytes.Data TS Data
// FQImport: com.lightningkite.khrysalis.ImageBitmap SKIPPED due to same file
import { hashString } from 'khrysalis/dist/Kotlin'
import { Data } from './bytes/Data.actual'

//! Declares com.lightningkite.khrysalis.Image
export class Image {
}
//! Declares com.lightningkite.khrysalis.ImageReference
export class ImageReference extends Image {
    public readonly uri: Uri;
    public constructor(uri: Uri) {
        super();
        this.uri = uri;
    }
    public hashCode(): number {
        let hash = 17;
        hash = 31 * hash + this.uri?.hashCode() ?? 0;
        return hash;
    }
    public equals(other: any): boolean { return other instanceof ImageReference && (this.uri?.equals(other.uri) ?? other.uri === null) }
    public toString(): string { return `ImageReference(uri = ${this.uri})` }
    public copy(uri: Uri = this.uri) { return new ImageReference(uri); }
}
//! Declares com.lightningkite.khrysalis.ImageBitmap
export class ImageBitmap extends Image {
    public readonly bitmap: Bitmap;
    public constructor(bitmap: Bitmap) {
        super();
        this.bitmap = bitmap;
    }
    public hashCode(): number {
        let hash = 17;
        hash = 31 * hash + this.bitmap?.hashCode() ?? 0;
        return hash;
    }
    public equals(other: any): boolean { return other instanceof ImageBitmap && (this.bitmap?.equals(other.bitmap) ?? other.bitmap === null) }
    public toString(): string { return `ImageBitmap(bitmap = ${this.bitmap})` }
    public copy(bitmap: Bitmap = this.bitmap) { return new ImageBitmap(bitmap); }
}
//! Declares com.lightningkite.khrysalis.ImageRaw
export class ImageRaw extends Image {
    public readonly raw: Data;
    public constructor(raw: Data) {
        super();
        this.raw = raw;
    }
    public hashCode(): number {
        let hash = 17;
        hash = 31 * hash + this.raw?.hashCode() ?? 0;
        return hash;
    }
    public equals(other: any): boolean { return other instanceof ImageRaw && (this.raw?.equals(other.raw) ?? other.raw === null) }
    public toString(): string { return `ImageRaw(raw = ${this.raw})` }
    public copy(raw: Data = this.raw) { return new ImageRaw(raw); }
}
//! Declares com.lightningkite.khrysalis.ImageRemoteUrl
export class ImageRemoteUrl extends Image {
    public readonly url: string;
    public constructor(url: string) {
        super();
        this.url = url;
    }
    public hashCode(): number {
        let hash = 17;
        hash = 31 * hash + hashString(this.url);
        return hash;
    }
    public equals(other: any): boolean { return other instanceof ImageRemoteUrl && this.url === other.url }
    public toString(): string { return `ImageRemoteUrl(url = ${this.url})` }
    public copy(url: string = this.url) { return new ImageRemoteUrl(url); }
}

//! Declares com.lightningkite.khrysalis.asImage
export function kotlinStringAsImage(this_: string): Image{ return new ImageRemoteUrl(this_); }
//! Declares com.lightningkite.khrysalis.asImage
export function androidNetUriAsImage(this_: Uri): Image{ return new ImageReference(this_); }
//! Declares com.lightningkite.khrysalis.asImage
export function androidGraphicsBitmapAsImage(this_: Bitmap): Image{ return new ImageBitmap(this_); }

