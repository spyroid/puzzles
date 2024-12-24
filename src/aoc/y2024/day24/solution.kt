package aoc.y2024.day24

import gears.puzzle

fun main() {
    puzzle { crossedWires(input()) }
}

private fun crossedWires(input: String): Any {
    val (regs, commands) = input.split("\n\n").let { (a, b) ->
        a.lines().map { it.split(": ") }.associate { (x, y) -> x to y.toInt() }.toMutableMap() to
                b.lines().map { it.split(" ") }
    }

    val set = mutableSetOf<Int>()
    while (set.size < commands.size) {
        commands.forEachIndexed { i, line ->
            val (a, op, b, _, target) = line
            val aa = regs[a]
            val bb = regs[b]
            if (aa == null || bb == null || !set.add(i)) return@forEachIndexed
            when (op) {
                "XOR" -> regs[target] = aa xor bb
                "OR" -> regs[target] = aa or bb
                "AND" -> regs[target] = aa and bb
            }
        }
    }

    val part1 = regs.filterKeys { it.startsWith("z") }
        .entries.sortedByDescending { it.key }
        .map { it.value }
        .joinToString("")
        .toLong(2)

    return part1
}
