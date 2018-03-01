package sudoku;

import java.util.List;

public class Sudoku
{
    public static void main( String[] args )
    {
	Puzzle puzzle = new PuzzleGenerator().generate();
        System.out.println(puzzle);
	Puzzle solution = new Solver().solve(puzzle);
	System.out.println(solution);
	new PuzzleEvaluator(puzzle).grade();
    }
}
