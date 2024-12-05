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
        fun diff() = indices.map { it to takeLast(size - it - 1).toSet() - (map[get(it)] ?: emptySet()) }

        return generateSequence(diff()) { a ->
            a.find { it.second.isNotEmpty() }?.let { (i, set) ->
                this[i] = this[indexOf(set.last())].also { this[indexOf(set.last())] = this[i] }
            }
            diff()
        }.withIndex().first { iv -> iv.value.sumOf { it.second.sum() } == 0 }.index
    }

    fun MutableList<Int>.mid() = this[size / 2]

    return list.map { ints ->
        ints.fix().let { if (it == 0) ints.mid() to 0 else 0 to ints.mid() }
    }.unzip().let { it.first.sum() to it.second.sum() }
}
