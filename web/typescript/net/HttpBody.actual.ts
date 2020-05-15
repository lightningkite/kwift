// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: net/HttpBody.actual.kt
// Package: com.lightningkite.khrysalis.net
// FQImport: okhttp3.MultipartBody.Builder.setType TS setType
// FQImport: com.lightningkite.khrysalis.net.multipartFormBody.<anonymous>.it TS it
// FQImport: com.lightningkite.khrysalis.net.multipartFormFilePart.value TS value
// FQImport: com.lightningkite.khrysalis.net.HttpMediaTypes.TEXT TS TEXT
// FQImport: com.lightningkite.khrysalis.net.toHttpBody.data TS data
// FQImport: com.lightningkite.khrysalis.net.HttpMediaType TS HttpMediaType
// FQImport: com.lightningkite.khrysalis.net.HttpBodyPart TS HttpBodyPart
// FQImport: com.lightningkite.khrysalis.net.HttpClient.mapper TS mapper
// FQImport: com.lightningkite.khrysalis.net.multipartFormBody.<anonymous>.part TS part
// FQImport: okhttp3.MultipartBody.Part.createFormData TS createFormData
// FQImport: okhttp3.RequestBody TS RequestBody
// FQImport: com.lightningkite.khrysalis.net.multipartFormFilePart.filename TS filename
// FQImport: com.lightningkite.khrysalis.net.multipartFormFilePart.body TS body
// FQImport: android.graphics.Bitmap TS Bitmap
// FQImport: android.graphics.Bitmap.compress TS compress
// FQImport: com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString TS writeValueAsString
// FQImport: android.util.Log TS Log
// FQImport: java.io.ByteArrayOutputStream.toByteArray TS toByteArray
// FQImport: com.lightningkite.khrysalis.net.multipartFormBody.parts TS parts
// FQImport: com.lightningkite.khrysalis.net.toHttpBody.qualityToTry TS qualityToTry
// FQImport: com.lightningkite.khrysalis.net.HttpBody TS HttpBody
// FQImport: kotlin.ByteArray.size TS size
// FQImport: com.lightningkite.khrysalis.net.toHttpBody.mediaType TS mediaType
// FQImport: android.graphics.Bitmap.CompressFormat TS CompressFormat
// FQImport: com.lightningkite.khrysalis.net.HttpBody SKIPPED due to same file
// FQImport: com.lightningkite.khrysalis.net.HttpMediaTypes.JSON TS JSON
// FQImport: okhttp3.MultipartBody.FORM TS FORM
// FQImport: android.util.Log.i TS i
// FQImport: okhttp3.MultipartBody.Builder.addPart TS addPart
// FQImport: okhttp3.MultipartBody.Part TS Part
// FQImport: com.lightningkite.khrysalis.net.toJsonHttpBody.sending TS sending
// FQImport: kotlin.io.use TS javaIoCloseableUse
// FQImport: com.lightningkite.khrysalis.net.toHttpBody.maxBytes TS maxBytes
// FQImport: okhttp3.RequestBody.create TS create
// FQImport: okhttp3.MultipartBody TS MultipartBody
// FQImport: okhttp3.MultipartBody.Builder.build TS build
// FQImport: com.lightningkite.khrysalis.net.multipartFormFilePart.name TS name
// FQImport: com.lightningkite.khrysalis.net.HttpMediaTypes.JPEG TS JPEG
// FQImport: java.io.ByteArrayOutputStream TS ByteArrayOutputStream
// FQImport: com.lightningkite.khrysalis.net.toHttpBody.<anonymous>.it TS it
// FQImport: com.lightningkite.khrysalis.bytes.Data TS Data
// FQImport: android.graphics.Bitmap.CompressFormat.JPEG TS JPEG
// FQImport: com.lightningkite.khrysalis.net.HttpBodyPart SKIPPED due to same file
// FQImport: com.lightningkite.khrysalis.Codable TS Codable
import { Data } from './../bytes/Data.actual'
import { Codable } from './../Codable.actual'
import { also } from 'khrysalis/dist/Kotlin'
import { HttpMediaType } from './HttpMediaType.actual'

//! Declares com.lightningkite.khrysalis.net.HttpBody
export type HttpBody = RequestBody;
//! Declares com.lightningkite.khrysalis.net.HttpBody
export let HttpBody = RequestBody;

//! Declares com.lightningkite.khrysalis.net.HttpBodyPart
export type HttpBodyPart = MultipartBody.Part;
//! Declares com.lightningkite.khrysalis.net.HttpBodyPart
export let HttpBodyPart = Part;


//! Declares com.lightningkite.khrysalis.net.toJsonHttpBody
export function comLightningkiteKhrysalisCodableToJsonHttpBody(this_ToJsonHttpBody: Codable): HttpBody{
    const sending = HttpClient.INSTANCE.mapper.writeValueAsString(this_ToJsonHttpBody);
    
    Log.i("HttpClient", `with body ${sending}`);
    return RequestBody.create(HttpMediaTypes.INSTANCE.JSON, sending);
}

//! Declares com.lightningkite.khrysalis.net.toHttpBody
export function kotlinByteArrayToHttpBody(this_ToHttpBody: Data, mediaType: HttpMediaType): HttpBody{
    return RequestBody.create(mediaType, this_ToHttpBody);
}

//! Declares com.lightningkite.khrysalis.net.toHttpBody
export function kotlinStringToHttpBody(this_ToHttpBody: string, mediaType: HttpMediaType = HttpMediaTypes.INSTANCE.TEXT): HttpBody{
    return RequestBody.create(mediaType, this_ToHttpBody);
}

//! Declares com.lightningkite.khrysalis.net.toHttpBody
export function androidGraphicsBitmapToHttpBody(this_ToHttpBody: Bitmap, maxBytes: number = 10_000_000): HttpBody{
    let qualityToTry = 100;
    
    let data = javaIoCloseableUse(ByteArrayOutputStream.constructor(), (it) => {
            this_ToHttpBody.compress(Bitmap.CompressFormat.JPEG, qualityToTry, it);
            it.toByteArray();
    });
    
    while (data.size > maxBytes) {
        qualityToTry = qualityToTry - 5;
        data = javaIoCloseableUse(ByteArrayOutputStream.constructor(), (it) => {
                this_ToHttpBody.compress(Bitmap.CompressFormat.JPEG, qualityToTry, it);
                it.toByteArray();
        });
    }
    return RequestBody.create(HttpMediaTypes.INSTANCE.JPEG, data);
}

//! Declares com.lightningkite.khrysalis.net.multipartFormBody
export function multipartFormBody(...parts: HttpBodyPart[]): HttpBody{
    return also(MultipartBody.Builder.constructor().setType(MultipartBody.FORM), (it) => for (const part of parts) {
            return it.addPart(part);
    }).build();
}
//! Declares com.lightningkite.khrysalis.net.multipartFormFilePart
export function multipartFormFilePart(name: string, value: string): HttpBodyPart{ return HttpBodyPart.createFormData(name, value); }
//! Declares com.lightningkite.khrysalis.net.multipartFormFilePart
export function multipartFormFilePart(name: string, filename: (string | null) = null, body: HttpBody): HttpBodyPart{ return HttpBodyPart.createFormData(name, filename, body); }

