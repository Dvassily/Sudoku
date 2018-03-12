package sudoku.solver;

import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

import sudoku.*;

public class BoxLineReductionProcessorTest {
    // Example Grid : http://www.sudokuwiki.org/Intersection_Removal
    @Test
    public void testBoxLineReduction() {
	Puzzle puzzle = new Puzzle();
	puzzle.setValue(1, 0, 1);
	puzzle.setValue(2, 0, 6);
	puzzle.setValue(5, 0, 7);
	puzzle.setValue(6, 0, 8);
	puzzle.setValue(8, 0, 3);

	puzzle.setValue(1, 1, 9);
	puzzle.setValue(3, 1, 8);

	puzzle.setValue(0, 2, 8);
	puzzle.setValue(1, 2, 7);
	puzzle.setValue(5, 2, 1);
	puzzle.setValue(7, 2, 6);

	puzzle.setValue(0, 3, 2);
	puzzle.setValue(1, 3, 4);
	puzzle.setValue(2, 3, 8);
	puzzle.setValue(6, 3, 3);

	puzzle.setValue(0, 4, 6);
	puzzle.setValue(1, 4, 5);
	puzzle.setValue(5, 4, 9);
	puzzle.setValue(7, 4, 8);
	puzzle.setValue(8, 4, 2);

	puzzle.setValue(1, 5, 3);
	puzzle.setValue(2, 5, 9);
	puzzle.setValue(6, 5, 6);
	puzzle.setValue(7, 5, 5);

	puzzle.setValue(1, 6, 6);
	puzzle.setValue(3, 6, 9);
	puzzle.setValue(7, 6, 2);

	puzzle.setValue(1, 7, 8);
	puzzle.setValue(5, 7, 2);
	puzzle.setValue(6, 7, 9);
	puzzle.setValue(7, 7, 3);
	puzzle.setValue(8, 7, 6);

	puzzle.setValue(0, 8, 9);
	puzzle.setValue(1, 8, 2);
	puzzle.setValue(2, 8, 4);
	puzzle.setValue(3, 8, 6);
	puzzle.setValue(6, 8, 5);
	puzzle.setValue(7, 8, 1);
	puzzle.updateCandidates();
	
	new HumanLikeSolver().processBoxLineReduction(puzzle);

	// Asserts candidates 2 are removed from square (1, 0)
	Set<Integer> candidates = puzzle.getCell(4, 1).getCandidates();
	assertTrue(candidates.contains(3));
	assertTrue(candidates.contains(4));
	assertTrue(candidates.contains(5));
	assertTrue(candidates.contains(6));
	assertEquals(4, candidates.size());

	candidates = puzzle.getCell(3, 2).getCandidates();
	assertTrue(candidates.contains(3));
	assertTrue(candidates.contains(4));
	assertTrue(candidates.contains(5));
	assertEquals(3, candidates.size());

	candidates = puzzle.getCell(4, 2).getCandidates();
	assertTrue(candidates.contains(3));
	assertTrue(candidates.contains(4));
	assertTrue(candidates.contains(5));
	assertTrue(candidates.contains(9));
	assertEquals(4, candidates.size());

	// Asserts candidates 2 are removed from square (2, 0)
	candidates = puzzle.getCell(6, 1).getCandidates();
	assertTrue(candidates.contains(1));
	assertTrue(candidates.contains(2));
	assertTrue(candidates.contains(7));
	assertEquals(3, candidates.size());

	candidates = puzzle.getCell(8, 1).getCandidates();
	assertTrue(candidates.contains(1));
	assertTrue(candidates.contains(5));
	assertTrue(candidates.contains(7));
	assertEquals(3, candidates.size());

	candidates = puzzle.getCell(6, 2).getCandidates();
	assertTrue(candidates.contains(2));
	assertEquals(1, candidates.size());

	candidates = puzzle.getCell(8, 2).getCandidates();
	assertTrue(candidates.contains(5));
	assertTrue(candidates.contains(9));
	assertEquals(2, candidates.size());
    }
}
