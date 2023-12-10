package aoc.y2016.day7

import gears.puzzle

private fun main() {
    puzzle { ip71(inputLines("input.txt")) }
    puzzle { ip72(inputLines("input.txt")) }
}

private val re = "(\\[\\w+])".toRegex()
private fun hypers(s: String) = re.findAll(s).map { it.value }
private fun hasPairs(s: String) = s.windowed(4).any { w -> w[0] == w[3] && w[1] == w[2] && w[0] != w[1] }

private fun ip71(lines: List<String>) = lines.count { line -> line.split(re).any { hasPairs(it) } && hypers(line).all { !hasPairs(it) } }

private fun ip72(lines: List<String>) = lines.count { line ->
    line.split(re).any { aa ->
        aa.windowed(3).any { w -> w[0] == w[2] && hypers(line).any { b -> b.contains("${w[1]}${w[0]}${w[1]}") } }
    }
}
