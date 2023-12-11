import java.io.File
import kotlin.math.abs

fun expand_col(x: Int, lines: ArrayList<String>) {
    for (y in 0 ..< lines.size) {
        lines.set(y, lines[y].substring(0, x) + "1" + lines[y].substring(x + 1))
    }
}

fun is_empty_col(x: Int, lines: List<String>): Boolean {
    for (line in lines) {
        if (line[x] == '#') {
            return false
        }
    }
    return true
}

fun expand_x_pos(x: Int, positions: ArrayList<Pair<Long, Long>>, amount: Long = 2) {
    for (i in 0 ..< positions.size) {
        if (positions[i].first > x) {
            positions.set(i, Pair(positions[i].first + amount - 1, positions[i].second))
        }
    }
}

fun expand_y_pos(y: Int, positions: ArrayList<Pair<Long, Long>>, amount: Long = 2) {
    for (i in 0 ..< positions.size) {
        if (positions[i].second > y) {
            positions.set(i, Pair(positions[i].first, positions[i].second + amount - 1))
        }
    }
}

fun main() {
    var lines = ArrayList(File("input.txt").readLines())
    var positions = ArrayList<Pair<Long, Long>>()
    for (y in 0 ..< lines.size) {
        for (x in 0 ..< lines[y].length) {
            if (lines[y][x] == '#') {
                positions.add(Pair(x.toLong(), y.toLong()))
            }
        }
    }

    for (i in lines.size - 1 downTo 0) {
        if (lines[i].toSet().size == 1) {
            expand_y_pos(i, positions, 1000_000)
        }
    }

    for (x in lines[0].length - 1 downTo 0) {
        if (is_empty_col(x, lines)) {
            expand_x_pos(x, positions, 1000_000)
        }
    }

    var sum: Long = 0L
    for (i in 0 ..< positions.size - 1) {
        for (j in i + 1 ..< positions.size) {
            sum +=
                    abs(positions[i].first - positions[j].first) +
                            abs(positions[i].second - positions[j].second)
        }
    }
    print(sum)
}
