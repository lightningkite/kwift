package com.lightningkite.khrysalis.typescript

import com.lightningkite.khrysalis.typescript.replacements.TemplatePart
import com.lightningkite.khrysalis.util.SmartTabWriter
import com.lightningkite.khrysalis.util.fqNamesToCheck
import com.lightningkite.khrysalis.util.simpleFqName
import com.lightningkite.khrysalis.util.simplerFqName
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.load.java.descriptors.JavaPropertyDescriptor
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameOrNull
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameSafe
import org.jetbrains.kotlin.resolve.descriptorUtil.getImportableDescriptor
import org.jetbrains.kotlin.synthetic.SyntheticJavaPropertyDescriptor
import java.io.BufferedWriter
import java.io.File

class TypescriptFileEmitter(val translator: TypescriptTranslator, val file: KtFile) : Appendable {
    val stringBuilder = StringBuilder()
    val out = SmartTabWriter(stringBuilder)
    private val imports = HashMap<String, TemplatePart.Import>()
    val importedFqs = HashSet<String>()
    val missedImports = HashSet<String>()
    var fileEndingActions = ArrayList<() -> Unit>()

    companion object {
        val overwriteWarning = "// Generated by Khrysalis TypeScript converter - this file will be overwritten."
    }

    fun write(writer: BufferedWriter, file: KtFile) {
        writer.appendln(overwriteWarning)
        val relPath = file.virtualFilePath.removePrefix(translator.commonPath)
        writer.appendln("// File: $relPath")
        writer.appendln("// Package: ${file.packageFqName.asString()}")
//        missedImports.forEach {
//            writer.appendln("// Couldn't find import for ${it}")
//        }
        renderImports(translator.projectName, relPath, imports.values, writer)
        writer.appendln()

        out.flush()
        writer.appendln(stringBuilder)

        while (fileEndingActions.isNotEmpty()) {
            stringBuilder.clear()
            stringBuilder.appendln()
            val copy = fileEndingActions
            fileEndingActions = ArrayList()
            copy.forEach { it() }
            out.flush()
            writer.appendln(stringBuilder)
        }
    }

    //Map of FQ name to import info
    fun addImport(path: String, identifier: String, asName: String? = null) {
        val fqName = "$path->$identifier"
        if (imports.containsKey(fqName)) return
        imports[fqName] = TemplatePart.Import(path, identifier, asName)
    }

    private fun addImportFromFq(fqName: String, name: String): Boolean {
        val newImport = translator.declarations.importLine(
            currentRelativeFile = File(file.virtualFilePath.removePrefix(translator.commonPath).removeSuffix(".kt").plus(".ts")),
            fqName = fqName,
            name = name
        )
        if(newImport != null) {
            addImport(newImport)
            return true
        }
        return false
    }

    fun addImport(decl: DeclarationDescriptor, overrideName: String? = null) {
        val useDecl = when (decl) {
            is ConstructorDescriptor -> decl.containingDeclaration
            is ClassDescriptor -> if(decl.isCompanionObject) decl.containingDeclaration else decl
            else -> decl
        }
        val name = overrideName ?: useDecl.name.asString().safeJsIdentifier()
        val n = "${useDecl.simpleFqName} TS $name"
        if (importedFqs.contains(n))
            return
        importedFqs.add(n)
        useDecl.fqNamesToCheck.firstOrNull {
            addImportFromFq(it, name)
        } ?: missedImports.add(n)
    }

    fun addImport(part: TemplatePart.Import) {
        addImport(
            path = part.path,
            identifier = part.identifier,
            asName = part.asName
        )
    }

    fun addImports(parts: Iterable<TemplatePart.Import>) {
        for (p in parts) addImport(p)
    }

    override fun append(p0: CharSequence): Appendable {
        out.append(p0)
        return this
    }

    override fun append(p0: CharSequence, p1: Int, p2: Int): Appendable {
        out.append(p0, p1, p2)
        return this
    }

    override fun append(p0: Char): Appendable {
        out.append(p0)
        return this
    }
}