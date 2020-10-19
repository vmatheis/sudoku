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
public class Sudo implements ISodukoSolver {

    private int[][] sudoku;
    int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    List<Integer> col = new ArrayList<>();
    List<Integer> row = new ArrayList<>();

    public Sudo() {

    }

//    public static void main(String[] args) {
//        Sudo ss = new Sudo();
//        ss.readSudoku(new File("1_sudoku_level1.csv"));
//        boolean bool = ss.solve(ss.sudoku);
//        if (bool) {
//            for (int i = 0; i < ss.sudoku.length; i++) {
//                for (int j = 0; j < ss.sudoku[i].length; j++) {
//                    System.out.print(ss.sudoku[i][j] + " ");
//                }
//                System.out.println();
//            }
//        }
//    }

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
        return checkSudo(rawSudoku);
    }

    @Override
    public int[][] solveSudoku(int[][] rawSudoku) {
        return new int[0][0]; // delete this line!
    }

    @Override
    public int[][] solveSudokuParallel(int[][] rawSudoku) {
        // implement this method
        return new int[0][0]; // delete this line!
    }

    // add helper methods here if necessary

    public boolean solve(int[][] arr) {
        if (!checkSudoku(arr)) {
            finishSudo(arr);
            return false;
        }

        boolean solved = solve(arr, 0, 0);
        finishSudo(arr);
        return solved;
    }

    private boolean checkSudo(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[i][j] > 0) {
                    int temp = arr[i][j];
                    arr[i][j] = 0;
                    if (Arrays.binarySearch(getPossible(arr, j, i), temp) < 0) {
                        arr[i][j] = temp;
                        return false;
                    }
                    arr[i][j] = temp * -1;
                }
            }
        }
        return true;
    }

    private boolean solve(int[][] arr, int y, int x) {
        int[] array = null;
        int x2 = x + 1;
        int y2 = y;
        if (arr[x][y] == 0) {
            array = getPossible(arr, y, x);
            if (array.length == 0) {
                return false;
            }
            arr[x][y] = array[0];
        }
        if (x2 >= arr.length) {
            x2 = 0;
            y2++;
        }
        if (y2 >= arr[x].length) {
            return true;
        }

        while (!solve(arr, y2, x2)) {
            if (array == null) {
                return false;
            } else {
                array = remove(arr[x][y], array);
                if (array.length == 0) {
                    arr[x][y] = 0;
                    return false;
                }
                arr[x][y] = array[0];
            }
        }
        return true;
    }

    private int[] remove(int number, int[] arr) {
        int[] array = new int[arr.length - 1];
        for (int i = 0, j = 0; i < arr.length; i++, j++) {
            if (arr[i] != number) {
                array[j] = arr[i];
            } else {
                j--;
            }
        }
        return array;
    }

    private int[] getPossible(int[][] arr, int y, int x) {
        List<Integer> list = new ArrayList<>();
        int startX = (int) (x / 3) * 3;
        int startY = (int) (y / 3) * 3;
        for (int i = 1; i < 10; i++) {
            list.add(i);
        }

        for (int i = 0; i < arr.length; i++) {
            list.remove(new Integer(Math.abs(arr[x][i])));
            list.remove(new Integer(Math.abs(arr[i][y])));
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                list.remove(new Integer(Math.abs(arr[i + startX][j + startY])));
            }
        }

        int[] val = new int[list.size()];
        for (int i = 0; i < val.length; i++) {
            val[i] = list.get(i);
        }
        return val;
    }

    private void finishSudo(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[i][j] < 0) {
                    arr[i][j] = arr[i][j] * -1;
                }
            }
        }
    }
}
