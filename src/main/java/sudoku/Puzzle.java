package sudoku;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class Puzzle implements Cloneable {
    public static final int sideLength = 9;
    public static final int regionSize = 3;
    
    private List<List<Cell>> content;

    public Puzzle() {
	content = new ArrayList<List<Cell>>();

	for (int i = 0; i < Puzzle.sideLength; ++i) {
	    content.add(new ArrayList<Cell>());
	    
	    for (int j = 0; j < Puzzle.sideLength; ++j) {
		content.get(i).add(new Cell(j, i, 0));
	    }
	}
    }

    public Cell getCell(int x, int y) {
	return content.get(y).get(x);
    }

    public void setValue(int x, int y, int value) {
	content.get(y).get(x).setValue(value);
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

    public boolean checkConstraints() {
	Set<Integer> values = new HashSet<>();

	int numberOfSquares = 3;

	for (int squareX = 0; squareX < numberOfSquares; ++squareX) {
	    for (int squareY = 0; squareY < numberOfSquares; ++squareY) {
		if (! checkSquare(squareX, squareY)) {
		    return false;
		}
	    }
	}
	

	for (int i = 0; i < Puzzle.sideLength; i++) {
	    for (int j = 0; j < Puzzle.sideLength; j++) {
		int value = content.get(i).get(j).getValue();

		if (value != 0) {
		    if (values.contains(value)) {
			return false;
		    }

		    values.add(value);
		}
	    }

	    values.clear();
	}

	for (int i = 0; i < Puzzle.sideLength; i++) {
	    for (int j = 0; j < Puzzle.sideLength; j++) {
		int value = content.get(j).get(i).getValue();

		if (value != 0) {
		    if (values.contains(value)) {
			return false;
		    }

		    values.add(value);
		}
	    }

	    values.clear();
	}

	return true;
    }

    private boolean checkSquare(int squareX, int squareY) {
	Set<Integer> values = new HashSet<>();
	
	for (int i = squareX * 3; i < squareX * 3 + 3; ++i) {
	    for (int j = squareY * 3; j < squareY * 3 + 3; ++j) {
		int value = getCell(i, j).getValue();

		if (value != 0) {
		    if (values.contains(value)) {
			return false;
		    }

		    values.add(value);
		}
	    }
	}

	return true;
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

    public static void main(String[] args) {
	System.out.println("foo");
    }
}
