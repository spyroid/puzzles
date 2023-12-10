package aoc.y2020.day12

import gears.Direction
import gears.Point
import gears.puzzle

private fun main() {
    puzzle { part1(inputLines("test.txt").asCommands()) }
    puzzle { part1(inputLines("input.txt").asCommands()) }
    puzzle { part2(inputLines("test.txt").asCommands()) }
    puzzle { part2(inputLines("input.txt").asCommands()) }
}

private fun part2(input: List<Pair<Char, Int>>): Int {
    val start = Point.zero
    var p = start
    var wp = Point(10, 1)
    input.forEach { (inst, v) ->
        when (inst) {
            'F' -> p += Point(wp.x * v, wp.y * v)
            'R' -> when (v) {
                90 -> wp = Point(wp.y, -wp.x)
                180 -> wp = Point(-wp.x, -wp.y)
                270 -> wp = Point(-wp.y, wp.x)
            }

            'L' -> when (v) {
                90 -> wp = Point(-wp.y, wp.x)
                180 -> wp = Point(-wp.x, -wp.y)
                270 -> wp = Point(wp.y, -wp.x)
            }

            'N' -> wp += Direction.UP.asPoint().times(v)
            'S' -> wp += Direction.DOWN.asPoint().times(v)
            'E' -> wp += Direction.RIGHT.asPoint().times(v)
            'W' -> wp += Direction.LEFT.asPoint().times(v)

            else -> Point.zero
        }
    }
    return p.manhattan(start)
}

private fun part1(input: List<Pair<Char, Int>>): Int {
    val start = Point.zero
    var p = start
    var dir = Direction.of('E')
    input.forEach { (inst, v) ->
        when (inst) {
            'F' -> p += dir.asPoint().times(v)
            'R' -> repeat(v / 90) { dir = dir.turnCw() }
            'L' -> repeat(v / 90) { dir = dir.turnCcw() }
            'N' -> p += Direction.UP.asPoint().times(v)
            'S' -> p += Direction.DOWN.asPoint().times(v)
            'E' -> p += Direction.RIGHT.asPoint().times(v)
            'W' -> p += Direction.LEFT.asPoint().times(v)

            else -> Point.zero
        }
    }
    return p.manhattan(start)
}

private fun List<String>.asCommands(): List<Pair<Char, Int>> = this.map { Pair(it.first(), it.drop(1).toInt()) }

