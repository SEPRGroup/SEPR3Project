package states;

import java.io.IOException;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.loading.DeferredResource;
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
		LoadingList.get().add(new DeferredResource(){
			public void load() throws IOException{
				
                try { 
                    //create the resource
                    
                	menuBackground = new Image("res/menu_graphics/menu_screen.png");
                } catch (SlickException e) {
                    throw new IOException("error loading image");
                }
                
              
            }

            public String getDescription() {
                return "menu background image";
            }
			
		});
	
		LoadingList.get().add(new DeferredResource(){
			public void load() throws IOException{
				
                try { 
                    //create the resource
                    
                	playButton = new Image("res/menu_graphics/play_button.png");
                } catch (SlickException e) {
                    throw new IOException("error loading image");
                }
                
              
            }

            public String getDescription() {
                return "play button image";
            }
			
		});
	
		LoadingList.get().add(new DeferredResource(){
			public void load() throws IOException{
				
                try { 
                    //create the resource
                    
                	playHover = new Image("res/menu_graphics/play_hover.png");
                } catch (SlickException e) {
                    throw new IOException("error loading image");
                }
                
              
            }

            public String getDescription() {
                return "play hover image";
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
                    
                	creditsButton = new Image("res/menu_graphics/credits.png");
                } catch (SlickException e) {
                    throw new IOException("error loading image");
                }
                
              
            }

            public String getDescription() {
                return "credits button image";
            }
			
		});
	
		LoadingList.get().add(new DeferredResource(){
			public void load() throws IOException{
				
                try { 
                    //create the resource
                    
                	creditsHover = new Image("res/menu_graphics/credits_hover.png");
                } catch (SlickException e) {
                    throw new IOException("error loading image");
                }
                
              
            }

            public String getDescription() {
                return "credits hover image";
            }
			
		});
		
		LoadingList.get().add(new DeferredResource(){
			public void load() throws IOException{
				
                try { 
                    //create the resource
                    
                	controlsButton = new Image("res/menu_graphics/controls_silver.png");
                } catch (SlickException e) {
                    throw new IOException("error loading image");
                }
                
              
            }

            public String getDescription() {
                return "controls button image";
            }
			
		});
		
		LoadingList.get().add(new DeferredResource(){
			public void load() throws IOException{
				
                try { 
                    //create the resource
                    
            		controlsHover = new Image("res/menu_graphics/controls_hover.png");
                } catch (SlickException e) {
                    throw new IOException("error loading image");
                }
                
              
            }

            public String getDescription() {
                return "controls hover image";
            }
			
		});

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
