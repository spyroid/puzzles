package aoc.y2022.day17

import gears.puzzle

fun main() {
    puzzle("t1") { part1(linesFrom("test.txt")) }
    puzzle("1") { part1(linesFrom("input.txt")) }
    puzzle("t2") { part2(linesFrom("test.txt")) }
    puzzle("2") { part2(linesFrom("input.txt")) }
}

fun part1(input: List<String>): Long {
    return Tetris(input.first(), 2022L).simulate()
}

fun part2(input: List<String>): Long {
    return Tetris(input.first(), 1000000000000L).simulate()
}

typealias Rocks = List<Int>

val rows = 20_000
val cols = 7
val bottom = rows * cols

val rockVecs = listOf(
    listOf(0, 1, 2, 3),
    listOf(1, 1 - cols, 1 - 2 * cols, 2 - cols, -cols),
    listOf(0, 1, 2, 2 - cols, 2 - 2 * cols),
    listOf(0, -cols, -2 * cols, -3 * cols),
    listOf(0, 1, -cols, -cols + 1)
)

/*
 * after the first run through the input, the state repeats each input cycle.
 * but after the first run, instead of waiting 1 cycle before forwarding, we wait 2 because line.length could be odd.
 */
private tailrec fun Tetris.simulate(): Long {
    if (rockCount == target) return rows - row + heightToAdd
    if (firstCycleEnd()) save = Save(rockCount, row)
    return if (thirdCycleEnd() && target != 2022L) forward().simulate()
    else updateRocks().simulate()
}

private fun Tetris.firstCycleEnd() = step == jet.length
private fun Tetris.thirdCycleEnd() = step == jet.length * 3
private fun Tetris.forward(): Tetris {
    val rockDiff = rockCount - save.rockCount
    val factor = (target - save.rockCount) / rockDiff
    return copy(
        heightToAdd = (save.row - row) * (factor - 1),
        rockCount = save.rockCount + factor * rockDiff,
        step = step + 1
    )
}

private fun Tetris.rightDir() = jet[(step / 2) % jet.length] == '>'
private fun Tetris.jetPushTime() = step and 1 == 1
private fun Tetris.updateRocks() = apply {
    rocks = if (jetPushTime()) if (rightDir()) rocks.right() else rocks.left()
    else if (rocks.canFall()) rocks.fall()
    else {
        rocks.forEach { taken[it] = true }
        row = minOf(row, rocks.min() / cols)
        rockVecs[(++rockCount % 5).toInt()].map { it + getSpawningPoint(row * cols) }
    }
    step++
}

private data class Save(val rockCount: Long = 0L, val row: Int = 0)

private data class Tetris(
    val jet: String,
    val target: Long,
    var rocks: Rocks = rockVecs.first().map { it + getSpawningPoint(bottom) },
    val taken: BooleanArray = BooleanArray((rows + 1) * cols),
    var rockCount: Long = 0,
    var row: Int = rows,
    var step: Int = 1,
    var heightToAdd: Long = 0L,
    var save: Save = Save()
) {
    fun Rocks.left() = if (any { it % cols == 0 || taken[it - 1] }) this else map { it - 1 }
    fun Rocks.right() = if (any { it % cols == cols - 1 || taken[it + 1] }) this else map { it + 1 }
    fun Rocks.canFall() = none { it + cols > rows * cols || taken[it + cols] }
    fun Rocks.fall() = map { it + cols }
}

private fun getSpawningPoint(initial: Int) = (initial / cols - 4) * cols + 2
