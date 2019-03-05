package com.lightningkite.kwift

import java.io.File


fun main(vararg args: String) {
    println("Groan...")
    kwiftTask(
        listOf(
            File("/Users/josephivie/StudioProjects/lifting-generations-android/app/src/main/java/org/liftinggenerations/shared") to
                    File("/Users/josephivie/StudioProjects/lifting-generations-android/app/../../lifting-generations-ios/Lifting Generations/shared"),

            File("/Users/josephivie/StudioProjects/lifting-generations-android/app/src/main/java/com/lightningkite/kwift/shared") to
                    File("/Users/josephivie/StudioProjects/lifting-generations-android/app/../../lifting-generations-ios/kwift/shared"),

            File("/Users/josephivie/IdeaProjects/kwift/testData") to
                    File("/Users/josephivie/IdeaProjects/kwift/testDataOutput")

        )
//        listOf(
//            File("/Users/josephivie/IdeaProjects/kwift/testData")
//                    to
//                    File("/Users/josephivie/IdeaProjects/kwift/testDataOutput")
//        )
    )
    println("SUCCESS!")
}
