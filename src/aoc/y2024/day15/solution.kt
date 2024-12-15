package aoc.y2024.day15

import gears.Direction
import gears.Direction.LEFT
import gears.Direction.RIGHT
import gears.Grid
import gears.Point
import gears.puzzle

fun main() {
    puzzle { warehouseWoes(input()) }
}

private fun warehouseWoes(input: String): Any {
    var (grid, dirs) = input.split("\n\n").let { (a, b) -> Grid.of(a.lines()) { it } to b.map { Direction.of(it, true) } }
    val grid2 = Grid.of(grid.maxX() * 2 + 2, grid.maxY() + 1, '.').apply {
        grid.all().forEach { (p, c) ->
            val (a, b) = when (c) {
                '#' -> '#' to '#'
                'O' -> '[' to ']'
                '@' -> '@' to '.'
                else -> '.' to '.'
            }
            this[p.x * 2, p.y] = a
            this[p.x * 2 + 1, p.y] = b
        }
    }

    fun moveRobo(g: Grid<Char>, cur: Point, dir: Direction): Point {
        g[cur] = '.'
        g[cur + dir] = '@'
        return cur + dir
    }

    fun moveBlocks(g: Grid<Char>, cur: Point, dir: Direction): Boolean {
        val blocks = mutableSetOf<Point>()
        val toCheck = ArrayDeque<Point>().apply { add(cur + dir) }

        while (toCheck.isNotEmpty()) {
            g.entryAt(toCheck.removeFirst())?.let { next ->
                if (next.v == 'O' || (next.v in "[]" && (dir == RIGHT || dir == LEFT))) {
                    blocks.add(next.p)
                    toCheck.add(next.p + dir)
                } else if (next.v in "[]") {
                    val p = if (next.v == '[') next.p + RIGHT else next.p + LEFT
                    blocks.add(p); blocks.add(next.p)
                    toCheck.add(p + dir); toCheck.add(next.p + dir)
                }
            }
        }

        return blocks.all { g.entryAt(it + dir)?.v != '#' }.also {
            if (it) blocks.mapNotNull { g.entryAt(it) }.onEach { g[it.p] = '.' }.forEach { g[it.p + dir] = it.v }
        }
    }

    fun solve(grid: Grid<Char>, flags: List<Char>): Int {
        var robo = grid.all().first { it.v == '@' }.p
        for (dir in dirs) {
            when (grid.at(robo + dir)) {
                '.' -> robo = moveRobo(grid, robo, dir)
                flags.first(), flags.last() -> if (moveBlocks(grid, robo, dir)) robo = moveRobo(grid, robo, dir)
            }
        }
        return grid.all().filter { it.v == flags.first() }.sumOf { (100 * it.p.y) + it.p.x }
    }

    return solve(grid, listOf('O')) to solve(grid2, listOf('[', ']'))
}