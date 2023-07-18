package aoc.y2020.day20

import gears.Grid
import gears.puzzle

private fun main() {
    puzzle("test") {
        part1(linesFrom("test.txt").asTiles())
    }
    puzzle {
        part1(linesFrom("input.txt").asTiles())
    }
}

private fun part1(tiles: List<Tile>) = tiles.asSequence()
    .map { Pair(it.id, findAround(it, tiles)) }
    .filter { it.second.size == 2 }
    .map { it.first }
    .fold(1L) { v, acc -> acc * v }

private fun findAround(tile: Tile, tiles: List<Tile>) = tiles.filter { it.id != tile.id }
    .mapNotNull { if (it.allEdges.intersect(tile.allEdges).isNotEmpty()) it else null }

private fun List<String>.asTiles(): List<Tile> {
    var a = 1
    var id = 0
    val lines = mutableListOf<String>()
    val tiles = mutableListOf<Tile>()
    for (line in this) {
        if (line.isEmpty()) {
            tiles.add(Tile(id, Grid.of(lines) { if (it == '.') 0 else 1 }))
            lines.clear()
            a = 1
            continue
        }
        if (a == 1) id = line.drop(5).dropLast(1).toInt() else lines.add(line)
        a += 1
    }
    tiles.add(Tile(id, Grid.of(lines) { if (it == '.') 0 else 1 }))
    return tiles
}

private data class Tile(val id: Int, var grid: Grid<Int>) {
    val allEdges = listOf(
        grid.topEdge(), grid.topEdge().reversed(),
        grid.bottomEdge(), grid.bottomEdge().reversed(),
        grid.leftEdge(), grid.leftEdge().reversed(),
        grid.rightEdge(), grid.rightEdge().reversed(),
    ).map { list -> list.map { it.digitToChar() }.joinToString("").toInt(2) }.toSet()
}
