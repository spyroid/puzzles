package codingame.green_valleys

import readLocal
import kotlin.system.measureTimeMillis

typealias XY = Pair<Int, Int>

class GreenValleys(private val input: List<String>) {

    data class Point(val value: Int, var seen: Boolean)

    private val greenLine = input.first().toInt()
    private val size = input[1].toInt()
    private val area = Array(size) { y ->
        val line = input[y + 2].split(" ").map { it.toInt() }
        Array(size) { x -> Point(line[x], line[x] > greenLine) }
    }

    private fun nextUnseen() = (0 until size).flatMap { y -> (0 until size).map { it to y } }.firstOrNull { !area[it.second][it.first].seen }
    private fun at(x: Int, y: Int) = if (x in (0 until size) && y in (0 until size)) XY(x, y) else null
    private fun pAt(p: XY): Point = area[p.second][p.first]
    private fun neighbours(p: XY) = listOfNotNull(at(p.first + 1, p.second), at(p.first - 1, p.second), at(p.first, p.second + 1), at(p.first, p.second - 1)).filterNot { pAt(it).seen }

    fun solve(): Int {
        fun deeper(p: XY): List<XY> {
            pAt(p).seen = true
            return neighbours(p).map { deeper(it) }.flatten().plus(p)
        }

        return generateSequence { nextUnseen() }
            .map { deeper(it) }
            .map { it.size to it.map { p -> pAt(p).value }.minOf { v -> v } }
            .ifEmpty { sequenceOf(Pair(0, 0)) }
            .sortedByDescending { it.first }
            .first().second
    }
}

fun main() {
    measureTimeMillis { print("⭐️ Result: ${GreenValleys(readLocal(Main(), "input.txt")).solve()}") }
        .also { time -> println(" in $time ms") }
}

class Main
