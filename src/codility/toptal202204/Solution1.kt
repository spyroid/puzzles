package codility.toptal202204

import puzzle

fun main() {
    puzzle {
        solution(
            listOf("test1a", "test2", "test1b", "test1c", "test3").toTypedArray(),
            listOf("Wrong answer", "OK", "Runtime error", "OK", "Time limit exceeded").toTypedArray()
        )
    }

    puzzle {
        solution(
            listOf("codility1", "codility3", "codility2", "codility4b", "codility4a").toTypedArray(),
            listOf("W", "OK", "OK", "Runtime Err", "OK").toTypedArray()
        )
    }
}

// only id is important. count groups of ids
fun solution(T: Array<String>, R: Array<String>): Int {
    return T.asSequence()
        .map { Regex("\\w+(\\d+)").find(it) }
        .filterNotNull()
        .map { it.destructured.component1().toInt() }
        .zip(R.asSequence().map { it == "OK" })
        .groupingBy { it.first }
        .fold(true) { acc, el -> acc && el.second }
        .let { map -> map.values.count { it } * 100 / map.size }
}
