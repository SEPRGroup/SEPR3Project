package logicClasses;
import java.awt.Font;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.GameContainer;

public class Controls {

	// FIELDS
	private TrueTypeFont font;
	private TextField headingControlTextBox; //Object for heading control
	private TextField turnRightTextBox; //Object for turn right control
	private TextField turnLeftTextBox; // Object for turn left control
	private boolean selectingHeadingUsingTextBox; // Has the text box been reset?
	private boolean mouseHeldDownOnAltitudeButton, mouseHeldDownOnFlight, headingAlreadyChangedByMouse;
	public static final int  MAXIMUMALTITUDE = 31000;
	public static final int  MINIMUMALTITUDE = 26000;
	private Flight selectedFlight;
	private String text; //Used for parsing textbox inputs
	private Image altitudeButton, changePlanButton;
	private int difficultyValueOfGame; //Sets the difficulty of the control scheme
	
	private FlightMenu menu;
	
	
	// CONSTRUCTOR
	public Controls() {
		//Initializes all boolean values controlling selections to false
		selectingHeadingUsingTextBox = false; 
		mouseHeldDownOnAltitudeButton=false;
		mouseHeldDownOnFlight = false;
		headingAlreadyChangedByMouse = false;
		selectedFlight = null;
	}


	// INIT
	public void init(GameContainer gc) throws SlickException {	
		Font awtFont = new Font("Courier", Font.BOLD, 15); // Setting up fonts used in text boxes
		font = new TrueTypeFont(awtFont, false);
		turnLeftTextBox = new TextField(gc, font, 10, 145, 100, 23); //Creating textboxes
		headingControlTextBox = new TextField(gc, font, 10, 215, 100, 23);
		turnRightTextBox = new TextField(gc, font, 10, 285, 100, 23);
		turnLeftTextBox.setMaxLength(3); //Makes sure that user cannot enter more than three letters as a heading (360 is max)
		turnRightTextBox.setMaxLength(3);
		headingControlTextBox.setMaxLength(3);
		altitudeButton = new Image("res/graphics/altitudebutton.png");
		changePlanButton = new Image("res/graphics/altitudebutton.png"); // same as altitude button
		
		menu = new FlightMenu();
		menu.init();
		menu.setInput(gc.getInput());
	}
	
	

	// METHODS
	
	/**
	 * handleAndUpdateAltitudeButtons: Deals with analysing and updating the selected flights altitude
	 * based on the altitude adjustment buttons
	 */
	
	public void handleAndUpdateAltitudeButtons() {
		
		if(mouseHeldDownOnAltitudeButton) {
			return;
		}
		else{
			mouseHeldDownOnAltitudeButton = true;
		}
		
		int posX=Mouse.getX(); //Get the mouse positions 
		int posY=Mouse.getY();
		
		posY = 600 - posY; // Mapping Mouse coords onto graphic coords

		
			if(posX>10&&posX<150&&posY<410&&posY>390&&Mouse.isButtonDown(0)) { //Is the mouse position in the area enclosed by the increase altitude button and is the button being held down?
				if(selectedFlight.getTargetAltitude() < MAXIMUMALTITUDE) { //Is the target altitude already at the maximum value?
					selectedFlight.setTargetAltitude(selectedFlight.getTargetAltitude()+1000); //Set the target altitude 1000 higher
				}
			}

			else if(posX>10&&posX<150&&posY<440&&posY>420&&Mouse.isButtonDown(0)) {//Is the mouse position in the area enclosed by the decrease altitude button and is the button being held down?
				if(selectedFlight.getTargetAltitude()> MINIMUMALTITUDE) { //Is the target altitude already at the minimum value?
					selectedFlight.setTargetAltitude(selectedFlight.getTargetAltitude()-1000); //Set the target altitude 1000 lower
				}
			}
	}
	
	/**
	 * changeModeByClickingOnFlight: Handles changing between plan and nav modes by clicking on the selected flight
	 * @param nearestFlight Flight object
	 */
	public void changeModeByClickingOnFlight(Flight nearestFlight){
	
		if (selectedFlight.getFlightPlan().getChangingPlan() == true){
			nearestFlight.getFlightPlan().setChangingPlan(false);
		}
		else{
			nearestFlight.getFlightPlan().setChangingPlan(true);
		}
		
	}
	
