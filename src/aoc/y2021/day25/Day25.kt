package aoc.y2021.day25

import gears.puzzle

fun main() {
    puzzle { part1(inputLines("input.txt")) }
}

private data class Area(val input: List<String>) {
    private val width = input.first().length
    private val height = input.size

    data class Herd(val x: Int, val y: Int, val isHor: Boolean)

    private var herds = buildList {
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, c -> if (c != '.') add(Herd(x, y, c == '>')) }
        }
    }

    private fun move(herd: Herd): Pair<Herd, Int> {
        var (xx, yy) = herd
        if (herd.isHor) {
            xx = (herd.x + 1) % width
        } else {
            yy = (herd.y + 1) % height
        }
        if (herds.firstOrNull { it.x == xx && it.y == yy } == null) {
            return Pair(Herd(xx, yy, herd.isHor), 1)
        }
        return Pair(Herd(herd.x, herd.y, herd.isHor), 0)
    }

    private fun step12(isHor: Boolean): Int {
        var changes = 0
        herds = buildList {
            herds.forEach { herd ->
                if (herd.isHor == isHor) {
                    val (newHerd, c) = move(herd)
                    changes += c
                    add(newHerd)
                } else {
                    add(herd)
                }
            }
        }
        return changes
    }

    fun step() = step12(true) + step12(false)

    override fun toString() = buildString {
        for (y in 0 until height) {
            for (x in 0 until width) {
                val h = herds.firstOrNull { it.x == x && it.y == y }
                if (h == null) {
                    append('.')
                } else {
                    if (h.isHor) append('>') else append('V')
                }
            }
            appendLine()
        }
    }
}

private fun part1(input: List<String>): Int {
    val area = Area(input)
    return generateSequence(2) { it + 1 }.takeWhile { area.step() != 0 }.last()
}
