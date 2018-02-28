package sudoku;

import java.util.List;

public class Sudoku
{
    public static void main( String[] args )
    {
	Puzzle puzzle = new PuzzleGenerator().generate();
        System.out.println(puzzle);
	Puzzle solution = new Solver(puzzle).solve();
	System.out.println(solution);
    }
}
