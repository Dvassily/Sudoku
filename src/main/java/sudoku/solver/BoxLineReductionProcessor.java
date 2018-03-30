package sudoku.solver;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import static sudoku.Strategy.*;
import sudoku.*;

public class BoxLineReductionProcessor {
    private PuzzleEvaluator puzzleEvaluator;
    
    public BoxLineReductionProcessor(PuzzleEvaluator puzzleEvaluator) {
	this.puzzleEvaluator = puzzleEvaluator;
    }

    public BoxLineReductionProcessor() {
	this(null);
    }

    // TODO: Use constant for 3
    public boolean process(Puzzle puzzle) {
	boolean found = false;

	for (int candidate = 1; candidate <= Puzzle.SIDE_LENGTH; ++candidate) {

	    for (int i = 0; i < Puzzle.SIDE_LENGTH; ++i) {
		List<Set<Cell>> regions = new ArrayList<>();
		for (int j = 0; j < 3; ++j) {
		    regions.add(new HashSet<>());
		}

		List<Cell> cells = puzzle.findRow(i, true);
		for (Cell cell : cells) {
		    if (cell.getCandidates().contains(candidate)) {
			regions.get(cell.getSquareX()).add(cell);
		    }
		}

		found |= process(puzzle, regions, candidate);
	    }

	    for (int i = 0; i < Puzzle.SIDE_LENGTH; ++i) {
		List<Set<Cell>> regions = new ArrayList<>();
		for (int j = 0; j < 3; ++j) {
		    regions.add(new HashSet<>());
		}

		List<Cell> cells = puzzle.findColumn(i, true);
		for (Cell cell : cells) {
		    if (cell.getCandidates().contains(candidate)) {
			regions.get(cell.getSquareY()).add(cell);
		    }
		}

		found |= process(puzzle, regions, candidate);
	    }

	}

	return found;
    }

    public boolean process(Puzzle puzzle, List<Set<Cell>> regions, int candidate) {
	boolean found = false;
	
	Set<Cell> boxLineReduction = null;

	if (regions.get(0).size() >= 2 && regions.get(1).size() == 0 && regions.get(2).size() == 0) {
	    boxLineReduction = regions.get(0);
	} else if (regions.get(0).size() == 0 && regions.get(1).size() >= 2 && regions.get(2).size() == 0) {
	    boxLineReduction = regions.get(1);
	} else if (regions.get(0).size() == 0 && regions.get(1).size() == 0 && regions.get(2).size() >= 2) {
	    boxLineReduction = regions.get(2);
	}	
	
	if (boxLineReduction != null) {
	    Cell cell = boxLineReduction.iterator().next();

	    List<Cell> square = puzzle.findSquare(cell.getSquareX(), cell.getSquareY(), true);
	    square.removeAll(boxLineReduction);

	    for (Cell c : square) {
		found |= c.getCandidates().remove(candidate);
	    }
	}

	if (puzzleEvaluator != null && found) {
	    puzzleEvaluator.incrementScore(INTERSECTION_REMOVAL);
	}
	
	return found;
    }

}
