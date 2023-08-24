package aoc.y2021.day13

import gears.Point
import gears.puzzle

fun main() {
    puzzle { part1(linesFrom("input.txt")) }
    puzzle { part2(linesFrom("input.txt")) }
}

private class Paper(input: List<String>) {
    var points = mutableSetOf<Point>()
    val folds = mutableListOf<Point>()

    init {
        input.filter { it.isNotBlank() }.forEach { line ->
            if (line.contains("fold")) {
                val s = line.split(" ").last().split("=")
                if (s[0] == "x") folds.add(Point(s[1].toInt(), 0)) else folds.add(Point(0, s[1].toInt()))
            } else {
                line.split(",").map { it.toInt() }.let { points.add(Point(it[0], it[1])) }
            }
        }
    }

    fun fold() {
        val fold = folds.removeFirst()
        if (fold.x == 0) {
            points = points.onEach { if (it.y > fold.y) it.y = fold.y * 2 - it.y }.toMutableSet()
        } else {
            points = points.onEach { if (it.x > fold.x) it.x = fold.x * 2 - it.x }.toMutableSet()
        }
    }

    fun foldAll() {
        while (folds.isNotEmpty()) fold()
    }

    override fun toString() = buildString {
        for (y in 0..points.maxOf { it.y }) {
            for (x in 0..points.maxOf { it.x }) {
                if (Point(x, y) in points) append("#") else append(" ")
            }
            appendLine()
        }
    }
}

private fun part1(input: List<String>): Int {
    val paper = Paper(input)
    paper.fold()
    return paper.points.size
}


private fun part2(input: List<String>): String {
    val paper = Paper(input)
    paper.foldAll()
    println(paper.toString())
    return ""
}
