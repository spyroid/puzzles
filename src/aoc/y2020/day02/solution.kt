package aoc.y2020.day02

import gears.puzzle

private fun main() {
    puzzle { part1(inputLines("test.txt")) }
    puzzle { part1(inputLines("input.txt")) }
    puzzle { part2(inputLines("test.txt")) }
    puzzle { part2(inputLines("input.txt")) }
}

private fun part1(all: List<String>): Int = all.map { Policy.of(it).isValid() }.count { it }
private fun part2(all: List<String>): Int = all.map { Policy.of(it).isValidOTCAS() }.count { it }

private data class Policy(val range: IntRange, val c: Char, val password: String) {
    companion object {
        val regex = """(\d+)-(\d+) (.): (\w+)""".toRegex()
        fun of(s: String): Policy {
            val matchResult = regex.find(s)
            val (from, to, c, password) = matchResult!!.destructured
            return Policy((from.toInt()..to.toInt()), c[0], password)
        }
    }

    fun isValid() = range.contains(password.count { it == c })
    fun isValidOTCAS() = (password[range.first - 1] == c) xor (password[range.last - 1] == c)
}
