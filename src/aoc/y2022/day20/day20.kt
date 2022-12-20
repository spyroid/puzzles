package aoc.y2022.day20

import gears.puzzle

fun main() {
    puzzle("t1") { part1(linesFrom("test.txt")) }
    puzzle("1") { part1(linesFrom("input.txt")) }
    puzzle("t2") { part2(linesFrom("test.txt")) }
    puzzle("2") { part2(linesFrom("input.txt")) }
}

private fun part1(lines: List<String>): Long {
    return decrypt(lines.map { it.toLong() }, 1, 1)
}
private fun part2(lines: List<String>): Long {
    return decrypt(lines.map { it.toLong() }, 10, 811589153)
}

private fun decrypt(input: List<Long>, mixes: Int, decryptionKey: Int): Long {
    val amounts = input.mapIndexed { index, amount -> index to amount * decryptionKey }.toMutableList()

    repeat(mixes) {
        (0 until amounts.size).forEach { index -> move(index, amounts) }
    }

    val startIndex = amounts.indexOfFirst { (_, value) -> value == 0L }

    return listOf(1000L, 2000L, 3000L)
        .map { endIndex -> amounts[findIndex(startIndex, endIndex, amounts.size)] }
        .sumOf { (_, amount) -> amount }
}

private fun move(index: Int, values: MutableList<Pair<Int, Long>>) {
    val indexFrom = values.indexOfFirst { (initialIndex) -> initialIndex == index }
    val value = values[indexFrom]
    val (_, amount) = value

    if (amount == 0L) return

    values.removeAt(indexFrom)
    val indexTo = findIndex(indexFrom, amount, values.size)
    values.add(indexTo, value)
}

private fun findIndex(startIndex: Int, endIndex: Long, size: Int): Int = (startIndex + endIndex).mod(size)
