package com.lightningkite.khrysalis.observables.binding

import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.widget.ImageView
import com.lightningkite.khrysalis.net.HttpClient
import com.lightningkite.khrysalis.*
import com.squareup.picasso.Picasso
import com.lightningkite.khrysalis.observables.*
import com.lightningkite.khrysalis.rx.removed
import com.lightningkite.khrysalis.rx.until


fun ImageView.loadImage(image: Image?) {
    post {
        image?.let { image ->
            when (image) {
                is ImageRaw -> this.setImageBitmap(BitmapFactory.decodeByteArray(image.raw, 0, image.raw.size))
                is ImageReference -> this.setImageBitmap(
                    MediaStore.Images.Media.getBitmap(
                        HttpClient.appContext.contentResolver,
                        image.uri
                    )
                )
                is ImageBitmap -> this.setImageBitmap(image.bitmap)
                is ImageRemoteUrl -> {
                    if (image.url.isNotBlank() && this.width > 0 && this.height > 0) {
                        Picasso.get().load(image.url).resize(this.width * 2, this.height * 2).centerInside().into(this)
                    }
                }
            }
        }
        if (image == null) {
            this.setImageDrawable(null)
        }
    }
}

fun ImageView.bindImage(image: ObservableProperty<Image?>) {
    post {
        image.subscribeBy { it ->
            this.loadImage(it)
        }.until(this.removed)
    }
}
