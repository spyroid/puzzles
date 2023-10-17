package aoc.y2018.day5

import gears.puzzle
import kotlin.math.abs

private fun main() {
    puzzle { reduction(inputLines().first()) }
    puzzle { reduction2(inputLines().first()) }
}

private fun reduction2(input: String) = input.toSet()
    .filter { it in 'A'..'Z' }
    .minOf { c -> reduction(input.filter { it != c && it != c + 32 }) }

private fun reduction(input: String): Int {
    val all = input.toMutableList()
    while (true) {
        var f = false
        for (i in 0..<all.lastIndex - 1) {
            if (abs(all[i] - all[i + 1]) == 32) {
                repeat(2) { all.removeAt(i) }
                f = true
                break
            }
        }
        if (!f) break
    }
    return all.size
}
