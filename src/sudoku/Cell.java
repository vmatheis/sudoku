/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.List;

/**
 *
 * @author vmatheis
 */
public class Cell {
    private  int selectedValue;
    private List<Integer> possibleValues;

    public Cell() {
    }

    public Cell(int selectedValue) {
        this.selectedValue = selectedValue;
    }
 
    public boolean hasSelectedValue(){
        return false;
    }
    
    
}
