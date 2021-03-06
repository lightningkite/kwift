pluginManagement {
    repositories {
        maven("https://dl.bintray.com/kotlin/kotlin-eap")

        mavenCentral()

        maven("https://plugins.gradle.org/m2/")
    }
}
rootProject.name = "khrysalis"

include(":plugin")
include("kotlin-compiler-plugin-common")
include("kotlin-compiler-plugin-swift")
include("kotlin-compiler-plugin-typescript")
