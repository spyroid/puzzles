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

private fun part1(seq: Sequence<Command2>) = Engine().input(seq).magicNumber()

private fun part2(seq: Sequence<Command2>) = EngineExt().input(seq).magicNumber()

private data class Command2(val op: String, val value: Int)

private open class Engine {
    var depth = 0
    var horizontal = 0

    fun input(seq: Sequence<Command2>): Engine {
        seq.forEach {
            when (it.op) {
                "forward" -> forward(it.value)
                "down" -> down(it.value)
                "up" -> up(it.value)
            }
        }
        return this
    }

    open fun forward(value: Int) {
        horizontal += value
    }

    open fun up(value: Int) {
        depth -= value
    }

    open fun down(value: Int) {
        depth += value
    }

    fun magicNumber() = depth * horizontal
}

private class EngineExt : Engine() {
    var aim = 0

    override fun forward(value: Int) {
        horizontal += value
        depth += value * aim
    }

    override fun down(value: Int) {
        aim += value
    }

    override fun up(value: Int) {
        aim -= value
    }
}

private fun List<String>.asCommands() = this.asSequence().map { it.split(" ") }.map { Command2(it[0], it[1].toInt()) }
