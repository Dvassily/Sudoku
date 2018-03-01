package sudoku;

import java.util.List;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class PuzzleGeneratorTest {
    private static final int numberOfSquares = 3;
    private static final int numberOfPuzzles = 100;

    // TODO: Redo tests
    @Test
    public void testGenerate() {
	for (int i = 0; i < numberOfPuzzles; ++i) {
	    Puzzle puzzle = new PuzzleGenerator().generate();

	    List<Integer> values = new ArrayList<>();
	    for (int x = 0; x < Puzzle.sideLength; ++x) {
		for (int y = 0; y < Puzzle.sideLength; ++y) {
		    values.add(puzzle.getCell(x, y));
		}

		for (int j = 1; j <= Puzzle.sideLength; ++j) {
		    //assertTrue(values.contains(j));
		}

		values.clear();
	    }


	    values.clear();
	    for (int y = 0; y < Puzzle.sideLength; ++y) {
		for (int x = 0; x < Puzzle.sideLength; ++x) {
		    values.add(puzzle.getCell(x, y));
		}

		for (int j = 1; j <= Puzzle.sideLength; ++j) {
		    // assertTrue(values.contains(j));
		}

		values.clear();
	    }

	    for (int squareX = 0; squareX < numberOfSquares; ++squareX) {
		for (int squareY = 0; squareY < numberOfSquares; ++squareY) {
		    // checkSquare(grid, squareX, squareY);
		}
	    }
	}
    }

    private void checkSquare(Puzzle puzzle, int squareX, int squareY) {
	List<Integer> values = new ArrayList<>();
	
	for (int i = squareX * 3; i < squareX * 3 + 3; ++i) {
	    for (int j = squareY * 3; j < squareY * 3 + 3; ++j) {
		values.add(puzzle.getCell(i, j));
	    }
	}

	for (int i = 1; i <= Puzzle.sideLength; ++i) {
	    assertTrue(values.contains(i));
	}
    }
}
