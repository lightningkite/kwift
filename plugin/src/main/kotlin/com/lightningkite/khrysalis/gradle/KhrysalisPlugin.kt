package com.lightningkite.khrysalis.gradle

import com.lightningkite.khrysalis.KhrysalisSettings
import com.lightningkite.khrysalis.android.layout.createAndroidLayoutClasses
import com.lightningkite.khrysalis.flow.createFlowDocumentation
import com.lightningkite.khrysalis.flow.createPrototypeViewGenerators
import com.lightningkite.khrysalis.ios.layout.*
import com.lightningkite.khrysalis.ios.*
import com.lightningkite.khrysalis.ios.layout2.AppleResourceLayoutConversion
import com.lightningkite.khrysalis.ios.layout2.convertLayoutsToSwift2
import com.lightningkite.khrysalis.ios.swift.*
import com.lightningkite.khrysalis.utils.*
import com.lightningkite.khrysalis.web.convertToTypescript
import com.lightningkite.khrysalis.web.layout.HtmlTranslator
import com.lightningkite.khrysalis.web.layout.convertLayoutsToHtml
import com.lightningkite.khrysalis.web.setUpWebProject
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Exec
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.lightningkite.khrysalis.web.layout.convertLayoutsToHtmlXmlClasses
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Task
import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.compile.JavaCompile
import org.jetbrains.kotlin.ir.backend.js.compile
import java.io.File
import java.util.*

open class KhrysalisPluginExtension {
    open var organizationName: String = "Organization"
    open var swiftLayoutConversion: LayoutConverter = LayoutConverter.normal
    open var htmlTranslator: HtmlTranslator = HtmlTranslator()
    open var projectName: String? = null
    open var overrideIosPackageName: String? = null
    open var overrideWebPackageName: String? = null
    open var overrideIosFolder: String? = null
    open var overrideWebFolder: String? = null

    override fun toString(): String {
        return "(" +
                "\norganizationName: " + organizationName +
                "\nswiftLayoutConversion: " + swiftLayoutConversion +
                "\nhtmlTranslator: " + htmlTranslator +
                "\nprojectName: " + projectName +
                "\n)"
    }
}

class KhrysalisPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val isMac = Os.isFamily(Os.FAMILY_MAC)
        val project = target
        val ext = project.extensions.create<KhrysalisPluginExtension>("khrysalis", KhrysalisPluginExtension::class.java)

        fun extension() = ext
        fun projectName() = extension().projectName ?: project.name.takeUnless { it == "app" || it == "android" }
        ?: project.rootProject.name
        KhrysalisSettings.verbose = true
        fun androidBase() = project.projectDir
        fun webBase() = project.projectDir.resolve(ext.overrideWebFolder ?: "../web")
        fun iosBase() = project.projectDir.resolve(ext.overrideIosFolder ?: "../ios")
        fun iosFolder() = iosBase().resolve(projectName())
        fun packageName() =
            project.extensions.findByName("android")?.groovyObject?.getPropertyAsObject("defaultConfig")
                ?.getProperty("applicationId") as? String ?: "unknown.packagename"

        fun androidSdkDirectory() =
            project.extensions.findByName("android")?.groovyObject?.getProperty("sdkDirectory") as? File

        fun sdkLevel() =
            (project.extensions.findByName("android")?.groovyObject?.getProperty("buildToolsVersion") as? String)?.substringBefore(
                '.'
            )
                ?: (project.extensions.findByName("android")?.groovyObject?.getProperty("compileSdkVersion") as? String)?.substringBefore(
                    '.'
                )

        project.afterEvaluate {
            println("Determined your package to be ${packageName()}")
        }


        //Android support

        project.tasks.create("khrysalisAndroid") { task ->
            task.group = "android"
            task.dependsOn("khrysalisCreateAndroidLayoutClasses")
        }
        project.tasks.create("khrysalisCreateAndroidLayoutClasses") { task ->
            task.group = "android"
            task.doLast {
                createAndroidLayoutClasses(
                    androidFolder = androidBase(),
                    applicationPackage = packageName()
                )
            }
        }
        project.afterEvaluate {
            project.tasks.findByName("generateReleaseResources")?.dependsOn("khrysalisAndroid") ?: run {
                println("Could not configure resource dependency.  You'll need to run 'khrysalisAndroid' manually.")
            }
            project.tasks.findByName("generateDebugResources")?.dependsOn("khrysalisAndroid") ?: run {
                println("Could not configure resource dependency.  You'll need to run 'khrysalisAndroid' manually.")
            }
        }

        //IOS

        project.tasks.create("khrysalisSetupIosProject") { task ->
            task.group = "ios"
            task.doLast {
                setUpIosProject(
                    target = iosBase(),
                    organization = extension().organizationName,
                    organizationId = extension().overrideIosPackageName ?: project.group?.toString()
                    ?: "unknown.packagename",
                    projectName = projectName()
                )
            }
            if (isMac) {
                task.finalizedBy("khrysalisIosPodInstall")
            }
        }
        if (isMac) {
            project.tasks.create("khrysalisIosPodInstall", Exec::class.java) { task ->
                task.group = "ios"
                task.commandLine = listOf("pod", "install")
                task.doFirst {
                    task.workingDir = iosBase()
                }
            }
        }
        project.tasks.create("khrysalisConvertKotlinToSwift") { task ->
            task.group = "ios"
            var compileTask: KotlinCompile? = null
            project.afterEvaluate {
                compileTask = project.tasks
                    .asSequence()
                    .filter { it.name.startsWith("compile") && it.name.endsWith("Kotlin") }
                    .mapNotNull { it as? KotlinCompile }
                    .minBy { it.name.length }
                compileTask?.let {
                    println("Conversion depends on ${it.name}")
                    task.dependsOn(it)
                } ?: run {
                    println("WARNING: Could find no compile*Kotlin tasks - tasks available: ${project.tasks.joinToString { it.name }}")
                }
            }
            task.doFirst {
                val originalTask = compileTask
                    ?: project.tasks
                        .asSequence()
                        .filter { it.name.startsWith("compile") && it.name.endsWith("Kotlin") }
                        .mapNotNull { it as? KotlinCompile }
                        .minBy { it.name.length }
                    ?: throw IllegalStateException("Could not find compile*Kotlin tasks - what's up with your project?")
                val libraries = originalTask.classpath.asSequence()
                val files = originalTask.source.toList().asSequence()
                println("All files: ${files.joinToString("\n")}")
                println("All libraries: ${libraries.joinToString("\n")}")
                convertToSwift(
                    projectName = projectName(),
                    libraries = libraries,
                    files = files,
                    pluginCache = project.buildDir.resolve("khrysalis-kcp"),
                    buildCache = project.buildDir.resolve("testBuild"),
                    dependencies = run {
                        val localProperties = Properties().apply {
                            val f = project.rootProject.file("local.properties")
                            if (f.exists()) {
                                load(f.inputStream())
                            }
                        }
                        val pathRegex = Regex(":path => '([^']+)'")
                        val home = System.getProperty("user.home")
                        val localPodSpecRefs = iosBase()
                            .resolve("Podfile")
                            .takeIf { it.exists() }
                            ?.also { println("Found podfile: $it") }
                            ?.let {file ->
                                file
                                    .readText()
                                    .let { pathRegex.findAll(it) }
                                    .also { println("Found podfile paths: ${it.joinToString{ it.value }}") }
                                    .map { it.groupValues[1] }
                                    .map { it.replace("~", home) }
                                    .map { File(file.parentFile, it).parentFile }
                            } ?: sequenceOf()
                        val allLocations = (localProperties.getProperty("khrysalis.iospods")
                            ?: localProperties.getProperty("khrysalis.nonmacmanifest") ?: "")
                            .splitToSequence(File.pathSeparatorChar)
                            .filter { it.isNotBlank() }
                            .map { File(it) }
                            .filter { it.exists() }
                            .plus(sequenceOf(iosBase()))
                            .plus(sequenceOf(androidBase()))
                            .plus(localPodSpecRefs)
                        println("Checking for equivalents at: ${allLocations.joinToString("\n")}")
                        allLocations
                    },
                    output = iosFolder().resolve("src")
                )
            }
        }
        project.tasks.create("khrysalisConvertLayoutsToSwift") { task ->
            task.group = "ios"
            task.doLast {

                convertResourcesToIos(
                    androidFolder = androidBase(),
                    iosFolder = iosFolder()
                )
                convertLayoutsToSwift(
                    androidFolder = androidBase(),
                    iosFolder = iosFolder(),
                    converter = extension().swiftLayoutConversion
                )

            }
        }
        project.tasks.create("khrysalisConvertLayoutsToSwift2") { task ->
            task.group = "ios"
            task.doLast {
                val localProperties = Properties().apply {
                    val f = project.rootProject.file("local.properties")
                    if (f.exists()) {
                        load(f.inputStream())
                    }
                }
                val eq = (localProperties.getProperty("khrysalis.iospods")
                    ?: localProperties.getProperty("khrysalis.nonmacmanifest") ?: "")
                    .splitToSequence(File.pathSeparatorChar)
                    .filter { it.isNotBlank() }
                    .map { File(it) }
                    .filter { it.exists() }
                    .plus(sequenceOf(iosBase().resolve("Pods")))
                convertLayoutsToSwift2(
                    androidFolder = androidBase(),
                    iosFolder = iosFolder(),
                    equivalentsFolders = eq
                )
            }
        }
        project.tasks.create("khrysalisUpdateIosVersion") { task ->
            task.group = "ios"
            task.doLast {
                val versionName = project.extensions.findByName("android")?.groovyObject?.getPropertyAsObject("defaultConfig")
                    ?.getProperty("versionName") as? String ?: throw IllegalStateException("Could not find versionName")
                val versionCode = project.extensions.findByName("android")?.groovyObject?.getPropertyAsObject("defaultConfig")
                    ?.getProperty("versionCode") as? Int ?: throw IllegalStateException("Could not find versionCode")
                val projectFile = (iosBase().listFiles()?.toList()
                    ?.find { it.name.endsWith("xcodeproj", true) }
                    ?: throw IllegalStateException("Could not find projectFile at ${iosBase()}"))
                    .resolve("project.pbxproj")
                    .also {
                        if(!it.exists()) {
                            throw IllegalStateException("Could not find projectFile at ${it}")
                        }
                    }
                projectFile.readText()
                    .replace(Regex("CURRENT_PROJECT_VERSION = [0-9]+;"), "CURRENT_PROJECT_VERSION = $versionCode;")
                    .replace(Regex("MARKETING_VERSION = [0-9.]+;"), "MARKETING_VERSION = $versionName;")
                    .let { projectFile.writeText(it) }
            }
        }
        project.tasks.create("khrysalisIos") { task ->
            task.group = "ios"
            task.dependsOn("khrysalisConvertKotlinToSwift")
            task.dependsOn("khrysalisConvertLayoutsToSwift")
            task.dependsOn("khrysalisUpdateIosVersion")
            if (isMac) {
                task.finalizedBy("khrysalisIosUpdateFiles")
            }
        }
        if (isMac) {
            project.tasks.create("khrysalisIosUpdateFiles", Exec::class.java) { task ->
                task.group = "ios"
                task.commandLine = listOf("python3", "updateFiles.py")
                task.doFirst {
                    task.workingDir = iosBase()
                }
            }
        }


        //Web
        project.tasks.create("khrysalisSetupWebProject") { task ->
            task.group = "web"
            task.doLast {
                setUpWebProject(
                    target = webBase(),
                    organization = extension().organizationName,
                    organizationId = project.group?.toString() ?: "unknown.packagename",
                    projectName = projectName()
                )
            }
            task.finalizedBy("khrysalisWebNpmInstall")
        }
        project.tasks.create("khrysalisWebNpmInstall", Exec::class.java) { task ->
            task.group = "web"
            task.commandLine = listOf("npm", "install")
            task.doFirst {
                task.workingDir = webBase()
            }
        }
        project.tasks.create("khrysalisConvertLayoutsToHtmlSass") { task ->
            task.dependsOn("khrysalisCreateAndroidLayoutClasses")
            project.afterEvaluate {
                if (!iosFolder().exists()) {
                    task.dependsOn("khrysalisSetupWebProject")
                }
            }
            task.group = "web"
            task.doLast {
                convertLayoutsToHtml(
                    androidMainFolder = androidBase().resolve("src/main"),
                    webFolder = webBase(),
                    packageName = packageName(),
                    converter = extension().htmlTranslator
                )
                convertLayoutsToHtmlXmlClasses(
                    projectName = projectName(),
                    packageName = packageName(),
                    androidLayoutsSummaryFile = androidBase().resolve("build/layout/summary.json"),
                    baseTypescriptFolder = webBase().resolve("src"),
                    outputFolder = webBase().resolve("src/layout")
                )
            }
        }
        project.tasks.create("khrysalisUpdateWebVersion") { task ->
            task.group = "web"
            task.doLast {
                val versionName = project.extensions.findByName("android")?.groovyObject?.getPropertyAsObject("defaultConfig")
                    ?.getProperty("versionName") as? String ?: throw IllegalStateException("Could not find versionName")
                val versionCode = project.extensions.findByName("android")?.groovyObject?.getPropertyAsObject("defaultConfig")
                    ?.getProperty("versionCode") as? Int ?: throw IllegalStateException("Could not find versionCode")
                webBase().resolve("src/BuildConfig.ts").writeText("""
                    //! Declares com.tresitgroup.android.tresit.BuildConfig
                    export class BuildConfig {
                        static INSTANCE = BuildConfig
                        static VERSION_NAME: string = "$versionName"
                        static VERSION_CODE: number = $versionCode
                        static get DEBUG(): boolean {
                            return (window as any).isDebugMode ?? false
                        }
                    }
                """.trimIndent())
            }
        }
        project.tasks.create("khrysalisConvertKotlinToTypescript") { task ->
            task.dependsOn("khrysalisUpdateWebVersion")
            task.group = "web"
            var compileTask: KotlinCompile? = null
            project.afterEvaluate {
                compileTask = project.tasks
                    .asSequence()
                    .filter { it.name.startsWith("compile") && it.name.endsWith("Kotlin") }
                    .mapNotNull { it as? KotlinCompile }
                    .minBy { it.name.length }
                compileTask?.let {
                    task.dependsOn(it)
                }
            }
            task.doFirst {
                val originalTask = compileTask
                    ?: project.tasks
                        .asSequence()
                        .filter { it.name.startsWith("compile") && it.name.endsWith("Kotlin") }
                        .mapNotNull { it as? KotlinCompile }
                        .minBy { it.name.length }
                    ?: throw IllegalStateException("Could not find compile*Kotlin tasks - what's up with your project?")
                val libraries = originalTask.classpath.asSequence()
                val files = originalTask.source.toList().asSequence()
                println("All files: ${files.joinToString("\n")}")
                println("All libraries: ${libraries.joinToString("\n")}")
                convertToTypescript(
                    projectName = projectName(),
                    libraries = libraries,
                    files = files,
                    pluginCache = project.buildDir.resolve("khrysalis-kcp"),
                    buildCache = project.buildDir.resolve("testBuild"),
                    dependencies = sequenceOf(webBase()),
                    output = webBase().resolve("src")
                )
            }
        }
        project.tasks.create("khrysalisWeb") { task ->
            task.group = "web"
            task.dependsOn("khrysalisConvertLayoutsToHtmlSass")
            task.dependsOn("khrysalisConvertKotlinToTypescript")
        }


        //Prototyping

        project.tasks.create("khrysalisPrototype") { task ->
            task.group = "prototype"
            task.dependsOn("khrysalisCreateAndroidLayoutClasses")
            task.doLast {

                createPrototypeViewGenerators(
                    androidFolder = androidBase(),
                    applicationPackage = packageName()
                )

            }
        }
        project.tasks.create("khrysalisFlowDoc") { task ->
            task.group = "prototype"
            task.dependsOn("khrysalisCreateAndroidLayoutClasses")
            task.doLast {
                createFlowDocumentation(
                    androidFolder = androidBase()
                )
            }
        }

    }
}
