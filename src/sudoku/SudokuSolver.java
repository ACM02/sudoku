package sudoku;

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
		game.loadPuzzle(sampleBoard);
//		for (int i = 0; i < toSolve.squares.length; i++) {
//			toSolve.squares[i].value = sampleBoard[i];
//			if (toSolve.squares[i].value != 0) toSolve.squares[i].isMutable = false;
//		}  Replaced by above line
		//calculateSolution();
		solveRecursivelyToTwoSolutions(toSolve, 0);
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
			game.squares[index].setValue(i);
			if (!game.hasErrorAt(index)) {
				if (!finalSquare) solveRecursively(game, index+1);
				else addSolution(game);
			}
		}
		game.squares[index].setValue(0);
		return;
	}
	
	public static boolean isUnique(SudokuGame game) {
		solveRecursivelyToTwoSolutions(game, 0);
		game.clearMutableSquares();
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
			else solveRecursivelyToTwoSolutions(game, index+1);
			return;
		}
		//System.out.println("Trying index: " + index);
		//System.out.println("Final square? " + finalSquare);
		for (int i = 1; i < 10; i++) {
			if (game.solutions.size() > 1) return;
			game.squares[index].setValue(i);
			if (!game.hasErrorAt(index)) {
				if (!finalSquare) {
					solveRecursivelyToTwoSolutions(game, index+1);
				}
				else {
					addSolution(game);
					if (game.solutions.size() > 1) return;
				}
			}
		}
		game.squares[index].setValue(0);
		return;
	}
	
    private static void addSolution(SudokuGame game) {
    	int[] solution;
		solution = new int[81];
		for (int j = 0; j < game.squares.length; j++) {
			solution[j] = game.squares[j].getValue();
		}
		game.solutions.add(solution);
		//System.out.println("Found a solution!");
	}
    
    public static boolean isSolved(SudokuGame game) {
    	for (int i = 0; i < game.squares.length; i++) {
			if (game.squares[i].getValue() == 0) {
				return false;
			}
		}
		return !game.hasErrors();
    }
	
    /*
     * Old method that sucks 
     */
//	public void calculateSolution() {
//	Square[] squares = toSolve.squares;
//	int[] solution;
//	for (int i = 0; i < squares.length; i++) {
//		if (i == squares.length-1) {
//			if (isSolved(toSolve)) {
//				solution = new int[81];
//				for (int j = 0; j < squares.length; j++) {
//					solution[j] = squares[j].getValue();
//				}
//				toSolve.solutions.add(solution);
//				//if (squares[i].isMutable)
//				//	squares[i].value++;
//			}
//		} // Attempt at making this work for multiple solutions
//		//System.out.println("Checking square " + i + " with value: " + squares[i].value);
//		if (squares[i].isMutable) {
//			if (squares[i].getValue() == 0 || toSolve.hasErrors()) {
//				if (squares[i].getValue() < 9) {
//					squares[i].setValue(squares[i].getValue()+1);
//					i--;
//				} else {
//					squares[i].setValue(0);;
//					boolean backtracked = false;
//					while (!backtracked) {
//						i--;
//						if (squares[i].isMutable) {
//							if (squares[i].getValue() < 9) {
//								squares[i].setValue(squares[i].getValue()+1);
//								backtracked = true;
//								i--;
//							} else {
//								squares[i].setValue(0);
//							}
//						}
//					}
//				}
//			}
//		}
//	}
//}
	
}
