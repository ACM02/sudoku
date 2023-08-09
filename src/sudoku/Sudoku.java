package sudoku;

import components.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;
import sudokugame.SudokuGame;
import sudokugame.SelectionLevel;
import sudokugame.Square;

/** 
 * Sudoku.java - 
 * https://github.com/SH-GameMakersClub/Projects/tree/master/Flappy%20Bird/FlappyBirdFinal/src/flappybird
 * @author A. McLoed
 * @since Jul. 24, 2021 
 */
public class Sudoku implements ActionListener{

    public static Sudoku game;
    public Panel panel;
    public static final int SCREEN_HEIGHT = 600;
    public static final int SCREEN_WIDTH = 800;
    
    public SudokuGame sudokuGame;
    public MouseListener mouseListener;
    public Button[] numberButtons;
    public Button eraser;
    public int selectedNumber = 0;
    public Square selectedSquare;
    
    /**
     * Initializes the frame and everything else
     */
    public Sudoku() {
        sudokuGame = new SudokuGame();
        numberButtons = new Button[9];
        eraser = new Button(
                (int) (Square.WIDTH * 3 + sudokuGame.xPos),
                Square.HEIGHT * 10 + sudokuGame.yPos,
                Square.WIDTH * 2,
                Square.HEIGHT);
        eraser.text = "Erase";
        for (int i = 0; i < numberButtons.length; i++) {
            numberButtons[i] = new Button(
            Square.WIDTH * i + sudokuGame.xPos,
            Square.HEIGHT * 9 + sudokuGame.yPos + 2,
            Square.WIDTH,
            Square.HEIGHT);
            numberButtons[i].text = "" + (i + 1);
        }
        selectedSquare = sudokuGame.squares[0];
        //numberButtons[0].borderColor = Color.LIGHT_GRAY;
        
        initFrame(new JFrame());
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // create the game instance
        game = new Sudoku();

        // timer for the game loop with 20 millisec delay (50 fps)
        Timer timer = new Timer(20, game);
        timer.start();
        
        
        new SudokuSolver(null);
        
        
    }

  /**
     * The game loop: where everything inside the game updates.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
    	updateMousePosition();
        handleSquares();
        hightlightErrors();
        
        mouseListener.pointClicked = new Point();
        panel.repaint();
    }

    private void updateMousePosition() {
    	if (panel.getMousePosition() != null) {
        	mouseListener.mousePosition = panel.getMousePosition();
    	}

    	
    }
	/**
     * Draws the game screen
     * @param g Graphics object that does the drawing
     */
    public void draw(Graphics g) {
        sudokuGame.draw(g);
        for (Button numberButton : numberButtons) {
            numberButton.draw(g);
        }
        eraser.draw(g);
    }

    /**
     * Initializes the frame and its components
     * @param frame 
     */
    private void initFrame(JFrame frame) {
        // we'll draw the game screen on this panel
        panel = new Panel();
        // it's important that the screen dimension is the dimension of the panel,
        // not the frame
        panel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        frame.add(panel);

        addLabels(panel);

        frame.setTitle("Sukodu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mouseListener = new MouseListener();
        frame.addMouseListener(mouseListener);
        frame.setResizable(false); // we don't want people to resize our game's screen
        frame.setVisible(true);
        frame.pack(); // update the frame to fit the panel

        // set the location of the frame to center of the screen
        frame.setLocationRelativeTo(null);
    }

    /**
     * Adds labels to the panel - currently not used
     * @param panel the panel 
     */
    private void addLabels(Panel panel) {
        for (Button numberButton : numberButtons) {
            panel.add(numberButton.iconLabel);
        }
        panel.add(eraser.iconLabel);
    }

    /**
     * Handles the selection of squares and placing values in them
     */
    private void handleSquares() {
    	// Highlight Squares
    	if (sudokuGame.contains(mouseListener.mousePosition)) {
            for (int i = 0; i < sudokuGame.squares.length; i++) {
                if (sudokuGame.squares[i].contains(mouseListener.mousePosition)) {
                    sudokuGame.deselectAll();
                    selectedSquare = sudokuGame.squares[i];
                    selectedSquare.selectAll();
                    i = sudokuGame.squares.length; // Found selected square, leave loop
                }
            }
    	} else {
    		selectedSquare = null;
    		sudokuGame.deselectAll();
    	}
    	if (selectedNumber > 0 && (selectedSquare == null || selectedSquare.value == 0)) {
        	for (int i = 0; i < sudokuGame.squares.length; i++) {
    			if (sudokuGame.squares[i].value == selectedNumber) {
    				sudokuGame.squares[i].highlight(SelectionLevel.BACKGROUND);
    			}
    		}
    	}
    	if (selectedSquare != null) {
        	for (int j = 0; j < sudokuGame.squares.length; j++) {
                if (sudokuGame.squares[j].value == selectedSquare.value && selectedSquare.value != 0) {
                    sudokuGame.squares[j].highlight(SelectionLevel.FOREGROUND);
                }
            }
    	}



        // Number placement
        for (int i = 0; i < sudokuGame.squares.length; i++) {
			if (sudokuGame.squares[i].contains(mouseListener.pointClicked)) {
				selectedSquare.value = selectedNumber;
			}
		}
        
        
        // Number selection
        if (eraser.contains(mouseListener.pointClicked)) {
            selectedNumber = 0;
            eraser.borderColor = Color.GRAY;
            for (int i = 0; i < numberButtons.length; i++) {
				numberButtons[i].borderColor = null;
			}
            return; // Eraser selected, don't bother checking numbers
        }
        for (int i = 0; i < numberButtons.length; i++) {
            if (numberButtons[i].contains(mouseListener.pointClicked)) {
                selectedNumber = i + 1;
                numberButtons[i].borderColor = Color.GRAY;
                for (int j = 0; j < numberButtons.length; j++) {
					if (j != i) {
						numberButtons[j].borderColor = null;
						eraser.borderColor = null;
					}
				}
                i = numberButtons.length; // Exit loop, done
            }
        }
    }

    /**
     * Highlights all squares that are errors in red
     */
    private void hightlightErrors() {
        for (int i = 0; i < sudokuGame.squares.length; i++) {
            sudokuGame.squares[i].textColor = Color.BLACK;
        }
        for (int i = 0; i < sudokuGame.boxes.length; i++) {
            for (int j = 1; j < 10; j++) {
                int numFound = sudokuGame.boxes[i].contains(j);
                if (numFound >= 2) {
                    for (int k = 0; k < 9; k++) {
                        if (sudokuGame.boxes[i].squares[k].value == j) {
                            sudokuGame.boxes[i].squares[k].textColor = Color.RED;
                        }
                    }
                }
                numFound = sudokuGame.rows[i].contains(j);
                if (numFound >= 2) {
                    for (int k = 0; k < 9; k++) {
                        if (sudokuGame.rows[i].squares[k].value == j) {
                            sudokuGame.rows[i].squares[k].textColor = Color.RED;
                        }
                    }
                }
                numFound = sudokuGame.collumns[i].contains(j);
                if (numFound >= 2) {
                    for (int k = 0; k < 9; k++) {
                        if (sudokuGame.collumns[i].squares[k].value == j) {
                            sudokuGame.collumns[i].squares[k].textColor = Color.RED;
                        }
                    }
                }
            }
        }
    }

}
