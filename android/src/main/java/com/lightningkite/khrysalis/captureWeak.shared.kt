package com.lightningkite.khrysalis

import com.lightningkite.khrysalis.AnyObject
import com.lightningkite.khrysalis.weak


fun <Z : AnyObject> captureWeak(capture: Z, lambda: @Escaping() (Z) -> Unit): () -> Unit {
    val captured: Z? by weak(capture)
    return label@{ ->
        val actualCaptured = captured
        if (actualCaptured == null) {
            return@label
        }
        lambda(actualCaptured!!)
    }
}

fun <Z : AnyObject, A> captureWeak(capture: Z, lambda: @Escaping() (Z, A) -> Unit): (A) -> Unit {
    val captured: Z? by weak(capture)
    return label@{ a ->
        val actualCaptured = captured
        if (actualCaptured == null) {
            return@label
        }
        lambda(actualCaptured!!, a)
    }
}

fun <Z : AnyObject, A, B> captureWeak(capture: Z, lambda: @Escaping() (Z, A, B) -> Unit): (A, B) -> Unit {
    val captured: Z? by weak(capture)
    return label@{ a, b ->
        val actualCaptured = captured
        if (actualCaptured == null) {
            return@label
        }
        lambda(actualCaptured!!, a, b)
    }
}

fun <Z : AnyObject, A, B, C> captureWeak(capture: Z, lambda: @Escaping() (Z, A, B, C) -> Unit): (A, B, C) -> Unit {
    val captured: Z? by weak(capture)
    return label@{ a, b, c ->
        val actualCaptured = captured
        if (actualCaptured == null) {
            return@label
        }
        lambda(actualCaptured!!, a, b, c)
    }
}

fun <Z : AnyObject, A, B, C, D> captureWeak(
    capture: Z,
    lambda: @Escaping() (Z, A, B, C, D) -> Unit
): (A, B, C, D) -> Unit {
    val captured: Z? by weak(capture)
    return label@{ a, b, c, d ->
        val actualCaptured = captured
        if (actualCaptured == null) {
            return@label
        }
        lambda(actualCaptured!!, a, b, c, d)
    }
}

fun <Z : AnyObject, A, B, C, D, E> captureWeak(
    capture: Z,
    lambda: @Escaping() (Z, A, B, C, D, E) -> Unit
): (A, B, C, D, E) -> Unit {
    val captured: Z? by weak(capture)
    return label@{ a, b, c, d, e ->
        val actualCaptured = captured
        if (actualCaptured == null) {
            return@label
        }
        lambda(actualCaptured!!, a, b, c, d, e)
    }
}
