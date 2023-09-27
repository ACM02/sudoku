package sudokugame;

public enum Difficulty {

	EASY(20), MEDIUM(30), HARD(40), IMPOSSIBLE(55);
	
	public int squaresToRemove;
	
	Difficulty(int squaresToRemove) {
		this.squaresToRemove = squaresToRemove;
	}
}
