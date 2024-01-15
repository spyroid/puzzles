package aoc.y2022.day14

import gears.Point
import gears.puzzle
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

fun main() {
    puzzle("t1") { part1(inputLines("test.txt")) }
    puzzle("1") { part1(inputLines("input.txt")) }
    puzzle("t2") { part2(inputLines("test.txt")) }
    puzzle("2") { part2(inputLines("input.txt")) }
}

private fun parseWalls(input: List<String>) =
    input.map {
        it.split(" -> ")
            .map { it2 -> it2.split(",") }
            .map { (x, y) -> Point(x.toInt(), y.toInt()) }
    }

private fun part1(input: List<String>): Int {
    val w = World(parseWalls(input))
    return generateSequence { w.drop() }.takeWhile { it }.count()
}

private fun part2(input: List<String>): Int {
    val w = World(parseWalls(input), true)
    return generateSequence { w.drop() }.takeWhile { it }.count()
}

private const val EMPTY = 0
private const val BLOCKED = 1
private val MOVES_SAND = listOf(Point(0, 1), Point(-1, 1), Point(1, 1))

private operator fun <T> List<List<T>>.get(p: Point) = this[p.x][p.y]
private operator fun <E> MutableList<MutableList<E>>.set(p: Point, value: E) {
    this[p.x][p.y] = value
}

private class World(walls: List<List<Point>>, blockVoid: Boolean = false) {
    enum class MoveResultState { MOVED, BLOCKED, FREE }
    inner class MoveResult(val state: MoveResultState, val newPoint: Point? = null)

    val minY = min(0, walls.minOf { it.minOf { it2 -> it2.y } })
    val maxY = walls.maxOf { it.maxOf { it2 -> it2.y } } + if (blockVoid) 2 else 0
    val minX = min(walls.minOf { it.minOf { it2 -> it2.x } }, min(500, if (blockVoid) 500 - (maxY - minY + 2) else 500))
    val maxX = max(walls.maxOf { it.maxOf { it2 -> it2.x } }, max(500, if (blockVoid) 500 + (maxY - minY + 2) else 500))
    val world = MutableList(maxX - minX + 1) { MutableList(maxY - minY + 1) { EMPTY } }

    init {
        val walls2 = if (blockVoid) walls + listOf(listOf(Point(minX, maxY), Point(maxX, maxY))) else walls
        walls2.forEach {
            var cp = it[0] - Point(minX, minY)
            world[cp] = BLOCKED
            it.forEach { it2 ->
                while (cp != it2 - Point(minX, minY)) {
                    cp += Point((it2.x - minX - cp.x).sign, (it2.y - minY - cp.y).sign)
                    world[cp] = BLOCKED
                }
            }
        }
    }

    private fun canMove(from: Point, dir: Point): MoveResult {
        val newPoint = from + dir
        if (newPoint.x !in (0..(maxX - minX)) || newPoint.y !in (0..(maxY - minY))) return MoveResult(MoveResultState.FREE)
        if (world[newPoint] == BLOCKED) return MoveResult(MoveResultState.BLOCKED)
        return MoveResult(MoveResultState.MOVED, newPoint)
    }

    fun drop(): Boolean {
        var sandPoint = Point(500, 0) - Point(minX, minY)
        if (world[sandPoint] == BLOCKED) return false
        world[sandPoint] = BLOCKED
        while (true) {
            var movedRes = false
            run moves@{
                MOVES_SAND.forEach { dir ->
                    val moved = canMove(sandPoint, dir)
                    if (moved.state == MoveResultState.FREE) {
                        return@drop false
                    }
                    if (moved.state == MoveResultState.MOVED) {
                        movedRes = true
                        world[sandPoint] = EMPTY
                        sandPoint = moved.newPoint!!
                        world[sandPoint] = BLOCKED
                        return@moves
                    }
                }
            }
            if (!movedRes) break
        }
        return true
    }
}
