"use strict";
// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: bluetooth/BleServer.actual.kt
// Package: com.lightningkite.khrysalis.bluetooth
Object.defineProperty(exports, "__esModule", { value: true });
//! Declares com.lightningkite.khrysalis.bluetooth.RequestId
exports.RequestId = Number;
//! Declares com.lightningkite.khrysalis.bluetooth.BleResponseStatus
class BleResponseStatus {
    constructor(name, value) {
        this.name = name;
        this.value = value;
    }
    static values() { return BleResponseStatus._values; }
    static valueOf(name) { return BleResponseStatus[name]; }
    toString() { return this.name; }
}
exports.BleResponseStatus = BleResponseStatus;
//CBATTError.Code in iOS
BleResponseStatus.success = new BleResponseStatus("success", 0);
BleResponseStatus.invalidHandle = new BleResponseStatus("invalidHandle", 1);
BleResponseStatus.readNotPermitted = new BleResponseStatus("readNotPermitted", 2);
BleResponseStatus.writeNotPermitted = new BleResponseStatus("writeNotPermitted", 3);
BleResponseStatus.invalidPdu = new BleResponseStatus("invalidPdu", 4);
BleResponseStatus.insufficientAuthentication = new BleResponseStatus("insufficientAuthentication", 5);
BleResponseStatus.requestNotSupported = new BleResponseStatus("requestNotSupported", 6);
BleResponseStatus.invalidOffset = new BleResponseStatus("invalidOffset", 7);
BleResponseStatus.insufficientAuthorization = new BleResponseStatus("insufficientAuthorization", 8);
BleResponseStatus.prepareQueueFull = new BleResponseStatus("prepareQueueFull", 9);
BleResponseStatus.attributeNotFound = new BleResponseStatus("attributeNotFound", 10);
BleResponseStatus.attributeNotLong = new BleResponseStatus("attributeNotLong", 11);
BleResponseStatus.insufficientEncryptionKeySize = new BleResponseStatus("insufficientEncryptionKeySize", 12);
BleResponseStatus.invalidAttributeValueLength = new BleResponseStatus("invalidAttributeValueLength", 13);
BleResponseStatus.unlikelyError = new BleResponseStatus("unlikelyError", 14);
BleResponseStatus.insufficientEncryption = new BleResponseStatus("insufficientEncryption", 15);
BleResponseStatus.unsupportedGroupType = new BleResponseStatus("unsupportedGroupType", 16);
BleResponseStatus.insufficientResources = new BleResponseStatus("insufficientResources", 17);
BleResponseStatus._values = [BleResponseStatus.success, BleResponseStatus.invalidHandle, BleResponseStatus.readNotPermitted, BleResponseStatus.writeNotPermitted, BleResponseStatus.invalidPdu, BleResponseStatus.insufficientAuthentication, BleResponseStatus.requestNotSupported, BleResponseStatus.invalidOffset, BleResponseStatus.insufficientAuthorization, BleResponseStatus.prepareQueueFull, BleResponseStatus.attributeNotFound, BleResponseStatus.attributeNotLong, BleResponseStatus.insufficientEncryptionKeySize, BleResponseStatus.invalidAttributeValueLength, BleResponseStatus.unlikelyError, BleResponseStatus.insufficientEncryption, BleResponseStatus.unsupportedGroupType, BleResponseStatus.insufficientResources];