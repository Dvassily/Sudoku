package sudoku;

public class Cell implements Cloneable {
    private int x;
    private int y;
    private int value;

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
	
	return cell;
    }
}
