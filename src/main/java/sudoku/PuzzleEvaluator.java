package sudoku;

public class PuzzleEvaluator {
    private Puzzle puzzle;
    private HumanLikeSolver solver;
    
    public PuzzleEvaluator(Puzzle puzzle) {
	this.puzzle = (Puzzle) puzzle.clone();
	solver = new HumanLikeSolver();
    }
    
    public void grade() {
	puzzle.updateCandidates();
	
	boolean updated;
	
	do {
	    updated = false;

	    while (solver.processSingleCandidates(puzzle)) {
		updated = true;
	    }

	    while (solver.processNakedSets(puzzle)) {
		updated = true;
	    }
	    
	    System.out.print("s");
	} while (updated);

	System.out.println();
	System.out.println(puzzle);

    }
}
