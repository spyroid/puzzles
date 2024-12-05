package aoc.y2024.day5

import gears.puzzle

fun main() {
    puzzle { printQueue(input()) }
}

private fun printQueue(input: String): Any {
    val (map, list) = input.split("\n\n").let { (a, b) ->
        val m = a.lines().map { it.split("|").map { it.toInt() }.let { it.first() to it.last() } }
            .groupBy({ it.first }, { it.second }).mapValues { it.value.toSet() }

        val l = b.lines().map { it.split(",").map { it.toInt() } }

        m to l
    }

    fun findInvalid(list: List<Int>) = list.mapIndexed { i, e ->
        e to list.takeLast(list.size - i - 1).toSet() - (map[e] ?: emptySet())
    }.firstOrNull { it.second.isNotEmpty() }?.let { it.first to it.second.last() } ?: (0 to 0)

    fun MutableList<Int>.swap(a: Int, b: Int) {
        this[a] = this[b].also { this[b] = this[a] }
    }

    fun MutableList<Int>.mid() = this[size / 2]

    return list.map { line ->
        generateSequence(line.toMutableList() to findInvalid(line)) { (line, b) ->
            line.swap(line.indexOf(b.first), line.indexOf(b.second))
            line to findInvalid(line)
        }
            .withIndex()
            .first { (_, v) -> v.second.second == 0 }
            .let { (i, v) -> if (i == 0) v.first.mid() to 0 else 0 to v.first.mid() }
    }.unzip().let { it.first.sum() to it.second.sum() }
}
