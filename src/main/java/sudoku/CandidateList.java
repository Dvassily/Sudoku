package sudoku;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class CandidateList {
    private List<List<Set<Integer>>> candidates;

    public CandidateList(Puzzle puzzle) {
	initCandidates(puzzle);
    }

    private void initCandidates(Puzzle puzzle) {
	candidates = new ArrayList<List<Set<Integer>>>();
	
	for (int i = 0; i < Puzzle.sideLength; ++i) {
	    candidates.add(new ArrayList<Set<Integer>>());
	    for (int j = 0; j < Puzzle.sideLength; ++j) {
		candidates.get(i).add(new HashSet<Integer>());
		if (puzzle.getCell(j, i) == 0) {
		    candidates.get(i).get(j).addAll(Solver.findCandidates(puzzle, j, i));
		}
	    }
	}
    }

    public void update(Puzzle puzzle, int x, int y, int value) {
	candidates.get(y).get(x).clear();
	
	int squareX = x / Puzzle.regionSize;
	int squareY = y / Puzzle.regionSize;

	for (int i = squareY * Puzzle.regionSize; i < squareY * Puzzle.regionSize + Puzzle.regionSize; ++i) {
	    for (int j = squareX * Puzzle.regionSize; j < squareX * Puzzle.regionSize + Puzzle.regionSize; ++j) {
		removeCandidate(i, j, value);
	    }
	}

	for (int i = 0; i < Puzzle.sideLength; i++) {
	    removeCandidate(i, y, value);
	}

	for (int i = 0; i < Puzzle.sideLength; i++) {
	    removeCandidate(x, i, value);
	}
    }

    public Set<Integer> getCandidates(int x, int y) {
	return candidates.get(y).get(x);
    }

    public void removeCandidate(int x, int y, int candidate) {
	candidates.get(y).get(x).remove(candidate);
    }

    public String toString() {
	String result = "";

	for (int i = 0; i < Puzzle.sideLength; ++i) {
	    for (int j = 0; j < Puzzle.sideLength; ++j) {
		result += candidates.get(i).get(j) + " ";
	    }
	    
	    result += "\n";
	}

	return result;
    }

}
