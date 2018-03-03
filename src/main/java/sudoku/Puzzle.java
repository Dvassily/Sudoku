package sudoku;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class Puzzle implements Cloneable {
    public static final int sideLength = 9;
    public static final int blockSize = 3;
    
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

	int squareX = x / Puzzle.blockSize;
	int squareY = y / Puzzle.blockSize;

	for (int i = squareY * Puzzle.blockSize; i < squareY * Puzzle.blockSize + Puzzle.blockSize; ++i) {
	    for (int j = squareX * Puzzle.blockSize; j < squareX * Puzzle.blockSize + Puzzle.blockSize; ++j) {
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

	for (int i = 0; i < Puzzle.sideLength; ++i) {
	    for (int j = 0; j < Puzzle.sideLength; ++j) {
		result += getCell(j, i).getCandidates() + " ";
	    }
	    
	    result += "\n";
	}

	System.out.println(result);

    }
}
