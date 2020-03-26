package com.lightningkite.khrysalis.rx

import android.view.View
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView
import com.lightningkite.khrysalis.escaping
import io.reactivex.disposables.Disposable

val View.removed: DisposeCondition
    get() {
        return DisposeCondition { disposable ->
            this.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewDetachedFromWindow(v: View) {
                    disposable.dispose()
                    v.removeOnAttachStateChangeListener(this)
                }

                override fun onViewAttachedToWindow(v: View) {
                    v.parent?.recyclingParent()?.let {
                        v.removeOnAttachStateChangeListener(this)
                        disposable.until(it.removed)
                    }
                }
            })
        }
    }

private fun ViewParent.recyclingParent(): RecyclerView? = (this.parent as? RecyclerView) ?: this.parent?.recyclingParent()


class DisposableLambda(val lambda: @escaping() () -> Unit) : Disposable {
    var disposed: Boolean = false
    override fun isDisposed(): Boolean {
        return disposed
    }

    override fun dispose() {
        if (!disposed) {
            disposed = true
            lambda()
        }
    }
}
