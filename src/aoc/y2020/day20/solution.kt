package aoc.y2020.day20

import gears.Grid2
import gears.findInts
import gears.puzzle

fun main() {
    puzzle { `Jurassic Jigsaw`(input("test.txt")) }
//    puzzle { `Jurassic Jigsaw`(input()) }
}

private fun `Jurassic Jigsaw`(input: String): Any {
    val tiles = input.split("\n\n").map { block ->
        block.lines().let { lines -> Tile(lines.first().findInts().first(), Grid2.of(lines.drop(1)) { if (it == '.') 0 else 1 }) }
    }

    fun find(tile: Tile) = tiles.filter { it.id != tile.id }.mapNotNull { if (it.allEdges.intersect(tile.allEdges).isNotEmpty()) it else null }

    val found = tiles.map { Pair(it.id, find(it)) }.onEach { println("${it.first} -> ${it.second.map { it.id }}") }

    val part1 = found.filter { it.second.size == 2 }.map { it.first }.fold(1L) { v, acc -> acc * v }

    return part1
}

private data class Tile(val id: Int, var grid: Grid2<Int>) {
    val edges = listOf(grid.topEdge(), grid.bottomEdge(), grid.leftEdge(), grid.rightEdge())
    val allEdges = edges.plus(edges.map { it.reversed() }).map { list -> list.map { it.digitToChar() }.joinToString("").toInt(2) }.toSet()
}
