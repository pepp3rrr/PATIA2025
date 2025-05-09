import os
import time
import tempfile
import subprocess
import matplotlib.pyplot as plt
from concurrent.futures import ThreadPoolExecutor, as_completed

PUZZLE_SIZE_RANGE = range(3, 20, 4)
SOLVE_TIMEOUT = 60
NUM_SAMPLES = 2
MAX_LENGTH = 20
METHODS = ["bfs", "dfs", "astar"]

def bench(puzzle_size, max_length, num_puzzles, method):
    """
    Benchmarks the specified method on a given puzzle size and maximum length.
    Args:
        puzzle_size (int): Size of the puzzle (e.g., 3 for a 3x3 puzzle).
        max_length (int): Maximum length of the move sequence. Will generate puzzles of lengths 1 to max_length.
        num_puzzles (int): Number of puzzles to generate.
        method (str): Method to benchmark (e.g., "bfs", "dfs", "astar").
    """
    samples = []

    with tempfile.TemporaryDirectory(delete=True) as temp_dir:
        
        # Run the command with the temporary file as an argument
        command = [
            'python3', 'generate_npuzzle.py',
            '-s', f'{puzzle_size}',
            '-ml', f'{max_length}',
            '-n', f'{num_puzzles}',
            temp_dir,
        ]
        print(f"Generating {num_puzzles} puzzles of size {puzzle_size} with max length {max_length}...")
        result = subprocess.run(command, capture_output=True, text=True)
        if result.returncode != 0:
            print(f"Error generating puzzles: {result.stderr}")
            return
        print(f"Generated puzzles in {temp_dir}")

        for filename in os.listdir(temp_dir):
            filepath = os.path.join(temp_dir, filename)
            if os.path.isfile(filepath):
                # Run the command with the temporary file as an argument
                command = [
                    'python3', 'solve_npuzzle.py',
                    '-a', method,
                    filepath,
                ]

                """
                # Measure the time taken to run the command
                """
                time_start = time.perf_counter()
                # print(f"Running command: {' '.join(command)}")
                result = subprocess.run(command, capture_output=True, text=True, timeout=SOLVE_TIMEOUT)
                if result.returncode != 0:
                    print(f"Error solving puzzle: {result.stderr}")
                    continue
                time_end = time.perf_counter()
                duration = time_end - time_start

                samples.append(duration)
    
    # Calculate the average time taken
    if len(samples) == 0:
        raise ValueError("No samples collected.")
    
    average_time = sum(samples) / len(samples)
    print(f"Average time on {method} for {num_puzzles} puzzles of size {puzzle_size} with max length {max_length}: {average_time:.4f} seconds")
    return average_time
  
if __name__ == "__main__":
    results = {method: [None] * len(PUZZLE_SIZE_RANGE) for method in METHODS}
    
    def run_benchmark(index, puzzle_size, method):
        print(f"Running benchmarks for {method} on {puzzle_size}x{puzzle_size} puzzles...")
        return index, method, bench(puzzle_size, MAX_LENGTH, NUM_SAMPLES, method)

    with ThreadPoolExecutor() as executor:
        futures = [
            executor.submit(run_benchmark, index, puzzle_size, method)
            for index, puzzle_size in enumerate(PUZZLE_SIZE_RANGE)
            for method in METHODS
        ]
        
        for future in as_completed(futures):
            try:
                index, method, average_time = future.result()
                results[method][index] = average_time
            except Exception as e:
                print(f"Error during benchmarking: {e}")
    
    # Plotting the results
    plt.figure(figsize=(12, 8))
    for method in METHODS:
        plt.plot(PUZZLE_SIZE_RANGE, results[method], label=method)
    
    plt.xlabel('Puzzle Size')
    plt.ylabel('Average Time (seconds)')
    plt.title('Benchmarking N-Puzzle Solvers')
    plt.legend()
    plt.grid()
    plt.savefig('benchmark_results.png')
    plt.show()
