package sudoku;

import java.util.List;
import java.util.ArrayList;

public class Puzzle implements Cloneable {
    public static final int sideLength = 9;
    public static final int regionSize = 3;
    
    private List<List<Integer>> content;

    public Puzzle() {
	content = new ArrayList<List<Integer>>();

	for (int i = 0; i < Puzzle.sideLength; ++i) {
	    content.add(new ArrayList<Integer>());
	    
	    for (int j = 0; j < Puzzle.sideLength; ++j) {
		content.get(i).add(0);
	    }
	}
    }

    // TODO: Remove
    // public Puzzle(PuzzleGenerator generator) {
    // 	content = generator.generate();
    // }

    public int getCell(int x, int y) {
	return content.get(y).get(x);
    }

    public void setValue(int x, int y, int value) {
	content.get(y).set(x, value);
    }

    public boolean check() {
	boolean valid = true;
	
	for (List<Integer> row : content) {
	    for (int i = 0; i < Puzzle.sideLength; ++i) {
		for (int j = 0; j < Puzzle.sideLength; ++j) {
		    if (i != j) {
			valid &= row.get(i) == 0 || row.get(i) != row.get(j);
		    }
		}
	    }
	}
    }

    public Object clone() {
	Puzzle puzzle = null;

	try {
	    puzzle = (Puzzle) super.clone();
	} catch(CloneNotSupportedException e) {
	    e.printStackTrace();
	}

	puzzle.content = new ArrayList<List<Integer>>();
	for (int i = 0; i < Puzzle.sideLength; i++) {
	    puzzle.content.add(new ArrayList<Integer>());
	    for (int j = 0; j < Puzzle.sideLength; j++) {
		puzzle.content.get(i).add(content.get(i).get(j));
	    }
	}

	return puzzle;
    }

    public String toString() {
	String horizontalSeparator = " +-------+-------+-------+";
	String result = "";

	result += horizontalSeparator + "\n";
	
	for (int i = 0; i < sideLength; ++i)  {
	    List<Integer> row = content.get(i);

	    result += " | ";
	    for (int j = 0; j < sideLength; ++j)  {
		result += row.get(j) + " ";
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
