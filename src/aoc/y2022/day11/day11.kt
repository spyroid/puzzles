package aoc.y2022.day11

import gears.puzzle

fun main() {
    puzzle("1") { monkeys(inputLines("test.txt"), 20) }
    puzzle("1") { monkeys(inputLines("input.txt"), 20) }
    puzzle("2") { monkeys(inputLines("test.txt"), 10_000, true) }
    puzzle("2") { monkeys(inputLines("input.txt"), 10_000, true) }
}

private data class Monkey(var items: ArrayDeque<Long>, val divBy: Long, val throwTo: Pair<Int, Int>, val type: String, val op2: Long?) {
    var inspections = 0L

    companion object {
        fun of(input: List<String>): Monkey {
            val si = input[1].substringAfter(": ").split(", ").map { it.toLong() }
            val (_, type, op2) = input[2].substringAfter("= ").split(" ")
            val divBy = input[3].substringAfter("by ").toLong()
            val r1 = input[4].substringAfterLast(" ").toInt()
            val r2 = input[5].substringAfterLast(" ").toInt()
            return Monkey(ArrayDeque(si), divBy, Pair(r1, r2), type, if (op2 == "old") null else op2.toLong())
        }
    }

    fun calcWorry(v: Long) = (op2 ?: v).let { v2 -> if (type == "+") v + v2 else v * v2 }

    fun inspect(modulo: Long = 0): List<Pair<Long, Int>> {
        return items.onEach { inspections += 1 }
            .map { item -> calcWorry(item).let { if (modulo == 0L) it / 3 else it % modulo } }
            .map { w -> Pair(w, if (w % divBy == 0L) throwTo.first else throwTo.second) }
            .also { items.clear() }
    }
}

private fun monkeys(input: List<String>, count: Int, useModulo: Boolean = false): Long {
    val all = input.chunked(7).map { Monkey.of(it) }
    val modulo = if (useModulo) all.map { it.divBy }.fold(1, Long::times) else 0

    repeat(count) { all.forEach { m -> m.inspect(modulo).onEach { all[it.second].items.addLast(it.first) } } }
    return all.sortedByDescending { it.inspections }.take(2).let { it[0].inspections * it[1].inspections }
}
