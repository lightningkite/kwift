"use strict";
// Generated by Khrysalis TypeScript converter
// File: time/TimeZone.actual.kt
// Package: com.lightningkite.khrysalis.time
var _a;
Object.defineProperty(exports, "__esModule", { value: true });
/* SHARED DECLARATIONS
class TimeZone {

    val id: String
    val displayName: String
    fun getOffset(date: Long): Int

    companion object {
        fun getDefault(): TimeZone {}
    }
}
 */
class TimeZone {
    constructor(id, displayName, getOffset) {
        this.id = id;
        this.displayName = displayName;
        this.getOffset = getOffset;
    }
}
exports.TimeZone = TimeZone;
TimeZone.Companion = (_a = class Companion {
        getDefault() {
            return new TimeZone("local", new Date().toLocaleString(undefined, {
                timeZoneName: "long"
            }), (date) => {
                return new Date(date).getTimezoneOffset() * 1000;
            });
        }
    },
    _a.INSTANCE = new _a(),
    _a);
//# sourceMappingURL=TimeZone.actual.js.map