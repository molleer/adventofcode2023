import java.io.File

fun next(pos: Pair<Int, Int>, map: List<String>): List<Pair<Int, Int>> {
    var nexts = ArrayList<Pair<Int, Int>>()
    if (pos.first + 1 < map[pos.second].length && map[pos.second][pos.first + 1] != '#') {
        nexts.add(Pair(pos.first + 1, pos.second))
    }
    if (pos.first - 1 >= 0 &&
                    map[pos.second][pos.first - 1] !=
                            '#' // &&                    map[pos.second][pos.first - 1] != '>'
    ) {
        nexts.add(Pair(pos.first - 1, pos.second))
    }
    if (pos.second + 1 < map.size && map[pos.second + 1][pos.first] != '#') {
        nexts.add(Pair(pos.first, pos.second + 1))
    }
    if (pos.second - 1 >= 0 &&
                    map[pos.second - 1][pos.first] !=
                            '#' // && map[pos.second - 1][pos.first] != 'v'
    ) {
        nexts.add(Pair(pos.first, pos.second - 1))
    }
    return nexts
}

fun main() {
    var lines = File("input.txt").readLines()
    var pos = Pair(1, 0)
    var goal = Pair(lines[0].length - 2, lines.size - 1)
    var fringe =
            HashSet<Pair<Pair<Int, Int>, HashSet<Pair<Int, Int>>>>(
                    next(pos, lines).map { Pair(it, HashSet<Pair<Int, Int>>(listOf(pos))) }
            )
    var new_fringe = HashSet<Pair<Pair<Int, Int>, HashSet<Pair<Int, Int>>>>()

    var goal_steps = 0
    var i = 1

    while (!fringe.isEmpty()) {
        i++
        for (new_pos in fringe) {
            var new_set = HashSet<Pair<Int, Int>>(new_pos.second)
            new_set.add(new_pos.first)
            new_fringe.addAll(
                    next(new_pos.first, lines).filter { !(it in new_pos.second) }.map {
                        Pair(it, new_set)
                    }
            )
        }

        if (!new_fringe.filter { it.first.equals(goal) }.isEmpty()) {
            goal_steps = i
            println(i)
        }
        fringe = new_fringe
        new_fringe = HashSet<Pair<Pair<Int, Int>, HashSet<Pair<Int, Int>>>>()
    }
    println(goal_steps)
}
