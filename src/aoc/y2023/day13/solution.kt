package aoc.y2023.day13

import gears.puzzle
import java.lang.System.lineSeparator
import kotlin.math.min

private fun main() {
    puzzle("1") { pointIncidence(input(), 0) }
    puzzle("2") { pointIncidence(input(), 1) }
}

private fun pointIncidence(input: String, smudges: Int): Any {
    val blocks = input.split("${lineSeparator()}${lineSeparator()}").map { it.split(lineSeparator()) }

    fun diffs(a: String, b: String) = a.indices.count { a[it] != b[it] }
    fun mirrorAt(row: Int, size: Int, block: List<String>) =
        (0..<size).sumOf { diffs(block[row - 1 - it], block[row + it]) } == smudges

    fun rowScore(block: List<String>) = (1..<block.size).singleOrNull { mirrorAt(it, min(it, block.size - it), block) } ?: 0
    fun columnScore(block: List<String>) = rowScore(block.transpose())
    fun score(block: List<String>) = rowScore(block) * 100 + columnScore(block)

    return blocks.sumOf { score(it) }
}

private fun List<String>.transpose(): List<String> = this[0].indices.map { this.column(it) }
private fun List<String>.column(c: Int): String = buildString { this@column.forEach { s -> append(s[c]) } }
