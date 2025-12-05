package aoc.y2025.day3

import gears.puzzle

fun main() {
    puzzle {
        lobby(inputLines())
    }
}

private fun lobby(input: List<String>): Any {
    fun maximizer(batteries: String, size: Int) = (0..<size)
        .fold(Pair("", batteries)) { acc, i ->
            acc.second.dropLast(size - i - 1).let { frame ->
                val idx = frame.indices.maxBy { frame[it] }
                Pair(acc.first + frame[idx], acc.second.drop(idx + 1))
            }
        }.first.toLong()

    val part1 = input.sumOf { maximizer(it, 2) }
    val part2 = input.sumOf { maximizer(it, 12) }

    return part1 to part2
}
