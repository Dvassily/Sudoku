package sudoku;

import sudoku.solver.HumanLikeSolver;

public class PuzzleEvaluator {
    private int score = 0;
    private Puzzle puzzle;
    private HumanLikeSolver solver;
    private static int SINGLE_CANDIDATE_SCORE = 10;
    private static int NAKED_PAIR_SCORE = 20;
    private static int NAKED_TRIPLET_SCORE = 30;
    private static int NAKED_QUADRUPLET_SCORE = 40;
    private static int HIDDEN_PAIR_SCORE = 20;
    private static int HIDDEN_TRIPLET_SCORE = 30;
    private static int HIDDEN_QUADRUPLET_SCORE = 40;
    private static int INTERSECTION_REMOVAL_SCORE = 20;
    private static int X_WING_SCORE = 50;
    private static int SINGLE_CHAIN_TWICE_IN_UNIT_SCORE = 100;
    private static int SINGLE_CHAIN_OPPOSITE_IN_UNIT_SCORE = 110;
    private static int SINGLE_CHAIN_TWO_COLORS_ELSEWHERE_SCORE = 110;
    private static int Y_WING_SCORE = 75;
    
    public PuzzleEvaluator(Puzzle puzzle) {
	this.puzzle = (Puzzle) puzzle.clone();
	solver = new HumanLikeSolver(this);
    }
    
    public void grade() {
	puzzle.updateCandidates();
	
	boolean updated;
	
	do {
	    updated = false;

	    while (solver.processSingleCandidates(puzzle)) {
		updated = true;
	    }

	    while (solver.processHiddenSets(puzzle)) {
	    	updated = true;
	    }
	    
	    while (solver.processNakedSets(puzzle)) {
		updated = true;
	    }

	    while (solver.processBoxLineReduction(puzzle)) {
		updated = true;
	    }

	    while (solver.processPointingPairsTriples(puzzle)) {
		updated = true;
	    }

	    while (solver.processXWing(puzzle)) {
		updated = true;
	    }

	    while (solver.processSingleChains(puzzle)) {
		updated = true;
	    }

	    while (solver.processYWing(puzzle)) {
		updated = true;
	    }
	} while (updated);

	System.out.println();
	System.out.println(puzzle);
    }

    public void incrementScore(Strategy strategy) {
	switch(strategy) {
	case SINGLE_CANDIDATE:
	    score += SINGLE_CANDIDATE_SCORE;
	    break;
	case NAKED_PAIR:
	    score += NAKED_PAIR_SCORE;
	    break;
	case NAKED_TRIPLET:
	    score += NAKED_TRIPLET_SCORE;
	    break;
	case NAKED_QUADRUPLET:
	    score += NAKED_QUADRUPLET_SCORE;
	    break;
	case HIDDEN_PAIR:
	    score += HIDDEN_PAIR_SCORE;
	    break;
	case HIDDEN_TRIPLET:
	    score += HIDDEN_TRIPLET_SCORE;
	    break;
	case HIDDEN_QUADRUPLET:
	    score += HIDDEN_QUADRUPLET_SCORE;
	    break;
	case INTERSECTION_REMOVAL:
	    score += INTERSECTION_REMOVAL_SCORE;
	    break;
	case X_WING:
	    score += X_WING_SCORE;
	    break;
	case SINGLE_CHAIN_TWICE_IN_UNIT:
	    score += SINGLE_CHAIN_TWICE_IN_UNIT_SCORE;
	    break;
	case SINGLE_CHAIN_OPPOSITE_IN_UNIT:
	    score += SINGLE_CHAIN_OPPOSITE_IN_UNIT_SCORE;
	    break;
	case SINGLE_CHAIN_TWO_COLORS_ELSEWHERE:
	    score += SINGLE_CHAIN_TWO_COLORS_ELSEWHERE_SCORE;
	    break;
	case Y_WING:
	    score += Y_WING_SCORE;
	    break;
	default:
	    // TODO: throw exception
	}
    }

    public int getScore() {
	if (puzzle.isCompleted()) {
	    return score;
	}
	
	return -1;
    }
}
