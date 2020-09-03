"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
// Generated by Khrysalis TypeScript converter
// File: Video.actual.kt
// Package: com.lightningkite.khrysalis
const Video_shared_1 = require("./Video.shared");
const Image_shared_1 = require("./Image.shared");
const Language_1 = require("./kotlin/Language");
const rxjs_1 = require("rxjs");
//! Declares com.lightningkite.khrysalis.thumbnail>com.lightningkite.khrysalis.Video
function xVideoThumbnail(this_, timeMs = 2000, size = null) {
    return new rxjs_1.Observable((em) => {
        if (this_ instanceof Video_shared_1.VideoReference) {
            const url = URL.createObjectURL(this_.uri);
            videoUrlToBitmap(url, timeMs, (x) => em.next(x), (e) => em.error(e));
        }
        else if (this_ instanceof Video_shared_1.VideoRemoteUrl) {
            videoUrlToBitmap(this_.url, timeMs, (x) => em.next(x), (e) => em.error(e));
        }
        else {
            em.error(new Language_1.IllegalArgumentException(`Unrecognized type ${this_}`, null));
        }
    });
}
exports.xVideoThumbnail = xVideoThumbnail;
function videoUrlToBitmap(url, timeMs = 2000, onResult, onFail) {
    let video = document.createElement('video');
    let timeupdate = function () {
        if (snapImage()) {
            video.removeEventListener('timeupdate', timeupdate);
            video.pause();
        }
    };
    video.addEventListener('loadeddata', function () {
        if (snapImage()) {
            video.removeEventListener('timeupdate', timeupdate);
        }
    });
    video.addEventListener('error', function (it) {
        onFail(it.error);
    });
    let snapImage = function () {
        let canvas = document.createElement('canvas');
        canvas.width = video.videoWidth;
        canvas.height = video.videoHeight;
        canvas.getContext('2d').drawImage(video, 0, 0, canvas.width, canvas.height);
        let image = canvas.toDataURL();
        let success = image.length > 10000;
        if (success) {
            onResult(new Image_shared_1.ImageRemoteUrl(image));
        }
        return success;
    };
    video.addEventListener('timeupdate', timeupdate);
    video.preload = 'metadata';
    video.src = url;
    // Load video in Safari / IE11
    video.muted = true;
    video.currentTime = timeMs / 1000;
    if (video.playsInline) {
        video.playsInline = true;
    }
    video.play();
}
//# sourceMappingURL=Video.actual.js.map