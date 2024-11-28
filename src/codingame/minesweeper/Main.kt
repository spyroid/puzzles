package codingame.minesweeper

import gears.puzzle

fun main() {
    puzzle {
        solve(inputLines())
    }
}

private data class Point(val x: Int, val y: Int, var v: Char)

private fun solve(input: List<String>) {
    val (h, w) = input.first().split(" ").map { it.toInt() }
    val totalMines = input[1].toInt()
    val grid = mutableListOf<Point>()
    input.drop(2).take(h).forEachIndexed { y, line -> line.forEachIndexed { x, ch -> grid.add(Point(x, y, ch)) } }

    fun at(x: Int, y: Int): Point? = if (x !in 0 until w || y !in 0 until h) null else grid[x + y * w]

    fun around(it: Point): List<Point> = listOfNotNull(
        at(it.x - 1, it.y - 1), at(it.x, it.y - 1), at(it.x + 1, it.y - 1),
        at(it.x - 1, it.y), at(it.x + 1, it.y),
        at(it.x - 1, it.y + 1), at(it.x, it.y + 1), at(it.x + 1, it.y + 1)
    )

    fun scan() {
        grid.filter { it.v.isDigit() }.forEach { p ->
            val (qs, bombs) = around(p).let { p -> p.count { it.v == '?' } to p.count { it.v == 'x' } }
            val v = p.v.digitToIntOrNull()
            if (v == bombs) {
                around(p).filter { it.v == '?' }.forEach { it.v = '.' }
            } else if (v == qs && bombs == 0 || bombs + qs == v) {
                around(p).filter { it.v == '?' }.forEach { it.v = 'x' }
            }
        }
    }

    fun printAll() = grid.map { it.v }.windowed(w, w).map { it.joinToString("").also(::println) }.also { println() }

    val buffer = grid.mapNotNull { p -> if (around(p).all { it.v == '?' }) p else null }

    val steps = generateSequence(0 to 0) { scan().let { grid.count { it.v == 'x' } to grid.count { it.v == '.' } } }
        .onEach { print("$it ") }
        .zipWithNext()
        .takeWhile { (a, b) -> a != b }
        .last().second

    println("Steps: $steps")

    printAll()

    grid.filter { it.v == 'x' }.plus(buffer.take(totalMines - steps.first))
        .sortedWith(compareBy({ it.x }, { it.y }))
        .forEach { println("${it.x} ${it.y}") }
}

