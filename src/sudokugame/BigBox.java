/** required package class namespace */
package sudokugame;

 
/**
 * BigBox.java - Represents a 3x3 box in a game of sudoku
 *
 * @author A. McLeod
 * @since Jul. 24, 2021
 */
public class BigBox 
{
    // box number, 0-8, correlates to the boxes[] in SudokuGame
    public int boxNumber;
    // The squares contained by this box
    public Square[] squares;
    
    public BigBox(int boxNumber) {
        this.boxNumber = boxNumber;
        squares = new Square[9];
    }

    /**
     * Selects all squares in this box
     */
    public void select() {
        for (Square square : squares) {
            square.select();
        }
    }

    /**
     * Deselects all squares in this box
     */
    public void deselect() {
        for (Square square : squares) {
            square.deselect();
        }
    }
    
    /**
     * Checks how many of a given number are in this box
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