"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
// Generated by Khrysalis TypeScript converter
// File: Preferences.actual.kt
// Package: com.lightningkite.khrysalis
const Codable_actual_1 = require("./Codable.actual");
//! Declares com.lightningkite.khrysalis.Preferences
class Preferences {
    constructor() {
    }
    set(T, key, value) {
        window.localStorage[key] = Codable_actual_1.kotlinAnyToJsonString(value);
    }
    remove(key) {
        delete window.localStorage[key];
    }
    get(T, key) {
        const thing = window.localStorage[key];
        if (thing === null || thing === undefined)
            return null;
        return Codable_actual_1.kotlinStringFromJsonString(thing, T);
    }
    clear() {
        window.localStorage.clear();
    }
}
exports.Preferences = Preferences;
Preferences.INSTANCE = new Preferences();
//# sourceMappingURL=Preferences.actual.js.map