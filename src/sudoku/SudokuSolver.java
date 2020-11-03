package sudoku;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* Please enter here an answer to task four between the tags:
 * <answerTask4>
 *    Hier sollte die Antwort auf die Aufgabe 4 stehen.
 * </answerTask4>
 */
public class SudokuSolver implements ISodukoSolver {

    private int[][] sudoku;

    public SudokuSolver() {

    }

    @Override
    public final int[][] readSudoku(File file) {
        try {
            sudoku = Files.lines(file.toPath())
                    .map(s -> s.split(";"))
                    .map(x -> new int[]{Integer.parseInt(x[0]),
                Integer.parseInt(x[1]),
                Integer.parseInt(x[2]),
                Integer.parseInt(x[3]),
                Integer.parseInt(x[4]),
                Integer.parseInt(x[5]),
                Integer.parseInt(x[6]),
                Integer.parseInt(x[7]),
                Integer.parseInt(x[8])})
                    .toArray(int[][]::new);

        } catch (IOException e) {
            System.out.println("IOException");
        }
        return sudoku;
    }

    @Override
    public boolean checkSudoku(int[][] rawSudoku) {
        boolean cols = checkCols(rawSudoku);
        if (cols == false) {
            return false;
        }
        boolean rows = checkRows(rawSudoku);
        if (rows == false) {
            return false;
        }
        boolean quad = checkQuad(rawSudoku);
        if (quad == false) {
            return false;
        }
        return true;
    }

    @Override
    public int[][] solveSudoku(int[][] rawSudoku) {
        boolean solved = solve(rawSudoku);
        if (!solved) {
            System.out.println("Sudoku couldn't be solved");
        }
        return rawSudoku;
    }

    @Override
    public int[][] solveSudokuParallel(int[][] rawSudoku) {
        // implement this method
        return new int[0][0]; // delete this line!
    }

    public long benchmark(int[][] rawSudoku, File file) {
        long start = System.currentTimeMillis();
        for (int i = 0; i <= 10; i++) {
            readSudoku(file);
            checkSudoku(rawSudoku);
            solveSudoku(rawSudoku);
        }
        long end = System.currentTimeMillis();
        return (end - start) / 10;
    }

    // add helper methods here if necessary
    
    //befüllt Liste mit möglichen Werten (1-9) des Sudokus 
    public List<Integer> fillList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            list.add(i);
        }
        return list;
    }

    //checkt alle Reihen des Sudokus
    public boolean checkRows(int[][] arr) {
        List<Integer> list = fillList();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (list.contains(arr[i][j])) {
                    list.remove((Integer) (arr[i][j]));
                } else {
                    return false;
                }

            }
            list = fillList();
        }
        return true;
    }

    //checkt alle Spalten des Sudokus
    public boolean checkCols(int[][] arr) {
        List<Integer> list = fillList();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (list.contains(arr[j][i])) {
                    list.remove((Integer) (arr[j][i]));
                } else {
                    return false;
                }
            }
            list = fillList();
        }
        return true;
    }

    //checkt alle 3x3 Quadrate des Sudokus
    public boolean checkQuad(int[][] arr) {
        for (int rows = 0; rows <= 6; rows += 3) {
            for (int col = 0; col <= 6; col += 3) {
                if (subQuad(arr, rows, col)) {
                    return true;
                }
            }
        }
        return false;
    }

    //checkt ein 3x3 Quadrat des Sudokus
    public boolean subQuad(int[][] arr, int row, int col) {
        List<Integer> list = fillList();
        for (int i = row; i < row + 3; i++) {
            for (int j = col; j < col + 3; j++) {
                if (list.contains(arr[i][j])) {
                    list.remove((Integer) (arr[i][j]));
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    //checkt ob eine Nummer in einer Reihe schon vorhanden ist
    public boolean checkNumberAlreadyInRow(int[][] arr, int row, int number) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[row][i] == number) {
                return true;
            }
        }
        return false;
    }

    //checkt ob eine Nummer in einer Spalte schon vorhanden ist
    public boolean checkNumberAlreadyInCol(int[][] arr, int col, int number) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i][col] == number) {
                return true;
            }
        }
        return false;
    }

    //checkt ob eine Nummer in einem 3x3 Quadrat schon vorhanden ist
    public boolean checkNumberAlreadyInQuad(int[][] arr, int row, int col, int number) {
        row = row - row % 3;
        col = col - col % 3;
        for (int i = row; i < row + 3; i++) {
            for (int j = col; j < col + 3; j++) {
                if (arr[i][j] == number) {
                    return true;
                }
            }
        }
        return false;
    }

    //checkt ob eine Nummer in im Sudoku vorkommen kann
    public boolean checkNumberInSudoku(int[][] arr, int row, int col, int number) {
        return !checkNumberAlreadyInCol(arr, col, number) && !checkNumberAlreadyInRow(arr, row, number) && !checkNumberAlreadyInQuad(arr, row, col, number);
    }

    public boolean solve(int[][] rawSudoku) {
        for (int i = 0; i < rawSudoku.length; i++) {
            for (int j = 0; j < rawSudoku.length; j++) {
                if (rawSudoku[i][j] == 0) {
                    for (int number = 1; number <= rawSudoku.length; number++) {
                        if (checkNumberInSudoku(rawSudoku, i, j, number)) {
                            rawSudoku[i][j] = number;
                            if (solve(rawSudoku)) {
                                return true;
                            } else {
                                rawSudoku[i][j] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}
