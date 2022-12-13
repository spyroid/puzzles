package leetcode.easy

import gears.puzzle

fun main() {
    puzzle {
        topKFrequent(listOf("i","love","leetcode","i","love","coding").toTypedArray(), 2)
    }
    puzzle {
        topKFrequent(listOf("the","day","is","sunny","the","the","the","sunny","is","is").toTypedArray(), 4)
    }
}

fun topKFrequent(words: Array<String>, k: Int): List<String> {
    return words.asSequence().groupingBy { it }.eachCount().toList()
        .sortedWith(compareByDescending<Pair<String, Int>> { it.second }.thenBy { it.first })
        .take(k).map { it.first }
}
