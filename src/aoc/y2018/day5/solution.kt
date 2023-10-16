package aoc.y2018.day5

import gears.puzzle
import kotlin.math.abs

private fun main() {
    puzzle { reduction(inputLines().first()) }
}

private fun reduction(input: String): Any {
    val all = input.toMutableList()

    println('a' - 'A')
    while (true) {
        var f = false
        for (i in 0..<all.lastIndex - 1) {
            if (abs(all[i] - all[i + 1]) == 32) {
                all.removeAt(i)
                all.removeAt(i)
                f = true
                break
            }
        }
        if (!f) break
    }
    return all.size
}
