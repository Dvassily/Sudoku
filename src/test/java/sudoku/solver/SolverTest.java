package sudoku.solver;

import org.junit.Test;
import static org.junit.Assert.*;

import sudoku.*;

public class SolverTest {
    @Test
    public void testSolve() {
	Puzzle puzzle = new Puzzle();
	puzzle.load(".84.1......6.....7.2...3...3..6..1...1.....5..52.4.9.....98.57...5..4.39.......2.");

	Puzzle solution = new BacktrackingSolver().solve(puzzle, false).get(0);
	
	Solver solver = new Solver.SolverBuilder()
	    .addSolver(new NakedSetProcessor())
	    .addSolver(new HiddenSetProcessor())
	    .addSolver(new BoxLineReductionProcessor())
	    .addSolver(new PointingPairTripleProcessor())
	    .addSolver(new XWingProcessor())
	    .addSolver(new SingleChainProcessor())
	    .addSolver(new YWingProcessor())
	    .build();

	solver.solve(puzzle);

	for (int i = 0; i < Puzzle.SIDE_LENGTH; ++i) {
	    for (int j = 0; j < Puzzle.SIDE_LENGTH; ++j) {
		Cell cell = puzzle.getCell(j, i);

		if (cell.isFilled()) {
		    assertEquals(solution.getCell(j, i).getValue(), cell.getValue()); 
		}
	    }
	}
    }
}
