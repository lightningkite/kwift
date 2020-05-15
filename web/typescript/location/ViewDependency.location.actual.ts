// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: location/ViewDependency.location.actual.kt
// Package: com.lightningkite.khrysalis.location
// FQImport: com.lightningkite.khrysalis.location.requestLocation.timeoutInSeconds TS timeoutInSeconds
// FQImport: android.os.Looper TS Looper
// FQImport: time TS getJavaUtilDateTime
// FQImport: android.location.Location TS Location
// FQImport: android.location.LocationManager.requestSingleUpdate TS requestSingleUpdate
// FQImport: android.location.LocationManager.removeUpdates TS removeUpdates
// FQImport: com.lightningkite.khrysalis.location.requestLocation.<anonymous>.criteria TS criteria
// FQImport: latitude TS getAndroidLocationLocationLatitude
// FQImport: bearing TS getAndroidLocationLocationBearing
// FQImport: java.util.concurrent.atomic.AtomicBoolean.compareAndSet TS compareAndSet
// FQImport: altitude TS getAndroidLocationLocationAltitude
// FQImport: android.os.Handler.postDelayed TS postDelayed
// FQImport: android.os.Handler TS Handler
// FQImport: com.lightningkite.khrysalis.android.ActivityAccess.context TS context
// FQImport: android.location.Criteria.ACCURACY_MEDIUM TS ACCURACY_MEDIUM
// FQImport: kotlin.Exception TS Exception
// FQImport: com.lightningkite.khrysalis.location.LocationCache SKIPPED due to same file
// FQImport: android.content.Context.getSystemService TS getSystemService
// FQImport: android.location.LocationListener TS LocationListener
// FQImport: com.lightningkite.khrysalis.location.requestLocation TS comLightningkiteKhrysalisAndroidActivityAccessRequestLocation
// FQImport: com.lightningkite.khrysalis.location.lastLocation TS getLastLocation
// FQImport: com.lightningkite.khrysalis.location.requestLocation.listener TS listener
// FQImport: android.location.LocationManager TS LocationManager
// FQImport: android.Manifest TS Manifest
// FQImport: android.location.Criteria TS Criteria
// FQImport: android.os.Bundle TS Bundle
// FQImport: android.Manifest.permission TS permission
// FQImport: java.util.concurrent.atomic.AtomicBoolean TS AtomicBoolean
// FQImport: accuracy TS getAndroidLocationLocationAccuracy
// FQImport: horizontalAccuracy TS setAndroidLocationCriteriaHorizontalAccuracy
// FQImport: speed TS getAndroidLocationLocationSpeed
// FQImport: com.lightningkite.khrysalis.location.requestLocation.<anonymous>.e TS e
// FQImport: com.lightningkite.khrysalis.location.requestLocation.alreadyDone TS alreadyDone
// FQImport: android.Manifest.permission.ACCESS_FINE_LOCATION TS ACCESS_FINE_LOCATION
// FQImport: android.location.Criteria.ACCURACY_LOW TS ACCURACY_LOW
// FQImport: java.util.Date TS Date
// FQImport: com.lightningkite.khrysalis.location.lastLocation SKIPPED due to same file
// FQImport: com.lightningkite.khrysalis.location.requestLocation SKIPPED due to same file
// FQImport: com.lightningkite.khrysalis.location.requestLocation.manager TS manager
// FQImport: com.lightningkite.khrysalis.location.requestLocation.<anonymous>.it TS it
// FQImport: com.lightningkite.khrysalis.location.requestLocation.accuracyBetterThanMeters TS accuracyBetterThanMeters
// FQImport: com.lightningkite.khrysalis.location.LocationCache.location TS location
// FQImport: com.lightningkite.khrysalis.location.requestLocationCached.<anonymous>.string TS string
// FQImport: android.os.Looper.getMainLooper TS getMainLooper
// FQImport: com.lightningkite.khrysalis.location.requestLocation.<no name provided>.onLocationChanged.location TS location
// FQImport: com.lightningkite.khrysalis.location.LocationCache.accuracy TS accuracy
// FQImport: longitude TS getAndroidLocationLocationLongitude
// FQImport: java.util TS util
// FQImport: com.lightningkite.khrysalis.location.requestLocationCached.onResult TS onResult
// FQImport: com.lightningkite.khrysalis.android.ActivityAccess.requestPermission TS requestPermission
// FQImport: java TS java
// FQImport: com.lightningkite.khrysalis.location.lastLocation TS setLastLocation
// FQImport: com.lightningkite.khrysalis.location.requestLocationCached.<anonymous>.<anonymous>.it TS it
// FQImport: android.content.Context TS Context
// FQImport: com.lightningkite.khrysalis.views.ViewDependency TS ViewDependency
// FQImport: com.lightningkite.khrysalis.location.requestLocation.onResult TS onResult
// FQImport: android.location.Criteria.ACCURACY_HIGH TS ACCURACY_HIGH
// FQImport: com.lightningkite.khrysalis.location.GeoCoordinate TS GeoCoordinate
// FQImport: com.lightningkite.khrysalis.location.requestLocationCached.accuracyBetterThanMeters TS accuracyBetterThanMeters
// FQImport: android.content.Context.LOCATION_SERVICE TS LOCATION_SERVICE
// FQImport: com.lightningkite.khrysalis.location.LocationCache.timeSinceCall TS timeSinceCall
// FQImport: java.lang.Exception.printStackTrace TS printStackTrace
// FQImport: com.lightningkite.khrysalis.location.LocationCache TS LocationCache
// FQImport: com.lightningkite.khrysalis.location.LocationResult TS LocationResult
// FQImport: com.lightningkite.khrysalis.location.requestLocationCached.<anonymous>.result TS result
import { GeoCoordinate } from './GeoCoordinate.shared'
import { Range, tryCastClass } from 'khrysalis/dist/Kotlin'
import { ViewDependency } from './../views/ViewDependency.actual'
import { LocationResult } from './LocationResult.shared'

