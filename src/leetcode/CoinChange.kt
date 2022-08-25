package leetcode

import kotlin.streams.asSequence
import kotlin.streams.toList

fun main() {
//    println(coinChange(intArrayOf(1, 2, 5), 11))
//    println(coinChange(intArrayOf(2), 3))
//    println(coinChange(intArrayOf(1), 0))
//    println(coinChange(intArrayOf(186, 419, 83, 408), 6249))
    println(duplicateCount("aabbcde"))
}

fun duplicateCount(text: String): Int {
    return text.chars().asSequence().groupingBy { it }.eachCount().count { it.value > 1 }
}

fun spinWords(sentence: String): String {
    return sentence.split("\\s+".toRegex()).joinToString(" ") { if (it.length > 4) it.reversed() else it }
}
