package com.lightningkite.khrysalis.swift

import com.lightningkite.khrysalis.generic.FileEmitter
import com.lightningkite.khrysalis.replacements.Import
import com.lightningkite.khrysalis.swift.replacements.SwiftImport
import com.lightningkite.khrysalis.util.SmartTabWriter
import com.lightningkite.khrysalis.util.simpleFqName
import com.lightningkite.khrysalis.util.simplerFqName
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.psi.KtFile
import java.io.BufferedWriter
import java.io.File

class SwiftFileEmitter(val projectName: String) : FileEmitter() {
    val imports = hashSetOf(
        SwiftImport("Foundation")
    )
    override fun addImport(import: Import) {
        imports.add(import as SwiftImport)
    }

    override fun renderImports(to: Appendable) {
        for(imp in imports){
            if(imp.module != projectName) {
                to.appendLine("import ${imp.module}")
            }
        }
    }

    override fun sub(): FileEmitter = SwiftFileEmitter(projectName)
}