package aoc.y2024.day21

import gears.Direction
import gears.Grid2
import gears.Point
import gears.puzzle

fun main() {
    puzzle { keypadConundrum(inputLines()) }
}

private fun keypadConundrum(input: List<String>): Any {

    val keypad = mapOf(
        '7' to Point(0, 0), '8' to Point(1, 0), '9' to Point(2, 0),
        '4' to Point(0, 1), '5' to Point(1, 1), '6' to Point(2, 1),
        '1' to Point(0, 2), '2' to Point(1, 2), '3' to Point(2, 2),
        'X' to Point(0, 3), '0' to Point(1, 3), 'A' to Point(2, 3)
    )

    val directions = mapOf(
        'X' to Point(0, 0), '^' to Point(1, 0), 'A' to Point(2, 0),
        '<' to Point(0, 1), 'v' to Point(1, 1), '>' to Point(2, 1),
    )

    fun commands(data: Map<Char, Point>, start: Char, end: Char): List<String> {
        if (start == end) return listOf("A")
        val all = mutableListOf<String>()
        val queue = ArrayDeque<Grid2.Entry<String>>().apply { add(Grid2.Entry(data.getValue(start), "")) }
        val distances = mutableMapOf<Point, Int>()

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            if (current.p == data[end]) all.add(current.v + "A")
            if (distances[current.p] != null && distances[current.p]!! < current.v.length) continue
            "<>^v".map { it to Direction.of(it, true) }.forEach { (ch, direction) ->
                val position = current.p + direction
                if (position == data['X']) return@forEach

                data.values.find { it == position }?.let { button ->
                    val newPath = current.v + ch
                    val dist = distances[position]
                    if (dist == null || dist >= newPath.length) {
                        queue.add(Grid2.Entry(position, newPath))
                        distances[position] = newPath.length
                    }
                }
            }
        }
        return all
    }

    fun pressButtons(data: Map<Char, Point>, code: String, robo: Int, cache: MutableMap<Pair<String, Int>, Long>): Long {
        cache[code to robo]?.let { return it }
        var current = 'A'
        var length = 0L

        for (c in code) {
            val moves = commands(data, current, c)
            if (robo == 0) {
                length += moves.first().length
            } else {
                length += moves.minOf { pressButtons(directions, it, robo - 1, cache) }
            }
            current = c
        }
        return length.also { cache[code to robo] = it }
    }

    val part1 = input.sumOf { it.dropLast(1).toLong() * pressButtons(keypad, it, 2, mutableMapOf()) }
    val part2 = input.sumOf { it.dropLast(1).toLong() * pressButtons(keypad, it, 25, mutableMapOf()) }

    return part1 to part2
}

