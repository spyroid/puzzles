package aoc.y2020.day20

import gears.Grid
import gears.Point
import gears.findLongs
import gears.puzzle
import kotlin.math.sqrt

fun main() {
    puzzle { `Jurassic Jigsaw`(input()) }
}

private fun `Jurassic Jigsaw`(input: String): Any {
    val tiles = input.split("\n\n").map { block ->
        block.lines().let { lines -> Tile(lines.first().findLongs().first(), Grid.of(lines.drop(1)) { it }) }
    }

    val image = createImage(tiles)

    val part1 = image.first().first().id * image.first().last().id * image.last().first().id * image.last().last().id

    val seaMonster = listOf(
        Point(0, 18), Point(1, 0), Point(1, 5), Point(1, 6), Point(1, 11), Point(1, 12),
        Point(1, 17), Point(1, 18), Point(1, 19), Point(2, 1), Point(2, 4), Point(2, 7),
        Point(2, 10), Point(2, 13), Point(2, 16)
    )

    val part2 = imageToSingleTile(image).orientations().first { it.maskIfFound(seaMonster) }.grid.all().count { it.v == '#' }

    return part1 to part2
}

private fun imageToSingleTile(image: List<List<Tile>>): Tile {
    val lines = buildList {
        image.map { rowTiles ->
            for (r in 1..rowTiles.first().grid.maxY - 1) {
                val line = rowTiles.joinToString("") { rowTile -> rowTile.grid.data[r].drop(1).dropLast(1).map { it.v }.joinToString("") }
                add(line)
            }
        }
    }
    return Tile(0, Grid.of(lines) { it })
}

private fun createImage(tiles: List<Tile>): List<List<Tile>> {
    val width = sqrt(tiles.count().toFloat()).toInt()
    var mostRecentTile = findTopCorner(tiles)
    var mostRecentRowHeader = mostRecentTile
    return (0 until width).map { row ->
        (0 until width).map { col ->
            when {
                row == 0 && col == 0 -> mostRecentTile

                col == 0 -> {
                    mostRecentRowHeader = mostRecentRowHeader.findAndOrientNeighbor(Orientation.South, Orientation.North, tiles)
                    mostRecentTile = mostRecentRowHeader
                    mostRecentRowHeader
                }

                else -> {
                    mostRecentTile = mostRecentTile.findAndOrientNeighbor(Orientation.East, Orientation.West, tiles)
                    mostRecentTile
                }
            }
        }
    }
}

private fun findTopCorner(tiles: List<Tile>) =
    tiles
        .first { it.sharedSideCount(tiles) == 2 }
        .orientations()
        .first { it.isSideShared(Orientation.South, tiles) && it.isSideShared(Orientation.East, tiles) }

private data class Tile(val id: Long, var grid: Grid<Char>) {
    private val sides = Orientation.entries.map { sideFacing(it) }.toSet()
    private val sidesReversed = sides.map { it.reversed() }.toSet()
    private fun sideFacing(dir: Orientation) =
        when (dir) {
            Orientation.North -> grid.data.first().map { it.v }.joinToString("")
            Orientation.South -> grid.data.last().map { it.v }.joinToString("")
            Orientation.West -> grid.data.map { row -> row.first() }.map { it.v }.joinToString("")
            Orientation.East -> grid.data.map { row -> row.last() }.map { it.v }.joinToString("")
        }

    fun orientations(): Sequence<Tile> = sequence {
        repeat(2) {
            repeat(4) {
                yield(this@Tile.rotate())
            }
            this@Tile.flip()
        }
    }

    fun rotate() = grid.rotateCw().let { grid = it; this }
    fun flip() = grid.flipX().let { grid = it; this }
    private fun hasSide(side: String) = side in sides || side in sidesReversed
    fun sharedSideCount(tiles: List<Tile>) = sides.sumOf { side -> tiles.filterNot { it.id == id }.count { tile -> tile.hasSide(side) } }
    fun isSideShared(dir: Orientation, tiles: List<Tile>) = tiles.filterNot { it.id == id }.any { tile -> tile.hasSide(sideFacing(dir)) }
    fun findAndOrientNeighbor(mySide: Orientation, theirSide: Orientation, tiles: List<Tile>): Tile {
        val mySideValue = sideFacing(mySide)
        return tiles.filterNot { it.id == id }.first { it.hasSide(mySideValue) }.also { it.orientToSide(mySideValue, theirSide) }
    }

    private fun orientToSide(side: String, direction: Orientation) = orientations().first { it.sideFacing(direction) == side }
    fun maskIfFound(mask: List<Point>) = grid.all().map { (p, _) ->
        (mask.count { grid[p + it]?.v == '#' } == mask.size).also {
            if (it) mask.forEach { grid[p + it] = '0' }
        }
    }.count { it } > 0
}

private enum class Orientation {
    North, East, South, West
}
