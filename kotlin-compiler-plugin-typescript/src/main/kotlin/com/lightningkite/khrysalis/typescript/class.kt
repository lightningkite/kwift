package com.lightningkite.khrysalis.typescript

import com.lightningkite.khrysalis.generic.PartialTranslatorByType
import com.lightningkite.khrysalis.typescript.manifest.declaresPrefix
import com.lightningkite.khrysalis.util.allOverridden
import com.lightningkite.khrysalis.util.forEachBetween
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.js.descriptorUtils.getJetTypeFqName
import org.jetbrains.kotlin.js.translate.callTranslator.getReturnType
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.*
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter
import org.jetbrains.kotlin.types.typeUtil.supertypes
import java.util.*
import kotlin.collections.ArrayList


fun TypescriptTranslator.registerClass() {

    fun PartialTranslatorByType<TypescriptFileEmitter, Unit, Any>.ContextByType<*>.writeClassHeader(
        on: KtClassOrObject,
        defaultName: String = "Companion"
    ) {
        val typedRule = on
        -"class "
        -(tsTopLevelNameElement(on) ?: defaultName)
        -typedRule.typeParameterList
        typedRule.superTypeListEntries.mapNotNull { it as? KtSuperTypeCallEntry }.takeUnless { it.isEmpty() }?.let {
            -" extends "
            it.forEachBetween(
                forItem = { -it.resolvedCall?.getReturnType() },
                between = { -", " }
            )
        }
        typedRule.superTypeListEntries.mapNotNull { it as? KtSuperTypeEntry }
            .filter { it.typeReference?.resolvedType?.getJetTypeFqName(false) !in skippedExtensions }
            .takeUnless { it.isEmpty() }?.let {
                -" implements "
                it.forEachBetween(
                    forItem = { -it.typeReference },
                    between = { -", " }
                )
            }
    }

    fun PartialTranslatorByType<TypescriptFileEmitter, Unit, Any>.ContextByType<*>.writeInterfaceMarkers(on: KtClassOrObject) {
        val typedRule = on
        typedRule.superTypeListEntries
            .mapNotNull { it as? KtSuperTypeEntry }
            .filter { it.typeReference?.resolvedType?.getJetTypeFqName(false) !in skippedExtensions }
            .mapNotNull { it.typeReference?.resolvedType }
            .flatMap { listOf(it) + it.supertypes() }
            .map { it.getJetTypeFqName(false) }
            .filter { it != "kotlin.Any" }
            .distinct()
            .takeUnless { it.isEmpty() }
            ?.forEach {
                -"public static implementsInterface"
                -it.split('.').joinToString("") { it.capitalize() }
                -" = true;\n"
            }
    }

    fun PartialTranslatorByType<TypescriptFileEmitter, Unit, Any>.ContextByType<*>.writeInterfaceDefaultImplementations(
        on: KtClassOrObject
    ) {
        val resolvedType = when (on) {
            is KtClass -> on.resolvedClass
            is KtObjectDeclaration -> on.resolvedDeclarationToDescriptor as ClassDescriptor
            else -> throw IllegalArgumentException()
        }
        resolvedType
            ?.unsubstitutedMemberScope?.getContributedDescriptors(DescriptorKindFilter.CALLABLES) { true }?.asSequence()
            ?.mapNotNull { it as? MemberDescriptor }
            ?.filter { it.source == SourceElement.NO_SOURCE }
            ?.filter {
                when (it) {
                    is PropertyDescriptor -> it.allOverridden()
                        .all { (it.containingDeclaration as? ClassDescriptor)?.kind == ClassKind.INTERFACE }
                    is FunctionDescriptor -> it.allOverridden()
                        .all { (it.containingDeclaration as? ClassDescriptor)?.kind == ClassKind.INTERFACE }
                    else -> false
                }
            }
            ?.forEach {
                fun writeDefaultObj() {
                    val toOverrideParent = when (it) {
                        is PropertyDescriptor -> it.allOverridden().first().containingDeclaration as ClassDescriptor
                        is FunctionDescriptor -> it.allOverridden().first().containingDeclaration as ClassDescriptor
                        else -> return
                    }
                    -BasicType(toOverrideParent.defaultType)
                    -"Defaults."
                    out.addImport(toOverrideParent, toOverrideParent.tsTopLevelName + "Defaults")
                }
                when (it) {
                    is PropertyDescriptor -> {
                        -"public get "
                        -it.name.asString()
                        it.typeParameters.takeUnless { it.isEmpty() }?.let {
                            -'<'
                            it.forEachBetween(
                                forItem = { -it.name.asString() /*TODO: Type Argument Limits*/ },
                                between = { -", " }
                            )
                            -'>'
                        }
                        -"(): "
                        -it.type
                        -" { return "
                        writeDefaultObj()
                        -it.allOverridden().first().tsFunctionGetDefaultName
                        -"(this); }\n"

                        if (it.isVar) {
                            -"public set "
                            -it.name.asString()
                            it.typeParameters.takeUnless { it.isEmpty() }?.let {
                                -'<'
                                it.forEachBetween(
                                    forItem = { -it.name.asString() /*TODO: Type Argument Limits*/ },
                                    between = { -", " }
                                )
                                -'>'
                            }
                            -"(value: "
                            -it.type
                            -") { "
                            writeDefaultObj()
                            -it.allOverridden().first().tsFunctionGetDefaultName
                            -"(this, value); }\n"
                        }
                    }
                    is FunctionDescriptor -> {
                        -"public "
                        -it.name.asString()
                        it.typeParameters.takeUnless { it.isEmpty() }?.let {
                            -'<'
                            it.forEachBetween(
                                forItem = { -it.name.asString() /*TODO: Type Argument Limits*/ },
                                between = { -", " }
                            )
                            -'>'
                        }
                        -"("
                        it.valueParameters.forEachBetween(
                            forItem = {
                                -it.name.asString()
                                -": "
                                -it.type
                            },
                            between = { -", " }
                        )
                        -"): "
                        -(it.returnType ?: "void")
                        -" { return "
                        writeDefaultObj()
                        -it.allOverridden().first().tsDefaultName
                        -"(this"
                        it.valueParameters.forEach {
                            -", "
                            -it.name.asString()
                        }
                        -"); }\n"
                    }
                    else -> -"// Insert default for $it\n"
                }
            }
    }

    handle<KtClass>(
        condition = { typedRule.isInterface() },
        priority = 100
    ) {
        -"$declaresPrefix${typedRule.simpleFqName}\n"
        if (!typedRule.isPrivate()) -"export "
        -"interface "
        -tsTopLevelNameElement(typedRule)
        -typedRule.typeParameterList
        -" {\n"
        -typedRule.body
        -"}\n"
        if (!typedRule.isPrivate()) -"export "
        -"class "
        -tsTopLevelNameElement(typedRule)
        -"Defaults {"
        typedRule.runPostActions()
        -"\n}"
    }

    handle<KtClass>(
        condition = { typedRule.isInterface() && typedRule.parentOfType<KtClassBody>() != null },
        priority = 1000
    ) {
        noReuse = true
        out.fileEndingActions.add {
            doSuper()
            -"\n"
        }
    }

    handle<KtClass>(
        condition = { typedRule.parentOfType<KtClassBody>()?.parentOfType<KtClass>()?.isInterface() == true },
        priority = 1000
    ) {
        noReuse = true
        out.fileEndingActions.add {
            doSuper()
            -"\n"
        }
    }

    handle<KtClass>(
        condition = { typedRule.isInner() },
        priority = 1000
    ) {
        withReceiverScope(typedRule.parentOfType<KtClassBody>()!!.parentOfType<KtClass>()!!.resolvedClass!!, "parentThis") {
            doSuper()
        }
    }

    handle<KtObjectDeclaration>(
        condition = { typedRule.parentOfType<KtClassBody>()?.parentOfType<KtClass>()?.isInterface() == true },
        priority = 1000
    ) {
        noReuse = true
        out.fileEndingActions.add {
            doSuper()
            -"\n"
        }
    }

    handle<KtClass> {
        if (typedRule.parentOfType<KtClassBody>()?.parentOfType<KtClass>()?.isInterface() == false) {
            -(typedRule.visibilityModifier() ?: "public")
            -" static "
            -tsTopLevelNameElement(typedRule)
            -" = "
        } else {
            -"$declaresPrefix${typedRule.fqName?.asString()}\n"
            if (!typedRule.isPrivate()) -"export "
        }

        when (typedRule.modalityModifierType()) {
            KtTokens.ABSTRACT_KEYWORD -> -"abstract "
        }
        writeClassHeader(typedRule)
        -" {\n"
        writeInterfaceMarkers(typedRule)
        typedRule.primaryConstructor?.valueParameters?.filter { it.hasValOrVar() }?.forEach {

            -(it.visibilityModifier() ?: "public")
            -" "
            if ((it.valOrVarKeyword as? LeafPsiElement)?.elementType == KtTokens.VAL_KEYWORD) {
                -"readonly "
            }
            -it.nameIdentifier
            it.typeReference?.let {
                -": "
                -it
            }
            -";\n"
        }
        val parentClassName =
            typedRule.parentOfType<KtClassBody>()?.parentOfType<KtClass>()?.resolvedClass?.tsTopLevelName
        if (typedRule.isInner()) {
            -"parentThis: "
            -parentClassName
            -";\n"
        }
        typedRule.primaryConstructor?.let { cons ->
            if (typedRule.isEnum()) {
                -"private"
            } else if (typedRule.hasModifier(KtTokens.ABSTRACT_KEYWORD)) {
                -"protected"
            } else {
                -(cons.visibilityModifier() ?: "public")
            }
            -" constructor("
            (if (typedRule.isEnum()) {
                listOf("name: string") + cons.valueParameters
            } else if (typedRule.isInner()) {
                listOf("parentThis: $parentClassName") + cons.valueParameters
            } else {
                cons.valueParameters
            }).forEachBetween(
                forItem = { -it },
                between = { -", " }
            )
            -") {\n"
            typedRule.superTypeListEntries.mapNotNull { it as? KtSuperTypeCallEntry }.takeUnless { it.isEmpty() }
                ?.firstOrNull()?.let {
                    -"super("
                    if (typedRule.isEnum()) {
                        listOf("name: string") + it.valueArguments
                    } else {
                        it.valueArguments
                    }.forEachBetween(
                        forItem = { -it },
                        between = { -", " }
                    )
                    -");\n"
                }
            if (typedRule.isEnum()) {
                -"this.name = name;\n"
            } else if (typedRule.isInner()) {
                -"this.parentThis = parentThis;\n"
            }
            //Parameter assignment first
            cons.valueParameters.asSequence().filter { it.hasValOrVar() }.forEach {
                -"this."
                -it.nameIdentifier
                -" = "
                -it.nameIdentifier
                -";\n"
            }
            //Then, in order, variable initializers and anon initializers
            typedRule.body?.children?.forEach {
                when (it) {
                    is KtProperty -> {
                        it.initializer?.let { init ->
                            -"this."
                            -it.nameIdentifier
                            -" = "
                            -init
                            -";\n"
                        }
                    }
                    is KtAnonymousInitializer -> {
                        val b = it.body
                        if (b is KtBlockExpression) {
                            b.statements.forEach {
                                -it
                                -";\n"
                            }
                        } else {
                            -b
                            -";\n"
                        }
                    }
                }
            }
            -"}\n"
        } ?: typedRule.superTypeListEntries.mapNotNull { it as? KtSuperTypeCallEntry }.takeUnless { it.isEmpty() }
            ?.firstOrNull()?.let {
                if (typedRule.hasModifier(KtTokens.ABSTRACT_KEYWORD)) {
                    -"protected "
                } else {
                    -"public "
                }
                -"constructor() { super"
                -it.valueArgumentList
                -"; }"
            } ?: run {
            if (typedRule.isEnum()) {
                -"constructor(name: string) { this.name = name; }\n"
            }
        }

        if (typedRule.superTypeListEntries
                .mapNotNull { it as? KtSuperTypeEntry }
                .any { it.typeReference?.resolvedType?.getJetTypeFqName(false) == "com.lightningkite.khrysalis.Codable" }
        ) {
            //Generate codable constructor
            out.addImport("khrysalis/dist/net/jsonParsing", "parse", "parseJsonTyped")
            -"public static fromJson"
            -typedRule.typeParameterList
            -"(obj: any"
            typedRule.typeParameters.forEach {
                -", "
                -it.name
                -": any"
            }
            -"): "
            -tsTopLevelNameElement(typedRule)
            -typedRule.typeParameterList
            -" { return new "
            -tsTopLevelNameElement(typedRule)
            -"(\n"
            typedRule.primaryConstructor?.valueParameters?.forEachBetween(
                forItem = {
                    if (it.hasValOrVar()) {
                        val type = it.typeReference?.resolvedType ?: run {
                            -"obj[\""
                            -it.jsonName
                            -"\"]"
                            return@forEachBetween
                        }

                        -"parseJsonTyped("
                        -"obj[\""
                        -it.jsonName
                        -"\"], "
                        (type.constructor.declarationDescriptor as? TypeParameterDescriptor)?.let {
                            -it.name.asString()
                        } ?: -CompleteReflectableType(type)
                        -") as "
                        -type
                    } else {
                        -"undefined"
                    }
                },
                between = {
                    -", \n"
                }
            )
            -"\n) }\n"

            //Generate toJson()
            -"public toJson(): object { return {\n"
            typedRule.primaryConstructor?.valueParameters?.filter { it.hasValOrVar() }?.forEachBetween(
                forItem = {
                    -it.jsonName
                    -": this."
                    -it.jsonName
                },
                between = {
                    -", \n"
                }
            )
            -"\n} }\n"
        }

        if (typedRule.isData()) {
            //Generate hashCode() if not present
            if (typedRule.body?.declarations?.any { it is FunctionDescriptor && (it as KtDeclaration).name == "hashCode" && it.valueParameters.isEmpty() } != true) {
                -"public hashCode(): number {\nlet hash = 17;\n"
                typedRule.primaryConstructor?.valueParameters?.filter { it.hasValOrVar() }?.forEach { param ->
                    val rawType = param.typeReference?.resolvedType
                        ?: throw IllegalArgumentException("No type reference available to generate hashCode() function")
                    val typeName = param.typeReference?.resolvedType?.getJetTypeFqName(false)
                    -"hash = 31 * hash + "
                    replacements.functions[typeName + ".hashCode"]?.firstOrNull()?.let {
                        emitTemplate(
                            requiresWrapping = true,
                            template = it.template,
                            receiver = listOf("this.", param.nameIdentifier)
                        )
                    } ?: run {
                        if (rawType.constructor.declarationDescriptor is TypeParameterDescriptor) {
                            out.addImport("khrysalis/dist/Kotlin", "hashAnything")
                            -"hashAnything(this."
                            -param.nameIdentifier
                            -")"
                        } else {
                            -"this."
                            -param.nameIdentifier
                            -"?.hashCode() ?? 0"
                        }
                    }
                    -";\n"
                }

                -"return hash;\n}\n"
            }

            //Generate equals() if not present
            if (typedRule.body?.declarations?.any { it is FunctionDescriptor && (it as KtDeclaration).name == "equals" && it.valueParameters.size == 1 } != true) {
                -"public equals(other: any): boolean { return other instanceof "
                -tsTopLevelNameElement(typedRule)
                typedRule.primaryConstructor?.valueParameters?.filter { it.hasValOrVar() }?.forEach { param ->
                    -" && "
                    val rawType = param.typeReference?.resolvedType
                        ?: throw IllegalArgumentException("No type reference available to generate hashCode() function")
                    val typeName = param.typeReference?.resolvedType?.getJetTypeFqName(false)
                    replacements.functions[typeName + ".equals"]?.firstOrNull()?.let {
                        emitTemplate(
                            requiresWrapping = true,
                            template = it.template,
                            receiver = listOf("this.", param.nameIdentifier),
                            allParameters = listOf("other.", param.nameIdentifier),
                            parameter = { listOf("other.", param.nameIdentifier) },
                            parameterByIndex = { listOf("other.", param.nameIdentifier) }
                        )
                    } ?: run {
                        if (rawType.constructor.declarationDescriptor is TypeParameterDescriptor) {
                            out.addImport("khrysalis/dist/Kotlin", "equalityAnything")
                            -"equalityAnything(this."
                            -param.nameIdentifier
                            -", "
                            -"other."
                            -param.nameIdentifier
                            -")"
                        } else {
                            -"(this."
                            -param.nameIdentifier
                            -"?.equals("
                            -"other."
                            -param.nameIdentifier
                            -") ?? "
                            -"other."
                            -param.nameIdentifier
                            -" === null"
                            -")"
                        }
                    }
                }
                -" }\n"
            }

            //Generate toString() if not present
            if (typedRule.body?.declarations?.any { it is FunctionDescriptor && (it as KtDeclaration).name == "toString" && it.valueParameters.isEmpty() } != true) {
                -"public toString(): string { return "
                -'`'
                -tsTopLevelNameElement(typedRule)
                -'('
                typedRule.primaryConstructor?.valueParameters?.filter { it.hasValOrVar() }?.forEachBetween(
                    forItem = {
                        -it.nameIdentifier
                        -" = \${this."
                        -it.nameIdentifier
                        -"}"
                    },
                    between = { -", " }
                )
                -')'
                -'`'
                -" }\n"
            }

            //Generate copy(..)
            -"public copy("
            typedRule.primaryConstructor?.valueParameters?.filter { it.hasValOrVar() }?.forEachBetween(
                forItem = {
                    -it.nameIdentifier
                    -": "
                    -it.typeReference
                    -" = this."
                    -it.nameIdentifier
                },
                between = { -", " }
            )
            -") { return new "
            -tsTopLevelNameElement(typedRule)
            -"("
            typedRule.primaryConstructor?.valueParameters?.filter { it.hasValOrVar() }?.forEachBetween(
                forItem = {
                    -it.nameIdentifier
                },
                between = { -", " }
            )
            -"); }\n"
        }

        -typedRule.body

        writeInterfaceDefaultImplementations(typedRule)
        if (typedRule.isEnum()) {
            -"private static _values: Array<"
            -tsTopLevelNameElement(typedRule)
            -"> = ["
            typedRule.body?.enumEntries?.forEachBetween(
                forItem = {
                    -tsTopLevelNameElement(typedRule)
                    -'.'
                    -it.nameIdentifier
                },
                between = { -", " }
            )
            -"];\n"
            -"public static values(): Array<"
            -tsTopLevelNameElement(typedRule)
            -"> { return "
            -tsTopLevelNameElement(typedRule)
            -"._values; }\n"
            -"public readonly name: string;\n"

            -"public static valueOf(name: string): "
            -tsTopLevelNameElement(typedRule)
            -" { return ("
            -tsTopLevelNameElement(typedRule)
            -" as any)[name]; }\n"

            -"public toString(): string { return this.name }\n"
        }

        -"}"
        typedRule.runPostActions()
    }

    handle<KtSuperExpression> {
        -"super"
    }

    handle<KtObjectDeclaration> {
        if (typedRule.parentOfType<KtClassBody>()?.parentOfType<KtClass>()?.isInterface() == false) {
            -(typedRule.visibilityModifier() ?: "public")
            -" static "
            -(tsTopLevelNameElement(typedRule) ?: "Companion")
            -" = "
        } else {
            if (!typedRule.isPrivate()) {
                -"$declaresPrefix${typedRule.simpleFqName}\n"
                -"export "
            }
        }
        writeClassHeader(typedRule)
        -" {\n"
        writeInterfaceMarkers(typedRule)
        -"private constructor() {\n"
        typedRule.superTypeListEntries.mapNotNull { it as? KtSuperTypeCallEntry }.takeUnless { it.isEmpty() }
            ?.firstOrNull()?.let {
                -"super"
                -it.valueArgumentList
                -";\n"
            }
        //Then, in order, variable initializers and anon initializers
        typedRule.body?.children?.forEach {
            when (it) {
                is KtProperty -> {
                    it.initializer?.let { init ->
                        -"this."
                        -it.nameIdentifier
                        -" = "
                        -init
                        -";\n"
                    }
                }
                is KtAnonymousInitializer -> {
                    val b = it.body
                    if (b is KtBlockExpression) {
                        b.statements.forEach {
                            -it
                            -";\n"
                        }
                    } else {
                        -b
                        -";\n"
                    }
                }
            }
        }
        -"}\n"
        -"public static INSTANCE = new "
        -(tsTopLevelNameElement(typedRule) ?: "Companion")
        -"();\n"
        -typedRule.body
        writeInterfaceDefaultImplementations(typedRule)
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
        -"new "
        writeClassHeader(typedRule.objectDeclaration, "Anon")
        -" {\n"
        writeInterfaceMarkers(typedRule.objectDeclaration)
        -"public constructor() {\n"
        typedRule.objectDeclaration.superTypeListEntries.mapNotNull { it as? KtSuperTypeCallEntry }
            .takeUnless { it.isEmpty() }
            ?.firstOrNull()?.let {
                -"super"
                -it.valueArgumentList
                -";\n"
            }
        //Then, in order, variable initializers and anon initializers
        typedRule.objectDeclaration.body?.children?.forEach {
            when (it) {
                is KtProperty -> {
                    it.initializer?.let { init ->
                        -"this."
                        -it.nameIdentifier
                        -" = "
                        -init
                        -";\n"
                    }
                }
                is KtAnonymousInitializer -> {
                    val b = it.body
                    if (b is KtBlockExpression) {
                        b.statements.forEach {
                            -it
                            -";\n"
                        }
                    } else {
                        -b
                        -";\n"
                    }
                }
            }
        }
        -"}\n"
        -typedRule.objectDeclaration.body
        writeInterfaceDefaultImplementations(typedRule.objectDeclaration)
        -"}()"
    }

    handle<KtEnumEntry> {
        -"public static "
        -tsTopLevelNameElement(typedRule)
        -" = new "
        writeClassHeader(typedRule)
        -" {\n"
        writeInterfaceMarkers(typedRule)
        -"public constructor() {\n"
        -"super"
        -'('
        val args = arrayListOf({ ->
            -'"'
            -tsTopLevelNameElement(typedRule)
            -'"'
            Unit
        })
        (typedRule.initializerList?.initializers?.firstOrNull() as? KtSuperTypeCallEntry)?.valueArguments?.forEach {
            args.add { -it }
        }
        args.forEachBetween(
            forItem = { it() },
            between = { -", " }
        )
        -");\n"
        //Then, in order, variable initializers and anon initializers
        typedRule.body?.children?.forEach {
            when (it) {
                is KtProperty -> {
                    it.initializer?.let { init ->
                        -"this."
                        -it.nameIdentifier
                        -" = "
                        -init
                        -";\n"
                    }
                }
                is KtAnonymousInitializer -> {
                    val b = it.body
                    if (b is KtBlockExpression) {
                        b.statements.forEach {
                            -it
                            -";\n"
                        }
                    } else {
                        -b
                        -";\n"
                    }
                }
            }
        }
        -"}\n"
        -typedRule.body
        writeInterfaceDefaultImplementations(typedRule)
        -"}();\n"
    }

    handle<KtEnumEntry>(
        condition = { typedRule.body == null || typedRule.body?.declarations?.isEmpty() == true },
        priority = 100
    ) {
        -"public static "
        -tsTopLevelNameElement(typedRule)
        -" = new "
        -typedRule.parentOfType<KtClassBody>()?.parentOfType<KtClass>()?.nameIdentifier
        -'('
        val args = arrayListOf({ ->
            -'"'
            -tsTopLevelNameElement(typedRule)
            -'"'
            Unit
        })
        (typedRule.initializerList?.initializers?.firstOrNull() as? KtSuperTypeCallEntry)?.valueArguments?.forEach {
            args.add { -it }
        }
        args.forEachBetween(
            forItem = { it() },
            between = { -", " }
        )
        -");\n"
    }

    handle<KtDotQualifiedExpression>(
        condition = {
            var current = typedRule
            while (current.selectorExpression is KtDotQualifiedExpression) {
                current = current.selectorExpression as KtDotQualifiedExpression
            }
            val callExp = current.selectorExpression as? KtCallExpression ?: return@handle false
            val nre = callExp.calleeExpression as? KtNameReferenceExpression ?: return@handle false
            val resolved = nre.resolvedReferenceTarget as? ConstructorDescriptor ?: return@handle false
            (resolved.constructedClass as? ClassDescriptor)?.tsTopLevelMessedUp == true
        },
        priority = 100,
        action = {
            var current = typedRule
            while (current.selectorExpression is KtDotQualifiedExpression) {
                current = current.selectorExpression as KtDotQualifiedExpression
            }
            val callExp = current.selectorExpression as? KtCallExpression ?: return@handle
            val nre = callExp.calleeExpression as? KtNameReferenceExpression ?: return@handle
            val constructor = nre.resolvedReferenceTarget as ConstructorDescriptor
            val cl = constructor.constructedClass
            val n = cl.tsTopLevelName
            -n
            val withComments = callExp.valueArgumentList?.withComments() ?: listOf()
            -ArgumentsList(
                on = constructor,
                resolvedCall = callExp.resolvedCall,
                orderedArguments = withComments.filter { !it.first.isNamed() }
                    .map { it.first.getArgumentExpression()!! to it.second },
                namedArguments = withComments.filter { it.first.isNamed() },
                lambdaArgument = callExp.lambdaArguments.firstOrNull()
            )
            out.addImport(cl, n)
        }
    )

    handle<KtDotQualifiedExpression>(
        condition = {
            var current = typedRule
            while (current.selectorExpression is KtDotQualifiedExpression) {
                current = current.selectorExpression as KtDotQualifiedExpression
            }
            val nre = current.selectorExpression as? KtNameReferenceExpression ?: return@handle false
            val resolved = nre.resolvedReferenceTarget as? ClassDescriptor ?: return@handle false
            resolved.tsTopLevelMessedUp
        },
        priority = 100,
        action = {
            var current = typedRule
            while (current.selectorExpression is KtDotQualifiedExpression) {
                current = current.selectorExpression as KtDotQualifiedExpression
            }
            val nre = current.selectorExpression as KtNameReferenceExpression
            val cl = nre.resolvedReferenceTarget as ClassDescriptor
            val n = cl.tsTopLevelName
            -n
            -".INSTANCE"
            out.addImport(cl, n)
        }
    )

    handle<KtSecondaryConstructor> {
        val resolved = typedRule.resolvedConstructor ?: return@handle
        -(typedRule.visibilityModifier() ?: "public")
        -" static "
        -resolved.tsConstructorName
        -typedRule.typeParameterList
        -typedRule.valueParameterList
        -" {\n"
        val parent = typedRule.parentOfType<KtClassBody>()!!.parentOfType<KtClass>()!!.resolvedClass!!
        withReceiverScope(parent, "result") { outName ->
            -"let "
            -outName
            -" = new "
            typedRule.getDelegationCallOrNull()?.let {
                -parent.tsTopLevelName
                -it.typeArgumentList
                -it.valueArgumentList
                -";\n"
            }
            val b = typedRule.bodyExpression
            if (b is KtBlockExpression) {
                -b.allChildren.toList().drop(1).dropLast(1)
            } else {
                -b
            }
            -"\nreturn "
            -outName
            -";\n"
        }
        -"}"
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

fun KtElement.addPostAction(action: () -> Unit) {
    weakKtClassPostActions.getOrPut(this) { ArrayList() }.add(action)
}

val ConstructorDescriptor.tsConstructorName: String?
    get() = this.annotations
        .find { it.fqName?.asString()?.substringAfterLast('.') == "JsName" }
        ?.allValueArguments
        ?.entries
        ?.firstOrNull()
        ?.value
        ?.value
        ?.toString() ?: ("constructor" + this.valueParameters.joinToString {
        it.type.getJetTypeFqName(false).split('.').joinToString("").filter { it.isLetter() }
    })