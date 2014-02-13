package states;

//import java.awt.Font;
//import java.io.InputStream;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
//import org.newdawn.slick.util.ResourceLoader;


public class CreditsState extends BasicGameState {
	
	private static Image
		menuButton, menuHover, menuBackground;
	//private static TrueTypeFont font;
	
	private String[][] credits;	//[section, line]

	
	public CreditsState(int state){
		
	}
	
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {

		menuBackground = new Image("res/menu_graphics/menu_screen.png");
		menuButton = new Image("res/menu_graphics/menu_button.png");
		menuHover = new Image("res/menu_graphics/menu_hover.png");
		
		/*try {
			Font awtFont = new Font("Courier New", Font.PLAIN, 20);
			font = new TrueTypeFont(awtFont, false);
		} catch(Exception e){
			e.printStackTrace();
		}*/

		credits = new String[][] {
				{"Music Assets",
					"\"Jarvic 8\" Kevin MacLeod (incompetech.com)",
					"Licensed under Creative Commons: By Attribution 3.0",
					"http://creativecommons.org/licenses/by/3.0/"
				},
				{"Images",
					"Background image attributed to Rijksdienst voor het Cultureel Erfgoed",
					"This files are licensed under the Creative Commons Attribution-Share Alike 3.0 Netherlands license.",
					"Clouds image attirubted to Keith Pomakis"
				}				
		};
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException{
		
		int posX = Mouse.getX();
		int posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();
			//Fixing posY to reflect graphics coords
	
		menuBackground.draw(0,0);
		
		if (posX>20 && posX< 136 && posY>20 && posY<66)
			menuHover.draw(20,20);
		else menuButton.draw(20,20);
		
		//draw background panel
		g.setColor(new Color(250, 235, 215, 50));	//pale orange, semi-transparent
		g.fillRoundRect (50, 230, 1100, 320, 5);
				
		{	//draw credits screen
			g.setColor(Color.white);
			int y = 240;
			for (String[] section: credits){
				for (String line: section){
					//font.drawString(60, y, line);
					g.drawString(line, 60, y);
					y += 15;
				}
				y += 30;
			}
		}
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
	
		int posX = Mouse.getX();
		int posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();
			//Fixing posY to reflect graphics coords
	
		if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			
			if (posX>20 && posX<136 && posY>20 && posY<66) {
				sbg.enterState(stateContainer.Game.MENUSTATE);
			}
		}	
		
	}

	@Override
	public int getID(){
		return stateContainer.Game.CREDITSSTATE;
	}	
	
}
