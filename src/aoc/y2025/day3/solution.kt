package aoc.y2025.day3

import gears.puzzle

fun main() {
    puzzle {
        lobby(inputLines())
    }
}

private fun lobby(input: List<String>): Any {
    fun maximizer(batteries: String, size: Int): Long {
        val numbers = mutableListOf<Char>()
        var remaining = batteries
        repeat(size) {
            val s = remaining.dropLast(size - numbers.size - 1)
            val idx = s.indices.maxBy { s[it] }
            numbers.add(s[idx])
            remaining = remaining.drop(idx + 1)
        }
        return numbers.joinToString("").toLong()
    }

    val part1 = input.sumOf { maximizer(it, 2) }
    val part2 = input.sumOf { maximizer(it, 12) }

    return part1 to part2
}
