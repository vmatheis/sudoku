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
public class MyThreadRows implements Callable<Boolean> {

    int[][] arr;

    public MyThreadRows(int[][] arr) {
        this.arr = arr;
    }

    public List<Integer> fillList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            list.add(i);
        }
        return list;
    }

    @Override
    public Boolean call() throws Exception {
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

}
