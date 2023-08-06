package aoc.y2015.day19

import gears.puzzle

private fun main() {
    puzzle { medicine(linesFrom("input.txt")) }
}

private fun medicine(lines: List<String>): Int {

    val map = lines.takeWhile { it.isNotEmpty() }
        .map { it.split(" => ") }
        .map { it.first() to it.last() }
        .groupBy({ it.first }, { it.second })

    var formula = lines.last()

    val parts = mutableListOf<String>()

    while (formula.isNotEmpty()) {
        val k = map.keys.firstOrNull { formula.startsWith(it) } ?: formula.first().toString()
        parts.add(k)
        formula = formula.drop(k.length)
    }

    val molecules = buildSet {
        parts.onEachIndexed { i, p ->
            for (v in map.getOrDefault(p, listOf())) {
                parts.toMutableList().apply { set(i, v) }.joinToString("").also { add(it) }
            }
        }
    }

    return molecules.size
}
