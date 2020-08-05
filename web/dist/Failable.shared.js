"use strict";
// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: Failable.shared.kt
// Package: com.lightningkite.khrysalis
Object.defineProperty(exports, "__esModule", { value: true });
//! Declares com.lightningkite.khrysalis.Failable
class Failable {
    constructor(result = null, issue = null) {
        this.result = result;
        this.issue = issue;
    }
}
exports.Failable = Failable;
(function (Failable) {
    //! Declares com.lightningkite.khrysalis.Failable.Companion
    class Companion {
        constructor() {
        }
        failure(message) { return new Failable(undefined, message); }
        success(value) { return new Failable(value, undefined); }
    }
    Companion.INSTANCE = new Companion();
    Failable.Companion = Companion;
})(Failable = exports.Failable || (exports.Failable = {}));
//# sourceMappingURL=Failable.shared.js.map