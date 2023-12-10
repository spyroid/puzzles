package aoc.y2020.day08

import gears.puzzle

private fun main() {
    puzzle { part1(inputLines("test.txt").asCPU()) }
    puzzle { part1(inputLines("input.txt").asCPU()) }
    puzzle { part2(inputLines("test.txt").asCPU()) }
    puzzle { part2(inputLines("input.txt").asCPU()) }
}

private fun part1(cpu: List<Pair<String, Int>>): Pair<Int, Int> {
    var acc = 0
    var ip = 0
    val set = mutableSetOf<Int>()
    while (!set.contains(ip) && ip in cpu.indices) {
        val op = cpu[ip]
        set.add(ip)
        when (op.first) {
            "acc" -> acc += op.second.also { ip += 1 }
            "jmp" -> ip += op.second
            else -> ip += 1
        }
    }
    return Pair(acc, ip)
}

private fun part2(cpu: List<Pair<String, Int>>): Pair<Int, Int> {
    for ((i, op) in cpu.withIndex()) {
        if (op.first == "acc") continue
        val newOp = if (op.first == "jmp") "nop" else "jmp"
        val patched = cpu.toMutableList().also { it[i] = Pair(newOp, it[i].second) }
        val res = part1(patched)
        if (res.second == cpu.size) return res
    }
    return Pair(0, 0)
}

private fun List<String>.asCPU(): List<Pair<String, Int>> {
    return this.map { s -> s.split(" ").let { Pair(it.first(), it.last().toInt()) } }
}
