import argparse
import sys
from sympy import sqrt
from collections import defaultdict
import colorama
import itertools as it
import os

sys.path.append(os.path.join(os.path.dirname(__file__), '..'))
from lib.point import Point

def find_antinodes(grid: list[list[str]]) -> set[Point]:
    height = len(grid)
    width = len(grid[0])
    antinodes = set()

    nodes_by_type: dict[set[Point]] = defaultdict(set)
    for y in range(height):
        for x in range(width):
            sym = grid[y][x]
            if sym == '.':
                pass
            else:
                nodes_by_type[sym].add(Point(y,x))

    for sym, nodes in nodes_by_type.items():
        for (n1, n2) in it.combinations(nodes, r=2):
            a1 = n1 + 2 * (n2 - n1)
            a2 = n2 + 2 * (n1 - n2)
            print(f'Symbol {sym} generates antinodes {a1=} and {a2=}')
            if 0 <= a1.y < height and 0 <= a1.x < width:
                antinodes.add(a1)
            if 0 <= a2.y < height and 0 <= a2.x < width:
                antinodes.add(a2)

    return antinodes

def main():
    grid = [list(line.strip()) for line in sys.stdin]
    antinodes = find_antinodes(grid)

    for y in range(len(grid)):
        row = grid[y]
        print(''.join(
            f'{colorama.Fore.RED}#{colorama.Style.RESET_ALL}' if Point(y,x) in antinodes
            else row[x]
            for x in range(len(row))
        ))

    print(len(antinodes))

if __name__ == '__main__':
    main()
