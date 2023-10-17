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
    var all = input
    do {
        val idx = mutableSetOf<Int>()
        for (i in (0..<all.lastIndex)) {
            if (abs(all[i] - all[i + 1]) == 32 && !idx.contains(i)) idx.add(i).also { idx.add(i + 1) }
        }
        all = all.filterIndexed { i, _ -> !idx.contains(i) }
    } while (idx.isNotEmpty())
    return all.length
}
