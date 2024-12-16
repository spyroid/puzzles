package aoc.y2020.day11

import gears.Grid
import gears.puzzle

private fun main() {
    puzzle { part1(Grid.of(inputLines("test.txt")) { it }) }
    puzzle { part1(Grid.of(inputLines("input.txt")) { it }) }
    puzzle { part2(Grid.of(inputLines("test.txt")) { it }) }
    puzzle { part2(Grid.of(inputLines("input.txt")) { it }) }
}

private fun <T> findAll(grid: Grid<T>, x: Int, y: Int): List<List<T>> {
    fun getAll(xx: Int, yy: Int): Sequence<T> = sequence {
        var a = x + xx
        var b = y + yy
        do {
            val v = grid.at(a, b)
            if (v != null) yield(v)
            a += xx
            b += yy
        } while (v != null)
    }
    return buildList {
        add(getAll(-1, 0).toList())
        add(getAll(1, 0).toList())
        add(getAll(0, -1).toList())
        add(getAll(0, 1).toList())
        add(getAll(-1, -1).toList())
        add(getAll(1, 1).toList())
        add(getAll(1, -1).toList())
        add(getAll(-1, 1).toList())
    }
}

private fun part2(grid: Grid<Char>): Int {
    var g = grid
    var count = 0
    while (true) {
        var changed = false
        g = g.clone { x, y, e ->
            val around = findAll(g, x, y).map { d -> d.firstOrNull() { it != '.' } ?: '.' }
            when (e) {
                'L' -> if (around.count { it == '#' } == 0) {
                    changed = true
                    '#'
                } else 'L'

                '#' -> if (around.count { it == '#' } >= 5) {
                    changed = true
                    'L'
                } else '#'

                else -> '.'
            }
        }
        count += 1
        if (!changed) break
    }
    return g.all().count { it.v == '#' }
}

private fun part1(grid: Grid<Char>): Int {
    var g = grid
    var count = 0
    while (true) {
        var changed = false
        g = g.clone { x, y, e ->
            val around = g.around8(x, y)
            when (e) {
                'L' -> if (around.count { it.v == '#' } == 0) {
                    changed = true
                    '#'
                } else 'L'

                '#' -> if (around.count { it.v == '#' } >= 4) {
                    changed = true
                    'L'
                } else '#'

                else -> '.'
            }
        }
        count += 1
        if (!changed) break
    }
    return g.all().count { it.v == '#' }
}

