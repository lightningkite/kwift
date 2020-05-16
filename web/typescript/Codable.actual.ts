// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: Codable.actual.kt
// Package: com.lightningkite.khrysalis
// FQImport: com.lightningkite.khrysalis.IsCodable SKIPPED due to same file
// FQImport: com.fasterxml.jackson.module.kotlin.jacksonTypeRef TS jacksonTypeRef
// FQImport: kotlin.jvm.java TS getKotlinReflectKClassJava
// FQImport: kotlin.collections.Map TS Map
// FQImport: com.fasterxml.jackson.databind.ObjectMapper.readValue TS readValue
// FQImport: com.lightningkite.khrysalis.net.HttpClient.mapper TS mapper
// FQImport: com.lightningkite.khrysalis.fromJsonString.e TS e
// FQImport: com.lightningkite.khrysalis.fromJsonString.T TS T
// FQImport: kotlin.Any TS Any
// FQImport: java.lang.Exception.printStackTrace TS printStackTrace
// FQImport: com.lightningkite.khrysalis.fromJsonStringUntyped.e TS e
// FQImport: com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString TS writeValueAsString
// FQImport: com.lightningkite.khrysalis.IsCodable TS IsCodable
// FQImport: kotlin.Exception TS Exception

//! Declares com.lightningkite.khrysalis.Codable
export interface Codable {
}
export class CodableDefaults {
}
//! Declares com.lightningkite.khrysalis.IsCodable
export type IsCodable = any;
//! Declares com.lightningkite.khrysalis.IsCodable
export let IsCodable = null;

//! Declares com.lightningkite.khrysalis.JsonList
export type JsonList = Array<*>;
//! Declares com.lightningkite.khrysalis.JsonList
export let JsonList = Array;

//! Declares com.lightningkite.khrysalis.JsonMap
export type JsonMap = Map<*, *>;
//! Declares com.lightningkite.khrysalis.JsonMap
export let JsonMap = Map;


//! Declares com.lightningkite.khrysalis.toJsonString
export function kotlinAnyToJsonString(this_: (IsCodable | null)): string{
    return HttpClient.INSTANCE.mapper.writeValueAsString(this_);
}

//! Declares com.lightningkite.khrysalis.fromJsonString
export function kotlinStringFromJsonString<T extends IsCodable>(this_: string): (T | null){
    return try {
        return HttpClient.INSTANCE.mapper.readValue(this_, jacksonTypeRef<T>());
    } catch (e: Exception) {
        e.printStackTrace();
        return null;
    };
}

//! Declares com.lightningkite.khrysalis.fromJsonStringUntyped
export function kotlinStringFromJsonStringUntyped(this_: string): (IsCodable | null){
    return try {
        return HttpClient.INSTANCE.mapper.readValue(this_, getKotlinReflectKClassJava(Any::class));
    } catch (e: Exception) {
        e.printStackTrace();
        return null;
    };
}


