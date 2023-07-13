package aoc.y2020.day16

import gears.puzzle

private fun main() {
    puzzle("test") {
        all(linesFrom("test.txt").let { Box.of(it) })
    }
    puzzle {
        all(linesFrom("input.txt").let { Box.of(it) })
    }
}

private fun all(box: Box): Pair<Int, Long> {
    var sum = 0
    for (r in box.otherTickets) {
        for ((i, t) in r.withIndex()) {
            if (box.allValidators().map { v -> !v.contains(t) }.all { it }) {
                sum += t
                r[i] = -1
            }
        }
    }

    val map = mutableMapOf<String, MutableSet<Int>>()
    for (x in box.otherTickets.first().indices) {
        for (ve in box.validators) {
            var all = true
            for (y in box.otherTickets.indices) {
                val t = box.otherTickets[y][x]
                if (t == -1) continue
                all = all && (ve.value.map { it.contains(t) }.any { it })
            }
            if (all) map.getOrPut(ve.key) { mutableSetOf() }.add(x)
        }
    }

    val map2 = mutableMapOf<String, Int>()
    while (map.isNotEmpty()) {
        val e = map.entries.first { it.value.size == 1 }
        map2[e.key] = e.value.first()
        map.remove(e.key)
        map.forEach { (_, s) -> s.remove(e.value.first()) }
    }

    val mul = map2.filterKeys { it.startsWith("departure") }
        .map { box.ticket[it.value] }
        .fold(1L) { acc, v -> acc * v }

    return Pair(sum, mul)
}

private class Box {
    var ticket = listOf<Int>()
    var otherTickets = mutableListOf<MutableList<Int>>()
    val validators = mutableMapOf<String, List<IntRange>>()

    fun allValidators() = sequence { validators.forEach { yieldAll(it.value) } }

    companion object {
        fun of(lines: List<String>): Box {
            val box = Box()
            var section = 1
            for (line in lines) {
                if (line.isEmpty()) {
                    section += 1
                    continue
                } else if (section == 1) {
                    line.split(": ").also { a ->
                        val ranges = a.last().split(" or ").map { b ->
                            b.split("-").let { it.first().toInt()..it.last().toInt() }
                        }
                        box.validators[a.first()] = ranges
                    }
                } else if (section == 2) {
                    if (line.startsWith("your")) continue
                    box.ticket = line.split(",").map { it.toInt() }.toList()
                } else if (section == 3) {
                    if (line.startsWith("nearby")) continue
                    box.otherTickets.add(line.split(",").map { it.toInt() }.toMutableList())
                }
            }
            return box
        }
    }
}
