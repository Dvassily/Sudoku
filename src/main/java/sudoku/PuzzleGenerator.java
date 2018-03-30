package sudoku;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;
import java.util.Collections;

import sudoku.solver.Solver;

public class PuzzleGenerator {
    public Puzzle generate(int givens) {
	Puzzle puzzle = generateTerminalState();
	
	digHoles(puzzle, Puzzle.NUMBER_OF_CELLS - givens);
	
	return puzzle;
    }

    public Puzzle generateTerminalState() {
	Puzzle puzzle = new Puzzle();
	
	return new Solver().solve(puzzle, false).get(0);
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

	while (holes < numberOfHoles) {
	    Cell cell = cells.get(0);
	    int value = cell.getValue();
	    cells.remove(0);
	    cell.setValue(0);
	    
	    List<Puzzle> solutions = new Solver().solve(puzzle, true);
	    if (solutions.size() > 1) {
		cell.setValue(value);
	    } else {
		++holes;
	    }
	}
    }

    // public boolean checkUniqueSolution(Puzzle puzzle, Cell cell) {
    // 	int value = cell.getValue();

    // 	int solutions = 1;
    // 	int i;
    // 	for (i = 1; i <= Puzzle.SIDE_LENGTH && solutions == 1; ++i) {
    // 	    if (i != value) {
    // 		cell.setValue(i);
			
    // 		Puzzle solution = new Solver().solve(puzzle).;
    // 		if (solution != null) {
    // 		    ++solutions;
    // 		}
    // 	    }
    // 	}
	
    // 	return (solutions == 1);
    // }
}
