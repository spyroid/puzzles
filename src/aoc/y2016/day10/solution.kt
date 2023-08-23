package aoc.y2016.day10

import gears.puzzle

private fun main() {
    puzzle { bots(linesFrom("input.txt")) }
}

private val re1 = "\\d+".toRegex()
private val re2 = "(bot|output)".toRegex()

private fun bots(lines: List<String>): Int {
    val bots = mutableMapOf<Int, MutableList<Int>>()
    val outs = mutableMapOf<Int, MutableList<Int>>()
    val pipe = mutableMapOf<Int, List<Pair<String, Int>>>()
    for (line in lines) {
        if (line.startsWith("value")) {
            val (v, b) = re1.findAll(line).map { it.groupValues.first() }.toList()
            bots[b.toInt()] = bots.getOrDefault(b.toInt(), mutableListOf()).also { it.add(v.toInt()) }
        } else {
            val (b, v1, v2) = re1.findAll(line).map { it.groupValues.first() }.toList()
            val (o1, o2) = re2.findAll(line).map { it.groupValues.first() }.toList()
            pipe[b.toInt()] = listOf(o1 to v1.toInt(), o2 to v2.toInt())
        }
    }

    while (bots.isNotEmpty()) {

        for ((k, v) in bots.entries.toList()) {
            if (v.size == 2) {
                val vv = v.sorted().also { bots.remove(k) }
                if (vv[0] == 17 && vv[1] == 61) println(k)
                pipe[k]?.forEachIndexed { i, p ->
                    if (p.first == "bot") {
                        bots[p.second] = bots.getOrDefault(p.second, mutableListOf()).also { it.add(vv[i]) }
                    } else {
                        outs[p.second] = outs.getOrDefault(p.second, mutableListOf()).also { it.add(vv[i]) }
                    }
                }
            }
        }
        val w = 1
    }

    return 0
}
