package leetcode

import gears.puzzle

fun main() {
    puzzle { maximumWealth(arrayOf(intArrayOf(1, 2, 3), intArrayOf(3, 2, 1))) }
    puzzle { maximumWealth(arrayOf(intArrayOf(1, 5), intArrayOf(7, 3), intArrayOf(3, 5))) }
}

fun maximumWealth(accounts: Array<IntArray>) = accounts.maxOfOrNull { it.sum() }
