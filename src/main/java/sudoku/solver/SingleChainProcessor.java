package sudoku.solver;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

import sudoku.*;
import static sudoku.Strategy.*;
import static sudoku.util.SolverHelper.visibleCells;

public class SingleChainProcessor {
    public List<SolverStep> process(Puzzle puzzle) {
	List<SolverStep> steps = new ArrayList<>();
	
	for (int candidate = 1; candidate <= Puzzle.NUMBER_OF_VALUES; ++candidate) {
	    Set<Cell> processed = new HashSet<>();
	    
	    for (int y = 0; y < Puzzle.SIDE_LENGTH; ++y) {
		for (int x = 0; x < Puzzle.SIDE_LENGTH; ++x) {
		    List<ChainNode> chain = assembleChain(puzzle, puzzle.getCell(x, y), processed, candidate);

		    steps.addAll(twiceInUnit(chain, candidate));
		    steps.addAll(twoColorsElsewhere(puzzle, chain, candidate));
		}
	    }

	    processed.clear();
	}

	return steps;
    }

    private List<SolverStep> twiceInUnit(List<ChainNode> chain, int candidate) {
	List<SolverStep> steps = new ArrayList<>();
	int invalidColor = -1;
	
	for (int i = 0; i < chain.size(); ++i) {
	    for (int j = i + 1; j < chain.size(); ++j) {
		if (shareUnit(chain.get(i).cell, chain.get(j).cell) && chain.get(i).color == chain.get(j).color) {
		    invalidColor = chain.get(i).color;
		}
	    }
	}

	// TODO: Throw exception if two colors are invalids
	if (invalidColor != -1) {
	    SolverStep step = new SolverStep(SINGLE_CHAIN_TWICE_IN_UNIT);
	    
	    for (ChainNode node : chain) {
		if (node.color == invalidColor) {
		    if (node.cell.getCandidates().contains(candidate)) {
			step.removeCandidate(node.cell, candidate);
		    }
		}
	    }

	    if (step.getRemovals().size() > 0) {
		steps.add(step);
	    }
	}

	return steps;
    }

    private List<SolverStep> twoColorsElsewhere(Puzzle puzzle, List<ChainNode> chain, int candidate) {
	List<SolverStep> steps = new ArrayList<>();
	
	List<Cell> cells = new ArrayList<>();
	for (ChainNode node : chain) {
	    cells.add(node.cell);
	}


	for (int i = 0; i < chain.size(); ++i) {
	    for (int j = i + 1; j < chain.size(); ++j) {
		if (chain.get(i).color != chain.get(j).color) {
		    List<Cell> intersection = new ArrayList<>();
		    intersection.addAll(visibleCells(puzzle, chain.get(i).cell));
		    intersection.retainAll(visibleCells(puzzle, chain.get(j).cell));
		    intersection.removeAll(cells);
		    
		    SolverStep step = new SolverStep(SINGLE_CHAIN_TWO_COLORS_ELSEWHERE);
		    
		    for (Cell cell : intersection) {
			if (cell.getCandidates().contains(candidate)) {
			    step.removeCandidate(cell, candidate);
			}
		    }

		    if (step.getRemovals().size() > 0) {
			steps.add(step);
		    }
		}
	    }
	}

	return steps;
    }
    
    private List<ChainNode> assembleChain(Puzzle puzzle, Cell cell, Set<Cell> processed, int candidate) {
	List<ChainNode> chain = new ArrayList<>();
	Queue<ChainNode> queue = new LinkedList<>();

	if (cell.getCandidates().contains(candidate) && ! processed.contains(cell)) {
	    queue.add(new ChainNode(cell, 0));
	    processed.add(cell);
	}

	while (! queue.isEmpty()) {
	    ChainNode node = queue.poll();

	    chain.add(node);
	    
	    List<Cell> strongLinks = findStrongLinks(puzzle, node.cell, candidate);
	    for (Cell c : strongLinks) {
		if (! processed.contains(c)) {
		    processed.add(c);
		    queue.add(new ChainNode(c, (node.color == 0)? 1 : 0));
		    
		}
	    }
	}

	return chain;
    }

    private List<Cell> findStrongLinks(Puzzle puzzle, Cell cell, int candidate) {
	List<Cell> strongLinks = new ArrayList<>();
	List<Cell> candidateCells = new ArrayList<>();
	    
	for (Cell c : puzzle.findRow(cell.getY(), true)) {
	    if (c != cell && c.getCandidates().contains(candidate)) {
		candidateCells.add(c);
	    }
	}

	if (candidateCells.size() == 1) {
	    strongLinks.add(candidateCells.get(0));
	}

	candidateCells.clear();

	for (Cell c : puzzle.findColumn(cell.getX(), true)) {
	    if (c != cell && c.getCandidates().contains(candidate)) {
		candidateCells.add(c);
	    }
	}

	if (candidateCells.size() == 1) {
	    strongLinks.add(candidateCells.get(0));
	}

	candidateCells.clear();
	
	for (Cell c : puzzle.findSquare(cell.getSquareX(), cell.getSquareY(), true)) {
	    if (c != cell && c.getCandidates().contains(candidate)) {
		candidateCells.add(c);
	    }
	}

	if (candidateCells.size() == 1 && ! strongLinks.contains(candidateCells.get(0))) {
	    strongLinks.add(candidateCells.get(0));
	}

	return strongLinks;
    }

    private static boolean shareRow(Cell cell1, Cell cell2) {
	return cell1.getY() == cell2.getY();
    }

    private static boolean shareColumn(Cell cell1, Cell cell2) {
	return cell1.getX() == cell2.getX();
    }

    private static boolean shareSquare(Cell cell1, Cell cell2) {
	return cell1.getSquareX() == cell2.getSquareX() && cell1.getSquareY() == cell2.getSquareY();
    }

    private static boolean shareUnit(Cell cell1, Cell cell2) {
	return shareRow(cell1, cell2) || shareColumn(cell1, cell2) || shareSquare(cell1, cell2);
    }
    
    private class ChainNode {
	public Cell cell;
	public int color;

	public ChainNode(Cell cell, int color) {
	    this.cell = cell;
	    this.color = color;
	}

	@Override
	public String toString() {
	    return cell + "/c=" + color + "/";
	}
    }
}
