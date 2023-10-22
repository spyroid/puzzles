package aoc.y2018.day18

import gears.Grid
import gears.puzzle

private fun main() {
    puzzle { pole(inputLines()) }
    puzzle { pole2(inputLines()) }
}

private fun evolve(grid: Grid<Char>) = grid.clone { x, y, e ->
    val around = grid.around8(x, y)
    when (e) {
        '.' -> if (around.count { it == '|' } >= 3) '|' else '.'
        '|' -> if (around.count { it == '#' } >= 3) '#' else '|'
        else -> if (around.count { it == '#' } >= 1 && around.count { it == '|' } >= 1) '#' else '.'
    }
}

private fun pole(input: List<String>): Any {
    var grid = Grid.of(input) { it }
    repeat(10) { grid = evolve(grid) }
    return grid.all().count { it == '|' } * grid.all().count { it == '#' }
}

private fun pole2(input: List<String>): Any {
    var grid = Grid.of(input) { it }
    val seen = mutableMapOf<Int, Int>()
    var gen = 0
    while (true) {
        val hash = grid.deepHashCode()
        if (hash in seen) break else seen[hash] = gen++
        grid = evolve(grid)
    }
    val cycle = gen - seen[grid.deepHashCode()]!!
    val moreSteps = (1_000_000_000 - gen) % cycle
    repeat(moreSteps) { grid = evolve(grid) }
    return grid.all().count { it == '|' } * grid.all().count { it == '#' }
}
