package leetcode

import runWithTime

fun main() {
    runWithTime { evalRPN(arrayOf("2", "1", "+", "3", "*")) }
    runWithTime { evalRPN(arrayOf("4", "13", "5", "/", "+")) }
    runWithTime { evalRPN(arrayOf("10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+")) }
}

fun ArrayDeque<Int>.applyOp(op: (Int, Int) -> Int) = removeFirst().also { addFirst(op(removeFirst(), it)) }

fun evalRPN(tokens: Array<String>): Int {
    return ArrayDeque<Int>(tokens.size).apply {
        tokens.forEach { c ->
            when (c) {
                "+" -> applyOp(Int::plus)
                "-" -> applyOp(Int::minus)
                "/" -> applyOp(Int::div)
                "*" -> applyOp(Int::times)
                else -> addFirst(c.toInt())
            }
        }
    }.first()
}
