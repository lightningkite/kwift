package com.lightningkite.khrysalis.swift

import com.lightningkite.khrysalis.generic.PartialTranslatorByType
import com.lightningkite.khrysalis.swift.replacements.TemplatePart
import com.lightningkite.khrysalis.util.forEachBetween
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.js.descriptorUtils.getJetTypeFqName
import org.jetbrains.kotlin.js.translate.callTranslator.getReturnType
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.*
import org.jetbrains.kotlin.psi2ir.findFirstFunction
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameOrNull
import org.jetbrains.kotlin.resolve.descriptorUtil.getSuperClassNotAny
import org.jetbrains.kotlin.resolve.scopes.receivers.ClassValueReceiver
import org.jetbrains.kotlin.types.isNullable
import java.util.*
import kotlin.collections.ArrayList

fun FunctionDescriptor.callsForSwiftInterface(on: ClassDescriptor?): Boolean {
    val immediate = this.containingDeclaration == on
    val overriddenDescriptors = this.overriddenDescriptors
        .filter { it.kind == CallableMemberDescriptor.Kind.DECLARATION }
        .filter { it.containingDeclaration.fqNameOrNull()?.asString()?.startsWith("kotlin.") == false }
    return immediate && overriddenDescriptors.isNotEmpty() == true
}

fun KtModifierListOwner.swiftVisibility(): Any? = when {
    this.hasModifier(KtTokens.ABSTRACT_KEYWORD) ||
            this.hasModifier(KtTokens.OPEN_KEYWORD) -> "open"
    else -> this.visibilityModifier()
}

