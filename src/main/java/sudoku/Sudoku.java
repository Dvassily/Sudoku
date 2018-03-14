package sudoku;

import java.util.List;

import sudoku.solver.Solver;

public class Sudoku
{
    public static void main( String[] args )
    {
	System.out.println("Puzzle : ");
	Puzzle puzzle = new PuzzleGenerator().generate(49);
        System.out.println(puzzle);
	
	System.out.println("Evaluation : ");
	PuzzleEvaluator puzzleEvaluator = new PuzzleEvaluator(puzzle);
	puzzleEvaluator.grade();
	System.out.println("grade : " + puzzleEvaluator.getScore());
	
	System.out.println("Solution : ");
	Puzzle solution = new Solver().solve(puzzle);
	System.out.println(solution);
    }
}
