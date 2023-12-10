package aoc.y2015.day14

import gears.puzzle
import kotlin.math.min

private fun main() {
    puzzle {
        part1(inputLines("test.txt"), 1000)
    }
    puzzle {
        part1(inputLines("input.txt"), 2503)
    }
    puzzle {
        part2(inputLines("test.txt"), 1000)
    }
    puzzle {
        part2(inputLines("input.txt"), 2503)
    }
}

private fun part1(lines: List<String>, clock: Int) = lines.maxOf { dist(it, clock) }

private fun part2(lines: List<String>, clock: Int): Int {
    val map = mutableMapOf<Int, Int>()
    for (now in 1..clock) {
        lines.mapIndexed { i, line -> i to dist(line, now) }
            .sortedByDescending { it.second }
            .let { all ->
                all.takeWhile { all.first().second == it.second }
                    .forEach { map[it.first] = map.getOrDefault(it.first, 0) + 1 }
            }
    }
    return map.values.max()
}

private val re = ".+ (\\d+) .+ (\\d+) .+ (\\d+) .+".toRegex()
private fun dist(line: String, clock: Int): Int {
    val (speed, time, delay) = re.find(line)?.groupValues?.drop(1)?.map { it.toInt() } ?: throw RuntimeException()
    val a = clock / (time + delay) * (speed * time)
    val b = clock % (time + delay)
    val c = min(b, time) * speed
    return a + c
}
