package aoc.y2022.day18

import gears.puzzle

fun main() {
    puzzle("t1") { part1(inputLines("test.txt")) }
    puzzle("1") { part1(inputLines("input.txt")) }
    puzzle("t2") { part2(inputLines("test.txt")) }
    puzzle("2") { part2(inputLines("input.txt")) }
}

private fun part1(input: List<String>): Int {
    return cubes(input, false)
}

private fun part2(input: List<String>): Int {
    return cubes(input, true)
}

private fun cubes(lines: List<String>, all: Boolean): Int {
    val cubes = lines.map { it.split(",").map { it.toInt() }.let { (x, y, z) -> Point3D(x, y, z) } }.toSet()

    val xRange = cubes.minOf { it.x } until cubes.maxOf { it.x }
    val yRange = cubes.minOf { it.y } until cubes.maxOf { it.y }
    val zRange = cubes.minOf { it.z } until cubes.maxOf { it.y }
    fun Point3D.outOfRange() = x !in xRange || y !in yRange || z !in zRange

    val allPoints = xRange.flatMap { x -> yRange.flatMap { y -> zRange.map { z -> Point3D(x, y, z) } } }
    val trapped = allPoints.toMutableSet()

    tailrec fun Point3D.removeFromTrapped(start: Point3D = this, seen: Set<Point3D> = setOf(start)) {
        if (outOfRange()) trapped.removeAll(seen + this)
        else adjacents().filter { it !in cubes && it !in seen }.forEach {
            return it.removeFromTrapped(start, seen + this)
        }
    }

    allPoints.forEach { it.removeFromTrapped() }
    return if (all) {
        cubes.sumOf { it.adjacents().count { it !in cubes && it !in trapped } }
    } else {
        cubes.sumOf { it.adjacents().count { it !in cubes } }
    }
}

data class Point3D(val x: Int, val y: Int, val z: Int) {
    fun adjacents() = listOf(Point3D(x + 1, y, z), Point3D(x - 1, y, z), Point3D(x, y + 1, z), Point3D(x, y - 1, z), Point3D(x, y, z + 1), Point3D(x, y, z - 1))
}
