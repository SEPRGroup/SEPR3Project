package logicClasses;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class EntryPoint extends Point {
	
	static Image entryPointTop, entryPointRight, entryPointLeft;

    public EntryPoint(double xcoord, double ycoord) {
    	super(xcoord, ycoord);
    }
    
    public void init(GameContainer gc) throws SlickException {
    	
    /**
     * init: Initialises the variables and resources required for the EntryPoint object render (Sets EntryPoint Images)
     * @param gc Game container required by Slick2d
     * @throws SlickException
     */
    	
    	if (entryPointTop == null)
    		entryPointTop = new Image("res/graphics/entrypoint_top.png");
    	if (entryPointRight == null)
    		entryPointRight = new Image("res/graphics/entrypoint_right.png");
    	if (entryPointLeft == null)
    		entryPointLeft = new Image("res/graphics/entrypoint_left.png");

	}
    
    /**
	 * render: Render method for the EntryPoint object, position determines orientation of image
	 * @param g Graphics required by Slick2d
	 * @throws SlickException
	 */
    
	public void render(Graphics g) throws SlickException {
		
		if(y == 0){
			entryPointTop.draw((int)x-20, (int)y);
		}
		
		else if(x == 150){
			entryPointLeft.draw((int)x, (int)y-20);
		}
		
		else if(x == 1200){
			entryPointRight.draw((int)x-40, (int)y-20);
		}
    }
	


}
