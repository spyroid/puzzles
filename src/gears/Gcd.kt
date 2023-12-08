package gears

import kotlin.math.abs

fun gcd(unsortedInts: List<Int>): Int {
    require(unsortedInts.size >= 2) { "There must be at least two numbers" }
    return gcd(unsortedInts[0], unsortedInts[1], *unsortedInts.drop(2).toIntArray())
}

fun gcd(unsortedLongs: List<Long>): Long {
    require(unsortedLongs.size >= 2) { "There must be at least two numbers" }
    return gcd(unsortedLongs[0], unsortedLongs[1], *unsortedLongs.drop(2).toLongArray())
}

fun gcd(a: Int, b: Int, vararg n: Int): Int {
    val numbers: List<Int> = ArrayList<Int>(n.size + 2).apply {
        add(abs(a))
        add(abs(b))
        addAll(n.map { abs(it) })
    }
    require(numbers.any { it != 0 }) { "At least one number must not be zero" }

    tailrec fun gcd(a: Int, b: Int): Int = if (a == 0) b else gcd((b % a), a)

    return numbers.reduce { acc, i ->
        val (smaller, larger) = minMax(acc, i)
        val nextAcc = gcd(smaller, larger)
        if (nextAcc == 1) return nextAcc
        nextAcc
    }
}

fun gcd(a: Long, b: Long, vararg n: Long): Long {
    val numbers: List<Long> = ArrayList<Long>(n.size + 2).apply {
        add(abs(a))
        add(abs(b))
        addAll(n.map { abs(it) })
    }
    require(numbers.any { it != 0L }) { "At least one number must not be zero" }

    tailrec fun gcd(a: Long, b: Long): Long = if (a == 0L) b else gcd((b % a), a)

    return numbers.reduce { acc, i ->
        val (smaller, larger) = minMax(acc, i)
        val nextAcc = gcd(smaller, larger)
        if (nextAcc == 1L) return nextAcc
        nextAcc
    }
}

fun lcm(a: Long, b: Long, vararg n: Long): Long {
    val numbers: List<Long> = ArrayList<Long>(n.size + 2).apply {
        add(abs(a))
        add(abs(b))
        addAll(n.map { abs(it) })
    }
    require(numbers.any { it != 0L }) { "At least one number must not be zero" }

    fun lcm(a: Long, b: Long): Long = abs(a * b) / gcd(a, b)

    return numbers.reduce { acc, i ->
        lcm(acc, i)
    }
}

fun lcm(longs: List<Long>): Long {
    require(longs.size >= 2) { "There must be at least two numbers" }
    return lcm(longs[0], longs[1], *longs.drop(2).toLongArray())
}

fun Iterable<Long>.lcm(): Long {
    val longList = this.toList()
    return lcm(longList)
}

fun <E : Comparable<E>> minMax(vararg items: E): Pair<E, E> {
    var min = items.first()
    var max = items.first()
    items.forEach { if (it < min) min = it else if (it > max) max = it }
    return min to max
}
