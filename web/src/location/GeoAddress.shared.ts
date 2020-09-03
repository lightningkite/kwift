// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: location/GeoAddress.shared.kt
// Package: com.lightningkite.khrysalis.location
import { hashAnything, safeEq } from '../Kotlin'
import { StringBuilder } from '../kotlin/kotlin.text'
import { GeoCoordinate } from './GeoCoordinate.shared'
import { parse as parseJsonTyped } from '../net/jsonParsing'
import { runOrNull } from '../kotlin/Language'

//! Declares com.lightningkite.khrysalis.location.GeoAddress
export class GeoAddress {
    public readonly coordinate: (GeoCoordinate | null);
    public readonly name: (string | null);
    public readonly street: (string | null);
    public readonly subLocality: (string | null);
    public readonly locality: (string | null);
    public readonly subAdminArea: (string | null);
    public readonly adminArea: (string | null);
    public readonly countryName: (string | null);
    public readonly postalCode: (string | null);
    public constructor(coordinate: (GeoCoordinate | null) = null, name: (string | null) = null, street: (string | null) = null, subLocality: (string | null) = null, locality: (string | null) = null, subAdminArea: (string | null) = null, adminArea: (string | null) = null, countryName: (string | null) = null, postalCode: (string | null) = null) {
        this.coordinate = coordinate;
        this.name = name;
        this.street = street;
        this.subLocality = subLocality;
        this.locality = locality;
        this.subAdminArea = subAdminArea;
        this.adminArea = adminArea;
        this.countryName = countryName;
        this.postalCode = postalCode;
    }
    public static fromJson(obj: any): GeoAddress { return new GeoAddress(
            parseJsonTyped(obj["coordinate"], [GeoCoordinate]) as (GeoCoordinate | null), 
            parseJsonTyped(obj["name"], [String]) as (string | null), 
            parseJsonTyped(obj["street"], [String]) as (string | null), 
            parseJsonTyped(obj["subLocality"], [String]) as (string | null), 
            parseJsonTyped(obj["locality"], [String]) as (string | null), 
            parseJsonTyped(obj["subAdminArea"], [String]) as (string | null), 
            parseJsonTyped(obj["adminArea"], [String]) as (string | null), 
            parseJsonTyped(obj["countryName"], [String]) as (string | null), 
            parseJsonTyped(obj["postalCode"], [String]) as (string | null)
    ) }
    public toJSON(): object { return {
            coordinate: this.coordinate, 
            name: this.name, 
            street: this.street, 
            subLocality: this.subLocality, 
            locality: this.locality, 
            subAdminArea: this.subAdminArea, 
            adminArea: this.adminArea, 
            countryName: this.countryName, 
            postalCode: this.postalCode
    } }
    public hashCode(): number {
        let hash = 17;
        hash = 31 * hash + hashAnything(this.coordinate);
        hash = 31 * hash + hashAnything(this.name);
        hash = 31 * hash + hashAnything(this.street);
        hash = 31 * hash + hashAnything(this.subLocality);
        hash = 31 * hash + hashAnything(this.locality);
        hash = 31 * hash + hashAnything(this.subAdminArea);
        hash = 31 * hash + hashAnything(this.adminArea);
        hash = 31 * hash + hashAnything(this.countryName);
        hash = 31 * hash + hashAnything(this.postalCode);
        return hash;
    }
    public equals(other: any): boolean { return other instanceof GeoAddress && safeEq(this.coordinate, other.coordinate) && safeEq(this.name, other.name) && safeEq(this.street, other.street) && safeEq(this.subLocality, other.subLocality) && safeEq(this.locality, other.locality) && safeEq(this.subAdminArea, other.subAdminArea) && safeEq(this.adminArea, other.adminArea) && safeEq(this.countryName, other.countryName) && safeEq(this.postalCode, other.postalCode) }
    public toString(): string { return `GeoAddress(coordinate = ${this.coordinate}, name = ${this.name}, street = ${this.street}, subLocality = ${this.subLocality}, locality = ${this.locality}, subAdminArea = ${this.subAdminArea}, adminArea = ${this.adminArea}, countryName = ${this.countryName}, postalCode = ${this.postalCode})` }
    public copy(coordinate: (GeoCoordinate | null) = this.coordinate, name: (string | null) = this.name, street: (string | null) = this.street, subLocality: (string | null) = this.subLocality, locality: (string | null) = this.locality, subAdminArea: (string | null) = this.subAdminArea, adminArea: (string | null) = this.adminArea, countryName: (string | null) = this.countryName, postalCode: (string | null) = this.postalCode): GeoAddress { return new GeoAddress(coordinate, name, street, subLocality, locality, subAdminArea, adminArea, countryName, postalCode); }
    
    public oneLine(withCountry: boolean = false, withZip: boolean = false): string {
        const builder = new StringBuilder();
        
        const it_9 = this.street;
        if (it_9 !== null) { 
            builder.value += it_9;
        }
        const it_10 = this.locality;
        if (it_10 !== null) { 
            builder.value += ' ';
            builder.value += it_10;
        }
        const it_11 = this.adminArea;
        if (it_11 !== null) { 
            builder.value += ", ";
            builder.value += it_11;
        }
        if (withCountry) {
            const it_13 = this.adminArea;
            if (it_13 !== null) { 
                builder.value += ' ';
                builder.value += it_13;
            }
        }
        if (withZip) {
            const it_14 = this.postalCode;
            if (it_14 !== null) { 
                builder.value += ' ';
                builder.value += it_14;
            }
        }
        return builder.toString().trim();
    }
}

