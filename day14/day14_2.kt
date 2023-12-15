import java.io.File
import java.util.TreeSet

fun roll_north(bolders: MutableList<Pair<Int, Int>>, destination_grid: List<List<Pair<Int, Int>>>) {
    var counts = HashMap<Pair<Int, Int>, Int>()
    for (i in 0 ..< bolders.size) {
        var next = destination_grid[bolders[i].second][bolders[i].first]
        var corrected_pos = Pair(next.first, next.second + counts.getOrDefault(next, 0))
        bolders.set(i, corrected_pos)
        counts.set(next, counts.getOrDefault(next, 0) + 1)
    }
}

fun roll_south(
        bolders: MutableList<Pair<Int, Int>>,
        destination_grid: List<List<Pair<Int, Int>>>,
) {
    var counts = HashMap<Pair<Int, Int>, Int>()
    for (i in 0 ..< bolders.size) {
        var next = destination_grid[bolders[i].second][bolders[i].first]
        var corrected_pos = Pair(next.first, next.second - counts.getOrDefault(next, 0))
        bolders.set(i, corrected_pos)
        counts.set(next, counts.getOrDefault(next, 0) + 1)
    }
}

fun roll_west(
        bolders: MutableList<Pair<Int, Int>>,
        destination_grid: List<List<Pair<Int, Int>>>,
) {
    var counts = HashMap<Pair<Int, Int>, Int>()
    for (i in 0 ..< bolders.size) {
        var next = destination_grid[bolders[i].second][bolders[i].first]
        var corrected_pos = Pair(next.first + counts.getOrDefault(next, 0), next.second)
        bolders.set(i, corrected_pos)
        counts.set(next, counts.getOrDefault(next, 0) + 1)
    }
}

fun roll_east(
        bolders: MutableList<Pair<Int, Int>>,
        destination_grid: List<List<Pair<Int, Int>>>,
) {
    var counts = HashMap<Pair<Int, Int>, Int>()
    for (i in 0 ..< bolders.size) {
        var next = destination_grid[bolders[i].second][bolders[i].first]
        var corrected_pos = Pair(next.first - counts.getOrDefault(next, 0), next.second)
        bolders.set(i, corrected_pos)
        counts.set(next, counts.getOrDefault(next, 0) + 1)
    }
}

fun cycle(
        bolders: MutableList<Pair<Int, Int>>,
        destination_grid_north: List<List<Pair<Int, Int>>>,
        destination_grid_south: List<List<Pair<Int, Int>>>,
        destination_grid_west: List<List<Pair<Int, Int>>>,
        destination_grid_east: List<List<Pair<Int, Int>>>,
) {
    roll_north(bolders, destination_grid_north)
    roll_west(bolders, destination_grid_west)
    roll_south(bolders, destination_grid_south)
    roll_east(bolders, destination_grid_east)
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

    var destination_grid_north =
            MutableList<MutableList<Pair<Int, Int>>>(lines.size) {
                MutableList(lines[it].length) { Pair(0, 0) }
            }

    for (y in 0 ..< lines.size) {
        for (x in 0 ..< lines[y].length) {
            var closest = cubes_y[x].lower(y)
            var new_pos: Pair<Int, Int> = Pair(x, 0)
            if (closest !== null) {
                new_pos = Pair(x, closest + 1)
            }
            destination_grid_north[y][x] = new_pos
        }
    }

    var destination_grid_south =
            MutableList<MutableList<Pair<Int, Int>>>(lines.size) {
                MutableList(lines[it].length) { Pair(0, 0) }
            }

    for (y in 0 ..< lines.size) {
        for (x in 0 ..< lines[y].length) {
            var closest = cubes_y[x].higher(y)
            var new_pos: Pair<Int, Int> = Pair(x, lines.size - 1)
            if (closest !== null) {
                new_pos = Pair(x, closest - 1)
            }
            destination_grid_south[y][x] = new_pos
        }
    }

    var destination_grid_west =
            MutableList<MutableList<Pair<Int, Int>>>(lines.size) {
                MutableList(lines[it].length) { Pair(0, 0) }
            }

    for (y in 0 ..< lines.size) {
        for (x in 0 ..< lines[y].length) {
            var closest = cubes_x[y].lower(x)
            var new_pos: Pair<Int, Int> = Pair(0, y)
            if (closest !== null) {
                new_pos = Pair(closest + 1, y)
            }
            destination_grid_west[y][x] = new_pos
        }
    }

    var destination_grid_east =
            MutableList<MutableList<Pair<Int, Int>>>(lines.size) {
                MutableList(lines[it].length) { Pair(0, 0) }
            }

    for (y in 0 ..< lines.size) {
        for (x in 0 ..< lines[y].length) {
            var closest = cubes_x[y].higher(x)
            var new_pos: Pair<Int, Int> = Pair(lines[0].length - 1, y)
            if (closest !== null) {
                new_pos = Pair(closest - 1, y)
            }
            destination_grid_east[y][x] = new_pos
        }
    }

    var sets = HashSet<Set<Pair<Int, Int>>>()
    sets.add(positions.toSet())

    for (i in 1..208) {
        cycle(
                positions,
                destination_grid_north,
                destination_grid_south,
                destination_grid_west,
                destination_grid_east
        )
    }
    println(positions.fold(0) { a, b -> a + lines.size - b.second })
}
