package sudoku;

import sudokugame.Square;
import sudokugame.SudokuGame;

/**
 * SudokuGenerator.java - Class used to solve sudoku puzzles
 *
 * @author A. McLeod
 * @since Aug. 8, 2023
 */
public class SudokuSolver {

	private SudokuGame toSolve;
	
	private int[] sampleBoard = {
			   8, 0, 0, 0, 0, 0, 0, 0, 0 ,
			   0, 0, 3, 6, 0, 0, 0, 0, 0 ,
			   0, 7, 0, 0, 9, 0, 2, 0, 0 ,
			   0, 5, 0, 0, 0, 7, 0, 0, 0 ,
			   0, 0, 0, 0, 4, 5, 7, 0, 0 ,
			   0, 0, 0, 1, 0, 0, 0, 3, 0 ,
			   0, 0, 1, 0, 0, 0, 0, 6, 8 ,
			   0, 0, 8, 5, 0, 0, 0, 1, 0 ,
			   0, 9, 0, 0, 0, 0, 4, 0, 0  };
	
	public SudokuSolver(SudokuGame game) {
		//this.toSolve = game;
		toSolve = new SudokuGame();
		for (int i = 0; i < toSolve.squares.length; i++) {
			toSolve.squares[i].value = sampleBoard[i];
			if (toSolve.squares[i].value != 0) toSolve.squares[i].isMutable = false;
		}
		calculateSolution();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(toSolve.squares[i*9 + j].value);
			}
			System.out.println();
		}
	}
	
	public void calculateSolution() {
		Square[] squares = toSolve.squares;
		for (int i = 0; i < squares.length; i++) {
			//System.out.println("Checking square " + i + " with value: " + squares[i].value);
			if (squares[i].isMutable) {
				if (squares[i].value == 0 || !isValid(toSolve)) {
					if (squares[i].value < 9) {
						squares[i].value++;
						i--;
					} else {
						squares[i].value = 0;
						boolean backtracked = false;
						while (!backtracked) {
							i--;
							if (squares[i].isMutable) {
								if (squares[i].value < 9) {
									squares[i].value++;
									backtracked = true;
									i--;
								} else {
									squares[i].value = 0;
								}
							}
						}
					}
				}
			}
		}
	}
	
    private boolean isValid(SudokuGame game) {
        for (int i = 0; i < toSolve.boxes.length; i++) {
            for (int j = 1; j < 10; j++) {
                int numFound = toSolve.boxes[i].contains(j);
                if (numFound >= 2) {
                    for (int k = 0; k < 9; k++) {
                        if (toSolve.boxes[i].squares[k].value == j) {
                        	//System.out.println("Invalid!");
                        	return false;
                        }
                    }
                }
                numFound = toSolve.rows[i].contains(j);
                if (numFound >= 2) {
                    for (int k = 0; k < 9; k++) {
                        if (toSolve.rows[i].squares[k].value == j) {
                        	//System.out.println("Invalid!");
                        	return false;
                        }
                    }
                }
                numFound = toSolve.collumns[i].contains(j);
                if (numFound >= 2) {
                    for (int k = 0; k < 9; k++) {
                        if (toSolve.collumns[i].squares[k].value == j) {
                        	//System.out.println("Invalid!");
                        	return false;
                        }
                    }
                }
            }
        }
        return true;
    }
	
	
	
}