	/**
	 * checkSelected: Handles changing the selected flight and ensures that the flight is a valid selection
	 * Also makes sure that if two flights are intersecting that you only select one, not both
	 * @param pointX
	 * @param pointY
	 * @param airspace
	 */
	public void checkSelected(int pointX, int pointY, Airspace airspace ){
		
		double minimumDistanceBetweenFlightAndMouseClick;//Distance between there you clicked on the airspace and the closest flight
		Flight nearestFlight;
		int indexOfNearestFlightInAirspaceListOfFlights;
		
		// If mouse is being held down don't change selected flight. 
		if (mouseHeldDownOnFlight){
			return;
		}
		else{
			mouseHeldDownOnFlight = true;
		}
		
		// Checking if user is dragging a waypoint they can't change flights
		if (selectedFlight != null){
			if (selectedFlight.getFlightPlan().getDraggingWaypoint()){
				return;
			}
		}
		
		
	 
		
		// Working out nearest flight to click
		
		if(airspace.getListOfFlights().size()>=1){ //If there is more than one flight in the airspace
			//DONT PANIC, just pythagoras 
			minimumDistanceBetweenFlightAndMouseClick = Math.sqrt(Math.pow(pointX-airspace.getListOfFlights().get(0).getX(), 2)+Math.pow(pointY-airspace.getListOfFlights().get(0).getY(), 2));
			nearestFlight = airspace.getListOfFlights().get(0);
			indexOfNearestFlightInAirspaceListOfFlights = 0;
			
			for (int i =0; i< airspace.getListOfFlights().size(); i++){ //Loop through all flights and find the nearest one
				if(Math.sqrt(Math.pow(pointX-airspace.getListOfFlights().get(i).getX(), 2)+Math.pow(pointY-airspace.getListOfFlights().get(i).getY(), 2)) < minimumDistanceBetweenFlightAndMouseClick){
					minimumDistanceBetweenFlightAndMouseClick = Math.sqrt(Math.pow(pointX-airspace.getListOfFlights().get(i).getX(), 2)+Math.pow(pointY-airspace.getListOfFlights().get(i).getY(), 2));
					nearestFlight = airspace.getListOfFlights().get(i);
					indexOfNearestFlightInAirspaceListOfFlights = i;
				}
			}
			
			// Working out whether the nearest flight to click is close enough
			// to be selected.
			
			if (minimumDistanceBetweenFlightAndMouseClick <= 50){ // If the mouse if further from the flight than 50 then it cannot be selected
				
				if (nearestFlight == selectedFlight){ //If you are clicking on the currently selected flight then change the airspace mode instead of changing flight
					changeModeByClickingOnFlight(nearestFlight);
				}
				
				nearestFlight.setSelected(true);
				setSelectedFlight(nearestFlight);//Change the selected flight for controls
				for (int i =0; i< airspace.getListOfFlights().size(); i++){ //Loop through all flights
					if(i != indexOfNearestFlightInAirspaceListOfFlights){ //If the flight is not the currently selected flight
						airspace.getListOfFlights().get(i).setSelected(false); //Set that flight to not selected
						airspace.getListOfFlights().get(i).getFlightPlan().setChangingPlan(false);//Set the flight to nav mode
						turnLeftTextBox.setText(""); //Reset all the control text boxes
						turnRightTextBox.setText("");
					}
				}
			}
		}
	}
	
	/**
	 * giveHeadingWithMouse: Handles updating the currently selected flights heading by clicking in it's
	 * control circle with the left mouse button
	 * @param pointX X Coordinate of the mouse click
	 * @param pointY Y Coordinate of the mouse click
	 * @param airspace
	 */
	
	public void giveHeadingWithMouse(int pointX, int pointY, Airspace airspace){
		
		double deltaX, deltaY;
		double distanceBetweenMouseAndPlane;
		
		// If mouse is being held down don't change selected flight. 
		if (headingAlreadyChangedByMouse){
			return;
		}
		else{
			headingAlreadyChangedByMouse = true;
		}
		
		//More pythag - Finding the distance between the mouse click and the plane
		distanceBetweenMouseAndPlane = Math.sqrt(Math.pow(pointX-selectedFlight.getX(), 2)+Math.pow(pointY-selectedFlight.getY(), 2));
		
		
		if (distanceBetweenMouseAndPlane < 50) //If the distance between the mouse and the plane is greater than 50 then don't do anything
		{
			deltaY = pointY - selectedFlight.getY();
			deltaX = pointX - selectedFlight.getX();
			double angle = Math.toDegrees(Math.atan2(deltaY, deltaX)); // Find the angle between the current heading and where the mouse was clicked
			angle+=90;
			if (angle < 0) {
				angle += 360;
			}
			selectedFlight.giveHeading((int)angle);
		
		}
		
	}
	
