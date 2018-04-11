package sudoku.solver;

import org.junit.Test;
import static org.junit.Assert.*;

import sudoku.*;

public class BacktrackingSolverTest {
    @Test
    public void testSolve() {
	Puzzle puzzle = new PuzzleGenerator().generate(49);
	Puzzle solution = new BacktrackingSolver().solve(puzzle);
	assertTrue(new ConstraintChecker().check(puzzle));
    }
}
