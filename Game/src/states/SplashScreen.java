package states;

import java.awt.Rectangle;
import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class SplashScreen extends BasicGameState {

	private Image splash;
	private LoadingList loading = LoadingList.get(); 
	
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
		
		int	ox = stateContainer.Game.MAXIMUMWIDTH /4,
			oy = stateContainer.Game.MAXIMUMHEIGHT /16;
		Rectangle 
			loadBase = new Rectangle(ox, 10*oy, 2*ox, oy),
			loadFill = new Rectangle(loadBase);	
		loadFill.grow(-4, -4);
		loadFill.width = loadFill.width -
					((loadFill.width * loading.getRemainingResources()) 
							/ loading.getTotalResources());
		
		
		g.setColor(Color.white);
		g.fillRoundRect(loadBase.x, loadBase.y, 
				loadBase.width, loadBase.height, oy/3);
		g.setColor(Color.black);
		g.fillRoundRect(loadFill.x, loadFill.y, 
				loadFill.width, loadFill.height, oy/3);	
	}
	

	@Override
	public void update(GameContainer gc, StateBasedGame s, int delta)
			throws SlickException {
		
		if (loading.getRemainingResources() == 0){
			s.enterState(stateContainer.Game.MENUSTATE);
		}
		else try {
			loading.getNext().load();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	@Override
	public int getID() {
		return 0;
	}

	
	
}
