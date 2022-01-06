package aoc.y2021.day01

import readToIntSeq

fun main() {

    fun part1(seq: Sequence<Int>) = seq.windowed(2, 1).count { (it[1] > it[0]) }

    fun part2(seq: Sequence<Int>) = part1(seq.windowed(3, 1).map { it.sum() })

    val testSeq = readToIntSeq("day01/test")
    check(part1(testSeq) == 7)
    check(part2(testSeq) == 5)

    println("--------------------------------")
    val seq = readToIntSeq("day01/input")
    println(part1(seq))
    println(part2(seq))
}
