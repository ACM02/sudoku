/** required package class namespace */
package sudokugame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import sudoku.Main;

 
/**
 * Square.java - Represents a square in a game of sudoku
 *
 * @author A. McLeod
 * @since Jul. 24, 2021
 */
public class Square 
{
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;
    // The value in this square
    private int value = 0;
    public Rectangle hitbox;
    
    // The box, row, and collum this square is part of
    public BigBox box;
    public BigCollumn collumn;
    public BigRow row;
    public boolean isMutable = true;
    public boolean isError = false;
    
    // Colours of this square
    private Color backgroundColor = null;
    private Color borderColor = Color.BLACK;
    //private Color textColor = Color.BLACK;

    public Square(BigBox box, BigCollumn collumn, BigRow row) {
        this.box = box;
        this.collumn = collumn;
        this.row = row;
        hitbox = new Rectangle();
        hitbox.width = WIDTH;
        hitbox.height = HEIGHT;
    }
    
    /**
     * Sets the position of this square
     * @param xPos x position to set the square to
     * @param yPos y position to set the square to
     */
    public void setPosition(int xPos, int yPos) {
        hitbox.x = xPos;
        hitbox.y = yPos;
    }
    
    public int getValue() {
    	return this.value;
    }
    
    public void setValue(int value) {
    	if (isMutable) this.value = value;
    }
    
    /**
     * Draws a sudoku square 
     * @param g the Graphics object doing the drawing
     */
    public void draw(Graphics g) {
    	g.setFont(new Font("default", Font.BOLD, 16));
        if (backgroundColor != null) {
            g.setColor(backgroundColor);
            g.fillRect(hitbox.x, hitbox.y, WIDTH, HEIGHT);
        }
        if (borderColor != null) {
            g.setColor(borderColor);
            g.drawRect(hitbox.x, hitbox.y, WIDTH, HEIGHT);
        }
        if (value != 0) {
        	if (isError) {
        		g.setColor(Color.RED);
        	} else if (isMutable) {
                g.setColor(Main.SUDOKU_BLUE);
        	} else {
        		g.setColor(Color.BLACK);
        	}

            g.drawString(value + "", hitbox.x + (int) (WIDTH * 0.33), hitbox.y + (int) (HEIGHT * 0.80));
        }
        
    }
    
    public boolean contains(Point p) {
        if (p.x > hitbox.x && p.x < hitbox.x + WIDTH &&
            p.y > hitbox.y && p.y < hitbox.y + HEIGHT){
            return true;
        }
        return false;
    }
    
    public void selectAll() {
        row.select();
        collumn.select();
        box.select();
        highlight(SelectionLevel.FOREGROUND);
    }
    
    public void deselectAll() {
        row.deselect();
        collumn.deselect();
        box.deselect();
        highlight(SelectionLevel.UNSELECTED);
    }
    
    public void highlight(SelectionLevel level) {
        backgroundColor = level.color;
    }
}