package sudoku.solver;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;
import java.util.Comparator;
import java.util.Collections;

import sudoku.*;

public class Solver  {
    public Puzzle solve(Puzzle puzzle) {
	if (! new ConstraintChecker().check(puzzle)) {
	    return null;
	}

	//List<Puzzle> solutions = new ArrayList<>();
	Puzzle solution = null;
	int x = 0;
	int y = 0;

	while (puzzle.getCell(x, y).isFilled()) {
	    ++x;
	    if (x == Puzzle.sideLength) {
		++y;
		x = 0;

		if (y == Puzzle.sideLength) {
		    return (Puzzle) puzzle.clone();
		}
	    }
	}

	List<Integer> candidates = new ArrayList<>(puzzle.findCandidates(x, y));

	Collections.shuffle(candidates);
	for (int candidate : candidates) {
	    solution = fill(x, y, candidate, (Puzzle) puzzle.clone());
	    if (solution != null) {
		return solution;
	    }

	}

	return null;
    }
    
    public Puzzle fill(int x, int y, int value, Puzzle puzzle) {
	puzzle.setValue(x, y, value);
	
	while (y < Puzzle.sideLength && puzzle.getCell(x, y).isFilled()) {
	    ++x;
	    if (x == Puzzle.sideLength) {
		++y;
		x = 0;
	    }
	}
	
	if (y == Puzzle.sideLength) {
	    return puzzle;
	}

	List<Integer> candidates = new ArrayList<>(puzzle.findCandidates(x, y));

	Collections.shuffle(candidates);
	for (int candidate : candidates) {
	    Puzzle solution = fill(x, y, candidate, (Puzzle) puzzle.clone());
	    if (solution != null) {
		return solution;
	    }
	}

	return null;
    }

    private void setAndUpdateCandidates(Puzzle puzzle, int x, int y, int value) {
	puzzle.setValue(x, y, value);

	for (Cell cell : puzzle.findCellsInRegion(x, y)) {
	    cell.getCandidates().remove(value);
	}
    }
}
