package sudoku;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

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
	boolean found = false;
	
	// Looks for naked sets in rows
	for (int i = 0; i < Puzzle.sideLength; ++i) {
	    found |= processNakedSets(puzzle.findRow(i, true));
	}

	// Looks for naked sets in columns
	for (int i = 0; i < Puzzle.sideLength; ++i) {
	    found |= processNakedSets(puzzle.findColumn(i, true));
	}

	for (int squareX = 0; squareX < 3; ++squareX) {
	    for (int squareY = 0; squareY < 3; ++squareY) {
		found |= processNakedSets(puzzle.findSquare(squareX, squareY, true));
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
	
	// Looks for hidden sets in rows
	for (int i = 0; i < Puzzle.sideLength; ++i) {
	    found |= processHiddenSets(puzzle.findRow(i, true));
	}

	// Looks for hidden sets in columns
	for (int i = 0; i < Puzzle.sideLength; ++i) {
	    found |= processHiddenSets(puzzle.findColumn(i, true));
	}

	for (int squareX = 0; squareX < 3; ++squareX) {
	    for (int squareY = 0; squareY < 3; ++squareY) {
		found |= processHiddenSets(puzzle.findSquare(squareX, squareY, true));
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

    // TODO: Use constant for 3
    public boolean processBoxLineReduction(Puzzle puzzle) {
	boolean found = false;

	for (int candidate = 1; candidate <= Puzzle.sideLength; ++candidate) {

	    for (int i = 0; i < Puzzle.sideLength; ++i) {
		List<Set<Cell>> regions = new ArrayList<>();
		for (int j = 0; j < 3; ++j) {
		    regions.add(new HashSet<>());
		}

		Set<Cell> cells = puzzle.findRow(i, true);
		for (Cell cell : cells) {
		    if (cell.getCandidates().contains(candidate)) {
			regions.get(cell.getSquareX()).add(cell);
		    }
		}

		found |= processBoxLineReduction(puzzle, regions, candidate);
	    }

	    for (int i = 0; i < Puzzle.sideLength; ++i) {
		List<Set<Cell>> regions = new ArrayList<>();
		for (int j = 0; j < 3; ++j) {
		    regions.add(new HashSet<>());
		}

		Set<Cell> cells = puzzle.findColumn(i, true);
		for (Cell cell : cells) {
		    if (cell.getCandidates().contains(candidate)) {
			regions.get(cell.getSquareY()).add(cell);
		    }
		}

		found |= processBoxLineReduction(puzzle, regions, candidate);
	    }

	}

	return found;
    }

    public boolean processBoxLineReduction(Puzzle puzzle, List<Set<Cell>> regions, int candidate) {
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

	    Set<Cell> square = puzzle.findSquare(cell.getSquareX(), cell.getSquareY(), true);
	    square.removeAll(boxLineReduction);

	    for (Cell c : square) {
		found |= c.getCandidates().remove(candidate);
	    }
	}

	return found;
    }


    public boolean processPointingPairs(Puzzle puzzle) {
	boolean found = false;

	for (int candidate = 1; candidate <= Puzzle.sideLength; ++candidate) {

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

		    found |= processPointingPairs(puzzle, regions, candidate, true);
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

		    found |= processPointingPairs(puzzle, regions, candidate, false);
		}
	    }
	}
	
	return found;
    }

    public boolean processPointingPairs(Puzzle puzzle, List<Set<Cell>> regions, int candidate, boolean horizontal) {
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
	    Set<Cell> line = null;

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
