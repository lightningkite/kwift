package com.lightningkite.kwift.views.actual

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import com.lightningkite.kwift.actual.ImageData
import com.lightningkite.kwift.actual.ImageReference
import com.lightningkite.kwift.android.ActivityAccess
import com.lightningkite.kwift.views.android.startIntent
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File
import java.lang.Exception

fun ViewDependency.requestImageGallery(
    callback: (ImageReference) -> Unit
) {
    requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE) {
        if (it) {
            val getIntent = Intent(Intent.ACTION_GET_CONTENT)
            getIntent.type = "image/*"

            val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickIntent.type = "image/*"

            val chooserIntent = Intent.createChooser(getIntent, "Select Image")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

            this.startIntent(chooserIntent) { code, result ->
                val uri = result?.data
                if (code == Activity.RESULT_OK && uri != null) {
                    callback(uri)
                }
            }
        }
    }
}

fun ViewDependency.requestImageCamera(
    callback: (ImageReference) -> Unit
) {
    val fileProviderAuthority = context.packageName + ".fileprovider"
    val file = File(context.cacheDir, "images").also { it.mkdirs() }
        .let { File.createTempFile("image", ".jpg", it) }
        .let { FileProvider.getUriForFile(context, fileProviderAuthority, it) }
    requestPermission(Manifest.permission.CAMERA) {
        if (it) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, file)
            startIntent(intent) { code, result ->
                if (code == Activity.RESULT_OK) callback(result?.data ?: file)
            }
        }
    }
}

fun ViewDependency.loadImage(imageReference: ImageReference, onResult: (ImageData?) -> Unit) {
    try {
        onResult(MediaStore.Images.Media.getBitmap(context.contentResolver, imageReference))
    } catch (e: Exception) {
        onResult(null)
    }
}
