package sudoku;

import java.util.Set;
import java.util.HashSet;

public class Cell implements Cloneable {
    private int x;
    private int y;
    private int value;
    private Set<Integer> candidates = new HashSet<>();
    
    public Cell(int x, int y, int value) {
	this.x = x;
	this.y = y;
	this.value = value;
    }

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }

    public void setValue(int value) {
	this.value = value;
    }
    
    public int getValue() {
	return value;
    }

    public boolean isFilled() {
	return value > 0;
    }

    public void addToCandidates(int value) {
	candidates.add(value);
    }

    public void removeFromCandidates(int value) {
	candidates.remove(value);
    }

    public Set<Integer> getCandidates() {
	return candidates;
    }

    public Object clone() {
	Cell cell = null;

	try {
	    cell = (Cell) super.clone();
	} catch(CloneNotSupportedException e) {
	    e.printStackTrace();
	}

	cell.x = x;
	cell.y = y;
	cell.value = value;
	
	cell.candidates = new HashSet<Integer>();
	for (int candidate : candidates) {
	    cell.getCandidates().add(candidate);
	}
	
	return cell;
    }

    public String toString() {
	return "(" + x + "," + y + ")[" + value + "]{" + candidates + "}";
    }
}
