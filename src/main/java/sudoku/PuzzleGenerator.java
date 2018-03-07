package sudoku;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;

public class PuzzleGenerator {
    public Puzzle generate(int hints) {
	Puzzle puzzle = generateTerminalState();

	digHoles(puzzle, Puzzle.NUMBER_OF_CELLS - hints);
	
	return puzzle;
    }

    public Puzzle generateTerminalState() {
	Puzzle puzzle = new Puzzle();
	
	return new Solver().solve(puzzle);
    }

    public void digHoles(Puzzle puzzle, int numberOfHoles) {
	int holes = 0;
	Set<Cell> tried = new HashSet<>();
	
	while (holes < numberOfHoles) {
	    Cell cell = puzzle.getCell(new Random().nextInt(Puzzle.sideLength), new Random().nextInt(Puzzle.sideLength));
	    
	    while (tried.contains(cell)) {
		cell = puzzle.getCell(new Random().nextInt(Puzzle.sideLength), new Random().nextInt(Puzzle.sideLength));
	    }


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

	    if (solutions == 1) {
		++holes;
		cell.setValue(0);
	    } else {
		cell.setValue(value);
	    }

	    tried.add(cell);
	}
    }
}
