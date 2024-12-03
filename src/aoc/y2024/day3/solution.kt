package aoc.y2024.day3

import gears.puzzle

fun main() {
    puzzle { mullItOver(input()) }
}

private fun mullItOver(input: String): Any {
    val re1 = Regex("""\d+""")
    fun calc(re: String) = re.toRegex().findAll(input).map { it.groupValues.first() }
        .fold(true to 0) { acc, e ->
            when {
                e.contains("do(") -> true to acc.second
                e.contains("don") || !acc.first -> false to acc.second
                else -> true to acc.second + re1.findAll(e).fold(1) { acc, v -> acc * v.value.toInt() }
            }
        }.second

    val part1 = """mul\(\d+,\d+\)"""
    return calc(part1) to calc("""${part1}|do\(\)|don't\(\)""")
}