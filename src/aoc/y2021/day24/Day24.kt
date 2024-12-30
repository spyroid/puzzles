package aoc.y2021.day24

import gears.puzzle
import gears.toDigits
import kotlin.math.floor

fun main() {
    puzzle { `Arithmetic Logic Unit`(inputLines()) }
}

private fun `Arithmetic Logic Unit`(input: List<String>): Any {

    val instructions = input.chunked(18).map { list -> list[5].substringAfterLast(" ").toInt() to list[15].substringAfterLast(" ").toInt() }

    fun validate(model: List<Int>): List<Int>? {
        val result = mutableListOf<Int>()
        var (counter, z) = 0 to 0
        instructions.forEach {
            if (it.first < 0) {
                val num = (z % 26) + it.first
                if (num !in 1..9) return null
                result.add(num)
                z = floor(z / 26.0).toInt()
            } else {
                result.add(model[counter])
                z = 26 * z + model[counter] + it.second
                counter++
            }
        }
        return if (z == 0) result else null
    }

    fun validateRange(range: IntProgression) = range.map { it.toDigits() }.filter { 0 !in it }.firstNotNullOf { validate(it.toList()) }.joinToString("")

    return validateRange(9999999 downTo 1111111) to validateRange(1111111..9999999)
}