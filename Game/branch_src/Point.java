import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;



public class Point {

    //A "Point" object has a (X,Y) co-ordinate, representing a line in the
    //game airspace along the altitude axis. Values set individually as (xCoOrd, 
    // yCoOrd) double variables, initialised by default as 0,0.
    
    //If this helps:
    //String pointRef is the letter reference (Point A would be "A").
    //Class Var. static int PointCount tracks number of Points set up.
    protected double x;
    protected double y;
    protected String pointRef;
    public void setPointRef(String pointRef) {
		this.pointRef = pointRef;
	}

	protected static int pointCount = 0;
    
    //Point Constructor take two doubles, for x then y.
    Point(double xcoord, double ycoord){
    x = xcoord;
    y = ycoord;
    pointCount += 1;
    }
    
    //Point Constructor that also takes pointRef string:
    Point(double xcoord, double ycoord, String name){
    x = xcoord; 
    y = ycoord;
    //pointRef = name;
    pointCount += 1;
    System.out.println("Point " + pointRef + " set:(" + x + "," + y +").");
    }
    
    //Constructor pointing out that X,Y has not been set.
    Point(){
    System.out.println("Warning: Constructor method for Point Object called, "
            + "point set as (0,0) with no point ref.");
    pointCount +=1;
    }
    
    
    //Accessor methods for xCoOrd double, for yCoOrd double, 
    //for pointRef string, for pointcount int.
    double getX() {return this.x;}
    double getY() {return this.y;}
    String getPointRef() {return this.pointRef;}
    int getPointCount() {return Point.pointCount;}
    
    //Mutator for setting xCoOrd and yCoOrd. Each method takes a sole double.
    void setX(double newX) {this.x = newX;}
    void setY(double newY) {this.y = newY;}
    
    public void render(Graphics g, Airspace airspace) {
    	
    	if(airspace.get_selected_flight() !=null){
    		if (airspace.get_selected_flight().getFlight_plan().getWaypoints().contains(this)){
    			if (airspace.get_selected_flight().getFlight_plan().getWaypoints().indexOf(this)==0){
    				g.setColor(Color.blue);
        	    	g.fillOval((int)this.x -10, (int)this.y - 10, 20, 20);
    			}
    			else{
    			g.setColor(Color.green);
    	    	g.fillOval((int)this.x -10, (int)this.y - 10, 20, 20);
    			}
    		}
    		else{
    			g.setColor(Color.yellow);
    	    	g.fillOval((int)this.x -10, (int)this.y - 10, 20, 20);
    		}
    		
    	}
    	else{
    		g.setColor(Color.yellow);
	    	g.fillOval((int)this.x -10, (int)this.y - 10, 20, 20);
    	}
    	g.setColor(Color.black);
    	g.drawString(this.pointRef, (int)this.x-5, (int)this.y-9);
    	
    	
    	
    	
    }




}
