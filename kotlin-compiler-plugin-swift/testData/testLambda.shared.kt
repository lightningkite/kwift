package com.test

import android.view.View

private class LambdaTestClass {
    var item: Int = 0
    private fun test(action: ()->Unit){
        action()
    }
    fun testRec(action: Int.()->Unit){
        2.action()
    }
}

private fun lambdaMain(){
    val theAnswer = LambdaTestClass().apply { item = 42 }
    val myLambda: (Int)->String = {
        "Number: $it"
    }
    theAnswer.let {
        println(it)
    }
    theAnswer.let { _ ->
        println("I don't care.")
    }
    32.let {
    }
    32.let {
        println(it)
    }
    32.let label@{
        println(it)
        println(it)
        return@label
    }

    val lambda: ()->Unit = label@{ ->
        println("Hi")
        return@label
    }
    val lambda2: (Int, String)->Unit = label@{ i, s ->
        println(s + i.toString())
        return@label
    }
    val lambda3: ()->Unit = label@{ ->
        if(theAnswer.item < 22){
            println("Hello!")
        } else {
            println("WRONG")
        }
    }

    val takesNullable = label@ { a: String? ->
        println(a ?: "NO")
    }

    val view: View = View()
    view.setOnClickListener {
        println("Hello!  I am $it")
    }
    val altCallback = { it: View ->
        println("Hello!  I am also $it")
    }
    view.setOnClickListener(altCallback)
}