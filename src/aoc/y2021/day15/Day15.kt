package aoc.y2021.day15

import gears.puzzle
import java.util.*

fun main() {
    puzzle { part1(linesFrom("input.txt")) }
    puzzle { part2(linesFrom("input.txt")) }
}

fun part1(input: List<String>): Int {
    val data = input.map { line -> line.map { it.toString().toInt() } }
    return solve(data)
}

fun part2(input: List<String>): Int {
    val data = input.map { line -> line.map { it.toString().toInt() } }
    return solve(expandInput(data))
}

private fun solve(input: List<List<Int>>): Int {
    val start = Point(0, 0)
    val destination = Point(input.lastIndex, input.first().lastIndex)

    val seen = mutableSetOf(start)
    val queue = PriorityQueue<ContinuationPoint>(compareBy { it.risk }).also { it.add(ContinuationPoint(start, 0)) }

    while (queue.peek().position != destination) {
        val (position, risk) = queue.poll()
        position.neighbours()
            .filter { it.x in 0..destination.x && it.y in 0..destination.y }
            .filterNot { seen.contains(it) }
            .forEach { neighbour ->
                (risk + input[neighbour.y][neighbour.x]).let { neighbourRisk ->
                    queue.add(ContinuationPoint(neighbour, neighbourRisk))
                }
                seen.add(neighbour)
            }
    }
    return queue.poll().risk
}

private data class Point(val x: Int, val y: Int) {
    fun neighbours() = setOf(Point(x - 1, y), Point(x + 1, y), Point(x, y + 1), Point(x, y - 1))
}

private data class ContinuationPoint(val position: Point, val risk: Int)

private fun expandInput(input: List<List<Int>>): List<List<Int>> {
    val valueMapper = { value: Int -> if (value == 9) 1 else value + 1 }
    val rows = input.map { generateSequence(it) { row -> row.map(valueMapper) }.take(5).toList().flatten() }
    return generateSequence(rows) { chunk -> chunk.map { row -> row.map(valueMapper) } }.take(5).toList().flatten()
}
