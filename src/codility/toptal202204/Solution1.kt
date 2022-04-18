package codility.toptal202204

import runWithTime

fun main() {
    runWithTime {
        solution(
            listOf("test1a", "test1b", "test1c", "test2", "test3").toTypedArray(),
            listOf("W", "RE", "OK", "OK", "Time").toTypedArray()
        )
    }

    runWithTime {
        solution(
            listOf("codility1", "codility3", "codility2", "codility4b", "codility4a").toTypedArray(),
            listOf("W", "OK", "OK", "Runtime Err", "OK").toTypedArray()
        )
    }
}

// only id is important. count ids groups
fun solution(T: Array<String>, R: Array<String>): Int {
    val total = mutableMapOf<Int, Int>()
    val passed = mutableMapOf<Int, Int>()

    for (i in T.indices) {
        val match = Regex("\\w+(\\d+)").find(T[i]) ?: continue
        val id = match.destructured.component1().toInt()
        total.merge(id, 1) { o, _ -> o + 1 }
        passed.computeIfAbsent(id) { 0 }
        passed.merge(id, 0) { o, _ -> if (R[i] == "OK") o + 1 else o }
    }
//    println(total)
//    println(passed)
    return (total.keys).count { (total[it] == passed[it]) } * 100 / total.size
}
