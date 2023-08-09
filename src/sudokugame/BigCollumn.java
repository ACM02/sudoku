/** required package class namespace */
package sudokugame;

 
/**
 * BigCollumn.java - Represents a full collumn in a game of sudoku
 *
 * @author A. McLeod
 * @since Jul. 24, 2021
 */
public class BigCollumn 
{
    // collumn number, 0-8, correlates to the collumns[] in SudokuGame
    public int collumnNumber;
    // The squares contained by this collumn
    public Square[] squares;
    
    public BigCollumn(int collumnNumber) {
        this.collumnNumber = collumnNumber;
        squares = new Square[9];
    }

    /**
     * Selects all squares in this collumn
     */
    public void select() {
        for (Square square : squares) {
            square.highlight(SelectionLevel.BACKGROUND);
        }
    }

    /**
     * Deselects all squares in this collumn
     */
    public void deselect() {
        for (Square square : squares) {
            square.highlight(SelectionLevel.UNSELECTED);
        }
    }
    
    /**
     * Checks how many of a given number are in this collumn
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