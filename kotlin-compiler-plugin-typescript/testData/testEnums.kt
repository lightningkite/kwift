@file:SharedCode
package com.test.classes

import com.lightningkite.butterfly.*

enum class Suits {
    SPADES, CLUBS, DIAMONDS, HEARTS;
}

enum class AdvancedSuits(val black: Boolean) {
    SPADES(true){
        override fun print(cardNum: Int){
            println("♠$cardNum")
        }
    },
    CLUBS(true){},
    DIAMONDS(false){},
    HEARTS(false){};

    open fun print(cardNum: Int) {
        println("$this$cardNum")
    }
}

enum class StatusEnum(val comparableValue: Int, val darkColorResource: Int = 0, val colorResource: Int = 0, val textResource: Int = 0) : Codable {
    Safe(comparableValue = 3, darkColorResource = 0, colorResource = 0, textResource = 0),
    Unsafe(comparableValue = 1, colorResource = 0, textResource = 0),
    Cleared(comparableValue = 4, darkColorResource = 0, textResource = 0),
    Unknown(comparableValue = 2, darkColorResource = 0, colorResource = 0);
}

fun main(){
    val simpleSuit = Suits.CLUBS
    val advancedSuit = AdvancedSuits.DIAMONDS
    for(simp in Suits.values()){
        println(simp.name)
        println(Suits.valueOf(simp.name).name)
    }
    for(simp in AdvancedSuits.values()){
        println(simp.name)
        simp.print(3)
        println(AdvancedSuits.valueOf(simp.name).name)
    }
}