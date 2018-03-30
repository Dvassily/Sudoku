package sudoku.solver;

import java.util.Set;
import java.util.HashSet;

import sudoku.util.SolverHelper;
import sudoku.*;
import static sudoku.Strategy.hiddenSet;

public class HiddenSetProcessor {
    private PuzzleEvaluator puzzleEvaluator;
    
    public HiddenSetProcessor(PuzzleEvaluator puzzleEvaluator) {
	this.puzzleEvaluator = puzzleEvaluator;
    }

    public HiddenSetProcessor() {
	this(null);
    }

    public boolean process(Puzzle puzzle) {
	boolean found = false;
	
	// Looks for hidden sets in rows
	for (int i = 0; i < Puzzle.SIDE_LENGTH; ++i) {
	    found |= process(new HashSet<>(puzzle.findRow(i, true)));
	}

	// Looks for hidden sets in columns
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

		for (Cell cell : cells) {
		    if (! subset.contains(cell)) {
			for (int candidate : cell.getCandidates()) {
			    candidates.remove(candidate);
			}
		    }
		}

		if (candidates.size() == subset.size()) {
		    boolean removed = false;
		    
		    for (Cell cell : subset) {
			Set<Integer> toRemove = new HashSet<>();
			for (int candidate : cell.getCandidates()) {
			    if (! candidates.contains(candidate)) {
				toRemove.add(candidate);
			    }
			}

			removed |= cell.getCandidates().removeAll(toRemove);
		    }

		    if (removed) {
			if (puzzleEvaluator != null) {
			    puzzleEvaluator.incrementScore(hiddenSet(subset.size()));
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
