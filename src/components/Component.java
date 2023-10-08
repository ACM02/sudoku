package components;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * Component.java - A framework to build components from
 * @since Apr. 8, 2021
 * @author A. McLeod
 */
public abstract class Component {

    protected Rectangle hitbox;
    
    /**
     * Gets the x position of this component
     * @return The x position of this component
     */
    public int getX(){
        return hitbox.x;
    }
    
    /**
     * Gets the y position of this component
     * @return The y position of this component
     */
    public int getY(){
        return hitbox.y;
    }
    
    /**
     * Sets the x position of this component
     * @param xPos The x position to set the component to
     */
    public void setX(int xPos){
        hitbox.x = xPos;
    }
    
    /**
     * Sets the y position of this component
     * @param yPos The y position to set the component to
     */
    public void setY(int yPos){
        hitbox.y = yPos;
    }
    
    /**
     * Sets the position of this component to a point p
     * @param p The point to move this component to
     */
    public void setPosition(Point p) {
    	hitbox.x = p.x;
    	hitbox.y = p.y;
    }
        
    /**
     * Gets the width of this component
     * @return The width of this component
     */
    public int getWidth(){
        return hitbox.width;
    }
            
    /**
     * Gets the height of this component
     * @return The height of this component
     */
    public int getHeight(){
        return hitbox.height;
    }
    
    /**
     * Sets the width of this component
     * @param width The width of the component. Must be >= 0.
     */
    public void setWidth(int width){
        if (width >= 0) hitbox.width = width;
    }
       
    /**
     * Sets the height of this component
     * @param height The height of the component. Must be >= 0.
     */
    public void setHeight(int height){
    	if (height >= 0) hitbox.height = height;
    }
}
