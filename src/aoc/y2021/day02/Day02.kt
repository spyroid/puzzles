package aoc.y2021.day02

import gears.puzzle

fun main() {
    puzzle {
        part1(linesFrom("input.txt").asCommands())
    }
    puzzle {
        part2(linesFrom("input.txt").asCommands())
    }
}

private data class Command(val op: String, val value: Int)

private fun part1(seq: Sequence<Command>): Int {
    var depth = 0
    var horizontal = 0
    seq.forEach {
        when (it.op) {
            "forward" -> horizontal += it.value
            "down" -> depth += it.value
            "up" -> depth -= it.value
        }
    }
    return depth * horizontal
}

private fun part2(seq: Sequence<Command>): Int {
    var depth = 0
    var horizontal = 0
    var aim = 0
    seq.forEach {
        when (it.op) {
            "forward" -> {
                horizontal += it.value
                depth += it.value * aim
            }

            "down" -> aim += it.value
            "up" -> aim -= it.value
        }
    }
    return depth * horizontal
}

private fun List<String>.asCommands() = this.asSequence().map { it.split(" ") }.map { Command(it[0], it[1].toInt()) }

