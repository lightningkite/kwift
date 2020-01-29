package com.lightningkite.khrysalis

import com.lightningkite.khrysalis.layout.convertLayoutsToSwift
import com.lightningkite.khrysalis.layout.createAndroidLayoutClasses
import com.lightningkite.khrysalis.swift.convertKotlinToSwift
import com.lightningkite.khrysalis.swift.convertKotlinToSwiftByFolder
import java.io.File


const val INTERFACE_SCAN_VERSION: Int = 2
const val VERSION: Int = 12

fun main(vararg args: String) {
    println("Checking ${File("./testData").walkTopDown()
        .filter { it.extension == "kt" }
        .filter { it.name.contains(".shared") }.toList()}")
    convertKotlinToSwiftByFolder(
        interfacesOut = File("./testDataOutput/interfaces.json"),
        baseKotlin = File("./testData"),
        baseSwift = File("./testDataOutput"),
        clean = true
    )
//    convertKotlinToSwift(
//        androidFolder = File("/Users/josephivie/StudioProjects/khrysalis-template/android/app"),
//        iosFolder = File("/Users/josephivie/StudioProjects/khrysalis-template/ios/Khrysalis Template"),
//        clean = true
//    )
//    createAndroidLayoutClasses(
//        androidFolder = File("/Users/josephivie/StudioProjects/khrysalis-template/android/app"),
//        applicationPackage = "com.lightningkite.khrysalistest"
//    )
//    convertLayoutsToSwift(
//        androidFolder = File("/Users/josephivie/StudioProjects/khrysalis-template/android/app"),
//        iosFolder = File("/Users/josephivie/StudioProjects/khrysalis-template/ios/Khrysalis Template")
//    )
//    convertResourcesToIos(
//        androidFolder = File("/Users/josephivie/StudioProjects/khrysalis-template/android/app"),
//        iosFolder = File("/Users/josephivie/StudioProjects/khrysalis-template/ios/Khrysalis Template")
//    )
}