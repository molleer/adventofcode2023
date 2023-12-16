import java.io.File
import kotlin.math.max

fun next_dir(map: List<String>, pos: Pair<Int, Int>, dir: Pair<Int, Int>): List<Pair<Int, Int>> {
    var next_dir: List<Pair<Int, Int>> = listOf(dir)
    if (map[pos.second][pos.first] == '|') {
        if (dir.second == 0) {
            next_dir = listOf(Pair(0, 1), Pair(0, -1))
        }
    } else if (map[pos.second][pos.first] == '-') {
        if (dir.first == 0) {
            next_dir = listOf(Pair(1, 0), Pair(-1, 0))
        }
    } else if (map[pos.second][pos.first] == '/') {
        next_dir = listOf(Pair(-dir.second, -dir.first))
    } else if (map[pos.second][pos.first] == '\\') {
        next_dir = listOf(Pair(dir.second, dir.first))
    }

    return next_dir
}

fun step(
        map: List<String>,
        pos: Pair<Int, Int>,
        dir: Pair<Int, Int>,
        mem: MutableSet<Pair<Pair<Int, Int>, Pair<Int, Int>>>
) {
    var pos_dir = Pair(pos, dir)
    if (pos_dir in mem) {
        return
    } else {
        mem.add(pos_dir)
    }

    var next = next_dir(map, pos, dir)
    var next_pos_dirs =
            next.map { Pair(Pair(pos.first + it.first, pos.second + it.second), it) }.filter {
                it.first.first >= 0 &&
                        it.first.first < map[0].length &&
                        it.first.second >= 0 &&
                        it.first.second < map.size
            }

    for (pd in next_pos_dirs) {
        step(map, pd.first, pd.second, mem)
    }
}

fun main() {
    var lines = File("input.txt").readLines()
    var maximum = 0

    for (i in 0 ..< lines.size) {
        var mem = HashSet<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        step(lines, Pair(0, i), Pair(1, 0), mem)
        maximum = max(mem.map { it.first }.toSet().size, maximum)
    }

    for (i in 0 ..< lines.size) {
        var mem = HashSet<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        step(lines, Pair(lines[0].length - 1, i), Pair(-1, 0), mem)
        maximum = max(mem.map { it.first }.toSet().size, maximum)
    }

    for (i in 0 ..< lines[0].length) {
        var mem = HashSet<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        step(lines, Pair(i, 0), Pair(0, 1), mem)
        maximum = max(mem.map { it.first }.toSet().size, maximum)
    }

    for (i in 0 ..< lines[0].length) {
        var mem = HashSet<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        step(lines, Pair(i, lines.size - 1), Pair(0, -1), mem)
        maximum = max(mem.map { it.first }.toSet().size, maximum)
    }

    println(maximum)
}
