package aoc.y2015.day15

import gears.puzzle

private fun main() {
    puzzle { recipe() }
    puzzle { recipe(true) }
}

private fun recipe(withCalories: Boolean = false): Int {
    var res = 0

    for (sugar in 0..100) {
        for (sprinkles in 0..(100 - sugar)) {
            for (candy in 0..(100 - sugar - sprinkles)) {
                val choco = 100 - sugar - sprinkles - candy
                if (choco < 0) continue

                val capacity = maxOf(sugar * 3 + sprinkles * -3 - candy, 0)
                val durability = maxOf(sprinkles * 3, 0)
                val flavor = maxOf(candy * 4 + choco * -2, 0)
                val texture = maxOf(sugar * -3 + choco * 2, 0)
                val calories = maxOf(sugar * 2 + sprinkles * 9 + candy + choco * 8, 0)

                if (withCalories && calories != 500) continue
                res = maxOf(res, capacity * durability * flavor * texture)
            }
        }
    }

    return res
}
