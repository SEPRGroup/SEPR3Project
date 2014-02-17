package states;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import util.DeferredFile;



public class MenuState extends BasicGameState {
	public static TrueTypeFont font;
	private static Image 
		menuBackground,
		playButton, quitButton, creditsButton, controlsButton, 
		playHover, quitHover, creditsHover, controlsHover;
	
	private boolean mouseBeenReleased;

	public MenuState(int state) {
		this.mouseBeenReleased=false;
	}

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		{
			LoadingList loading = LoadingList.get();
			
			loading.add(new DeferredFile("res/menu_graphics/menu_screen.png"){
				public void loadFile(String filename) throws SlickException{
					menuBackground = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/play_button.png"){
				public void loadFile(String filename) throws SlickException{
					playButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/play_hover.png"){
				public void loadFile(String filename) throws SlickException{
					playHover = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/quit_button.png"){
				public void loadFile(String filename) throws SlickException{
					quitButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/quit_hover.png"){
				public void loadFile(String filename) throws SlickException{
					quitHover = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/credits.png"){
				public void loadFile(String filename) throws SlickException{
					creditsButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/credits_hover.png"){
				public void loadFile(String filename) throws SlickException{
					creditsHover = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/controls_silver.png"){
				public void loadFile(String filename) throws SlickException{
					controlsButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/controls_hover.png"){
				public void loadFile(String filename) throws SlickException{
					controlsHover = new Image(filename);
				}
			});
			
		}
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setColor(Color.white);
		g.setFont(font);
		menuBackground.draw(0,0);

		int	posX = Mouse.getX(),
			posY = Mouse.getY();

		if ((posX > 439 && posX < 762) && (posY > 165 && posY < 255)){
			playHover.draw(439,349);
		}
		else{
			playButton.draw(439,349);
		}

		if((posX > 1140 && posX < 1262) && (posY > 25 && posY < 50)){
			quitHover.draw(1148,556);
		}
		else{
			quitButton.draw(1148,556);
		}

		if (posX>20 && posX< 136 && posY>20 && posY<66){
			creditsHover.draw(20,534);
		} else {
			creditsButton.draw(20,534);
		}

		if (posX>490 && posX<725 && posY>20 && posY<66){
			controlsHover.draw(490,534);
		} else {
			controlsButton.draw(490,534);
		}

		g.setColor(Color.white);
		gc.setShowFPS(false);

	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

		int	posX = Mouse.getX(),
			posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();
		// Mapping Mouse coords onto graphics coords

		/*if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			System.out.println(posX);
			System.out.println(posY);
		}*/
		
		if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {	
			if(mouseBeenReleased){	//button first pressed
				mouseBeenReleased = false;
				
				if ((posX > 439 && posX < 762) && (posY > 349 && posY < 439)) {
					sbg.enterState(stateContainer.Game.PLAYSTATE);
				}
				
				if ((posX > 490 && posX < 725) && (posY > 534 && posY < 596)) {
					sbg.enterState(stateContainer.Game.CONTROLSSTATE);
				} 

				if ((posX > 1148 && posX < 1172) && (posY > 556 && posY < 582)) {
					System.exit(0);
				}

				if( (posX>20 && posX< 178 && posY>534 && posY<575) ) {	
					sbg.enterState(stateContainer.Game.CREDITSSTATE);
				}
			}
			/* else mouse is dragged*/
		}	
		else if (!mouseBeenReleased){	//mouse just released
			mouseBeenReleased = true;
		}
	}

	public int getID() {
		return stateContainer.Game.MENUSTATE;
	}

}
