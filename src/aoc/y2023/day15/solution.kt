package aoc.y2023.day15

import gears.puzzle

private fun main() {
    puzzle("1 & 2") { lensLibrary(input()) }
}

private fun lensLibrary(input: String): Any {
    val data = input.split(",")
    val part1 = data.sumOf { Key(it).hashCode() }

    val part2 = buildMap<Key, Int>() {
        data.onEach { s -> if (s.contains('-')) remove(Key(s.substringBefore('-'))) else s.split('=').let { (a, b) -> this[Key(a)] = b.toInt() } }
    }.entries.groupBy { it.key.hashCode() }
        .map { (it.key + 1) * it.value.foldIndexed(0) { i, acc, lens -> acc + (i + 1) * lens.value } }
        .sum()

    return part1 to part2
}

private data class Key(val id: String) {
    override fun hashCode() = id.fold(0) { acc, c -> (acc + c.code) * 17 % 256 }
}