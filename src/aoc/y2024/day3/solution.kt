package aoc.y2024.day3

import gears.puzzle

fun main() {
    puzzle { mullItOver(input()) }
}

private fun mullItOver(input: String): Any {
    fun search(re: Regex) = re.findAll(input).map { it.range.first to it.groupValues.first() }
    val re = Regex("""\d+""")
    fun calc(seq: Sequence<Pair<Int, String>>) = seq.sortedBy { it.first }.map { it.second }
        .fold(true to 0) { acc, e ->
            when {
                e.contains("do(") -> true to acc.second
                e.contains("don") || !acc.first -> false to acc.second
                else -> true to acc.second + re.findAll(e).fold(1) { acc, v -> acc * v.value.toInt() }
            }
        }.second

    val part1 = search(Regex("""mul\(\d+,\d+\)"""))
    val part2 = part1.plus(search(Regex("""do\(\)|don't\(\)""")))
    return calc(part1) to calc(part2)
}