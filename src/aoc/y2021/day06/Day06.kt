package aoc.y2021.day06

fun main() {

    fun liveOneDay(map: Map<Int, Long>): Map<Int, Long> {
        return map
            .mapKeys { it.key - 1 }
            .toMutableMap()
            .also {
                val m1 = it.getOrDefault(-1, 0)
                it.merge(6, m1) { a, b -> a + b }
                it.merge(8, m1) { a, b -> a + b }
                it.remove(-1)
            }
    }

    fun part1(seq: Map<Int, Long>, days: Int): Long {
        var mm = seq
        return repeat(days) {
            mm = liveOneDay(mm)
        }.let {
            mm.values.sum()
        }
    }

    fun inputToMap(str: String): Map<Int, Long> {
        return str.split(",")
            .map { it.toInt() }
            .groupingBy { it }
            .eachCount()
            .mapValues { (_, v) -> v.toLong() }
    }

//    val testSeq = readInput("day06/test").map { inputToMap(it) }.first()
//    val inputSeq = readInput("day06/input").map { inputToMap(it) }.first()
//
//    val res1 = part1(testSeq, 80)
//    check(res1 == 5934L) { "Expected 5934 but got $res1" }
//    println("Part1: ${part1(inputSeq, 80)}")
//    println("Part2: ${part1(inputSeq, 256)}")
}
