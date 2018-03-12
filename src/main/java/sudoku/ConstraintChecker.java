package sudoku;

import java.util.Set;
import java.util.HashSet;

public class ConstraintChecker {
    public boolean check(Puzzle puzzle) {
	Set<Integer> values = new HashSet<>();

	// TODO: Change name
	int numberOfSquares = 3;

	for (int squareX = 0; squareX < numberOfSquares; ++squareX) {
	    for (int squareY = 0; squareY < numberOfSquares; ++squareY) {
		if (! checkSquare(puzzle, squareX, squareY)) {
		    return false;
		}
	    }
	}
	
	for (int i = 0; i < Puzzle.sideLength; i++) {
	    for (int j = 0; j < Puzzle.sideLength; j++) {
		int value = puzzle.getCell(j, i).getValue();

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
		int value = puzzle.getCell(i, j).getValue();

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

    private boolean checkSquare(Puzzle puzzle, int squareX, int squareY) {
	Set<Integer> values = new HashSet<>();
	
	for (int i = squareY * 3; i < squareY * 3 + 3; ++i) {
	    for (int j = squareX * 3; j < squareX * 3 + 3; ++j) {
		int value = puzzle.getCell(i, j).getValue();

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

}
