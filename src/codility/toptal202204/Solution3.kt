package codility.toptal202204

import runWithTime

fun main() {
    runWithTime { (solution2(listOf(5, 3, 6, 2, 5, 5, 2).toIntArray(), 4)) }
    runWithTime { (solution2(listOf(2, 1, 3).toIntArray(), 2)) }
    runWithTime { (solution2(listOf(0, 4, 3, -1).toIntArray(), 2)) }
    runWithTime { (solution2(listOf(2, 1, 4).toIntArray(), 3)) }
}

// S = (A[i] + A[i+1] ... + A[j]) / (j - i + 1)
// S * (j - i + 1) = A[i] + A[i+1]...+ A[j]
// partial sums that is P[0] = 0 and P[i] = X[1] + ... + X[i]
// (P[j] - P[i - 1]) / (j - i + 1) = S -> P[j] - P[i - 1] = S * j - S * (i - 1) -> P[j] - S * j = P[i - 1] - S * (i - 1)
// Q[i] = P[i] - S * i
fun solution(A: IntArray, S: Int): Int {
    val prefs = mutableMapOf<Long, Int>().also { it[0] = 1 }
    val partialSums = LongArray(A.size + 1)
    val Q = LongArray(A.size + 1)

    for (i in 1 until A.size + 1) {
        partialSums[i] = partialSums[i - 1] + A[i - 1]
        Q[i] = partialSums[i] - S * i
        prefs.merge(Q[i], 1) { prev, one -> prev + one }
    }

    return prefs.values.sumOf { it * (it - 1) / 2 }
}

// simplified: generate cumulative sum, for each item use (mod S) to calc reminder and count number of occurrences
// of each reminder
// use double mod to avoid negative values
fun solution2(A: IntArray, S: Int): Int {
    return A.asSequence()
        .runningFold(0) { acc, i -> acc + i }
        .map { ((it % S) + S) % S }
        .groupingBy { it }
        .eachCount()
        .values
        .sumOf { it * (it - 1) / 2 }
}
