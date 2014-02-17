package logicClasses;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.LoadingList;

import util.DeferredFile;


public class Airport {
	
	//FIELDS
	private String airportName = "Nothing"; // {!} needs a name
	private static Image airportImage;
	
	//CONSTRUCTOR
	
	Airport() {
		
	}
	
	
	public void init(GameContainer gc) throws SlickException {
		LoadingList.get().add(new DeferredFile("res/graphics/airport.png"){
			public void loadFile(String filename) throws SlickException{
                airportImage = new Image(filename);
            }	
		});
	}
	
	public void render(Graphics g, GameContainer gc) throws SlickException { 
		
		airportImage.draw(572,197); // Airport image centred in middle of airspace
	} 
	
	
	@Override
	public String toString(){
		String s = "Airport Name: " + airportName;
		return s;
	}
	

}
