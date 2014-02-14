package states;

import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;

import logicClasses.Airspace;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Image;

import util.DeferredFile;


public class PlayState extends BasicGameState {
	private static Image 
		easyButton, mediumButton,hardButton,  
		easyHover, mediumHover, hardHover,  
		backgroundImage, difficultyBackground,
		controlBarImage, clockImage, cursorImg;
	private static Sound endOfGameSound;
	private static Music gameplayMusic;
	private static TrueTypeFont font;	
	public static float time;

	private Airspace airspace;
	// added in state field 
	private String stringTime;
	private boolean settingDifficulty, gameEnded;

	public PlayState(int state) {
		
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		gameEnded = false;
		settingDifficulty = true;
		time = 0;
		airspace = new Airspace();
		// Removed i as not necessary 
		this.stringTime="";
		
		gc.setAlwaysRender(true);
		gc.setUpdateOnlyWhenVisible(true);
		// Set mouse cursor
		//gc.setMouseCursor("res/graphics/cross.png",12,12);
	
		
		// Font
		try{
			InputStream inputStream = ResourceLoader.getResourceAsStream("res/blue_highway_font/bluehigh.ttf");
			Font awtFont= Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont = awtFont.deriveFont(20f);
			font = new TrueTypeFont(awtFont, true);		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		{
			LoadingList loading = LoadingList.get();
			
			// Music
			loading.add(new DeferredFile("res/music/Jarvic 8.ogg"){
				public void load() throws IOException{
					try { 
						gameplayMusic = new Music(filename);
					} catch (SlickException e) {
						throw new IOException("error loading music");
					}
				}
			});
			
			loading.add(new DeferredFile("res/music/175385__digitaldominic__scream.wav"){
				public void load() throws IOException{
					try { 
						endOfGameSound = new Sound(filename);
					} catch (SlickException e) {
						throw new IOException("error loading sound");
					}
				}
			});

			//Images
			loading.add(new DeferredFile("res/graphics/control_bar_vertical.png"){
				public void load() throws IOException{
					try { 
						controlBarImage = new Image(filename);
					} catch (SlickException e) {
						throw new IOException("error loading:\t" +filename);
					}
				}
			});

			loading.add(new DeferredFile("res/graphics/clock.png"){
				public void load() throws IOException{
					try { 
						clockImage = new Image(filename);
					} catch (SlickException e) {
						throw new IOException("error loading:\t" +filename);
					}
				}
			});

			loading.add(new DeferredFile("res/graphics/background.png"){
				public void load() throws IOException{
					try { 
						backgroundImage = new Image(filename);
					} catch (SlickException e) {
						throw new IOException("error loading:\t" +filename);
					}
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/difficulty.jpg"){
				public void load() throws IOException{
					try { 
						difficultyBackground = new Image(filename);
					} catch (SlickException e) {
						throw new IOException("error loading:\t" +filename);
					}
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/easy.png"){
				public void load() throws IOException{
					try { 
						easyButton = new Image(filename);
					} catch (SlickException e) {
						throw new IOException("error loading:\t" +filename);
					}
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/easy_hover.png"){
				public void load() throws IOException{
					try { 
						easyHover = new Image(filename);
					} catch (SlickException e) {
						throw new IOException("error loading:\t" +filename);
					}
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/medium.png"){
				public void load() throws IOException{
					try { 
						mediumButton = new Image(filename);
					} catch (SlickException e) {
						throw new IOException("error loading:\t" +filename);
					}

				}
			});

			loading.add(new DeferredFile("res/menu_graphics/medium_hover.png"){
				public void load() throws IOException{
					try {mediumHover = new Image(filename);
					} catch (SlickException e) {
						throw new IOException("error loading:\t" +filename);
					}
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/hard.png"){
				public void load() throws IOException{
					try { 
						hardButton = new Image(filename);
					} catch (SlickException e) {
						throw new IOException("error loading:\t" +filename);
					}
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/hard_hover.png"){
				public void load() throws IOException{
					try { 
						hardHover = new Image(filename);
					} catch (SlickException e) {
						throw new IOException("error loading:\t" +filename);
					}
				}
			});
		}
		
		//initialise the airspace object;
    	//Waypoints
    	airspace.newWaypoint(350, 150, "A");
    	airspace.newWaypoint(400, 470, "B");
    	airspace.newWaypoint(700, 60,  "C");
    	airspace.newWaypoint(800, 320, "D");
    	airspace.newWaypoint(600, 418, "E");
    	airspace.newWaypoint(500, 220, "F");
    	airspace.newWaypoint(950, 188, "G");
    	airspace.newWaypoint(1050, 272,"H");
    	airspace.newWaypoint(900, 420, "I");
    	airspace.newWaypoint(240, 250, "J");
    	//EntryPoints
    	airspace.newEntryPoint(150, 400);
    	airspace.newEntryPoint(1200, 200);
    	airspace.newEntryPoint(600, 0);
    	// Exit Points
    	airspace.newExitPoint(800, 0, "1");
    	airspace.newExitPoint(150, 200, "2");
    	airspace.newExitPoint(1200, 300, "3");
    	
    	airspace.init(gc);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		// Checks whether the user is still choosing the difficulty
		
		if(settingDifficulty){

			int posX = Mouse.getX();
			int posY= stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();
				//Fixing posY to reflect graphics coords

			difficultyBackground.draw(0,0);

			if (posX>100 && posX<216 && posY>300 && posY<354)
				easyHover.draw(100,300);
			else easyButton.draw(100,300);
			
			if (posX>100 && posX<284 && posY>400 && posY<454)
				mediumHover.draw(100,400);
			else mediumButton.draw(100,400);
			
			if (posX>100 && posX<227 && posY>500 && posY<554)
				hardHover.draw(100,500);
			else hardButton.draw(100,500);
		}
		
		else{	//main game
			g.setFont(font);
			
			// Drawing Side Images
			backgroundImage.draw(150,0);
			controlBarImage.draw(0,0);
			
			// Drawing Airspace and elements within it
			g.setColor(Color.white);
			airspace.render(g, gc);
					
			// Drawing Clock and Time
			g.setColor(Color.white);
			clockImage.draw(0,5);
			g.drawString(this.stringTime, 25, 11);
			
			// Drawing Score
			g.setColor(Color.white);
			g.drawString(airspace.getScore().toString(), 10, 101);
		
		}	

	}
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		// Checks if the game has been retried and if it has resets the airspace
		
		if (gameEnded){
	
			airspace.resetAirspace();
	    	time = 0;
	    	gameEnded = false;
	    	settingDifficulty = true;
	    	airspace.getScore().resetScore();
		}
		
		// Checks whether the user is still choosing the difficulty
		
		if(settingDifficulty){
		
			int posX = Mouse.getX();
			int posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();
			
			if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				if((posX>100&&posX<216) && (posY>300&&posY<354)) {
					
					airspace.setDifficultyValueOfGame(1);
					airspace.getControls().setDifficultyValueOfGame(1);
					airspace.createAndSetSeparationRules();
					settingDifficulty = false;			
				}
				
				
				if((posX>100&&posX<284) && (posY>400&&posY<454)) {
					
					airspace.setDifficultyValueOfGame(2);
					airspace.getControls().setDifficultyValueOfGame(2);
					airspace.createAndSetSeparationRules();
					settingDifficulty = false;	
				}
				
				
				if((posX>100&&posX<227) && (posY>500&&posY<554)) {
					
					airspace.setDifficultyValueOfGame(3);
					airspace.getControls().setDifficultyValueOfGame(3);
					airspace.createAndSetSeparationRules();
					settingDifficulty = false;
				}	
			}
		}
		
		else{	//main game
			
			// Updating Clock and Time
			
			time += delta;
			float decMins=time/1000/60;
			int mins = (int) decMins;
			float decSecs=decMins-mins;
				
			int secs = Math.round(decSecs*60);
				
			String stringMins="";
			String stringSecs="";
			if(secs>=60){
				secs -= 60;
				mins+=1;
				// {!} should do +60 score every minute(possibly) 
				//     - after 3 minutes adds on 2 less points every time?
				airspace.getScore().updateTimeScore();
			}
			if(mins<10) {
				stringMins="0"+mins;
			}
			else {
				stringMins=String.valueOf(mins);
			}
			if(secs<10) {
				stringSecs="0"+secs;
			}
			else {
				stringSecs=String.valueOf(secs);
			}
						
			this.stringTime=stringMins+":"+stringSecs;
						
						
			// Updating Airspace
						
			airspace.newFlight(gc);
			airspace.update(gc);
			if (airspace.getSeparationRules().getGameOverViolation() == true){
				airspace.getSeparationRules().setGameOverViolation(false);
				airspace.resetAirspace();
				gameplayMusic.stop();
				endOfGameSound.play();
				sbg.enterState(stateContainer.Game.GAMEOVERSTATE);
				gameEnded = true;
							
			}					
			
			Input input = gc.getInput();
						
			// Checking For Pause Screen requested in game
						
			if (input.isKeyPressed(Input.KEY_P)) {
				sbg.enterState(stateContainer.Game.PAUSESTATE);
			}			
						
			if (!gameplayMusic.playing()){
				//Loops gameplay music based on random number created in init
							
				gameplayMusic.loop(1.0f, 0.5f);
			}			
		}
	}


	@Override
	public int getID() {
		return stateContainer.Game.PLAYSTATE;
	}

	public Airspace getAirspace() {
		return airspace;
	}

	public void setAirspace(Airspace airspace) {
		this.airspace = airspace;
	}
	


}