	/**
	 * updateHeadingTextBox: Handles updating the currently selected flights heading by typing a value
	 * into a text-box as well as checking the input is valid
	 * @param input Input object
	 */
	
	public void updateHeadingTextBox(Input input){
		boolean headingTextBoxHasFocus = headingControlTextBox.hasFocus();
		if (headingTextBoxHasFocus) {
			
			// If the user has just selected the textbox, clear the textbox 
			if (!selectingHeadingUsingTextBox) {
				selectingHeadingUsingTextBox = true;
				headingControlTextBox.setText("");
			}
			// When the enter key is pressed retrieve its text and reset the textbox
			if (input.isKeyDown(Input.KEY_ENTER)) {
				text = headingControlTextBox.getText();
				text = text.replaceAll("\\D+", "");
				if (!text.isEmpty()) { //If there is a value then set the new heading and unselect the text box
					selectedFlight.giveHeading(Integer.valueOf(text));
					headingControlTextBox.setFocus(false);

				}
				
			}
		}
		
		if (selectingHeadingUsingTextBox && !headingTextBoxHasFocus) {
			selectingHeadingUsingTextBox = false;
		}
	}
	
	/**
	 * updateTurnLeftTextBox: Handles turning the currently selected flight left by typing a value
	 * into a text-box as well as checking the input is valid
	 * @param input Input Object
	 */
	
	public void updateTurnLeftTextBox(Input input){
		
		boolean turnLeftTextBoxHasFocus = turnLeftTextBox.hasFocus();
		if (turnLeftTextBoxHasFocus) {
			
			// When the enter key is pressed retrieve its text and reset the textbox
			if (input.isKeyDown(Input.KEY_ENTER)) {
				text = turnLeftTextBox.getText();
				text = text.replaceAll("\\D+", "");
				if (!text.isEmpty() && Integer.valueOf(text) <= 360) {//If there is a valid value then set the new heading and unselect the text box
					selectedFlight.turnFlightLeft(Integer.valueOf(text));
					turnLeftTextBox.setText("");
				}
				turnLeftTextBox.setFocus(false);
				
			}
		}
		else{
			turnLeftTextBox.setText("");
		}
		
	}
	
	/**
	 * updateTurnRightTextBox: Handles turning the currently selected flight right by typing a value
	 * into a text-box as well as checking the input is valid
	 * @param input
	 */
	
	public void updateTurnRightTextBox(Input input){
		
		if (turnRightTextBox.hasFocus()) {

			// When the enter key is pressed retrieve its text and reset the textbox
			if (input.isKeyDown(Input.KEY_ENTER)) {
				text = turnRightTextBox.getText();
				text = text.replaceAll("\\D+", "");
				if (!text.isEmpty() && Integer.valueOf(text) <= 360) { //If there is a valid value then set the new heading and unselect the text box
					selectedFlight.turnFlightRight(Integer.valueOf(text));
					turnRightTextBox.setText("");
				}
				turnRightTextBox.setFocus(false);
				
			}
		}
		else{
			turnRightTextBox.setText("");
		}

	}


	// RENDER AND UPDATE

