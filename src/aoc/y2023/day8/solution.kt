package aoc.y2023.day8

import gears.lcm
import gears.puzzle

private fun main() {
    puzzle("1 & 2") { hauntedWasteland(inputLines()) }
}

private fun hauntedWasteland(input: List<String>): Any {
    val map = input.drop(2)
        .map { it.replace("[(,)=]".toRegex(), "").split("\\s+".toRegex()) }
        .associate { (a, b, c) -> a to Pair(b, c) }

    val dirs = sequence { while (true) yieldAll(input.first().asSequence()) }
    val nodes = map.keys.filter { it.endsWith("A") }

    fun walk(start: String, end: (String) -> Boolean) = dirs
        .runningFold(start) { n, c -> map[n]?.let { (l, r) -> if (c == 'L') l else r }.toString() }
        .indexOfFirst(end).toLong()

    return walk(nodes.first()) { it == "ZZZ" } to nodes.map { n -> walk(n) { it.endsWith("Z") } }.lcm()
}
