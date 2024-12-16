package aoc.y2018.day18

import gears.Grid
import gears.puzzle

private fun main() {
    puzzle { pole(inputLines()) }
    puzzle { pole2(inputLines()) }
}

private fun seq(grid: Grid<Char>): Sequence<Grid<Char>> = generateSequence(grid) { g ->
    g.clone { x, y, e ->
        val around = g.around8(x, y)
        when (e) {
            '.' -> if (around.count { it.v == '|' } >= 3) '|' else '.'
            '|' -> if (around.count { it.v == '#' } >= 3) '#' else '|'
            else -> if (around.count { it.v == '#' } >= 1 && around.count { it.v == '|' } >= 1) '#' else '.'
        }
    }
}

private fun pole(input: List<String>) = seq(Grid.of(input) { it })
    .drop(10)
    .first()
    .let { grid -> grid.all().count { it.v == '|' } * grid.all().count { it.v == '#' } }

private fun pole2(input: List<String>): Any {
    val seen = mutableMapOf<Int, Int>()
    var gen = 0
    var grid = seq(Grid.of(input) { it }).dropWhile { grid ->
        val hash = grid.deepHashCode()
        if (hash in seen) return@dropWhile false else seen[hash] = gen++
        true
    }.first()
    val cycle = gen - seen[grid.deepHashCode()]!!
    val moreSteps = (1_000_000_000 - gen) % cycle
    grid = seq(grid).drop(moreSteps).first()
    return grid.all().count { it.v == '|' } * grid.all().count { it.v == '#' }
}
