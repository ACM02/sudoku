package sudoku;

import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 * MouseListener.java
 * 
 * @since Jul. 24, 2021
 * @author A. McLeod
 */
public class MouseListener implements java.awt.event.MouseListener {

    public Point pointClicked;
    public Point mousePosition = new Point();
    // e.getX() gets a point that's not quite right relative to the mouse, these offsets fix that
    private final int X_OFFSET = -9;
    private final int Y_OFFSET = -31;
    
    public MouseListener (){
        pointClicked = new Point();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        pointClicked.x = e.getX() + X_OFFSET;
        pointClicked.y = e.getY() + Y_OFFSET;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
