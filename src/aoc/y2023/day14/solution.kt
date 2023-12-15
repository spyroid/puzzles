package aoc.y2023.day14

import gears.puzzle

private fun main() {
    puzzle("1 & 2") { parabolicReflectorDish(inputLines()) }
}

private fun parabolicReflectorDish(input: List<String>): Any {
    val seen = mutableMapOf<List<String>, Long>()
    var cur = input
    var times = 0L
    val n = 1_000_000_000L
    while (cur !in seen) {
        seen[cur] = times
        cur = cur.cycle()
        if (++times == n) break
    }
    val offset = seen[cur] ?: 0
    val loop = seen.size - offset
    val afterLoop = (n - offset) % loop
    val p2 = (1..afterLoop).fold(cur) { total, _ -> total.cycle() }
    return input.packUp().score() to p2.score()
}

private fun List<String>.cycle() = (1..4).fold(this) { acc, _ -> acc.packUp().rotateRight() }
private fun List<String>.packUp() = this.first().indices.map { i ->
    column(i).split('#').joinToString("#") { it.toCharArray().sortedArrayDescending().joinToString("") }
}.let { it.first().indices.map { i -> it.column(i) } }

private fun List<String>.rotateRight(): List<String> = first().indices.map { this.column(it).reversed() }
private fun List<String>.column(c: Int): String = buildString { this@column.forEach { s -> append(s[c]) } }
private fun List<String>.score() = mapIndexed { i, s -> s.count { it == 'O' } * (this.size - i) }.sum()
