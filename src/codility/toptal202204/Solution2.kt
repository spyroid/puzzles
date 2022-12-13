package codility.toptal202204

import gears.puzzle
import kotlin.random.Random

fun main() {
    puzzle { (solution(listOf(7, 3, 7, 3, 1, 3, 4, 1).toIntArray())) }
    puzzle { solution(listOf(2, 1, 1, 3, 2, 1, 1, 3).toIntArray()) }
    puzzle { solution(listOf(7, 5, 2, 7, 2, 7, 4, 7).toIntArray()) }
    val huge = IntArray(100_000) { _ -> Random.nextInt(0, 100 - 1) }
    puzzle { solution(huge) }
}

// Use 2 pointers.
// For each iteration of p1, check sub-interval with initial days as a set
fun solution(A: IntArray): Int {
    var minDaysCount = A.size
    val all = A.toSet()
    for (i in A.indices) {
        var count = 0
        val sub = all.toMutableSet()
        for (j in i until A.size) {
            sub.remove(A[j]).also { count += 1 }
            if (sub.isEmpty()) {
                minDaysCount = minOf(count, minDaysCount)
                break
            }
        }
    }
    return minDaysCount
}
