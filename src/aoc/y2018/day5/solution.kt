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
    var all = input.toMutableList()
    do {
        for (i in (0..<all.lastIndex)) {
            if (abs(all[i] - all[i + 1]) == 32) {
                all[i] = 0.toChar()
                all[i + 1] = 0.toChar()
            }
        }
        val all2 = all.filter { it != 0.toChar() }.toMutableList()
        if (all2.size == all.size) break
        all = all2
    } while (true)
    return all.size
}
