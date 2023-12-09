package aoc.y2023.day5

import gears.findLongs
import gears.puzzle
import java.lang.System.lineSeparator

private fun main() {
    puzzle { scratch(input()) }
}

private fun scratch(input: String): Any {
    val blocks = input.split("${lineSeparator()}${lineSeparator()}")
    val seeds = blocks.first().findLongs()
    val mappers = blocks.drop(1).map { Mapper(it.split(lineSeparator()).drop(1)) }
    fun location(v: Long) = mappers.fold(v) { vv, mapper -> mapper.remap(vv) }

    val part1 = seeds.minOf { location(it) }

    val seeds2 = seeds.windowed(2, 2).map { (a, b) -> a until b + a }
    val part2 = seeds2.minOf { r -> r.minOf { location(it) } }
    return part1 to part2
}

private data class Mapper(private val lines: List<String>) {
    private val ranges = lines.map { s -> s.findLongs().let { Range(it[1], it[0], it[2]) } }
    fun remap(v: Long) = ranges.map { it.remap(v) }.firstNotNullOfOrNull { it } ?: v
    data class Range(val a: Long, val b: Long, val len: Long) {
        fun remap(v: Long) = if (v >= a && v < a + len) b + (v - a) else null
    }
}
