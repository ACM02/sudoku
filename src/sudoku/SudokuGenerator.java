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
        	fillPuzzle(generated);
		} while (!SudokuSolver.isSolved(generated));
    	
    	// I SHOULD ADD THE SOLUTION TO SOLUTIONS HERE!! (Since I already have it)
    	
    	// Phase 2, remove values
    	
    	
    	for (int i = 0; i < generated.squares.length; i++) {
			generated.squares[i].isMutable = false;
		}
    	Random r = new Random();
    	int attempts = 0;
    	for (int squaresRemoved = 0; squaresRemoved < difficulty.squaresToRemove; squaresRemoved++) {
			int index = r.nextInt(81);
			
			if (!generated.squares[index].isMutable) {
				// Track the removed value in case we need to place it back
				int valueRemoved = generated.squares[index].getValue();
				// Remove the random index from the puzzle
				generated.squares[index].isMutable = true;
				generated.squares[index].setValue(0);;
				generated.solutions = new ArrayList<>();
				boolean unique = SudokuSolver.isUnique(generated);
				if (!unique) {
					// FAILED, restore original value and try again
					squaresRemoved--;
					generated.squares[index].setValue(valueRemoved);
					generated.squares[index].isMutable = false;
				} else {
					// SUCCESS, try next index
					generated.squares[index].isMutable = true;
				}
				attempts++;
				if (attempts > difficulty.squaresToRemove*2) {
					// Ran out of attempts, generate a new puzzle
					squaresRemoved = Integer.MAX_VALUE;
					return generatePuzzle(difficulty);
				}
			} else {
				squaresRemoved--;
			}

		}
		return generated;
    }
     
    /**
     * Public driver method for the recursive fill puzzle 
     */
    public void fillPuzzle(SudokuGame puzzle) {
    	fillPuzzle(puzzle, 0);
    }
    
    /**
     * Fills a sudoku puzzle with random entries, while following sudoku rules
     * @param puzzle The puzzle to fill
     * @param index The index to fill in the puzzle
     * @return If filling this index resulted in a valid puzzle
     */
    private boolean fillPuzzle(SudokuGame puzzle, int index) {
		if (index == puzzle.squares.length) return true;
		Random r = new Random();
		boolean done = false;
		for (int i = 0; i < 9; i++) {
			puzzle.squares[index].setValue(r.nextInt(9)+1);
			if (!puzzle.hasErrorAt(index)) {
				done = fillPuzzle(puzzle, index+1);
				if (done) return true;
			}
		}
		puzzle.squares[index].setValue(0);
		
		return false;
    }
    
}