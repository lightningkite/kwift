package com.lightningkite.khrysalis.swift

import com.lightningkite.khrysalis.analysis.resolvedCall
import com.lightningkite.khrysalis.analysis.resolvedExpressionTypeInfo
import com.lightningkite.khrysalis.generic.*
import com.lightningkite.khrysalis.replacements.*
import com.lightningkite.khrysalis.swift.replacements.SwiftImport
import com.lightningkite.khrysalis.util.forEachBetween
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.lexer.KtToken
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.resolve.calls.components.isVararg
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
import org.jetbrains.kotlin.synthetic.hasJavaOriginInHierarchy
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.typeUtil.contains
import org.jetbrains.kotlin.types.typeUtil.isTypeParameter
import java.util.concurrent.atomic.AtomicInteger

val num = AtomicInteger(0)

open class SwiftTemplateRendering: TemplateRendering {
    companion object: SwiftTemplateRendering()

    override val receiver: TemplateInfoPart<KtExpression> get() = TemplateInfoPart.empty()
    override val dispatchReceiver: TemplateInfoPart<KtExpression> get() = TemplateInfoPart.empty()
    override val extensionReceiver: TemplateInfoPart<KtExpression> get() = TemplateInfoPart.empty()
    override val value: TemplateInfoPart<KtExpression> get() = TemplateInfoPart.empty()
    override val operatorToken: TemplateInfoPart<KtToken> get() = TemplateInfoPart.empty()

    override fun parameters(name: String): TemplateInfoPart<KtExpression> {
        return TemplateInfoPart.empty()
    }

    override fun parameters(index: Int): TemplateInfoPart<KtExpression> {
        return TemplateInfoPart.empty()
    }

    override fun typeParameters(name: String): TemplateInfoPart<KtTypeProjection> {
        return TemplateInfoPart.empty()
    }

    override fun typeParameters(index: Int): TemplateInfoPart<KtTypeProjection> {
        return TemplateInfoPart.empty()
    }

    override fun block(out: FileEmitter, body: TemplateRendering.DeclarationSet.() -> Unit) {
        out.append("run {")
        val declaredMutable = HashMap<String, TemplateInfoPart<KtExpression>>()
        body(object : TemplateRendering.DeclarationSet {
            override fun declareName(context: TemplateInfoPart<KtExpression>): String {
                val name = "temp${num.getAndIncrement()}"
                if (context.type?.requiresMutable() == true) {
                    out.append("var ")
                    declaredMutable[name] = context
                } else {
                    out.append("let ")
                }
                out.append(name)
                out.append(" = ")
                context.write(out)
                out.append("\n")
                return name
            }
        })
        for(item in declaredMutable){
            item.value.write(out)
            out.append(" = ")
            out.append(item.key)
            out.append("\n")
        }
        out.append("}")
    }

    override fun declare(out: FileEmitter, name: String, context: TemplateInfoPart<KtExpression>) {
        out.append("let ")
        out.append(name)
        out.append(" = ")
        context.write(out)
        out.append("\n")
    }

    override fun writeBody(out: FileEmitter, expression: KtBlockExpression) {
        expression.swift(out as SwiftFileEmitter)
    }
}

class SwiftTemplateInfoPartExpression(override val source: KtExpression): TemplateInfoPart<KtExpression> {
    override fun write(out: FileEmitter) {
        source.swift(out as SwiftFileEmitter)
    }
    override val type: KotlinType?
        get() = source.resolvedExpressionTypeInfo?.type
}

