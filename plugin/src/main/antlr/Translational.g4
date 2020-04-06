grammar Translational;

@header {
   package org.jetbrains.kotlin;
}

file
: fileEntry (';' fileEntry )* ';'? EOF
| EOF
;

fileEntry
: parserRuleOption
| parserRuleMacro
| parserRuleMacroUsage
| directiveMacroDefinition
;

directiveMacroDefinition
: '#' name=PathPart '->' parserRuleOption
;

parserRuleMacroUsage
: PathPart '(' (parameterExpression '=' expression ','?)* ')'
;

parserRuleMacro
: 'rulemacro' name=PathPart 'on' parserRuleOption
;

parserRuleOption
: PathPart ('when' condition+)? ('priority' Index)? '->' directive+
;

condition
: conditionExists
| conditionEquals
| conditionAny
| conditionAll
;
conditionExists
: expression
;
conditionEquals
: expression '==' expression
;
conditionAny
: 'any' '(' (condition ','?)+ ')'
;
conditionAll
: 'all' '(' (condition ','?)+ ')'
;

expression
: path
| constantExpression
| parameterExpression
| elvisExpression
| parenthesizedExpression
;

parenthesizedExpression
: '(' expression ')'
;

elvisExpression
: 'first' '(' (expression ','?)+ ')'
;

parameterExpression
: '@' PathPart
;

path
: pathSection ('.' pathSection)*
;

pathSection
: PathPart ('[' Index ']')?
;

constantExpression
: String
| Index
;

directive
: expression
| directiveBlock
| directiveRepeat
| directiveLoop
| directiveIf
| directiveSet
| directiveMacro
| DirectivePass
;
directiveMacro
: '#' PathPart '(' (parameterExpression '=' expression ','?)* ')'
;
directiveBlock
: '(' (directive ','?)* ')'
;
directiveRepeat
: directiveBlock '*' (expression)?
;
directiveLoop
: 'for' parameterExpression 'in' expression 'do' directive
;
directiveIf
: 'if' condition 'then' directive ('else' directive)?
;
directiveSet
: expression '=' expression
;

PathPart: [a-zA-Z]+;
Index: [0-9]+;
String: '"' ('\\"' | ~'"')* '"';
DirectivePass: 'continue';

SPACE
    : [ \t\r\n] -> skip
    ;

COMMENT
    :   '/*' .*? '*/' -> skip
    ;

LINE_COMMENT
    :   '//' ~[\r\n]* -> skip
    ;

/*
Examples

someRule do path;
someRule
    when subPart
    do subPart;
someRule
    when $global == "Hello"
    do subpart;
macro replaceType() on postfixExpression
    when suffix.callSuffix
    do x;
replaceType(a, b, c);
someRule
    do partA partB.thing "string" (partanother "hello")* indexed[1]
    ;


*/
