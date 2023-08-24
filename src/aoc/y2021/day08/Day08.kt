package aoc.y2021.day08

fun main() {

    fun part1(seq: Sequence<Box>) = seq.map { box -> box.numbers().count { it in listOf(1, 4, 7, 8) } }.sum()

    fun part2(seq: Sequence<Box>) = seq.map { box -> box.asValue() }.sum()


//    val testSeq = readDigits(readInput("day08/test"))
//    val inputSeq = readDigits(readInput("day08/input"))
//
//    var res1 = part1(testSeq)
//    check(res1 == 26) { "Expected 26 but got $res1" }
//
//    var time = measureTimeMillis { res1 = part1(inputSeq) }
//    println("Part1: $res1 in $time ms")
//
//    time = measureTimeMillis { res1 = part2(inputSeq) }
//    println("Part2: $res1 in $time ms")
}

fun readDigits(list: List<String>): Sequence<Box> {
    return list.map { line ->
        line
            .split("|", " ")
            .filter { it.isNotBlank() }
            .map { it.toSet() }
            .let { Box(it.take(10), it.takeLast(4)) }
    }.asSequence()
}

data class Box(val left: List<Set<Char>>, var right: List<Set<Char>>) {

    private val mappings = findMappings()

    fun asValue() = right.map { mappings.getValue(it) }.joinToString("").toInt()

    fun numbers() = right.map { mappings.getValue(it) }

    private fun findMappings(): Map<Set<Char>, Int> {
        val digits = Array<Set<Char>>(10) { emptySet() }

        digits[1] = left.first { it.size == 2 }
        digits[4] = left.first { it.size == 4 }
        digits[7] = left.first { it.size == 3 }
        digits[8] = left.first { it.size == 7 }
        digits[3] = left
            .filter { it.size == 5 }
            .first { it.containsAll(digits[1]) }

        digits[9] = left
            .filter { it.size == 6 }
            .first { it.containsAll(digits[3]) }

        digits[0] = left
            .filter { it.size == 6 }
            .filter { it.containsAll(digits[1]) && it.containsAll(digits[7]) }
            .first { it != digits[9] }

        digits[6] = left
            .filter { it.size == 6 }
            .first { it != digits[0] && it != digits[9] }

        digits[5] = left
            .filter { it.size == 5 }
            .first { digits[6].containsAll(it) }

        digits[2] = left
            .filter { it.size == 5 }
            .first { it != digits[3] && it != digits[5] }

        return digits.mapIndexed { index, chars -> chars to index }.toMap()
    }
}
