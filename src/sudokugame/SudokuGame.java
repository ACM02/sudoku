/** required package class namespace */
package sudokugame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import sudoku.Main;

 
/**
 * SudokuGame.java - description
 *
 * @author A. McLeod
 * @since Jul. 24, 2021
 */
public class SudokuGame 
{

	public static final double X_PLACEMENT = 0.33;
	public static final double Y_PLACEMENT = 0.4;
    public int width = Square.WIDTH*9;
    public int height = Square.HEIGHT*9;
    public int xPos = (int) ((Main.SCREEN_WIDTH*X_PLACEMENT)-(width/2));
    public int yPos =  (int) ((Main.SCREEN_HEIGHT*Y_PLACEMENT)-(height/2));
    
    // all squares in the game
    public Square[] squares;
    
    // all full rows in the game
    public BigRow[] rows;
    // all full collumns in the game
    public BigCollumn[] collumns;
    // all 3x3 boxes in the game
    public BigBox[] boxes;
    
    public ArrayList<int[]> solutions = new ArrayList<int[]>();
    
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
    	g.setColor(Color.BLACK);
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
    
    public void resize() {
        width = Square.WIDTH*9;
        height = Square.HEIGHT*9;
        xPos = (int) ((Main.SCREEN_WIDTH*X_PLACEMENT)-(width/2));
        yPos =  (int) ((Main.SCREEN_HEIGHT*Y_PLACEMENT)-(height/2));
        int xCounter = 0;
        int yCounter = 0;
        for (int i = 0; i < squares.length; i++) {
        	squares[i].setPosition(xCounter * Square.WIDTH + xPos, yCounter * Square.HEIGHT + yPos);
            // increase the position in the row each time through
            if (xCounter < 8)
                xCounter++;
            else { // if xCounter hits it's limit go down a row and back to the start
                xCounter = 0;
                yCounter++;
            }
        	
        }
    }
    
    /**
     * Simply deselects all the squares 
     */
    public void deselectAll() {
        for (int i = 0; i < squares.length; i++) {
            squares[i].highlight(SelectionLevel.UNSELECTED);
        }
    }
    
    public void loadPuzzle(int[] puzzle) {
        for (int i = 0; i < squares.length; i++) {
        	squares[i].isMutable=true;
            squares[i].setValue(puzzle[i]);
            if (squares[i].getValue() != 0) squares[i].isMutable = false;
            else squares[i].isMutable = true;
        }
    }
    
    public boolean contains(Point p) {
    	if (p==null) return false;
        if (p.x > xPos && p.x < xPos + width &&
            p.y > yPos && p.y < yPos + height){
            return true;
        }
        return false;
    }
    
    public void output() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(this.squares[i*9 + j].getValue());
			}
			System.out.println();
		}
    }
    
    public void clearMutableSquares() {
    	for (int i = 0; i < squares.length; i++) {
			if (squares[i].isMutable) squares[i].setValue(0);
		}
    }
    
    public boolean hasErrors() {
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 1; j < 10; j++) {
            	if (boxes[i].contains(j) >= 2) return true;
            	if (rows[i].contains(j) >= 2) return true;
            	if (collumns[i].contains(j) >= 2) return true;
            }
        }
        return false;
    }
    
    public boolean hasErrorAt(int index) {
		Square toCheck = squares[index];
        if (toCheck.box.contains(toCheck.getValue()) > 1) return true;
        if (toCheck.row.contains(toCheck.getValue()) > 1) return true;
        if (toCheck.collumn.contains(toCheck.getValue()) > 1) return true;
		return false;
    }
    
}