fun SwiftTranslator.registerClass() {

    fun PartialTranslatorByType<SwiftFileEmitter, Unit, Any>.ContextByType<*>.writeClassHeader(
        on: KtClassOrObject
    ) {
        val typedRule = on
        -"class "
        -swiftTopLevelNameElement(on)
        -typedRule.typeParameterList
        typedRule.superTypeListEntries
            .mapNotNull { it as? KtSuperTypeCallEntry }
            .map {
                it.calleeExpression?.typeReference
            }
            .plus(
                typedRule.superTypeListEntries
                    .mapNotNull { it as? KtSuperTypeEntry }
                    .map {
                        listOf(it.typeReference)
                    }
            )
            .let {
                if (on is KtClass && on.isData()) {
                    out.addImport(TemplatePart.Import("Khrysalis"))
                    it + listOf("KDataClass")
                } else it
            }
            .let {
                if (on is KtClass && on.isEnum()) {
                    out.addImport(TemplatePart.Import("Khrysalis"))
                    it + listOf("KEnum")
                } else it
            }
            .let {
                val over = on.resolvedClass?.findFirstFunction("equals") {
                    it.valueParameters.size == 1 && it.valueParameters[0].type.getJetTypeFqName(false) == "kotlin.Any"
                }
                if (over?.callsForSwiftInterface(on.resolvedClass) == true) {
                    out.addImport(TemplatePart.Import("Khrysalis"))
                    it + listOf("KEquatable")
                } else it
            }
            .let {
                val over = on.resolvedClass?.findFirstFunction("hashCode") { it.valueParameters.size == 0 }
                if (over?.callsForSwiftInterface(on.resolvedClass) == true) {
                    out.addImport(TemplatePart.Import("Khrysalis"))
                    it + listOf("KHashable")
                } else it
            }
            .let {
                val over = on.resolvedClass?.findFirstFunction("toString") { it.valueParameters.size == 0 }
                if (over?.callsForSwiftInterface(on.resolvedClass) == true) {
                    out.addImport(TemplatePart.Import("Khrysalis"))
                    it + listOf("KStringable")
                } else it
            }
            .takeUnless { it.isEmpty() }?.let {
                -" : "
                it.forEachBetween(
                    forItem = { -it },
                    between = { -", " }
                )
            }
        typedRule.typeConstraintList?.let {
            -" where "
            -it
        }
    }

    handle<KtClass>(
        condition = { typedRule.resolvedClass?.swiftTopLevelMessedUp == true },
        priority = 1000,
        action = {
            this.noReuse = true
            typedRule.containingKtFile.after.add {
                doSuper()
                -"\n"
            }
        }
    )
    handle<KtObjectDeclaration>(
        condition = { typedRule.resolvedClass?.swiftTopLevelMessedUp == true },
        priority = 1000,
        action = {
            this.noReuse = true
            typedRule.containingKtFile.after.add {
                doSuper()
                -"\n"
            }
        }
    )

    handle<KtClass>(
        condition = { typedRule.isInterface() },
        priority = 100
    ) {
        -(typedRule.swiftVisibility() ?: "public")
        -" protocol "
        -swiftTopLevelNameElement(typedRule)
        typedRule.typeParameterList?.let {
            //TODO
            //This is possible using 4 entries per interface:
            //struct XBox<T>: X - Implements X from a contained 'boxed' property of AnyX.
            //protocol AnyX - has copy of all the properties with untyped_ prepended
            //protocol X: AnyX - has associated type
            //extension X - implements AnyX, also provides .box() function
            //Then:
            //- Use XBox<T> in all property or argument positions
            //- Every time something is used in a property or argument position and is generic, use `.box()`
            //- Every time something using XBox<T> is typecast, `use x.boxed`
            throw IllegalStateException("Converting a generic interface is not possible at the moment due to Swift crap. See code for potential solution, yet unimplemented due to complexity.")
        }
        -": "
        listOf("AnyObject").plus(typedRule.superTypeListEntries.map { it.typeReference })
            .forEachBetween(
                forItem = { -it },
                between = { -", " }
            )
        -" {\n"
        -typedRule.body
        -"}\n"
        if (typedRule.body?.hasPostActions() == true) {
            -(typedRule.swiftVisibility() ?: "public")
            -" extension "
            -swiftTopLevelNameElement(typedRule)
            -" {"
            typedRule.body?.runPostActions()
            -"\n}"
        }
        typedRule.runPostActions()
    }

    handle<KtClass> {
        -(typedRule.swiftVisibility() ?: "public")
        -' '
        writeClassHeader(typedRule)
        -" {\n"
        typedRule.primaryConstructor?.valueParameters?.filter { it.hasValOrVar() }?.forEach {
            if (it.resolvedPrimaryConstructorParameter?.hasSwiftOverride == true) {
                -"private var _"
                -it.nameIdentifier
                it.typeReference?.let {
                    -": "
                    -it
                }
                -"\n"
                -"override "
                -(it.swiftVisibility() ?: "public")
                -" var "
                -it.nameIdentifier
                it.typeReference?.let {
                    -": "
                    -it
                }
                -" { get { return self._"
                -it.nameIdentifier
                -" } set(value) { self._"
                -it.nameIdentifier
                -" = value } }"
            } else {
                -(it.swiftVisibility() ?: "public")
                -" var "
                -it.nameIdentifier
                it.typeReference?.let {
                    -": "
                    -it
                }
            }
            -"\n"
        }
        val parentClassName =
            typedRule.parentOfType<KtClassBody>()?.parentOfType<KtClass>()?.resolvedClass?.name?.asString()
        if (typedRule.isInner()) {
            -"parentThis: "
            -parentClassName
            -"\n"
        }

        run {
            val c = typedRule.resolvedClass ?: return@run
            val s = c.getSuperClassNotAny() ?: return@run
            val sc = s.unsubstitutedPrimaryConstructor ?: return@run
            val cc = c.unsubstitutedPrimaryConstructor ?: return@run
            if (sc.valueParameters.size != cc.valueParameters.size) return@run
            if (sc.valueParameters.zip(cc.valueParameters).all {
                    it.first.name.asString() == it.second.name.asString()
                            && it.first.type.getJetTypeFqName(false) == it.second.type.getJetTypeFqName(false)
                }) {
                -"override "
            }
        }
        handleConstructor(this, parentClassName, this@registerClass)

        if (typedRule.superTypeListEntries
                .mapNotNull { it as? KtSuperTypeEntry }
                .any { it.typeReference?.resolvedType?.getJetTypeFqName(false) == "com.lightningkite.khrysalis.Codable" }
        ) {
            -"required public init(from decoder: Decoder) throws {\n"
            -"let values = try decoder.container(keyedBy: CodingKeys.self)\n"
            typedRule.primaryConstructor?.valueParameters?.filter { it.hasValOrVar() }?.forEach {
                if (it.typeReference?.resolvedType?.getJetTypeFqName(false) == "kotlin.Double") {
                    it.defaultValue?.let { default ->
                        -it.nameIdentifier
                        -" = try values.decodeDoubleIfPresent(forKey: ."
                        -it.nameIdentifier
                        -") ?? "
                        -default
                    } ?: run {
                        -it.nameIdentifier
                        -" = try values.decodeDouble(forKey: ."
                        -it.nameIdentifier
                        -")"
                    }
                } else if (it.typeReference?.resolvedType?.getJetTypeFqName(false) == "kotlin.Double?") {
                    it.defaultValue?.let { default ->
                        -it.nameIdentifier
                        -" = try values.decodeDoubleIfPresent(forKey: ."
                        -it.nameIdentifier
                        -") ?? "
                        -default
                    } ?: run {
                        -it.nameIdentifier
                        -" = try values.decodeDoubleIfPresent(forKey: ."
                        -it.nameIdentifier
                        -")"
                    }
                } else {
                    it.defaultValue?.let { default ->
                        -it.nameIdentifier
                        -" = try values.decodeIfPresent("
                        -it.typeReference
                        -".self, forKey: ."
                        -it.nameIdentifier
                        -") ?? "
                        -default
                    } ?: run {
                        -it.nameIdentifier
                        -" = try values.decode("
                        -it.typeReference
                        -".self, forKey: ."
                        -it.nameIdentifier
                        -")"
                    }
                }
                -"\n"
            }

            -"}\n"
            -'\n'
            -"enum CodingKeys: String, CodingKey {\n"
            typedRule.primaryConstructor?.valueParameters?.filter { it.hasValOrVar() }?.forEach {
                val jsonName = it.jsonName
                -"case "
                -it.nameIdentifier
                -" = \"${jsonName}\"\n"
            }

            -"}\n"
            -'\n'
            -"public func encode(to encoder: Encoder) throws {\n"
            -"var container = encoder.container(keyedBy: CodingKeys.self)\n"
            typedRule.primaryConstructor?.valueParameters?.filter { it.hasValOrVar() }?.forEach {
                if (it.typeReference?.resolvedType?.isNullable() == false) {
                    -"try container.encode(self."
                    -it.nameIdentifier
                    -", forKey: ."
                    -it.nameIdentifier
                    -")"
                } else {
                    -"try container.encodeIfPresent(self."
                    -it.nameIdentifier
                    -", forKey: ."
                    -it.nameIdentifier
                    -")"
                }
                -"\n"
            }

            -"}\n"
            -'\n'
        }

        if (typedRule.isData()) {
            //Generate hashCode() if not present
            if (typedRule.body?.declarations?.any { it is FunctionDescriptor && (it as KtDeclaration).name == "hashCode" && it.valueParameters.isEmpty() } != true) {
                -"public func hash(into hasher: inout Hasher) {\n"
                typedRule.primaryConstructor?.valueParameters?.filter { it.hasValOrVar() }?.forEach { param ->
                    -"hasher.combine("
                    -param.nameIdentifier
                    -")\n"
                }
                -"}\n"
            }

            //Generate equals() if not present
            if (typedRule.body?.declarations?.any { it is FunctionDescriptor && (it as KtDeclaration).name == "equals" && it.valueParameters.size == 1 } != true) {
                -"public static func == (lhs: "
                -swiftTopLevelNameElement(typedRule)
                -", rhs: "
                -swiftTopLevelNameElement(typedRule)
                -") -> Bool { return "
                typedRule.primaryConstructor?.valueParameters?.filter { it.hasValOrVar() }?.forEachBetween(
                    forItem = { param ->
                        val typeName = param.typeReference?.resolvedType?.getJetTypeFqName(false)
                        replacements.functions[typeName + ".equals"]?.firstOrNull()?.let {
                            emitTemplate(
                                requiresWrapping = true,
                                template = it.template,
                                receiver = listOf("lhs.", param.nameIdentifier),
                                allParameters = listOf("rhs.", param.nameIdentifier),
                                parameter = { listOf("rhs.", param.nameIdentifier) },
                                parameterByIndex = { listOf("rhs.", param.nameIdentifier) }
                            )
                        } ?: run {
                            -"lhs."
                            -param.nameIdentifier
                            -" == "
                            -"rhs."
                            -param.nameIdentifier
                        }
                    },
                    between = {
                        -" && "
                    }
                )
                -" }\n"
            }

            //Generate toString() if not present
            if (typedRule.body?.declarations?.any { it is FunctionDescriptor && (it as KtDeclaration).name == "toString" && it.valueParameters.isEmpty() } != true) {
                -"public var description: String { return "
                -'"'
                -swiftTopLevelNameElement(typedRule)
                -'('
                typedRule.primaryConstructor?.valueParameters?.filter { it.hasValOrVar() }?.forEachBetween(
                    forItem = {
                        -it.nameIdentifier
                        -" = \\(self."
                        -it.nameIdentifier
                        -")"
                    },
                    between = { -", " }
                )
                -')'
                -'"'
                -" }\n"
            }

            //Generate copy(..)
            -"public func copy("
            typedRule.primaryConstructor?.valueParameters?.filter { it.hasValOrVar() }?.forEachBetween(
                forItem = {
                    -it.nameIdentifier
                    -": "
                    -it.typeReference
                    -"? = "
                    if (it.typeReference?.resolvedType?.isNullable() == true) {
                        -".some(nil)"
                    } else {
                        -"nil"
                    }
                },
                between = { -", " }
            )
            -") -> "
            -swiftTopLevelNameElement(typedRule)
            typedRule.typeParameterList?.let {
                -'<'
                it.parameters.forEachBetween(
                    forItem = { -it.name },
                    between = { -", " }
                )
                -'>'
            }
            -" { return "
            -swiftTopLevelNameElement(typedRule)
            -"("
            typedRule.primaryConstructor?.valueParameters?.filter { it.hasValOrVar() }?.forEachBetween(
                forItem = {
                    -it.nameIdentifier
                    -": "
                    if (it.typeReference?.resolvedType?.isNullable() == true) {
                        -"invertOptional("
                        -it.nameIdentifier
                        -") ?? self."
                        -it.nameIdentifier
                    } else {
                        -it.nameIdentifier
                        -" ?? self."
                        -it.nameIdentifier
                    }
                },
                between = { -", " }
            )
            -") }\n"
        }

        -typedRule.body

        if (typedRule.isEnum()) {
            -"private static let _values: Array<"
            -swiftTopLevelNameElement(typedRule)
            -"> = ["
            typedRule.body?.enumEntries?.forEachBetween(
                forItem = {
                    -swiftTopLevelNameElement(typedRule)
                    -'.'
                    -it.nameIdentifier
                },
                between = { -", " }
            )
            -"]\n"
            -"public static func values() -> Array<"
            -swiftTopLevelNameElement(typedRule)
            -"> { return _values }\n"
            -"public let name: String;\n"

            -"public func toString() -> String { return self.name }\n"
        }

        -"}"
        typedRule.runPostActions()
    }

    handle<KtSuperExpression> {
        -"super"
    }

    handle<KtObjectDeclaration> {
        -(typedRule.swiftVisibility() ?: "public")
        -' '
        writeClassHeader(typedRule)
        -" {\n"
        handleConstructor(this, null, this@registerClass)
        -"public static let INSTANCE = "
        -swiftTopLevelNameElement(typedRule)
        -"()\n"
        -typedRule.body
        -"}"
        typedRule.runPostActions()
    }

    handle<KtClassBody> {
        typedRule.allChildren.toList().drop(1).dropLast(1).forEach {
            -it
        }
    }

    handle<KtClassInitializer> { /*skip*/ }

    handle<KtObjectLiteralExpression> {
        //TODO
    }

    //Enums
    handle<KtClass>(
        condition = { typedRule.isEnum() },
        priority = 10,
        action = {
            -(typedRule.swiftVisibility() ?: "public")
            -" enum "
            -swiftTopLevelNameElement(typedRule)
            -": String, KEnum, StringEnum, CaseIterable {\n"
            for (entry in typedRule.body?.enumEntries ?: listOf()) {
                -"case "
                -entry.nameIdentifier
                -"\n"
            }
            -"\npublic init(from decoder: Decoder) throws {"
            -"\n    self = try Self(rawValue: decoder.singleValueContainer().decode(RawValue.self)) ?? ."
            -typedRule.body?.enumEntries?.first()?.name
            -"\n}\n"
            //Constructor properties
            typedRule.primaryConstructorParameters
                .filter { it.hasValOrVar() }
                .forEach { prop ->
                    -(prop.swiftVisibility() ?: "public")
                    -" var "
                    -prop.nameIdentifier
                    -": "
                    -prop.typeReference
                    -" {\n"
                    -"switch self {\n"
                    for (entry in typedRule.body?.enumEntries ?: listOf()) {
                        -"case ."
                        -entry.nameIdentifier
                        -":\n"
                        -entry.initializerList?.initializers
                            ?.find { it is KtSuperTypeCallEntry }
                            ?.resolvedCall?.valueArguments?.entries
                            ?.find { it.key.name.asString() == prop.name }
                            ?.value
                            ?.arguments
                            ?.firstOrNull()
                            ?.let {
                                -"return "
                                -it
                            } ?: -"TODO()"
                        -"\n"
                    }
                    -"@unknown default:\n"
                    -"TODO()\n"
                    -"}\n"
                    -"}\n"
                }
            //Functions
            typedRule.body
                ?.functions
                ?.forEach { func ->
                    -VirtualFunction(
                        name = func.name!!,
                        resolvedFunction = func.resolvedFunction,
                        typeParameters = func.typeParameters,
                        valueParameters = func.valueParameters,
                        returnType = func.typeReference ?: func.resolvedFunction?.returnType ?: "Void",
                        body = if (func.hasModifier(KtTokens.OPEN_KEYWORD) || func.hasModifier(KtTokens.ABSTRACT_KEYWORD)) {
                            {->
                                -"{ \nswitch self {\n"
                                typedRule.body?.enumEntries?.mapNotNull { entry ->
                                    val matching = entry.body?.functions?.find { it.name == func.name } ?: return@mapNotNull null
                                    return@mapNotNull entry to matching
                                }?.forEach { (entry, matching) ->
                                    -"case ."
                                    -entry.nameIdentifier
                                    -":\n"
                                    when(val body = matching.bodyExpression){
                                        is KtBlockExpression -> {
                                            -body.allChildren.drop(1).toList().dropLast(1)
                                        }
                                        else -> {
                                            -"return "
                                            -body
                                        }
                                    }
                                    -"\n"
                                }
                                -"default:\n"
                                when(val body = func.bodyExpression){
                                    is KtBlockExpression -> {
                                        -body.allChildren.drop(1).toList().dropLast(1)
                                    }
                                    null -> -"TODO()"
                                    else -> {
                                        -"return "
                                        -body
                                    }
                                }
                                -"\n}\n}\n"
                            }
                        }
                        else func.bodyExpression as Any
                    )
                    -"\n"
                }
            //Open properties
            //Abstract properties
            //Closed properties
            -"}\n"
        }
    )

    handle<KtSecondaryConstructor> {
        -(typedRule.swiftVisibility() ?: "public")
        -" convenience init("
        typedRule.valueParameters.forEachBetween(
            forItem = { -it },
            between = { -", " }
        )
        -") {\n"
        -"self.init("
        val dg = typedRule.getDelegationCall()
        val resSuper = dg.resolvedCall
        if (resSuper != null) {
            var first = true
            resSuper.valueArguments.entries.sortedBy { it.key.index }.forEach {
                val args = it.value.arguments
                when (args.size) {
                    0 -> {
                    }
                    1 -> {
                        if (first) {
                            first = false
                        } else {
                            -", "
                        }
                        -it.key.name.asString()
                        -": "
                        -args[0].getArgumentExpression()
                    }
                    else -> {
                        if (first) {
                            first = false
                        } else {
                            -", "
                        }
                        -it.key.name.asString()
                        -": ["
                        args.forEachBetween(
                            forItem = { -it.getArgumentExpression() },
                            between = { -", " }
                        )
                        -"]"
                    }
                }
            }
        }
        -")\n"
        -typedRule.bodyExpression?.allChildren?.toList()?.drop(1)?.dropLast(1)
        -"}"
    }

    handle<KtNameReferenceExpression>(
        condition = {
            val call = typedRule.resolvedCall ?: return@handle false
            if (call.resultingDescriptor !is FakeCallableDescriptorForObject) return@handle false
            (call.getReturnType().constructor.declarationDescriptor as? ClassDescriptor)?.kind != ClassKind.ENUM_CLASS
        },
        priority = 1000,
        action = {
            doSuper()
            -".INSTANCE"
        }
    )
    handle<KtDotQualifiedExpression>(
        condition = {
            val p = (typedRule.parent as? KtDotQualifiedExpression) ?: return@handle false
            if (p.receiverExpression != typedRule) return@handle false
            val rec = p.resolvedCall?.dispatchReceiver as? ClassValueReceiver ?: return@handle false
            (rec.type.constructor.declarationDescriptor as? ClassDescriptor)?.kind != ClassKind.ENUM_ENTRY
        },
        priority = 100_000,
        action = {
            val p = typedRule.parent as KtDotQualifiedExpression
            val r = p.resolvedCall!!.dispatchReceiver as ClassValueReceiver
            val actual = r.type.constructor.declarationDescriptor
            val written = r.classQualifier.descriptor
            doSuper()
            if (actual?.swiftTopLevelMessedUp != true && actual != written) {
                -"."
                -actual?.name?.asString()
            }
            -".INSTANCE"
        }
    )
    handle<KtNameReferenceExpression>(
        condition = {
            val p = (typedRule.parent as? KtDotQualifiedExpression) ?: return@handle false
            if (p.receiverExpression != typedRule) return@handle false
            val rec = p.resolvedCall?.dispatchReceiver as? ClassValueReceiver ?: return@handle false
            (rec.type.constructor.declarationDescriptor as? ClassDescriptor)?.kind != ClassKind.ENUM_ENTRY
        },
        priority = 100_000,
        action = {
            val p = typedRule.parent as KtDotQualifiedExpression
            val r = p.resolvedCall!!.dispatchReceiver as ClassValueReceiver
            val actual = r.type.constructor.declarationDescriptor
            val written = r.classQualifier.descriptor
            doSuper()
            if (actual?.swiftTopLevelMessedUp != true && actual != written) {
                -"."
                -actual?.name?.asString()
            }
            -".INSTANCE"
        }
    )

    handle<KtNameReferenceExpression>(
        condition = {
            val descriptor =
                typedRule.resolvedReferenceTarget as? ClassDescriptor
                    ?: return@handle false
            descriptor.swiftTopLevelMessedUp
        },
        priority = 900,
        action = {
            val nameRef = typedRule
            val descriptor = nameRef.resolvedReferenceTarget as ClassDescriptor
            -descriptor.swiftTopLevelName

            run instanceBlock@{
                val call = typedRule.resolvedCall ?: return@instanceBlock
                if (call.resultingDescriptor !is FakeCallableDescriptorForObject) return@instanceBlock
                if ((call.getReturnType().constructor.declarationDescriptor as? ClassDescriptor)?.kind != ClassKind.ENUM_CLASS) {
                    -".INSTANCE"
                }
            }
        }
    )
    handle<KtDotQualifiedExpression>(
        condition = {
            val descriptor =
                (typedRule.selectorExpression as? KtNameReferenceExpression)?.resolvedReferenceTarget as? ClassDescriptor
                    ?: return@handle false
            descriptor.swiftTopLevelMessedUp
        },
        priority = 900,
        action = {
            val nameRef = typedRule.selectorExpression as KtNameReferenceExpression
            val descriptor = nameRef.resolvedReferenceTarget as ClassDescriptor
            -descriptor.swiftTopLevelName

            run instanceBlock@{
                val call = typedRule.resolvedCall ?: return@instanceBlock
                if (call.resultingDescriptor !is FakeCallableDescriptorForObject) return@instanceBlock
                if ((call.getReturnType().constructor.declarationDescriptor as? ClassDescriptor)?.kind != ClassKind.ENUM_CLASS) {
                    -".INSTANCE"
                }
            }
        }
    )
    handle<KtDotQualifiedExpression>(
        condition = {
            val descriptor =
                (((typedRule.selectorExpression as? KtCallExpression)?.calleeExpression as? KtNameReferenceExpression)?.resolvedReferenceTarget as? ConstructorDescriptor)?.constructedClass
                    ?: return@handle false
            descriptor.swiftTopLevelMessedUp
        },
        priority = 900,
        action = {
            val callExp = typedRule.selectorExpression as KtCallExpression
            val constructor =
                (callExp.calleeExpression as KtNameReferenceExpression).resolvedReferenceTarget as ConstructorDescriptor
            val descriptor = constructor.constructedClass
            -descriptor.swiftTopLevelName
            -ArgumentsList(
                on = constructor,
                resolvedCall = callExp.resolvedCall!!
            )
        }
    )

}

