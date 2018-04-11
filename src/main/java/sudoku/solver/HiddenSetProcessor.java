package sudoku.solver;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import sudoku.util.SolverHelper;
import sudoku.*;
import static sudoku.Strategy.hiddenSet;

public class HiddenSetProcessor {
    public List<SolverStep> process(Puzzle puzzle) {
	List<SolverStep> steps = new ArrayList<>();
	
	// Looks for hidden sets in rows
	for (int i = 0; i < Puzzle.SIDE_LENGTH; ++i) {
	    steps.addAll(process(new HashSet<>(puzzle.findRow(i, true))));
	}

	// Looks for hidden sets in columns
	for (int i = 0; i < Puzzle.SIDE_LENGTH; ++i) {
	    steps.addAll(process(new HashSet<>(puzzle.findColumn(i, true))));
	}

	for (int squareX = 0; squareX < 3; ++squareX) {
	    for (int squareY = 0; squareY < 3; ++squareY) {
		steps.addAll(process(new HashSet<>(puzzle.findSquare(squareX, squareY, true))));
	    }
	}

	return steps;
    }

    private List<SolverStep> process(Set<Cell> cells) {
	List<SolverStep> steps = new ArrayList<>();
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
		    SolverStep step = new SolverStep(hiddenSet(subset.size()));
		    
		    for (Cell cell : subset) {
			for (int candidate : cell.getCandidates()) {
			    if (! candidates.contains(candidate) && cell.getCandidates().contains(candidate)) {
				step.removeCandidate(cell, candidate);
			    }
			}
		    }

		    if (step.getRemovals().size() > 0) {
			steps.add(step);
		    }
		}

		candidates.clear();
	    }
	}

	return steps;
    }
}
