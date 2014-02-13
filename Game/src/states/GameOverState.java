package states;
import java.io.IOException;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
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
		
		LoadingList.get().add(new DeferredResource(){
			public void load() throws IOException{
				
                try { 
                    //create the resource
                    
            		gameOverBackground = new Image("res/menu_graphics/gameover_screen.png");
                } catch (SlickException e) {
                    throw new IOException("error loading image");
                }
                
              
            }

            public String getDescription() {
                return "game over background image";
            }
			
		});

		LoadingList.get().add(new DeferredResource(){
			public void load() throws IOException{
				
                try { 
                    //create the resource
                    
                	playAgainButton = new Image("res/menu_graphics/playagain_button.png");
                } catch (SlickException e) {
                    throw new IOException("error loading image");
                }
                
              
            }

            public String getDescription() {
                return "play again button image";
            }
			
		});
	
		LoadingList.get().add(new DeferredResource(){
			public void load() throws IOException{
				
                try { 
                    //create the resource
                    
                	quitButton = new Image("res/menu_graphics/quit_button.png");
                } catch (SlickException e) {
                    throw new IOException("error loading image");
                }
                
              
            }

            public String getDescription() {
                return "quit button image";
            }
			
		});
		
		LoadingList.get().add(new DeferredResource(){
			public void load() throws IOException{
				
                try { 
                    //create the resource
                    
                	menuButton = new Image("res/menu_graphics/menu_button.png");
                } catch (SlickException e) {
                    throw new IOException("error loading image");
                }
                
              
            }

            public String getDescription() {
                return "menu button image";
            }
			
		});
	
		LoadingList.get().add(new DeferredResource(){
			public void load() throws IOException{
				
                try { 
                    //create the resource
                    
                	playAgainHover = new Image("res/menu_graphics/playagain_hover.png");
                } catch (SlickException e) {
                    throw new IOException("error loading image");
                }
                
              
            }

            public String getDescription() {
                return "play again hover image";
            }
			
		});
		
		LoadingList.get().add(new DeferredResource(){
			public void load() throws IOException{
				
                try { 
                    //create the resource
                    
                	quitHover = new Image("res/menu_graphics/quit_hover.png");
                } catch (SlickException e) {
                    throw new IOException("error loading image");
                }
                
              
            }

            public String getDescription() {
                return "quit hover image";
            }
			
		});
		
		LoadingList.get().add(new DeferredResource(){
			public void load() throws IOException{
				
                try { 
                    //create the resource
                    
                	menuHover = new Image("res/menu_graphics/menu_hover.png");
                } catch (SlickException e) {
                    throw new IOException("error loading image");
                }
                
              
            }

            public String getDescription() {
                return "menu hover image";
            }
			
		});
		
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
