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
import sudokugame.Difficulty;
import sudokugame.SelectionLevel;
import sudokugame.Square;

/** 
 * Sudoku.java - 
 * https://github.com/SH-GameMakersClub/Projects/tree/master/Flappy%20Bird/FlappyBirdFinal/src/flappybird
 * @author A. McLoed
 * @since Jul. 24, 2021 
 */
public class Main implements ActionListener {

    public static Main game;
    public Panel panel;
    public static int SCREEN_HEIGHT = 400;
    public static int SCREEN_WIDTH = 500;
    
    public static SudokuGame sudokuGame;
    public MouseListener mouseListener;
    public static Button[] numberButtons;
    public static Button eraser;
    public static Button newGameImpossible;
    public static Button newGameHard;
    public static Button newGameMedium;
    public static Button newGameEasy;
    public static int selectedNumber = 0;
    public static Square selectedSquare;
    
    public static final Color SUDOKU_BLUE = new Color(14,41,115);
    
    /**
     * Initializes the frame and everything else
     */
    public Main() {
        sudokuGame = new SudokuGame();
        initButtons();

        //newGame.backgroundColor = Color.BLACK;
        initFrame(new JFrame());
    }
    
    /**
     * Initializes the number and eraser buttons below the game
     */
    private static void initButtons() {
        numberButtons = new Button[9];
        eraser = new Button(
                (int) (Square.WIDTH * 3 + sudokuGame.xPos),
                Square.HEIGHT * 10 + sudokuGame.yPos,
                Square.WIDTH * 3,
                Square.HEIGHT);
        eraser.text = "Erase";
        eraser.textColor = SUDOKU_BLUE;
        for (int i = 0; i < numberButtons.length; i++) {
            numberButtons[i] = new Button(
            Square.WIDTH * i + sudokuGame.xPos,
            Square.HEIGHT * 9 + sudokuGame.yPos + 2,
            Square.WIDTH,
            Square.HEIGHT);
            numberButtons[i].textColor = SUDOKU_BLUE;
            numberButtons[i].text = "" + (i + 1);
        }
        selectedSquare = sudokuGame.squares[0];
        
        
        newGameImpossible = new Button(sudokuGame.xPos + Square.WIDTH*10, sudokuGame.yPos, Square.WIDTH*6, Square.HEIGHT);
        newGameImpossible.borderColor = Color.BLACK;
        newGameImpossible.text = "New game (Impossible)";
        
        newGameHard = new Button(sudokuGame.xPos + Square.WIDTH*10, sudokuGame.yPos + Square.HEIGHT, Square.WIDTH*5, Square.HEIGHT);
        newGameHard.borderColor = Color.BLACK;
        newGameHard.text = "New game (Hard)";
        
        newGameMedium = new Button(sudokuGame.xPos + Square.WIDTH*10, sudokuGame.yPos + Square.HEIGHT*2, Square.WIDTH*5, Square.HEIGHT);
        newGameMedium.borderColor = Color.BLACK;
        newGameMedium.text = "New game (Medium)";
        
        newGameEasy = new Button(sudokuGame.xPos + Square.WIDTH*10, sudokuGame.yPos + Square.HEIGHT*3, Square.WIDTH*5, Square.HEIGHT);
        newGameEasy.borderColor = Color.BLACK;
        newGameEasy.text = "New game (Easy)";
	}
    
	/**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // create the game instance
        game = new Main();

        // timer for the game loop with 20 millisec delay (50 fps)
        Timer timer = new Timer(20, game);
        timer.start();
        
        
        //new SudokuSolver(Main.sudokuGame);
        SudokuGenerator gen = new SudokuGenerator();
        gen.generatePuzzle(Difficulty.IMPOSSIBLE);
        
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
    	//g.drawLine(SCREEN_WIDTH/2, 0, SCREEN_WIDTH/2, SCREEN_HEIGHT);
    	//g.drawLine(0, SCREEN_HEIGHT/2, SCREEN_WIDTH, SCREEN_HEIGHT/2);
        sudokuGame.draw(g);
        for (Button numberButton : numberButtons) {
            numberButton.draw(g);
        }
        eraser.draw(g);
        newGameImpossible.draw(g);
        newGameHard.draw(g);
        newGameMedium.draw(g);
        newGameEasy.draw(g);
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
        frame.setResizable(true); // we don't want people to resize our game's screen
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
    	highlightSquares();

        // Number placement
        for (int i = 0; i < sudokuGame.squares.length; i++) {
			if (sudokuGame.squares[i].contains(mouseListener.pointClicked)) {
				selectedSquare.setValue(selectedNumber);
			}
		}
        
        handleButtons();
    }

    private void handleButtons() {
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
		
        if (newGameImpossible.contains(mouseListener.pointClicked)) {
            SudokuGenerator gen = new SudokuGenerator();
            gen.generatePuzzle(Difficulty.IMPOSSIBLE);
        }
        if (newGameHard.contains(mouseListener.pointClicked)) {
            SudokuGenerator gen = new SudokuGenerator();
            gen.generatePuzzle(Difficulty.HARD);
        }
        if (newGameMedium.contains(mouseListener.pointClicked)) {
            SudokuGenerator gen = new SudokuGenerator();
            gen.generatePuzzle(Difficulty.MEDIUM);
        }
        if (newGameEasy.contains(mouseListener.pointClicked)) {
            SudokuGenerator gen = new SudokuGenerator();
            gen.generatePuzzle(Difficulty.EASY);
        }
	}

	/**
     * Sets the selected square based on mouse position and highlights the appropriate squares.
     * If no square is selected, highlights based on the selected number.
     */
    private void highlightSquares() {
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
    	
    	if (selectedNumber > 0 && (selectedSquare == null || selectedSquare.getValue() == 0)) {
        	for (int i = 0; i < sudokuGame.squares.length; i++) {
    			if (sudokuGame.squares[i].getValue() == selectedNumber) {
    				sudokuGame.squares[i].highlight(SelectionLevel.BACKGROUND);
    			}
    		}
    	}
    	if (selectedSquare != null) {
        	for (int j = 0; j < sudokuGame.squares.length; j++) {
                if (sudokuGame.squares[j].getValue() == selectedSquare.getValue() && selectedSquare.getValue() != 0) {
                    sudokuGame.squares[j].highlight(SelectionLevel.FOREGROUND);
                }
            }
    	}
		
	}

