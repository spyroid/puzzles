package aoc.y2022.day24

import gears.Point
import gears.puzzle

fun main() {
    puzzle { `Blizzard Basin`(inputLines()) }
}

private fun `Blizzard Basin`(input: List<String>): Any {

    val initialValley = Valley.from(input)
    val start = Point(input.first().indexOf('.'), 0)
    val goal = Point(input.last().indexOf('.'), input.lastIndex)

    fun reachTheGoal(start: Point, goal: Point, initial: Valley): Pair<Int, Valley> {
        var minutes = 0
        var valley = initial
        var batch = setOf(start)

        while (true) {
            minutes++
            valley = valley.moved()
            batch = buildSet {
                batch.forEach { current ->
                    addAll(current.around4().onEach { if (it == goal) return minutes to valley }.filter(valley::isSafe))
                    if (valley.isSafe(current) || current == start) add(current)
                }
            }
        }
    }

    val part1 = reachTheGoal(start, goal, initialValley).first

    val (firstMinutes, afterFirst) = reachTheGoal(start, goal, initialValley)
    val (secondMinutes, afterSecond) = reachTheGoal(goal, start, afterFirst)
    val (thirdMinutes, _) = reachTheGoal(start, goal, afterSecond)
    val part2 = firstMinutes + secondMinutes + thirdMinutes

    return part1 to part2
}

private data class Valley(val blizzards: List<Blizzard>, val boundary: Point) {
    private val unsafePoints = blizzards.map { it.point }.toSet()

    fun moved() = copy(blizzards.map { it.moved(boundary) })

    fun isSafe(point: Point): Boolean {
        return point !in unsafePoints && point.x in 1 until boundary.x && point.y in 1 until boundary.y
    }

    companion object {
        fun from(input: List<String>): Valley {
            return Valley(
                blizzards = input.flatMapIndexed { y, line ->
                    line.mapIndexedNotNull { x, char ->
                        when (char) {
                            '>' -> Blizzard(Point(x, y), Point(1, 0))
                            '<' -> Blizzard(Point(x, y), Point(-1, 0))
                            'v' -> Blizzard(Point(x, y), Point(0, 1))
                            '^' -> Blizzard(Point(x, y), Point(0, -1))
                            else -> null
                        }
                    }
                },
                boundary = Point(input.last().lastIndex, input.lastIndex)
            )
        }
    }
}

private data class Blizzard(val point: Point, val dir: Point) {
    fun moved(boundary: Point): Blizzard {
        val next = point + dir
        return copy(
            point = when {
                next.x == 0 -> Point(boundary.x - 1, next.y)
                next.y == 0 -> Point(next.x, boundary.y - 1)
                next.x == boundary.x -> Point(1, next.y)
                next.y == boundary.y -> Point(next.x, 1)
                else -> next
            }
        )
    }
}
