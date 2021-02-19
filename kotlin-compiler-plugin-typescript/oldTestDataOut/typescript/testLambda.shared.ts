// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: testLambda.shared.kt
// Package: com.test.lambda

//! Declares com.test.lambda.TestClass
export class TestClass {
    public constructor() {
        this.item = 0;
    }
    
    public item: Int;
    
    public test(action: (() => Unit)): void {
        action();
    }
    public testRec(action: ((a: Int) => Unit)): void {
        action(2);
    }
}

//! Declares com.test.lambda.main
export function main(): void {
    const theAnswer = xAnyApply<TestClass>(new TestClass(), (this_: TestClass): Unit => {
            this_.item = 42;
    });
    
    const myLambda: ((a: Int) => String) = (it: Int): String => `Number: ${it}`;
    
    xAnyLet<TestClass, Unit>(theAnswer, (it: TestClass): Unit => {
            return println(it);
    });
    xAnyLet<Int, Unit>(32, (it: Int): Unit => {});
    xAnyLet<Int, Unit>(32, (it: Int): Unit => {
            return println(it);
    });
    xAnyLet<Int, Unit>(32, (it: Int): Unit => {
            println(it);
            println(it);
            return;
    });
    
    const lambda: (() => Unit) = (): Unit => {
        println("Hi");
        return;
    };
    
    const lambda2: ((a: Int, b: String) => Unit) = (i: Int, s: String): Unit => {
        println(s.plus(i.toString()));
        return;
    };
    
    const lambda3: (() => Unit) = (): Unit => {
        if (theAnswer.item.compareTo(22)) {
            println("Hello!");
        } else {
            println("WRONG");
        }
    };
    
    
    const view: View = new View();
    
    view.setOnClickListener((it: View): Unit => {
            println(`Hello!  I am ${it}`);
    });
    const altCallback = (it: View): Unit => {
        return println(`Hello!  I am also ${it}`);
    };
    
    view.setOnClickListener(altCallback);
}
