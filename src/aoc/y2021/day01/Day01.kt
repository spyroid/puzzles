package aoc.y2021.day01

import gears.puzzle
import gears.toInts

fun main() {
    puzzle {
        part1(inputLines("input.txt").toInts())
    }
    puzzle {
        part2(inputLines("input.txt").toInts())
    }
}

private fun part1(seq: List<Int>) = seq.windowed(2, 1).count { (it[1] > it[0]) }

private fun part2(seq: List<Int>) = part1(seq.windowed(3, 1).map { it.sum() })

