package com.lightningkite.khrysalis.typescript

import com.lightningkite.khrysalis.util.SmartTabWriter
import org.jetbrains.kotlin.analyzer.AnalysisResult
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.com.intellij.openapi.project.Project
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey
import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.resolve.BindingTrace
import org.jetbrains.kotlin.resolve.jvm.extensions.AnalysisHandlerExtension
import java.io.File

class KotlinTypescriptCLP : CommandLineProcessor {
    companion object {
        const val KEY_ACTUALS_DIRECTORY_NAME = "actualsDirectories"
        val KEY_ACTUALS_DIRECTORY = CompilerConfigurationKey.create<List<File>>(KEY_ACTUALS_DIRECTORY_NAME)
        const val KEY_OUTPUT_DIRECTORY_NAME = "outputDirectory"
        val KEY_OUTPUT_DIRECTORY = CompilerConfigurationKey.create<File>(KEY_OUTPUT_DIRECTORY_NAME)
        const val PLUGIN_ID = "com.lightningkite.khrysalis.typescript"
    }

    override val pluginId: String get() = PLUGIN_ID
    override val pluginOptions: Collection<AbstractCliOption> = listOf(
        CliOption(KEY_OUTPUT_DIRECTORY_NAME, "A directory", "Where to store the output files.", required = true),
        CliOption(
            KEY_ACTUALS_DIRECTORY_NAME,
            "A directory",
            "Where to look for translational information.",
            required = false
        )
    )

    override fun processOption(option: AbstractCliOption, value: String, configuration: CompilerConfiguration) =
        when (option.optionName) {
            KEY_ACTUALS_DIRECTORY_NAME -> configuration.put(
                KEY_ACTUALS_DIRECTORY,
                value.split(File.pathSeparatorChar).map { File(it) })
            KEY_OUTPUT_DIRECTORY_NAME -> configuration.put(KEY_OUTPUT_DIRECTORY, value.let { File(it) })
            else -> {
            }
        }
}

class KotlinTypescriptCR : ComponentRegistrar {
    override fun registerProjectComponents(project: MockProject, configuration: CompilerConfiguration) {
        AnalysisHandlerExtension.registerExtension(
            project,
            KotlinTypescriptExtension(
                configuration[KotlinTypescriptCLP.KEY_ACTUALS_DIRECTORY] ?: listOf(),
                configuration[KotlinTypescriptCLP.KEY_OUTPUT_DIRECTORY]!!
            )
        )
    }
}

class KotlinTypescriptExtension(
    val actuals: List<File>,
    val output: File
) : AnalysisHandlerExtension {
    override fun analysisCompleted(
        project: Project,
        module: ModuleDescriptor,
        bindingTrace: BindingTrace,
        files: Collection<KtFile>
    ): AnalysisResult? {
        println("Completed analysis.")
        val ctx = bindingTrace.bindingContext
        val translator = TypescriptTranslator(ctx)
        val commonPath = files.asSequence().map { it.virtualFilePath }.takeUnless { it.none() }?.reduce { acc, s -> acc.commonPrefixWith(s) } ?: ""
        for (file in files) {
            val outputFile = output.resolve(file.virtualFilePath.removePrefix(commonPath)).resolve(file.name.removeSuffix(".kt").plus(".ts"))
            println("Translating $file to ... $outputFile")
            outputFile.parentFile.mkdirs()
            outputFile.bufferedWriter().use {
                translator.translate(file, SmartTabWriter(it))
            }
        }
        return AnalysisResult.Companion.success(ctx, module, false)
    }
}

/* IDEA TIME!

ACTUALS SUPPORT

import fully.qualified.name.Receiver
import fully.qualified.name.Something

type Something<TypeArg, TypeArg2> = TypescriptThing<${TypeArg}>
call Receiver<TypeArg>.functionName(arg: Arg, arg2: Arg...) = someTsFunction(${this}, ${arg}, ${arg})
get Receiver<TypeArg>.propertyName = ${this}.otherNameInTs
set Receiver<TypeArg>.propertyName = ${this}.otherNameInTs = ${value}
call functionName(arg: Arg, arg2: Arg...) = someTsFunction(${this}, ${arg}, ${arg})
get propertyName = ${this}.otherNameInTs
set propertyName = ${this}.otherNameInTs = ${value}

call ... = something
    that
    spans
    multiple
    lines

add .Companion for static
#import("asdfasdf.asdfasd.asdf") allows for macro stuff
${temp} to create a temporary identifier

Comment and whitespace preservation: Try to translate at the lowest level possible to preserve

call kotlin.let -> asdfasdfasdfafd
call kotlin.let where ARG1 = x ->
 */
