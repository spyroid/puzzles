package codingame.going_in_circles

import gears.readLocal
import kotlin.system.measureTimeMillis

class GoingInCircles(input: List<String>) {
    private val width = input.first().length
    private val height = input.size
    private val seen = mutableSetOf<Pair<Int, Int>>()
    private val area = Array(height) { y -> Array(width) { x -> input[y][x] } }

    private fun at(pos: Pair<Int, Int>) = if (pos.first in 0 until width && pos.second in 0 until height) area[pos.second][pos.first] else null

    fun solve() = generateSequence { findNextUnseen() }.map { findLoop(it) }.count { it }

    private val dirs = mapOf('>' to Pair(1, 0), '^' to Pair(0, -1), 'v' to Pair(0, 1), '<' to Pair(-1, 0))

    private fun findLoop(pos: Pair<Int, Int>): Boolean {
        val localSeen = mutableSetOf<Pair<Int, Int>>()
        var pp = pos
        var dd = dirs[at(pp)]!!
        while (true) {
            val ch = at(pp)
            if (ch == null || pp in seen) seen.addAll(localSeen).also { return false }

            if (ch != '.') {
                dd = dirs[ch]!!
                localSeen.add(pp)
            }
            pp = Pair(pp.first + dd.first, pp.second + dd.second)
            if (pp in localSeen) seen.addAll(localSeen).also { return true }
        }
    }

    private fun findNextUnseen() = (0 until height).flatMap { y -> (0 until width).map { it to y } }.firstOrNull { at(it) != '.' && it !in seen }
}

fun main() {
    measureTimeMillis { print("⭐️ Result: ${GoingInCircles(readLocal(Main(), "input.txt")).solve()}") }
        .also { time -> println(" in $time ms") }
}

class Main {}
