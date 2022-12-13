package aoc.y2021.day12

import gears.readInput
import kotlin.system.measureTimeMillis

fun main() {

    fun find1(map: Map<String, List<String>>, el: String, target: String, visited: MutableSet<String>, cPath: MutableList<String>, distinctPaths: MutableSet<List<String>>) {
        if (el == target) {
            val copy = cPath.toMutableList()
            copy += target
            distinctPaths += copy
            return
        }

        if (el in visited) {
            return
        }

        if (el[0].isLowerCase()) {
            visited += el
        }

        cPath += el
        for (v in map[el]!!) {
            find1(map, v, target, visited, cPath, distinctPaths)
        }

        if (el[0].isLowerCase()) {
            visited -= el
        }
        cPath.removeAt(cPath.lastIndex)
    }

    fun toMap(input: List<String>): Map<String, List<String>> {
        val adj = mutableMapOf<String, MutableList<String>>()
        for (line in input) {
            val (u, v) = line.split("-")
            adj.getOrPut(u) { mutableListOf() } += v
            adj.getOrPut(v) { mutableListOf() } += u
        }
        return adj
    }

    fun part1(input: List<String>): Int {
        val paths = mutableSetOf<List<String>>()
        find1(toMap(input), "start", "end", mutableSetOf(), mutableListOf(), paths)
        return paths.size
    }

    fun find2(map: Map<String, List<String>>, el: String, target: String, visited: MutableMap<String, Int>, cPath: MutableList<String>, distinctPaths: MutableSet<List<String>>) {
        if (el == target) {
            val copy = cPath.toMutableList()
            copy += target
            distinctPaths += copy
            return
        }

        if (el in visited && (el == "start" || visited[el] == 2 || cPath.any { x -> visited[x] == 2 })) {
            return
        }

        if (el[0].isLowerCase()) {
            visited[el] = (visited[el] ?: 0) + 1
        }

        cPath += el
        for (v in map[el]!!) {
            find2(map, v, target, visited, cPath, distinctPaths)
        }

        if (el[0].isLowerCase()) {
            if (visited[el] == 1) {
                visited -= el
            } else {
                visited[el] = visited[el]!! - 1
            }
        }
        cPath.removeAt(cPath.lastIndex)
    }

    fun part2(input: List<String>): Int {
        val paths = mutableSetOf<List<String>>()
        find2(toMap(input), "start", "end", mutableMapOf(), mutableListOf(), paths)
        return paths.size
    }


    val testData = readInput("day12/test")
    val inputData = readInput("day12/input")

    var res1 = part1(testData)
    check(res1 == 10) { "Expected 10 but got $res1" }

    var time = measureTimeMillis { res1 = part1(inputData) }
    println("Part1: $res1 in $time ms")

    time = measureTimeMillis { res1 = part2(inputData) }
    println("Part2: $res1 in $time ms")
}

