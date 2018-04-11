package sudoku.solver;

import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

import sudoku.*;

public class PointingPairProcessorTest {
    @Test
    public void testPointingPairHorizontal() {
	Puzzle puzzle = new Puzzle();
	puzzle.setValue(1, 0, 1);
	puzzle.setValue(2, 0, 7);
	puzzle.setValue(3, 0, 9);
	puzzle.setValue(5, 0, 3);
	puzzle.setValue(6, 0, 6);

	puzzle.setValue(4, 1, 8);

	puzzle.setValue(0, 2, 9);
	puzzle.setValue(6, 2, 5);
	puzzle.setValue(8, 2, 7);

	puzzle.setValue(1, 3, 7);
	puzzle.setValue(2, 3, 2);
	puzzle.setValue(4, 3, 1);
	puzzle.setValue(6, 3, 4);
	puzzle.setValue(7, 3, 3);

	puzzle.setValue(3, 4, 4);
	puzzle.setValue(5, 4, 2);
	puzzle.setValue(7, 4, 7);

	puzzle.setValue(1, 5, 6);
	puzzle.setValue(2, 5, 4);
	puzzle.setValue(3, 5, 3);
	puzzle.setValue(4, 5, 7);
	puzzle.setValue(6, 5, 2);
	puzzle.setValue(7, 5, 5);

	puzzle.setValue(0, 6, 7);
	puzzle.setValue(2, 6, 1);
	puzzle.setValue(7, 6, 6);
	puzzle.setValue(8, 6, 5);

	puzzle.setValue(4, 7, 3);

	puzzle.setValue(2, 8, 5);
	puzzle.setValue(3, 8, 6);
	puzzle.setValue(5, 8, 1);
	puzzle.setValue(6, 8, 7);
	puzzle.setValue(7, 8, 2);

	puzzle.updateCandidates();
	
	new HumanLikeSolver().processPointingPairsTriples(puzzle);

	Set<Integer> candidates = puzzle.getCell(0, 1).getCandidates();
	assertTrue(candidates.contains(2));
	assertTrue(candidates.contains(4));
	assertTrue(candidates.contains(5));
	assertTrue(candidates.contains(6));
	assertEquals(4, candidates.size());

    	candidates = puzzle.getCell(1, 1).getCandidates();
	assertTrue(candidates.contains(2));
	assertTrue(candidates.contains(4));
	assertTrue(candidates.contains(5));
	assertEquals(3, candidates.size());

    	candidates = puzzle.getCell(2, 1).getCandidates();
	assertTrue(candidates.contains(6));
	assertEquals(1, candidates.size());
    }

    @Test
    public void testPointingPairVertical() {
	Puzzle puzzle = new Puzzle();
	puzzle.setValue(0, 1, 1);
	puzzle.setValue(0, 2, 7);
	puzzle.setValue(0, 3, 9);
	puzzle.setValue(0, 5, 3);
	puzzle.setValue(0, 6, 6);
			   
	puzzle.setValue(1, 4, 8);
			   
	puzzle.setValue(2, 0, 9);
	puzzle.setValue(2, 6, 5);
	puzzle.setValue(2, 8, 7);
			   
	puzzle.setValue(3, 1, 7);
	puzzle.setValue(3, 2, 2);
	puzzle.setValue(3, 4, 1);
	puzzle.setValue(3, 6, 4);
	puzzle.setValue(3, 7, 3);
			   
	puzzle.setValue(4, 3, 4);
	puzzle.setValue(4, 5, 2);
	puzzle.setValue(4, 7, 7);
			   
	puzzle.setValue(5, 1, 6);
	puzzle.setValue(5, 2, 4);
	puzzle.setValue(5, 3, 3);
	puzzle.setValue(5, 4, 7);
	puzzle.setValue(5, 6, 2);
	puzzle.setValue(5, 7, 5);
			   
	puzzle.setValue(6, 0, 7);
	puzzle.setValue(6, 2, 1);
	puzzle.setValue(6, 7, 6);
	puzzle.setValue(6, 8, 5);
			   
	puzzle.setValue(7, 4, 3);
			   
	puzzle.setValue(8, 2, 5);
	puzzle.setValue(8, 3, 6);
	puzzle.setValue(8, 5, 1);
	puzzle.setValue(8, 6, 7);
	puzzle.setValue(8, 7, 2);

	puzzle.updateCandidates();
	
	new HumanLikeSolver().processPointingPairsTriples(puzzle);

	Set<Integer> candidates = puzzle.getCell(1, 0).getCandidates();
	assertTrue(candidates.contains(2));
	assertTrue(candidates.contains(4));
	assertTrue(candidates.contains(5));
	assertTrue(candidates.contains(6));
	assertEquals(4, candidates.size());

    	candidates = puzzle.getCell(1, 1).getCandidates();
	assertTrue(candidates.contains(2));
	assertTrue(candidates.contains(4));
	assertTrue(candidates.contains(5));
	assertEquals(3, candidates.size());

    	candidates = puzzle.getCell(1, 2).getCandidates();
	assertTrue(candidates.contains(6));
	assertEquals(1, candidates.size());
    }
}
