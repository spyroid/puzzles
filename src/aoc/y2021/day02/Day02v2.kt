package aoc.y2021.day02

fun main() {

    data class Command(val op: String, val value: Int)

    open class Engine {
        var depth = 0
        var horizontal = 0

        fun input(seq: Sequence<Command>): Engine {
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

    class EngineExt : Engine() {
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

    fun part1(seq: Sequence<Command>) = Engine().input(seq).magicNumber()

    fun part2(seq: Sequence<Command>) = EngineExt().input(seq).magicNumber()

    fun mapCommand(str: String): Command {
        val parts = str.split(" ")
        return Command(parts[0], parts[1].toInt())
    }

//    val testSeq = readInput("day02/test").asSequence().map { mapCommand(it) }
//    val seq = readInput("day02/input").asSequence().map { mapCommand(it) }
//
//    val res1 = part1(testSeq)
//    check(res1 == 150) { "Expected 150 but got $res1" }
//    println("Part1: " + part1(seq))
//
//    val res2 = part2(testSeq)
//    check(res2 == 900) { "Expected 900 but got $res2" }
//    println("Part2: " + part2(seq))
}
