package com.lightningkite.khrysalis.ios.layout

import com.lightningkite.khrysalis.utils.*
import com.lightningkite.khrysalis.ios.*
import com.lightningkite.khrysalis.swift.safeSwiftIdentifier
import kotlin.math.PI

val LayoutConverter.Companion.displayViews
    get() = LayoutConverter(
        viewTypes = ViewType.mapOf(
            ViewType("View", "UIView") { node ->
                node.allAttributes["android:id"]?.let { raw ->
                    val name = raw.removePrefix("@+id/").removePrefix("@id/").camelCase()
                    appendln("self.${name.safeSwiftIdentifier()} = view")
                    bindings[name] =
                        converter.viewTypes[node.name]?.iosName ?: (node.name.substringAfterLast('.'))
                }
                node.attributeAsBoolean("tools:focusAtStartup")?.let { raw ->
                    appendln("view.focusAtStartup = $raw")
                }
                node.allAttributes["android:background"]?.let { raw ->
                    when {
                        raw.startsWith("@drawable/") -> {
                            node.attributeAsSwiftDrawable("android:background")!!.let {
                                appendln("view.backgroundDrawable = $it")
                            }
                        }
                        raw.startsWith("@mipmap/") -> {
                            val drawableName = raw.removePrefix("@mipmap/")
                            appendln("if let image = UIImage(named: \"$drawableName\") {")
                            appendln("view.backgroundColor = UIColor(patternImage: image)")
                            appendln("}")
                        }
                        raw.startsWith("@color/") || raw.startsWith("@android:color/") -> {
                            setToColor(node, "android:background") { it, s ->
                                appendln("view.backgroundColor = $it")
                            }
                        }
                        else -> {
                        }
                    }
                }
                node.attributeAsDouble("android:alpha")?.let {
                    appendln("view.alpha = $it")
                }
                node.attributeAsSwiftDimension("android:elevation")?.let {
                    appendln("view.layer.masksToBounds = false")
                    appendln("view.layer.shadowColor = UIColor.black.cgColor")
                    appendln("view.layer.shadowOffset = CGSize(width: 0, height: $it)")
                    appendln("view.layer.shadowRadius = $it")
                    appendln("view.layer.shadowOpacity = 0.24")
                }
                node.attributeAsDouble("android:rotation")?.let {
                    appendln("view.transform = CGAffineTransform(rotationAngle: ${it * PI / 180.0})")
                }
                node.allAttributes["android:visibility"]?.let {
                    appendln("view.visibility = UIView.${it.toUpperCase()}")
                }
                node.allAttributes["tools:systemEdges"]?.let {
                    appendln("view.safeInsets(align: ${alignFill(it)})")
                }
                node.allAttributes["tools:systemEdgesSizing"]?.let {
                    appendln("view.safeInsetsSizing(align: ${alignFill(it)})")
                }
                node.allAttributes["tools:systemEdgesBoth"]?.let {
                    appendln("view.safeInsetsBoth(align: ${alignFill(it)})")
                }
            },
            ViewType("Space", "UIView", "View") {},
            ViewType("ProgressBar", "UIActivityIndicatorView", "View") { node ->
                appendln("view.startAnimating()")
                if(!setToColor(node, "android:indeterminateTint") { it, s ->
                    appendln("view.color = $it")
                }) {
                    appendln("view.color = R.color.colorPrimary")
                }
            },
            ViewType("com.lightningkite.butterfly.views.widget.HorizontalProgressBar", "UIProgressView", "View") { node ->
                appendln("view.progressViewStyle = .bar")
                if(!setToColor(node, "android:progressTint") { it, s ->
                    appendln("view.progressTintColor = $it")
                }) {
                    appendln("view.progressTintColor = R.color.colorPrimary")
                }
            },
            ViewType("ImageView", "UIImageView", "View") { node ->
                node.attributeAsSwiftImage("android:src")?.let { text ->
                    appendln("view.image = $text")
                }
                appendln("view.clipsToBounds = true")
                appendln(
                    "view.contentMode = ${when (node.allAttributes["android:scaleType"]) {
                        "fitXY" -> ".scaleToFill"
                        "centerCrop" -> ".scaleAspectFill"
                        "centerInside" -> ".scaleAspectFit"
                        else -> ".scaleAspectFit"
                    }}"
                )
//            node.attributeAsDouble("android:rotation")?.let{
//                "view.transform = CGAffineTransform(rotationAngle: ${it * PI / 180.0}"
//            }
            },
            ViewType("com.lightningkite.butterfly.views.widget.VideoPlayer", "UIVideoView", "View") { node ->
            },
            ViewType("de.hdodenhof.circleimageview.CircleImageView", "UIImageView", "ImageView") { node ->

                appendln("view.addOnLayoutSubviews { [weak view] in")
                appendln("if let view = view {")
                appendln("    view.layer.cornerRadius = view.frame.size.width / 2;")
                appendln("    view.contentMode = .scaleAspectFill")
                appendln("}")
                appendln("}")
                appendln("view.clipsToBounds = true")
            },


            ViewType("TextView", "UILabel", "View") { node ->
                handleCommonText(node)
            },
            ViewType("com.lightningkite.butterfly.views.widget.CustomView", "CustomView", "View") { node ->

                node.allAttributes["android:id"]?.let { raw ->
                    val id = raw.removePrefix("@+id/").removePrefix("@id/").camelCase()
                    (node.allAttributes["app:delegateClass"] ?: node.allAttributes["delegateClass"])?.let { raw ->
                        val name = raw.removePrefix("@+id/").removePrefix("@id/").camelCase().substringAfterLast('.')
                        appendln("let dg = $name()")
                        appendln("view.delegate = dg")
                        appendln("self.${(id + "Delegate").safeSwiftIdentifier()} = dg")
                        delegateBindings[id] = name
                    }
                }
            },

            ViewType(
                "com.lightningkite.butterfly.views.widget.SelectableText",
                "SelectableText",
                "TextView",
                handlesPadding = true
            ) { node ->
                handleCommonText(node)
            }
        )
    )


