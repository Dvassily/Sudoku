package sudoku.solver;

import java.util.List;

import sudoku.*;

public interface IStrategySolver {
    public List<SolverStep> solve(Puzzle puzzle);
}
