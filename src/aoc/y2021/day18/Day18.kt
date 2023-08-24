package aoc.y2021.day18

import gears.puzzle
import kotlin.math.ceil
import kotlin.math.floor

fun main() {
    puzzle { part1(linesFrom("input.txt")) }
    puzzle { part2(linesFrom("input.txt")) }
}

private fun part1(input: List<String>) = input.map { Node.of(it) }.reduce { a, b -> a + b }.magnitude()

private fun part2(input: List<String>): Long {
    val pairList = input.map { Node.of(it) }
    val allNodes = buildSet {
        pairList.forEach { node ->
            pairList.filterNot { otherNode -> otherNode == node }.forEach { otherNode ->
                add(node to otherNode)
                add(otherNode to node)
            }
        }
    }
    return allNodes.map { (it.first + it.second).magnitude() }.maxOf { it }
}

private val simplePairRegex = "\\[(-?\\d+),(-?\\d+)]".toRegex()

private class Node(var left: Node? = null, var right: Node? = null, var parent: Node? = null, var value: Int? = null) {
    init {
        left?.parent = this;
        right?.parent = this;
    }

    companion object {
        fun of(line: String): Node {
            var idx = 0
            fun parse(): Node {
                if (line[idx] == '[') {
                    idx++
                    val l = parse()
                    idx++ // ,
                    val r = parse()
                    idx++ // ]
                    return Node(value = null, left = l, right = r)
                }
                val start = idx
                while (line[idx] in '0'..'9') idx++
                return Node(value = line.substring(start, idx).toInt())
            }
            return parse()
        }
    }

    operator fun plus(other: Node): Node {
        val newParent = Node(this.deepCopy(), other.deepCopy());
        newParent.reduce()
        return newParent;
    }

    fun deepCopy() = of(this.toString())

    fun reduce() {
        while (true) {
            val seq = endValueNodes().asSequence()
            if (seq.any { it.tryExplode() })
                continue;
            else if (seq.any { it.trySplit() })
                continue
            else break;
        }
    }

    fun endValueNodes(): MutableList<Node> {
        if (this.value != null) return mutableListOf(this)
        val list = left!!.endValueNodes();
        list.addAll(right!!.endValueNodes());
        return list;
    }

    fun tryExplode(): Boolean {
        if (this.parent?.parent?.parent?.parent?.parent != null) {
            this.parent!!.explode();
            return true
        }
        return false
    }

    fun trySplit(): Boolean {
        if (this.value != null && this.value!! >= 10) {
            this.split();
            return true
        }
        return false
    }

    fun explode() {
        val left = nodeToTheLeft()?.rightMost();
        val right = nodeToTheRight()?.leftMost();
        left?.value = left!!.value!! + this.left!!.value!!
        right?.value = right!!.value!! + this.right!!.value!!

        this.left = null;
        this.right = null;
        this.value = 0;
    }

    fun split() {
        val leftVal = floor(value!! / 2.0).toInt()
        val rightVal = ceil(value!! / 2.0).toInt()
        left = Node(value = leftVal, parent = this)
        right = Node(value = rightVal, parent = this)
        this.value = null;
    }

    fun nodeToTheRight(): Node? {
        return if (parent == null || parent?.right == null) null
        else if (parent!!.right != this) parent!!.right!!
        else parent!!.nodeToTheRight()
    }

    fun nodeToTheLeft(): Node? {
        return if (parent == null || parent?.left == null) null
        else if (parent!!.left != this) parent!!.left!!
        else parent!!.nodeToTheLeft()
    }

    fun leftMost(): Node {
        return if (value != null) this
        else if (left!!.value != null) left!!
        else left!!.leftMost()
    }

    fun rightMost(): Node {
        return if (value != null) this
        else if (right!!.value != null) right!!
        else right!!.rightMost()
    }

    override fun toString() = if (this.value != null) this.value.toString() else "[$left,$right]"

    fun magnitude(): Long {
        if (value != null) return value!!.toLong()
        return (3 * left!!.magnitude()) + (2 * right!!.magnitude())
    }
}
