package sudoku.solver;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

import sudoku.*;

public class SingleChainProcessor {
    private PuzzleEvaluator puzzleEvaluator;
    
    public SingleChainProcessor(PuzzleEvaluator puzzleEvaluator) {
	this.puzzleEvaluator = puzzleEvaluator;
    }

    public SingleChainProcessor() {
	this(null);
    }

    public boolean process(Puzzle puzzle) {
	for (int candidate = 1; candidate <= Puzzle.NUMBER_OF_VALUES; ++candidate) {
	    Set<Cell> processed = new HashSet<>();
	    
	    for (int y = 0; y < Puzzle.SIDE_LENGTH; ++y) {
		for (int x = 0; x < Puzzle.SIDE_LENGTH; ++x) {
		    List<ChainNode> chain = assembleChain(puzzle, puzzle.getCell(x, y), processed, candidate);

		    twiceInUnit(chain, candidate);
		    oppositeInUnit(puzzle, chain, candidate);
		    twoColorsElsewhere(puzzle, chain, candidate);
		}
	    }

	    processed.clear();
	}

	return false;
    }

    private boolean twiceInUnit(List<ChainNode> chain, int candidate) {
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
	    for (ChainNode node : chain) {
		if (node.color == invalidColor) {
		    node.cell.getCandidates().remove(candidate);
		}
	    }
	}

	return false;
    }

    private boolean oppositeInUnit(Puzzle puzzle, List<ChainNode> chain, int candidate) {
	List<Cell> cells = new ArrayList<>();
	for (ChainNode node : chain) {
	    cells.add(node.cell);
	}

	for (int i = 0; i < chain.size(); ++i) {
	    for (int j = i + 1; j < chain.size(); ++j) {
		if (shareRow(chain.get(i).cell, chain.get(j).cell) && chain.get(i).color != chain.get(j).color) {
		    for (Cell cell : puzzle.findRow(chain.get(i).cell.getY(), true)) {
			if (! cells.contains(cell)) {
			    cell.getCandidates().remove(candidate);
			}
		    }
		}

		if (shareColumn(chain.get(i).cell, chain.get(j).cell) && chain.get(i).color != chain.get(j).color) {
		    for (Cell cell : puzzle.findColumn(chain.get(i).cell.getX(), true)) {
			if (! cells.contains(cell)) {
			    cell.getCandidates().remove(candidate);
			}
		    }
		}

		if (shareSquare(chain.get(i).cell, chain.get(j).cell) && chain.get(i).color != chain.get(j).color) {
		    for (Cell cell : puzzle.findSquare(chain.get(i).cell.getSquareX(), chain.get(i).cell.getSquareY(), true)) {
			if (! cells.contains(cell)) {
			    cell.getCandidates().remove(candidate);
			}
		    }
		}
	    }
	}

	return false;
    }

    private boolean twoColorsElsewhere(Puzzle puzzle, List<ChainNode> chain, int candidate) {
	List<Cell> cells = new ArrayList<>();
	for (ChainNode node : chain) {
	    cells.add(node.cell);
	}


	for (int i = 0; i < chain.size(); ++i) {
	    for (int j = i + 1; j < chain.size(); ++j) {
		if (chain.get(i).color != chain.get(j).color) {
		    Cell intersection = puzzle.getCell(chain.get(i).cell.getX(), chain.get(j).cell.getY());

		    if ( ! cells.contains(intersection) && intersection.getCandidates().contains(candidate)) {
			intersection.getCandidates().remove(candidate);
		    }
		    
		    intersection = puzzle.getCell(chain.get(j).cell.getX(), chain.get(i).cell.getY());
		    if ( ! cells.contains(intersection) && intersection.getCandidates().contains(candidate)) {
			intersection.getCandidates().remove(candidate);
		    }
		}
	    }
	}

	return false;
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
