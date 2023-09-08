/** required package class namespace */
package sudoku;

import java.util.ArrayList;
import java.util.Random;

import sudokugame.Difficulty;
import sudokugame.SudokuGame;

/**
 * SudokuGenerator.java - Class used to generate new sudoku puzzles
 *
 * @author A. McLeod
 * @since Jul. 28, 2021
 */
public class SudokuGenerator 
{

	public SudokuGame generated;
	
    /**
     * Default constructor, set class properties
     */
    public SudokuGenerator() {
        
    }
    
    public SudokuGame generatePuzzle(Difficulty difficulty) {
    	generated = new SudokuGame();
    	Main.sudokuGame = generated;
    	// Phase 1, fill in a valid puzzle
    	do {
        	fillPuzzle(0);
		} while (!SudokuSolver.isSolved(generated));
    	//generated.output();
    	
    	// I SHOULD ADD THE SOLUTION TO SOLUTIONS HERE!! (Since I already have it)
    	//System.out.println("--------------------------");
    	
    	
    	// Phase 2, remove values
    	
    	
    	for (int i = 0; i < generated.squares.length; i++) {
			generated.squares[i].isMutable = false;
		}
    	Random r = new Random();
    	int attempts = 0;
    	for (int i = 0; i < difficulty.squaresRemoved; i++) {
			int index = r.nextInt(81);
			
			//System.out.println("Attempting to remove index " + index + " (" + i + "/" + difficulty.squaresRemoved + ")");
			if (!generated.squares[index].isMutable) {
				int valueRemoved = generated.squares[index].getValue();
				generated.squares[index].isMutable = true;
				generated.squares[index].setValue(0);;
				generated.solutions = new ArrayList<>();
				boolean unique = SudokuSolver.isUnique(generated);
				//if (generated.solutions.size() == 0) System.out.println("NO SOLUTIONS!!!!");
				//System.out.println("Found " + generated.solutions.size() + " solution(s)!");
				if (!unique) {
					i--;
					generated.squares[index].setValue(valueRemoved);
					generated.squares[index].isMutable = false;
					//System.out.println("Failed (Attempt: " + attempts + "/" + difficulty.squaresRemoved*2 + ")");
				} else {
					generated.squares[index].isMutable = true;
					//System.out.println("Success");
				}
				attempts++;
				if (attempts > difficulty.squaresRemoved*2) {
					i = Integer.MAX_VALUE;
					//System.out.println("FAILED, GENERATING NEW PUZZLE");
					return generatePuzzle(difficulty);
				}
			} else {
				i--;
			}

		}
    	//generated.output();
		return generated;
    }
     
    public boolean fillPuzzle(int index) {
    	//System.out.println("Trying index " + index);
		if (index == generated.squares.length) return true;
		Random r = new Random();
		boolean done = false;
		for (int i = 0; i < 9; i++) {
			generated.squares[index].setValue(r.nextInt(9)+1);
			if (!generated.hasErrorAt(index)) {
				done = fillPuzzle(index+1);
				if (done) return true;
			}
		}
		generated.squares[index].setValue(0);
		
		return false;
    }
    
}