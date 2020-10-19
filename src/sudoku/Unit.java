
package sudoku;

import java.util.List;

/**
 *
 * @author vmatheis
 */
public class Unit {
    private List<Cell> cells;
    
    public Unit(List<Cell> cells){
        this.cells = cells;
    }
      
    public void reducePossibleValues(){
        
    }
    
    public boolean isCorrect(){
        return false;
    }
    
    public boolean tryToSelectValue(){
        return false;
    }

    @Override
    public String toString() {
        return "Cells: " + cells;
    }
    
    
}
