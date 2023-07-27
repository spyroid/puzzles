package aoc.y2015.day11

import gears.puzzle

private fun main() {
    val pass = puzzle {
        generateSequence("hxbxwxba") { next(it) }.first { valid(it) }
    }
    puzzle {
        generateSequence(next(pass)) { next(it) }.first { valid(it) }
    }
}

private val re = "(\\w)\\1.*(\\w)\\2".toRegex()
private fun valid(s: String) = !s.contentEquals("iol")
        && re.containsMatchIn(s)
        && s.windowed(3) { it[1] == it[0] + 1 && it[2] == it[0] + 2 }.any { it }

private fun next(s: String): String = s.dropLast(1).let { if (s.last() == 'z') next(it) + 'a' else it + (s.last() + 1) }

