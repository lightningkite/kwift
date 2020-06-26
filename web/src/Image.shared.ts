// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: Image.shared.kt
// Package: com.lightningkite.khrysalis
import { hashString } from './Kotlin'

//! Declares com.lightningkite.khrysalis.Image
export class Image {
    public constructor() {
    }
}
//! Declares com.lightningkite.khrysalis.ImageReference
export class ImageReference extends Image {
    public readonly uri: File;
    public constructor(uri: File) {
        super();
        this.uri = uri;
    }
    public hashCode(): number {
        let hash = 17;
        hash = 31 * hash + this.uri?.hashCode() ?? 0;
        return hash;
    }
    public equals(other: any): boolean { return other instanceof ImageReference && this.uri === other.uri }
    public toString(): string { return `ImageReference(uri = ${this.uri})` }
    public copy(uri: File = this.uri) { return new ImageReference(uri); }
}
//! Declares com.lightningkite.khrysalis.ImageBitmap
export class ImageImageBitmap extends Image {
    public readonly bitmap: ImageBitmap;
    public constructor(bitmap: ImageBitmap) {
        super();
        this.bitmap = bitmap;
    }
    public hashCode(): number {
        let hash = 17;
        hash = 31 * hash + this.bitmap?.hashCode() ?? 0;
        return hash;
    }
    public equals(other: any): boolean { return other instanceof ImageImageBitmap && (this.bitmap?.equals(other.bitmap) ?? other.bitmap === null) }
    public toString(): string { return `ImageImageBitmap(bitmap = ${this.bitmap})` }
    public copy(bitmap: ImageBitmap = this.bitmap) { return new ImageImageBitmap(bitmap); }
}
//! Declares com.lightningkite.khrysalis.ImageRaw
export class ImageRaw extends Image {
    public readonly raw: Int8Array;
    public constructor(raw: Int8Array) {
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
    public copy(raw: Int8Array = this.raw) { return new ImageRaw(raw); }
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

//! Declares com.lightningkite.khrysalis.asImage>kotlin.String
export function kotlinStringAsImage(this_: string): Image { return new ImageRemoteUrl(this_); }
//! Declares com.lightningkite.khrysalis.asImage>android.net.Uri
export function androidNetUriAsImage(this_: File): Image { return new ImageReference(this_); }
//! Declares com.lightningkite.khrysalis.asImage>android.graphics.Bitmap
export function androidGraphicsBitmapAsImage(this_: ImageBitmap): Image { return new ImageImageBitmap(this_); }

