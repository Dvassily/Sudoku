package sudoku.solver;

import java.util.List;
import java.util.ArrayList;

public class Solver {
    private List<IStrategySolver> solvers;
    private boolean evaluation = false;
    
    private Solver(List<IStrategySolver> solvers, boolean evaluation) {
	this.solvers = solvers;
	this.evaluation = evaluation;
    }

    public int solve() {
	return 0;
    }
    
    public class SolverBuilder {
	private List<IStrategySolver> solvers = new ArrayList<>();
	private boolean evaluation = false;
	
	public SolverBuilder addSolver(IStrategySolver solver) {
	    solvers.add(solver);
	    return this;
	}

	public SolverBuilder withEvaluation() {
	    evaluation = true;
	    return this;
	}

	public Solver build() {
	    return new Solver(solvers, evaluation);
	}
    }
}
