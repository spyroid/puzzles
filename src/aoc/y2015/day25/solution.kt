package aoc.y2015.day25

import gears.puzzle

private fun main() {
    puzzle { snow(3010, 3019) }
}

private fun snow(r: Int, c: Int): Long {
    var row = 1
    var col = 1
    var v = 20151125L

    while (row != r || col != c) {
        if (row == 1) {
            row = col + 1
            col = 1
        } else {
            col++
            row--
        }
        v = v * 252533L % 33554393L
    }
    return v
}

