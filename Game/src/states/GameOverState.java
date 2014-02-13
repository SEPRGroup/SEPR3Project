package states;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;



public class GameOverState extends BasicGameState {
	
	private static Image 
		quitButton, menuButton, playAgainButton,   
		quitHover, menuHover, playAgainHover,
		gameOverBackground;
	
	
	public GameOverState(int state) {
		
	}
	
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
				throws SlickException {
		
		gameOverBackground = new Image("res/menu_graphics/gameover_screen.png");
		playAgainButton = new Image("res/menu_graphics/playagain_button.png");
		quitButton = new Image("res/menu_graphics/quit_button.png");
		menuButton = new Image("res/menu_graphics/menu_button.png");
		playAgainHover = new Image("res/menu_graphics/playagain_hover.png");
		quitHover = new Image("res/menu_graphics/quit_hover.png");
		menuHover = new Image("res/menu_graphics/menu_hover.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
				throws SlickException{
		
		gameOverBackground.draw(0,0);
		
		int	posX = Mouse.getX();
		int posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();
			//Fixing posY to reflect graphics coords
		
		if (posX>728&&posX<844&&posY>380&&posY<426)
			menuHover.draw(728,380);
		else menuButton.draw(728,380);
		
		if (posX>354&&posX<582&&posY>380&&posY<424)
			playAgainHover.draw(354,380);
		else playAgainButton.draw(354,380);
		
		if ((posX > 1150 && posX < 1170) && (posY > 550 && posY < 580))
			quitHover.draw(1148,556);
		else quitButton.draw(1148,556);
		
		g.setColor(Color.white);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)throws SlickException {
		
		int posX = Mouse.getX(),
			posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();
		

		if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)){
			if(posX>354&&posX<582&&posY>380&&posY<424) {
				sbg.enterState(stateContainer.Game.PLAYSTATE);
			}
			
			if(posX>728&&posX<844&&posY>380&&posY<426) { // 116 46
				sbg.enterState(stateContainer.Game.MENUSTATE);
			}
			
			if((posX > 1150 && posX < 1170) && (posY > 550 && posY < 580)) {
				System.exit(0);
			}
		}
		
	}

	@Override
	public int getID() {
		return stateContainer.Game.GAMEOVERSTATE;
	}
}
