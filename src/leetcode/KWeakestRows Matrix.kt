package leetcode

fun main() {
//    runWithTime {
//        ladderLength("hit", "cog", listOf("hot", "dot", "dog", "lot", "log"))
//    }
}


private fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
    val list = mutableListOf<Int>()
    var j = 0
    for (v in nums1) {
        while (j < nums2.size && nums2[j] <= v) {
            list.add(nums2[j++])
        }
        list.add(v)
    }
    while (j < nums2.size) {
        list.add(nums2[j++])
    }
    val idx = list.size / 2
    return if (list.size % 2 == 0) (list[idx] + list[idx - 1]) / 2.0 else list[idx].toDouble()
}

private fun isPalindrome(head: ListNode?): Boolean {
    val list = mutableListOf<Int>()
    var a = head
    while (a != null) {
        list.add(a.`val`)
        a = a.next
    }
    return list == list.reversed()
}

private fun canConstruct(ransomNote: String, magazine: String): Boolean {
    val map = magazine.asSequence().groupingBy { it }.eachCount().toMutableMap()
    for (c: Char in ransomNote) {
        val v = map[c] ?: 0
        if (v == 0) return false
        map[c] = v - 1
    }
    return true
}

private fun kWeakestRows(mat: Array<IntArray>, k: Int): IntArray {
    return mat
        .mapIndexed { i, row -> Pair(i, row.sum()) }
        .sortedWith(compareBy({ it.second }, { it.first }))
        .take(k)
        .map { it.first }
        .toIntArray()
}

 class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

private fun middleNode(head: ListNode?): ListNode? {
    var a = head
    var b = head
    while (b?.next != null) {
        a = a?.next
        b = b.next?.next
    }
    return a
}
