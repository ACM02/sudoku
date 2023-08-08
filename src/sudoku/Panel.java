package sudoku;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Where we draw stuff.
 * 
 *  @author Juhyung Kim
 */
@SuppressWarnings("serial")
public class Panel extends JPanel {

    /**
     * A built-in method from JPanel that'll allow us to draw on screen.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (Sudoku.game != null){
            Sudoku.game.draw(g);
        }
        
    }
}