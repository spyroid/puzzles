package codingame.minesweeper

import gears.readLocal
import kotlin.system.measureTimeMillis

data class Point(val x: Int, val y: Int, var v: Double?)

fun solve(input: List<String>) {
    val (h, w) = input.first().split(" ").map { it.toInt() }
    val totalMines = input[1].toInt()
    val items = mutableListOf<Point>()
    input.drop(2)
        .forEachIndexed { y, line ->
            line.forEachIndexed { x, ch ->
                val value = if (ch == '.') null else (if (ch == '?') 0 else ch.digitToInt()).toDouble()
                items.add(Point(x, y, value))
            }
        }

    fun at(x: Int, y: Int): Point? {
        if (x !in 0 until w || y !in 0 until h) return null
        return items[x + y * w]
    }

    fun questionsAround(it: Point): List<Point> {
        return listOfNotNull(
            at(it.x - 1, it.y - 1), at(it.x, it.y - 1), at(it.x + 1, it.y - 1),
            at(it.x - 1, it.y), at(it.x + 1, it.y),
            at(it.x - 1, it.y + 1), at(it.x, it.y + 1), at(it.x + 1, it.y + 1)
        ).filter { it.v != null }.filter { it.v!! < 1 }
    }

    fun mark(p: Point) {
        if (p.v == null) return
        val questions = questionsAround(p)
        questions.forEach { it.v = it.v?.minus(p.v!! / questions.size) }
    }

    items.filter { it.v != null && it.v!! > 0 }.forEach { mark(it) }
    items.asSequence()
        .filter { it.v != null }
        .filter { it.v!! < 1 }
        .sortedWith(compareBy { it.v })
        .take(totalMines)
        .sortedWith(compareBy<Point> {it.x}.thenBy { it.y })
        .forEach { println("${it.x} ${it.y} : ${it.v}") }
}

fun main() {
    measureTimeMillis { print("⭐️ Result: ${solve(readLocal(Main(), "input.txt"))}") }
        .also { time -> println(" in $time ms") }
}

class Main {}
