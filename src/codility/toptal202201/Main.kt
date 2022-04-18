package codility.toptal202201

fun solve(input: List<String>): Int {
    val arr1 = intArrayOf()
    val arr2 = intArrayOf()
    return solution2(arr1, arr2)
}

fun main() {
    println(solution4(listOf(-1, -2, 1, 2, 3, 4, 5).toIntArray()))
    println(solution4(listOf(-1, -2).toIntArray()))
}

fun solution4(A: IntArray): Int {
    val set = A.filter { it > 0 }.toSet()
    for (i in 1..100_000) if (i !in set) return i
    return 1
}


fun solution2(P: IntArray, S: IntArray): Int {
    var freeSeats = 0
    for (i in 0..P.lastIndex) if (S[i] - P[i] > 0) freeSeats += S[i] - P[i]
    P.sort()
    for (i in 0..P.lastIndex) {
        if (P[i] <= freeSeats) {
            freeSeats -= P[i]
            P[i] = 0
        } else break
    }
    return P.count { it > 0 }
}

fun solution3(A: IntArray): Int {
    val initSum = A.sum()
    var filters = 0
    if (A.isEmpty()) return 0

    do {
        A.sortDescending()
        A[0] /= 2
        filters++
    } while (A.sum() > initSum / 2)

    return filters
}


fun solution1(message: String, K: Int): String {
    var ids = -1
    if (K >= message.length) return message
    for (i in 0 until message.length - 1) {
        if (!message[i].isWhitespace() && message[i + 1].isWhitespace()) ids = i + 1
        if (i >= K) break
    }
    return message.substring(0, ids)
}

//fun main() {
//    measureTimeMillis { print("⭐️ Result: ${solve(listOf())}") }
//        .also { time -> println(" in $time ms") }
//}

class Main {}
