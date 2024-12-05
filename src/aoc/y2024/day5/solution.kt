package aoc.y2024.day5

import gears.puzzle

fun main() {
    puzzle { printQueue(input()) }
}

private fun printQueue(input: String): Any {
    val (map, list) = input.split("\n\n").let { (a, b) ->
        val m = a.lines().map { it.split("|").map { it.toInt() }.let { it.first() to it.last() } }
            .groupBy({ it.first }, { it.second }).mapValues { it.value.toSet() }
        val l = b.lines().map { it.split(",").map { it.toInt() }.toMutableList() }
        m to l
    }

    fun MutableList<Int>.fix(): Int {
        var idx = 0
        while (true) {
            val a = indices.map {
                it to takeLast(size - it - 1).toSet() - (map[get(it)] ?: emptySet())
            }.find { it.second.isNotEmpty() }?.let { (a, b) -> a to b.last() }
            if (a == null) return idx
            this[a.first] = this[indexOf(a.second)].also { this[indexOf(a.second)] = this[a.first] }
            idx++
        }
        return idx
    }

    fun MutableList<Int>.mid() = this[size / 2]

    return list.map { ints ->
        ints.fix().let { if (it == 0) ints.mid() to 0 else 0 to ints.mid() }
    }.unzip().let { it.first.sum() to it.second.sum() }
}
