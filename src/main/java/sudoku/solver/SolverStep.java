package sudoku.solver;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import sudoku.*;

public class SolverStep {
    private Strategy strategy;
    private Map<Cell, Set<Integer>> removals = new HashMap<>();

    public SolverStep(Strategy strategy) {
	this.strategy = strategy;
    }

    public Strategy getStrategy() {
	return strategy;
    }

    public Map<Cell, Set<Integer>> getRemovals() {
	return removals;
    }

    public void removeCandidate(Cell cell, int candidate) {
	if (! removals.containsKey(cell)) {
	    removals.put(cell, new HashSet<>());
	}

	removals.get(cell).add(candidate);
    }
}
