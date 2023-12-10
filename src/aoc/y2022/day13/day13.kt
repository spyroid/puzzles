package aoc.y2022.day13

import gears.puzzle

fun main() {
    puzzle("t1") { part1(inputLines("test.txt")) }
    puzzle("1") { part1(inputLines("input.txt")) }
    puzzle("t2") { part2(inputLines("test.txt")) }
    puzzle("2") { part2(inputLines("input.txt")) }
}

private fun parseList(line: String, idx: Int = 1): Pair<Value, Int> {
    var i = idx
    val list = buildList {
        while (i < line.length) {
            when (line[i]) {
                '[' -> parseList(line, i + 1).also {
                    add(it.first)
                    i += it.second
                }

                ']' -> return Pair(Value.ListValue(this), i)
                in '0'..'9' -> {
                    val v = line.subSequence(i, line.length - 1).takeWhile { it.isDigit() }.toString()
                    add(Value.IntValue(v.toInt()))
                    i += v.length - 1
                }
            }
            i += 1
        }
    }
    return Pair(Value.ListValue(list), i)
}


private fun part1(input: List<String>): Int {
    return input.chunked(3)
        .map { Pair(parseList(it[0]).first, parseList(it[1]).first) }
        .mapIndexed { index, pair -> if (pair.first < pair.second) index + 1 else 0 }
        .sum()
}

private fun part2(input: List<String>): Int {
    val d1 = parseList("[[2]]").first
    val d2 = parseList("[[6]]").first

    return input.asSequence()
        .filter { it.isNotBlank() }
        .map { parseList(it).first }
        .plus(listOf(d1, d2))
        .sorted()
        .mapIndexed { i, v -> if (v == d1 || v == d2) i + 1 else 1 }
        .fold(1, Int::times)
}

private interface Value : Comparable<Value> {
    data class IntValue(val value: Int) : Value
    data class ListValue(val value: List<Value>) : Value

    fun IntValue.toList() = ListValue(listOf(this))

    override operator fun compareTo(that: Value): Int {
        when {
            this is IntValue && that is IntValue -> return this.value.compareTo(that.value)

            this is ListValue && that is ListValue -> {
                repeat(minOf(this.value.size, that.value.size)) { i ->
                    val comp = this.value[i].compareTo(that.value[i])
                    if (comp != 0) return comp
                }
                return this.value.size.compareTo(that.value.size)
            }

            else -> when {
                this is IntValue -> return this.toList().compareTo(that)
                that is IntValue -> return this.compareTo(that.toList())
            }
        }
        return 0
    }
}
