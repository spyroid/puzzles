package aoc.y2021.day03

enum class Type(val order: Int) {
    OXY(1), CO2(-1)
}

fun main() {

    fun part1(seq: List<String>): Int {
        val counts = IntArray(seq.first().length) { idx ->
            if (seq.sumOf { (if (it[idx].digitToInt() == 0) -1L else 1L) } > 0) 1 else 0
        }

        val gamma = counts.fold(0) { acc, el -> (acc shl 1) + el }
        val epsilon = counts.fold(0) { acc, el -> (acc shl 1) + el xor 1 }
        return gamma * epsilon
    }

    fun part2(seq: List<String>): Int {

        fun findMeasure(seq: List<String>, type: Type, idx: Int = 0): Int {
            val group = seq.groupBy { it[idx].digitToInt() }
                .entries
                .sortedWith(compareBy({ it.value.size * -type.order }, { it.key * -type.order }))
                .first().value

            if (group.size == 1) return group.first().toInt(2)
            return findMeasure(group, type, idx + 1)
        }

        val oxy = findMeasure(seq, Type.OXY)
        val co2 = findMeasure(seq, Type.CO2)

        return oxy * co2
    }

//    val testSeq = readInput("day03/test")
//    val inputSeq = readInput("day03/input")
//
//    val res1 = part1(testSeq)
//    check(res1 == 198) { "Expected 198 but got $res1" }
//    println("Part1: ${part1(inputSeq)}")
//
//    val res2 = part2(testSeq)
//    check(res2 == 230) { "Expected 900 but got $res2" }
//    println("Part2: ${part2(inputSeq)}")
}
