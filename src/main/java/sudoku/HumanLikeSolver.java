package sudoku;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

// TODO: Implements ISolver
public class HumanLikeSolver {
    public boolean processSingleCandidates(Puzzle puzzle) {
	boolean found = false;
	
	for (int i = 0; i < Puzzle.sideLength; ++i) {
	    for (int j = 0; j < Puzzle.sideLength; ++j) {
		Set<Integer> cellCandidates = puzzle.getCell(j, i).getCandidates();
		
		if (cellCandidates.size() == 1) {
		    found = true;
		    int value = cellCandidates.iterator().next();
		    puzzle.setValue(j, i, value);
		    removeCandidates(puzzle, j, i, value);
		}
	    }
	}

	return found;
    }

    public boolean processNakedSets(Puzzle puzzle) {
	Set<Cell> cells = new HashSet<>();

	// Looks for naked sets in rows
	for (int i = 0; i < Puzzle.sideLength; ++i) {
	    for (int j = 0; j < Puzzle.sideLength; ++j) {
		Cell cell = puzzle.getCell(j, i);
		if (! cell.isFilled()) {
		    cells.add(cell);
		}
	    }

	    processNakedSets(cells);
	    
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

	    processNakedSets(cells);
	    
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
		
		processNakedSets(cells);
		
		cells.clear();
	    }
	}

	return false;
    }

    private void processNakedSets(Set<Cell> cells) {
	Set<Set<Cell>> subsets = powerSet(cells);

	for (Set<Cell> subset : subsets) {
	    if (subset.size() > 1 && subset.size() <= 4) {
		Set<Integer> candidates = new HashSet<>();
		for (Cell cell : subset) {
		    candidates.addAll(cell.getCandidates());
		}

		if (subset.size() == candidates.size()) {
		    for (Cell cell : cells) {
			if (! subset.contains(cell)) {
			    for (int candidate : candidates) {
				cell.removeFromCandidates(candidate);
			    }
			}
		    }
		}

		candidates.clear();
	    }
	}
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
