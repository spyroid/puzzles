package aoc.y2017.day8

import gears.puzzle

private fun main() {
    puzzle { registers(inputLines()) }
}

private val re = "\\S+".toRegex()
private fun registers(input: List<String>): Any {
    val map = mutableMapOf<String, Int>()
    var max = 0
    input.map {
        re.findAll(it).map { a -> a.value }.toList()
            .let { b -> Command(b[0], if (b[1] == "inc") b[2].toInt() else -b[2].toInt(), b[4], b[5], b[6].toInt()) }
    }
        .forEach { c ->
            val r2 = map.getOrPut(c.reg2) { 0 }
            val flag = when (c.op2) {
                ">=" -> r2 >= c.v2
                ">" -> r2 > c.v2
                "==" -> r2 == c.v2
                "!=" -> r2 != c.v2
                "<=" -> r2 <= c.v2
                else -> r2 < c.v2
            }
            if (flag) map.compute(c.reg1) { _, v -> (v ?: 0) + c.v1 }.also { max = maxOf(it ?: 0, max) }
        }
    return map.values.maxByOrNull { it } to max
}

private data class Command(val reg1: String, val v1: Int, val reg2: String, val op2: String, val v2: Int)
