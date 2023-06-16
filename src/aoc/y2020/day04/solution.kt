package aoc.y2020.day04

import gears.puzzle

private fun main() {
    puzzle { part1(linesFrom("test.txt")) }
    puzzle { part1(linesFrom("input.txt")) }
    puzzle { part2(linesFrom("test.txt")) }
    puzzle { part2(linesFrom("input.txt")) }
}

private fun part1(lines: List<String>) = parsePassports(lines).count { map -> map.size == 7 }

private fun part2(lines: List<String>): Int {
    return parsePassports(lines)
        .count { pass ->
            pass.size == 7 && pass.map { p -> validate(p.toPair()) }.fold(true) { acc, p -> acc && p }
        }
}

private fun validate(p: Pair<String, String>): Boolean {
    val v = p.second
    val r = when (p.first) {
        "byr" -> v.toIntOrNull() in (1920..2002)
        "iyr" -> v.toIntOrNull() in (2010..2020)
        "eyr" -> v.toIntOrNull() in (2020..2030)
        "hgt" -> {
            val match = Regex("^(\\d+)(cm|in)\$").find(v)
            if (match != null) {
                val (hv, hm) = match.destructured
                return when (hm) {
                    "cm" -> hv.toInt() in (150..193)
                    "in" -> hv.toInt() in (59..76)
                    else -> false
                }
            }
            false
        }

        "hcl" -> v.matches("^#[0-9a-f]{6}$".toRegex())
        "ecl" -> v.matches("^(?:amb|blu|brn|gry|grn|hzl|oth)$".toRegex())
        "pid" -> v.matches("^\\d{9}$".toRegex())
        else -> true
    }
    return r
}

private fun parsePassports(lines: List<String>): Sequence<Map<String, String>> = sequence {
    var part = ""
    for (line in lines) {
        if (line.isEmpty()) {
            yield(part)
            part = ""
        }
        part = part.plus(" $line")
    }
    yield(part)
}
    .map {
        it.split(" ")
            .filter { z -> z.isNotEmpty() }
            .map { a -> a.split(":").let { b -> Pair(b[0], b[1]) } }
    }
    .map { a ->
        a.filter { it.first != "cid" }.associate { b -> b }
    }
