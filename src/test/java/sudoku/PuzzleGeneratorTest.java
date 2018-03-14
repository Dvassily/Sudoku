package sudoku;

import java.util.Set;
import java.util.HashSet;
import org.junit.Test;
import static org.junit.Assert.*;

public class PuzzleGeneratorTest {
    private static final int numberOfSquares = 3;

    // TODO: Redo tests
    @Test
    public void testGenerate() {
	Puzzle puzzle = new PuzzleGenerator().generate(49);
	
	Set<Integer> values = new HashSet<>();
	
	int numberOfSquares = 3;

	for (int squareX = 0; squareX < numberOfSquares; ++squareX) {
	    for (int squareY = 0; squareY < numberOfSquares; ++squareY) {
		checkSquare(puzzle, squareX, squareY);
	    }
	}
	

	for (int i = 0; i < Puzzle.sideLength; i++) {
	    for (int j = 0; j < Puzzle.sideLength; j++) {
		int value = puzzle.getCell(j, i).getValue();

		if (value != 0) {
		    assertFalse(values.contains(value));
		    values.add(value);
		}
	    }

	    values.clear();
	}

	for (int i = 0; i < Puzzle.sideLength; i++) {
	    for (int j = 0; j < Puzzle.sideLength; j++) {
		int value = puzzle.getCell(i, j).getValue();

		if (value != 0) {
		    assertFalse(values.contains(value));
		    values.add(value);
		}
	    }

	    values.clear();
	}

    }

    private boolean checkSquare(Puzzle puzzle, int squareX, int squareY) {
	Set<Integer> values = new HashSet<>();
	
	for (int i = squareY * 3; i < squareY * 3 + 3; ++i) {
	    for (int j = squareX * 3; j < squareX * 3 + 3; ++j) {
		int value = puzzle.getCell(j, i).getValue();

		if (value != 0) {
		    assertFalse(values.contains(value));
		    values.add(value);
		}
	    }
	}

	return true;
    }
}
