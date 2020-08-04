"use strict";
// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: observables/Closeable.actual.kt
// Package: com.lightningkite.khrysalis.observables
Object.defineProperty(exports, "__esModule", { value: true });
var CloseableDefaults;
(function (CloseableDefaults) {
    function isDisposed(this_) {
        return false;
    }
    CloseableDefaults.isDisposed = isDisposed;
    function dispose(this_) {
        this_.close();
    }
    CloseableDefaults.dispose = dispose;
})(CloseableDefaults = exports.CloseableDefaults || (exports.CloseableDefaults = {}));
//! Declares com.lightningkite.khrysalis.observables.Close
class Close {
    constructor(closer) {
        this.closer = closer;
        this.disposed = false;
    }
    isDisposed() {
        return this.disposed;
    }
    close() {
        this.disposed = true;
        this.closer();
    }
    dispose() { return CloseableDefaults.dispose(this); }
}
exports.Close = Close;
Close.implementsInterfaceComLightningkiteKhrysalisObservablesCloseable = true;
Close.implementsInterfaceIoReactivexDisposablesDisposable = true;
//# sourceMappingURL=Closeable.actual.js.map