package aoc.y2015.day14

import gears.puzzle

private fun main() {
    puzzle {
        part1(linesFrom("test.txt"), 1000)
    }
    puzzle {
        part1(linesFrom("input.txt"), 2503)
    }
}

private val re = ".+ (\\d+) .+ (\\d+) .+ (\\d+) .+".toRegex()
private fun part1(lines: List<String>, clock: Int): Int {
    return lines.maxOf { line ->
        val (speed, time, delay) = re.find(line)?.groupValues?.drop(1)?.map { it.toInt() } ?: throw RuntimeException()
        val a = clock / (time + delay) * (speed * time)
        val b = clock % (time + delay)
        val c = if (b > time) (speed * time) else 0
        a + c
    }
}
