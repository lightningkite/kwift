package com.lightningkite.kwift.flow

import org.junit.Assert.*
import org.junit.Test

class ViewNodeTest {
    @Test
    fun dependenciesWorkRight(){
        val nodeA = ViewNode("nodeA")
        val nodeB = ViewNode("nodeB")
        nodeA.requires
    }
}