open class SwiftTemplateRenderingFromCall(val call: KtExpression): SwiftTemplateRendering() {
    override val receiver: TemplateInfoPart<KtExpression> = (call as? KtQualifiedExpression)?.let {
        SwiftTemplateInfoPartExpression(it.receiverExpression)
    } ?: TemplateInfoPart.empty()
    override val dispatchReceiver: TemplateInfoPart<KtExpression> get() = object : TemplateInfoPart<KtExpression> {
        override fun write(out: FileEmitter) {
            call.resolvedCall?.dispatchReceiver?.swift(out as SwiftFileEmitter)
        }
    }
    override val extensionReceiver: TemplateInfoPart<KtExpression> get() = receiver
    override val value: TemplateInfoPart<KtExpression> get() = (call as? KtBinaryExpression)?.right?.let { SwiftTemplateInfoPartExpression(it) } ?: TemplateInfoPart.empty()
    override val operatorToken: TemplateInfoPart<KtToken> get() = (call as? KtBinaryExpression)?.operationToken?.let {
        object:TemplateInfoPart<KtToken> {
            override val source: KtToken
                get() = it as KtToken
            override fun write(out: FileEmitter) {
                operators.find { op -> op.kotlinToken == it }?.swiftToken?.let { out.append(it) }
            }
        }
    } ?: TemplateInfoPart.empty()

    override fun parameters(name: String): TemplateInfoPart<KtExpression> {
        val call = this.call.resolvedCall ?: return TemplateInfoPart.empty()
        return call.valueArguments.entries.find { it.key.name.asString() == name }?.let {
            if (it.key.isVararg)
                object : TemplateInfoPart<KtExpression> {
                    override fun write(out: FileEmitter) {
                        it.value.arguments.map { it.getArgumentExpression() }.forEachBetween(
                            forItem = { it!!.swift(out as SwiftFileEmitter) },
                            between = { out.append(", ") }
                        )
                    }
                }
            else
                it.value.arguments.firstOrNull()?.getArgumentExpression()?.let { SwiftTemplateInfoPartExpression(it) }
        } ?: TemplateInfoPart.empty()
    }

    override fun parameters(index: Int): TemplateInfoPart<KtExpression> {
        val call = this.call.resolvedCall ?: return TemplateInfoPart.empty()
        return call.valueArguments.entries.find { it.key.index == index }?.let {
            if (it.key.isVararg)
                object : TemplateInfoPart<KtExpression> {
                    override fun write(out: FileEmitter) {
                        it.value.arguments.map { it.getArgumentExpression() }.forEachBetween(
                            forItem = { it!!.swift(out as SwiftFileEmitter) },
                            between = { out.append(", ") }
                        )
                    }
                }
            else
                it.value.arguments.firstOrNull()?.getArgumentExpression()?.let { SwiftTemplateInfoPartExpression(it) }
        } ?: TemplateInfoPart.empty()
    }

    override fun typeParameters(name: String): TemplateInfoPart<KtTypeProjection> {
        val callExpr = call as? KtCallExpression ?: (call as? KtQualifiedExpression)?.selectorExpression as? KtCallExpression
        val entry = call.resolvedCall?.typeArguments?.entries?.find { it.key.name.asString() == name }
        val index = entry?.key?.index
        return index?.let { callExpr?.typeArguments?.getOrNull(it) }?.let {
            object : TemplateInfoPart<KtTypeProjection> {
                override val source: KtTypeProjection = it
                override fun write(out: FileEmitter) {
                    it.swift(out as SwiftFileEmitter)
                }
            }
        } ?: entry?.value?.let {
            object : TemplateInfoPart<KtTypeProjection> {
                override fun write(out: FileEmitter) {
                    it.swift(out as SwiftFileEmitter)
                }
            }
        } ?: TemplateInfoPart.empty()
    }

    override fun typeParameters(index: Int): TemplateInfoPart<KtTypeProjection> {
        val callExpr = call as? KtCallExpression ?: (call as? KtQualifiedExpression)?.selectorExpression as? KtCallExpression
        return callExpr?.typeArguments?.getOrNull(index)?.let {
            object : TemplateInfoPart<KtTypeProjection> {
                override val source: KtTypeProjection = it
                override fun write(out: FileEmitter) {
                    it.swift(out as SwiftFileEmitter)
                }
            }
        } ?: call.resolvedCall?.typeArguments?.entries?.find { it.key.index == index }?.value?.let {
            object : TemplateInfoPart<KtTypeProjection> {
                override fun write(out: FileEmitter) {
                    it.swift(out as SwiftFileEmitter)
                }
            }
        } ?: TemplateInfoPart.empty()
    }

}