package sudoku.solver;

import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

import sudoku.*;

public class YWingProcessorTest {
    // TODO: Example grid : http://www.sudokuwiki.org/X_Wing_Strategy
    @Test
    public void testProcess() {
	Puzzle puzzle = new Puzzle();
	puzzle.load("9..24.....5.69.231.2..5..9..9.7..32...29356.7.7...29...69.2..7351..79.622.7.86..9");

	puzzle.updateCandidates();
	
	new HumanLikeSolver().processYWing(puzzle);

	Set<Integer> candidates = puzzle.getCell(2, 7).getCandidates();
	assertTrue(candidates.contains(3));
	assertTrue(candidates.contains(8));
	assertEquals(2, candidates.size());

	candidates = puzzle.getCell(0, 1).getCandidates();
	assertTrue(candidates.contains(4));
	assertTrue(candidates.contains(7));
	assertEquals(2, candidates.size());

	candidates = puzzle.getCell(0, 2).getCandidates();
	assertTrue(candidates.contains(1));
	assertTrue(candidates.contains(3));
	assertTrue(candidates.contains(4));
	assertTrue(candidates.contains(6));
	assertTrue(candidates.contains(7));
	assertEquals(5, candidates.size());

	new HumanLikeSolver().processYWing(puzzle);
	
	candidates = puzzle.getCell(0, 3).getCandidates();
	System.out.println(candidates);

	assertTrue(candidates.contains(1));
	assertTrue(candidates.contains(6));
	assertTrue(candidates.contains(8));
	assertEquals(3, candidates.size());

    }
}
