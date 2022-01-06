package aoc.y2021.day20

import readInput
import kotlin.system.measureTimeMillis

data class Pixel(val x: Int, val y: Int, var color: Int)

data class Image(val width: Int, val height: Int, val bgColor: Int, val algo: List<Int>, val pixels: List<Pixel>) {

    companion object {
        private fun mapPixel(ch: Char) = if (ch == '#') 1 else 0

        fun of(input: List<String>): Image {
            val algo = input.first().map { mapPixel(it) }.toList()
            val pixels = mutableListOf<Pixel>()
                .apply {
                    input.drop(2).forEachIndexed { y, line -> line.forEachIndexed { x, v -> add(Pixel(x, y, mapPixel(v))) } }
                }
            return Image(input.drop(2).first().length, input.size - 2, 0, algo, pixels)
        }
    }

    private fun at(x: Int, y: Int): Pixel? {
        if (x < 0 || x >= width || y < 0) return null
        return pixels.getOrNull(x + width * y)
    }

    private fun matrix(x: Int, y: Int) = listOf(
        at(x - 1, y - 1), at(x, y - 1), at(x + 1, y - 1),
        at(x - 1, y), at(x, y), at(x + 1, y),
        at(x - 1, y + 1), at(x, y + 1), at(x + 1, y + 1),
    )

    private fun clone(): Image {
        val bgColor = if (bgColor == 0) algo.first() else algo.last()
        val pixels = mutableListOf<Pixel>()
            .apply {
                (0 until height + 2).forEach { y -> (0 until width + 2).forEach { x -> add(Pixel(x, y, 0)) } }
            }
        return Image(width + 2, height + 2, bgColor, algo, pixels)
    }

    private fun colorAt(x: Int, y: Int) = matrix(x, y)
        .map { it?.color ?: bgColor }
        .joinToString("")
        .toInt(2)
        .let { algo[it] }

    fun enhance() = clone()
        .also { it.pixels.onEach { p -> p.color = colorAt(p.x - 1, p.y - 1) } }

    fun litPixels() = pixels.filter { it.color == 1 }
}

fun solve(input: List<String>, iterations: Int) = generateSequence(Image.of(input)) { it.enhance() }
    .drop(iterations)
    .first()
    .litPixels()
    .count()

fun main() {

    val testData = readInput("day20/test")
    val inputData = readInput("day20/input")

    var res1 = solve(testData, 2)
    check(res1 == 35) { "Expected 35 but got $res1" }

    measureTimeMillis { res1 = solve(inputData, 2) }
        .also { time ->
            println("⭐️ Part1: $res1 in $time ms")
        }
    measureTimeMillis { res1 = solve(inputData, 50) }
        .also { time ->
            println("⭐️ Part2: $res1 in $time ms")
        }
}

