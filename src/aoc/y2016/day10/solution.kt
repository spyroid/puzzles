package aoc.y2016.day10

import gears.puzzle

private fun main() {
    puzzle { bots(inputLines("input.txt")) }
}

private fun bots(lines: List<String>): Pair<String, Int> {
    val bots = mutableMapOf<String, MutableList<Int>>()
    val outs = mutableMapOf<String, MutableList<Int>>()
    val connections = mutableMapOf<String, List<Pair<String, String>>>()
    val pipe = ArrayDeque<String>()

    fun add(bot: String, v: Int) {
        bots.putIfAbsent(bot, mutableListOf())
        bots[bot]?.also {
            it.add(v)
            if (it.size == 2) pipe.add(bot)
        }
    }

    fun send(o: Pair<String, String>, v: Int) {
        if (o.first == "bot") add(o.second, v) else outs.compute(o.second) { _, l -> (l ?: mutableListOf()).also { it.add(v) } }
    }

    for (line in lines) {
        line.split(" ").also {
            if (it[0] == "value") add(it[5], it[1].toInt()) else connections[it[1]] = listOf(it[5] to it[6], it[10] to it[11])
        }
    }

    var bot = "0"

    while (pipe.isNotEmpty()) {
        val b = pipe.removeLast()
        val (low, high) = bots[b]?.sorted() ?: throw Error()
        if (low == 17 && high == 61) bot = b
        connections[b]?.also { (p1, p2) ->
            send(p1, low)
            send(p2, high)
        }
    }
    val mul = outs.filterKeys { it in setOf("0", "1", "2") }.values.map { it.first() }.fold(1) { acc, v -> acc * v }

    return Pair(bot, mul)
}
