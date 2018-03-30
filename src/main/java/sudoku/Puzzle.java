package sudoku;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import sudoku.exceptions.*;

public class Puzzle implements Cloneable {
    public static final int SIDE_LENGTH = 9;
    public static final int BLOCK_SIZE = 3;
    public static final int BLOCKS_PER_LINE = 3;
    public static final int NUMBER_OF_CELLS = 81;
    public static final int NUMBER_OF_VALUES = 9;
    
    private List<List<Cell>> content;

    public Puzzle() {
	content = new ArrayList<List<Cell>>();

	for (int i = 0; i < Puzzle.SIDE_LENGTH; ++i) {
	    content.add(new ArrayList<Cell>());
	    
	    for (int j = 0; j < Puzzle.SIDE_LENGTH; ++j) {
		Cell cell = new Cell(j, i, 0);
		content.get(i).add(cell);
	    }
	}

	updateCandidates();
    }

    public Cell getCell(int x, int y) {
	return content.get(y).get(x);
    }

    public boolean isCompleted() {
	for (int i = 0; i < Puzzle.SIDE_LENGTH; ++i) {
	    for (int j = 0; j < Puzzle.SIDE_LENGTH; ++j) {
		if (! getCell(i, j).isFilled()) {
		    return false;
		}
	    }
	}

	return true;
    }

    public void setValue(int x, int y, int value) {
	getCell(x, y).setValue(value);
    }

    public void updateCandidates() {
	for (int y = 0; y < Puzzle.SIDE_LENGTH; ++y) {
	    for (int x = 0; x < Puzzle.SIDE_LENGTH; ++x) {
		Cell cell = getCell(x, y);
		cell.getCandidates().clear();
		    
		for (int candidate : findCandidates(x, y)) {
		    cell.addToCandidates(candidate);
		}
	    }
	}
    }

    public Set<Cell> findCellsInRegion(int x, int y) {
	Set<Cell> cells = new HashSet<>();

	int squareX = x / Puzzle.BLOCK_SIZE;
	int squareY = y / Puzzle.BLOCK_SIZE;

	for (int i = squareY * Puzzle.BLOCK_SIZE; i < squareY * Puzzle.BLOCK_SIZE + Puzzle.BLOCK_SIZE; ++i) {
	    for (int j = squareX * Puzzle.BLOCK_SIZE; j < squareX * Puzzle.BLOCK_SIZE + Puzzle.BLOCK_SIZE; ++j) {
		if (i != y && j != x) {
		    cells.add(getCell(j, i));
		}
	    }
	}

	for (int i = 0; i < Puzzle.SIDE_LENGTH; i++) {
	    if (i != x) {
		cells.add(getCell(i, y));
	    }
	}

	for (int i = 0; i < Puzzle.SIDE_LENGTH; i++) {
	    if (i != y) {
		cells.add(getCell(x, i));
	    }
	}

	return cells;
    }

    public List<Cell> findRow(int y, boolean filterFilled) {
	List<Cell> cells = new ArrayList<>();

	for (int i = 0; i < Puzzle.SIDE_LENGTH; ++i) {
	    if (filterFilled) {
		if (! getCell(i, y).isFilled()) {
		    cells.add(getCell(i, y));
		}
	    } else {
		cells.add(getCell(i, y));
	    }
	}

	return cells;
    }

    public List<Cell> findColumn(int x, boolean filterFilled) {
	List<Cell> cells = new ArrayList<>();

	for (int i = 0; i < Puzzle.SIDE_LENGTH; ++i) {
	    if (filterFilled) {
		if (! getCell(x, i).isFilled()) {
		    cells.add(getCell(x, i));
		}
	    } else {
		cells.add(getCell(x, i));
	    }
	}

	return cells;
    }

    public List<Cell> findSquare(int squareX, int squareY, boolean filterFilled) {
	List<Cell> cells = new ArrayList<>();

	// TODO: Add constant for 3
	for (int i = squareY * 3; i < squareY * 3 + 3; ++i) {
	    for (int j = squareX * 3; j < squareX * 3 + 3; ++j) {
		Cell cell = getCell(j, i);
		if (filterFilled) {
		    if (! cell.isFilled()) {
			cells.add(cell);
		    }
		} else {
		    cells.add(cell);
		}
	    }
	}

	return cells;
    }
    
    public Set<Integer> findCandidates(int x, int y) {
	Set<Integer> candidates = new HashSet<Integer>();

	if (getCell(x, y).isFilled()) {
	    return candidates;
	}
	
	for (int i = 1; i <= Puzzle.SIDE_LENGTH; ++i) {
	    candidates.add(i);
	}

	for (Cell cell : findCellsInRegion(x, y)) {
	    candidates.remove(cell.getValue());
	}
	
	return candidates;
    }


    public Object clone() {
	Puzzle puzzle = null;

	try {
	    puzzle = (Puzzle) super.clone();
	} catch(CloneNotSupportedException e) {
	    e.printStackTrace();
	}

	puzzle.content = new ArrayList<List<Cell>>();
	for (int i = 0; i < Puzzle.SIDE_LENGTH; i++) {
	    puzzle.content.add(new ArrayList<Cell>());
	    for (int j = 0; j < Puzzle.SIDE_LENGTH; j++) {
		puzzle.content.get(i).add((Cell) content.get(i).get(j).clone());
	    }
	}

	return puzzle;
    }

    public void load(String puzzle) {
	if (puzzle.length() != 81) {
	    throw new InvalidPuzzleException(puzzle);
	}

	for (int i = 0; i < puzzle.length(); ++i) {
	    char current = puzzle.charAt(i);
	    
	    if (current == '.') {
		current = '0';
	    }

	    int value = Character.getNumericValue(current);

	    if (value < 0) {
		throw new InvalidPuzzleException(puzzle);
	    }

	    setValue(i % Puzzle.SIDE_LENGTH, i / Puzzle.SIDE_LENGTH, value);
	}
    }

    public String toString() {
	String horizontalSeparator = " +-------+-------+-------+";
	String result = "";

	result += horizontalSeparator + "\n";
	
	for (int i = 0; i < SIDE_LENGTH; ++i)  {
	    List<Cell> row = content.get(i);

	    result += " | ";
	    for (int j = 0; j < SIDE_LENGTH; ++j)  {
		result += row.get(j).getValue() + " ";
		if ((j + 1) % 3 == 0) {
		     result += "| ";
		}
	    }

	    result += "\n";
	    
	    if ((i + 1) % 3 == 0) {
		result += horizontalSeparator + "\n";
	    }
	    
	}

	return result;
    }
}
