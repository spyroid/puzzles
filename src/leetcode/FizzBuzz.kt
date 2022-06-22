package leetcode

import runWithTime

fun main() {
    runWithTime { fizzBuzz(3) }
    runWithTime { fizzBuzz(5) }
    runWithTime { fizzBuzz(15) }
}

fun fizzBuzz(n: Int) = (1..n).asSequence().map {
    val a = it % 3 == 0
    val b = it % 5 == 0
    if (a && b) {
        "FizzBuzz"
    } else if (a) {
        "Fizz"
    } else if (b) {
        "Buzz"
    } else {
        it.toString()
    }
}.toList()

