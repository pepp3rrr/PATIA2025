# N-Puzzle Solver and Benchmarking

This project implements three search algorithms for solving the N-Puzzle (taquin):

- **DFS** (Depth-First Search)
- **BFS** (Breadth-First Search)
- **A\*** (A-star Search) using Manhattan distance as the heuristic.

## Benchmarking

A benchmarking script is included that:
- Automatically generates random taquin puzzles of increasing size from **3x3 up to 100x100**, in steps of 3.
- Measures the average solving time of each algorithm and plots to a png
- The graph we obtained is `benchmark_results.png`

### Multithreading

Two versions of the benchmark are available:
- **Multithreaded** `benchmark.py` (faster but may not work in VM)
- **Single-threaded** `benchmark_nomultithread.py`

> **Note:** VM provided for testing **does not have** `matplotlib` installed globally, which is **required** for plotting results. You may need to install it to be able to run the benchmark locally.
> Also you **must** be in this repository directly to be able to run anything!

## Timeout Setting

The variable `SOLVE_TIMEOUT` in the benchmark script defines a 60-timeout per puzzle solve.

> **Note:** Since this value is machine-dependent, if you see VM struggles with solving large puzzles (especially BFS or DFS for sizes above 12x12), increase the timeout by setting `SOLVE_TIMEOUT` in `benchmark.py` or `benchmark_nomultithread.py` to a larger value manually.
