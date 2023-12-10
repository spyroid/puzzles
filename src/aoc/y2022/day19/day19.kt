package aoc.y2022.day19

import gears.puzzle

fun main() {
    puzzle("t1") { part1(inputLines("test.txt")) }
    puzzle("1") { part1(inputLines("input.txt")) }
    puzzle("t2") { part2(inputLines("test.txt")) }
    puzzle("2") { part2(inputLines("input.txt")) }
}

private fun part1(input: List<String>): Int {
    return input
        .map { Factory(it) }
        .sumOf { it.id * it.run(minutes = 24, times = 100000) }
}

private fun part2(input: List<String>): Int {
    return input
        .take(3)
        .map { Factory(it) }
        .map { it.run(minutes = 32, times = 10000000) }
        .reduce(Int::times)
}
private data class OreRobotBlueprint(val oreCost: Int)

private data class ClayRobotBlueprint(val oreCost: Int)

private data class ObsidianRobotBlueprint(val oreCost: Int, val clayCost: Int)

private data class GeodeRobotBlueprint(val oreCost: Int, val obsidianCost: Int)

private data class Minerals(var ore: Int = 0, var clay: Int = 0, var obsidian: Int = 0, var geode: Int = 0)

private data class Robots(var ore: Int = 1, var clay: Int = 0, var obsidian: Int = 0, var geode: Int = 0)

private data class Blueprints(
    var ore: OreRobotBlueprint? = null,
    var clay: ClayRobotBlueprint? = null,
    var obsidian: ObsidianRobotBlueprint? = null,
    var geode: GeodeRobotBlueprint? = null
)

private class Factory() {
    var id = 0

    private var minerals = Minerals()
    private var robots = Robots()
    private var blueprints = Blueprints()

    constructor(blueprint: String) : this() {
        val split = blueprint.split(": ")
        id = split.first().split(" ").last().toInt()

        val (oreCost, clayCost, obsidianCost, geodeCost) = split.last().split(". ")
            .map { it.split(" ") }

        blueprints = Blueprints(
            OreRobotBlueprint(oreCost.reversed()[1].toInt()),
            ClayRobotBlueprint(clayCost.reversed()[1].toInt()),
            ObsidianRobotBlueprint(obsidianCost.reversed()[4].toInt(), obsidianCost.reversed()[1].toInt()),
            GeodeRobotBlueprint(geodeCost.reversed()[4].toInt(), geodeCost.reversed()[1].toInt())
        )

    }

    private fun reset() {
        minerals = Minerals()
        robots = Robots()
    }

    fun run(minutes: Int, times: Int): Int {

        var max = 0

        repeat(times) {
            for (minute in 1..minutes) {
                val toBuild = decide()
                produce()
                build(toBuild)
            }
            max = maxOf(max, minerals.geode)
            reset()
        }

        return max

    }

    private fun produce() {
        minerals.ore += robots.ore
        minerals.clay += robots.clay
        minerals.obsidian += robots.obsidian
        minerals.geode += robots.geode
    }

    enum class Robot { GEODE, OBSIDIAN, CLAY, ORE }

    private fun decide(): Robot? {

        if (minerals.obsidian >= blueprints.geode!!.obsidianCost && minerals.ore >= blueprints.geode!!.oreCost) {
            return Robot.GEODE
        }

        if (robots.obsidian <= blueprints.geode!!.obsidianCost &&
            minerals.ore >= blueprints.obsidian!!.oreCost &&
            minerals.clay >= blueprints.obsidian!!.clayCost && listOf(0, 1).random() == 1
        ) {
            return Robot.OBSIDIAN
        }

        if (robots.clay <= blueprints.obsidian!!.clayCost &&
            minerals.ore >= blueprints.clay!!.oreCost &&
            listOf(0, 1).random() == 1
        ) {
            return Robot.CLAY
        }

        if (robots.ore <= listOf(
                blueprints.clay!!.oreCost,
                blueprints.obsidian!!.oreCost,
                blueprints.geode!!.oreCost
            ).max() &&
            minerals.ore >= blueprints.ore!!.oreCost && listOf(0, 1).random() == 1
        ) {
            return Robot.ORE
        }

        return null
    }

    private fun build(toBuild: Robot?) {
        when (toBuild) {
            Robot.GEODE -> {
                minerals.ore -= blueprints.geode!!.oreCost
                minerals.obsidian -= blueprints.geode!!.obsidianCost
                robots.geode++
            }

            Robot.OBSIDIAN -> {
                minerals.ore -= blueprints.obsidian!!.oreCost
                minerals.clay -= blueprints.obsidian!!.clayCost
                robots.obsidian++
            }

            Robot.CLAY -> {
                minerals.ore -= blueprints.clay!!.oreCost
                robots.clay++
            }

            Robot.ORE -> {
                minerals.ore -= blueprints.ore!!.oreCost
                robots.ore++
            }

            else -> null
        }
    }
}
