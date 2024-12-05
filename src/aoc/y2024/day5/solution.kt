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

    val all = list.map { line ->
        val valid = line.mapIndexed { i, v ->
            line.subList(i + 1, line.size).all { map[v]?.contains(it) == true }
        }.all { it }
        line to valid
    }
    val p1 = all.filter { (a, b) -> b }.sumOf { (a, b) -> a[a.size / 2] }

    fun findInvalid(list: List<Int>): Pair<Int, Set<Int>> {
        val res = list.mapIndexed { i, e ->
            val after = list.subList(i + 1, list.size).toSet() - (map[e] ?: emptySet())
            e to after
        }.firstOrNull { it.second.isNotEmpty() }
        return res ?: Pair(0, emptySet())
    }

    fun swap(list: MutableList<Int>, a: Int, b: Int) {
        list[a] = list[b].also { list[b] = list[a] }
    }

    val p2 = all.filter { (a, b) -> !b }
        .map { (a, b) ->
            val list = a.toMutableList()
            while (findInvalid(list).first != 0) {
                val (i, j) = findInvalid(list).let { list.indexOf(it.first) to list.indexOf(it.second.last()) }
                swap(list, i, j)
            }
            list
        }

    return p1 to p2.sumOf { it[it.size / 2] }
}