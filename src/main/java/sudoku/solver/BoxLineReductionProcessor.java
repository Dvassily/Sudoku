package sudoku.solver;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import static sudoku.Strategy.*;
import sudoku.*;

public class BoxLineReductionProcessor {
    // TODO: Use constant for 3
    public List<SolverStep> process(Puzzle puzzle) {
	List<SolverStep> steps = new ArrayList<>();

	for (int candidate = 1; candidate <= Puzzle.SIDE_LENGTH; ++candidate) {

	    for (int i = 0; i < Puzzle.SIDE_LENGTH; ++i) {
		List<List<Cell>> regions = new ArrayList<>();
		for (int j = 0; j < 3; ++j) {
		    regions.add(new ArrayList<>());
		}

		List<Cell> cells = puzzle.findRow(i, true);
		for (Cell cell : cells) {
		    if (cell.getCandidates().contains(candidate)) {
			regions.get(cell.getSquareX()).add(cell);
		    }
		}

		steps.addAll(process(puzzle, regions, candidate));
	    }

	    for (int i = 0; i < Puzzle.SIDE_LENGTH; ++i) {
		List<List<Cell>> regions = new ArrayList<>();
		for (int j = 0; j < 3; ++j) {
		    regions.add(new ArrayList<>());
		}

		List<Cell> cells = puzzle.findColumn(i, true);
		for (Cell cell : cells) {
		    if (cell.getCandidates().contains(candidate)) {
			regions.get(cell.getSquareY()).add(cell);
		    }
		}

		steps.addAll(process(puzzle, regions, candidate));
	    }

	}

	return steps;
    }

    public List<SolverStep> process(Puzzle puzzle, List<List<Cell>> regions, int candidate) {
	List<SolverStep> steps = new ArrayList<>();
	List<Cell> boxLineReduction = null;

	if (regions.get(0).size() >= 2 && regions.get(1).size() == 0 && regions.get(2).size() == 0) {
	    boxLineReduction = regions.get(0);
	} else if (regions.get(0).size() == 0 && regions.get(1).size() >= 2 && regions.get(2).size() == 0) {
	    boxLineReduction = regions.get(1);
	} else if (regions.get(0).size() == 0 && regions.get(1).size() == 0 && regions.get(2).size() >= 2) {
	    boxLineReduction = regions.get(2);
	}	
	
	if (boxLineReduction != null) {
	    Cell cell = boxLineReduction.get(0);

	    List<Cell> square = puzzle.findSquare(cell.getSquareX(), cell.getSquareY(), true);
	    square.removeAll(boxLineReduction);

	    SolverStep step = new SolverStep(INTERSECTION_REMOVAL);
	    for (Cell c : square) {
		if (c.getCandidates().contains(candidate)) {
		    step.removeCandidate(c, candidate);
		}
	    }

	    if (step.getRemovals().size() > 0) {
		steps.add(step);
	    }
	}
	
	return steps;
    }

}
