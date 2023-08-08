/** required package class namespace */
package sudokugame;

import java.awt.Graphics;
import java.awt.Point;

 
/**
 * SudokuGame.java - description
 *
 * @author A. McLeod
 * @since Jul. 24, 2021
 */
public class SudokuGame 
{
    public int xPos = 50;
    public int yPos = 50;
    public int width = Square.WIDTH*9;
    public int height = Square.HEIGHT*9;
    
    // all squares in the game
    public Square[] squares;
    
    // all full rows in the game
    public BigRow[] rows;
    // all full collumns in the game
    public BigCollumn[] collumns;
    // all 3x3 boxes in the game
    public BigBox[] boxes;
    
    /**
     * This gets kind of complicated but it initializes all the arrays required, 
     * fills those arrays with new objects, then fills the properties of those 
     * objects
     */
    public SudokuGame() {
        // Initialize the arrays
        this.squares = new Square[81];
        this.rows = new BigRow[9];
        this.collumns = new BigCollumn[9];
        this.boxes = new BigBox[9];
        
        // Populate the arrays
        for (int i = 0; i < rows.length; i++) {
            rows[i] = new BigRow(i);
            collumns[i] = new BigCollumn(i);
            boxes[i] = new BigBox(i);
        }
        
        // These counters are used to tell each square which box/row/collumn it's
        // in
        int xCounter = 0;
        int yCounter = 0;
        int boxCounter = 0;
        
        /**
         * Big loop, initializes all squares, giving them which row, collumn, and
         * box they're in and sets their position on the screen.
         */
        for (int i = 0; i < squares.length; i++) {
            // For each square, make a new square that is in box[boxCounter], 
            // row[rowCounter] and collumn[collumnCounter]
            squares[i] = new Square(boxes[boxCounter], collumns[xCounter], rows[yCounter]);
            // Set the x position of this square to the number of squares already 
            // in this row (xCOunter) times the width of the square plus the offset
            // of the game grid on the screen. Then the y position to the number 
            // of collumns down we are times the height of the square plus the offset
            // of the game grid on the screen
            squares[i].setPosition(xCounter * Square.WIDTH + xPos, yCounter * Square.HEIGHT + yPos);
            
            // Increment the box counter if we hit a new box (every 3 squares)
            if (xCounter == 2 || xCounter == 5) {
                boxCounter++;
            // if we it the end of the line
            } else if (xCounter == 8) {
                // go back to box 1
                boxCounter = 0;
                // unless we're in row 3-5 or 6-8
                if (yCounter == 2 || yCounter == 3 || yCounter == 4) {
                    boxCounter = 3;
                } else if (yCounter == 5 || yCounter == 6 || yCounter == 7) {
                    boxCounter = 6;
                }
            }
            // increase the position in the row each time through
            if (xCounter < 8)
                xCounter++;
            else { // if xCounter hits it's limit go down a row and back to the start
                xCounter = 0;
                yCounter++;
            }
        }
        
        // Somehow, this works. It gives the box, row, and collumn arrays the squares
        // that they contain
        
        // and array of counters, each will count to 9 by the end of the loop 
        // totalling 81, 1 for each square because we loop through each square
        int[] boxCounters = new int[9];
        int[] rowCounters = new int[9];
        int[] collumnCounters = new int[9];
        // here we loop through each square telling the box, row, and collumn 
        // which one it belongs to, the square already contains this information
        // this just copies it 
        for (int i = 0; i < squares.length; i++) {
            // This is the following statement put into not very plain english:
            // The square in the square array in the bigBox array is assigned to
            // be the square who's box number matches the box this square should
            // be in and is assigned to the next open spot in the squares array in
            // the bigBox array
            
            boxes[squares[i].box.boxNumber].squares[boxCounters[squares[i].box.boxNumber]] = squares[i];
            // Increment the box counter for the box that just had a value added 
            // to it so we know to add it to the next index next time we hit this
            // box
            boxCounters[squares[i].box.boxNumber]++;
            
            // The same statements as above but for rows
            rows[squares[i].row.rowNumber].squares[rowCounters[squares[i].row.rowNumber]] = squares[i];
            rowCounters[squares[i].row.rowNumber]++;
            
            // The same statements as above but for collumns
            collumns[squares[i].collumn.collumnNumber].squares[collumnCounters[squares[i].collumn.collumnNumber]] = squares[i];
            collumnCounters[squares[i].collumn.collumnNumber]++;
        }
    }
    /**
     * Draws the game onto the screen
     * @param g The graphics object doing the drawing
     */
    public void draw(Graphics g) {
        // Draw all the squares
        for (int i = 0; i < squares.length; i++) {
            squares[i].draw(g);
        }
        // Draw the lines that separate the 3x3 squares
        for (int i = 0; i < 9; i += 3) {
            if (i == 3 || i == 6) {
                g.drawLine(Square.WIDTH * i + 1 + xPos, yPos, Square.WIDTH * i + 1 + xPos, Square.HEIGHT * 9 + yPos);
            }
            for (int j = 0; j < 9; j += 3) {
                if (j == 3 || j == 6) {
                    g.drawLine(xPos, Square.HEIGHT * j - 1 + yPos, Square.WIDTH * 9 + xPos, Square.HEIGHT * j - 1 + yPos);
                }
            }
        }
    }
    
    /**
     * Simply deselects all the squares 
     */
    public void deselectAll() {
        for (int i = 0; i < squares.length; i++) {
            squares[i].deselect();
        }
    }
    
    public void loadPuzzle(int[] puzzle) {
        for (int i = 0; i < squares.length; i++) {
            squares[i].value = puzzle[i];
        }
    }
    
    public boolean contains(Point p) {
        if (p.x > xPos && p.x < xPos + width &&
            p.y > yPos && p.y < yPos + height){
            return true;
        }
        return false;
    }
}