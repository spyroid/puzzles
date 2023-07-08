package aoc.y2020.day07

import gears.puzzle

private fun main() {
    puzzle { part1(linesFrom("test.txt")) }
//    puzzle { part1(linesFrom("input.txt")) }
}

private fun part1(lines: List<String>): Int {
    var all = lines.map { parse(it) }.onEach { println(it) }
    return 0 //g.size
}

private fun parse(it: String): Bag {
    val a = it.split(" bags contain ")
    val bag = Bag(a.first(), mutableMapOf())
    if (a.last().contains("no other")) return bag
    a.last()
        .split(", ")
        .forEach { bag.other[it.drop(2).substringBefore(" bag")] = it.take(1).toInt() }
    return bag
}

private data class Bag(val id: String, val other: MutableMap<String, Int>)
