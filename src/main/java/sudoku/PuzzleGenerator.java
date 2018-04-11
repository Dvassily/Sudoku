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
	
	return new BacktrackingSolver().solve(puzzle);
    }

    public void digHoles(Puzzle puzzle, int numberOfHoles) {
	int holes = 0;
	List<Cell> cells = new ArrayList<>();
	
	for (int i = 0; i < Puzzle.sideLength; ++i) {
	    for (int j = 0; j < Puzzle.sideLength; ++j) {
		cells.add(puzzle.getCell(j, i));
	    }
	}

	Collections.shuffle(cells);

	while (holes < numberOfHoles) {
	    Cell cell = cells.get(0);
	    cells.remove(0);
	    
	    if (checkUniqueSolution(puzzle, cell)) {
		cell.setValue(0);
		++holes;
	    } else {
		cell.setValue(cell.getValue());
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
			
		Puzzle solution = new BacktrackingSolver().solve(puzzle);
		if (solution != null) {
		    ++solutions;
		}
	    }
	}
	System.out.println(i);

	return (solutions == 1);
    }
}
