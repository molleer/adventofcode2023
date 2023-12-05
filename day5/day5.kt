import java.io.File

fun in_range(range: Triple<Long, Long, Long>, seed: Long): Boolean {
    return seed >= range.second && seed < range.second + range.third
}

fun apply_cat(category: List<Triple<Long, Long, Long>>, seed: Long): Long {
    for (range in category) {
        if (in_range(range, seed)) {
            return seed - range.second + range.first
        }
    }

    return seed
}

fun main() {
    var lines = File("input.txt").readLines()

    var seeds = lines[0].removePrefix("seeds: ").split(" ").map { it.toLong() }
    var categories = ArrayList<ArrayList<Triple<Long, Long, Long>>>()
    for (i in 2 ..< lines.size) {
        if (lines[i].contains("map")) {
            categories.add(ArrayList())
            continue
        }
        if (lines[i].equals("")) {
            continue
        }

        var nums = lines[i].split(" ").map { it.toLong() }
        categories.last().add(Triple(nums[0], nums[1], nums[2]))
    }

    var real_seeds = HashSet<Long>()
    for (i in 0 ..< seeds.size step 2) {
        real_seeds.addAll(seeds[i + 1]..seeds[i])
    }
    seeds = real_seeds.toList()

    for (cat in categories) {
        println(cat)
        seeds = seeds.map { apply_cat(cat, it) }
    }
    println(seeds.min())
}
