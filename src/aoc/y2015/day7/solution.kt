package aoc.y2015.day7

import gears.puzzle

private fun main() {
    puzzle { part1(linesFrom("test.txt"), "y") }
    puzzle { part1(linesFrom("input.txt"), "a") }
    puzzle { part2(linesFrom("input.txt"), "a") }
}

private fun part2(lines: List<String>, wire: String) = part1(lines, wire, part1(lines, wire))

private fun part1(lines: List<String>, wire: String, b: UShort? = null): UShort {

    val cpu = mutableMapOf<String, UShort>()
    if (b != null) cpu["b"] = b

    val commands = lines.map { parse(it) }.groupBy { it.dest }.mapValues { it.value.first() }

    fun find(ip: String): UShort {
        val cached = cpu[ip]
        if (cached != null) return cached
        val c = commands[ip] ?: throw RuntimeException("Error $ip")
        val res = when (c.ip) {
            "SET" -> c.av ?: find(c.a!!)
            "NOT" -> (c.av ?: find(c.a!!)).inv().and(65535.toUShort())
            "AND" -> (c.av ?: find(c.a!!)) and (c.bv ?: find(c.b!!))
            "OR" -> (c.av ?: find(c.a!!)) or (c.bv ?: find(c.b!!))
            "LSHIFT" -> (c.av ?: find(c.a!!)).toUInt().shl((c.bv!!).toInt()).toUShort()
            "RSHIFT" -> (c.av ?: find(c.a!!)).toUInt().shr((c.bv!!).toInt()).toUShort()
            else -> throw RuntimeException("Error $ip")
        }
        cpu[ip] = res
        return res
    }

    return find(wire)
}

private val r1 = "(\\w+) -> (\\w+)".toRegex()
private val r2 = "(\\w+)\\s(\\w+)\\s(\\w+) -> (\\w+)".toRegex()
private val r3 = "NOT\\s(\\w+) -> (\\w+)".toRegex()
private fun parse(s: String): Command {
    var res = r3.find(s)
    if (res != null) {
        val (a, dest) = res.groupValues.drop(1)
        val aa = a.toUShortOrNull()
        return Command(
            ip = "NOT",
            dest = dest,
            a = if (aa == null) a else null,
            av = aa,
        )
    }
    res = r2.find(s)
    if (res != null) {
        val (a, ip, b, dest) = res.groupValues.drop(1)
        val aa = a.toUShortOrNull()
        val bb = b.toUShortOrNull()
        return Command(
            ip = ip, dest = dest,
            a = if (aa == null) a else null,
            b = if (bb == null) b else null,
            av = aa,
            bv = bb,
        )
    }
    res = r1.find(s)!!
    val (a, dest) = res.groupValues.drop(1)
    val aa = a.toUShortOrNull()
    return Command(
        ip = "SET", dest = dest,
        a = if (aa == null) a else null,
        av = aa,
    )
}

data class Command(
    val ip: String, val dest: String,
    val a: String? = null, val b: String? = null,
    val av: UShort? = null, val bv: UShort? = null,
)
