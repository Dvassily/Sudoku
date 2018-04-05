package sudoku.solver;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import sudoku.*;
import sudoku.util.SolverHelper;
import static sudoku.Strategy.*;


// TODO: Implements ISolver
public class HumanLikeSolver {
    private PuzzleEvaluator puzzleEvaluator;
    
    public HumanLikeSolver(PuzzleEvaluator puzzleEvaluator) {
	this.puzzleEvaluator = puzzleEvaluator;
    }

    public HumanLikeSolver() {
	this(null);
    }

    public boolean processSingleCandidates(Puzzle puzzle) {
	boolean found = false;
	
	for (int i = 0; i < Puzzle.SIDE_LENGTH; ++i) {
	    for (int j = 0; j < Puzzle.SIDE_LENGTH; ++j) {
		Set<Integer> cellCandidates = puzzle.getCell(j, i).getCandidates();
		
		if (cellCandidates.size() == 1) {
		    int value = cellCandidates.iterator().next();
		    puzzle.setValue(j, i, value);
		    SolverHelper.removeCandidates(puzzle, j, i, value);
		    found = true;

		    if (puzzleEvaluator != null) {
			puzzleEvaluator.incrementScore(SINGLE_CANDIDATE);
		    }
		}
	    }
	}

	return found;
    }

    public boolean processNakedSets(Puzzle puzzle) {
	return new NakedSetProcessor(puzzleEvaluator).process(puzzle);
    }

    public boolean processHiddenSets(Puzzle puzzle) {
	return new HiddenSetProcessor(puzzleEvaluator).process(puzzle);	
    }

        // TODO: Use constant for 3
    public boolean processBoxLineReduction(Puzzle puzzle) {
	return new BoxLineReductionProcessor(puzzleEvaluator).process(puzzle);	
    }

    public boolean processPointingPairs(Puzzle puzzle) {
	return new PointingPairProcessor(puzzleEvaluator).process(puzzle);	
    }    

    public boolean processXWing(Puzzle puzzle) {
	return new XWingProcessor(puzzleEvaluator).process(puzzle);	
    }    

    public boolean processSingleChains(Puzzle puzzle) {
	return new SingleChainProcessor(puzzleEvaluator).process(puzzle);	
    }
    
    public boolean processYWing(Puzzle puzzle) {
	return new YWingProcessor(puzzleEvaluator).process(puzzle);	
    }    

}
