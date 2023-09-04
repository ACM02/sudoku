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
		toSolve = game;
		for (int i = 0; i < toSolve.squares.length; i++) {
			toSolve.squares[i].value = sampleBoard[i];
			if (toSolve.squares[i].value != 0) toSolve.squares[i].isMutable = false;
		}
		//calculateSolution();
		solveRecursively(toSolve, 0);
		System.out.println("Found " + toSolve.solutions.size() + " solution(s)");
		if (toSolve.solutions.size() == 0) {
			System.out.println("Sudoku Solver finished.");
			return;
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(toSolve.solutions.get(0)[i*9 + j]);
			}
			System.out.println();
		}
		System.out.println("Sudoku Solver finished.");
	}
	
	public void calculateSolution() {
		Square[] squares = toSolve.squares;
		int[] solution;
		for (int i = 0; i < squares.length; i++) {
			if (i == squares.length-1) {
				if (isSolved(toSolve)) {
					solution = new int[81];
					for (int j = 0; j < squares.length; j++) {
						solution[j] = squares[j].value;
					}
					toSolve.solutions.add(solution);
					//if (squares[i].isMutable)
					//	squares[i].value++;
				}
			} // Attempt at making this work for multiple solutions
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
	
	public static void solveRecursively(SudokuGame game, int index) {
		boolean finalSquare = index==game.squares.length-1;
		//if (index == toSolve.squares.length) return;
		if (!game.squares[index].isMutable) {
			if (finalSquare) {
				addSolution(game);
				return;
			}
			else solveRecursively(game, index+1);
			return;
		}
		//System.out.println("Trying index: " + index);
		//System.out.println("Final square? " + finalSquare);
		for (int i = 1; i < 10; i++) {
			game.squares[index].value = i;
			if (isValid(game, index)) {
				if (!finalSquare) solveRecursively(game, index+1);
				else addSolution(game);
			}
		}
		game.squares[index].value = 0;
		return;
	}
	
	public static boolean isUnique(SudokuGame game) {
		solveRecursivelyToTwoSolutions(game, 0);
		return game.solutions.size() == 1;
	}
	
	/*
	 * Still finds >2 solutions for some reason!!!
	 */
	private static void solveRecursivelyToTwoSolutions(SudokuGame game, int index) {
		if (game.solutions.size() > 1) return;
		boolean finalSquare = index==game.squares.length-1;
		//if (index == toSolve.squares.length) return;
		if (!game.squares[index].isMutable) {
			if (finalSquare) {
				addSolution(game);
				return;
			}
			else solveRecursively(game, index+1);
			return;
		}
		//System.out.println("Trying index: " + index);
		//System.out.println("Final square? " + finalSquare);
		for (int i = 1; i < 10; i++) {
			if (game.solutions.size() > 1) return;
			game.squares[index].value = i;
			if (isValid(game, index)) {
				if (!finalSquare) {
					if (game.solutions.size() > 1) return;
					solveRecursively(game, index+1);
				}
				else {
					addSolution(game);
					if (game.solutions.size() > 1) return;
				}
			}
		}
		game.squares[index].value = 0;
		return;
	}
	
    private static void addSolution(SudokuGame game) {
    	int[] solution;
		solution = new int[81];
		for (int j = 0; j < game.squares.length; j++) {
			solution[j] = game.squares[j].value;
		}
		game.solutions.add(solution);
		//System.out.println("Found a solution!");
	}

	public static boolean isValid(SudokuGame game) {
        for (int i = 0; i < game.boxes.length; i++) {
            for (int j = 1; j < 10; j++) {
                int numFound = game.boxes[i].contains(j);
                if (numFound >= 2) {
                    for (int k = 0; k < 9; k++) {
                        if (game.boxes[i].squares[k].value == j) {
                        	//System.out.println("Invalid!");
                        	return false;
                        }
                    }
                }
                numFound = game.rows[i].contains(j);
                if (numFound >= 2) {
                    for (int k = 0; k < 9; k++) {
                        if (game.rows[i].squares[k].value == j) {
                        	//System.out.println("Invalid!");
                        	return false;
                        }
                    }
                }
                numFound = game.collumns[i].contains(j);
                if (numFound >= 2) {
                    for (int k = 0; k < 9; k++) {
                        if (game.collumns[i].squares[k].value == j) {
                        	//System.out.println("Invalid!");
                        	return false;
                        }
                    }
                }
            }
        }
        return true;
    }
	
	public static boolean isValid(SudokuGame game, int index) {
		Square toCheck = game.squares[index];
        if (toCheck.box.contains(toCheck.value) > 1) return false;
        if (toCheck.row.contains(toCheck.value) > 1) return false;
        if (toCheck.collumn.contains(toCheck.value) > 1) return false;
		return true;
	}
    
    public static boolean isSolved(SudokuGame game) {
    	for (int i = 0; i < game.squares.length; i++) {
			if (game.squares[i].value == 0) {
				return false;
			}
		}
		return isValid(game);
    }
	
	
	
}
