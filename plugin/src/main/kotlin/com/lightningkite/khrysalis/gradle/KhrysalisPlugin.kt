package com.lightningkite.khrysalis.gradle

import com.lightningkite.khrysalis.KhrysalisSettings
import com.lightningkite.khrysalis.android.layout.createAndroidLayoutClasses
import com.lightningkite.khrysalis.flow.createFlowDocumentation
import com.lightningkite.khrysalis.flow.createPrototypeViewGenerators
import com.lightningkite.khrysalis.ios.layout.*
import com.lightningkite.khrysalis.ios.*
import com.lightningkite.khrysalis.ios.swift.*
import com.lightningkite.khrysalis.utils.*
import com.lightningkite.khrysalis.web.TypescriptSubPlugin
import com.lightningkite.khrysalis.web.layout.HtmlTranslator
import com.lightningkite.khrysalis.web.layout.convertLayoutsToHtml
import com.lightningkite.khrysalis.web.setUpWebProject
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.GradleBuild
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocation
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler
import org.jetbrains.kotlin.config.Services
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformAndroidPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.incremental.classpathAsList
import org.jetbrains.kotlin.incremental.destinationAsFile
import java.io.File

open class KhrysalisPluginExtension {
    open var organizationName: String = "Organization"
    open var swiftConversion: SwiftAltListener.() -> Unit = {}
    open var swiftLayoutConversion: LayoutConverter = LayoutConverter.normal
    open var htmlTranslator: HtmlTranslator = HtmlTranslator()
    open var projectName: String? = null
    open var overrideIosPackageName: String? = null
    open var overrideWebPackageName: String? = null

    open var equivalentsDirectories: Set<File> = setOf()

    override fun toString(): String {
        return "(" +
                "\norganizationName: " + organizationName +
                "\nswiftConversion: " + swiftConversion +
                "\nswiftLayoutConversion: " + swiftLayoutConversion +
                "\nhtmlTranslator: " + htmlTranslator +
                "\nprojectName: " + projectName +
                "\n)"
    }
}

class KhrysalisPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val project = target
        val ext = project.extensions.create<KhrysalisPluginExtension>("khrysalis", KhrysalisPluginExtension::class.java)
        fun extension() = ext
        fun projectName() = extension().projectName ?: project.name.takeUnless { it == "app" || it == "android" }
        ?: project.rootProject.name
        KhrysalisSettings.verbose = true
        fun androidBase() = project.projectDir
        fun webBase() = project.projectDir.resolve("../web")
        fun iosBase() = project.projectDir.resolve("../ios")
        fun iosFolder() = iosBase().resolve(projectName())
        fun packageName() =
            project.extensions.findByName("android")?.groovyObject?.getPropertyAsObject("defaultConfig")?.getProperty("applicationId") as? String ?: "unknown.packagename"
        fun androidSdkDirectory() =
            project.extensions.findByName("android")?.groovyObject?.getProperty("sdkDirectory") as? File
        fun sdkLevel() =
            (project.extensions.findByName("android")?.groovyObject?.getProperty("buildToolsVersion") as? String)?.substringBefore('.')
                ?: (project.extensions.findByName("android")?.groovyObject?.getProperty("compileSdkVersion") as? String)?.substringBefore('.')

        project.afterEvaluate {
            println("Determined your package to be ${packageName()}")
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
            task.finalizedBy("khrysalisIosPodInstall")
        }
        project.tasks.create("khrysalisIosPodInstall", Exec::class.java) { task ->
            task.group = "ios"
            task.commandLine = listOf("pod", "install")
            task.doFirst {
                task.workingDir = iosBase()
            }
        }

        project.tasks.create("khrysalisConvertKotlinToSwiftClean") { task ->
            task.group = "ios"
            task.doLast {

                project.convertKotlinToSwiftWithDependencies(
                    androidFolder = androidBase(),
                    iosFolder = iosFolder(),
                    clean = true
                ) {
                    imports = listOf("Khrysalis", "RxSwift", "RxRelay")
                    extension().swiftConversion.invoke(this)
                }

            }
        }
        project.tasks.create("khrysalisConvertKotlinToSwift") { task ->
            task.group = "ios"
            project.afterEvaluate {
                if (!iosFolder().exists()) {
                    task.dependsOn("khrysalisSetupIosProject")
                }
            }
            task.doLast {

                project.convertKotlinToSwiftWithDependencies(
                    androidFolder = androidBase(),
                    iosFolder = iosFolder(),
                    clean = false
                ) {
                    imports = listOf("Khrysalis", "RxSwift", "RxRelay")
                    extension().swiftConversion.invoke(this)
                }

            }
        }
        project.tasks.create("khrysalisConvertLayoutsToSwift") { task ->
            task.group = "ios"
            task.doLast {

                convertLayoutsToSwift(
                    androidFolder = androidBase(),
                    iosFolder = iosFolder(),
                    converter = extension().swiftLayoutConversion
                )

            }
        }
        project.tasks.create("khrysalisConvertResourcesToIos") { task ->
            task.group = "ios"
            task.doLast {

                convertResourcesToIos(
                    androidFolder = androidBase(),
                    iosFolder = iosFolder()
                )

            }
        }
        project.tasks.create("khrysalisIos") { task ->
            task.group = "ios"
            task.dependsOn("khrysalisConvertKotlinToSwift")
            task.dependsOn("khrysalisConvertLayoutsToSwift")
            task.dependsOn("khrysalisConvertResourcesToIos")
            task.finalizedBy("khrysalisIosUpdateFiles")
        }
        project.tasks.create("khrysalisIosUpdateFiles", Exec::class.java) { task ->
            task.group = "ios"
            task.commandLine = listOf("python3", "updateFiles.py")
            task.doFirst {
                task.workingDir = iosBase()
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
        }
        project.tasks.create("khrysalisConvertLayoutsToHtmlSass") { task ->
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
                    converter = extension().htmlTranslator
                )
            }
        }
        project.tasks.create("khrysalisConvertKotlinToTypescript") { task ->
            task.group = "web"
            task.doFirst {
                val result = K2JVMCompiler().exec(
                    messageCollector = object : MessageCollector {
                        override fun clear() {

                        }

                        override fun hasErrors(): Boolean {
                            return false
                        }

                        override fun report(
                            severity: CompilerMessageSeverity,
                            message: String,
                            location: CompilerMessageLocation?
                        ) {
                            if (message.isNotBlank() && severity <= CompilerMessageSeverity.STRONG_WARNING) {
                                println(message + ":")
                                location?.toString()?.let { println(it) }
                            }
                        }

                    },
                    services = Services.EMPTY,
                    arguments = K2JVMCompilerArguments().apply {
                        val originalTask = project.tasks.getByName("compileDebugKotlin") as KotlinCompile
                        this.freeArgs = originalTask.source.toList().filter { it.extension in setOf("kt", "java") }.map { it.absolutePath }
                        println("freeArgs: ${this.freeArgs.joinToString("\n","\n")}")
                        val androidJar = androidSdkDirectory()!!.resolve("platforms/android-${sdkLevel()}/android.jar")
                        this.classpathAsList =
                            listOf(androidJar) + project.configurations.getByName("debugCompileClasspath").files.mapNotNull {
                                when(it.extension){
                                    "aar" -> project.zipTree(it)
                                        .matching {
                                            it.include("classes.jar")
                                        }
                                        .asSequence()
                                        .firstOrNull()
                                    else -> it
                                }
                            }
                        this.pluginClasspaths = project.buildDir.resolve("khrysalis-kcp/typescript.jar")
                            .also { it.parentFile.mkdirs() }
                            .also {
                                copyFolderOutFromRes("compiler-plugins", it.parentFile)
                            }
                            .let { arrayOf(it.path) }
                        this.pluginOptions =
                            arrayOf(
                                "plugin:com.lightningkite.khrysalis.typescript:outputDirectory=\"${webBase().resolve("src").path}\"",
                                "plugin:com.lightningkite.khrysalis.typescript:equivalents=\"${(ext.equivalentsDirectories + File(
                                    project.projectDir.resolve("src/main/equivalents").path
                                )).joinToString(File.pathSeparator)}\""
                            )
                        this.destinationAsFile = project.buildDir.resolve("testBuild").also { it.mkdirs() }
                    }
                )
                if (result.code != 0) {
                    throw IllegalStateException("Got a code ${result.code} back from the compiler! ${result.name}")
                }
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


        //Android support

        project.tasks.create("khrysalisAndroid") { task ->
            task.group = "android"
            task.dependsOn("khrysalisCreateAndroidLayoutClasses")
            task.mustRunAfter("generateReleaseResources", "generateDebugResources")
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
    }
}
