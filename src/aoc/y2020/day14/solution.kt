package aoc.y2020.day14

import gears.puzzle

private fun main() {
    puzzle { part1(inputLines("test.txt")) }
    puzzle { part1(inputLines("input.txt")) }
    puzzle { part2(inputLines("test2.txt")) }
    puzzle { part2(inputLines("input.txt")) }
}

private fun part2(input: List<String>): Long {
    var mask = ""
    val mem = mutableMapOf<Long, Long>()

    for (line in input) {
        if (line.startsWith("mask")) {
            mask = line.drop(7)
        } else {
            val parts = line.drop(4).split("] = ")
            val addr = parts[0].toInt()
            val addr2 = addr.toString(2).padStart(36, '0')

            val v = parts[1].toLong()
            val maskedAddr = mask.mapIndexed { i, c -> if (c == '0') addr2[i] else c }.joinToString("")
            var addrs = mutableListOf(maskedAddr)
            while (addrs.first().contains('X')) {
                val aa = mutableListOf<String>()
                for (a in addrs) {
                    val i = a.indexOfFirst { it == 'X' }
                    a.toCharArray().also {
                        it[i] = '1'
                        aa.add(it.joinToString(""))
                        it[i] = '0'
                        aa.add(it.joinToString(""))
                    }
                }
                addrs = aa
            }
            addrs.forEach { mem[it.toLong(2)] = v }
        }
    }

    return mem.values.sumOf { it }
}

private fun part1(input: List<String>): Long {
    var mask = ""
    val mem = mutableMapOf<Int, String>()

    for (line in input) {
        if (line.startsWith("mask")) {
            mask = line.drop(7)
        } else {
            val parts = line.drop(4).split("] = ")
            val addr = parts[0].toInt()
            val v = parts[1].toInt().toString(2).padStart(36, '0')
            mem[addr] = mask.mapIndexed { i, c -> if (c == 'X') v[i] else c }.joinToString("")
        }
    }

    return mem.values.map { it.toLong(2) }.sumOf { it }
}

