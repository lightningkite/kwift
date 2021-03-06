package com.lightningkite.khrysalis.ios.drawables

import com.lightningkite.khrysalis.utils.*
import com.lightningkite.khrysalis.ios.*
import com.lightningkite.khrysalis.ios.layout.setToColor
import java.lang.IllegalStateException
import java.util.*

fun <E> MutableList<E>.unshift(): E {
    return removeAt(0)
}

val pathLetters = charArrayOf(
    'M',
    'L',
    'Z',
    'H',
    'V',
    'Q',
    'T',
    'C',
    'S',
    'A'
)
val spaceOrComma = Regex("[ ,]+")
fun convertVectorDrawable(name: String, node: XmlNode, out: Appendable) {
    println("Writing vector $name")

    val width = node.attributeAsDouble("android:width") ?: 0.0
    val height = node.attributeAsDouble("android:height") ?: 0.0

    with(out) {
        appendln("static let $name: Drawable = Drawable { (view: UIView?) -> CALayer in ")
        appendln(
            "    let scaleX: CGFloat = CGFloat(${node.attributeAsSwiftDimension("android:width") ?: "10"}) / ${
                node.attributeAsDouble(
                    "android:viewportWidth"
                ) ?: "10"
            }"
        )
        appendln(
            "    let scaleY: CGFloat = CGFloat(${node.attributeAsSwiftDimension("android:height") ?: "10"}) / ${
                node.attributeAsDouble(
                    "android:viewportHeight"
                ) ?: "10"
            }"
        )
        appendln("    let layer = CALayer()")
        node.children.filter { it.name == "path" }.forEach { subnode ->
            subnode.children
                .find { it.name == "aapt:attr" && it.allAttributes["name"] == "android:fillColor" }
                ?.children?.find { it.name == "gradient" }
                ?.let { gradientNode ->

                    appendln("    layer.addSublayer({")
                    appendln("        let mask = CAShapeLayer()")
                    subnode.allAttributes["android:fillType"]?.let {
                        when (it) {
                            "evenOdd" -> appendln("        mask.fillRule = .evenOdd")
                            else -> {
                            }
                        }
                    }
                    subnode.allAttributes["android:pathData"]?.let { pathData ->
                        appendln("        let path = CGMutablePath()")
                        pathDataToSwift(pathData)
                        appendln("        mask.path = path")
                    }
                    appendln("        let gradient = CAGradientLayer()")
                    appendln("        gradient.mask = mask")
                    gradientNode.attributeAsDouble("android:startX")?.let { x ->
                        gradientNode.attributeAsDouble("android:startY")?.let { y ->
                            appendln("        gradient.startPoint = CGPoint(x: ${x / width}, y: ${y / height})")
                        }
                    }
                    gradientNode.attributeAsDouble("android:endX")?.let { x ->
                        gradientNode.attributeAsDouble("android:endY")?.let { y ->
                            appendln("        gradient.endPoint = CGPoint(x: ${x / width}, y: ${y / height})")
                        }
                    }
                    val colors = gradientNode.children.filter { it.name == "item" }.mapNotNull {
                        it.attributeAsSwiftColor("android:color")
                    }.joinToString(", ", "[", "]") { it + ".cgColor" }
                    appendln("        gradient.colors = $colors")
                    appendln("        gradient.frame = CGRect(x: 0, y: 0, width: $width, height: $height)")
                    appendln("        return gradient")
                    appendln("    }())")

                } ?: run {
                appendln("    layer.addSublayer({")
                appendln("        let sublayer = CAShapeLayer()")
                subnode.allAttributes["android:fillType"]?.let {
                    when (it) {
                        "evenOdd" -> appendln("        sublayer.fillRule = .evenOdd")
                        else -> {
                        }
                    }
                }
                subnode.allAttributes["android:pathData"]?.let { pathData ->
                    appendln("        let path = CGMutablePath()")
                    pathDataToSwift(pathData)
                    appendln("        sublayer.path = path")
                }
                if (!setToColor(subnode, "android:fillColor") { it, s ->
                        appendln("        sublayer.fillColor = $it.cgColor")
                    }) {
                    appendln("        sublayer.fillColor = nil")
                }
                setToColor(subnode, "android:strokeColor") { it, s ->
                    appendln("        sublayer.strokeColor = $it.cgColor")
                }
                subnode.attributeAsDouble("android:strokeWidth")?.let {
                    appendln("        sublayer.lineWidth = ${it} * scaleX")
                }
                appendln("        return sublayer")
                appendln("    }())")
            }
        }
        appendln("    layer.bounds.size = CGSize(width: ${width}, height: ${height})")
        appendln("    layer.scaleOverResize = true")
        appendln("    return layer")
        appendln("}")
    }

}

