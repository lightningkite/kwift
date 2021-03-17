package com.lightningkite.khrysalis.typescript.replacements

import com.lightningkite.khrysalis.replacements.Import
import com.lightningkite.khrysalis.typescript.safeJsIdentifier
import com.lightningkite.khrysalis.util.fqNamesToCheck
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor
import java.io.File

data class TypescriptImport(
    val module: FileInfo,
    val identifier: String,
    val asName: String? = null
): Import {
    companion object {
        const val WHOLE = "WHOLE"

        fun render(moduleName: String, root: File, file: File, imports: Collection<TypescriptImport>, writer: Appendable){
            val grouped = imports
                .groupBy { it.module }
                .entries
                .sortedBy { (it.key.moduleName ?: "AAA") + " -> " + it.key.path }
                .map { it.value }
            
            for(group in grouped) {
                val rep = group[0].module
                if (group.size == 1 && group.first().identifier == WHOLE) {
                    writer.append("import ")
                    writer.append(group.first().identifier)
                    writer.append(" from '")
                } else if (group.size == 1 && group.first().identifier == "") {
                    writer.append("import {} from '")
                } else {
                    writer.append("import { ")
                    writer.append(group.sortedBy { it.asName ?: it.identifier }.joinToString(", ") {
                        it.asName?.let { name ->
                            it.identifier + " as " + name
                        } ?: it.identifier
                    })
                    writer.append(" } from '")
                }
                if(rep.moduleName != null && rep.moduleName != moduleName) {
                    // Render for node_modules resolution
                    writer.append(rep.moduleName)
                    writer.append('/')
                    writer.append(rep.path)
                    writer.appendLine("'")
                } else {
                    // Render for relative resolution
                    val importTarget = root.resolve(rep.path)
                    writer.append(importTarget.absoluteFile.relativeTo(file.absoluteFile).path)
                    writer.appendLine("'")
                }
            }
        }
    }

    data class FileInfo(val moduleName: String?, val path: String) {
        override fun toString(): String {
            return if(moduleName == null) path
            else "$moduleName/$path"
        }
    }

    class Manifest(
        val available: Map<String, FileInfo>
    ) {
        fun get(descriptor: DeclarationDescriptor, asName: String?): TypescriptImport? {
            val target = descriptor.fqNamesToCheck.mapNotNull { available[it] }.firstOrNull() ?: return null
            return TypescriptImport(target, asName ?: descriptor.name.asString().safeJsIdentifier())
        }
    }

    override fun toString(): String {
        return "<import $module $identifier $asName>"
    }
}