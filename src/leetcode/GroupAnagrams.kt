package leetcode

fun main() {
    GroupAnagrams().groupAnagrams(listOf("eat", "tea", "tan", "ate", "nat", "bat").toTypedArray())
    println(GroupAnagrams().groupAnagrams(listOf("ddddddddddg","dgggggggggg").toTypedArray()))
}

class GroupAnagrams {
    fun groupAnagrams(strs: Array<String>) = strs.groupBy { it.toCharArray().sorted() }.values.toList()
}
