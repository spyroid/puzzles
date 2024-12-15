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

    fun moveBlock(g: Grid<Char>, cur: Point, dir: Direction): Boolean {
        val end = generateSequence(g.entryAt(cur + dir)) { g.entryAt(it.p + dir) }.first { it.v == '#' || it.v == '.' }
        if (end.v == '#') return false
        generateSequence(end) { e -> g.entryAt(e.p - dir)?.also { g[e.p] = g[it.p] } }.first { it.p == cur }
        return true
    }

    fun part1(): Int {
        var robo = grid.all().first { it.v == '@' }.p
        for (dir in dirs) {
            when (grid.at(robo + dir)) {
                '.' -> robo = moveRobo(grid, robo, dir)
                'O' -> if (moveBlock(grid, robo, dir)) robo = moveRobo(grid, robo, dir)
            }
        }
        return grid.all().filter { it.v == 'O' }.sumOf { (100 * it.p.y) + it.p.x }
    }

    fun moveBlock2(g: Grid<Char>, cur: Point, dir: Direction): Boolean {
        val blocks = mutableSetOf<Point>()
        val toCheck = ArrayDeque<Point>().apply { add(cur + dir) }

        while (toCheck.isNotEmpty()) {
            g.entryAt(toCheck.removeFirst())?.let { next ->
                if (next.v in "[]") {
                    val p = if (next.v == '[') next.p + RIGHT else next.p + LEFT
                    blocks.add(p); blocks.add(next.p)
                    toCheck.add(p + dir); toCheck.add(next.p + dir)
                }
            }
        }

        return blocks.all { g.entryAt(it + dir)?.v != '#' }.also {
            if (it ) blocks.mapNotNull { g.entryAt(it) }.onEach { g[it.p] = '.' }.forEach { g[it.p + dir] = it.v }
        }
    }

    fun part2(): Int {
        var robo = grid2.all().first { it.v == '@' }.p
        for (dir in dirs) {
            when (grid2.at(robo + dir) ?: '?') {
                '.' -> robo = moveRobo(grid2, robo, dir)
                '[', ']' -> {
                    val canMove = if (dir == RIGHT || dir == LEFT) moveBlock(grid2, robo, dir) else moveBlock2(grid2, robo, dir)
                    if (canMove) robo = moveRobo(grid2, robo, dir)
                }
            }
        }
        return grid2.all().filter { it.v == '[' }.sumOf { (100 * it.p.y) + it.p.x }
    }

    return part1() to part2()
}

