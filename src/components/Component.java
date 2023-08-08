package components;

import java.awt.Rectangle;

/**
 *
 * @since Apr. 8, 2021
 * @author a.mcleod
 */
public abstract class Component {

    public Rectangle hitbox;
    
    public int getX(){
        return hitbox.x;
    }
    
    public int getY(){
        return hitbox.y;
    }
        
    public int getWidth(){
        return hitbox.width;
    }
            
    public int getHeight(){
        return hitbox.height;
    }
}
