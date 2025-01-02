from sympy import sqrt

class Point:
    def __init__(self, y: int, x: int):
        self.y = y
        self.x = x

    def __add__(self, p: 'Point') -> 'Point':
        return Point(self.y + p.y, self.x + p.x)

    def __sub__(self, p: 'Point') -> 'Point':
        return Point(self.y - p.y, self.x - p.x)

    def __mul__(self, n: int) -> 'Point':
        return Point(self.y * n, self.x * n)

    def __rmul__(self, n: int) -> 'Point':
        return self * n

    def __str__(self) -> str:
        return f'({self.y}, {self.x})'

    def __repr__(self) -> str:
        return f'Point(y={self.y}, x={self.x})'

    def __eq__(self, p: 'Point') -> bool:
        return (self.y, self.x) == (p.y, p.x)

    def distance(self, p: 'Point') -> float:
        return sqrt( (self.y - p.y)**2, (self.x - p.x)**2 )

    # methods to let me put this into a set and stuff

    def __lt__(self, p: 'Point') -> bool:
        return (self.y, self.x) < (p.y, p.x)

    def __hash__(self):
        return hash((self.y, self.x))


if __name__ == '__main__':
    pass
