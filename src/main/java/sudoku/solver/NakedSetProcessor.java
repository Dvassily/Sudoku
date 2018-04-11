package sudoku.solver;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import sudoku.util.SolverHelper;
import sudoku.*;
import static sudoku.Strategy.nakedSet;

public class NakedSetProcessor {
    // TODO: Remove
    private PuzzleEvaluator puzzleEvaluator;
    
    public NakedSetProcessor(PuzzleEvaluator puzzleEvaluator) {
	this.puzzleEvaluator = puzzleEvaluator;
    }

    public NakedSetProcessor() {
	this(null);
    }
    
    public List<SolverStep> process(Puzzle puzzle) {
	List<SolverStep> steps = new ArrayList<>();
	
	// Looks for naked sets in rows
	for (int i = 0; i < Puzzle.SIDE_LENGTH; ++i) {
	    steps.addAll(process(new HashSet<>(puzzle.findRow(i, true))));
	}

	// Looks for naked sets in columns
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

		if (subset.size() == candidates.size()) {
		    SolverStep step = new SolverStep(nakedSet(subset.size()));

		    for (Cell cell : cells) {
			if (! subset.contains(cell)) {
			    for (int candidate : candidates) {
				if (cell.getCandidates().contains(candidate)) {
				    step.removeCandidate(cell, candidate);
				}
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
