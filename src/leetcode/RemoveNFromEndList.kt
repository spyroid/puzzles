package leetcode

fun main() {
    var r = removeNthFromEnd(ListNode(1).also { it.next = ListNode(2) }, 1)
    while (r != null) print("${r.`val`} ").also { r = r?.next }
}

fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
    var a = head
    var b = head
    var i = 0
    while (a?.next != null) {
        a = a.next
        if (i++ >= n) {
            b = b?.next
        }
    }
    if (a == b) return null
    b?.next = if (n == 1) null else a
    println(a?.`val`)
    println(b?.`val`)
    return head
}