//! Declares com.lightningkite.khrysalis.location.requestLocation
export function comLightningkiteKhrysalisAndroidActivityAccessRequestLocation(this_RequestLocation: ViewDependency, accuracyBetterThanMeters: number = 10.0, timeoutInSeconds: number = 100.0, onResult: (a: (LocationResult | null), b: (string | null)) => void){
    const alreadyDone = AtomicBoolean.constructorkotlinBoolean(false);
    
    const manager = (tryCastClass(context.getSystemService(Context.LOCATION_SERVICE), LocationManager));
    
    const listener = new class Anon implements LocationListener {
        public static implementsInterfaceAndroidLocationLocationListener = true;
        public constructor() {
        }
        
        onLocationChanged(location: Location){
            alreadyDone.compareAndSet(false, true) ? this.onResult(
                new LocationResult(new GeoCoordinate(getAndroidLocationLocationLatitude(location), getAndroidLocationLocationLongitude(location)), getAndroidLocationLocationAccuracy(location), getAndroidLocationLocationAltitude(location), 100.0, getAndroidLocationLocationBearing(location), getAndroidLocationLocationSpeed(location)),
                null
            ) : 
        }
        
        onStatusChanged(p0: (string | null), p1: number, p2: (Bundle | null)){
            
        }
        
        onProviderEnabled(p0: (string | null)){
            
        }
        
        onProviderDisabled(p0: (string | null)){
            
        }
    }();
    
    requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, (it) => if (it) {
            const criteria = Criteria.constructor();
            
            setAndroidLocationCriteriaHorizontalAccuracy(criteria, (() => {if(in new Range(0f, 100f).contains(accuracyBetterThanMeters)){
                            return Criteria.ACCURACY_HIGH
                        }else if(in new Range(100f, 500f).contains(accuracyBetterThanMeters)){
                            return Criteria.ACCURACY_MEDIUM
                        }else {
                            return Criteria.ACCURACY_LOW
            }})());
            manager?.requestSingleUpdate(Criteria.constructor(), listener, Looper.getMainLooper());
        } else {
            alreadyDone.compareAndSet(false, true) ? this.onResult(null, "No permission") : 
    });
    return Handler.constructorandroidosLooper(Looper.getMainLooper()).postDelayed(() => try {
            manager?.removeUpdates(listener);
            alreadyDone.compareAndSet(false, true) ? this.onResult(null, "Timeout") : 
        } catch (e: Exception) {
            e.printStackTrace();
            //squish
    }, Math.floor(timeoutInSeconds * 1000));
}


//! Declares com.lightningkite.khrysalis.location.LocationCache
export class LocationCache {
    public location: LocationResult;
    public timeSinceCall: Date;
    public accuracy: number;
    public constructor( location: LocationResult,  timeSinceCall: Date,  accuracy: number) {
        this.location = location;
        this.timeSinceCall = timeSinceCall;
        this.accuracy = accuracy;
    }
    public hashCode(): number {
        let hash = 17;
        hash = 31 * hash + this.location.hashCode();
        hash = 31 * hash + this.timeSinceCall.hashCode();
        hash = 31 * hash + Math.floor(this.accuracy);
        return hash;
    }
    public equals(other: any): boolean { return other instanceof LocationCache && this.location.equals(other.location) && this.timeSinceCall.equals(other.timeSinceCall) && this.accuracy === other.accuracy }
    public toString(): string { return `LocationCache(location = ${this.location}, timeSinceCall = ${this.timeSinceCall}, accuracy = ${this.accuracy})` }
    public copy(location: LocationResult = this.location, timeSinceCall: Date = this.timeSinceCall, accuracy: number = this.accuracy) { return new LocationCache(location, timeSinceCall, accuracy); }
}

//! Declares com.lightningkite.khrysalis.location.lastLocation
export let _lastLocation: (LocationCache | null) = null;
export function getLastLocation(): (LocationCache | null) { return _lastLocation; }
export function setLastLocation(value: (LocationCache | null)) { _lastLocation = value; }


//! Declares com.lightningkite.khrysalis.location.requestLocationCached
export function comLightningkiteKhrysalisAndroidActivityAccessRequestLocationCached(this_RequestLocationCached: ViewDependency, accuracyBetterThanMeters: number = 10.0, timeoutInSeconds: number = 100.0, onResult: (a: (LocationResult | null), b: (string | null)) => void){
    if (!(getLastLocation().equals(null)) && getJavaUtilDateTime(getLastLocation()!!.timeSinceCall) - getJavaUtilDateTime(java.util.Date.constructor()) < 300000 && getLastLocation()!!.accuracy < accuracyBetterThanMeters) {
        this.onResult(
            getLastLocation()!!.location,
            null
        );
    } else {
        comLightningkiteKhrysalisAndroidActivityAccessRequestLocation(accuracyBetterThanMeters, undefined, (result, string) => {
                result?.((it) => setLastLocation(new LocationCache(it, Date.constructor(), accuracyBetterThanMeters)))(this);
                this.onResult(result, string);
        });
    }
}

