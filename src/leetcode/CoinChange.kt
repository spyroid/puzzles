package leetcode

fun main() {
//    println(coinChange(intArrayOf(1, 2, 5), 11))
//    println(coinChange(intArrayOf(2), 3))
//    println(coinChange(intArrayOf(1), 0))
    println(coinChange(intArrayOf(186, 419, 83, 408), 6249))

}

fun coinChange(coins: IntArray, amount: Int): Int {
    var res = 0
    var rest = amount
    val koins = coins.sortedDescending()
    for (k in koins) {
        while (rest >= k) {
            res++
            rest -= k
        }
    }
    return if (rest == 0) res else -1
}
