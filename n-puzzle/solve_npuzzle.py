from npuzzle import (
    Solution,
    State,
    Move,
    UP,
    DOWN,
    LEFT,
    RIGHT,
    create_goal,
    get_children,
    is_goal,
    is_solution,
    load_puzzle,
    to_string,
)
from node import Node
from typing import Literal, List
import argparse
import math
import time

BFS = "bfs"
DFS = "dfs"
ASTAR = "astar"

Algorithm = Literal["bfs", "dfs", "astar"]


def solve_bfs(open: List[Node]) -> Solution:
    """Solve the puzzle using the BFS algorithm"""
    dimension = int(math.sqrt(len(open[0].get_state())))
    moves = [UP, DOWN, LEFT, RIGHT]
    while open:
        node = open.pop(0)
        if is_goal(node.get_state()):
            return node.get_path()
        puzzle = node.get_state()
        k = node.cost
        # print('k = ', k)
        children = get_children(puzzle, moves, dimension)
        for child in children:
            n = Node(state=child[0], move=child[1], parent=node, cost=k + 1)
            open.append(n)
    return []


def solve_dfs(open: List[Node]) -> Solution:
    """Solve the puzzle using the DFS algorithm"""
    dimension = int(math.sqrt(len(open[0].get_state())))
    moves = [UP, DOWN, LEFT, RIGHT]
    closed: List[Node] = []
    while open:
        node = open.pop()
        if is_goal(node.get_state()):
            return node.get_path()
        puzzle = node.get_state()
        k = node.cost
        # print('k = ', k)
        children = get_children(puzzle, moves, dimension)
        for child in children:
            n = Node(state=child[0], move=child[1], parent=node, cost=k + 1)
            if n not in closed:
                open.insert(0, n)
    return []


def solve_astar(open: List[Node]) -> Solution:
    """Solve the puzzle using the A* algorithm"""
    dimension = int(math.sqrt(len(open[0].get_state())))
    moves = [UP, DOWN, LEFT, RIGHT]
    while open:
        node = open.pop(0)
        if is_goal(node.get_state()):
            return node.get_path()
        puzzle = node.get_state()
        k = node.cost
        # print('k = ', k)
        children = get_children(puzzle, moves, dimension)
        for child in children:
            n = Node(
                state=child[0],
                move=child[1],
                parent=node,
                cost=k + 1,
                heuristic=heuristic(node),
            )
            open.append(n)
            open.sort(key=lambda x: x.cost + x.heuristic)
    return []


def heuristic(node: Node) -> int:
    """Calculate the heuristic value of the puzzle"""
    # return 0
    return manhattan_distance(node)

def manhattan_distance(node: Node) -> int:
    """Calculate the Manhattan distance of the puzzle"""
    puzzle = node.get_state()
    dimension = int(math.sqrt(len(puzzle)))
    distance = 0
    for i in range(len(puzzle)):
        if puzzle[i] != 0:
            x = i // dimension
            y = i % dimension
            goal_index = puzzle[i]
            goal_x = goal_index // dimension
            goal_y = goal_index % dimension
            distance += abs(x - goal_x) + abs(y - goal_y)
    return distance


def main():
    parser = argparse.ArgumentParser(description="Load an n-puzzle and solve it.")
    parser.add_argument("filename", type=str, help="File name of the puzzle")
    parser.add_argument(
        "-a",
        "--algo",
        type=str,
        choices=["bfs", "dfs", "astar"],
        required=True,
        help="Algorithm to solve the puzzle",
    )
    parser.add_argument(
        "-v", "--verbose", action="store_true", help="Increase output verbosity"
    )

    args = parser.parse_args()

    puzzle = load_puzzle(args.filename)

    if args.verbose:
        print("Puzzle:\n")
        print(to_string(puzzle))

    if not is_goal(puzzle):

        root = Node(state=puzzle, move=None)
        open = [root]

        if args.algo == BFS:
            print("BFS\n")
            start_time = time.time()
            solution = solve_bfs(open)
            duration = time.time() - start_time
            if solution:
                print("Solution:", solution)
                print("Valid solution:", is_solution(puzzle, solution))
                print("Duration:", duration)
            else:
                print("No solution")
        elif args.algo == DFS:
            print("DFS")
            start_time = time.time()
            solution = solve_dfs(open)
            duration = time.time() - start_time
            if solution:
                print("Solution:", solution)
                print("Valid solution:", is_solution(puzzle, solution))
                print("Duration:", duration)
            else:
                print("No solution")
        elif args.algo == ASTAR:
            print("A*")
            start_time = time.time()
            solution = solve_astar(open)
            duration = time.time() - start_time
            if solution:
                print("Solution:", solution)
                print("Valid solution:", is_solution(puzzle, solution))
                print("Duration:", duration)
        else:
            print("Puzzle is already solved")


if __name__ == "__main__":
    main()
