package sudoku.solver;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

import sudoku.*;

public class BacktrackingSolverTest {
    @Test
    public void testSolve() {
	Puzzle puzzle = new PuzzleGenerator().generate(32);
	List<Puzzle> solutions = new BacktrackingSolver().solve(puzzle, true);
	assertEquals(1, solutions.size());
	assertTrue(new ConstraintChecker().check(solutions.get(0)));
    }
}
