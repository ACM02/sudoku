package sudoku;

import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

/**
 * Where we draw stuff.
 * 
 *  @author Juhyung Kim
 */
@SuppressWarnings("serial")
public class Panel extends JPanel implements ComponentListener {

	public Panel() {
		super();
		this.addComponentListener(this);
	}
	
    /**
     * A built-in method from JPanel that'll allow us to draw on screen.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (Main.game != null){
            Main.game.draw(g);
        }
        
    }

    /**
     * Resizes the game components when the frame is resized
     */
	@Override
	public void componentResized(ComponentEvent e) {
		Main.resize(e.getComponent().getWidth(), e.getComponent().getHeight());
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
}