package sudoku.solver;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;
import java.util.Comparator;
import java.util.Collections;

import sudoku.*;

public class BacktrackingSolver  {
    public List<Puzzle> solve(Puzzle puzzle, boolean findAll) {
	List<Puzzle> results = new ArrayList<>();
	
	if (! new ConstraintChecker().check(puzzle)) {
	    return results;
	}

	int x = 0;
	int y = 0;

	while (puzzle.getCell(x, y).isFilled()) {
	    ++x;
	    if (x == Puzzle.SIDE_LENGTH) {
		++y;
		x = 0;

		if (y == Puzzle.SIDE_LENGTH) {
		    results.add((Puzzle) puzzle.clone());
		    return results;
		}
	    }
	}

	List<Integer> candidates = new ArrayList<>(puzzle.findCandidates(x, y));

	Collections.shuffle(candidates);
	for (int candidate : candidates) {
	    results.addAll(fill(x, y, candidate, (Puzzle) puzzle.clone(), findAll));
	    
	    if (! findAll && results.size() == 1) {
		return results;
	    }
	}

	return results;
    }
    
    public List<Puzzle> fill(int x, int y, int value, Puzzle puzzle, boolean findAll) {
	List<Puzzle> results = new ArrayList<>();
	puzzle.setValue(x, y, value);
	
	while (y < Puzzle.SIDE_LENGTH && puzzle.getCell(x, y).isFilled()) {
	    ++x;
	    if (x == Puzzle.SIDE_LENGTH) {
		++y;
		x = 0;
	    }
	}
	
	if (y == Puzzle.SIDE_LENGTH) {
	    results.add(puzzle);
	    return results;
	}

	List<Integer> candidates = new ArrayList<>(puzzle.findCandidates(x, y));

	Collections.shuffle(candidates);
	for (int candidate : candidates) {
	    List<Puzzle> solutions = fill(x, y, candidate, (Puzzle) puzzle.clone(), findAll);
	    results.addAll(solutions);
	    
	    if (! findAll && results.size() == 1) {
		return results;
	    }
	}

	return results;
    }
}
