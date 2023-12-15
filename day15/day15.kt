import java.io.File

fun hash(line: String): Int {
    var hash = 0
    for (c in line) {
        hash += c.code
        hash *= 17
        hash %= 256
    }
    return hash
}

fun main() {
    var codes = File("input.txt").readLines()[0].split(",")
    var map = HashMap<Int, List<Pair<String, Int>>>()

    for (code in codes) {
        if (code[code.length - 1] != '-') {
            var split = code.split("=")
            var h = hash(split[0])
            var found = false

            var existing = ArrayList(map.getOrDefault(h, listOf()))
            for (i in 0 ..< existing.size) {
                if (existing[i].first.equals(split[0])) {
                    existing.set(i, Pair(split[0], Integer.parseInt(split[1])))
                    map.set(h, existing)
                    found = true
                    break
                }
            }
            if (found) {
                continue
            }

            map.set(h, existing + listOf(Pair(split[0], Integer.parseInt(split[1]))))
        } else {
            var key = code.substring(0, code.length - 1)
            var h = hash(key)
            map.set(h, map.getOrDefault(h, listOf()).filter { it.first != key })
        }
    }

    var sum = 0
    for (key in map.keys) {
        var list = map.getOrDefault(key, listOf())
        for (i in 0 ..< list.size) {
            sum += (key + 1) * (i + 1) * list[i].second
        }
    }
    println(sum)
}
