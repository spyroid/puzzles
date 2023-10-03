package aoc.y2017.day13

import gears.puzzle

private fun main() {
    puzzle { scanner(inputLines()) }
}

private fun scanner(input: List<String>): Any {
    val all = input.map { it.split(": ").map { a -> a.toInt() } }.map { Layer(it.first(), it.last()) }
    val part1 = all.filter { it.caughtAt(0) }.sumOf { it.severity }
    val part2 = generateSequence(0, Int::inc).filter { time -> all.none { it.caughtAt(time) } }.first()
    return part1 to part2
}

private data class Layer(val depth: Int, val range: Int = 0) {
    fun caughtAt(time: Int) = (time + depth) % ((range - 1) * 2) == 0
    val severity = depth * range
}
