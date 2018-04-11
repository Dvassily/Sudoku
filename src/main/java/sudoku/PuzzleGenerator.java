package sudoku;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;
import java.util.Collections;

import sudoku.solver.BacktrackingSolver;

public class PuzzleGenerator {
    public Puzzle generate(int givens) {
	Puzzle puzzle = generateTerminalState();
	
	digHoles(puzzle, Puzzle.NUMBER_OF_CELLS - givens);
	
	return puzzle;
    }

    public Puzzle generateTerminalState() {
	Puzzle puzzle = new Puzzle();
	
	return new BacktrackingSolver().solve(puzzle, false).get(0);
    }

    public void digHoles(Puzzle puzzle, int numberOfHoles) {
	int holes = 0;
	List<Cell> cells = new ArrayList<>();
	
	for (int i = 0; i < Puzzle.SIDE_LENGTH; ++i) {
	    for (int j = 0; j < Puzzle.SIDE_LENGTH; ++j) {
		cells.add(puzzle.getCell(j, i));
	    }
	}

	Collections.shuffle(cells);

	while (! cells.isEmpty() && holes < numberOfHoles) {
	    Cell cell = cells.get(0);
	    int value = cell.getValue();
	    cells.remove(0);
	    cell.setValue(0);
	    
	    List<Puzzle> solutions = new BacktrackingSolver().solve(puzzle, true);
	    if (solutions.size() > 1) {
		cell.setValue(value);
	    } else {
		++holes;
	    }
	}
    }
}
