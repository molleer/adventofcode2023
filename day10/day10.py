from typing import List, Set, Tuple


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

def flood(pos, explored: Set[Tuple[int, int]], max_x: int, max_y: int) -> Set[Tuple[int, int]]:
    dirs = [(0, 1), (0, -1), (1, 0), (-1, 0)]
    explored.add(pos)
    new_positions = {pos}
    for d in dirs:
        new_pos = (pos[0] + d[0], pos[1] + d[1])
        if new_pos[0] < 0 or new_pos[0] >= max_x or new_pos[1] < 0 or new_pos[1] >= max_y or new_pos in explored:
            continue
        new_positions |= flood(new_pos, explored, max_x, max_y)
    
    return new_positions

def get_right(pos, dir):
    # Right
    # 1 0 -> 0 -1
    # 0 1    1  0
    return (pos[0] - dir[1], pos[1] + dir[0])

    # Left
    # 1 0 -> 0  1
    # 0 1    -1 0
    # return (pos[0] + dir[1], pos[1] - dir[0])

def right_of(start, dir, path: List[Tuple[int, int]], max_x, max_y) -> List[Tuple[int, int]]:
    pos = start
    right_elements = set()    
    right = get_right(pos, dir)
    if right not in path and right != start:
        right_elements.add(right)

    path = [start] + path + [start, path[0]]

    for i in range(1, len(path)):
        right = get_right(pos, dir)
        if right not in path and right != start and right[0] >= 0 and right[0] < max_x and right[1] >= 0 and right[1] < max_y:
            right_elements.add(right)

        dir = (path[i][0] - pos[0], path[i][1] - pos[1])
        right = get_right(pos, dir)
        if right not in path and right != start and right[0] >= 0 and right[0] < max_x and right[1] >= 0 and right[1] < max_y:
            right_elements.add(right)
        pos = path[i]
    
    return right_elements
                


def get_path(start, lines):
    pos = start

    for offset in [(0, 1), (0, -1), (1, 0), (-1, 0)]:
        adjacent = (pos[0] + offset[0], pos[1] + offset[1])
        if pos in exits(lines, adjacent):
            return find_path(lines, adjacent)
            # print(int(len(path) / 2) + 1)
    return []

def main():
    with open("adventofcode2023/day10/input.txt", "r") as f:
        lines = f.readlines()

    for y in range(len(lines) - 1):
        if "S" in lines[y]:
            pos = (0, y)
            break
    
    for x in range(len(lines[pos[1]]) - 1):
        if lines[pos[1]][x] == "S":
            pos = (x, pos[1])
            break

    path = get_path(pos, lines)
    
    rights = right_of(pos, (path[0][0] - pos[0], path[0][1] - pos[1]), path, len(lines[0]), len(lines))
    all_rights = set()

    for r in rights:
        all_rights |= flood(r, all_rights | set(path) | {pos}, len(lines[0]) - 1, len(lines))
    
    #for y in range(len(lines)):
    #    line = ""
    #    for x in range(len(lines[y]) - 1):
    #        if (x, y) in path:
    #            line += "#"
    #        elif (x, y) in all_rights:
    #            line += "R"
    #        else:
    #            line += "."
    #    print(line)

    print(len(all_rights))


if __name__ == "__main__":
    main()