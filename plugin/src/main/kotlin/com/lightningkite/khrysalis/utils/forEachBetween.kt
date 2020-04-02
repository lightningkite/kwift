package com.lightningkite.khrysalis.utils

import java.util.concurrent.*
import kotlin.streams.asStream

inline fun <T> Iterable<T>.forEachBetween(
    forItem: (T) -> Unit,
    between: () -> Unit
) {
    var hasDoneFirst = false
    forEach {
        if (hasDoneFirst) {
            between()
        } else {
            hasDoneFirst = true
        }
        forItem(it)
    }
}


inline fun <T> Sequence<T>.forEachBetween(
    forItem: (T) -> Unit,
    between: () -> Unit
) {
    var hasDoneFirst = false
    forEach {
        if (hasDoneFirst) {
            between()
        } else {
            hasDoneFirst = true
        }
        forItem(it)
    }
}

inline fun <T> Iterable<T>.forEachBetweenIndexed(
    forItem: (Int, T) -> Unit,
    between: () -> Unit
) {
    var hasDoneFirst = false
    forEachIndexed { index, it ->
        if (hasDoneFirst) {
            between()
        } else {
            hasDoneFirst = true
        }
        forItem(index, it)
    }
}


inline fun <T> Sequence<T>.forEachBetweenIndexed(
    forItem: (Int, T) -> Unit,
    between: () -> Unit
) {
    var hasDoneFirst = false
    forEachIndexed { index, it ->
        if (hasDoneFirst) {
            between()
        } else {
            hasDoneFirst = true
        }
        forItem(index, it)
    }
}

fun <T> Collection<T>.forEachMultithreaded(action: (T)->Unit) {
    this.parallelStream().forEach(action)
}

fun <T> Sequence<T>.forEachMultithreaded(action: (T)->Unit) {
//    this.asStream().parallel().forEach(action)
    this.forEach(action)
}

inline fun <T, K : Comparable<K>> MutableList<T>.binaryInsertBy(
    item: T,
    crossinline selector: (T) -> K?
) {
    val index = binarySearchBy(selector(item), selector = selector)
    if (index < 0) {
        add(
            index = -index - 1,
            element = item
        )
    } else {
        add(
            index = index,
            element = item
        )
    }
}
