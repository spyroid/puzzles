package leetcode

fun main() {
    println(FindMinimumRotatedSortedArray().findMin(intArrayOf(3, 4, 5, 1, 2)))
    println(FindMinimumRotatedSortedArray().findMin(intArrayOf(3)))
}

class FindMinimumRotatedSortedArray {
    fun findMin(nums: IntArray) = nums.asSequence().zipWithNext().onEach { println(it) }.find { it.first > it.second }?.second ?: nums[0]
}
