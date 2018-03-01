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

	while (solver.searchSingleCandidates(puzzle, candidates)) {
	    System.out.print("s");
	}

	System.out.println();
	System.out.println(puzzle);

    }
}
