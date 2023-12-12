import java.io.File

fun spring_combos_damaged(
        pos: Int,
        group_size: Int,
        group_index: Int,
        conditions: String,
        groups: List<Int>
): Int {
    if (group_size > 0 && group_size != groups[group_index]) {
        return 0
    }
    if (group_size > 0) {
        return spring_combos(pos + 1, 0, group_index + 1, conditions, groups)
    }
    return spring_combos(pos + 1, 0, group_index, conditions, groups)
}

fun spring_combos(
        pos: Int,
        group_size: Int,
        group_index: Int,
        conditions: String,
        groups: List<Int>
): Int {
    if (pos >= conditions.length &&
                    group_index == groups.size - 1 &&
                    group_size == groups[group_index]
    ) {
        return 1
    }
    if (pos >= conditions.length && group_size == 0 && groups.size == group_index) {
        return 1
    }
    if (pos >= conditions.length) {
        return 0
    }

    if (group_size > 0 && groups.size <= group_index) {
        return 0
    }
    if (group_size > 0 && groups[group_index] < group_size) {
        return 0
    }

    if (conditions[pos] == '.') {
        return spring_combos_damaged(pos, group_size, group_index, conditions, groups)
    }

    if (conditions[pos] == '#') {
        return spring_combos(pos + 1, group_size + 1, group_index, conditions, groups)
    }

    return spring_combos_damaged(pos, group_size, group_index, conditions, groups) +
            spring_combos(pos + 1, group_size + 1, group_index, conditions, groups)
}

fun main() {
    var lines = File("input.txt").readLines()
    var count = 0
    for (line in lines) {
        var conditions = line.split(" ")[0]
        var groups = line.split(" ")[1].split(",").map { Integer.parseInt(it) }

        count += spring_combos(0, 0, 0, conditions, groups)
    }
    println(count)
}
