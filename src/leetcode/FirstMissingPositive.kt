package leetcode

import runWithTime

fun main() {
    runWithTime { firstMissingPositive(intArrayOf(7, 8, 9, 11, 12)) }
}

fun firstMissingPositive(nums: IntArray): Int {
    val exist = BooleanArray(nums.size + 1)
    for (v in nums) if (v > 0 && v <= nums.size) exist[v - 1] = true
    for ((i, v) in exist.withIndex()) if (!v) return i + 1
    return 1
}

//    fun firstMissingPositive(nums: IntArray): Int {
//        val n = nums.size
//        val exist = BooleanArray(n + 2)
//        // for each number presented in range, mark its existence
//        nums
//            .filter {
//                it in 1..n
//            }
//            .forEach {
//                exist[it] = true
//            }
//        // we want to find the first number in 1..n+1 that is not marked
//        return (1..n + 1).first { !exist[it] }
//    }

//fun firstMissingPositive(nums: IntArray): Int {
//    nums.asSequence().filter { it > 0 }.toSet()
//        .let {
//            for (i in 1..Int.MAX_VALUE) if (i !in it) return i
//            return 0
//        }
//}
//
