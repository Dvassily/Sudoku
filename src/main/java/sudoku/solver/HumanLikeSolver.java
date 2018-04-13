package sudoku.solver;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

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

    // public boolean processSingleCandidates(Puzzle puzzle) {
    // 	boolean found = false;
	
    // 	for (int i = 0; i < Puzzle.SIDE_LENGTH; ++i) {
    // 	    for (int j = 0; j < Puzzle.SIDE_LENGTH; ++j) {
    // 		Set<Integer> cellCandidates = puzzle.getCell(j, i).getCandidates();
		
    // 		if (cellCandidates.size() == 1) {
    // 		    int value = cellCandidates.iterator().next();
    // 		    puzzle.setValue(j, i, value);
    // 		    SolverHelper.removeCandidates(puzzle, j, i, value);
    // 		    found = true;

    // 		    if (puzzleEvaluator != null) {
    // 			puzzleEvaluator.incrementScore(SINGLE_CANDIDATE);
    // 		    }
    // 		}
    // 	    }
    // 	}

    // 	return found;
    // }

    // public boolean processNakedSets(Puzzle puzzle) {
    // 	List<SolverStep> steps = new NakedSetProcessor().process(puzzle);
	
    // 	for (SolverStep step : steps) {
    // 	    boolean removed = false;
	    
    // 	    for (Map.Entry<Cell, Set<Integer>> removals : step.getRemovals().entrySet()) {
    // 		removed = removals.getKey().getCandidates().removeAll(removals.getValue());
    // 	    }

    // 	    if (removed && puzzleEvaluator != null) {
    // 		puzzleEvaluator.incrementScore(step.getStrategy());
    // 	    }
    // 	}
	
    // 	return (steps.size() > 0)? true : false;
    // }

    // public boolean processHiddenSets(Puzzle puzzle) {
    // 	List<SolverStep> steps = new HiddenSetProcessor().process(puzzle);
	
    // 	for (SolverStep step : steps) {
    // 	    boolean removed = false;
	    
    // 	    for (Map.Entry<Cell, Set<Integer>> removals : step.getRemovals().entrySet()) {
    // 		removed = removals.getKey().getCandidates().removeAll(removals.getValue());
    // 	    }

    // 	    if (removed && puzzleEvaluator != null) {
    // 		puzzleEvaluator.incrementScore(step.getStrategy());
    // 	    }
    // 	}
	
    // 	return (steps.size() > 0)? true : false;
    // }

    //     // TODO: Use constant for 3
    // public boolean processBoxLineReduction(Puzzle puzzle) {
    // 	List<SolverStep> steps = new BoxLineReductionProcessor().process(puzzle);
	
    // 	for (SolverStep step : steps) {
    // 	    boolean removed = false;
	    
    // 	    for (Map.Entry<Cell, Set<Integer>> removals : step.getRemovals().entrySet()) {
    // 		removed = removals.getKey().getCandidates().removeAll(removals.getValue());
    // 	    }

    // 	    if (removed && puzzleEvaluator != null) {
    // 		puzzleEvaluator.incrementScore(step.getStrategy());
    // 	    }
    // 	}
	
    // 	return (steps.size() > 0)? true : false;
    // }

    // public boolean processPointingPairsTriples(Puzzle puzzle) {
    // 	List<SolverStep> steps = new PointingPairTripleProcessor().process(puzzle);
	
    // 	for (SolverStep step : steps) {
    // 	    boolean removed = false;
	    
    // 	    for (Map.Entry<Cell, Set<Integer>> removals : step.getRemovals().entrySet()) {
    // 		removed = removals.getKey().getCandidates().removeAll(removals.getValue());
    // 	    }

    // 	    if (removed && puzzleEvaluator != null) {
    // 		puzzleEvaluator.incrementScore(step.getStrategy());
    // 	    }
    // 	}
	
    // 	return (steps.size() > 0)? true : false;
    // }    

    // public boolean processXWing(Puzzle puzzle) {
    // 	List<SolverStep> steps = new XWingProcessor().process(puzzle);
	
    // 	for (SolverStep step : steps) {
    // 	    boolean removed = false;
	    
    // 	    for (Map.Entry<Cell, Set<Integer>> removals : step.getRemovals().entrySet()) {
    // 		removed = removals.getKey().getCandidates().removeAll(removals.getValue());
    // 	    }

    // 	    if (removed && puzzleEvaluator != null) {
    // 		puzzleEvaluator.incrementScore(step.getStrategy());
    // 	    }
    // 	}
	
    // 	return (steps.size() > 0)? true : false;
    // }    

    // public boolean processSingleChains(Puzzle puzzle) {
    // 	List<SolverStep> steps = new SingleChainProcessor().process(puzzle);
	
    // 	for (SolverStep step : steps) {
    // 	    boolean removed = false;
	    
    // 	    for (Map.Entry<Cell, Set<Integer>> removals : step.getRemovals().entrySet()) {
    // 		removed = removals.getKey().getCandidates().removeAll(removals.getValue());
    // 	    }

    // 	    if (removed && puzzleEvaluator != null) {
    // 		puzzleEvaluator.incrementScore(step.getStrategy());
    // 	    }
    // 	}
	
    // 	return (steps.size() > 0)? true : false;
    // }
    
    // public boolean processYWing(Puzzle puzzle) {
    // 	List<SolverStep> steps = new YWingProcessor().process(puzzle);
	
    // 	for (SolverStep step : steps) {
    // 	    boolean removed = false;
	    
    // 	    for (Map.Entry<Cell, Set<Integer>> removals : step.getRemovals().entrySet()) {
    // 		removed = removals.getKey().getCandidates().removeAll(removals.getValue());
    // 	    }

    // 	    if (removed && puzzleEvaluator != null) {
    // 		puzzleEvaluator.incrementScore(step.getStrategy());
    // 	    }
    // 	}
	
    // 	return (steps.size() > 0)? true : false;
    // }    

}
