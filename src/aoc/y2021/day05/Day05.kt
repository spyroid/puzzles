package aoc.y2021.day05

import readInput
import java.awt.Point
import kotlin.math.sign

fun main() {

    fun move(p1: Point, p2: Point): Sequence<Point> = sequence {
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

    fun overlaps(seq: Sequence<Pair<Point, Point>>): Int {
        val map = mutableMapOf<Point, Int>()
        return seq.forEach {
            move(it.first, it.second).forEach { point -> map.merge(point, 1) { a, b -> a + b } }
        }.let { map.values.count { it >= 2 } }
    }

    fun part1(seq: Sequence<Pair<Point, Point>>) = overlaps(seq.filter { it.first.x == it.second.x || it.first.y == it.second.y })

    fun part2(seq: Sequence<Pair<Point, Point>>) = overlaps(seq)


    val testSeq = readInput("day05/test").asSequence().map { toPairPoints(it) }
    val inputSeq = readInput("day05/input").asSequence().map { toPairPoints(it) }

    val res1 = part1(testSeq)
    check(res1 == 5) { "Expected 5 but got $res1" }
    println("Part1: ${part1(inputSeq)}")

    val res2 = part2(testSeq)
    check(res2 == 12) { "Expected 12 but got $res2" }
    println("Part2: ${part2(inputSeq)}")
}

val regex = "(\\d+),(\\d+) -> (\\d+),(\\d+)".toRegex()

fun toPairPoints(line: String): Pair<Point, Point> {
    return regex.find(line)!!.groupValues.drop(1).map { it.toInt() }
        .let { Pair(Point(it[0], it[1]), Point(it[2], it[3])) }
}
