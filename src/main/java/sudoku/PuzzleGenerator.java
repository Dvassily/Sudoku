package sudoku;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;

public class PuzzleGenerator {
    public Puzzle generate() {
	Puzzle puzzle = generateTerminalState();
	digHoles(puzzle);
	
	return puzzle;
    }

    public Puzzle generateTerminalState() {
	Puzzle puzzle = new Puzzle();
	
	return new Solver(puzzle).solve();
    }

    public void digHoles(Puzzle puzzle) {
	int holes = 0;

	while (holes < 54) {
	    int x = new Random().nextInt(Puzzle.sideLength);
	    int y = new Random().nextInt(Puzzle.sideLength);

	    int value = puzzle.getCell(x, y);
	    
	    if (value > 0) {
		int solutions = 1;
		
		for (int i = 1; i <= Puzzle.sideLength; ++i) {
		    if (i != value) {
			puzzle.setValue(x, y, i);
			Puzzle solution = new Solver(puzzle).solve();
			if (solution != null) {
			    System.out.println(solution);

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
