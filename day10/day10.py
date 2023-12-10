def exits(map, pos):
    pipes = {
        "|": [(0, 1), (0, -1)],
        "-": [(1, 0), (-1, 0)],
        "L": [(0, -1), (1, 0)],
        "J": [(0, -1), (-1, 0)],
        "7": [(0, 1), (-1, 0)],
        "F": [(0, 1), (1, 0)],
    }
    diffs = pipes[map[pos[1]][pos[0]]]
    return [
        (pos[0] + diffs[0][0], pos[1] + diffs[0][1]),
        (pos[0] + diffs[1][0], pos[1] + diffs[1][1])
        ]

def find_path(map, start):
    path = [start]
    next = list(filter(lambda p: map[p[1]][p[0]] != "S", exits(map, start)))[0]
    while map[next[1]][next[0]] != "S":
        path.append(next)
        next = list(filter(lambda x: x != path[-2], exits(map, next)))[0]
    return path

def main():
    with open("input.txt", "r") as f:
        lines = f.readlines()

    pos = []
    for y in range(len(lines) - 1):
        if "S" in lines[y]:
            pos = (0, y)
            break
    
    for x in range(len(lines[pos[1]]) - 1):
        if lines[pos[1]][x] == "S":
            pos = (x, pos[1])
            break

    for offset in [(0, 1), (0, -1), (1, 0), (-1, 0)]:
        adjacent = (pos[0] + offset[0], pos[1] + offset[1])
        if pos in exits(lines, adjacent):
            print(adjacent)
            path = find_path(lines, adjacent)
            print(int(len(path) / 2) + 1)
            break


if __name__ == "__main__":
    main()