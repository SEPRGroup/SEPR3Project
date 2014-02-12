package stateContainer;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import states.GameOverState;
import states.MenuState;
import states.PauseState;
import states.PlayState;
import states.CreditsState;
import states.ControlsState;
import states.SplashScreen;

public class Game extends StateBasedGame {

	public static final String NAME = "Turbulence";
	public static final int SPLASHSTATE = 0;
	public static final int MENUSTATE = 1;
	public static final int PLAYSTATE = 2;
	public static final int GAMEOVERSTATE = 3;
	public static final int PAUSESTATE = 4;
	public static final int CREDITSSTATE = 5;
	public static final int CONTROLSSTATE = 6;
	
	public static final int MAXIMUMWIDTH = 1200;
	public static final int MAXIMUMHEIGHT = 600;

	/**
	 * Adds all states to a container 
	 * @param NAME The game's title
	 */

	public Game(String NAME) {
		super(NAME);
		this.addState(new SplashScreen(SPLASHSTATE));
		this.addState(new MenuState(MENUSTATE));
		this.addState(new PlayState(PLAYSTATE));
		this.addState(new GameOverState(GAMEOVERSTATE));
		this.addState(new PauseState(PAUSESTATE));
		this.addState(new CreditsState(CREDITSSTATE));
		this.addState(new ControlsState(CONTROLSSTATE));
		this.enterState(SPLASHSTATE);
	}

	public void initStatesList(GameContainer gc) throws SlickException {	

	}

	public static void main(String[] args) {
		AppGameContainer appgc;
		
//	    fLogger.info("Showing the main window.");
		try {
			appgc = new AppGameContainer(new Game(NAME));
			appgc.setDisplayMode(MAXIMUMWIDTH, MAXIMUMHEIGHT, false);
			appgc.setTargetFrameRate(60);
			appgc.setIcon("res/graphics/icon.png");
			appgc.start();
			
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		
	}	
}