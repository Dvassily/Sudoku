package sudoku.solver;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;

import sudoku.*;
import static sudoku.util.SolverHelper.removeCandidates;
import static sudoku.Strategy.*;

public class Solver {
    private List<IStrategySolver> solvers;
    
    private Solver(List<IStrategySolver> solvers) {
	this.solvers = solvers;
    }

    public int solve(Puzzle puzzle) {
	int score = 0;
	boolean updated;
	List<SolverStep> steps = null;

	puzzle.updateCandidates();
	score += processSingleCandidates(puzzle) * SINGLE_CANDIDATE.getScore();
	
	if (puzzle.isCompleted()) {
	    return score;
	}
	
	do {
	    updated = false;
	    
	    for (IStrategySolver solver : solvers) {
		int size = 0;
		
		do {
		    steps = solver.solve(puzzle);

		    if (steps.size() > 0) {
			updated = true;
		    }

		    for (SolverStep step : steps) {
			step.apply();
			
			score += (step.getStrategy().getScore());
		    }

		    if (steps.size() > 0) {
			score += processSingleCandidates(puzzle) * SINGLE_CANDIDATE.getScore();
		    }
		} while (steps.size() > 0);
	    }
	} while (updated);

	return score;
    }

    public int processSingleCandidates(Puzzle puzzle) {
	int total = 0;
	int found;
	
	do {
	    found = 0;
	    
	    for (int i = 0; i < Puzzle.SIDE_LENGTH; ++i) {
		for (int j = 0; j < Puzzle.SIDE_LENGTH; ++j) {
		    Set<Integer> cellCandidates = puzzle.getCell(j, i).getCandidates();
		
		    if (cellCandidates.size() == 1) {
			int value = cellCandidates.iterator().next();
			puzzle.setValue(j, i, value);
			removeCandidates(puzzle, j, i, value);
			++found;
		    }
		}
	    }

	    total += found;
	} while (found != 0);
	
	return total;
    }

    
    public static class SolverBuilder {
	private List<IStrategySolver> solvers = new ArrayList<>();
	
	public SolverBuilder addSolver(IStrategySolver solver) {
	    solvers.add(solver);
	    return this;
	}

	public Solver build() {
	    return new Solver(solvers);
	}
    }
}
