package sudoku;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

// TODO: Implements ISolver
public class HumanLikeSolver {
    public boolean searchSingleCandidates(Puzzle puzzle, CandidateList candidates) {
	boolean found = false;
	
	for (int i = 0; i < Puzzle.sideLength; ++i) {
	    for (int j = 0; j < Puzzle.sideLength; ++j) {
		Set<Integer> cellCandidates = candidates.getCandidates(j, i);
		if (cellCandidates.size() == 1) {
		    found = true;
		    int value = cellCandidates.iterator().next();
		    puzzle.setValue(j, i, value);
		    candidates.update(puzzle, j, i, value);
		    System.out.println(candidates);
		    System.out.println(puzzle);
		}
	    }
	}

	return found;
    }

    public boolean searchNakedSets(Puzzle puzzle, CandidateList candidates) {
	return false;
	// List<>
	// for (int i = 0; i < Puzzle.sideLength; ++i) {
	//     for (int j = 0; j < Puzzle.sideLength; ++j) {
		
	//     }
	// }
    }
}
