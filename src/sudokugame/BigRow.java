/** required package class namespace */
package sudokugame;

 
/**
 * BigRow.java - Represents a full row in a game of sudoku
 *
 * @author A. McLeod
 * @since Jul. 24, 2021
 */
public class BigRow 
{
    // row number, 0-8, correlates to the rows[] in SudokuGame
    public int rowNumber;
    // The squares contained by this row
    public Square[] squares;
    
    public BigRow(int rowNumber) {
        this.rowNumber = rowNumber;
        squares = new Square[9];
    }

    /**
     * Selects all squares in this row
     */
    public void select() {
        for (Square square : squares) {
            square.highlight(SelectionLevel.BACKGROUND);
        }
    }

    /**
     * Deselects all squares in this row
     */
    public void deselect() {
        for (Square square : squares) {
            square.highlight(SelectionLevel.UNSELECTED);
        }
    }
    
    /**
     * Checks how many of a given number are in this row
     * @param value the value to check
     * @return how many are found, -1 is returned if and invalid number is given
     */
    public int contains (int value) {
        if (value <= 0) return -1;
        else if (value > 9) return -1;
        int counter = 0;
        for (int i = 0; i < squares.length; i++) {
            if (squares[i].value == value) {
                counter++;
            }
        }
        return counter;
    }
}
