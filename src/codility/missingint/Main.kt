package codility.missingint

import readLocal
import kotlin.system.measureTimeMillis

fun solve(input: List<String>): Int {
    val arr = intArrayOf(-11, -2, -3)
    return solution(arr)
}

fun solution(A: IntArray): Int {
    val set = A.filter { it > 0 }.toSet()
    var counter = 1
    while (counter in set) counter++
    return counter
}

fun main() {
    measureTimeMillis { print("⭐️ Result: ${solve(listOf())}") }
        .also { time -> println(" in $time ms") }
}

class Main {}
