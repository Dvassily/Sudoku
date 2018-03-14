package sudoku;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;

import sudoku.solver.Solver;

public class PuzzleGenerator {
    public Puzzle generate() {
	Puzzle puzzle = generateTerminalState();
	
	digHoles(puzzle);
	
	return puzzle;
    }

    public Puzzle generateTerminalState() {
	Puzzle puzzle = new Puzzle();
	
	return new Solver().solve(puzzle);
    }

    public void digHoles(Puzzle puzzle) {
	int holes = 0;

	for (int i = 0; i < Puzzle.sideLength; ++i) {
	    for (int j = 0; j < Puzzle.sideLength; ++j) {
		Cell cell = puzzle.getCell(j, i);

		if (checkUniqueSolution(puzzle, cell)) {
		    cell.setValue(0);
		} else {
		    cell.setValue(cell.getValue());
		}
	    }
	}
    }

    public boolean checkUniqueSolution(Puzzle puzzle, Cell cell) {
	int value = cell.getValue();

	int solutions = 1;
	int i;
	for (i = 1; i <= Puzzle.sideLength && solutions == 1; ++i) {
	    System.out.println(i);

	    if (i != value) {
		cell.setValue(i);
			
		Puzzle solution = new Solver().solve(puzzle);
		if (solution != null) {
		    ++solutions;
		}
	    }
	}
	System.out.println(i);

	return (solutions == 1);
    }
}
