package aoc.y2023.day15

import gears.puzzle

private fun main() {
    puzzle("1 & 2") { lensLibrary(input()) }
}

private fun lensLibrary(input: String): Any {
    val lenses = input.split(",")
    val part1 = lenses.sumOf { it.hash() }
    val part2 = buildMap<String, Int>() {
        lenses.onEach { s -> if (s.contains('-')) remove(s.substringBefore('-')) else s.split('=').let { (a, b) -> this[a] = b.toInt() } }
    }.entries.groupBy { it.key.hash() }
        .map { (it.key + 1) * it.value.foldIndexed(0) { i, acc, lens -> acc + (i + 1) * lens.value } }
        .sum()

    return part1 to part2
}

private fun String.hash() = this.fold(0) { acc, c -> (acc + c.code) * 17 % 256 }