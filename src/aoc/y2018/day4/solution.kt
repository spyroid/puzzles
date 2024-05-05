package aoc.y2018.day4

import gears.puzzle
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private fun main() {
    puzzle { reposeRecord(inputLines()) }
}

private fun reposeRecord(lines: List<String>): Any {
    var id = 0
    val map = mutableMapOf<Int, MutableList<Int>>()

    lines.map { convert(it) }.sortedBy { it.timestamp }.windowed(2).forEach { (a, b) ->
        when (a.data.first().first()) {
            'G' -> id = a.data[1].drop(1).toInt()
            'f' -> map.computeIfAbsent(id) { mutableListOf() }.addAll(IntRange(a.timestamp.minute, b.timestamp.minute - 1))
        }
    }
    val map2 = map.map { (k, v) -> Triple(k, v.size, v.groupingBy { it }.eachCount().maxBy { it.value }) }
    return map2.maxBy { it.second }.let { (a, _, c) -> a * c.key } to map2.maxBy { it.third.value }.let { (a, _, c) -> a * c.key }
}

private fun convert(string: String) = string.split("] ").let { (a, b) ->
    LogEntry(LocalDateTime.parse(a.drop(1), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), b.split(" "))
}

data class LogEntry(val timestamp: LocalDateTime, val data: List<String>)