package sudoku;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class Puzzle implements Cloneable {
    public static final int sideLength = 9;
    public static final int BLOCK_SIZE = 3;
    public static final int BLOCKS_PER_LINE = 3;
    public static final int NUMBER_OF_CELLS = 81;
    
    private List<List<Cell>> content;

    public Puzzle() {
	content = new ArrayList<List<Cell>>();

	for (int i = 0; i < Puzzle.sideLength; ++i) {
	    content.add(new ArrayList<Cell>());
	    
	    for (int j = 0; j < Puzzle.sideLength; ++j) {
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
	for (int i = 0; i < Puzzle.sideLength; ++i) {
	    for (int j = 0; j < Puzzle.sideLength; ++j) {
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
	for (int y = 0; y < Puzzle.sideLength; ++y) {
	    for (int x = 0; x < Puzzle.sideLength; ++x) {
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

	for (int i = 0; i < Puzzle.sideLength; i++) {
	    if (i != x) {
		cells.add(getCell(i, y));
	    }
	}

	for (int i = 0; i < Puzzle.sideLength; i++) {
	    if (i != y) {
		cells.add(getCell(x, i));
	    }
	}

	return cells;
    }

    public Set<Cell> findRow(int y, boolean filterFilled) {
	Set<Cell> cells = new HashSet<>();

	for (int i = 0; i < Puzzle.sideLength; ++i) {
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

    public Set<Cell> findColumn(int x, boolean filterFilled) {
	Set<Cell> cells = new HashSet<>();

	for (int i = 0; i < Puzzle.sideLength; ++i) {
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

    public Set<Cell> findSquare(int squareX, int squareY, boolean filterFilled) {
	Set<Cell> cells = new HashSet<>();

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
	
	for (int i = 1; i <= Puzzle.sideLength; ++i) {
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
	for (int i = 0; i < Puzzle.sideLength; i++) {
	    puzzle.content.add(new ArrayList<Cell>());
	    for (int j = 0; j < Puzzle.sideLength; j++) {
		puzzle.content.get(i).add((Cell) content.get(i).get(j).clone());
	    }
	}

	return puzzle;
    }

    public String toString() {
	String horizontalSeparator = " +-------+-------+-------+";
	String result = "";

	result += horizontalSeparator + "\n";
	
	for (int i = 0; i < sideLength; ++i)  {
	    List<Cell> row = content.get(i);

	    result += " | ";
	    for (int j = 0; j < sideLength; ++j)  {
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

    public void printCandidates() {
	String result = "";

	int min = 0;
	for (int i = 0; i < Puzzle.sideLength; ++i) {
	    for (int j = 0; j < Puzzle.sideLength; ++j) {
		String candidates = getCell(j, i).getCandidates().toString();
		if (min == 0 || candidates.length() > min) {
		    min = candidates.length();
		}
	    }
	}
	
	for (int i = 0; i < Puzzle.sideLength; ++i) {
	    for (int j = 0; j < Puzzle.sideLength; ++j) {
		result += String.format("%" + min + "s ", getCell(j, i).getCandidates().toString());
	    }
	    
	    result += "\n";
	}

	System.out.println(result);
    }
}
