package com.lightningkite.khrysalis.util

import com.lightningkite.khrysalis.generic.FileEmitter
import org.junit.Test
import java.io.File

class SmartTabWriterTest {
    @Test
    fun singleBraceTest() {
        println(buildString {
            with(SmartTabWriter(this)) {
                appendln('{')
                append('}')
            }
        })
    }

    @Test fun overwriteTest(){
        println(FileEmitter.canBeOverwritten(File("/home/joseph/IdeaProjects/tresit-khrysalis/ios/DIRS/src/vg/PinAuth.swift")))
    }
}