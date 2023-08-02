package nik

import gears.puzzle

fun main() {
    puzzle { solution("-H-HH--") }
    puzzle { solution("H") }
    puzzle { solution("H-") }
    puzzle { solution("HH-HH") }
    puzzle { solution("-H-H-H-H-H") }
    puzzle { solution("--") }
    puzzle { solution("HHH") }
}

private fun solution(line: String): Int {
    if (line.length < 2) return -1
    val homes = mutableSetOf<Int>()
    val jars = mutableSetOf<Int>()
    line.windowed(3).onEachIndexed { i, s ->
        if (s == "H-H" && !homes.contains(i) && !homes.contains(i + 2)) {
            homes.add(i)
            homes.add(i + 2)
            jars.add(i + 1)
        }
    }
    line.windowed(2).onEachIndexed { i, s ->
        if (s == "-H" && !homes.contains(i + 1)) {
            homes.add(i + 1)
            jars.add(i)
        }
    }
    line.windowed(2).onEachIndexed { i, s ->
        if (s == "H-" && !homes.contains(i)) {
            homes.add(i)
            jars.add(i + 1)
        }
    }
    return if (jars.isNotEmpty() && line.count { it == 'H' } == homes.size) jars.size else -1
}
