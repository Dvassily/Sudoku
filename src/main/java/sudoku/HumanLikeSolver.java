package sudoku;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import static sudoku.Strategy.*;

// TODO: Implements ISolver
public class HumanLikeSolver {
    private PuzzleEvaluator puzzleEvaluator;
    
    public HumanLikeSolver(PuzzleEvaluator puzzleEvaluator) {
	this.puzzleEvaluator = puzzleEvaluator;
    }

    public HumanLikeSolver() {
	this(null);
    }

    public boolean processSingleCandidates(Puzzle puzzle) {
	boolean found = false;
	
	for (int i = 0; i < Puzzle.sideLength; ++i) {
	    for (int j = 0; j < Puzzle.sideLength; ++j) {
		Set<Integer> cellCandidates = puzzle.getCell(j, i).getCandidates();
		
		if (cellCandidates.size() == 1) {
		    int value = cellCandidates.iterator().next();
		    puzzle.setValue(j, i, value);
		    removeCandidates(puzzle, j, i, value);
		    found = true;

		    if (puzzleEvaluator != null) {
			puzzleEvaluator.incrementScore(SINGLE_CANDIDATE);
		    }
		}
	    }
	}

	return found;
    }

    public boolean processNakedSets(Puzzle puzzle) {
	Set<Cell> cells = new HashSet<>();
	boolean found = false;
	
	// Looks for naked sets in rows
	for (int i = 0; i < Puzzle.sideLength; ++i) {
	    for (int j = 0; j < Puzzle.sideLength; ++j) {
		Cell cell = puzzle.getCell(j, i);
		if (! cell.isFilled()) {
		    cells.add(cell);
		}
	    }

	    found |= processNakedSets(cells);
	    
	    cells.clear();
	}

	// Looks for naked sets in columns
	for (int i = 0; i < Puzzle.sideLength; ++i) {
	    for (int j = 0; j < Puzzle.sideLength; ++j) {
		Cell cell = puzzle.getCell(i, j);
		if (! cell.isFilled()) {
		    cells.add(cell);
		}
	    }

	    found |= processNakedSets(cells);
	    
	    cells.clear();
	}

	// TODO: Add constant
	for (int squareX = 0; squareX < 3; ++squareX) {
	    for (int squareY = 0; squareY < 3; ++squareY) {
		for (int i = squareY * 3; i < squareY * 3 + 3; ++i) {
		    for (int j = squareX * 3; j < squareX * 3 + 3; ++j) {
			Cell cell = puzzle.getCell(j, i);
			if (! cell.isFilled()) {
			    cells.add(cell);
			}
		    }
		}
		
		found |= processNakedSets(cells);
		
		cells.clear();
	    }
	}

	return found;
    }

    private boolean processNakedSets(Set<Cell> cells) {
	boolean found = false;
	
	Set<Set<Cell>> subsets = powerSet(cells);

	for (Set<Cell> subset : subsets) {
	    if (subset.size() > 1 && subset.size() <= 4 && subset.size() < cells.size()) {
		Set<Integer> candidates = new HashSet<>();
		for (Cell cell : subset) {
		    candidates.addAll(cell.getCandidates());
		}

		if (subset.size() == candidates.size()) {
		    boolean removed = false;
		    for (Cell cell : cells) {
			if (! subset.contains(cell)) {
			    for (int candidate : candidates) {
				removed |= cell.removeFromCandidates(candidate);
			    }
			}
		    }

		    if (removed) {
			if (puzzleEvaluator != null) {
			    puzzleEvaluator.incrementScore(nakedSet(subset.size()));
			}

			found = true;
		    }
		}

		candidates.clear();
	    }
	}

	return found;
    }

    public boolean processHiddenSets(Puzzle puzzle) {
	boolean found = false;
	
	Set<Cell> cells = new HashSet<>();

	// Looks for naked sets in rows
	for (int i = 0; i < Puzzle.sideLength; ++i) {
	    for (int j = 0; j < Puzzle.sideLength; ++j) {
		Cell cell = puzzle.getCell(j, i);
		if (! cell.isFilled()) {
		    cells.add(cell);
		}
	    }

	    found |= processHiddenSets(cells);
	    
	    cells.clear();
	}

	// Looks for hidden sets in columns
	for (int i = 0; i < Puzzle.sideLength; ++i) {
	    for (int j = 0; j < Puzzle.sideLength; ++j) {
		Cell cell = puzzle.getCell(i, j);
		if (! cell.isFilled()) {
		    cells.add(cell);
		}
	    }

	    found |= processHiddenSets(cells);
	    
	    cells.clear();
	}

	// TODO: Add constant
	for (int squareX = 0; squareX < 3; ++squareX) {
	    for (int squareY = 0; squareY < 3; ++squareY) {
		for (int i = squareY * 3; i < squareY * 3 + 3; ++i) {
		    for (int j = squareX * 3; j < squareX * 3 + 3; ++j) {
			Cell cell = puzzle.getCell(j, i);
			if (! cell.isFilled()) {
			    cells.add(cell);
			}
		    }
		}

		found |= processHiddenSets(cells);
		
		cells.clear();
	    }
	}


	return found;
    }

    private boolean processHiddenSets(Set<Cell> cells) {
	boolean found = false;
	Set<Set<Cell>> subsets = powerSet(cells);

	for (Set<Cell> subset : subsets) {
	    if (subset.size() > 1 && subset.size() <= 4 && subset.size() < cells.size()) {
		Set<Integer> candidates = new HashSet<>();
		for (Cell cell : subset) {
		    candidates.addAll(cell.getCandidates());
		}

		for (Cell cell : cells) {
		    if (! subset.contains(cell)) {
			for (int candidate : cell.getCandidates()) {
			    candidates.remove(candidate);
			}
		    }
		}

		if (candidates.size() == subset.size()) {
		    boolean removed = false;
		    
		    for (Cell cell : subset) {
			Set<Integer> toRemove = new HashSet<>();
			for (int candidate : cell.getCandidates()) {
			    if (! candidates.contains(candidate)) {
				toRemove.add(candidate);
			    }
			}

			removed |= cell.getCandidates().removeAll(toRemove);
		    }

		    if (removed) {
			if (puzzleEvaluator != null) {
			    puzzleEvaluator.incrementScore(hiddenSet(subset.size()));
			}

			found = true;
		    }

		}

		candidates.clear();
	    }
	}

	return found;
    }

    private void removeCandidates(Puzzle puzzle, int x, int y, int value) {
	Set<Cell> cellsInRegion = new HashSet<>();
	puzzle.getCell(x, y).getCandidates().clear();

	for (Cell cell : puzzle.findCellsInRegion(x, y)) {
	    cell.getCandidates().remove(value);
	}
    }

    public Set<Set<Cell>> powerSet(Set<Cell> cells) {
	Set<Set<Cell>> sets = new HashSet<Set<Cell>>();

	if (cells.size() == 0) {
	    sets.add(new HashSet<Cell>());
	    return sets;
	}

	List<Cell> cellsList = new ArrayList<Cell>(cells);
	Cell head = cellsList.get(0);
	for (Set<Cell> subset : powerSet(new HashSet<Cell>(cellsList.subList(1, cells.size())))) {
	    Set<Cell> set = new HashSet<>();
	    set.add(head);
	    set.addAll(subset);
	    sets.add(set);
	    sets.add(subset);
	}

	return sets;
    }
    
}
