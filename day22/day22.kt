import java.io.File
import kotlin.math.max

fun is_clear(z_offset: Int, positions: List<List<Int>>, grid: List<List<List<Int>>>): Boolean {
    for (pos in positions) {
        if (grid[pos[2] + z_offset][pos[1]][pos[0]] != -1) {
            return false
        }
    }
    return true
}

fun may_fall(positions: List<List<Int>>, grid: List<List<List<Int>>>): Int {
    var z_offset = -1
    var start_z = positions[0][2]
    while (start_z + z_offset >= 0 && is_clear(z_offset, positions, grid)) {
        z_offset--
    }
    return start_z + z_offset + 1
}

fun subsets(set: Set<Int>): Set<Set<Int>> {
    if (set.isEmpty()) {
        return setOf(set)
    }

    var first_el = set.first()
    var subs = subsets(set.filter { it != first_el }.toSet())
    var new_subs = subs.map { it + first_el }.toSet()
    return subs + new_subs
}

fun main() {
    var lines = File("input.txt").readLines()
    var bricks = ArrayList<Pair<List<Int>, List<Int>>>()

    for (line in lines) {
        var split = line.split("~")
        bricks.add(
                Pair(split[0].split(",").map { it.toInt() }, split[1].split(",").map { it.toInt() })
        )
    }

    var maxes =
            Triple(
                    max(bricks.map { it.first[0] }.max(), bricks.map { it.second[0] }.max()) + 1,
                    max(bricks.map { it.first[1] }.max(), bricks.map { it.second[1] }.max()) + 1,
                    max(bricks.map { it.first[2] }.max(), bricks.map { it.second[2] }.max()) + 1
            )

    var grid =
            MutableList<MutableList<MutableList<Int>>>(maxes.third) {
                MutableList<MutableList<Int>>(maxes.second) { MutableList<Int>(maxes.first) { -1 } }
            }

    for (i in 0 ..< bricks.size) {
        var brick = bricks[i]
        for (z in brick.first[2]..brick.second[2]) {
            for (y in brick.first[1]..brick.second[1]) {
                for (x in brick.first[0]..brick.second[0]) {
                    grid[z][y][x] = i
                }
            }
        }
    }

    for (z in 0 ..< grid.size) {
        var ids = grid[z].flatten().toSet()

        for (id in ids) {
            if (id == -1) {
                continue
            }

            var positions = ArrayList<List<Int>>()
            for (y in 0 ..< grid[z].size) {
                for (x in 0 ..< grid[z][y].size) {
                    if (grid[z][y][x] == id) {
                        positions.add(listOf(x, y, z))
                    }
                }
            }

            var new_z = may_fall(positions, grid)

            for (pos in positions) {
                grid[pos[2]][pos[1]][pos[0]] = -1
                grid[new_z][pos[1]][pos[0]] = id
            }
        }
    }

    var supports = HashMap<Int, MutableSet<Int>>()
    for (z in 0 ..< grid.size) {
        for (y in 0 ..< grid[z].size) {
            for (x in 0 ..< grid[z][y].size) {
                if (grid[z][y][x] == -1 ||
                                grid[z + 1][y][x] == -1 ||
                                grid[z][y][x] == grid[z + 1][y][x]
                ) {
                    continue
                }

                if (!(grid[z][y][x] in supports.keys)) {
                    supports.set(grid[z][y][x], HashSet<Int>())
                }
                supports.get(grid[z][y][x])?.add(grid[z + 1][y][x])
            }
        }
    }

    var ids_at = grid.map { it.flatten().toSet() }
    var count = 0

    for (z in 0 ..< grid.size - 1) {
        if (ids_at[z].size <= 1) {
            continue
        }

        for (id in ids_at[z]) {
            if (id in ids_at[z + 1]) {
                continue
            }

            var supported_by_others =
                    ids_at[z]
                            .filter { it != id }
                            .map { supports.getOrDefault(it, HashSet<Int>()) }
                            .flatten()
                            .toSet()

            if (supports.getOrDefault(id, HashSet()).all { it in supported_by_others }) {
                count++
            }
        }
    }

    /*for (k1 in supports.keys) {
        for (k2 in supports.keys) {
            if (k1 == k2) {
                continue
            }

            if (supports.getOrDefault(k2, HashSet()).all {
                        it in supports.getOrDefault(k1, HashSet())
                    }
            ) {
                count++
                break
            }
        }
    }*/

    println(count)
}
