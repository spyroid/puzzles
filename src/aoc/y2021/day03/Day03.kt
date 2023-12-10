package aoc.y2021.day03

import gears.puzzle

fun main() {
    puzzle { part1(inputLines("input.txt")) }
    puzzle { part2(inputLines("input.txt")) }
}

private fun part1(seq: List<String>): Int {
    val counts = IntArray(seq.first().length) { idx ->
        if (seq.sumOf { (if (it[idx].digitToInt() == 0) -1L else 1L) } > 0) 1 else 0
    }

    val gamma = counts.fold(0) { acc, el -> (acc shl 1) + el }
    val epsilon = counts.fold(0) { acc, el -> (acc shl 1) + el xor 1 }
    return gamma * epsilon
}

private fun part2(seq: List<String>): Int {

    fun findMeasure(seq: List<String>, type: Type, idx: Int = 0): Int {
        val group = seq.groupBy { it[idx].digitToInt() }
            .entries
            .sortedWith(compareBy({ it.value.size * -type.order }, { it.key * -type.order }))
            .first().value

        if (group.size == 1) return group.first().toInt(2)
        return findMeasure(group, type, idx + 1)
    }

    val oxy = findMeasure(seq, Type.OXY)
    val co2 = findMeasure(seq, Type.CO2)

    return oxy * co2
}

private enum class Type(val order: Int) {
    OXY(1), CO2(-1)
}
