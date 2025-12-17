package other

import gears.overlaps
import gears.puzzle

fun main() {
    puzzle { palindromic("attract") }
}

private fun palindromic(input: String): Any {
    val all = sequence {
        for (start in input.indices) {
            for (i in start..input.lastIndex) {
                for (j in i..input.lastIndex) {
                    yield(Palindrome(input.substring(start, i) + input[j], IntRange(start, j)))
                }
            }
        }
    }
        .filter { it.str == it.str.reversed() }
        .sortedByDescending { it.str.length }
        .toList()

    return all.flatMap { p1 -> all.map { p2 -> Pair(p1, p2) } }.first { (p1, p2) -> !p1.range.overlaps(p2.range) }
}

private data class Palindrome(val str: String, val range: IntRange) {}