import java.io.File

fun apply_rule(part: List<Int>, rule: List<Triple<Int, (Int) -> Boolean, String>>): String {
    for (sub_rule in rule) {
        if (sub_rule.second(part[sub_rule.first])) {
            return sub_rule.third
        }
    }

    return rule.last().third
}

fun run(
        part: List<Int>,
        rules: HashMap<String, List<Triple<Int, (Int) -> Boolean, String>>>
): Boolean {
    var current = "in"
    while (!current.equals("A") && !current.equals("R")) {
        current =
                apply_rule(part, rules.getOrDefault(current, listOf(Triple(0, { _ -> true }, "R"))))
    }
    return current.equals("A")
}

fun main() {
    var lines = File("input.txt").readLines()
    var rules = HashMap<String, List<Triple<Int, (Int) -> Boolean, String>>>()
    var parts = ArrayList<List<Int>>()

    var i = 0
    for (line in lines) {
        if (line.isEmpty()) {
            break
        }

        var categories = listOf('x', 'm', 'a', 's')

        var name = line.split("{")[0]
        var str_rules = line.split("{")[1].removeSuffix("}").split(",")
        var new_rules = ArrayList<Triple<Int, (Int) -> Boolean, String>>()

        for (rule in str_rules) {
            if (!rule.contains(':')) {
                new_rules.add(Triple(0, { _ -> true }, rule))
                continue
            }
            var split = rule.split(":")
            if (split[0].contains('>')) {
                var v = Integer.parseInt(split[0].split(">")[1])
                new_rules.add(
                        Triple(categories.indexOf(split[0].first()), { n -> n > v }, split[1])
                )
            } else {
                var v = Integer.parseInt(split[0].split("<")[1])
                new_rules.add(
                        Triple(categories.indexOf(split[0].first()), { n -> n < v }, split[1])
                )
            }
        }

        rules.set(name, new_rules)

        i++
    }

    for (j in i + 1 ..< lines.size) {
        parts.add(
                lines[j].removePrefix("{").removeSuffix("}").split(",").map {
                    Integer.parseInt(it.split("=")[1])
                }
        )
    }

    var sum = 0
    for (part in parts) {
        if (run(part, rules)) {
            sum += part.sum()
        }
    }
    println(sum)
}