internal fun OngoingLayoutConversion.handleCommonText(node: XmlNode, viewHandle: String = "view", controlView: String? = null, checkView: String? = null) {

    val size = node.attributeAsSwiftDimension("android:textSize") ?: "12"

    val fontStylesFromFamily = listOfNotNull(
        if(node.allAttributes["android:fontFamily"]?.contains("bold", true) == true) "bold" else null,
        if(node.allAttributes["android:fontFamily"]?.contains("light", true) == true) "light" else null
    )
    val fontStyles = (node.allAttributes["android:textStyle"]?.split('|') ?: listOf()) + fontStylesFromFamily
    appendln("$viewHandle.font = UIFont.get(size: $size, style: [${fontStyles.joinToString { "\"$it\"" }}])")

    node.attributeAsSwiftDimension("android:letterSpacing")?.let {
        appendln("$viewHandle.letterSpacing = $it")
    }
    node.attributeAsBoolean("android:textAllCaps")?.let {
        appendln("$viewHandle.textAllCaps = $it")
    }
    node.attributeAsSwiftString("android:text")?.let { text ->
        appendln("$viewHandle.textString = $text")
    }
    node.attributeAsDouble("android:lineSpacingMultiplier")?.let { lineSpacingMultiplier ->
        appendln("$viewHandle.lineSpacingMultiplier = $lineSpacingMultiplier")
    }
    val lines = node.attributeAsInt("android:maxLines")
    appendln("$viewHandle.numberOfLines = ${lines ?: 0}")


    setToColor(node, "android:textColor", controlView ?: checkView ?: viewHandle) { it, s ->
        appendln("$viewHandle.textColor = $it")
    }
    node.allAttributes["android:gravity"]?.let {
        it.split('|')
            .asSequence()
            .mapNotNull { horizontalGravityWords[it] }
            .firstOrNull()
            ?.let {
                val fixed = when (it) {
                    ".start" -> ".left"
                    ".center" -> ".center"
                    ".end" -> ".right"
                    else -> ".left"
                }
                appendln("$viewHandle.textAlignment = $fixed")
            }
        it.split('|')
            .asSequence()
            .mapNotNull { verticalGravityWords[it] }
            .firstOrNull()
            ?.let {
                val fixed = when (it) {
                    ".start" -> ".none"
                    ".center" -> ".alignCenters"
                    ".end" -> ".alignBaselines"
                    else -> ".none"
                }
                appendln("$viewHandle.baselineAdjustment = $fixed")
            }
    }
}


internal val horizontalGravityWords = mapOf(
    "left" to ".start",
    "right" to ".end",
    "center_horizontal" to ".center",
    "start" to ".start",
    "end" to ".end",
    "center" to ".center"
)
internal val verticalGravityWords = mapOf(
    "top" to ".start",
    "bottom" to ".end",
    "center_vertical" to ".center",
    "center" to ".center"
)
