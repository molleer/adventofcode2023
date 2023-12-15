import java.io.File

fun roll_south(
        rocks: Set<Pair<Int, Int>>,
        cubes: Set<Pair<Int, Int>>,
        max_y: Int
): Set<Pair<Int, Int>> {
    var new_rocks = HashSet<Pair<Int, Int>>()
    var sorted_rocks = rocks.sortedBy { -it.second }

    for (rock in sorted_rocks) {
        var new_pos = rock
        var next_pos = Pair(new_pos.first, new_pos.second + 1)
        while (next_pos.second < max_y && !(next_pos in cubes) && !(next_pos in new_rocks)) {
            new_pos = next_pos
            next_pos = Pair(new_pos.first, new_pos.second + 1)
        }
        new_rocks.add(new_pos)
    }

    return new_rocks
}

fun roll_east(
        rocks: Set<Pair<Int, Int>>,
        cubes: Set<Pair<Int, Int>>,
        max_x: Int
): Set<Pair<Int, Int>> {
    var new_rocks = HashSet<Pair<Int, Int>>()
    var sorted_rocks = rocks.sortedBy { -it.first }

    for (rock in sorted_rocks) {
        var new_pos = rock
        var next_pos = Pair(new_pos.first + 1, new_pos.second)
        while (next_pos.first < max_x && !(next_pos in cubes) && !(next_pos in new_rocks)) {
            new_pos = next_pos
            next_pos = Pair(new_pos.first + 1, new_pos.second)
        }
        new_rocks.add(new_pos)
    }

    return new_rocks
}

fun roll_north(rocks: Set<Pair<Int, Int>>, cubes: Set<Pair<Int, Int>>): Set<Pair<Int, Int>> {
    var new_rocks = HashSet<Pair<Int, Int>>()
    var sorted_rocks = rocks.sortedBy { it.second }

    for (rock in sorted_rocks) {
        var new_pos = rock
        var next_pos = Pair(new_pos.first, new_pos.second - 1)
        while (next_pos.second >= 0 && !(next_pos in cubes) && !(next_pos in new_rocks)) {
            new_pos = next_pos
            next_pos = Pair(new_pos.first, new_pos.second - 1)
        }
        new_rocks.add(new_pos)
    }

    return new_rocks
}

fun roll_west(rocks: Set<Pair<Int, Int>>, cubes: Set<Pair<Int, Int>>): Set<Pair<Int, Int>> {
    var new_rocks = HashSet<Pair<Int, Int>>()
    var sorted_rocks = rocks.sortedBy { it.first }

    for (rock in sorted_rocks) {
        var new_pos = rock
        var next_pos = Pair(new_pos.first - 1, new_pos.second)
        while (next_pos.first >= 0 && !(next_pos in cubes) && !(next_pos in new_rocks)) {
            new_pos = next_pos
            next_pos = Pair(new_pos.first - 1, new_pos.second)
        }
        new_rocks.add(new_pos)
    }

    return new_rocks
}

fun cycle(
        rocks: Set<Pair<Int, Int>>,
        cubes: Set<Pair<Int, Int>>,
        max_x: Int,
        max_y: Int
): Set<Pair<Int, Int>> {
    return roll_east(
            roll_south(roll_west(roll_north(rocks, cubes), cubes), cubes, max_y),
            cubes,
            max_x
    )
}

fun print_rocks(rocks: Set<Pair<Int, Int>>, cubes: Set<Pair<Int, Int>>, max_x: Int, max_y: Int) {
    for (y in 0 ..< max_y) {
        for (x in 0 ..< max_x) {
            var pos = Pair(x, y)
            if (pos in rocks && pos in cubes) {
                print("X")
            } else if (pos in rocks) {
                print("O")
            } else if (pos in cubes) {
                print("#")
            } else {
                print(".")
            }
        }
        println()
    }
}

fun main() {
    var lines = File("input.txt").readLines()
    var cubes = HashSet<Pair<Int, Int>>()
    var rocks = ArrayList<Pair<Int, Int>>()

    for (y in 0 ..< lines.size) {
        for (x in 0 ..< lines[y].length) {
            when (lines[y][x]) {
                '#' -> cubes.add(Pair(x, y))
                'O' -> rocks.add(Pair(x, y))
            }
        }
    }

    var new_rocks = rocks.toSet()

    // println(roll_north(new_rocks, cubes).fold(0) { a, b -> a + lines.size - b.second })

    for (i in 0 ..< 1000_000_000) {
        if (i % 1000 == 0) {
            println(i)
        }
        new_rocks = cycle(new_rocks, cubes, lines[0].length, lines.size)
    }
    println(roll_north(new_rocks, cubes).fold(0) { a, b -> a + lines.size - b.second })
}
