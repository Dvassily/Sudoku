package sudoku.solver;

import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

import sudoku.Puzzle;

public class SingleChainProcessorTest {
    @Test
    public void testTwiceInUnit() {
	Puzzle puzzle = new Puzzle();
	puzzle.load("123...587..58172399873..164.51..847339.75.6187.81..925.765..89153..8174681..7.352");

	puzzle.updateCandidates();

	new HumanLikeSolver().processSingleChains(puzzle);

	Set<Integer> candidates = puzzle.getCell(0, 3).getCandidates();
	assertTrue(candidates.contains(6));
	assertEquals(1, candidates.size());	
	
	candidates = puzzle.getCell(3, 3).getCandidates();
	assertTrue(candidates.contains(6));
	assertTrue(candidates.contains(9));
	assertEquals(2, candidates.size());
	
	candidates = puzzle.getCell(2, 4).getCandidates();
	assertTrue(candidates.contains(2));
	assertTrue(candidates.contains(4));
	assertEquals(2, candidates.size());
	
	candidates = puzzle.getCell(5, 4).getCandidates();
	assertTrue(candidates.contains(4));
	assertEquals(1, candidates.size());
	
	candidates = puzzle.getCell(0, 6).getCandidates();
	assertTrue(candidates.contains(2));
	assertTrue(candidates.contains(4));
	assertEquals(2, candidates.size());
	
    	candidates = puzzle.getCell(2, 7).getCandidates();
	assertTrue(candidates.contains(9));
	assertEquals(1, candidates.size());
	
	candidates = puzzle.getCell(3, 7).getCandidates();
	assertTrue(candidates.contains(2));
	assertTrue(candidates.contains(9));
	assertEquals(2, candidates.size());
    }

    @Test
    public void testOppositeInUnit() {
	Puzzle puzzle = new Puzzle();
	puzzle.load("..85.21.335...12.8.21.3..5.56324.7.14821.753.179.53..2.3..2581.8.731..25215.843..");
	puzzle.updateCandidates();

	new HumanLikeSolver().processSingleChains(puzzle);

	Set<Integer> candidates = puzzle.getCell(7, 1).getCandidates();
	assertTrue(candidates.contains(6));
	assertTrue(candidates.contains(7));
	assertTrue(candidates.contains(9));
	assertEquals(3, candidates.size());

	candidates = puzzle.getCell(6, 2).getCandidates();
	assertTrue(candidates.contains(6));
	assertTrue(candidates.contains(9));
	assertEquals(2, candidates.size());
    }

    @Test
    public void testTwoColorsElsewhere() {
	Puzzle puzzle = new Puzzle();
	puzzle.load("2...41..64..6.2.1..16.9...43..12964.142.6.59..695.4..158421637992.4.81656.19..482");

	puzzle.updateCandidates();

	new HumanLikeSolver().processSingleChains(puzzle);

	Set<Integer> candidates = puzzle.getCell(4, 1).getCandidates();
	assertTrue(candidates.contains(5));
	assertTrue(candidates.contains(7));
	assertTrue(candidates.contains(8));
	assertEquals(3, candidates.size());
    }
}
