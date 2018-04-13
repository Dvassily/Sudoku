package sudoku;

import java.util.List;

import sudoku.solver.BacktrackingSolver;
import sudoku.solver.*;

public class Sudoku
{
    public static void main( String[] args )
    {
	System.out.println("Puzzle : ");
	Puzzle puzzle = new PuzzleGenerator().generate(22);
        System.out.println(puzzle);
	
	System.out.println("Evaluation : ");
	Solver solver = new Solver.SolverBuilder()
	    .addSolver(new NakedSetProcessor())
	    .addSolver(new HiddenSetProcessor())
	    .addSolver(new BoxLineReductionProcessor())
	    .addSolver(new PointingPairTripleProcessor())
	    .addSolver(new XWingProcessor())
	    .addSolver(new SingleChainProcessor())
	    .addSolver(new YWingProcessor())
	    .build();

	int grade = solver.solve((Puzzle) puzzle.clone());
	System.out.println("grade : " + grade);
	
	System.out.println("Solution : ");
	Puzzle solution = new BacktrackingSolver().solve(puzzle, false).get(0);
	System.out.println(solution);
    }
}
