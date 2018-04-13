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

	Puzzle solution1 = (Puzzle) puzzle.clone();
	int grade = solver.solve(solution1);
	System.out.println("grade : " + grade);
	System.out.println(solution1);
	
	System.out.println("Solution : ");
	Puzzle solution2 = new BacktrackingSolver().solve(puzzle, false).get(0);
	System.out.println(solution2);
    }
}
