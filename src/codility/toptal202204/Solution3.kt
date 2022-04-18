package codility.toptal202204

import runWithTime

fun main() {
    runWithTime { (solution(listOf(2, 1, 3).toIntArray(), 2)) }
//    runWithTime { (solution(listOf(0, 4, 3, -1).toIntArray(), 2)) }
//    runWithTime { (solution(listOf(2, 1, 4).toIntArray(), 3)) }
}

fun solution(A: IntArray, S: Int): Int {
    val prefixes = mutableMapOf<Int, Int>()
    prefixes[0] = 1
    var result = 0
    val P = IntArray(A.size + 1)
    val Q = IntArray(A.size + 1)

    for (i in 1 until A.size + 1) {
        P[i] = P[i - 1] + A[i - 1]
        Q[i] = P[i] - S * i
        if (!prefixes.containsKey(Q[i])) {
            prefixes[Q[i]] = 1
        } else {
            var temp = prefixes[Q[i]]!!
            temp += 1
            prefixes[Q[i]] = temp
        }
    }
    println(prefixes)

    for (v in prefixes.values) result += v * (v - 1) / 2

    return result
}
