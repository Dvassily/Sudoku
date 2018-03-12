package sudoku.util;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import sudoku.*;

public class SolverHelper {
    public static Set<Set<Cell>> powerSet(Set<Cell> cells) {
	Set<Set<Cell>> sets = new HashSet<Set<Cell>>();

	if (cells.size() == 0) {
	    sets.add(new HashSet<Cell>());
	    return sets;
	}

	List<Cell> cellsList = new ArrayList<Cell>(cells);
	Cell head = cellsList.get(0);
	for (Set<Cell> subset : powerSet(new HashSet<Cell>(cellsList.subList(1, cells.size())))) {
	    Set<Cell> set = new HashSet<>();
	    set.add(head);
	    set.addAll(subset);
	    sets.add(set);
	    sets.add(subset);
	}

	return sets;
    }

    public static void removeCandidates(Puzzle puzzle, int x, int y, int value) {
	Set<Cell> cellsInRegion = new HashSet<>();
	puzzle.getCell(x, y).getCandidates().clear();

	for (Cell cell : puzzle.findCellsInRegion(x, y)) {
	    cell.getCandidates().remove(value);
	}
    }

}
