package aoc.y2015.day3

import gears.Point
import gears.puzzle

private fun main() {
    puzzle { part1(inputLines("test.txt").first()).size }
    puzzle { part1(inputLines("input.txt").first()).size }
    puzzle { part2(inputLines("test.txt").first()).size }
    puzzle { part2(inputLines("input.txt").first()).size }
}

private fun part1(all: String): MutableSet<Point> {
    var p = Point.zero
    val set = mutableSetOf(p)
    for (m in all) {
        when (m) {
            '>' -> p += Point(1, 0)
            '<' -> p += Point(-1, 0)
            '^' -> p += Point(0, 1)
            'v' -> p += Point(0, -1)
        }
        set.add(p)
    }
    return set
}

private fun part2(all: String): Set<Point> {
    val a = all.mapIndexedNotNull { i, c -> if ((i % 2) == 0) c else null }.joinToString("")
    val b = all.mapIndexedNotNull { i, c -> if ((i % 2) != 0) c else null }.joinToString("")
    return part1(a) + part1(b)
}

