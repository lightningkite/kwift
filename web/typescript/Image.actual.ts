// Generated by Khrysalis TypeScript converter
// File: Image.actual.kt
// Package: com.lightningkite.khrysalis
import { Image, ImageImageBitmap, ImageRaw, ImageReference, ImageRemoteUrl } from './Image.shared'
import { Exception } from './Kotlin'
import { kotlinCharSequenceIsBlank } from './kotlin/kotlin.text'
import { also } from 'Kotlin'

//! Declares com.lightningkite.khrysalis.loadImage
export function loadImage(image: Image, onResult: (a: (ImageBitmap | null)) => void): void{
    //TODO
}

//! Declares com.lightningkite.khrysalis.load>com.lightningkite.khrysalis.Image
export function comLightningkiteKhrysalisImageLoad(this_: Image, onResult: (a: (ImageBitmap | null)) => void): void{ return loadImage(this_, onResult); }
