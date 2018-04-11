package sudoku.exceptions;

import java.lang.Exception;

public class InvalidPuzzleException extends RuntimeException {
    public InvalidPuzzleException(String puzzle) {
	super("Invalid puzzle : " + puzzle);
    }
}