	/**
	 * render: Render all of the graphics required by controls
	 * @param g The slick2d graphics object
	 * @param gc The slick2d game container
	 * @throws SlickException
	 */
	
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if(selectedFlight != null) {
			if(!selectedFlight.getFlightPlan().getChangingPlan()){
				g.setColor(Color.white);
				
				g.drawString("Turn Left:", 10, 125);
				turnLeftTextBox.render(gc, g);
				g.drawString("DEG", 114, 153);
				
				g.drawString("Target Heading:", 10, 195);
				headingControlTextBox.render(gc, g);
				g.drawString("DEG", 114, 224);
				
				g.drawString("Turn Right:", 10, 265);
				turnRightTextBox.render(gc, g);
				g.drawString("DEG", 114, 294);
				
				
				g.drawString("Change Altitude:", 10, 330);
				g.setColor(Color.blue);
				altitudeButton.draw(0,390);
				altitudeButton.draw(0,420);
				g.setColor(Color.white);
				g.drawString("Target: "+selectedFlight.getTargetAltitude()+"Ft", 10, 360);
				if(selectedFlight.getTargetAltitude() != Math.round(31000)){
					g.drawString("Increase to "+ (selectedFlight.getTargetAltitude()+1000), 10, 390);
				}
				else {
					g.drawString("At max altitude", 10, 390);
				}
				if(selectedFlight.getTargetAltitude() != Math.round(26000)){
					g.drawString("Decrease to "+ (selectedFlight.getTargetAltitude()-1000), 10, 420);
				}
				else {
					g.drawString("At min altitude", 10, 420);
				}
				
				menu.render(g, gc);
				
				}
			changePlanButton.draw(0,45);
			changePlanButton.draw(0, 75);
			if(selectedFlight.getFlightPlan().getChangingPlan() == true){
				g.setColor(Color.white);
				g.drawString("Plan Mode", 10, 45);
				g.setColor(Color.lightGray);
				g.drawString("Navigator Mode", 10, 75);
			}
			else{
				g.setColor(Color.white);
				g.drawString("Navigator Mode", 10, 75);
				g.setColor(Color.lightGray);
				g.drawString("Plan Mode", 10, 45);
				
			}
		}	
	}
	
	
	
	
	public void update(GameContainer gc, Airspace airspace) {
		Input input = gc.getInput();
		int posX=Mouse.getX();
		int posY=Mouse.getY();
		posY = 600-Mouse.getY();

		if (selectedFlight != null ){
			
			// Only allow controls if user isn't changing a plan
			
			if(!(selectedFlight.getFlightPlan().getChangingPlan())){
				
				if(posX>10&&posX<150&&posY<65&&posY>45&&Mouse.isButtonDown(0)){
					selectedFlight.getFlightPlan().setChangingPlan(true);
				}
				
				if(Mouse.isButtonDown(1) && (difficultyValueOfGame != 3)){
					giveHeadingWithMouse(posX, posY, airspace);
				}

				updateHeadingTextBox(input);
				updateTurnLeftTextBox(input);
				updateTurnRightTextBox(input);
		
			
				
				// Handle and update Altitude Buttons
				
				handleAndUpdateAltitudeButtons();
				
				
				//ALTITUDE KEYS
				if(input.isKeyPressed(Input.KEY_UP)){
					if(selectedFlight.getTargetAltitude() < MAXIMUMALTITUDE) {
						selectedFlight.setTargetAltitude(selectedFlight.getTargetAltitude()+1000);
					}
				}
				if(input.isKeyPressed(Input.KEY_DOWN)){
					if(selectedFlight.getTargetAltitude() > MINIMUMALTITUDE) {
						selectedFlight.setTargetAltitude(selectedFlight.getTargetAltitude()-1000);
					}
				}
				
				
				if (!headingControlTextBox.hasFocus()) {
					getHeadingControlTB().setText(
							String.valueOf(Math.round(selectedFlight.getTargetHeading())));
				}
			
			}
			
			else{
				if(posX>10&&posX<150&&posY<95&&posY>75&&Mouse.isButtonDown(0)){
					selectedFlight.getFlightPlan().setChangingPlan(false);
				}
			}
		
		}
		
		if(Mouse.isButtonDown(0)){
			checkSelected(posX,posY,airspace);
			}
		
		if(!Mouse.isButtonDown(0)){
			mouseHeldDownOnFlight = false;
			mouseHeldDownOnAltitudeButton = false;
		}
		
		if (!Mouse.isButtonDown(1)){
			headingAlreadyChangedByMouse = false;
		}
		
	}
	
	//MUTATORS AND ACCESSORS

	

	public void setSelectedFlight(Flight flight1){
		selectedFlight = flight1;
		menu.setFlight(flight1);
	}


	
	public TextField getHeadingControlTB() {
		return headingControlTextBox;
	}

	
	public Flight getSelectedFlight(){
		return selectedFlight;
	}
	
	public void setDifficultyValueOfGame(int value){
		difficultyValueOfGame = value;
		
	}
}



