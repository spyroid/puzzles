package leetcode

import gears.puzzle
import java.util.*

fun main() {
    puzzle {
        maxSlidingWindow(intArrayOf(1, 3, -1, -3, 5, 3, 6, 7), 3).toList()
    }

    puzzle {
        maxSlidingWindow(intArrayOf(1), 1).toList()
    }
}

fun maxSlidingWindow(nums: IntArray, k: Int): IntArray {
    val size = nums.size
    val ans = IntArray(size - k + 1)

    // sanity check
    if (nums.isEmpty()) return ans

    var idx = 0

    val deque = LinkedList<Int>()

    for (i in 0 until size) {
        // the window is too wide/large
        while (deque.isNotEmpty() && deque.peekFirst() < i - k + 1) {
            deque.removeFirst()
        }

        // if there is any larger value coming in from the last/tail
        while (deque.isNotEmpty() && nums[i] > nums[deque.peekLast()]) {
            deque.removeLast()
        }

        deque.add(i)

        if (i >= k - 1) {
            ans[idx++] = nums[deque.peekFirst()]
        }
    }

    return ans
}
