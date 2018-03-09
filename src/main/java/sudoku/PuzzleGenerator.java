package sudoku;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;

public class PuzzleGenerator {
    public Puzzle generate(double holesProportion) {
	Puzzle puzzle = generateTerminalState();

	digHoles(puzzle, holesProportion);
	
	return puzzle;
    }

    public Puzzle generateTerminalState() {
	Puzzle puzzle = new Puzzle();
	
	return new Solver().solve(puzzle);
    }

    public void digHoles(Puzzle puzzle, double holesProportion) {
	int holes = 0;

	for (int i = 0; i < Puzzle.sideLength; ++i) {
	    for (int j = 0; j < Puzzle.sideLength; ++j) {
		if (new Random().nextDouble() < holesProportion) {
		    Cell cell = puzzle.getCell(j, i);

		    if (checkUniqueSolution(puzzle, cell)) {
			cell.setValue(0);
		    } else {
			cell.setValue(cell.getValue());
		    }
		}
	    }
	}
    }

    public boolean checkUniqueSolution(Puzzle puzzle, Cell cell) {
	int value = cell.getValue();

	int solutions = 1;
	for (int i = 1; i <= Puzzle.sideLength && solutions == 1; ++i) {
	    if (i != value) {
		cell.setValue(i);
			
		Puzzle solution = new Solver().solve(puzzle);
		if (solution != null) {
		    ++solutions;
		}
	    }
	}

	return (solutions == 1);
    }
}
