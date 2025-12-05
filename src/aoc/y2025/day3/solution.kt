package aoc.y2025.day3

import gears.puzzle

fun main() {
    puzzle {
        lobby(inputLines())
    }
}

private fun lobby(input: List<String>): Any {
    fun maximizer(batteries: String, size: Int): Long {
        var numbers = ""
        var remaining = batteries
        repeat(size) { i ->
            remaining.dropLast(size - i - 1).let { frame ->
                val idx = frame.indices.maxBy { frame[it] }
                numbers += frame[idx]
                remaining = remaining.drop(idx + 1)
            }
        }
        return numbers.toLong()
    }

    val part1 = input.sumOf { maximizer(it, 2) }
    val part2 = input.sumOf { maximizer(it, 12) }

    return part1 to part2
}
