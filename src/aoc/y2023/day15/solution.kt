package aoc.y2023.day15

import gears.puzzle

private fun main() {
    puzzle("1 & 2") { lensLibrary(input()) }
}

private fun lensLibrary(input: String): Any {
    val data = input.split(",")
    val part1 = data.sumOf { it.HASH() }

    val map = mutableMapOf<Int, ArrayDeque<Step>>()
    data.map { Step.of(it) }.onEach { s ->
        if (s.op == '-') map[s.id.HASH()]?.removeAll { s.id == it.id } else map.compute(s.id.HASH()) { a, b ->
            b?.also { ss ->
                val idx = ss.indexOfFirst { it.id == s.id }
                if (idx == -1) ss.add(s) else ss[idx] = s
            } ?: ArrayDeque<Step>().also { it.add(s) }
        }
    }
    val part2 = map.filterValues { it.isNotEmpty() }
        .map { (it.key + 1) * it.value.foldIndexed(0) { i, acc, step -> acc + ((i + 1) * step.length) } }
        .sum()

    return part1 to part2
}

private fun String.HASH() = this.fold(0) { acc, c -> (acc + c.code) * 17 % 256 }

private data class Step(val id: String, val op: Char, val length: Int = -1) {
    companion object {
        fun of(s: String): Step {
            val (a, b) = s.split("[-=]".toRegex())
            return if (b.isEmpty()) Step(a, '-') else Step(a, '=', b.toInt())
        }
    }
}
