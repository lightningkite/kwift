# Translational Language

The translational language was created to aid in source-to-source transpiling.

If you are unfamiliar with language grammar, you're unlikely to be able to use this.  The language is directly based on ANTLR G4 grammar files.

Everything is set up to create increasing optimizations, and thus, the general structure looks something like this:

- A given parser rule (from the source language's G4 file) has a sorted list of options for handling it
- Each option has conditions that must be met in order to run.
- Each option has a 'priority' level, which is either explicitly stated or inferred by the number of conditions multiplied by 100.
- When translating an element of this rule, handlers are checked in priority order (highest to lowest) and each handler is checked for viability.  The first handler found whose conditions are satisfied is invoked, and the rule is then considered handled.
- If no usable handler is found, the subrules and tokens will be emitted separated by spaces.

General rule: don't write to properties unless you absolutely must.  It's likely this feature will be removed in the future to allow parallelization.  It's bad habit anyways.

## Syntax Overview

*Whitespace is ignored.*

```
//This is a comment.
/*This is also a comment.*/
//I'm using <thing> to indicate placeholders for another kind of expression.
ruleInSourceLanguageAntlr4Grammar
    when <condition>
        <and condition>
        <and condition>
    priority <integer>
    do <directive>
        <directive>
        <directive>
        <directive>
    ;
ruleInSourceLanguageAntlr4Grammar do <directive>;
```

Any expression in the actions section is interpreted as a request to emit the result of the expression.

### Conditions

The `when` section is optional; if omitted, this action will always take place if the handler is reached.

The following conditions are possible:

- `<expression>` - Checks if the expression is not null
- `<expression> == <expression>` - Checks if the expressions are equal
- `all(<condition> <condition> ...)` - Checks if every condition listed passes
- `any(<condition> <condition> ...)` - Checks if any of the conditions pass

#### Priority

The `priority` section is also optional; if omitted, the handler's priority will be:

- The total priority level of all conditions within `all()` 
- The smallest priority level of all conditions within `any()`
- An null check condition will be worth 100
- An equality condition will be worth 150

### Expressions

An expression returns a value of some sort.

The following expressions are possible:

- `subrule.subrule.subrule` - access sub-rules
- `subrule[0].subrule[1].subrule[2]` - access the nth instance of the sub-rules
- `subrule.subrule.property` - identifiers that aren't rule references work as properties, for which the built-ins are described below
- `parent` - Accesses the parent rule of the current rule
- `property` - You can access properties on the current rule, writing and reading
- `global.property` - Global properties are available via 'global', probably shouldn't use them though
- `"Constant String"`
- `431` - Integers
- `(123)` - It's OK to use parens around expressions
- `first(property, anotherProperty, "constant")` - Picks the first property that isn't null

#### Built-in Properties

Parser rules and tokens have some built-in properties.  Most are still planned.

- `rule.text` - Yields the raw text of the rule or token

Planned rules for Kotlin, pending full type-analysis

- `expressionRelatedRule.type` - Yields the type of the expression, if findable.
    - `type.fullyQualifiedName` - Fully-qualified name of the type
    - `type.name` - Short name of the type
    - `type.arguments` - Indexed, yields the ordered type arguments of the type as types.
- `identifier.fullyQualifiedName` - Yields the fully-qualified name of an identifier
- `identifier.declarationObject` - Yields the declaration of the given object

### Directives

A directive does some action, usually affecting output.

- `<expression>` - Emits the given expression:
    - If the expression is a subrule, then it runs the options for that rule to emit stuff.
    - If the expression is a value, then it will be turned into a string and written.
    - If the expression is null, it is ignored.
- `(<directive>, <directive>, ...)` - A block of directives, runs all of them.  Organizational, primarly.
- `(<directive>, <directive>, ...)*` - Repeats the directive, incrementing the offset of references.  Used to output lists, like:
    - `thing[0] ("," thing[1])*` would print out all of the subrule instances of `thing` separated by commas.
    - Don't try nesting these right now.  I'm pretty sure it wouldn't do what you think.
- `if <condition> then <directive>` - Runs the directive only if the condition passes
- `if <condition> then <directive> else <directive>` - Runs the first directive if the condition passes, otherwise the second one.
- `<expression> = <expression>` - For settable expressions, sets the left expression to the value to the right.
