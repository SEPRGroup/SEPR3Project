package logicClasses;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class ExitPoint extends Point {

	static Image exitPointTop, exitPointRight, exitPointLeft;

	public ExitPoint(double xcoord, double ycoord, String name){
		super(xcoord, ycoord, name);

		System.out.println("ExitPoint " + pointRef + " set:(" + x + "," + y +").");
	}

	/**
	 * init: Initialises the variables and resources required for the ExitPoint object render (Sets ExitPoint Images)
	 * @param gc Game container required by Slick2d
	 * @throws SlickException 
	 */

	public void init(GameContainer gc) throws SlickException {

		if (exitPointTop == null)
			exitPointTop = new Image("res/graphics/exitpoint_top.png");
		if (exitPointRight == null)
			exitPointRight = new Image("res/graphics/exitpoint_right.png");
		if (exitPointLeft == null)
			exitPointLeft = new Image("res/graphics/exitpoint_left.png");
	}

	/**
	 * render: Render method for the ExitPoint object, position determines orientation of image and String of name
	 * @param g Graphics required by Slick2d 
	 * @param airspace Airspace object
	 * @throws SlickException 
	 */

	public void render(Graphics g, Airspace airspace) throws SlickException {

		if(y == 0){
			exitPointTop.draw((int)x-20, (int)y);
		}

		else if(x == 150){
			exitPointLeft.draw((int)x, (int)y-20);
		}

		if(x == 1200){
			exitPointRight.draw((int)x-40, (int)y-20);
		}


		g.setColor(Color.white);
		if(y == 0){
			g.drawString(pointRef, (int)x-15, (int)y);
		}
		else if(x ==150){
			g.drawString(pointRef, (int)x, (int)y-7);
		}

		else if(x ==1200){
			g.drawString(pointRef, (int)x-35, (int)y-7);
		}

	}

}
