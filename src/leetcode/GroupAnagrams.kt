package leetcode

import java.util.SortedSet

fun main() {
//    GroupAnagrams().groupAnagrams(listOf("eat", "tea", "tan", "ate", "nat", "bat").toTypedArray())
    println(GroupAnagrams().groupAnagrams(listOf("ddddddddddg","dgggggggggg").toTypedArray()))
}

class GroupAnagrams {

    fun groupAnagrams(strs: Array<String>) = strs
//        .map { Pair(it.toList().sorted(), it) }
        .groupBy { it.toCharArray().sorted() }
        .values.toList()


}
