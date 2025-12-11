package aoc.y2025.day11

import gears.puzzle

fun main() {
    puzzle {
        reactor(inputLines())
    }
}

private fun reactor(input: List<String>): Any {
    val devices = input.map { line -> line.split(": ") }.associate { (a, b) -> a to b.split(" ") }

    val part1 = {
        fun find(device: String): Long = if (device == "out") 1L else devices[device]?.sumOf { find(it) } ?: 0L
        find("you")
    }
    val part2 = {
        fun find(device: String, a: Boolean = false, b: Boolean = false, memo: MutableMap<Triple<String, Boolean, Boolean>, Long> = mutableMapOf()): Long {
            return if (device == "out") {
                if (a && b) 1L else 0L
            } else {
                memo.getOrPut(Triple(device, a, b)) {
                    devices[device]?.sumOf {
                        find(it, (a || device == "fft"), (b || device == "dac"), memo)
                    } ?: 0L
                }
            }
        }
        find("svr")
    }

    return part1() to part2()
}
