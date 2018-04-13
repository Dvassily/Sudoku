package sudoku.solver;

import java.util.List;
import java.util.ArrayList;

import sudoku.*;
import static sudoku.Strategy.*;

public class XWingProcessor implements IStrategySolver {
    public List<SolverStep> solve(Puzzle puzzle) {
	List<SolverStep> steps = new ArrayList<>();
	
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
    		    steps.addAll(findParallelRow(puzzle, candidateCells.get(0), candidateCells.get(1), candidate, i));
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
    		    steps.addAll(findParallelColumn(puzzle, candidateCells.get(0), candidateCells.get(1), candidate, i));
    		}
    	    }
    	}

	return steps;
    }

    public List<SolverStep> findParallelRow(Puzzle puzzle, Cell cell1, Cell cell2, int candidate, int y) {
	List<SolverStep> steps = new ArrayList<>();
	
	for (int i = y + 1; i < Puzzle.SIDE_LENGTH; ++i) {
    	    List<Cell> row = puzzle.findRow(i, true);
	    List<Cell> candidateCells = new ArrayList<>();

	    for (Cell cell : row) {
		if (cell.getCandidates().contains(candidate)) {
		    candidateCells.add(cell);
		}
	    }

	    if (candidateCells.size() == 2 && candidateCells.get(0).getX() == cell1.getX() && candidateCells.get(1).getX() == cell2.getX()) {
		SolverStep step = new SolverStep(X_WING);
		
		for (Cell cell : puzzle.findColumn(cell1.getX(), true)) {
		    if (cell != cell1 && cell != candidateCells.get(0)) {
			if (cell.getCandidates().contains(candidate)) {
			    step.removeCandidate(cell, candidate);
			}
		    }
		}

		for (Cell cell : puzzle.findColumn(cell2.getX(), true)) {
		    if (cell != cell2 && cell != candidateCells.get(1)) {
			if (cell.getCandidates().contains(candidate)) {
			    step.removeCandidate(cell, candidate);
			}
		    }
		}

		if (step.getRemovals().size() > 0) {
		    steps.add(step);
		}
	    }
	}

	return steps;
    }

    public List<SolverStep> findParallelColumn(Puzzle puzzle, Cell cell1, Cell cell2, int candidate, int x) {
	List<SolverStep> steps = new ArrayList<>();
	
	for (int i = x + 1; i < Puzzle.SIDE_LENGTH; ++i) {
    	    List<Cell> row = puzzle.findColumn(i, true);
	    List<Cell> candidateCells = new ArrayList<>();

	    for (Cell cell : row) {
		if (cell.getCandidates().contains(candidate)) {
		    candidateCells.add(cell);
		}
	    }

	    if (candidateCells.size() == 2 && candidateCells.get(0).getY() == cell1.getY() && candidateCells.get(1).getY() == cell2.getY()) {
		SolverStep step = new SolverStep(X_WING);
		
		for (Cell cell : puzzle.findRow(cell1.getY(), true)) {
		    if (cell != cell1 && cell != candidateCells.get(0)) {
			if (cell.getCandidates().contains(candidate)) {
			    step.removeCandidate(cell, candidate);
			}
		    }
		}

		for (Cell cell : puzzle.findRow(cell2.getY(), true)) {
		    if (cell != cell2 && cell != candidateCells.get(1)) {
			if (cell.getCandidates().contains(candidate)) {
			    step.removeCandidate(cell, candidate);
			}
		    }
		}

		if (step.getRemovals().size() > 0) {
		    steps.add(step);
		}
	    }
	}
	
	return steps;
    }
}
