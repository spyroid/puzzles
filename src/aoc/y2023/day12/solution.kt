package aoc.y2023.day12

import gears.puzzle

private fun main() {
    puzzle("1 & 2") { hotSprings(inputLines()) }
}

private fun hotSprings(input: List<String>): Any {
    val springs = input.mapTo(mutableListOf()) { line ->
        val (row, countsStr) = line.split(" ", limit = 2)
        val counts = countsStr.split(",").map(String::toInt)
        Spring(row, counts)
    }
    val part1 = springs.sumOf { (row, counts) -> variants(row, counts) }
    val part2 = springs.map(Spring::unfold).sumOf { (row, counts) -> variants(row, counts) }
    return part1 to part2
}

private data class Spring(val row: String, val brokenCounts: List<Int>) {
    fun unfold() = Spring(
        (0 until 5).joinToString("?") { row },
        (0 until 5).flatMap { brokenCounts }
    )
}

private fun variants(row: String, brokenGroupLengths: List<Int>): Long {
    val cache = mutableMapOf<Pair<Int, Int>, Long>()

    fun brokenGroupPossible(from: Int, to: Int): Boolean = when {
        to > row.length -> false
        to == row.length -> (from until to).all { row[it] != '.' }
        else -> (from until to).all { row[it] != '.' } && row[to] != '#'
    }

    fun compute(i: Int, j: Int): Long {
        fun computeWorking() = compute(i + 1, j)
        fun computeBroken(): Long {
            if (j == brokenGroupLengths.size) return 0
            val endGroupIdx = i + brokenGroupLengths[j]

            if (!brokenGroupPossible(i, endGroupIdx)) return 0
            if (endGroupIdx == row.length) return if (j == brokenGroupLengths.size - 1) 1 else 0
            return compute(endGroupIdx + 1, j + 1)
        }

        if (i == row.length) return if (j == brokenGroupLengths.size) 1 else 0
        cache[Pair(i, j)]?.let { return it }

        return when (val c = row[i]) {
            '.' -> computeWorking()
            '#' -> computeBroken()
            else -> computeWorking() + computeBroken()
        }.also { cache[Pair(i, j)] = it }
    }
    return compute(0, 0)
}
