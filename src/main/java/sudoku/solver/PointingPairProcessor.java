package sudoku.solver;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import sudoku.*;
import static sudoku.Strategy.*;

public class PointingPairProcessor {
    private PuzzleEvaluator puzzleEvaluator;
    
    public PointingPairProcessor(PuzzleEvaluator puzzleEvaluator) {
	this.puzzleEvaluator = puzzleEvaluator;
    }

    public PointingPairProcessor() {
	this(null);
    }
    
    public boolean process(Puzzle puzzle) {
	boolean found = false;

	for (int candidate = 1; candidate <= Puzzle.SIDE_LENGTH; ++candidate) {

	    for (int squareY = 0; squareY < Puzzle.BLOCKS_PER_LINE; ++squareY) {
		for (int squareX = 0; squareX < Puzzle.BLOCKS_PER_LINE; ++squareX) {
		    List<Set<Cell>> regions = new ArrayList<>();
		    for (int j = 0; j < 3; ++j) {
			regions.add(new HashSet<>());
		    }

		    for (Cell cell : puzzle.findSquare(squareX, squareY, true)) {
			if (cell.getCandidates().contains(candidate)) {
			    regions.get(cell.getY() % 3).add(cell);
			}
		    }

		    found |= process(puzzle, regions, candidate, true);
		}
	    }

	    for (int squareY = 0; squareY < Puzzle.BLOCKS_PER_LINE; ++squareY) {
		for (int squareX = 0; squareX < Puzzle.BLOCKS_PER_LINE; ++squareX) {
		    List<Set<Cell>> regions = new ArrayList<>();
		    for (int j = 0; j < 3; ++j) {
			regions.add(new HashSet<>());
		    }

		    for (Cell cell : puzzle.findSquare(squareX, squareY, true)) {
			if (cell.getCandidates().contains(candidate)) {
			    regions.get(cell.getX() % 3).add(cell);
			}
		    }

		    found |= process(puzzle, regions, candidate, false);
		}
	    }
	}

	return found;
    }
    
    public boolean process(Puzzle puzzle, List<Set<Cell>> regions, int candidate, boolean horizontal) {
	boolean found = false;
	Set<Cell> pointingPair = null;
	
	if (regions.get(0).size() >= 2 && regions.get(1).size() == 0 && regions.get(2).size() == 0) {
	    pointingPair = regions.get(0);
	} else if (regions.get(0).size() == 0 && regions.get(1).size() >= 2 && regions.get(2).size() == 0) {
	    pointingPair = regions.get(1);
	} else if (regions.get(0).size() == 0 && regions.get(1).size() == 0 && regions.get(2).size() >= 2) {
	    pointingPair = regions.get(2);
	}

	if (pointingPair != null) {
	    Cell cell = pointingPair.iterator().next();
	    List<Cell> line = null;

	    if (horizontal) {
		line = puzzle.findRow(cell.getY(), true);
	    } else {
		line = puzzle.findColumn(cell.getX(), true);
	    }

	    line.removeAll(pointingPair);

	    for (Cell c : line) {
		found |= c.getCandidates().remove(candidate);
	    }
	}

	if (puzzleEvaluator != null && found) {
	    puzzleEvaluator.incrementScore(INTERSECTION_REMOVAL);
	}
	
	return found;
    }
}
