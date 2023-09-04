package sudoku;

public enum Difficulty {

	EASY(20), MEDIUM(30), HARD(40), IMPOSSIBLE(55);
	
	public int squaresRemoved;
	
	Difficulty(int squaresRemoved) {
		this.squaresRemoved = squaresRemoved;
	}
}