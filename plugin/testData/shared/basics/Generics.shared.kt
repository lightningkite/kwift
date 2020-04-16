package com.test

import com.lightningkite.khrysalis.*

data class NameTag<T: Hashable>(val item: T, val name: String)

fun <T: Hashable> NameTag<T>.printSelf(){
    println("Hello!  I am ${this.name}.")
}

fun NameTag<Int>.printInt(){
    println("Hello!  I am ${this.name}, I stand for ${this.item}.")
}

fun <T: Hashable> printAlt(nameTag: NameTag<T>) {
    println("Hello!  I am ${nameTag.name}")
}

fun main(){
    val tag = NameTag(2.toInt(), "Two")
    tag.printSelf()
    tag.printInt()
    printAlt(tag)
}