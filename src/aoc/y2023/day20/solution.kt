package aoc.y2023.day20

import gears.lcm
import gears.puzzle

private fun main() {
    puzzle("1") { aplenty(inputLines()) }
    puzzle("2") { aplenty2(inputLines()) }
}

private fun aplenty(input: List<String>): Any {
    val components = readData(input)
    val counts = mutableMapOf(State.High to 0L, State.Low to 0L)
    repeat(1000) { pushButton(components, counts) }
    return counts.values.fold(1L) { acc, l -> acc * l }
}

private fun aplenty2(input: List<String>): Any {
    val components = readData(input)
    val counts = mutableMapOf(State.High to 0L, State.Low to 0L)
    val feeder = components["rx"]!!.inputs.first() as Conjunction
    feeder.feeder = true

    while (true) {
        buttonPresses += 1
        pushButton(components, counts)
        if (feeder.allFlipped()) {
            return feeder.inputFlipped.values.lcm()
        }
    }
}

private enum class State {
    High, Low
}

private typealias Pulses = ArrayDeque<Triple<Broadcast, Broadcast, State>>

private val pulses = Pulses()

private open class Broadcast(open val name: String, var feeder: Boolean = false) {
    val outputs = mutableListOf<Broadcast>()
    val inputs = mutableListOf<Broadcast>()

    open fun pulseIn(source: Broadcast, state: State, list: Pulses) {
        pulseOut(state, list)
    }

    fun pulseOut(state: State, list: Pulses) {
        for (o in outputs) list.add(Triple(o, this, state))
    }

    fun addOutput(output: Broadcast) {
        outputs.add(output)
    }

    open fun addInput(input: Broadcast) {
        inputs.add(input)
    }
}

private var buttonPresses = 0L

private class Conjunction(override val name: String) : Broadcast(name) {
    val inputStates = mutableMapOf<Broadcast, State>()
    val inputFlipped = mutableMapOf<Broadcast, Long>()

    override fun addInput(input: Broadcast) {
        super.addInput(input)
        inputStates[input] = State.Low
        inputFlipped[input] = 0
    }

    override fun pulseIn(source: Broadcast, state: State, list: Pulses) {
        inputStates[source] = state
        if (state == State.High && inputFlipped[source] == 0L) {
            inputFlipped[source] = buttonPresses
        }
        val sendState = if (inputStates.values.all { it == State.High }) State.Low else State.High
        pulseOut(sendState, list)
    }

    fun allFlipped() = inputFlipped.values.all { it > 0 }
}

private class FlipFlop(override val name: String) : Broadcast(name) {
    var myState = State.Low

    override fun pulseIn(source: Broadcast, state: State, list: Pulses) {
        if (state == State.Low) {
            myState = if (myState == State.Low) State.High else State.Low
            pulseOut(myState, list)
        }
    }
}

private class Button(override val name: String = "Button") : Broadcast(name) {
    override fun pulseIn(source: Broadcast, state: State, list: Pulses) {
        pulseOut(State.Low, list)
    }
}

private class Output(override val name: String = "output") : Broadcast(name) {
    var flipped = false
    override fun pulseIn(source: Broadcast, state: State, list: Pulses) {
        if (state == State.Low) flipped = true
    }
}

private fun createComponent(name: String, names: MutableMap<String, String>, components: MutableMap<String, Broadcast>) {
    var component: Broadcast
    var cleanName = name
    if (name == "broadcaster") {
        component = Broadcast(name)
    } else if (name.startsWith("&")) {
        cleanName = name.drop(1)
        component = Conjunction(cleanName)
    } else {
        cleanName = name.drop(1)
        component = FlipFlop(cleanName)
    }
    names[name] = cleanName
    components[cleanName] = component
}

private fun connectComponents(input: String, output: String, names: MutableMap<String, String>, components: MutableMap<String, Broadcast>) {
    for (out in output.split(", ")) {
        if (out !in components) components[out] = Output(out)

        val component = components[names[input]]
        components[out]?.let { component?.addOutput(it) }
        component?.let { components[out]?.addInput(it) }
    }
}

private fun readData(input: List<String>): MutableMap<String, Broadcast> {
    val components = mutableMapOf<String, Broadcast>().apply { put("button", Button()) }
    val names = mutableMapOf<String, String>()

    input.map { it.split(" -> ") }
        .onEach { (a, _) -> createComponent(a, names, components) }

    input.map { it.split(" -> ") }
        .onEach { (i, o) -> connectComponents(i, o, names, components) }

    components["button"]!!.addOutput(components["broadcaster"]!!)
    components["broadcaster"]!!.addInput(components["button"]!!)

    return components
}

private fun pushButton(components: MutableMap<String, Broadcast>, counts: MutableMap<State, Long>) {
    components["button"]?.pulseIn(Button("none"), State.Low, pulses)
    while (pulses.isNotEmpty()) {
        val (dest, src, state) = pulses.removeFirst()
        counts.compute(state) { _, b -> (b ?: 0) + 1 }
        dest.pulseIn(src, state, pulses)
    }
}

