package sudoku.solver;

import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

import sudoku.*;

public class HiddenSetProcessorTest {
    // TODO: Example grid : http://www.sudokuwiki.org/Hidden_Candidates
    @Test
    public void testProcessHiddenSets() {
	Puzzle puzzle = new Puzzle();
	puzzle.setValue(0, 1, 9);
	puzzle.setValue(2, 1, 4);
	puzzle.setValue(3, 1, 6);
	puzzle.setValue(5, 1, 7);
	puzzle.setValue(1, 2, 7);
	puzzle.setValue(2, 2, 6);
	puzzle.setValue(3, 2, 8);
	puzzle.setValue(5, 2, 4);
	puzzle.setValue(6, 2, 1);
	puzzle.setValue(0, 3, 3);
	puzzle.setValue(2, 3, 9);
	puzzle.setValue(3, 3, 7);
	puzzle.setValue(5, 3, 1);
	puzzle.setValue(7, 3, 8);
    	puzzle.setValue(0, 4, 7);
	puzzle.setValue(2, 4, 8);
	puzzle.setValue(6, 4, 3);
	puzzle.setValue(8, 4, 1);
    	puzzle.setValue(1, 5, 5);
	puzzle.setValue(2, 5, 1);
	puzzle.setValue(3, 5, 3);
	puzzle.setValue(5, 5, 8);
	puzzle.setValue(6, 5, 7);
	puzzle.setValue(8, 5, 2);
	puzzle.setValue(2, 6, 7);
	puzzle.setValue(3, 6, 5);
	puzzle.setValue(5, 6, 2);
	puzzle.setValue(6, 6, 6);
	puzzle.setValue(7, 6, 1);
    	puzzle.setValue(2, 7, 5);
	puzzle.setValue(3, 7, 4);
	puzzle.setValue(5, 7, 3);
	puzzle.setValue(6, 7, 2);
	puzzle.setValue(8, 7, 8);
	
	puzzle.updateCandidates();
	
	Set<Integer> candidates = puzzle.getCell(7, 0).getCandidates();
	assertTrue(candidates.contains(2));
	assertTrue(candidates.contains(3));
	assertTrue(candidates.contains(4));
	assertTrue(candidates.contains(5));
	assertTrue(candidates.contains(6));
	assertTrue(candidates.contains(7));
	assertTrue(candidates.contains(9));
	assertEquals(7, candidates.size());

	candidates = puzzle.getCell(8, 0).getCandidates();
	assertTrue(candidates.contains(3));
	assertTrue(candidates.contains(4));
	assertTrue(candidates.contains(5));
	assertTrue(candidates.contains(6));
	assertTrue(candidates.contains(7));
	assertTrue(candidates.contains(9));
	assertEquals(6, candidates.size());

	for (SolverStep step : new HiddenSetProcessor().solve(puzzle)) {
	    step.apply();
	}

	candidates = puzzle.getCell(7, 0).getCandidates();

	assertTrue(candidates.contains(6));
	assertTrue(candidates.contains(7));
	assertEquals(2, candidates.size());

	candidates = puzzle.getCell(8, 0).getCandidates();
	assertTrue(candidates.contains(6));
	assertTrue(candidates.contains(7));
	assertEquals(2, candidates.size());
    }
}
