package sudoku.solver;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import sudoku.*;
import static sudoku.Strategy.*;
import static sudoku.util.SolverHelper.visibleCells;

public class YWingProcessor {
    public List<SolverStep> process(Puzzle puzzle) {
	List<SolverStep> steps = new ArrayList<>();
	
    	for (int y = 0; y < Puzzle.SIDE_LENGTH; ++y) {
	    for (int x = 0; x < Puzzle.SIDE_LENGTH; ++x) {
		Cell hinge = puzzle.getCell(x, y);

		if (hinge.getCandidates().size() == 2) {
		    List<Cell> visible = filterBivalues(visibleCells(puzzle, hinge));
		    
		    for (int i = 0; i < visible.size(); ++i) {
			for (int j = i + 1; j < visible.size(); ++j) {
			    Cell wing1 = visible.get(i);
			    Cell wing2 = visible.get(j);

			    int a = 0;
			    int b = 0;
			    int c = 0;

			    List<Integer> candidates = new ArrayList<>(hinge.getCandidates());
			    candidates.retainAll(wing2.getCandidates());

			    if (candidates.size() == 1) {
				a = candidates.iterator().next();
			    }

			    candidates = new ArrayList<>(hinge.getCandidates());
			    candidates.retainAll(wing1.getCandidates());

			    if (candidates.size() == 1) {
				b = candidates.iterator().next();
			    }

			    candidates = new ArrayList<>(wing1.getCandidates());
			    candidates.retainAll(wing2.getCandidates());

			    if (candidates.size() == 1) {
				c = candidates.iterator().next();
			    }

			    boolean removed = false;
			    if (a != b && b != c && a != c) {
				List<Cell> intersection = visibleCells(puzzle, wing1);
				intersection.retainAll(visibleCells(puzzle, wing2));

				SolverStep step = new SolverStep(Y_WING);
				for (Cell cell : intersection) {
				    step.removeCandidate(cell, c);
				}

				if (step.getRemovals().size() > 0) {
				    steps.add(step);
				}
			    }
			}
		    }
		}
	    }
	}

	return steps;
    }

    private static List<Cell> filterBivalues(List<Cell> cells) {
	List<Cell> result = new ArrayList<>();
	
	for (Cell cell : cells) {
	    if (cell.getCandidates().size() == 2) {
		result.add(cell);
	    }
	}

	return result;
    }
}
