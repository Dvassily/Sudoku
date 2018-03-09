package sudoku;

import java.util.Set;
import java.util.HashSet;
import org.junit.Test;
import static org.junit.Assert.*;

public class HumanLikeSolverTest {
    // TODO: Example grid : http://www.sudokuwiki.org/Naked_Candidates
    @Test
    public void testProcessNakedSets() {
	Puzzle puzzle = new Puzzle();
	puzzle.setValue(0, 0, 4);
	puzzle.setValue(6, 0, 9);
	puzzle.setValue(7, 0, 3);
	puzzle.setValue(8, 0, 8);
	puzzle.setValue(1, 1, 3);
	puzzle.setValue(2, 1, 2);
	puzzle.setValue(4, 1, 9);
	puzzle.setValue(5, 1, 4);
	puzzle.setValue(6, 1, 1);
	puzzle.setValue(1, 2, 9);
	puzzle.setValue(2, 2, 5);
	puzzle.setValue(3, 2, 3);
	puzzle.setValue(6, 2, 2);
	puzzle.setValue(7, 2, 4);
	puzzle.setValue(0, 3, 3);
	puzzle.setValue(1, 3, 7);
	puzzle.setValue(3, 3, 6);
	puzzle.setValue(5, 3, 9);
	puzzle.setValue(8, 3, 4);
	puzzle.setValue(0, 4, 5);
	puzzle.setValue(1, 4, 2);
	puzzle.setValue(2, 4, 9);
	puzzle.setValue(5, 4, 1);
	puzzle.setValue(6, 4, 6);
	puzzle.setValue(7, 4, 7);
	puzzle.setValue(8, 4, 3);
	puzzle.setValue(0, 5, 6);
	puzzle.setValue(2, 5, 4);
	puzzle.setValue(3, 5, 7);
	puzzle.setValue(5, 5, 3);
	puzzle.setValue(7, 5, 9);
	puzzle.setValue(0, 6, 9);
	puzzle.setValue(1, 6, 5);
	puzzle.setValue(2, 6, 7);
	puzzle.setValue(5, 6, 8);
	puzzle.setValue(6, 6, 3);
	puzzle.setValue(2, 7, 3);
	puzzle.setValue(3, 7, 9);
	puzzle.setValue(6, 7, 4);
	puzzle.setValue(0, 8, 2);
	puzzle.setValue(1, 8, 4);
	puzzle.setValue(4, 8, 3);
	puzzle.setValue(6, 8, 7);
	puzzle.setValue(8, 8, 9);

	puzzle.updateCandidates();

	// Naked set : { [1, 0], [2, 0] }
	Set<Integer> candidates = puzzle.getCell(1, 0).getCandidates();
	assertTrue(candidates.contains(1));
	assertTrue(candidates.contains(6));
	assertEquals(2, candidates.size());

	candidates = puzzle.getCell(2, 0).getCandidates();
	assertTrue(candidates.contains(1));
	assertTrue(candidates.contains(6));
	assertEquals(2, candidates.size());

	candidates = puzzle.getCell(3, 0).getCandidates();
	assertTrue(candidates.contains(1));
	assertTrue(candidates.contains(2));
	assertTrue(candidates.contains(5));
	assertEquals(3, candidates.size());

	candidates = puzzle.getCell(4, 0).getCandidates();
	assertTrue(candidates.contains(1));
	assertTrue(candidates.contains(2));
	assertTrue(candidates.contains(5));
	assertTrue(candidates.contains(6));
	assertTrue(candidates.contains(7));
	assertEquals(5, candidates.size());

	candidates = puzzle.getCell(5, 0).getCandidates();
	assertTrue(candidates.contains(2));
	assertTrue(candidates.contains(5));
	assertTrue(candidates.contains(6));
	assertTrue(candidates.contains(7));
	assertEquals(4, candidates.size());

	candidates = puzzle.getCell(0, 2).getCandidates();
	assertTrue(candidates.contains(1));
	assertTrue(candidates.contains(7));
	assertTrue(candidates.contains(8));
	assertEquals(3, candidates.size());

	candidates = puzzle.getCell(4, 2).getCandidates();
	assertTrue(candidates.contains(1));
	assertTrue(candidates.contains(6));
	assertTrue(candidates.contains(7));
	assertTrue(candidates.contains(8));
	assertEquals(4, candidates.size());

	// Naked set : { [5, 2], [8, 2] }
	candidates = puzzle.getCell(5, 2).getCandidates();
	assertTrue(candidates.contains(6));
	assertTrue(candidates.contains(7));
	assertEquals(2, candidates.size());

	candidates = puzzle.getCell(8, 2).getCandidates();
	assertTrue(candidates.contains(6));
	assertTrue(candidates.contains(7));
	assertEquals(2, candidates.size());

	// TODO: [4, 8]

	new HumanLikeSolver().processNakedSets(puzzle);

	// Asserts candidates 1 and 6 are removed from cells [3,0], [4,0], [5, 0] and [0, 2],
	//                and 7 and 6 from cell [0, 2]
	
	candidates = puzzle.getCell(3, 0).getCandidates();
	assertTrue(candidates.contains(2));
	assertTrue(candidates.contains(5));
	assertEquals(2, candidates.size());

	candidates = puzzle.getCell(4, 0).getCandidates();
	assertTrue(candidates.contains(2));
	assertTrue(candidates.contains(5));
	assertTrue(candidates.contains(7));
	assertEquals(3, candidates.size());

	candidates = puzzle.getCell(5, 0).getCandidates();
	assertTrue(candidates.contains(2));
	assertTrue(candidates.contains(5));
	assertTrue(candidates.contains(7));
	assertEquals(3, candidates.size());

	candidates = puzzle.getCell(0, 2).getCandidates();
	assertTrue(candidates.contains(8));
	assertEquals(1, candidates.size());
    }

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

	new HumanLikeSolver().processHiddenSets(puzzle);
	candidates = puzzle.getCell(7, 0).getCandidates();

	assertTrue(candidates.contains(6));
	assertTrue(candidates.contains(7));
	assertEquals(2, candidates.size());

	candidates = puzzle.getCell(8, 0).getCandidates();
	assertTrue(candidates.contains(6));
	assertTrue(candidates.contains(7));
	assertEquals(2, candidates.size());
    }

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
	
	new HumanLikeSolver().processPointingPairs(puzzle);

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
	
	new HumanLikeSolver().processPointingPairs(puzzle);

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
