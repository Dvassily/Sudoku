package sudoku.solver;

import java.util.List;
import java.util.ArrayList;

import sudoku.*;
import static sudoku.Strategy.*;

public class XWingProcessor {
    private PuzzleEvaluator puzzleEvaluator;
    
    public XWingProcessor(PuzzleEvaluator puzzleEvaluator) {
    	this.puzzleEvaluator = puzzleEvaluator;
    }

    public XWingProcessor() {
    	this(null);
    }
    
    public boolean process(Puzzle puzzle) {
	boolean found = false;
	
    	for (int i = 0; i < Puzzle.SIDE_LENGTH; ++i) {
    	    List<Cell> row = puzzle.findRow(i, true);

    	    for (int candidate = 1; candidate <= Puzzle.NUMBER_OF_VALUES; ++candidate) {
    		List<Cell> candidateCells = new ArrayList<>();

    		for (Cell cell : row) {
    		    if (cell.getCandidates().contains(candidate)) {
    			candidateCells.add(cell);
    		    }
    		}

    		if (candidateCells.size() == 2) {
    		    found |= findParallelRow(puzzle, candidateCells.get(0), candidateCells.get(1), candidate, i);
    		}
    	    }
    	}

	for (int i = 0; i < Puzzle.SIDE_LENGTH; ++i) {
	    List<Cell> column = puzzle.findColumn(i, true);

    	    for (int candidate = 1; candidate <= Puzzle.NUMBER_OF_VALUES; ++candidate) {
    		List<Cell> candidateCells = new ArrayList<>();

    		for (Cell cell : column) {
    		    if (cell.getCandidates().contains(candidate)) {
    			candidateCells.add(cell);
    		    }
    		}

    		if (candidateCells.size() == 2) {
    		    found |= findParallelColumn(puzzle, candidateCells.get(0), candidateCells.get(1), candidate, i);
    		}
    	    }
    	}

	return found;
    }

    public boolean findParallelRow(Puzzle puzzle, Cell cell1, Cell cell2, int candidate, int y) {
	boolean found = false;
	
	for (int i = y + 1; i < Puzzle.SIDE_LENGTH; ++i) {
    	    List<Cell> row = puzzle.findRow(i, true);
	    List<Cell> candidateCells = new ArrayList<>();

	    for (Cell cell : row) {
		if (cell.getCandidates().contains(candidate)) {
		    candidateCells.add(cell);
		}
	    }

	    if (candidateCells.size() == 2 && candidateCells.get(0).getX() == cell1.getX() && candidateCells.get(1).getX() == cell2.getX()) {
		for (Cell cell : puzzle.findColumn(cell1.getX(), true)) {
		    if (cell != cell1 && cell != candidateCells.get(0)) {
			found |= cell.getCandidates().remove(candidate);
		    }
		}

		for (Cell cell : puzzle.findColumn(cell2.getX(), true)) {
		    if (cell != cell2 && cell != candidateCells.get(1)) {
			found |= cell.getCandidates().remove(candidate);
		    }
		}
	    }
	}

	if (found && puzzleEvaluator != null) {
	    puzzleEvaluator.incrementScore(X_WING);
	}

	return found;
    }

    public boolean findParallelColumn(Puzzle puzzle, Cell cell1, Cell cell2, int candidate, int x) {
	boolean found = false;
	
	for (int i = x + 1; i < Puzzle.SIDE_LENGTH; ++i) {
    	    List<Cell> row = puzzle.findColumn(i, true);
	    List<Cell> candidateCells = new ArrayList<>();

	    for (Cell cell : row) {
		if (cell.getCandidates().contains(candidate)) {
		    candidateCells.add(cell);
		}
	    }

	    if (candidateCells.size() == 2 && candidateCells.get(0).getY() == cell1.getY() && candidateCells.get(1).getY() == cell2.getY()) {
		for (Cell cell : puzzle.findRow(cell1.getY(), true)) {
		    if (cell != cell1 && cell != candidateCells.get(0)) {
			found |= cell.getCandidates().remove(candidate);
		    }
		}

		for (Cell cell : puzzle.findRow(cell2.getY(), true)) {
		    if (cell != cell2 && cell != candidateCells.get(1)) {
			found |= cell.getCandidates().remove(candidate);
		    }
		}
	    }
	}

	if (found && puzzleEvaluator != null) {
	    puzzleEvaluator.incrementScore(X_WING);
	}
	
	return found;
    }
}
