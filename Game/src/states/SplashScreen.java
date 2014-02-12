package states;

import org.newdawn.slick.Image;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class SplashScreen extends BasicGameState {

	private Image splash;
	
	private int elapsedTime;
	private final int DELAY = 6000;
	
	public SplashScreen(int state){
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame s)
			throws SlickException {
		splash = new Image("res/graphics/startup_bg.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame s, Graphics g)
			throws SlickException {
		g.drawImage(splash, 0, 0);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame s, int delta)
			throws SlickException {
		elapsedTime += delta;
		
		if(elapsedTime >= DELAY){
			s.enterState(1);
		}
	}

	@Override
	public int getID() {
		return 0;
	}

	
	
}
