package logicClasses;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;



public class Airport {
	
	//FIELDS
	private String airportName = "Nothing"; // {!} needs a name
	Image airportImage;
	
	//CONSTRUCTOR
	
	Airport() {
		
	}
	
	public void init(GameContainer gc) throws SlickException {
		LoadingList.get().add(new DeferredResource(){
			public void load() throws IOException{
                try { 
                    //create the resource
                    
                    airportImage = new Image("res/graphics/airport.png");
                } catch (SlickException e) {
                    throw new IOException("error loading image");
                }
            }

            public String getDescription() {
                return "airport image";
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
