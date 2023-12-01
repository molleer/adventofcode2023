def to_digits(line: str):
    digits = [
        "one",
        "two",
        "three",
        "four",
        "five",
        "six",
        "seven",
        "eight",
        "nine",
    ]
    ans = ""
    for i in range(len(line)):
        if line[i].isdigit():
            ans += line[i]
            continue
        for d in range(9):
            if line[i:].startswith(digits[d]):
                ans += str(d + 1)

    return ans


def main():
    with open("input.txt", "r") as f:
        lines = f.readlines()

    sum = 0
    for line in lines:
        digits = to_digits(line)
        sum += int(digits[0]) * 10 + int(digits[-1])

    print(sum)


if __name__ == "__main__":
    main()
