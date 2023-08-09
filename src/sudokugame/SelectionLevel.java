package sudokugame;

import java.awt.Color;

public enum SelectionLevel {
	UNSELECTED(null), BACKGROUND(Color.LIGHT_GRAY), FOREGROUND(Color.GRAY);
	
	public Color color;
	
	SelectionLevel(Color color) {
		this.color = color;
	}
}
