import java.io.File

fun is_valid(line: String): Boolean {
    var split = line.split(": ")[1]
    var cubes = HashMap<String, Int>()

    var sets = split.split("; ")
    for (set in sets) {
        var splits = set.split(", ")
        for (s in splits) {
            var color_split = s.split(" ")
            cubes.set(
                    color_split[1],
                    cubes.getOrDefault(color_split[1], 0) + Integer.parseInt(color_split[0])
            )
        }

        if (!(cubes.getOrDefault("red", 0) <= 12 &&
                        cubes.getOrDefault("green", 0) <= 13 &&
                        cubes.getOrDefault("blue", 0) <= 14)
        ) {
            return false
        }
        cubes.clear()
    }
    return true
}

fun game_power(line: String): Int {
    var split = line.split(": ")[1]
    var cubes = HashMap<String, Int>()

    var sets = split.split("; ")
    for (set in sets) {
        var splits = set.split(", ")
        for (s in splits) {
            var color_split = s.split(" ")
            if (color_split[1] in cubes.keys) {
                var n = Integer.parseInt(color_split[0])
                if (cubes.getOrDefault(color_split[1], 0) < n) {
                    cubes.set(color_split[1], n)
                }
            } else {
                cubes.set(color_split[1], Integer.parseInt(color_split[0]))
            }
        }
    }
    return cubes.values.fold(1) { a, b -> a * b }
}

fun main() {
    var lines = File("input.txt").readLines()
    var sum = 0
    var prod_sum = 0
    for (line in lines) {
        prod_sum += game_power(line)
        if (is_valid(line)) {
            var game = Integer.parseInt(line.split(":")[0].removePrefix("Game "))
            sum += game
        }
    }
    println(sum)
    println(prod_sum)
}
