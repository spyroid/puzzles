package aoc.y2023.day19

import gears.puzzle

private fun main() {
    puzzle("1 & 2") { aplenty(inputLines()) }
}

private fun aplenty(input: List<String>): Any {
    val workflows = mutableMapOf<String, Workflow>()
    val parts = mutableListOf<Map<String, Int>>()
    input.onEach { l ->
        if (l.startsWith('{')) {
            val part = mutableMapOf<String, Int>()
            l.substring(1, l.length - 1).split(',').forEach {
                part[it.split('=')[0]] = it.split('=')[1].toInt()
            }
            parts.add(part)
        } else if (l.isNotBlank()) {
            val items = l.substring(l.indexOf('{') + 1, l.length - 1).split(',')
            workflows[l.substring(0, l.indexOf('{'))] = Workflow(
                items.dropLast(1).map {
                    listOf(it[0].toString(), it[1].toString(), it.substring(2, it.indexOf(':')), it.substring(it.indexOf(':') + 1))
                }.toList(),
                items.last()
            )
        }
    }
    var res1 = 0L
    var res2 = 0L
    for (part in parts) {
        var wf = "in"
        do {
            var matched = false
            for (rule in workflows[wf]!!.rules) {
                val value = part[rule[0]]!!
                val arg = rule[2].toInt()
                matched = if (rule[1] == "<") value < arg else value > arg
                if (matched) {
                    wf = rule[3]; break
                }
            }
            if (!matched) wf = workflows[wf]!!.default
        } while (wf != "A" && wf != "R")
        if (wf == "A") res1 += part["x"]!! + part["m"]!! + part["a"]!! + part["s"]!!
    }
    val queue = ArrayDeque<Pair<String, MutableMap<String, IntRange>>>()
    queue.add(Pair("in", mutableMapOf("x" to 1..4000, "m" to 1..4000, "a" to 1..4000, "s" to 1..4000)))
    while (!queue.isEmpty()) {
        val r = queue.removeFirst()
        fun count(category: String) = r.second[category]!!.count().toLong()
        if (r.first == "A") {
            res2 += count("x") * count("m") * count("a") * count("s")
            continue
        } else if (r.first == "R") continue
        var ranges = r.second
        for (rule in workflows[r.first]!!.rules) {
            val part = rule[0]
            val arg = rule[2].toInt()
            val matched = ranges.toMutableMap()
            val left = ranges.toMutableMap()
            when (rule[1]) {
                "<" -> {
                    matched[part] = ranges[part]!!.first..<arg
                    left[part] = arg..ranges[part]!!.last
                }

                ">" -> {
                    matched[part] = (arg + 1)..ranges[part]!!.last
                    left[part] = ranges[part]!!.first..arg
                }
            }
            if (matched[part]!!.first <= matched[part]!!.last) {
                queue.add(Pair(rule[3], matched))
            }
            if (left[part]!!.first <= left[part]!!.last) {
                ranges = left
            } else {
                ranges.clear()
                break
            }
        }
        if (ranges.isNotEmpty()) queue.add(Pair(workflows[r.first]!!.default, ranges))
    }
    return res1 to res2
}

private data class Workflow(val rules: List<List<String>>, val default: String)
