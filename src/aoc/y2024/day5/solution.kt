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

    fun swap(list: MutableList<Int>, a: Int, b: Int) {
        list[a] = list[b].also { list[b] = list[a] }
    }

    val all = list.map { line ->
        val a = findInvalid(line)
        if (a.second == 0) {
            line[line.size / 2] to 0
        } else {
            generateSequence(line.toMutableList() to a) { (a, b) ->
                swap(a, a.indexOf(b.first), a.indexOf(b.second))
                a to findInvalid(a)
            }.first { (a, b) -> b.first == 0 }.first
                .let { 0 to it[it.size / 2] }
        }
    }.unzip()
    return all.first.sum() to all.second.sum()
}