package sudoku.solver;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import sudoku.*;
import static sudoku.Strategy.*;

public class PointingPairTripleProcessor {
    public List<SolverStep> process(Puzzle puzzle) {
	List<SolverStep> steps = new ArrayList<>();

	for (int candidate = 1; candidate <= Puzzle.SIDE_LENGTH; ++candidate) {

	    for (int squareY = 0; squareY < Puzzle.BLOCKS_PER_LINE; ++squareY) {
		for (int squareX = 0; squareX < Puzzle.BLOCKS_PER_LINE; ++squareX) {
		    List<List<Cell>> regions = new ArrayList<>();
		    for (int j = 0; j < 3; ++j) {
			regions.add(new ArrayList<>());
		    }

		    for (Cell cell : puzzle.findSquare(squareX, squareY, true)) {
			if (cell.getCandidates().contains(candidate)) {
			    regions.get(cell.getY() % 3).add(cell);
			}
		    }

		    steps.addAll(process(puzzle, regions, candidate, true));
		}
	    }

	    for (int squareY = 0; squareY < Puzzle.BLOCKS_PER_LINE; ++squareY) {
		for (int squareX = 0; squareX < Puzzle.BLOCKS_PER_LINE; ++squareX) {
		    List<List<Cell>> regions = new ArrayList<>();
		    for (int j = 0; j < 3; ++j) {
			regions.add(new ArrayList<>());
		    }

		    for (Cell cell : puzzle.findSquare(squareX, squareY, true)) {
			if (cell.getCandidates().contains(candidate)) {
			    regions.get(cell.getX() % 3).add(cell);
			}
		    }

		    steps.addAll(process(puzzle, regions, candidate, false));
		}
	    }
	}

	return steps;
    }
    
    public List<SolverStep> process(Puzzle puzzle, List<List<Cell>> regions, int candidate, boolean horizontal) {
	List<SolverStep> steps = new ArrayList<>();
	List<Cell> pointingPairTriple = null;
	
	if (regions.get(0).size() >= 2 && regions.get(1).size() == 0 && regions.get(2).size() == 0) {
	    pointingPairTriple = regions.get(0);
	} else if (regions.get(0).size() == 0 && regions.get(1).size() >= 2 && regions.get(2).size() == 0) {
	    pointingPairTriple = regions.get(1);
	} else if (regions.get(0).size() == 0 && regions.get(1).size() == 0 && regions.get(2).size() >= 2) {
	    pointingPairTriple = regions.get(2);
	}

	if (pointingPairTriple != null) {
	    Cell cell = pointingPairTriple.iterator().next();
	    List<Cell> line = null;

	    if (horizontal) {
		line = puzzle.findRow(cell.getY(), true);
	    } else {
		line = puzzle.findColumn(cell.getX(), true);
	    }

	    line.removeAll(pointingPairTriple);

	    SolverStep step = new SolverStep(INTERSECTION_REMOVAL);
	    
	    for (Cell c : line) {
		if (cell.getCandidates().contains(candidate)) {
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
