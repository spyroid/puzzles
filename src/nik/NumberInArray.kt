package nik

import gears.puzzle

fun main() {
    puzzle { solution(listOf(1, 2), 1) }
}

private fun solution(nums: List<Int>, k : Int): Boolean {
    if (nums.size == 1) return nums.first() == k
    nums.zipWithNext().forEach {
        if (it.second - it.first > 1) return false
        if (it.first == k) return true
    }
    return false
}
