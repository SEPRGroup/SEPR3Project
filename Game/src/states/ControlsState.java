package states;

import java.io.IOException;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class ControlsState extends BasicGameState {
		
	private static Image 
		nextPageButton, previousPageButton, backButton, quitButton, 
		nextPageHover, previousPageHover, backHover, quitHover,
		controlsBackgroundPage1, controlsBackgroundPage2;
	
	private int pageNumber;
    
	public ControlsState(int state){
		
	}
	
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		pageNumber = 1;
		
		
			LoadingList.get().add(new DeferredResource(){
				public void load() throws IOException{
					
	                try { 
	                    //create the resource
	                    
	                	controlsBackgroundPage1 = new Image("res/menu_graphics/controls1.jpg");
	                } catch (SlickException e) {
	                    throw new IOException("error loading image");
	                }
	               
	            }
	
	            public String getDescription() {
	                return "control background page 1 image";
	            }
			
			});
			
			LoadingList.get().add(new DeferredResource(){
				public void load() throws IOException{
					
	                try { 
	                    //create the resource
	                    
	                	controlsBackgroundPage2 = new Image("res/menu_graphics/controls2.jpg");
	                } catch (SlickException e) {
	                    throw new IOException("error loading image");
	                }
	               
	            }
	
	            public String getDescription() {
	                return "control background page 2 image";
	            }
			
			});
		
			LoadingList.get().add(new DeferredResource(){
				public void load() throws IOException{
					
	                try { 
	                    //create the resource
	                    
	                	backButton = new Image("res/menu_graphics/back.png");
	                } catch (SlickException e) {
	                    throw new IOException("error loading image");
	                }
	               
	            }
	
	            public String getDescription() {
	                return "back button image";
	            }
			
			});
		
			LoadingList.get().add(new DeferredResource(){
				public void load() throws IOException{
					
	                try { 
	                    //create the resource
	                    
	                	backHover = new Image("res/menu_graphics/back_hover.png");
	                } catch (SlickException e) {
	                    throw new IOException("error loading image");
	                }
	               
	            }
	
	            public String getDescription() {
	                return "back hover image";
	            }
			
			});
		
			LoadingList.get().add(new DeferredResource(){
				public void load() throws IOException{
					
	                try { 
	                    //create the resource
	                    
	                	nextPageButton = new Image("res/menu_graphics/next page.png");
	                } catch (SlickException e) {
	                    throw new IOException("error loading image");
	                }
	               
	            }
	
	            public String getDescription() {
	                return "next page button image";
	            }
			
			});
			
			LoadingList.get().add(new DeferredResource(){
				public void load() throws IOException{
					
	                try { 
	                    //create the resource
	                    
	                	nextPageHover = new Image("res/menu_graphics/next page_hover.png");
	                } catch (SlickException e) {
	                    throw new IOException("error loading image");
	                }
	               
	            }
	
	            public String getDescription() {
	                return "next page hover image";
	            }
			
			});
		
			LoadingList.get().add(new DeferredResource(){
				public void load() throws IOException{
					
	                try { 
	                    //create the resource
	                    
	                	previousPageButton = new Image("res/menu_graphics/previous page.png");
	                } catch (SlickException e) {
	                    throw new IOException("error loading image");
	                }
	               
	            }
	
	            public String getDescription() {
	                return "previous page button image";
	            }
			
			});
			
			LoadingList.get().add(new DeferredResource(){
				public void load() throws IOException{
					
	                try { 
	                    //create the resource
	                    
	                	previousPageHover = new Image("res/menu_graphics/previous hover.png");
	                } catch (SlickException e) {
	                    throw new IOException("error loading image");
	                }
	               
	            }
	
	            public String getDescription() {
	                return "previous page hover image";
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
			
	
	
	
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException{
		
		int	posX = Mouse.getX(),
			posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();
			//Fixing posY to reflect graphics coords
		
		if (pageNumber == 1) {
			
			controlsBackgroundPage1.draw(0,0);
			
			if (posX>1030 && posX<1193 && posY>280 && posY<315)
				nextPageHover.draw(1030,280);
			else nextPageButton.draw(1030,280);
			
			//menuButton.draw(1050, 20);
			
			if (posX>1150 && posX<1170 && posY>550 && posY<580)
				quitHover.draw(1148,556);
			else quitButton.draw(1148,556);
			
		}
		
		else if (pageNumber == 2){
			controlsBackgroundPage2.draw(0,0);
			
			if (posX>30 && posX<241 && posY>280 && posY<315)
				previousPageHover.draw(30,280);
			else previousPageButton.draw(30,280);
			
			//menuButton.draw(1050, 20);
			
			if (posX>1150 && posX<1170 && posY>550 && posY<580)
				quitHover.draw(1148,556);
			else quitButton.draw(1148,556);	
		}
		
		if (posX>20 && posX<40 && posY>20 && posY<40)
			backHover.draw(20,20);
		else backButton.draw(20,20);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

		int	posX = Mouse.getX(),
			posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();

		if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			
			if (posX>20 && posX<40 && posY>20 && posY<40) {
				pageNumber = 1;
				sbg.enterState(stateContainer.Game.MENUSTATE);
			}
			
			if (posX>1150 && posX<1170 && posY>550 && posY<580) {
				System.exit(0);
			}

			if (pageNumber == 1){
				if (posX>1030 && posX<1193 && posY>280 && posY<315) {
					pageNumber = 2;
				}
			}

			if (pageNumber == 2){
				if (posX>30 && posX<241 && posY>280 && posY<315) {
					pageNumber = 1;
				}
			}
		}
		
	}

	public int getID(){
		return stateContainer.Game.CONTROLSSTATE;
	}
	
}
