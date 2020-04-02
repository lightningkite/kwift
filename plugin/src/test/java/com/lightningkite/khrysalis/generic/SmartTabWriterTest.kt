package com.lightningkite.khrysalis.generic

import org.junit.Assert.*
import org.junit.Test
import java.lang.StringBuilder
import kotlin.system.measureTimeMillis

class SmartTabWriterTest {

    val testData = """
class X {
public TerminalNode addChild(Token matchedToken) {
TerminalNodeImpl t = new TerminalNodeImpl(matchedToken);
addChild(t);
t.parent = this;
return t;
}

public ErrorNode addErrorNode(Token badToken) {
ErrorNodeImpl t = new ErrorNodeImpl(badToken);
addChild(t);
t.parent = this;
return t;
}

@Override
/** Override to make type more specific */
public ParserRuleContext getParent() {
return (ParserRuleContext)super.getParent();
}

@Override
public ParseTree getChild(int i) {
return children!=null && i>=0 && i<children.size() ? children.get(i) : null;
}

public <T extends ParseTree> T getChild(Class<? extends T> ctxType, int i) {
if ( children==null || i < 0 || i >= children.size() ) {
return null;
}

int j = -1; // what element have we found with ctxType?
for (ParseTree o : children) {
if ( ctxType.isInstance(o) ) {
j++;
if ( j == i ) {
return ctxType.cast(o);
}
}
}
return null;
}
}
                """.trimIndent()

    @Test fun lemmeSee(){
        buildString {
            with(SmartTabWriter(this)){
                appendln(testData)
            }
        }.let { println(it) }
    }

    object Sink: Appendable {
        val builder = StringBuilder(2048)
        fun reset() {
            builder.clear()
        }

        override fun append(p0: CharSequence?): java.lang.Appendable {
            builder.append(p0)
            return this
        }

        override fun append(p0: CharSequence?, p1: Int, p2: Int): java.lang.Appendable {
            builder.append(p0, p1, p2)
            return this
        }

        override fun append(p0: Char): java.lang.Appendable {
            builder.append(p0)
            return this
        }

    }

    @Test fun performanceCheck(){
        val normal = measureTimeMillis {
            repeat(10000){
                Sink.reset()
                Sink.append(testData)
            }
        }
        println("Untabbed: $normal")
        val tabWriter = SmartTabWriter(Sink)
        val tabbed = measureTimeMillis {
            repeat(10000){
                Sink.reset()
                tabWriter.append(testData)
            }
        }
        println("Tabbed: $tabbed")
        println("ratio: ${tabbed.toDouble()/normal }")
    }
}
