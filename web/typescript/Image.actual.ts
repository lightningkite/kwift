// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: Image.actual.kt
// Package: com.lightningkite.khrysalis
// FQImport: okhttp3.Request.Builder.url TS url
// FQImport: com.lightningkite.khrysalis.ImageReference TS ImageReference
// FQImport: java.io.IOException.printStackTrace TS printStackTrace
// FQImport: com.lightningkite.khrysalis.loadImage.<anonymous>.it TS it
// FQImport: com.lightningkite.khrysalis.loadImage.maxDimension TS maxDimension
// FQImport: com.lightningkite.khrysalis.loadImage.<anonymous>.<no name provided>.onResponse.<anonymous>.it TS it
// FQImport: com.lightningkite.khrysalis.loadImage.onResult TS onResult
// FQImport: com.lightningkite.khrysalis.loadImage.<anonymous>.<anonymous>.<anonymous>.it TS it
// FQImport: com.lightningkite.khrysalis.net.HttpClient.appContext TS appContext
// FQImport: kotlin.ranges.coerceAtLeast TS kotlinIntCoerceAtLeast
// FQImport: com.lightningkite.khrysalis.ImageReference.uri TS uri
// FQImport: com.lightningkite.khrysalis.net.HttpClient.go TS okhttp3CallGo
// FQImport: com.lightningkite.khrysalis.loadImage.finalOpts TS finalOpts
// FQImport: android.graphics.BitmapFactory.decodeByteArray TS decodeByteArray
// FQImport: com.lightningkite.khrysalis.loadImage.image TS image
// FQImport: okhttp3.OkHttpClient.newCall TS newCall
// FQImport: android.content.Context.contentResolver TS getAndroidContentContextContentResolver
// FQImport: android.content.ContentResolver.openInputStream TS openInputStream
// FQImport: kotlin.Exception TS Exception
// FQImport: okhttp3.Request TS Request
// FQImport: android.graphics.BitmapFactory.Options.inSampleSize TS inSampleSize
// FQImport: okhttp3.Request.Builder.get TS get
// FQImport: okhttp3.Response.body TS body
// FQImport: android.graphics.BitmapFactory.decodeStream TS decodeStream
// FQImport: android.graphics.BitmapFactory TS BitmapFactory
// FQImport: com.lightningkite.khrysalis.ImageRemoteUrl.url TS url
// FQImport: kotlin.math.max TS max
// FQImport: com.lightningkite.khrysalis.loadImage TS loadImage
// FQImport: okhttp3.Call TS Call
// FQImport: com.lightningkite.khrysalis.load.onResult TS onResult
// FQImport: com.lightningkite.khrysalis.loadImage.result TS result
// FQImport: com.lightningkite.khrysalis.ImageRaw.raw TS raw
// FQImport: com.lightningkite.khrysalis.Image TS Image
// FQImport: com.lightningkite.khrysalis.loadImage.uri TS uri
// FQImport: java.lang.Math.ceil TS ceil
// FQImport: okhttp3.Response TS Response
// FQImport: android.graphics.BitmapFactory.Options.outHeight TS outHeight
// FQImport: com.lightningkite.khrysalis.loadImage.<anonymous>.<no name provided>.onResponse.response TS response
// FQImport: com.lightningkite.khrysalis.loadImage.url TS url
// FQImport: okhttp3.ResponseBody.byteStream TS byteStream
// FQImport: android.graphics.BitmapFactory.Options.inJustDecodeBounds TS inJustDecodeBounds
// FQImport: com.lightningkite.khrysalis.loadImage.e TS e
// FQImport: com.lightningkite.khrysalis.loadImage.<anonymous>.sizeOpts TS sizeOpts
// FQImport: com.lightningkite.khrysalis.loadImage.call TS call
// FQImport: okhttp3.Request.Builder.build TS build
// FQImport: okhttp3.Callback TS Callback
// FQImport: com.lightningkite.khrysalis.loadImage.<anonymous>.<no name provided>.onResponse.e TS e
// FQImport: kotlin.io.use TS javaIoCloseableUse
// FQImport: com.lightningkite.khrysalis.ImageRaw TS ImageRaw
// FQImport: com.lightningkite.khrysalis.ImageRemoteUrl TS ImageRemoteUrl
// FQImport: java.lang.Math TS Math
// FQImport: com.lightningkite.khrysalis.loadImage.<anonymous>.<no name provided>.onResponse.<anonymous>.<anonymous>.it TS it
// FQImport: com.lightningkite.khrysalis.loadImage SKIPPED due to same file
// FQImport: java.lang.Exception.printStackTrace TS printStackTrace
// FQImport: com.lightningkite.khrysalis.ImageBitmap.bitmap TS bitmap
// FQImport: kotlin.text.isBlank TS kotlinCharSequenceIsBlank
// FQImport: com.lightningkite.khrysalis.net.HttpClient.client TS client
// FQImport: com.lightningkite.khrysalis.ImageBitmap TS ImageImageBitmap
// FQImport: java.io.IOException TS IOException
// FQImport: android.graphics.BitmapFactory.Options.outWidth TS outWidth
// FQImport: com.lightningkite.khrysalis.loadImage.<anonymous>.<no name provided>.onFailure.e TS e
import { Image, ImageImageBitmap, ImageRaw, ImageReference, ImageRemoteUrl } from './Image.shared'
import { kotlinCharSequenceIsBlank } from './kotlin/kotlin.text'
import { also } from 'Kotlin'

