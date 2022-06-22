package leetcode

import runWithTime

fun main() {
//    var res = sequenceOf(sequenceOf(1,1), sequenceOf(2,2), sequenceOf(3,3))
//        .fold(sequenceOf<Sequence<Int>>()) { acc, seq ->
//            acc.flatMap { seq2 -> seq.map { seq2.plus(it) } }
//        }
////        .flatMap { it }
//
//
//    res.forEach { print("${it.toList()} ") }

//    var cp = cartesianProduct(listOf(listOf(1, 1), listOf(listOf(2, 2))))
//    var cp = cartesianProduct(listOf(listOf('a', 'b'), listOf('c', 'd'), listOf('0', '1')))
//    println()
//    println(cp)

//    runWithTime { letterCombinations("23") }
}

fun <T> cartesianProduct(arr: List<List<T>>): List<List<T>> {
    if (arr.isEmpty()) return emptyList()
    return arr.drop(1)
        .fold(arr.first().map(::listOf)) { acc, iterable ->
            println(acc)
            println(iterable)
            acc.flatMap { list -> iterable.map(list::plus) }
        }
}


//fun <T> List<List<T>>.getCartesianProduct(): List<List<T>> =
//    if (isEmpty()) emptyList()
//    else drop(1)
//        .fold(first().map(::listOf)) { acc, iterable ->
//            acc.flatMap { list -> iterable.map(list::plus) }
//        }
//
//val arr = arrayOf("abc".toList(), "def".toList(), "ghi".toList(), "jkl".toList(),
//    "mno".toList(), "pqrs".toList(), "tuv".toList(), "wxyz".toList())
//
//fun letterCombinations(digits: String): List<String> {
//    return digits.mapNotNull { arr[it.code - 50] }.getCartesianProduct().map { String(it.toCharArray()) }
//}

