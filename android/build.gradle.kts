import com.lightningkite.khrysalis.gradle.KhrysalisPluginExtension

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    id("digital.wup.android-maven-publish") version "3.6.2"
}

buildscript {
    repositories { mavenLocal() }
    dependencies { classpath("com.lightningkite.khrysalis:plugin:0.1.0") }
}
apply(plugin = "com.lightningkite.khrysalis")

configure<KhrysalisPluginExtension> {
    projectName = "Khrysalis"
    organizationName = "Lightning Kite"
}

group = "com.lightningkite.khrysalis"
version = "0.1.1"

repositories {
    maven("https://jitpack.io")
    google()
}

android {
    //    buildToolsVersion = "28.0.3"
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(19)
        targetSdkVersion(29)
        versionCode = 5
        versionName = "1.0.5"
    }
    buildTypes {
        //        release {
//            minifyEnabled = false
//            proguardFiles = getDefaultProguardFile("proguard-android.txt"), 'proguard-rules.pro'
//        }
    }
    packagingOptions {
        pickFirst("META-INF/android_release.kotlin_module")
        pickFirst("META-INF/android_debug.kotlin_module")
    }
}

val kotlin_version = "1.3.72"

dependencies {
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${org.jetbrains.kotlin.config.KotlinCompilerVersion.VERSION}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${org.jetbrains.kotlin.config.KotlinCompilerVersion.VERSION}")
    implementation("com.wdullaer:materialdatetimepicker:4.2.3")
    api("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    api("androidx.appcompat:appcompat:1.1.0")
    api("com.google.android.material:material:1.1.0")
    api("androidx.preference:preference-ktx:1.1.1")
    api("androidx.constraintlayout:constraintlayout:1.1.3")
    api("androidx.recyclerview:recyclerview:1.1.0")
    api("com.fasterxml.jackson.core:jackson-core:2.9.9")
    api("com.fasterxml.jackson.core:jackson-annotations:2.9.7")
    api("com.fasterxml.jackson.core:jackson-databind:2.9.9")
    api("com.squareup.okhttp3:okhttp:3.12.0")
    api("de.hdodenhof:circleimageview:2.2.0")
    api("br.com.simplepass:loading-button-android:1.14.0")
    implementation("com.github.bumptech.glide:glide:4.11.0")
    api("com.romandanylyk:pageindicatorview:1.0.3")
    api("com.theartofdev.edmodo:android-image-cropper:2.7.0")
    api("io.reactivex.rxjava2:rxkotlin:2.4.0")
    api("io.reactivex.rxjava2:rxandroid:2.1.1")
    api("com.google.android.exoplayer:exoplayer:2.11.8")
}

tasks.create("sourceJar", Jar::class) {
    classifier = "sources"
    from(android.sourceSets["main"].java.srcDirs)
    from(project.projectDir.resolve("src/include"))
}

publishing {
    publications {
        val mavenAar by creating(MavenPublication::class) {
            from(components["android"])
            artifact(tasks.getByName("sourceJar"))
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
        }
    }
}
