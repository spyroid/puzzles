package aoc.y2019.day5

import aoc.y2019.day5.IntComputer.State.RUNNING
import gears.toDigits

// state: 0 - running, 1 - suspended, 2 - terminated
data class IntComputer(var input: ArrayDeque<Long>, var output: Long = 0, var ip: Long = 0, var state: State = RUNNING) {
    val memory = mutableMapOf<Long, Long>()
    var base = 0L

    enum class State { RUNNING, SUSPENDED, TERMINATED }

    companion object {
        fun of(p: List<Long>) = IntComputer(ArrayDeque()).apply {
            p.forEachIndexed { i, v -> memory[i.toLong()] = v }
        }
    }

    data class Instruction(val op: Int, val flags: Int = 0) {
        companion object {
            fun of(s: Long) = Instruction((s % 100).toInt(), (s / 100).toInt())
        }
    }

    fun op1(mode: Int) = memory[addr(1, mode)] ?: 0
    fun op2(mode: Int) = memory[addr(2, mode)] ?: 0
    fun addr(offset: Int, mode: Int) = when (mode) {
        0 -> memory[ip + offset] ?: 0
        1 -> ip + offset
        else -> (memory[ip + offset] ?: 0) + base
    }

    fun writeAt(addr: Long, value: Long) = memory.set(addr, value)

    fun run(vararg inParams: Long): Long {
        if (state == State.TERMINATED) return output
        state = RUNNING
        this.input.addAll(inParams.asList())
        return generateSequence(RUNNING) { execute() }.first { it != RUNNING }.let { output }
    }

    fun execute(): State {
        if (state == State.TERMINATED) return state
        val inst = Instruction.of(memory.getValue(ip))
        val (m1, m2, m3) = inst.flags.toDigits().toList().plus(listOf(0, 0, 0))
        when (inst.op) {
            1 -> writeAt(addr(3, m3), op1(m1) + op2(m2)).also { ip += 4 }
            2 -> writeAt(addr(3, m3), op1(m1) * op2(m2)).also { ip += 4 }
            3 -> if (input.isNotEmpty()) writeAt(addr(1, m1), input.removeFirst()).also { ip += 2 } else state = State.SUSPENDED
            4 -> output = op1(m1).also { ip += 2 }
            5 -> if (op1(m1) != 0L) ip = op2(m2) else ip += 3
            6 -> if (op1(m1) == 0L) ip = op2(m2) else ip += 3
            7 -> writeAt(addr(3, m3), (op1(m1) < op2(m2)).compareTo(false).toLong()).also { ip += 4 }
            8 -> writeAt(addr(3, m3), (op1(m1) == op2(m2)).compareTo(false).toLong()).also { ip += 4 }
            9 -> base += op1(m1).also { ip += 2 }
            else -> state = State.TERMINATED
        }
        return state
    }
}