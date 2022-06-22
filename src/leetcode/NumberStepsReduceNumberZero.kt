package leetcode

import runWithTime

fun main() {
    runWithTime { numberOfSteps(123) }
}

fun numberOfSteps(num: Int): Int {
    var v = num
    var steps = 0
    while (v > 0) {
        steps += 1.also { if (v % 2 == 0) v /= 2 else v -= 1 }
    }
    return steps
}
