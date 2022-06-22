package leetcode

fun main() {
    var n: ListNode? = ListNode(2)
    val r1 = n
    n?.next = ListNode(4).also { n = it }
    n?.next = ListNode(9).also { n = it }

    n = ListNode(5)
    val r2 = n
    n?.next = ListNode(6).also { n = it }
    n?.next = ListNode(4).also { n = it }
    n?.next = ListNode(9).also { n = it }

    var res = addTwoNumbers(r1, r2)
    while (res != null) {
        println(res.`val`)
        res = res.next
    }
}

// [2,4,9]
// [5,6,4,9]
// [7,0,4,0,1]

fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
    var aa = l1
    var bb = l2
    var extra = 0
    var root: ListNode? = null
    var n: ListNode? = null
    while (aa != null || bb != null || extra > 0) {
        val a = aa?.`val`?.also { aa = aa?.next } ?: 0
        val b = bb?.`val`?.also { bb = bb?.next } ?: 0
        var s = a + b + extra
        extra = s / 10
        s %= 10

        if (n == null) {
            n = ListNode(s)
            root = n
        } else {
            n.next = ListNode(s)
            n = n.next
        }
    }
    return root
}
