package logicClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class FlightPlan {
	
	// FIELDS
	private static final int A = 0, B = 1, C = 2, D = 3, E = 4, F = 5, G = 6, H = 7, I = 8, J = 9;

	private List<Point> currentRoute = new ArrayList<Point>(); // Array that stores the current list of waypoints
	private List<Point> waypointsAlreadyVisited; // Array that stores all the waypoints the flight has passed through
	private double velocity; // velocity of the aircraft
	private Flight flight; // The flight object associated with the flight plan
	private Point waypointMouseIsOver; // What waypoint is the mouse currently hovering over
	private Point waypointClicked;
	private boolean changingPlan; // Is the user currently changing the flight plan?
	private boolean draggingWaypoint;// Is the user currently dragging a waypoint?
	private EntryPoint entryPoint;
	

	// CONSTRUCTOR
	

	public FlightPlan(Airspace airspace, Flight flight) {
		this.flight = flight;
		this.velocity = generateVelocity();
		this.entryPoint = generateEntryPoint(airspace);
		this.currentRoute = buildRoute(airspace, this.entryPoint);
		this.waypointsAlreadyVisited = new ArrayList<Point>();
		this.changingPlan = false;
		this.draggingWaypoint = false;
				
	}

	// METHODS
	
	/**
	 * generateEntryPoint: Creates the entry point for the flight
	 * @param airspace airspace object
	 * @return airspace.getListofEntryPoints 
	 */
	
	public EntryPoint generateEntryPoint(Airspace airspace){
		
		Random rand = new Random();
		int randomNumber = rand.nextInt(3);
			
		// Setting flights x and y to the coordinates of it's entrypoint
		flight.setX(airspace.getListOfEntryPoints().get(randomNumber).getX()); // choose one a get the x and y values
		flight.setY(airspace.getListOfEntryPoints().get(randomNumber).getY());
		
		return airspace.getListOfEntryPoints().get(randomNumber);
		
	}
	
	/**
	 * buildRoute: Creates an array of waypoints that the aircraft will be initially given  
	 * @param airspace airspace object
	 * @param entryPoint entry point object
	 * @return tempRoute
	 */
	
	public ArrayList<Point> buildRoute(Airspace airspace, EntryPoint entryPoint) {
		ArrayList<Point> tempRoute = new ArrayList<Point>();  // Create the array lists for route and points
		ArrayList<Point> tempListOfWaypoints = new ArrayList<Point>();
		ArrayList<Point> tempListOfExitPoints = new ArrayList<Point>();
		Boolean exitpointAdded = false;
		
		if (!airspace.getListOfWaypoints().isEmpty()&& !airspace.getListOfExitPoints().isEmpty()) { // if there is a list of waypoints and a list of exit points
				Random rand = new Random();
				
				// Initialising Temporary Lists
				
				for (int i = 0; i < airspace.getListOfWaypoints().size(); i++) { //loop through all waypoints and add them to tempwaypoints
					tempListOfWaypoints.add(airspace.getListOfWaypoints().get(i));
				}
				
				for (int i = 0; i < airspace.getListOfExitPoints().size(); i++) {// loop through all exit points and add them to temppoints
					tempListOfExitPoints.add(airspace.getListOfExitPoints().get(i));
				}
				
				// Adding ExitPoint to Plan
				
				int ExitPointIndex = rand.nextInt(tempListOfExitPoints.size());
				int a = 0;
				while (exitpointAdded == false){
					// if entrypoint.y is 0 then top point so remove top exit point
					if ((entryPoint.getY() == 0) && (entryPoint.getY() == tempListOfExitPoints.get(ExitPointIndex).getY())){
						tempListOfExitPoints.remove(ExitPointIndex);
						ExitPointIndex = rand.nextInt(tempListOfExitPoints.size());
					}
					// if entrypoint.x is 150 then left point so remove left exit point
					else if ((entryPoint.getX() == 150) && (entryPoint.getX() == tempListOfExitPoints.get(ExitPointIndex).getX())){
						tempListOfExitPoints.remove(ExitPointIndex);
						ExitPointIndex = rand.nextInt(tempListOfExitPoints.size());
					}
					// if entrypoint.x is 1200
					else if ((entryPoint.getX() == 1200) && (entryPoint.getX() == tempListOfExitPoints.get(ExitPointIndex).getX())){
						tempListOfExitPoints.remove(ExitPointIndex);
						ExitPointIndex = rand.nextInt(tempListOfExitPoints.size());
					}
					else{
					
						tempRoute.add(tempListOfExitPoints.get(ExitPointIndex));
						exitpointAdded = true;
					}
				}
//					double[] distanceToExit = new double[tempListOfWaypoints.size()];
//					double[] distanceToWaypoint = new double[tempListOfWaypoints.size()];
//					double distanceLeft = 0;
//					System.out.println(distanceToExit);
					// Adding Waypoints to Plan
//					for (int b = 0; b < 5; b++ ){
//						for (int i = 0; i < tempListOfWaypoints.size(); i++){
//							
//							if(tempRoute.size() == 1){
//								distanceLeft = Math.sqrt(Math.pow((entryPoint.getX() -tempListOfExitPoints.get(ExitPointIndex).getX()),2) 
//										+ Math.pow((entryPoint.getY() -tempListOfExitPoints.get(ExitPointIndex).getY()),2));
//								
//								distanceToExit[i] = Math.sqrt(Math.pow((entryPoint.getX() -tempListOfExitPoints.get(ExitPointIndex).getX()),2) 
//										+ Math.pow((entryPoint.getY() -tempListOfExitPoints.get(ExitPointIndex).getY()),2));
//							}else{
//								distanceLeft = Math.sqrt(Math.pow((tempRoute.get(tempRoute.size()-1).getX() -tempListOfExitPoints.get(ExitPointIndex).getX()),2) 
//										+ Math.pow((tempRoute.get(tempRoute.size()-1).getY() -tempListOfExitPoints.get(ExitPointIndex).getY()),2));
//							
//								Math.sqrt(Math.pow((tempRoute.get(tempRoute.size()-1).getX() -tempListOfExitPoints.get(ExitPointIndex).getX()),2) 
//										+ Math.pow((tempRoute.get(tempRoute.size()-1).getY() -tempListOfExitPoints.get(ExitPointIndex).getY()),2));
//							}
//								
//								distanceToWaypoint[i] = Math.sqrt(Math.pow(entryPoint.getX() - tempListOfWaypoints.get(i).getX(),2) 
//										+ Math.pow(entryPoint.getY() - tempListOfWaypoints.get(i).getY(),2));
//								
//								
//							String str = "distance to waypoint " + i + " is "+ distanceToWaypoint[i];
//							System.out.println(str);
//						}
//						double minValue = 900000;  
//						int minIndex = 1;
//						for( int i = 1;i<distanceToWaypoint.length;i++){
//							if((distanceToWaypoint[i] < minValue)){// && (distanceToExit[i] < distanceLeft )){  
//								minValue = distanceToWaypoint[i];  
//								minIndex = i;
//								String str = "min val " + i + " is " + minValue;
//								System.out.println(str);
//						     }  
//						}  
//						tempRoute.add(a, tempListOfWaypoints.get(minIndex));
//						a++;
//						tempListOfWaypoints.remove(minIndex);
//					}
				
					for ( int i = 0; i < airspace.getListOfEntryPoints().size(); i ++){
						if(entryPoint == airspace.getListOfEntryPoints().get(i)){
							switch (i){
								// entry point on the left
								case 0:
									for (int k = 0; k < airspace.getListOfExitPoints().size(); k++){
										if(tempRoute.get(tempRoute.size()-1) == airspace.getListOfExitPoints().get(k)){
											int randInt = new Random().nextInt(2);
											switch(k){
											
											//left to top
											case 0:
												
												//selects random flight plan
												switch(randInt){
												case 0:
													tempRoute.add(0,tempListOfWaypoints.get(J));
													tempRoute.add(1,tempListOfWaypoints.get(A));
													tempRoute.add(2,tempListOfWaypoints.get(F));
													tempRoute.add(3,tempListOfWaypoints.get(C));
													break;
												case 1:

													tempRoute.add(0,tempListOfWaypoints.get(B));
													tempRoute.add(1,tempListOfWaypoints.get(E));
													tempRoute.add(2,tempListOfWaypoints.get(D));
													tempRoute.add(3,tempListOfWaypoints.get(G));
													break;
												case 2:
													tempRoute.add(0,tempListOfWaypoints.get(B));
													tempRoute.add(1,tempListOfWaypoints.get(E));
													tempRoute.add(2,tempListOfWaypoints.get(F));
													tempRoute.add(3,tempListOfWaypoints.get(C));
													break;
												}
												break;
												
											//left to right
											case 2:
												switch(randInt){
												case 0:
													tempRoute.add(0,tempListOfWaypoints.get(J));
													tempRoute.add(1,tempListOfWaypoints.get(F));
													tempRoute.add(2,tempListOfWaypoints.get(D));
													tempRoute.add(3,tempListOfWaypoints.get(H));
													break;
												case 1:

													tempRoute.add(0,tempListOfWaypoints.get(B));
													tempRoute.add(1,tempListOfWaypoints.get(E));
													tempRoute.add(2,tempListOfWaypoints.get(I));
													tempRoute.add(3,tempListOfWaypoints.get(H));
													break;
												case 2:
													tempRoute.add(0,tempListOfWaypoints.get(J));
													tempRoute.add(1,tempListOfWaypoints.get(F));
													tempRoute.add(2,tempListOfWaypoints.get(D));
													tempRoute.add(3,tempListOfWaypoints.get(I));
													break;
												}
												
												break;
											}
										}
									}
									break;
								//entry point at the top
								case 2:
									for (int k = 0; k < airspace.getListOfExitPoints().size(); k++){
										if(tempRoute.get(tempRoute.size()-1) == airspace.getListOfExitPoints().get(k)){
											int randInt = new Random().nextInt(2);
											switch(k){
											
											//top to left
											case 1:
												
												//selects random flight plan
												switch(randInt){
												case 0:
													tempRoute.add(0,tempListOfWaypoints.get(C));
													tempRoute.add(1,tempListOfWaypoints.get(A));
													tempRoute.add(2,tempListOfWaypoints.get(F));
													tempRoute.add(3,tempListOfWaypoints.get(J));
													break;
												case 1:
													tempRoute.add(0,tempListOfWaypoints.get(C));
													tempRoute.add(1,tempListOfWaypoints.get(D));
													tempRoute.add(2,tempListOfWaypoints.get(E));
													tempRoute.add(3,tempListOfWaypoints.get(B));
													tempRoute.add(3,tempListOfWaypoints.get(J));
													break;
												case 2:
													tempRoute.add(0,tempListOfWaypoints.get(C));
													tempRoute.add(1,tempListOfWaypoints.get(F));
													tempRoute.add(2,tempListOfWaypoints.get(B));
													tempRoute.add(3,tempListOfWaypoints.get(A));
													tempRoute.add(3,tempListOfWaypoints.get(J));
													break;
												}
												break;
												
											//top to right
											case 2:
												switch(randInt){
												//selects random flight plan
												case 0:
													tempRoute.add(0,tempListOfWaypoints.get(C));
													tempRoute.add(1,tempListOfWaypoints.get(D));
													tempRoute.add(2,tempListOfWaypoints.get(I));
													tempRoute.add(3,tempListOfWaypoints.get(H));
													break;
												case 1:
													tempRoute.add(0,tempListOfWaypoints.get(C));
													tempRoute.add(1,tempListOfWaypoints.get(D));
													tempRoute.add(2,tempListOfWaypoints.get(G));
													tempRoute.add(3,tempListOfWaypoints.get(H));
													break;
												case 2:
													tempRoute.add(0,tempListOfWaypoints.get(C));
													tempRoute.add(1,tempListOfWaypoints.get(G));
													tempRoute.add(2,tempListOfWaypoints.get(H));
													break;
												}
												
												break;
											}
										}
									}
								
									break;
								// entry point on the right
									
								case 1:
									for (int k = 0; k < airspace.getListOfExitPoints().size(); k++){
										if(tempRoute.get(tempRoute.size()-1) == airspace.getListOfExitPoints().get(k)){
											int randInt = new Random().nextInt(2);
											switch(k){
											
											//right to top
											case 1:
												switch(randInt){
												//selects random flight plan
												case 0:
													tempRoute.add(0,tempListOfWaypoints.get(H));
													tempRoute.add(1,tempListOfWaypoints.get(D));
													tempRoute.add(2,tempListOfWaypoints.get(F));
													tempRoute.add(3,tempListOfWaypoints.get(J));
													break;
												case 1:

													tempRoute.add(0,tempListOfWaypoints.get(G));
													tempRoute.add(1,tempListOfWaypoints.get(D));
													tempRoute.add(2,tempListOfWaypoints.get(E));
													tempRoute.add(3,tempListOfWaypoints.get(B));
													tempRoute.add(3,tempListOfWaypoints.get(J));
													break;
												case 2:
													tempRoute.add(0,tempListOfWaypoints.get(H));
													tempRoute.add(1,tempListOfWaypoints.get(I));
													tempRoute.add(2,tempListOfWaypoints.get(D));
													tempRoute.add(3,tempListOfWaypoints.get(F));
													tempRoute.add(3,tempListOfWaypoints.get(A));
													tempRoute.add(3,tempListOfWaypoints.get(J));
													break;
												}
												
												break;
											//right to left
											case 0:
												
												//selects random flight plan
												switch(randInt){
												case 0:
													tempRoute.add(0,tempListOfWaypoints.get(H));
													tempRoute.add(1,tempListOfWaypoints.get(G));
													tempRoute.add(2,tempListOfWaypoints.get(C));
													break;
												case 1:

													tempRoute.add(0,tempListOfWaypoints.get(G));
													tempRoute.add(1,tempListOfWaypoints.get(D));
													tempRoute.add(2,tempListOfWaypoints.get(F));
													tempRoute.add(3,tempListOfWaypoints.get(C));
													break;
												case 2:
													tempRoute.add(0,tempListOfWaypoints.get(H));
													tempRoute.add(1,tempListOfWaypoints.get(I));
													tempRoute.add(2,tempListOfWaypoints.get(D));
													tempRoute.add(3,tempListOfWaypoints.get(C));
													break;
												}
												break;
												
											
											}
										}
									}
									break;
						
							}		
						}
							
					}
					
					
					
					
					}
						
					
			
		
		
		return tempRoute;
	}
	
	/**
	 * generateVelocity: Creates a velocity from a range of values
	 */

	public int generateVelocity() {
		Random rand = new Random();
		return (rand.nextInt(200) + 200);
	}
	
	/**
	 * isMouseOnWaypoint: Used to tell what waypoint the mouse is currently over
	 */
	
	private boolean isMouseOnWaypoint() {
		int mouseX = Mouse.getX(); //Get mouse coordinates
		int mouseY = Mouse.getY();
		mouseY=600-mouseY;
		if(this.getCurrentRoute().isEmpty()) { //If there are no waypouints
			return false;
		}
		for(int i=0; i<this.flight.getAirspace().getListOfWaypoints().size();i++) { // calculate if the mouse is over the waypoint and set the value
			if (((Math.abs(Math.round(mouseX) - Math.round(this.flight.getAirspace().getListOfWaypoints().get(i).getX()))) <= 15)
					&& (Math.abs(Math.round(mouseY) - Math.round(this.flight.getAirspace().getListOfWaypoints().get(i).getY()))) <= 15) {
				
					this.waypointMouseIsOver=this.flight.getAirspace().getListOfWaypoints().get(i);
					return true;
					
			}
		}
		this.waypointMouseIsOver=null;
		return false;
	}
	
	/**
	 * updateFlightPlan: Handles updating the flight plan when a flight passes through a waypoint
	 */
	
	public void updateFlightPlan(ScoreTracking score){

		if (this.currentRoute.size() > 0) { //Check to see if there are still waypoints to visit and then check if the flight is passing through waypoint
			if (this.flight.checkIfFlightAtWaypoint(currentRoute.get(0))) {
				this.waypointsAlreadyVisited.add(this.currentRoute.get(0));
				this.currentRoute.remove(0);
				score.updateWaypointScore();
			}
		}

	}
	
	/**
	 * changeFlightPlan: Handles the user changing the flightplan using the mouse in 
	 * plan mode
	 */
	
	public void changeFlightPlan(){
		if (this.flight.getSelected() && this.currentRoute.size() > 0 ){
			boolean mouseOverWaypoint = this.isMouseOnWaypoint();

				// Checks if user is not currently dragging a waypoint
				if (!draggingWaypoint){
					//Checks if user has clicked on a waypoint
					if(mouseOverWaypoint && Mouse.isButtonDown(0)) {
						this.waypointClicked=this.waypointMouseIsOver;
						this.draggingWaypoint=true;
					}
				}
				
				// Checks if user is currently dragging a waypoint
				else if(draggingWaypoint){
					// Checks if user has released mouse from drag over empty airspace
					if((!Mouse.isButtonDown(0)) && !mouseOverWaypoint){
						this.waypointClicked=null;
						this.draggingWaypoint=false;
							
					}
					
					// Checks if user has released mouse from drag over another waypoint
					else if((!Mouse.isButtonDown(0)) && mouseOverWaypoint){
						
						//Finding waypoint that mouse is over
						for(int i=0; i<this.currentRoute.size();i++) {
							
							// Checks if new waypoint is not already in the plan and adds if not in plan
							if (this.waypointClicked == this.currentRoute.get(i)&& (!this.currentRoute.contains(this.waypointMouseIsOver))&& (!this.waypointsAlreadyVisited.contains(this.waypointMouseIsOver))){
								this.currentRoute.remove(i);
								this.currentRoute.add(i,this.waypointMouseIsOver);
								this.waypointClicked=null;
								this.draggingWaypoint=false;
								
							}
							
							// Checks if waypoint already in plan and doesn't add if not
							else if(this.waypointClicked == this.currentRoute.get(i)&& ((this.currentRoute.contains(this.waypointMouseIsOver)) || (this.waypointsAlreadyVisited.contains(this.waypointMouseIsOver)))){
								this.waypointClicked=null;
								this.draggingWaypoint=false;
								break;
								
							}
						}
					}
				}
		}
	}
	
	/**
	 * drawFlightsPlan: Draws the graphics required for the flightplan
	 * @param g Slick2d graphics object
	 * @param gs Slick2d gamecontainer object
	 */
	
	public void drawFlightsPlan(Graphics g, GameContainer gc){

		if (this.currentRoute.size() > 0){
			
			g.setColor(Color.cyan);
			
			// If not dragging waypoints, just draw lines between all waypoints in plan
			if(!draggingWaypoint){
				for(int i=1; i<this.currentRoute.size();i++) {
					g.drawLine((float)this.currentRoute.get(i).getX(), (float)this.currentRoute.get(i).getY(), (float)this.currentRoute.get(i-1).getX(), (float)this.currentRoute.get(i-1).getY());
				}
			}
			
			else if(draggingWaypoint){
				for(int i=1; i<this.currentRoute.size();i++) {
					
					// This is needed as i=1 behavours differently to other values of i when first waypoint is being dragged.
					if(i==1){
						if(this.waypointClicked == this.currentRoute.get(0) ) {
							g.drawLine(Mouse.getX(),600-Mouse.getY() , (float)this.currentRoute.get(1).getX(),(float)this.currentRoute.get(1).getY());
						}
						
						else if (this.waypointClicked == this.currentRoute.get(1)){
							g.drawLine((float)this.currentRoute.get(i+1).getX(), (float)this.currentRoute.get(i+1).getY(),Mouse.getX(),600-Mouse.getY());
							g.drawLine((float)this.currentRoute.get(i-1).getX(), (float)this.currentRoute.get(i-1).getY(),Mouse.getX(),600-Mouse.getY());
							i++;
							
						}
						
						else{
							g.drawLine((float)this.currentRoute.get(i).getX(), (float)this.currentRoute.get(i).getY(), (float)this.currentRoute.get(i-1).getX(), (float)this.currentRoute.get(i-1).getY());
						}
						

					}
					
					else{
						// If Waypoint is being changed draw lines between mouse and waypoint before and after the waypoint being changed. 
						if (this.waypointClicked == this.currentRoute.get(i)){
							g.drawLine((float)this.currentRoute.get(i+1).getX(), (float)this.currentRoute.get(i+1).getY(),Mouse.getX(),600-Mouse.getY());
							g.drawLine((float)this.currentRoute.get(i-1).getX(), (float)this.currentRoute.get(i-1).getY(),Mouse.getX(),600-Mouse.getY());
							i++;
						}
						
						else{
							g.drawLine((float)this.currentRoute.get(i).getX(), (float)this.currentRoute.get(i).getY(), (float)this.currentRoute.get(i-1).getX(), (float)this.currentRoute.get(i-1).getY());
						}
						
					}
				}
			}
				
		}
		
		
	}
	
	/**
	 * markUnavaliableWaypoints: Handles alerting the user to any waypoints that are
	 * invalid for selection
	 * @param g slick2d graphics object
	 * @param gc slick2d gamecontainer object
	 */
	
	public void markUnavailableWaypoints(Graphics g, GameContainer gc){
		for (int i = 0; i < this.waypointsAlreadyVisited.size(); i++){
			g.drawLine((float) this.waypointsAlreadyVisited.get(i).getX()-14, (float) this.waypointsAlreadyVisited.get(i).getY()-14, (float) this.waypointsAlreadyVisited.get(i).getX()+14, (float) this.waypointsAlreadyVisited.get(i).getY()+14);
			g.drawLine((float) this.waypointsAlreadyVisited.get(i).getX()+14, (float) this.waypointsAlreadyVisited.get(i).getY()-14, (float) this.waypointsAlreadyVisited.get(i).getX()-14, (float) this.waypointsAlreadyVisited.get(i).getY()+14);
		}
	}
	
	public void update(ScoreTracking score) {
		
		this.updateFlightPlan(score);
		if(this.changingPlan == true){
			this.changeFlightPlan();
		}

	}
	
	public void render(Graphics g, GameContainer gc) throws SlickException {


		if(this.flight.getSelected()) {
			if(this.changingPlan == true){
				this.drawFlightsPlan(g, gc);
				this.markUnavailableWaypoints(g, gc);
			}
			

		}
		
	}
	
	
	
	
	

	// ACCESSORS AND MUTATORS

	public void setVelocity(double newVelocity){
		this.velocity = newVelocity;
		
	}
	
	public double getVelocity() {
		return this.velocity;
	}

	public List<Point> getCurrentRoute() {
		return currentRoute;
	}

	public Point getPointByIndex(int i) {
		return this.currentRoute.get(i);

	}
	
	public boolean getChangingPlan(){
		return this.changingPlan;
	}
	
	public void setChangingPlan(boolean bool){
		this.changingPlan = bool;
	}
	
	public boolean getDraggingWaypoint(){
		return this.draggingWaypoint;
	}
	
	public EntryPoint getEntryPoint(){
		return this.entryPoint;
	}

	@Override
	public String toString() {
		String returnString = "";
		for (int i = 0; i < this.currentRoute.size(); i++) {
			returnString += "Point " + i + ": ";
			returnString += this.currentRoute.get(i).getX();
			returnString += ", ";
			returnString += this.currentRoute.get(i).getY();
			returnString += " | ";
		}
		return returnString;
	}

}