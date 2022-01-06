package aoc.y2021.day13

import com.github.ajalt.mordant.terminal.Terminal
import readInput
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {

    data class Point(var x: Int, var y: Int)

    class Paper(input: List<String>) {

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

    fun part1(input: List<String>): Int {
        val paper = Paper(input)
        paper.fold()
        return paper.points.size
    }


    fun part2(input: List<String>): String {
        val paper = Paper(input)
        paper.foldAll()
        return paper.toString()
    }

    val testData = readInput("day13/test")
    val inputData = readInput("day13/input")

    val term = Terminal()

    var res1 = part1(testData)
    check(res1 == 17) { "Expected 17 but got $res1" }

    var time = measureTimeMillis { res1 = part1(inputData) }
    term.success("⭐️ Part1: $res1 in $time ms")

    var res2: String
    time = measureTimeMillis { res2 = part2(inputData) }
    term.success("⭐️ Part2: in $time ms\n\n\n\n\n")
    res2.split("\n").forEach {
        val c = term.colors.hsl(Random.nextInt(0, 25) * 10, 1_00, 60)
        term.println("\t\t" + c(it))
    }.let { println("\n\n") }
}

