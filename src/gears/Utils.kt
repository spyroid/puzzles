package gears

import java.security.MessageDigest

fun List<String>.toInts() = this.map { it.toInt() }

private val dig = MessageDigest.getInstance("MD5")

fun md5(str: String): ByteArray = dig.digest(str.toByteArray(Charsets.UTF_8))

fun ByteArray.hasLeadingZeros(n: Int): Boolean {
    repeat(n / 2) { if (this[it] != 0.toByte()) return false }
    return !(n % 2 != 0 && this[n / 2].toUByte() > 15.toUByte())
}

fun ByteArray.toHex(): String = java.util.HexFormat.of().formatHex(this)

infix fun IntRange.isFullyOverlaps(other: IntRange) = first <= other.first && last >= other.last
infix fun IntRange.isOverlaps(other: IntRange) = first <= other.last && other.first <= last
infix fun LongRange.isOverlaps(other: LongRange) = first <= other.last && other.first <= last

fun <E, F> cartesian(list1: List<E>, list2: List<F>): Sequence<Pair<E, F>> =
    cartesian(listOf(list1, list2)).map { it[0] as E to it[1] as F }

fun <E, F, G> cartesian(list1: List<E>, list2: List<F>, list3: List<G>): Sequence<Triple<E, F, G>> =
    cartesian(listOf(list1, list2, list3)).map { Triple(it[0] as E, it[1] as F, it[2] as G) }

fun <E> cartesian(lists: List<List<E>>): Sequence<List<E>> {
    return sequence {
        val counters = Array(lists.size) { 0 }
        val length = lists.fold(1) { acc, list -> acc * list.size }

        for (i in 0 until length) {
            val result = lists.mapIndexed { index, list ->
                list[counters[index]]
            }
            yield(result)
            for (pointer in lists.size - 1 downTo 0) {
                counters[pointer]++
                if (counters[pointer] == lists[pointer].size) {
                    counters[pointer] = 0
                } else {
                    break
                }
            }
        }
    }
}

fun <T> permutations(list: List<T>): List<List<T>> = when {
    list.size > 10 -> throw Exception("You probably dont have enough memory to keep all those permutations")
    list.size <= 1 -> listOf(list)
    else ->
        permutations(list.drop(1)).map { perm ->
            (list.indices).map { i ->
                perm.subList(0, i) + list.first() + perm.drop(i)
            }
        }.flatten()
}

fun <T> MutableList<T>.safeSet(i: Int, v: T) {
    this[i % size] = v
}

fun <T> MutableList<T>.safeGet(i: Int) = this[i % size]

fun <T> MutableList<T>.reverseSubList(start: Int, end: Int) {
    for (j in 0..((end - start) / 2)) {
        val x = safeGet(start + j)
        safeSet(start + j, safeGet(end - j))
        safeSet(end - j, x)
    }
}

fun String.toIntVec(delim: String = ",") = Point.fromStr(this, delim)

private val re = "[-0-9]+".toRegex()
fun String.findIntNumbers() = re.findAll(this).map { it.value.toInt() }.toList()
fun String.findLongNumbers() = re.findAll(this).map { it.value.toLong() }.toList()
fun String.findNumbers() = re.findAll(this).map { it.value }.toList()

fun <T> List<T>.splitBy(idx: Set<Int>) = foldIndexed(mutableListOf<MutableList<T>>()) { i, list, v ->
    if (idx.contains(i) || list.isEmpty()) list.add(mutableListOf(v)) else list.last().add(v)
    list
}
