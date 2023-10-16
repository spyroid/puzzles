package aoc.y2018.day3

import gears.Point
import gears.puzzle

private fun main() {
    puzzle { slice(inputLines()) }
    puzzle { slice2(inputLines()) }
}

private val re = "\\d+".toRegex()

private fun slice(input: List<String>): Any {
    val grid = mutableMapOf<Point, Int>()
    for (s in input) {
        val (_, x, y, w, h) = re.findAll(s).map { it.value }.map { it.toInt() }.toList()
        for (i in 0..<h) for (j in 0..<w) grid.compute(Point(x + j, y + i)) { _, v -> v?.plus(1) ?: 1 }
    }
    return grid.filterValues { it > 1 }.count()
}

private fun slice2(input: List<String>): Any {
    val all = input.map { s ->
        re.findAll(s).map { it.value }.map { it.toInt() }.toList().let { (id, x, y, w, h) -> Claim(id, x, y, w, h) }
    }
    val cloth = mutableMapOf<Pair<Int, Int>, Int>()
    val uncovered = all.map { it.id }.toMutableSet()
    all.forEach { claim ->
        claim.area().forEach { spot ->
            val found = cloth.getOrPut(spot) { claim.id }
            if (found != claim.id) {
                uncovered.remove(found)
                uncovered.remove(claim.id)
            }
        }
    }
    return uncovered.first()
}

private data class Claim(val id: Int, val x: Int, val y: Int, val w: Int, val h: Int) {
    fun area(): List<Pair<Int, Int>> = (x until w + x).flatMap { w -> (y until h + y).map { h -> Pair(w, h) } }
}
