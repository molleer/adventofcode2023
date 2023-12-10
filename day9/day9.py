def diff(nums):
    res = []
    for i in range(1, len(nums)):
        res.append(nums[i] - nums[i - 1])
    return res


def prev_num(nums):
    if not any(nums):
        return 0
    diffs = diff(nums)
    return nums[0] - prev_num(diffs)


def next_num(nums):
    if not any(nums):
        return 0
    diffs = diff(nums)
    return next_num(diffs) + nums[-1]


def main():
    with open("input.txt", "r") as f:
        lines = f.readlines()
    lines = [
        [int(n) for n in line.replace("\n", "").split(" ")]
        for line in lines]
    sum = 0

    for line in lines:
        sum += prev_num(line)

    print(sum)


if __name__ == "__main__":
    main()
