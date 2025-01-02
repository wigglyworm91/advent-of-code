import sys
import typing

def listify(g):
    def newG(*args):
        return list(g(*args))
    return newG

@listify
def expand(diskmap: list[int]) -> list[int]:
    for val, count in enumerate(diskmap):
        if val % 2 == 0:
            yield from [val//2] * count
        else:
            yield from [None] * count

T = typing.TypeVar('T')
def compact(blocks: list[T]):
    blocks = blocks[:]
    def find_space(n: int, upper: int = len(blocks)) -> int:
        for i in range(upper - n):
            if all(b is None for b in blocks[i : i+n]):
                return i
        return None

    def find_block(b: T) -> int:
        return blocks.index(b)

    def get_block_size(i: int) -> int:
        j = 0
        b = blocks[i]
        while i+j < len(blocks) and blocks[i+j] == b:
            j += 1
        return j

    uniq_blocks = sorted(set(blocks) - set([None]))
    for b in reversed(uniq_blocks):
        orig_idx = find_block(b)
        orig_len = get_block_size(orig_idx)
        new_idx = find_space(orig_len, orig_idx)
        print(f'Attempting to move block {b}: {orig_idx=}, {orig_len=}, {new_idx=}')
        if new_idx is not None and new_idx < orig_idx:
            #print(f'Moving block {b} from {orig_idx=} to {new_idx=}')
            for i in range(orig_len):
                blocks[orig_idx + i] = None
                blocks[new_idx + i] = b
            #print(blocks)

    return blocks

def checksum(blocks: list[T]) -> T:
    return sum((i * b if b is not None else 0) for (i,b) in enumerate(blocks))

def main():
    diskmap = list(map(int, sys.stdin.readline().strip()))
    #print(diskmap)
    x = diskmap
    x = expand(x)
    #print(x)
    x = compact(x)
    #print(x)
    x = checksum(x)
    print(x)

if __name__ == '__main__':
    main()
