package aoc.y2020.day20

import gears.Grid
import gears.puzzle
import kotlin.math.sqrt

private fun main() {
    puzzle("test") {
        part1(linesFrom("test.txt").asTiles())
    }
}

private fun findAround(tile: Tile, tiles: List<Tile>): List<Tile> {
    val all = mutableListOf<Tile>()
    for (t in tiles) {
        if (t.id == tile.id) continue
        val set = setOf(t.topId, t.bottomId, t.leftId, t.rightId)
        if (tile.topId in set
            || tile.bottomId in set
            || tile.leftId in set
            || tile.rightId in set
        ) all.add(t)
    }
    return all
}

private fun findLeftFor(tile: Tile, all: MutableList<Tile>): Tile? {
    for (t in all) {
        if (t.id == tile.id) continue
        if (tile.leftId == t.rightId) return t
    }
    return null
}

private fun findRightFor(tile: Tile, all: MutableList<Tile>): Tile? {
    for (t in all) {
        if (t.id == tile.id) continue
        if (tile.rightId == t.leftId) return t
    }
    return null
}

private fun findTopFor(tile: Tile, all: MutableList<Tile>): Tile? {
    for (t in all) {
        if (t.id == tile.id) continue
        if (tile.topId == t.bottomId) return t
    }
    return null
}

private fun findBottomFor(tile: Tile, all: MutableList<Tile>): Tile? {
    for (t in all) {
        if (t.id == tile.id) continue
        if (tile.bottomId == t.topId) return t
    }
    return null
}

private fun part1(tiles: List<Tile>): Long {

    val all = tiles.toMutableList()
    for (tile in all) {
        var around = findAround(tile, all).count()
        if (around < 2) tile.flipX()
        around = findAround(tile, all).count()
        if (around < 2) tile.flipY()
    }

    val rows = sqrt(all.size.toFloat()).toInt()

    for (tile in all) {
        val around = findAround(tile, all).count()
        if (around != 2) continue
        val right = findRightFor(tile, all)
        val top = findTopFor(tile, all)
        val bottom = findBottomFor(tile, all)
        val left = findLeftFor(tile, all)
        val t = 1
    }


//    all.forEach {
//        val neighbours = findAround(it, all).count()
//        println("\t${it.topId}")
//        println("${it.leftId}     ${it.rightId}       neighbours = $neighbours")
//        println("\t${it.bottomId}\n")
//    }
    return 0
}

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
        if (a == 1) {
            id = line.drop(5).removeSuffix(":").toInt()
        } else {
            lines.add(line)
        }
        a += 1
    }
    tiles.add(Tile(id, Grid.of(lines) { if (it == '.') 0 else 1 }))
    return tiles
}

private data class Tile(val id: Int, var grid: Grid<Int>) {
    var rightId: Long = 0
    var leftId: Long = 0
    var bottomId: Long = 0
    var topId: Long = 0

    init {
        recalculate()
    }

    fun flipY() {
        grid = grid.flipY()
        recalculate()
    }

    fun flipX() {
        grid = grid.flipX()
        recalculate()
    }

    private fun recalculate() {
        val rotated = grid.rotate2D()
        topId = grid.rowAsNumber(0) { it.digitToChar() }
        bottomId = grid.rowAsNumber(grid.data().lastIndex) { it.digitToChar() }
        leftId = rotated.rowAsNumber(0) { it.digitToChar() }
        rightId = rotated.rowAsNumber(rotated.data().lastIndex) { it.digitToChar() }
    }


}
