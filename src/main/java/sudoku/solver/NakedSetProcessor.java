package sudoku.solver;

import java.util.Set;
import java.util.HashSet;

import sudoku.util.SolverHelper;
import sudoku.*;
import static sudoku.Strategy.nakedSet;

public class NakedSetProcessor {
    private PuzzleEvaluator puzzleEvaluator;
    
    public NakedSetProcessor(PuzzleEvaluator puzzleEvaluator) {
	this.puzzleEvaluator = puzzleEvaluator;
    }

    public NakedSetProcessor() {
	this(null);
    }
    
    public boolean process(Puzzle puzzle) {
	boolean found = false;
	
	// Looks for naked sets in rows
	for (int i = 0; i < Puzzle.SIDE_LENGTH; ++i) {
	    found |= process(new HashSet<>(puzzle.findRow(i, true)));
	}

	// Looks for naked sets in columns
	for (int i = 0; i < Puzzle.SIDE_LENGTH; ++i) {
	    found |= process(new HashSet<>(puzzle.findColumn(i, true)));
	}

	for (int squareX = 0; squareX < 3; ++squareX) {
	    for (int squareY = 0; squareY < 3; ++squareY) {
		found |= process(new HashSet<>(puzzle.findSquare(squareX, squareY, true)));
	    }
	}

	return found;
    }

    private boolean process(Set<Cell> cells) {
	boolean found = false;
	
	Set<Set<Cell>> subsets = SolverHelper.powerSet(cells);

	for (Set<Cell> subset : subsets) {
	    if (subset.size() > 1 && subset.size() <= 4 && subset.size() < cells.size()) {
		Set<Integer> candidates = new HashSet<>();
		for (Cell cell : subset) {
		    candidates.addAll(cell.getCandidates());
		}

		if (subset.size() == candidates.size()) {
		    boolean removed = false;
		    for (Cell cell : cells) {
			if (! subset.contains(cell)) {
			    for (int candidate : candidates) {
				removed |= cell.removeFromCandidates(candidate);
			    }
			}
		    }

		    if (removed) {
			if (puzzleEvaluator != null) {
			    puzzleEvaluator.incrementScore(nakedSet(subset.size()));
			}

			found = true;
		    }
		}

		candidates.clear();
	    }
	}

	return found;
    }

}
