package leetcode

import runWithTime

fun main() {
    val a = intArrayOf(1, 2, 3)
    rotate(a, 2)
    println(a.toList())
}

fun rotate(nums: IntArray, k: Int): Unit {
    fun step() {
        val last = nums.last()
        for (i in nums.size - 1 downTo 1) nums[i] = nums[i - 1]
        nums[0] = last
    }
    repeat(k) { step() }
}