private fun <T : KtClassOrObject> handleConstructor(
    contextByType: PartialTranslatorByType<SwiftFileEmitter, Unit, Any>.ContextByType<T>,
    parentClassName: Any?,
    swiftTranslator: SwiftTranslator
) = with(swiftTranslator) {
    with(contextByType) {
        val isInner = (typedRule as? KtClass)?.isInner() == true
        val isEnum = (typedRule as? KtClass)?.isEnum() == true
        -(contextByType.typedRule.primaryConstructor?.swiftVisibility() ?: "public")
        -" init("
        partOfParameter = true
        contextByType.typedRule.primaryConstructor?.let { cons ->
            (if (isInner) {
                listOf(listOf("parentThis: ", parentClassName)) + cons.valueParameters
            } else {
                cons.valueParameters
            }).forEachBetween(
                forItem = { -it },
                between = { -", " }
            )
        } ?: contextByType.run {
            if (isInner) {
                -"parentThis: "
                -parentClassName
            } else Unit
        }
        partOfParameter = false
        -") {\n"
        if (isInner) {
            -"self.parentThis = parentThis;\n"
        }

        //Parameter assignment first
        contextByType.typedRule.primaryConstructor?.let { cons ->
            cons.valueParameters.asSequence().filter { it.hasValOrVar() }.forEach {
                -"self."
                if (it.resolvedPrimaryConstructorParameter?.hasSwiftOverride == true) {
                    -'_'
                    -it.nameIdentifier
                } else {
                    -it.nameIdentifier
                }
                -" = "
                -it.nameIdentifier
                -"\n"
            }
        }

        //If used by future initializers directly, create inbetween.
        val usedInInits = listOfNotNull(
            contextByType.typedRule.primaryConstructor?.valueParameters?.map { it.defaultValue },
            contextByType.typedRule.body?.children?.mapNotNull { (it as? KtProperty)?.initializer }
        )
            .asSequence()
            .flatMap { it.asSequence() }
            .filterNotNull()
            .flatMap {
                val matches = HashSet<PropertyDescriptor>()
                fun check(it: PsiElement) {
                    if (it is KtNameReferenceExpression) {
                        val prop = it.resolvedReferenceTarget as? PropertyDescriptor ?: return
                        if (prop.containingDeclaration == contextByType.typedRule.resolvedClass) {
                            matches.add(prop)
                        }
                    }
                    if (it !is KtFunctionLiteral) {
                        it.allChildren.forEach { check(it) }
                    }
                }
                check(it)
                matches.asSequence()
            }
            .toSet()

        //If uses self capture, delay and mark with !
        val postSuper = ArrayList<() -> Unit>()

        //Then, in order, variable initializers
        suppressReceiverAddition = true
        contextByType.typedRule.body?.children?.forEach {
            when (it) {
                is KtProperty -> {
                    it.initializer?.let { init ->
                        if (swiftTranslator.capturesSelf(init, contextByType.typedRule.resolvedClass)) {
                            postSuper += {
                                -"self."
                                if (it.resolvedProperty?.hasSwiftBacking == true) {
                                    -'_'
                                }
                                -it.nameIdentifier
                                -" = "
                                -init
                                -"\n"
                            }
                        } else if (it.resolvedProperty in usedInInits) {
                            -"let "
                            -it.nameIdentifier
                            -": "
                            -(it.typeReference ?: it.resolvedProperty?.type ?: it.resolvedVariable?.type)
                            -" = "
                            -init
                            -"\n"
                            -"self."
                            if (it.resolvedProperty?.hasSwiftBacking == true) {
                                -'_'
                            }
                            -it.nameIdentifier
                            -" = "
                            -it.nameIdentifier
                            -"\n"
                        } else {
                            -"self."
                            if (it.resolvedProperty?.hasSwiftBacking == true) {
                                -'_'
                            }
                            -it.nameIdentifier
                            -" = "
                            -init
                            -"\n"
                        }
                    }
                }
            }
        }
        suppressReceiverAddition = false
        //Then super
        val superEntries =
            (typedRule as? KtEnumEntry)?.initializerList?.initializers?.mapNotNull { it as? KtSuperTypeCallEntry }
                ?: contextByType.typedRule.superTypeListEntries.mapNotNull { it as? KtSuperTypeCallEntry }
        superEntries
            .takeUnless { it.isEmpty() }
            ?.firstOrNull()?.let {
                -"super.init("
                it.resolvedCall?.valueArguments?.entries
                    ?.sortedBy { it.key.index }
                    ?.filter { it.value.arguments.isNotEmpty() }
                    ?.forEachBetween(
                        forItem = {
                            val args = it.value.arguments
                            when (args.size) {
                                0 -> {
                                }
                                1 -> {
                                    -it.key.name.asString()
                                    -": "
                                    -args[0].getArgumentExpression()
                                }
                                else -> {
                                    -it.key.name.asString()
                                    -": ["
                                    args.forEachBetween(
                                        forItem = { -it.getArgumentExpression() },
                                        between = { -", " }
                                    )
                                    -"]"
                                }
                            }
                        },
                        between = { -", " }
                    )
                -")\n"
            }
        -"//Necessary properties should be initialized now\n"
        postSuper.forEach { it() }
        //Then, in order, anon initializers
        contextByType.typedRule.body?.children?.forEach {
            when (it) {
                is KtAnonymousInitializer -> {
                    val b = it.body
                    if (b is KtBlockExpression) {
                        b.statements.forEach {
                            -it
                            -"\n"
                        }
                    } else {
                        -b
                        -"\n"
                    }
                }
            }
        }
        -"}\n"
    }
}

private val KtParameter.jsonName: String
    get() = this.annotations
        .flatMap { it.entries }
        .find { it.typeReference?.text?.substringAfterLast('.')?.substringBefore('(') == "JsonProperty" }
        ?.valueArguments
        ?.firstOrNull()
        ?.getArgumentExpression()
        ?.text
        ?.trim('"')
        ?: this.name ?: "x"
private val weakKtClassPostActions = WeakHashMap<KtElement, ArrayList<() -> Unit>>()
fun KtElement.runPostActions() {
    weakKtClassPostActions.remove(this)?.forEach { it() }
}

fun KtElement.hasPostActions(): Boolean {
    return weakKtClassPostActions.get(this)?.isNotEmpty() == true
}

fun KtElement.addPostAction(action: () -> Unit) {
    weakKtClassPostActions.getOrPut(this) { ArrayList() }.add(action)
}


fun KtClass.isSimpleEnum() = isEnum() && body?.enumEntries?.all { !it.hasInitializer() && it.body == null } == true