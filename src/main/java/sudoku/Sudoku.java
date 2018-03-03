package sudoku;

import java.util.List;

public class Sudoku
{
    public static void main( String[] args )
    {
	System.out.println("Puzzle : ");
	Puzzle puzzle = new PuzzleGenerator().generate();
        System.out.println(puzzle);
	System.out.println("Evaluation : ");
	new PuzzleEvaluator(puzzle).grade();
	System.out.println("Solution : ");
	Puzzle solution = new Solver().solve(puzzle);
	System.out.println(solution);
    }
}
