package aoc.y2022.day12

import Array2d
import Point
import at
import pointsAround
import pointsOf
import puzzle

fun main() {
    puzzle("1") { part1(linesFrom("test.txt")) }
    puzzle("1") { part1(linesFrom("input.txt")) }
    puzzle("2") { part2(linesFrom("test.txt")) }
    puzzle("2") { part2(linesFrom("input.txt")) }
}

private fun part1(input: List<String>): Int {
    val map = input.map { row -> row.map { it }.toMutableList() }.toMutableList()
    val start = map.pointsOf { _, _, v -> v == 'S' }.onEach { map[it.y][it.x] = 'a' }.first()
    var goal = map.pointsOf { _, _, v -> v == 'E' }.onEach { map[it.y][it.x] = 'z' }.first()
    return hill(map, start, goal)
}

private fun part2(input: List<String>): Int {
    val map = input.map { row -> row.map { it }.toMutableList() }.toMutableList()
    var goal = map.pointsOf { _, _, v -> v == 'E' }.onEach { map[it.y][it.x] = 'z' }.first()
    val all = map.pointsOf { _, _, v -> (v == 'S' || v == 'a') }.onEach { map[it.y][it.x] = 'a' }.filter { it.x == 0 }
    return all.minOf { hill(map, it, goal) }
}

private fun hill(map: Array2d<Char>, start: Point, goal: Point): Int {
    val points = mutableMapOf<Point, Point>()
    var seen = mutableSetOf(start)
    val queue = mutableListOf(start)
    var current = Point(-1, -1)

    while (queue.isNotEmpty()) {
        current = queue.removeFirst()
        if (current == goal) {
            break
        }
        map.pointsAround(current.x, current.y)
            .filter { it !in seen }
            .filter { map.at(it.x, it.y)!! - map.at(current.x, current.y)!! <= 1 }
            .forEach {
                points[it] = current
                seen.add(it)
                queue.add(it)
            }
    }

    return generateSequence(current) { points[it] }.count() - 1
}
