package aoc.y2022

import puzzle

fun main() {
    puzzle { part1(readLinesFrom("day2.test.txt")) }
    puzzle { part1(readLinesFrom("day2.input.txt")) }
    puzzle { part2(readLinesFrom("day2.test.txt")) }
    puzzle { part2(readLinesFrom("day2.input.txt")) }
}

private fun part1(input: List<String>): Int {
    return input
        .filter { it.isNotBlank() }
        .map { Pair(it[0], it[2]) }
        .map {
            when (it.first) { // X rock   Y  paper    Z scizzors
                'A' -> {
                    when (it.second) {
                        'X' -> 1 + 3
                        'Y' -> 2 + 6
                        'Z' -> 3 + 0
                        else -> 0
                    }
                }

                'B' -> { // paper
                    when (it.second) {
                        'X' -> 1 + 0
                        'Y' -> 2 + 3
                        'Z' -> 3 + 6
                        else -> 0
                    }
                }

                'C' -> {
                    when (it.second) {
                        'X' -> 1 + 6
                        'Y' -> 2 + 0
                        'Z' -> 3 + 3
                        else -> 0
                    }
                }

                else -> 0
            }

        }
        .sum()
}

private fun part2(input: List<String>): Int {
    return input
        .filter { it.isNotBlank() }
        .map { Pair(it[0], it[2]) }
        .map { //1 for Rock, 2 for Paper, and 3 for Scissors
            when (it.first) { // X loose   Y  draw    Z   win
                'A' -> {
                    when (it.second) {
                        'X' -> 3 + 0
                        'Y' -> 1 + 3
                        'Z' -> 2 + 6
                        else -> 0
                    }
                }

                'B' -> { // paper
                    when (it.second) {
                        'X' -> 1 + 0
                        'Y' -> 2 + 3
                        'Z' -> 3 + 6
                        else -> 0
                    }
                }

                'C' -> {
                    when (it.second) {
                        'X' -> 2 + 0
                        'Y' -> 3 + 3
                        'Z' -> 1 + 6
                        else -> 0
                    }
                }

                else -> 0
            }

        }
        .sum()
}

//✔️️ 15 		 ⏳ 48.617733ms
//✔️️ 12535 		 ⏳ 26.168046ms
//✔️️ 12 		 ⏳ 530.075us
//✔️️ 15457 		 ⏳ 8.586528ms