//! Declares com.lightningkite.khrysalis.loadImage
export function loadImage(image: Image, onResult: (a: (ImageBitmap | null)) => void): void{
    try {
        if (image instanceof ImageRaw) {
            onResult(BitmapFactory.decodeByteArray(image.raw, 0, image.raw.length))
        } else if (image instanceof ImageReference) {
            loadImage(image.uri, undefined, onResult)
        } else if (image instanceof ImageImageBitmap) {
            onResult(image.bitmap)
        } else if (image instanceof ImageRemoteUrl) {
            loadImage(image.url, onResult)
        }
    } catch (e: Exception) {
        onResult(null);
    };
}

//! Declares com.lightningkite.khrysalis.load
export function comLightningkiteKhrysalisImageLoad(this_: Image, onResult: (a: (ImageBitmap | null)) => void): void{ return loadImage(this_, onResult); }

//! Declares com.lightningkite.khrysalis.loadImage
export function loadImage(uri: string, maxDimension: number = 2048, onResult: (a: (ImageBitmap | null)) => void): void{
    let result: (ImageBitmap | null) = null;
    
    try {
        const finalOpts = BitmapFactory.Options.constructor();
        
        ((_it)=>{
                if(_it === null) return null;
                return javaIoCloseableUse(_it, (it) => {
                        const sizeOpts = also(BitmapFactory.Options.constructor(), (this_) => {
                                this_.inJustDecodeBounds = true
                        });
                        
                        return also(BitmapFactory.decodeStream(it, null, sizeOpts), (this_) => {
                                finalOpts.inSampleSize = kotlinIntCoerceAtLeast(max(Math.floor(((it) => Math.ceil(it))(sizeOpts.outWidth / maxDimension)), Math.floor(((it) => Math.ceil(it))(sizeOpts.outHeight / maxDimension))), 1)
                        });
                })
        })(getAndroidContentContextContentResolver(HttpClient.INSTANCE.appContext).openInputStream(uri));
        ((_it)=>{
                if(_it === null) return null;
                return javaIoCloseableUse(_it, (it) => {
                        result = BitmapFactory.decodeStream(it, null, finalOpts)
                })
        })(getAndroidContentContextContentResolver(HttpClient.INSTANCE.appContext).openInputStream(uri));
    } catch (e: Exception) {
        e.printStackTrace();
    };
    onResult(result);
}

//! Declares com.lightningkite.khrysalis.loadImage
export function loadImage(url: string, onResult: (a: (ImageBitmap | null)) => void): void{
    if (kotlinCharSequenceIsBlank(url)) {
        onResult(null);
        return;
    }
    const call = Request.Builder.constructor()
    .url(url)
    .get()
    .build();
    
    ((this_) => {
            this_.okhttp3CallGo(this_.client.newCall(call), new class Anon implements Callback {
                    public static implementsInterfaceOkhttp3Callback = true;
                    public constructor() {
                    }
                    
                    onFailure(call: Call, e: IOException): void{
                        e.printStackTrace();
                        onResult(null);
                    }
                    
                    onResponse(call: Call, response: Response): void{
                        try {
                            ((_it)=>{
                                    if(_it === null) return null;
                                    return javaIoCloseableUse(_it, (it) => {
                                            javaIoCloseableUse(it.byteStream(), (it) => {
                                                    onResult(BitmapFactory.decodeStream(it))
                                            })
                                    })
                            })(response.body());
                        } catch (e: Exception) {
                            e.printStackTrace();
                            onResult(null);
                        };
                    }
            }())
    })(HttpClient.INSTANCE);
}

