
def step(curr, network, instruction):
    if instruction == "R":
        return network[curr][1]
    if instruction == "L":
        return network[curr][0]

def main():
    with open("input.txt", "r") as f:
        lines = f.readlines()
    instructions = lines[0]
    network = {}
    count = 0

    for line in lines[2:]:
        next = line.split(" = (")[1].replace(")\n", "")
        network[line.split(" ")[0]] = next.split(", ")
    end = False
    positions = list(filter(lambda x: x[2] == "A", network.keys()))
    while True:
        # print(positions)
        for i in instructions.replace(" ", "").replace("\n", ""):
            end = True
            for p in range(len(positions)):
                positions[p] = step(positions[p], network, i)
                if positions[p][2] != "Z":
                    end = False
            count += 1
            if end:
                break
        if end:
            break
        
    print(count)

    
if __name__ == "__main__": 
    main()