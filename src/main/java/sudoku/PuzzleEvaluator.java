package sudoku;

public class PuzzleEvaluator {
    private Puzzle puzzle;
    private HumanLikeSolver solver;
    
    public PuzzleEvaluator(Puzzle puzzle) {
	this.puzzle = (Puzzle) puzzle.clone();
	solver = new HumanLikeSolver();
    }
    
    public void grade() {
	CandidateList candidates = new CandidateList(puzzle);
	boolean updated;
	
	do {
	    updated = false;
	    
	    updated |= solver.searchSingleCandidates(puzzle, candidates);
	    updated |= solver.searchNakedSets(puzzle, candidates);
	    
	    System.out.print("s");
	} while (updated);

	System.out.println();
	System.out.println(puzzle);

    }
}
