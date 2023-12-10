package aoc.y2021.day05

import gears.Point
import gears.puzzle
import kotlin.math.sign

private val regex = "(\\d+),(\\d+) -> (\\d+),(\\d+)".toRegex()

private fun toPairPoints(line: String): Pair<Point, Point> {
    return regex.find(line)!!.groupValues.drop(1).map { it.toInt() }
        .let { Pair(Point(it[0], it[1]), Point(it[2], it[3])) }
}

fun main() {
    puzzle { part1(inputLines("input.txt").asSequence().map { toPairPoints(it) }) }
    puzzle { part2(inputLines("input.txt").asSequence().map { toPairPoints(it) }) }
}

private fun move(p1: Point, p2: Point): Sequence<Point> = sequence {
    val dx = (p2.x - p1.x).sign
    val dy = (p2.y - p1.y).sign
    var x = p1.x
    var y = p1.y
    while (x != p2.x + dx || y != p2.y + dy) {
        yield(Point(x, y))
        x += dx
        y += dy
    }
}

private fun overlaps(seq: Sequence<Pair<Point, Point>>): Int {
    val map = mutableMapOf<Point, Int>()
    return seq.forEach {
        move(it.first, it.second).forEach { point -> map.merge(point, 1) { a, b -> a + b } }
    }.let { map.values.count { it >= 2 } }
}

private fun part1(seq: Sequence<Pair<Point, Point>>) = overlaps(seq.filter { it.first.x == it.second.x || it.first.y == it.second.y })

private fun part2(seq: Sequence<Pair<Point, Point>>) = overlaps(seq)
