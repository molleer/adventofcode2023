import java.io.File
import java.util.TreeSet

fun roll_north(bolders: MutableList<Pair<Int, Int>>, cubes_y: List<TreeSet<Int>>) {
    var counts = HashMap<Pair<Int, Int>, Int>()
    for (i in 0 ..< bolders.size) {
        var closest = cubes_y[bolders[i].first].lower(bolders[i].second)
        var new_pos: Pair<Int, Int>
        if (closest === null) {
            new_pos = Pair(bolders[i].first, 0)
        } else {
            new_pos = Pair(bolders[i].first, closest + 1)
        }
        var corrected_pos = Pair(new_pos.first, new_pos.second + counts.getOrDefault(new_pos, 0))
        bolders.set(i, corrected_pos)
        counts.set(new_pos, counts.getOrDefault(new_pos, 0) + 1)
    }
}

fun roll_south(
        bolders: MutableList<Pair<Int, Int>>,
        cubes_y: List<TreeSet<Int>>,
        max_y: Int,
) {
    var counts = HashMap<Pair<Int, Int>, Int>()
    for (i in 0 ..< bolders.size) {
        var closest = cubes_y[bolders[i].first].higher(bolders[i].second)
        var new_pos: Pair<Int, Int>
        if (closest === null) {
            new_pos = Pair(bolders[i].first, max_y)
        } else {
            new_pos = Pair(bolders[i].first, closest - 1)
        }
        var corrected_pos = Pair(new_pos.first, new_pos.second - counts.getOrDefault(new_pos, 0))
        bolders.set(i, corrected_pos)
        counts.set(new_pos, counts.getOrDefault(new_pos, 0) + 1)
    }
}

fun roll_west(
        bolders: MutableList<Pair<Int, Int>>,
        cubes_x: List<TreeSet<Int>>,
) {
    var counts = HashMap<Pair<Int, Int>, Int>()
    for (i in 0 ..< bolders.size) {
        var closest = cubes_x[bolders[i].second].lower(bolders[i].first)
        var new_pos: Pair<Int, Int>
        if (closest === null) {
            new_pos = Pair(0, bolders[i].second)
        } else {
            new_pos = Pair(closest + 1, bolders[i].second)
        }
        var corrected_pos = Pair(new_pos.first + counts.getOrDefault(new_pos, 0), new_pos.second)
        bolders.set(i, corrected_pos)
        counts.set(new_pos, counts.getOrDefault(new_pos, 0) + 1)
    }
}

fun roll_east(
        bolders: MutableList<Pair<Int, Int>>,
        cubes_x: List<TreeSet<Int>>,
        max_x: Int,
) {
    var counts = HashMap<Pair<Int, Int>, Int>()
    for (i in 0 ..< bolders.size) {
        var closest = cubes_x[bolders[i].second].higher(bolders[i].first)
        var new_pos: Pair<Int, Int>
        if (closest === null) {
            new_pos = Pair(max_x, bolders[i].second)
        } else {
            new_pos = Pair(closest - 1, bolders[i].second)
        }
        var corrected_pos = Pair(new_pos.first - counts.getOrDefault(new_pos, 0), new_pos.second)
        bolders.set(i, corrected_pos)
        counts.set(new_pos, counts.getOrDefault(new_pos, 0) + 1)
    }
}

fun cycle(
        bolders: MutableList<Pair<Int, Int>>,
        cubes_x: List<TreeSet<Int>>,
        cubes_y: List<TreeSet<Int>>,
        max_x: Int,
        max_y: Int
) {
    roll_north(bolders, cubes_y)
    roll_west(bolders, cubes_x)
    roll_south(bolders, cubes_y, max_y)
    roll_east(bolders, cubes_x, max_x)
}

fun print_rocks(
        rocks: Set<Pair<Int, Int>>,
        cubes: Set<Pair<Int, Int>>,
        max_x: Int,
        max_y: Int,
        lines: List<String>
) {
    for (y in 0 ..< max_y) {
        for (x in 0 ..< max_x) {
            var pos = Pair(x, y)
            if (pos in rocks && lines[y][x] == '#') {
                print("X")
            } else if (pos in rocks) {
                print("O")
            } else if (lines[y][x] == '#') {
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
    var cubes_x = List<TreeSet<Int>>(lines.size) { TreeSet<Int>() }
    var cubes_y = List<TreeSet<Int>>(lines[0].length) { TreeSet<Int>() }
    var positions = ArrayList<Pair<Int, Int>>()

    for (y in 0 ..< lines.size) {
        for (x in 0 ..< lines[y].length) {
            if (lines[y][x] == '#') {
                cubes_x[y].add(x)
                cubes_y[x].add(y)
            }
            if (lines[y][x] == 'O') {
                positions.add(Pair(x, y))
            }
        }
    }

    for (i in 1..1000_000_000) {
        if (i % 10_000 == 0) {
            println(i)
        }
        cycle(positions, cubes_x, cubes_y, lines[0].length - 1, lines.size - 1)
    }
    // cycle(positions, cubes_x, cubes_y, lines[0].length - 1, lines.size - 1)
    print_rocks(positions.toSet(), setOf(), lines[0].length, lines.size, lines)
}
