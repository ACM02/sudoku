package sudokugame;

import java.awt.Color;

/**
 * Difficulty.java
 *
 * @author A. McLeod
 * @since Aug. 8, 2023
 */
public enum SelectionLevel {
	UNSELECTED(null), BACKGROUND(Color.LIGHT_GRAY), FOREGROUND(Color.GRAY);
	
	public Color color;
	
	SelectionLevel(Color color) {
		this.color = color;
	}
}
