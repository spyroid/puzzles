package aoc.y2018.day18

import gears.Grid
import gears.puzzle

private fun main() {
    puzzle { pole(inputLines()) }
    puzzle { pole2(inputLines()) }
}

private fun seq(grid: Grid<Char>): Sequence<Grid<Char>> = generateSequence(grid) {
    evolve(it)
}

private fun evolve(grid: Grid<Char>) = grid.clone { x, y, e ->
    val around = grid.around8(x, y)
    when (e) {
        '.' -> if (around.count { it == '|' } >= 3) '|' else '.'
        '|' -> if (around.count { it == '#' } >= 3) '#' else '|'
        else -> if (around.count { it == '#' } >= 1 && around.count { it == '|' } >= 1) '#' else '.'
    }
}

private fun pole(input: List<String>) = seq(Grid.of(input) { it })
    .drop(10)
    .first()
    .let { grid ->
    grid.all().count { it == '|' } * grid.all().count { it == '#' }
}

private fun pole2(input: List<String>): Any { // 174420
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
    return grid.all().count { it == '|' } * grid.all().count { it == '#' }
}