private fun Appendable.pathDataToSwift(pathData: String) {
    fun Double.scaleX(): String = "$this * scaleX"
    fun Double.scaleY(): String = "$this * scaleY"
    var referenceX: Double = 0.0
    var referenceY: Double = 0.0
    var previousC2X: Double = 0.0
    var previousC2Y: Double = 0.0
    var stringIndex = pathData.indexOfAny(pathLetters, 0, true)
    while (true) {
        var nextLetterIndex = pathData.indexOfAny(pathLetters, stringIndex + 1, true)
        if (nextLetterIndex == -1) nextLetterIndex = pathData.length

        val rawInstruction: Char = pathData[stringIndex]
        val arguments = ArrayList<Double>()
        val currentNumber = StringBuilder()
        for (c in pathData.substring(stringIndex + 1, nextLetterIndex)) {
            when (c) {
                ' ', ',', '-' -> {
                    if (currentNumber.length > 0) {
                        arguments.add(currentNumber.toString().toDouble())
                        currentNumber.setLength(0)
                    }
                    if (c == '-') {
                        currentNumber.append('-')
                    }
                }
                in '0'..'9' -> {
                    currentNumber.append(c)
                }
                '.' -> {
                    if (currentNumber.contains('.')) {
                        if (currentNumber.length > 0) {
                            arguments.add(currentNumber.toString().toDouble())
                            currentNumber.setLength(0)
                        }
                        currentNumber.append(c)
                    } else {
                        currentNumber.append(c)
                    }
                }
            }
        }
        if (currentNumber.length > 0) {
            arguments.add(currentNumber.toString().toDouble())
        }

        val instruction = rawInstruction.toLowerCase()
        val isAbsolute: Boolean = rawInstruction.isUpperCase()
        fun offsetX(): Double = if (isAbsolute) 0.0 else referenceX
        fun offsetY(): Double = if (isAbsolute) 0.0 else referenceY
        var updateReference = true
        appendln("        //$rawInstruction ${arguments.joinToString()}")
        while (arguments.isNotEmpty()) {
            when (instruction) {
                'm' -> {
                    val destX = arguments.unshift() + offsetX()
                    val destY = arguments.unshift() + offsetY()
                    referenceX = destX
                    referenceY = destY
                    appendln("        path.move(to: CGPoint(x: ${destX.scaleX()}, y: ${destY.scaleY()}))")
                }
                'l' -> {
                    while (arguments.size >= 2) {
                        val destX = arguments.unshift() + offsetX()
                        val destY = arguments.unshift() + offsetY()
                        referenceX = destX
                        referenceY = destY
                        appendln("        path.addLine(to: CGPoint(x: ${destX.scaleX()}, y: ${destY.scaleY()}))")
                    }
                }
                'z' -> {
                    appendln("        path.closeSubpath()")
                }
                'h' -> {
                    updateReference = false
                    referenceX =
                        if (isAbsolute) arguments.unshift() else referenceX + arguments.unshift()
                    appendln("        path.addLine(to: CGPoint(x: ${referenceX.scaleX()}, y: ${referenceY.scaleY()}))")
                }
                'v' -> {
                    updateReference = false
                    referenceY =
                        if (isAbsolute) arguments.unshift() else referenceY + arguments.unshift()
                    appendln("        path.addLine(to: CGPoint(x: ${referenceX.scaleX()}, y: ${referenceY.scaleY()}))")
                }
                'q' -> {
                    val controlX = arguments.unshift() + offsetX()
                    val controlY = arguments.unshift() + offsetY()
                    val destX = arguments.unshift() + offsetX()
                    val destY = arguments.unshift() + offsetY()
                    previousC2X = controlX
                    previousC2Y = controlY
                    referenceX = destX
                    referenceY = destY
                    appendln("        path.addQuadCurve(to: CGPoint(x: ${destX.scaleX()}, y: ${destY.scaleY()}), control: CGPoint(x: ${controlX.scaleX()}, y: ${controlY.scaleY()}))")
                }
                't' -> {
                    val destX = arguments.unshift() + offsetX()
                    val destY = arguments.unshift() + offsetY()
                    val controlX = referenceX - (referenceX - previousC2X)
                    val controlY = referenceY - (referenceY - previousC2Y)
                    referenceX = destX
                    referenceY = destY
                    appendln("        path.addQuadCurve(to: CGPoint(x: ${destX.scaleX()}, y: ${destY.scaleY()}), control: CGPoint(x: ${controlX.scaleX()}, y: ${controlY.scaleY()}))")
                }
                'c' -> {
                    val control1X = arguments.unshift() + offsetX()
                    val control1Y = arguments.unshift() + offsetY()
                    val control2X = arguments.unshift() + offsetX()
                    val control2Y = arguments.unshift() + offsetY()
                    val destX = arguments.unshift() + offsetX()
                    val destY = arguments.unshift() + offsetY()
                    previousC2X = control2X
                    previousC2Y = control2Y
                    referenceX = destX
                    referenceY = destY
                    appendln("        path.addCurve(to: CGPoint(x: ${destX.scaleX()}, y: ${destY.scaleY()}), control1: CGPoint(x: ${control1X.scaleX()}, y: ${control1Y.scaleY()}), control2: CGPoint(x: ${control2X.scaleX()}, y: ${control2Y.scaleY()}))")
                }
                's' -> {
                    val control2X = arguments.unshift() + offsetX()
                    val control2Y = arguments.unshift() + offsetY()
                    val destX = arguments.unshift() + offsetX()
                    val destY = arguments.unshift() + offsetY()
                    val c1x = referenceX - (previousC2X - referenceX)
                    val c1y = referenceY - (previousC2Y - referenceY)
                    previousC2X = control2X
                    previousC2Y = control2Y
                    referenceX = destX
                    referenceY = destY
//                    appendln("        //Calculated from previous control point $previousC2X and $previousC2Y mirrored around ")
                    appendln("        path.addCurve(to: CGPoint(x: ${destX.scaleX()}, y: ${destY.scaleY()}), control1: CGPoint(x: ${c1x.scaleX()}, y: ${c1y.scaleY()}), control2: CGPoint(x: ${control2X.scaleX()}, y: ${control2Y.scaleY()}))")
                }
                'a' -> {
                    val radiusX = arguments.unshift()
                    val radiusY = arguments.unshift()
                    val xAxisRotation = arguments.unshift()
                    val largeArcFlag = arguments.unshift()
                    val sweepFlag = arguments.unshift()
                    val destX = arguments.unshift() + offsetX()
                    val destY = arguments.unshift() + offsetY()
                    referenceX = destX
                    referenceY = destY
                    appendln("        path.arcTo(radius: CGSize(width: ${radiusX.scaleX()}, height: ${radiusY.scaleY()}), rotation: ${xAxisRotation}, largeArcFlag: ${largeArcFlag > 0.5}, sweepFlag: ${sweepFlag > 0.5}, end: CGPoint(x: ${destX.scaleX()}, y: ${destY.scaleY()}))")
                }
                else -> throw IllegalStateException("Non-legal command ${instruction}")
            }
        }

        stringIndex = nextLetterIndex
        if (nextLetterIndex == pathData.length) break
    }
    if (pathData.endsWith('z', true)) {
        appendln("        path.closeSubpath()")
    }
}
