package leetcode

import gears.puzzle

fun main() {
    puzzle { fizzBuzz(3) }
    puzzle { fizzBuzz(5) }
    puzzle { fizzBuzz(15) }
}

fun fizzBuzz(n: Int): List<Any> {
    return (1..n).map {
        val a = it % 3 == 0
        val b = it % 5 == 0
        when {
            a && b -> "FizzBuzz"
            a -> "Fizz"
            b -> "Buzz"
            else -> it
        }
    }.toList()
}

