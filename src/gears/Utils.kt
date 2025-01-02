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

fun <T> List<T>.safeGet(i: Int) = this[i % size]
fun <T> List<T>.safeGet(i: Long) = this[(i % size).toInt()]

fun <T> MutableList<T>.reverseSubList(start: Int, end: Int) {
    for (j in 0..((end - start) / 2)) {
        val x = safeGet(start + j)
        safeSet(start + j, safeGet(end - j))
        safeSet(end - j, x)
    }
}

private val re = "[-0-9]+".toRegex()
fun String.findInts() = re.findAll(this).map { it.value.toInt() }.toList()
fun String.findLongs() = re.findAll(this).map { it.value.toLong() }.toList()
fun String.findNumbers() = re.findAll(this).map { it.value }.toList()

fun <T> List<T>.splitBy(idx: Set<Int>) = foldIndexed(mutableListOf<MutableList<T>>()) { i, list, v ->
    if (idx.contains(i) || list.isEmpty()) list.add(mutableListOf(v)) else list.last().add(v)
    list
}

fun Iterable<Long>.lcm() = this.reduce { total, next -> lcm(total, next) }
fun lcm(a: Long, b: Long) = a / gcd(a, b) * b
tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

fun <K> MutableMap<K, Int>.inc(key: K, amount: Int = 1): Int? = merge(key, amount, Int::plus)
fun <K> MutableMap<K, Long>.inc(key: K, amount: Long = 1L): Long? = merge(key, amount, Long::plus)

fun range(a: Int, b: Int) = if (a <= b) IntRange(a, b) else IntRange(b, a)
fun range(a: Long, b: Long) = if (a <= b) LongRange(a, b) else LongRange(b, a)

fun Int.toDigits(base: Int = 10) = sequence {
    var n = this@toDigits
    require(n >= 0)
    while (n != 0) {
        yield(n % base)
        n /= base
    }
}

const val fullBlock = '█'
