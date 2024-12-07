package aoc.y2024.day7

import gears.puzzle

fun main() {
    puzzle { bridgeRepair(inputLines()) }
}

private fun bridgeRepair(input: List<String>): Any {
    val equations = input.map { it.split(""" |: """.toRegex()).map { it.toLong() } }
    val operations = listOf<(Long, Long) -> Long>(
        { a, b -> a + b },
        { a, b -> a * b },
        { a, b -> (a.toString() + b.toString()).toLong() },
    )

    var part2 = 1
    fun calc(eq: List<Long>, i: Int, res: Long): Boolean {
        if (i == eq.size) return res == eq[0]
        return operations.dropLast(part2).any { func -> calc(eq, i + 1, func(res, eq[i])) }
    }

    val p1 = equations.sumOf { if (calc(it, 2, it[1])) it.first() else 0 }

    part2 = 0
    val p2 = equations.sumOf { if (calc(it, 2, it[1])) it.first() else 0 }
    return p1 to p2
}
