package sudoku;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;

public class PuzzleGenerator {
    private static int totalHoles = 49;
    
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
	
	while (holes < totalHoles) {
	    int x = new Random().nextInt(Puzzle.sideLength);
	    int y = new Random().nextInt(Puzzle.sideLength);

	    int value = puzzle.getCell(x, y).getValue();

	    if (value > 0) {
		int solutions = 1;

		// TODO: Prevent compute all solutions
		for (int i = 1; i <= Puzzle.sideLength && solutions == 1; ++i) {
		    if (i != value) {
			puzzle.setValue(x, y, i);
			
			Puzzle solution = new Solver().solve(puzzle);
			if (solution != null) {
			    ++solutions;
			}
		    }
		}

		if (solutions == 1) {
		    ++holes;
		    puzzle.setValue(x, y, 0);
		} else {
		    puzzle.setValue(x, y, value);
		}
	    }

	}
    }
}
