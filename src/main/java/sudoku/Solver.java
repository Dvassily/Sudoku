package sudoku;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;
import java.util.Comparator;
import java.util.Collections;

public class Solver {
    private Puzzle puzzle;
    
    public Solver(Puzzle puzzle) {
	this.puzzle = puzzle;
    }
    
    // TODO: Use recursivity
    //public List<Puzzle> solve(boolean findAllSolutions) {
    public Puzzle solve() {
	if (! puzzle.check()) {
	    return null;
	}
	//List<Puzzle> solutions = new ArrayList<>();
	Puzzle solution = null;
	int x = 0;
	int y = 0;

	while (puzzle.getCell(x, y) > 0) {
	    ++x;
	    if (x == Puzzle.sideLength) {
		++y;
		x = 0;

		if (y == Puzzle.sideLength) {
		    return (Puzzle) puzzle.clone();
		}
	    }
	}
	
	List<Integer> candidates = findCandidates(puzzle, x, y);

	Collections.shuffle(candidates);
	for (int candidate : candidates) {
	    // solutions.addAll(fill(x, y, candidate, (Puzzle) puzzle.clone(), findAllSolutions));

	    // if (solutions.size() == 1 && ! findAllSolutions) {
	    // 	return solutions;
	    // }
	    solution = fill(x, y, candidate, (Puzzle) puzzle.clone());
	    if (solution != null) {
		return solution;
	    }
	}

	return null;
    }

    public Puzzle fill(int x, int y, int value, Puzzle puzzle) {
	puzzle.setValue(x, y, value);

	while (y < Puzzle.sideLength && puzzle.getCell(x, y) > 0) {
	    ++x;
	    if (x == Puzzle.sideLength) {
		++y;
		x = 0;
	    }
	}
	
	if (y == Puzzle.sideLength) {
	    return puzzle;
	    // solutions.add(puzzle);
	    // return solutions;
	}

	List<Integer> candidates = findCandidates(puzzle, x, y);

	Collections.shuffle(candidates);
	for (int candidate : candidates) {
	    // solutions.addAll(fill(x, y, candidate, (Puzzle) puzzle.clone(), findAllSolutions));
	    // if (solutions.size() == 1 && ! findAllSolutions) {
	    // 	return solutions;
	    // }
	    Puzzle solution = fill(x, y, candidate, (Puzzle) puzzle.clone());
	    if (solution != null) {
		return solution;
	    }
	}

	return null;
    }

    private List<Integer> findCandidates(Puzzle puzzle, int x, int y) {
	Set<Integer> candidates = new HashSet<Integer>();

	for (int i = 1; i <= Puzzle.sideLength; ++i) {
	    candidates.add(i);
	}

	int squareX = x / Puzzle.regionSize;
	int squareY = y / Puzzle.regionSize;

	for (int i = squareX * Puzzle.regionSize; i < squareX * Puzzle.regionSize + Puzzle.regionSize; ++i) {
	    for (int j = squareY * Puzzle.regionSize; j < squareY * Puzzle.regionSize + Puzzle.regionSize; ++j) {
		candidates.remove(puzzle.getCell(i, j));
	    }
	}

	for (int i = 0; i < Puzzle.sideLength; i++) {
	    candidates.remove(puzzle.getCell(i, y));
	}

	for (int i = 0; i < Puzzle.sideLength; i++) {
		candidates.remove(puzzle.getCell(x, i));
	}

	return new ArrayList<Integer>(candidates);
    }
}
