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
    int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    List<Integer> col = new ArrayList<>();
    List<Integer> row = new ArrayList<>();

    public SudokuSolver() {

    }

    public static void main(String[] args) {
        SudokuSolver ss = new SudokuSolver();
        int[][] arr = ss.readSudoku(new File("1_sudoku_level1.csv"));
        ss.checkSudoku(arr);
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
        rawSudoku = solveRowsSudoku(rawSudoku);
        List<Integer> list = new ArrayList<>();
        List<Integer> doubleList = new ArrayList<>();
        int counter = 0;
        int maxCounter = 1;
        while (maxCounter < 10) {
            for (int i = 0; i < rawSudoku.length; i++) {
                for (int j = counter; j < maxCounter; j++) {
                    if (!list.isEmpty()) {
                        if (list.contains(rawSudoku[i][j])) {
                            doubleList.add(rawSudoku[i][j]);
                        } else {
                            list.add(rawSudoku[j][i]);
                        }
                    } else {
                        list.add(rawSudoku[j][i]);
                    }
                }
            }

            for (int i = 0; i < rawSudoku.length; i++) {
                for (int j = counter; j < maxCounter; j++) {
                    if (doubleList.contains(rawSudoku[i][j])) {
                        rawSudoku[i][j] = 0;
                    }
                }
            }
            maxCounter++;
            counter++;
            list.clear();
            doubleList.clear();
        }
        //rawSudoku = solveColsSudoku(rawSudoku);
        return rawSudoku; // delete this line!
    }

    @Override
    public int[][] solveSudokuParallel(int[][] rawSudoku) {
        // implement this method
        return new int[0][0]; // delete this line!
    }

    public long benchmark(int[][] rawSudoku) {
        long start = System.currentTimeMillis();
        for (int i = 0; i <= 10; i++) {
            readSudoku(new File("1_sudoku_level1.csv"));
            checkSudoku(rawSudoku);
            solveSudoku(rawSudoku);
        }
        long end = System.currentTimeMillis();
        return (end - start) / 10;
    }

    // add helper methods here if necessary
    public List<Integer> fillList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            list.add(i);
        }
        return list;
    }

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

    public boolean checkQuad(int[][] arr) {
        for (int rows = 0; rows <= 6; rows += 3) {
            for (int col = 0; col <= 6; col += 3) {
                if (subQuad(arr, rows, col) == false) {
                    return false;
                }
            }
        }
        return true;
    }

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

    public int[][] solveRowsSudoku(int[][] rawSudoku) {
        List<Integer> list = fillList();
        int counter = 0;
        int maxCount = 1;

        while (maxCount < 10) {
            for (int i = counter; i < maxCount; i++) {
                for (int j = 0; j < rawSudoku.length; j++) {
                    if (list.contains(rawSudoku[i][j])) {
                        list.remove((Integer) (rawSudoku[i][j]));
                    }
                }
            }
            maxCount++;
            counter++;
            for (int i = 0; i < rawSudoku.length; i++) {
                for (int j = 0; j < rawSudoku.length; j++) {
                    if (rawSudoku[i][j] == 0) {
                        if (list.isEmpty()) {
                            break;
                        } else {
                            rawSudoku[i][j] = list.get(list.size() - 1);
                            list.remove(list.size() - 1);
                        }
                    }
                }
            }
            list = fillList();
        }
        return rawSudoku;
    }

    public int[][] solveColsSudoku(int[][] rawSudoku) {
        List<Integer> list = fillList();
        int counter = 0;
        int maxCount = 1;
        while (maxCount < 10) {
            for (int i = 0; i < rawSudoku.length; i++) {
                for (int j = 0; j < rawSudoku.length; j++) {

                }

            }
            for (int i = counter; i < maxCount; i++) {
                for (int j = 0; j < rawSudoku.length; j++) {
                    if (list.contains(rawSudoku[j][i])) {
                        list.remove((Integer) (rawSudoku[j][i]));
                    }
                }
            }
            maxCount++;
            counter++;
            for (int i = 0; i < rawSudoku.length; i++) {
                for (int j = 0; j < rawSudoku.length; j++) {
                    if (rawSudoku[j][i] == 0) {
                        if (list.isEmpty()) {
                            break;
                        } else {
                            rawSudoku[j][i] = list.get(list.size() - 1);
                            list.remove(list.size() - 1);
                        }
                    }
                }
            }
            list = fillList();
        }
        return rawSudoku;
    }
}
