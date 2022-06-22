package other

import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {
//    runWithTime { other.findNumbers2(listOf(1, 1, 2)) }
//    runWithTime { other.findNumbers2(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 8)) }
//    runWithTime { other.findNumbers2(listOf(7, 17, 11, 1, 23)) }

    val size = 10_000_000
    for (i in 1..5) {
        val huge = IntArray(size) { _ -> Random.nextInt(0, 1000 - 1) }.toList()
        measureTimeMillis { findNumbers2(huge) }.also { println("%,d".format(size) + " => ${it}ms") }
    }
}

fun findNumbers(input: List<Int>) = input.groupingBy { it }.eachCount().let { stats -> input.filter { stats[it * 2] == 1 } }

fun findNumbers2(input: List<Int>): List<Int> {
    val stats = IntArray(2000)
    for (i in input) stats[i]++
    return input.filter { stats[it * 2] == 1 }
}
