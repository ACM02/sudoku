package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Button.java - A button component
 * @since Apr. 8, 2021
 * @author A. McLeod
 */
public class Button extends Component {

    public Color backgroundColor;
    public Color borderColor;
    public Color textColor;
    public ImageIcon icon;
    public JLabel iconLabel;
    public String text;
    
    public Button (int xPos, int yPos, int width, int height) {
        hitbox = new Rectangle(xPos, yPos, width, height);
        iconLabel = new JLabel();
    }
    
    public Button (int xPos, int yPos, int width, int height, ImageIcon icon){
        hitbox = new Rectangle(xPos, yPos, width, height);
        iconLabel = new JLabel(icon);
        iconLabel.setBounds(hitbox);
        this.icon = icon;
    }
    
    /**
     * Draws this component using a graphics object
     * @param g Graphics object to draw using
     */
    public void draw(Graphics g) {
        if (icon == null) {
            if (backgroundColor != null) {
                g.setColor(backgroundColor);
                g.fillRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
            }
            if (borderColor != null) {
                g.setColor(borderColor);
                g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
            }
            if (text != null && !text.equals("")) {
            	int textWidth = g.getFontMetrics().stringWidth(text);
            	int textHeight = g.getFontMetrics().getHeight();
                g.setColor(textColor);
                g.drawString(text, hitbox.x + (int) (hitbox.width * 0.55 - textWidth*0.5), hitbox.y + (int) (hitbox.height * 0.5 + textHeight*.30));
            }
        }
        else {
            iconLabel.setIcon(icon);
            iconLabel.setBounds(hitbox);
        }
        
    }
    
    /**
     * Returns if this component contains the point P
     * @param p The point to check
     * @return If this component contains P
     */
    public boolean contains(Point p) {
        if (p.x > hitbox.x && p.x < hitbox.x + hitbox.width &&
            p.y > hitbox.y && p.y < hitbox.y + hitbox.height){
            return true;
        }
        return false;
    }
}
