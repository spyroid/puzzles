package leetcode

import runWithTime

fun main() {
    runWithTime { maximumWealth(arrayOf(intArrayOf(1, 2, 3), intArrayOf(3, 2, 1))) }
    runWithTime { maximumWealth(arrayOf(intArrayOf(1, 5), intArrayOf(7, 3), intArrayOf(3, 5))) }
}

fun maximumWealth(accounts: Array<IntArray>) = accounts.maxOfOrNull { it.sum() }