	/**
     * Highlights all squares that are errors in red
     */
    private void hightlightErrors() {
        for (int i = 0; i < sudokuGame.squares.length; i++) {
            sudokuGame.squares[i].isError = false;
        }
        for (int i = 0; i < sudokuGame.boxes.length; i++) {
            for (int j = 1; j < 10; j++) {
                int numFound = sudokuGame.boxes[i].contains(j);
                if (numFound >= 2) {
                    for (int k = 0; k < 9; k++) {
                        if (sudokuGame.boxes[i].squares[k].getValue() == j) {
                            sudokuGame.boxes[i].squares[k].isError = true;
                        }
                    }
                }
                numFound = sudokuGame.rows[i].contains(j);
                if (numFound >= 2) {
                    for (int k = 0; k < 9; k++) {
                        if (sudokuGame.rows[i].squares[k].getValue() == j) {
                            sudokuGame.rows[i].squares[k].isError = true;
                        }
                    }
                }
                numFound = sudokuGame.collumns[i].contains(j);
                if (numFound >= 2) {
                    for (int k = 0; k < 9; k++) {
                        if (sudokuGame.collumns[i].squares[k].getValue() == j) {
                            sudokuGame.collumns[i].squares[k].isError = true;
                        }
                    }
                }
            }
        }
    }

    /**
     * Resizes components on the game frame to a new size based off of a new frame dimension
     * @param width Width of the frame
     * @param height Height of the frame
     */
    public static void resize(int width, int height) {
    	int smaller;
    	if (width < height) smaller = width;
    	else smaller = height;
    	SCREEN_WIDTH = width;
    	SCREEN_HEIGHT = height;
    	Square.HEIGHT = (int) (smaller * 0.07);
    	Square.WIDTH = Square.HEIGHT;
    	sudokuGame.resize();
    	
    	//System.out.println("Screen is: " + SCREEN_WIDTH/Square.WIDTH + " squares wide");

        eraser.setX((int) (Square.WIDTH * 3 + sudokuGame.xPos));
        eraser.setY(Square.HEIGHT * 10 + sudokuGame.yPos);
        eraser.setWidth(Square.WIDTH*3);
        eraser.setHeight(Square.HEIGHT);
        
        for (int i = 0; i < numberButtons.length; i++) {
            numberButtons[i].setX(Square.WIDTH * i + sudokuGame.xPos);
            numberButtons[i].setY(Square.HEIGHT * 9 + sudokuGame.yPos + 2);
            numberButtons[i].setWidth(Square.WIDTH);
            numberButtons[i].setHeight(Square.HEIGHT);
            
        }
        
        newGameImpossible.setX(sudokuGame.xPos + Square.WIDTH*10);
        newGameImpossible.setY(sudokuGame.yPos);
        newGameImpossible.setWidth(Square.WIDTH*10);
        newGameImpossible.setHeight(Square.HEIGHT);
        
        newGameHard.setX(sudokuGame.xPos + Square.WIDTH*10);
        newGameHard.setY(sudokuGame.yPos + Square.HEIGHT);
        newGameHard.setWidth(Square.WIDTH*10);
        newGameHard.setHeight(Square.HEIGHT);
        
        newGameMedium.setX(sudokuGame.xPos + Square.WIDTH*10);
        newGameMedium.setY(sudokuGame.yPos + Square.HEIGHT*2);
        newGameMedium.setWidth(Square.WIDTH*10);
        newGameMedium.setHeight(Square.HEIGHT);
        
        newGameEasy.setX(sudokuGame.xPos + Square.WIDTH*10);
        newGameEasy.setY(sudokuGame.yPos + Square.HEIGHT*3);
        newGameEasy.setWidth(Square.WIDTH*10);
        newGameEasy.setHeight(Square.HEIGHT);
    	
    }
}
