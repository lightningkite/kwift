package com.test

import com.lightningkite.butterfly.*

private typealias TypesMyInt = Int

private class TypesThing()

private typealias MyThing = TypesThing
private typealias MyList<T> = List<T>
private typealias ListOfThings = MyList<MyThing>

private class TypesConstructorTest(
){
    val x = HashMap<String, Long>()

}

fun <T> myRun(action: ()->T) {
    return action()
}

private fun typesMain(){
    val x: Int = 0
    val y: TypesMyInt = 0
    val stuff: ListOfThings = listOf(MyThing(), TypesThing())
    val otherList: List<Int> = listOf(1, 2, 3)
    val nullabilityTest: Int? = null
    val nullabilityTest2: ListOfThings? = null
    println("Success")

    val unknownThing: Any? = "Hi"
    var unknownThingMut: Any? = unknownThing

    if(unknownThing is Int){
        println("Hello!")
    }
    println(unknownThing as? Int)
    if(unknownThingMut is String){
        println(unknownThingMut + "asdf")
    }

    val a = 23L
    val b = 23f
    val c = 23
    val d = 23.0

    val unit: Unit = Unit
    val unitArmy = otherList.map { Unit }

    val functionTypeA: ()->Unit = {}
    val functionTypeB: (Int)->Unit = { println(it) }
    val functionTypeC: (Int)->Int = { it + 1 }
    println(myRun<Int> { 82 })
}
