package aoc.y2021.day23

import gears.puzzle
import java.util.PriorityQueue
import kotlin.math.abs

fun main() {
    puzzle { Amphipod(inputLines()) }
}

private fun Amphipod(input: List<String>): Any {

    val initialState = State.from(input)
    val initialStateExtended = State.from(input.take(3) + "  #D#C#B#A#  " + "  #D#B#A#C#  " + input.takeLast(2))

    fun organizeAmphipods(initialState: State): Int {
        val toVisit = PriorityQueue<StateWithCost>().apply { add(StateWithCost(initialState, 0)) }
        val visited = mutableSetOf<StateWithCost>()
        val currentCosts = mutableMapOf<State, Int>().withDefault { Int.MAX_VALUE }

        while (toVisit.isNotEmpty()) {
            val current = toVisit.poll().also { visited.add(it) }
            current.state.nextPossibleStates().forEach { next ->
                if (!visited.contains(next)) {
                    val newCost = current.cost + next.cost
                    if (newCost < currentCosts.getValue(next.state)) {
                        currentCosts[next.state] = newCost
                        toVisit.add(StateWithCost(next.state, newCost))
                    }
                }
            }
        }

        return currentCosts.keys.first { it.isFinished() }.let { currentCosts.getValue(it) }
    }

    return organizeAmphipods(initialState) to organizeAmphipods(initialStateExtended)
}

private data class State(private val config: List<List<Char>>) {
    private val hallway = config[0]
    private val rooms = config.drop(1)
    private val destinationRooms = mapOf(
        'A' to Room('A', 2, rooms.map { row -> row[2] }),
        'B' to Room('B', 4, rooms.map { row -> row[4] }),
        'C' to Room('C', 6, rooms.map { row -> row[6] }),
        'D' to Room('D', 8, rooms.map { row -> row[8] })
    )
    private val multipliers = mapOf('A' to 1, 'B' to 10, 'C' to 100, 'D' to 1000)
    private val legalHallwayIndexes
        get() = listOf(0, 1, 3, 5, 7, 9, 10).filter { hallway[it] == '.' }

    fun isFinished() = destinationRooms.values.all { it.hasOnlyValidAmphipods() }

    fun nextPossibleStates(): List<StateWithCost> {
        return buildList {
            amphipodsInHallwayThatCanMove().forEach {
                val room = destinationRooms.getValue(it.value)
                if (hallwayPathIsClear(it.index, room.index)) {
                    val y = room.content.lastIndexOf('.') + 1
                    val cost = (abs(it.index - room.index) + y) * multipliers.getValue(it.value)
                    add(
                        StateWithCost(
                            State(
                                config.map { row -> row.toMutableList() }.apply {
                                    get(0)[it.index] = '.'
                                    get(y)[room.index] = it.value
                                }
                            ), cost))
                }
            }
            roomsWithWrongAmphipods().forEach { room ->
                val toMove = room.content.withIndex().first { it.value != '.' }
                legalHallwayIndexes.forEach { index ->
                    if (hallwayPathIsClear(index, room.index)) {
                        val y = toMove.index + 1
                        val cost = (abs(room.index - index) + y) * multipliers.getValue(toMove.value)
                        add(
                            StateWithCost(
                                State(
                                    config.map { row -> row.toMutableList() }.apply {
                                        get(y)[room.index] = '.'
                                        get(0)[index] = toMove.value
                                    }
                                ), cost))
                    }
                }
            }
        }
    }

    private fun amphipodsInHallwayThatCanMove(): List<IndexedValue<Char>> {
        return hallway.withIndex().filter {
            it.value.isLetter() && destinationRooms.getValue(it.value).isEmptyOrHasAllValidAmphipods()
        }
    }

    private fun roomsWithWrongAmphipods() = destinationRooms.values.filter { it.hasAmphipodsWithWrongType() }

    private fun hallwayPathIsClear(start: Int, end: Int): Boolean {
        return hallway.slice(
            when (start > end) {
                true -> (start - 1) downTo end
                false -> (start + 1)..end
            }
        ).all { it == '.' }
    }

    companion object {
        fun from(input: List<String>) = State(input.drop(1).dropLast(1).map { it.drop(1).dropLast(1).toList() })
    }
}

private class StateWithCost(val state: State, val cost: Int) : Comparable<StateWithCost> {
    override fun compareTo(other: StateWithCost) = cost.compareTo(other.cost)
}

private class Room(val char: Char, val index: Int, val content: List<Char>) {
    fun hasOnlyValidAmphipods() = content.all { it == char }

    fun isEmptyOrHasAllValidAmphipods() = content.all { it == '.' || it == char }

    fun hasAmphipodsWithWrongType() = !isEmptyOrHasAllValidAmphipods()
}
