package sudoku.solver;

import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

import sudoku.*;

public class XWingProcessorTest {
    // TODO: Example grid : http://www.sudokuwiki.org/X_Wing_Strategy
    @Test
    public void testXWingRow() {
	Puzzle puzzle = new Puzzle();
	puzzle.setValue(0, 0, 1);
	puzzle.setValue(6, 0, 5);
	puzzle.setValue(7, 0, 6);
	puzzle.setValue(8, 0, 9);

	puzzle.setValue(0, 1, 4);
	puzzle.setValue(1, 1, 9);
	puzzle.setValue(2, 1, 2);
	puzzle.setValue(4, 1, 5);
	puzzle.setValue(5, 1, 6);
	puzzle.setValue(6, 1, 1);
	puzzle.setValue(8, 1, 8);

	puzzle.setValue(1, 2, 5);
	puzzle.setValue(2, 2, 6);
	puzzle.setValue(3, 2, 1);
	puzzle.setValue(5, 2, 9);
	puzzle.setValue(6, 2, 2);
	puzzle.setValue(7, 2, 4);

	puzzle.setValue(2, 3, 9);
	puzzle.setValue(3, 3, 6);
	puzzle.setValue(4, 3, 4);
	puzzle.setValue(6, 3, 8);
	puzzle.setValue(8, 3, 1);

	puzzle.setValue(1, 4, 6);
	puzzle.setValue(2, 4, 4);
	puzzle.setValue(4, 4, 1);

	puzzle.setValue(0, 5, 2);
	puzzle.setValue(1, 5, 1);
	puzzle.setValue(2, 5, 8);
	puzzle.setValue(4, 5, 3);
	puzzle.setValue(5, 5, 5);
	puzzle.setValue(6, 5, 6);
	puzzle.setValue(8, 5, 4);
	
	puzzle.setValue(1, 6, 4);
	puzzle.setValue(3, 6, 5);
	puzzle.setValue(7, 6, 1);
	puzzle.setValue(8, 6, 6);

	puzzle.setValue(0, 7, 9);
	puzzle.setValue(2, 7, 5);
	puzzle.setValue(4, 7, 6);
	puzzle.setValue(5, 7, 1);
	puzzle.setValue(6, 7, 4);
	puzzle.setValue(8, 7, 2);

	puzzle.setValue(0, 8, 6);
	puzzle.setValue(1, 8, 2);
	puzzle.setValue(2, 8, 1);
	puzzle.setValue(8, 8, 5);
	puzzle.updateCandidates();
	
	new HumanLikeSolver().processXWing(puzzle);

	Set<Integer> candidates = puzzle.getCell(3, 0).getCandidates();
	assertTrue(candidates.contains(2));
	assertTrue(candidates.contains(3));
	assertTrue(candidates.contains(4));
	assertTrue(candidates.contains(8));
	assertEquals(4, candidates.size());

	candidates = puzzle.getCell(3, 4).getCandidates();
	assertTrue(candidates.contains(2));
	assertTrue(candidates.contains(8));
	assertTrue(candidates.contains(9));
	assertEquals(3, candidates.size());

	candidates = puzzle.getCell(3, 7).getCandidates();
	assertTrue(candidates.contains(3));
	assertTrue(candidates.contains(8));
	assertEquals(2, candidates.size());

	candidates = puzzle.getCell(3, 8).getCandidates();
	assertTrue(candidates.contains(3));
	assertTrue(candidates.contains(4));
	assertTrue(candidates.contains(8));
	assertTrue(candidates.contains(9));
	assertEquals(4, candidates.size());

    	candidates = puzzle.getCell(7, 7).getCandidates();
	assertTrue(candidates.contains(3));
	assertTrue(candidates.contains(8));
	assertEquals(2, candidates.size());

	candidates = puzzle.getCell(7, 8).getCandidates();
	assertTrue(candidates.contains(3));
	assertTrue(candidates.contains(8));
	assertTrue(candidates.contains(9));
	assertEquals(3, candidates.size());
    }

    @Test
    public void testXWingColumn() {
	Puzzle puzzle = new Puzzle();
	puzzle.setValue(7, 0, 9);
	puzzle.setValue(8, 0, 4);

	puzzle.setValue(0, 1, 7);
	puzzle.setValue(1, 1, 6);
	puzzle.setValue(3, 1, 9);
	puzzle.setValue(4, 1, 1);
	puzzle.setValue(7, 1, 5);

	puzzle.setValue(1, 2, 9);
	puzzle.setValue(5, 2, 2);
	puzzle.setValue(7, 2, 8);
	puzzle.setValue(8, 2, 1);

	puzzle.setValue(1, 3, 7);
	puzzle.setValue(4, 3, 5);
	puzzle.setValue(7, 3, 1);
	
	puzzle.setValue(3, 4, 7);
	puzzle.setValue(5, 4, 9);
	
	puzzle.setValue(1, 5, 8);
	puzzle.setValue(4, 5, 3);
	puzzle.setValue(5, 5, 1);
	puzzle.setValue(7, 5, 6);
	puzzle.setValue(8, 5, 7);

	puzzle.setValue(0, 6, 2);
	puzzle.setValue(1, 6, 4);
	puzzle.setValue(3, 6, 1);
	puzzle.setValue(7, 6, 7);

	puzzle.setValue(1, 7, 1);
	puzzle.setValue(4, 7, 9);
	puzzle.setValue(7, 7, 4);
	puzzle.setValue(8, 7, 5);

	puzzle.setValue(0, 8, 9);
	puzzle.setValue(6, 8, 1);
	puzzle.updateCandidates();

	new HumanLikeSolver().processXWing(puzzle);
	
	Set<Integer> candidates = puzzle.getCell(1, 4).getCandidates();
	assertTrue(candidates.contains(3));
	assertTrue(candidates.contains(5));
	assertEquals(2, candidates.size());

	candidates = puzzle.getCell(2, 4).getCandidates();
	assertTrue(candidates.contains(1));
	assertTrue(candidates.contains(3));
	assertTrue(candidates.contains(4));
	assertTrue(candidates.contains(5));
	assertTrue(candidates.contains(6));
	assertEquals(5, candidates.size());

	candidates = puzzle.getCell(6, 4).getCandidates();
	assertTrue(candidates.contains(3));
	assertTrue(candidates.contains(4));
	assertTrue(candidates.contains(5));
	assertTrue(candidates.contains(8));
	assertEquals(4, candidates.size());

	candidates = puzzle.getCell(8, 4).getCandidates();
	assertTrue(candidates.contains(3));
	assertTrue(candidates.contains(8));
	assertEquals(2, candidates.size());

	candidates = puzzle.getCell(3, 8).getCandidates();
	assertTrue(candidates.contains(3));
	assertTrue(candidates.contains(4));
	assertTrue(candidates.contains(5));
	assertTrue(candidates.contains(6));
	assertTrue(candidates.contains(8));
	assertEquals(5, candidates.size());

	candidates = puzzle.getCell(8, 8).getCandidates();
	assertTrue(candidates.contains(3));
	assertTrue(candidates.contains(6));
	assertTrue(candidates.contains(8));
	assertEquals(3, candidates.size());
    }
}
