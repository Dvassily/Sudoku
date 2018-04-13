package sudoku.solver;

import java.util.List;
import java.util.ArrayList;

import sudoku.*;
import static sudoku.Strategy.*;

public class BacktrackingProcessor implements IStrategySolver {
    public List<SolverStep> solve(Puzzle puzzle) {
	Puzzle solution = new BacktrackingSolver().solve(puzzle, false).get(0);

	for (int i = 0; i < Puzzle.SIDE_LENGTH; ++i) {
	    for (int j = 0; j < Puzzle.SIDE_LENGTH; ++j) {
		puzzle.setValue(j, i, solution.getCell(j, i).getValue());
	    }
	}

	return new ArrayList<>();
    }
}
