package sudoku;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        SudokuSolver ss = new SudokuSolver();
        int[][] input = ss.readSudoku(new File("1_sudoku_level1.csv"));

        System.out.println(">--- ORIGINAL ---");
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input.length; j++) {
                System.out.print(input[i][j] + "|");
            }
            System.out.println("");
        } 
        int[][] output = ss.solveSudoku(input);
        System.out.println(">--- SOLUTION ---");
        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < output.length; j++) {
                System.out.print(output[i][j] + "|");
            }
            System.out.println("");
        }
        System.out.println(">----------------");
        System.out.println("SOLVED    = " + ss.checkSudoku(output));
        System.out.println(">----------------");
        System.out.println(ss.benchmark(output, new File("1_sudoku_level1.csv")));
    }
}
