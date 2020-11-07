/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author vmatheis
 */
public class MyThreadQuads implements Callable<Boolean> {

    int[][] arr;

    public MyThreadQuads(int[][] arr) {
        this.arr = arr;
    }

    public List<Integer> fillList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            list.add(i);
        }
        return list;
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

    @Override
    public Boolean call() throws Exception {
        for (int rows = 0; rows <= 6; rows += 3) {
            for (int col = 0; col <= 6; col += 3) {
                if (subQuad(arr, rows, col)) {
                    return true;
                }
            }
        }
        return false;
    }

}
