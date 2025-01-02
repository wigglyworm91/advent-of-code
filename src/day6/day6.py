# that's right it's python time

import enum
import sys
import time
import copy
from collections import namedtuple
from colorama import init as colorama_init, Fore, Back, Style

colorama_init(autoreset=True)

class Square:
    sym: str
    count: int
    def __init__(self, sym: str, count: int = 0):
        self.sym = sym
        self.count = count
    def __str__(self):
        if self.sym == '#': return '#'
        elif self.sym == 'O': return f'{Style.BRIGHT}{Back.WHITE}O{Style.RESET_ALL}'
        elif self.count == 0:
            return f'{Style.DIM}0{Style.RESET_ALL}'
        else:
            return f'{self.count}'

class Direction:
    UP = (-1, 0)
    DOWN = (1, 0)
    LEFT = (0, -1)
    RIGHT = (0, 1)

class Maze:
    grid: list[list[list[str, int]]]
    pos: tuple[int, int]
    vel: tuple[int, int]

    def __init__(self, grid: list[list[str]]):
        self.height = len(grid)
        self.width = len(grid[0])
        self.grid = {
            (y, x): Square(grid[y][x])
            for y in range(self.height)
            for x in range(self.width)
        }
        self.pos = self.find_carat(grid)
        self.vel = self.carat2direc(self.grid[self.pos].sym)
        self.grid[self.pos] = Square('.', 1)

    def step(self) -> bool:
        y, x = self.pos
        dy, dx = self.vel
        npos = (y + dy, x + dx)
        if not self.isvalid(npos):
            return False
        if self.grid[npos].sym == '#':
            self.turnright()
            return self.step()

        self.pos = npos
        self.grid[self.pos].count += 1
        return True

    def isloop(self) -> bool:
        hare = copy.deepcopy(self)
        tort = copy.deepcopy(self)
        while True:
            if not hare.step():
                return False
            if not hare.step():
                return False
            if not tort.step():
                return False
            if hare.pos == tort.pos and hare.vel == tort.vel:
                return True

    def isvalid(self, pos) -> bool:
        return pos in self.grid

    @staticmethod
    def find_carat(grid) -> (int, int):
        for y in range(len(grid)):
            row = grid[y]
            for x in range(len(row)):
                if row[x] in '><^v':
                    return y, x
        raise ValueError(f'Could not find carat')

    def countX(self):
        return sum(sq.count > 0 for sq in self.grid.values())

    def turnright(self):
        self.vel = self.rotate(self.vel)

    @staticmethod
    def rotate(vel):
        match vel:
            case Direction.RIGHT: return Direction.DOWN
            case Direction.DOWN:  return Direction.LEFT
            case Direction.LEFT:  return Direction.UP
            case Direction.UP:    return Direction.RIGHT

    @staticmethod
    def carat2direc(carat: str) -> (int, int):
        match carat:
            case '>': return Direction.RIGHT
            case '<': return Direction.LEFT
            case '^': return Direction.UP
            case 'v': return Direction.DOWN
            case _:
                raise ValueError()

    def __str__(self):
        return '\n'.join(
            ''.join(
                f'{Back.YELLOW}*{Style.RESET_ALL}' if (y,x) == self.pos else str(self.grid[y,x])
                for x in range(self.width)
            )
            for y in range(self.height)
        )

m = Maze([list(l.strip()) for l in sys.stdin.readlines()])
m2 = copy.deepcopy(m)

while m.step():
    pass
    #print(m)
    #print()
    #time.sleep(0.05)

print(f'Testing {sum(m.grid[pos].count > 0 for pos in m.grid)} potential loop positions')
num_potential = 0

for pos in m.grid:
    # we only care about positions that the car actually traveled through
    if m.grid[pos].count == 0: continue
    
    # we can't put it at the starting position
    if pos == m2.pos: continue

    m2.grid[pos].sym = '#'
    if m2.isloop():
        print(f'Found looper at {pos}')
        m2.grid[pos].sym = 'O'
        #print(m2)
        num_potential += 1
    m2.grid[pos].sym = '.'

print(num_potential)
