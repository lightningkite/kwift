package com.lightningkite.kwift.layout

import com.lightningkite.kwift.utils.XmlNode
import com.lightningkite.kwift.log
import com.lightningkite.kwift.utils.camelCase
import java.io.File

fun createAndroidLayoutClasses(androidFolder: File, applicationPackage: String) = createAndroidLayoutClasses(
    resourcesFolder = androidFolder.resolve("src/main/res"),
    applicationPackage = applicationPackage,
    outputFolder = androidFolder.resolve("src/main/java/${applicationPackage.replace('.', '/')}/layouts")
)

fun createAndroidLayoutClasses(
    resourcesFolder: File,
    applicationPackage: String,
    outputFolder: File
) {
    val styles = File(resourcesFolder, "values/styles.xml").readXMLStyles()
    val packageName = outputFolder.toString().substringAfter("src/main/").substringAfter('/').replace('/', '.')

    outputFolder.deleteRecursively()
    File(resourcesFolder, "layout").walkTopDown()
        .filter { it.extension == "xml" }
        .forEach { item ->
            log(item.toString())
            val output = item.translateLayoutXmlAndroid(styles, packageName, applicationPackage)
            File(outputFolder, item.nameWithoutExtension.camelCase().capitalize() + "Xml.kt").also {
                it.parentFile.mkdirs()
            }.writeText(output)
        }
}

private data class AndroidIdHook(
    val name: String,
    val type: String,
    val resourceId: String
)

private data class AndroidSubLayout(
    val name: String,
    val resourceId: String,
    val layoutXmlClass: String
)

private fun File.translateLayoutXmlAndroid(styles: Styles, packageName: String, applicationPackage: String): String {

    val node = XmlNode.read(this, styles)
    val fileName = this.nameWithoutExtension.camelCase().capitalize()
    val bindings = ArrayList<AndroidIdHook>()
    val sublayouts = ArrayList<AndroidSubLayout>()

    fun addBindings(node: XmlNode) {
        node.attributes["android:id"]?.let { raw ->
            val id = raw.removePrefix("@+id/").removePrefix("@id/")
            val camelCasedId = id.camelCase()
            if(node.name == "include"){
                val layout = node.attributes["layout"]!!.removePrefix("@layout/")
                sublayouts.add(
                    AndroidSubLayout(
                        name = camelCasedId,
                        resourceId = id,
                        layoutXmlClass = layout.camelCase().capitalize() + "Xml"
                    )
                )
            } else {
                bindings.add(
                    AndroidIdHook(
                        name = raw.removePrefix("@+id/").removePrefix("@id/").camelCase(),
                        type = node.name,
                        resourceId = raw.removePrefix("@+id/").removePrefix("@id/")
                    )
                )
            }
        }
        node.children.forEach {
            addBindings(it)
        }
    }
    addBindings(node)

    return """
    |//
    |// ${fileName}Xml.swift
    |// Created by Kwift XML Android
    |//
    |package $packageName
    |
    |import android.widget.*
    |import android.view.*
    |import com.lightningkite.kwift.views.actual.*
    |import com.lightningkite.kwift.views.shared.*
    |import $applicationPackage.R
    |
    |class ${fileName}Xml {
    |
    |    ${bindings.joinToString("\n|    ") { it.run { "lateinit var $name: $type" } }}
    |    ${sublayouts.joinToString("\n|    ") { it.run { "lateinit var $name: $layoutXmlClass" } }}
    |
    |    fun setup(dependency: ViewDependency): View {
    |        val view = LayoutInflater.from(dependency.context).inflate(R.layout.$nameWithoutExtension, null, false)
    |        return setup(view)
    |    }
    |    fun setup(view: View): View {
    |        ${bindings.joinToString("\n|        ") { it.run { "$name = view.findViewById<$type>(R.id.$resourceId)" } }}
    |        ${sublayouts.joinToString("\n|        ") { it.run { "$name = $layoutXmlClass().apply{ setup(view.findViewById<View>(R.id.$resourceId)) }" } }}
    |        return view
    |    }
    |}
    """.trimMargin("|")
}