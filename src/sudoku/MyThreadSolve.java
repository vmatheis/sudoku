/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.concurrent.Callable;

/**
 *
 * @author vmatheis
 */
public class MyThreadSolve implements Callable<Boolean> {

    int[][] arr;

    public MyThreadSolve(int[][] arr) {
        this.arr = arr;
    }

    public boolean checkNumberInSudoku(int[][] arr, int row, int col, int number) {
        boolean result = false;
        for (int i = 0; i < arr.length; i++) {
            if (arr[row][i] == number) {
                result = true;
            }
        }
        if (!result) {
            return false;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i][col] == number) {
                result = true;
            }
        }
        if (!result) {
            return false;
        }
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

    @Override
    public Boolean call() throws Exception {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[i][j] == 0) {
                    for (int number = 1; number <= arr.length; number++) {
                        if (checkNumberInSudoku(arr, i, j, number)) {
                            arr[i][j] = number;
                            if (call()) {
                                return true;
                            } else {
                                arr[i][j] = 0;
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
