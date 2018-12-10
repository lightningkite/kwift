/**
 * See https://kotlinlang.org/docs/reference/control-flow.html
 */

fun main(args: Array<String>) {
    cases(x = 1)
    cases(x = 2)
    cases(x = 3)
    cases(x = 4)
}

fun cases(x: Int) {
    when (x) {
        1 -> print("x == 1")
        2 -> print("x == 2")
        else -> {
            print("x is neither 1 nor 2")
            print("x might be something more")
        }
    }

    when (x) {
        0, 1 -> print("x == 0 or x == 1")
        else -> print("otherwise")
    }
}
