package logicClasses;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class FlightMenu {
	private static Image	//base images
		sliderBase, sliderRingBase, sliderIndicator, sliderIndicatorSelect,
		button, buttonSelect;
			
	private int	//graphics data
		altSize = 100, speedSize = 100, headingSize = 100,	//slider lengths
		sliderWidth = 20, indicatorSize = 30,	//track and indicator sizes
		buttonWidth = 40, buttonHeight = 25,	//buttonSizes
		spacingSize = 8;	//spacing between components
	
	private Image	//scaled instance copies
		altBase, speedBase, headingBase, aIndicator, aIndicatorSelect,
		aButton, aButtonSelect;
	private Point2D.Float	//cached graphics position data
		altPos , speedPos, headingPos, cmdPos, abortPos,
		altIndicatorPos, speedIndicatorPos, headingIndicatorPos;
	
	private Flight flight;	//bound Flight
	
	private double	//indicator positions
		altIndicator, speedIndicator, headingIndicator;
	private int mode = 0;	//active subcomponent
	private static final int ALT = 1, SPEED = 2, HEADING = 3, CMD = 4, ABORT = 5;
		

	public FlightMenu() {
		//create scaled copies of images
		altBase = sliderBase.getScaledCopy(altSize, sliderWidth);
		altBase.rotate(90);
		speedBase = sliderBase.getScaledCopy(altSize, sliderWidth);
		headingBase = sliderRingBase.getScaledCopy(headingSize, headingSize);
		aIndicator = sliderIndicator.getScaledCopy(indicatorSize, indicatorSize);
		aIndicatorSelect = sliderIndicatorSelect.getScaledCopy(indicatorSize, indicatorSize);
		aButton = button.getScaledCopy(buttonWidth, buttonHeight);
		aButtonSelect = buttonSelect.getScaledCopy(buttonWidth, buttonHeight);
			
		//initialise objects, initialise layout
		altPos = new Point2D.Float();
		speedPos = new Point2D.Float();
		headingPos = new Point2D.Float();
		cmdPos = new Point2D.Float();
		abortPos = new Point2D.Float();
		altIndicatorPos = new Point2D.Float();
		speedIndicatorPos = new Point2D.Float();
		headingIndicatorPos = new Point2D.Float();
		position();
	}

	
	public void init() throws SlickException{
		sliderBase = new Image("res/graphics/FlightMenu/rectangle_slider.png");
		sliderRingBase = new Image("res/graphics/FlightMenu/circle_slider.png");
		sliderIndicator = new Image("res/graphics/FlightMenu/slider_element.png");
		button = new Image("res/graphics/FlightMenu/button_bg.png");
		
		//{!} unimplemented graphics; duplicating existing:
		sliderIndicatorSelect = sliderIndicator;
		buttonSelect = button;
	}
	
	
	public void update(){
		//check for mouse state, trigger events
	}
	
	
	public void render(Graphics g, GameContainer gc) throws SlickException {
		if (flight != null){
			//{!} constrain positions
			
			drawImage(altBase, altPos);
			if (ALT == mode)
				drawImage(sliderIndicatorSelect, altPos);
			else drawImage(sliderIndicator, altPos);
			
			drawImage(speedBase, speedPos);
			if (SPEED == mode)
				drawImage(sliderIndicatorSelect, speedPos);
			else drawImage(sliderIndicator, speedPos);
			
			drawImage(headingBase, headingPos);
			if (HEADING == mode)
				drawImage(sliderIndicatorSelect, headingPos);
			else drawImage(sliderIndicator, headingPos);
			
			if (CMD == mode)
				drawImage(aButtonSelect, cmdPos);
			else drawImage(aButton, cmdPos);
			//{!} draw button text
			
			if (ABORT == mode)
				drawImage(aButtonSelect, cmdPos);
			else drawImage(aButton, cmdPos);
			//{!} draw button text
		}
	}
	
	private void drawImage(Image image, Point2D pos){
		image.draw((float)( pos.getX() +flight.getX() ), 
		           (float)( pos.getY() +flight.getY() ) );
	}
	
	private void position(){
		mode = 0;	//invalidate any current mouse movements
		
		double r = headingSize /2.0f;
		
		//base position at centre of bearing slider
		headingPos.setLocation(-r, -r);
		
		//position altitude slider to left of bearing slider, centred
		altPos.setLocation(-r -spacingSize -sliderWidth, -(altSize/2.0f));
		
		//position speed slider to below bearing slider, centred
		speedPos.setLocation(-(speedSize/2.0f), r +spacingSize);
		
		//position buttons to left of altitude slider, centred
		cmdPos.setLocation(altPos.getX() -spacingSize -buttonWidth, -(spacingSize/2) -buttonHeight);
		abortPos.setLocation(altPos.getX() -spacingSize -buttonWidth, (spacingSize/2));
		
		setIndicatorPos();
	}
	
	private void setIndicatorPos(){
		if (flight != null){
			double 
				br = headingSize/2.0,
				sr = sliderWidth/2.0,
				ir = indicatorSize/2.0;
			
			altIndicatorPos.setLocation(
					altPos.x +sr -ir,
					altPos.y +altSize*normalScale(Controls.MAXIMUMALTITUDE, 
					                              Controls.MAXIMUMALTITUDE, 
					                              flight.getTargetAltitude()) -ir);
			
			speedIndicatorPos.setLocation(
					altPos.x +speedSize*normalScale(200,400, 
					                                flight.getFlightPlan().getVelocity()) -ir,	//{!} no variables to use speed yet
					altPos.y +sr -ir);
				
			headingIndicatorPos.setLocation(
					(br-sr+ir) * Math.sin(Math.toRadians(headingIndicator)),
					(br-sr+ir) * Math.cos(Math.toRadians(headingIndicator)) );
		}
	}
	
	private double normalScale(double min, double max, double pos){
		//return the position of pos on the scale min-max, normalised to 0-1
		return (pos-min) / (max-min);
	}
	
	private void eventTargetSpeed(double speed){
		System.out.println(String.format("speed := %1$3d", speed));
	}
	
	private void eventTargetAltitude(double altitude){
		System.out.println(String.format("altitude := %1$4d", altitude));
	}
	
	private void eventTargetHeading(double heading){
		System.out.println(String.format("altitude := %1$3d", heading));
	}
	
	private void eventAbort(double heading){
		System.out.println(String.format("altitude := %1$3d", heading));
	}
	
	private void eventLand(){
		System.out.println("land");
	}
	
	private void eventTakeoff(){
		System.out.println("takeoff");
	}
	
	
	public int getAltSize() {
		return altSize;
	}
	public void setAltSize(int altSize) {
		this.altSize = altSize;
		altBase = sliderBase.getScaledCopy(sliderWidth, altSize);
		altBase.rotate(90);
		position();
	}

	public int getSpeedSize() {
		return speedSize;
	}
	public void setSpeedSize(int speedSize) {
		this.speedSize = speedSize;
		speedBase = sliderBase.getScaledCopy(altSize, sliderWidth);
		position();
	}

	public int getBearingSize() {
		return headingSize;
	}
	public void setHeadingSize(int bearingSize) {
		this.headingSize = bearingSize;
		headingBase = sliderRingBase.getScaledCopy(headingSize, headingSize);
		position();
	}

	public int getSliderWidth() {
		return sliderWidth;
	}
	public void setSliderWidth(int sliderWidth) {
		this.sliderWidth = sliderWidth;
		altBase = sliderBase.getScaledCopy(sliderWidth, altSize);
		altBase.rotate(90);
		speedBase = sliderBase.getScaledCopy(altSize, sliderWidth);
		position();
	}

	public int getIndicatorSize() {
		return indicatorSize;
	}
	public void setIndicatorSize(int indicatorSize) {
		this.indicatorSize = indicatorSize;
		aIndicator = sliderIndicator.getScaledCopy(indicatorSize, indicatorSize);
		aIndicatorSelect = sliderIndicatorSelect.getScaledCopy(indicatorSize, indicatorSize);
		position();
	}

	public int getButtonWidth() {
		return buttonWidth;
	}
	public void setButtonWidth(int buttonWidth) {
		this.buttonWidth = buttonWidth;
		aButton = button.getScaledCopy(buttonWidth, buttonHeight);
		aButtonSelect = buttonSelect.getScaledCopy(buttonWidth, buttonHeight);
		position();
	}

	public int getButtonHeight() {
		return buttonHeight;
	}
	public void setButtonHeight(int buttonHeight) {
		this.buttonHeight = buttonHeight;
		aButton = button.getScaledCopy(buttonWidth, buttonHeight);
		aButtonSelect = buttonSelect.getScaledCopy(buttonWidth, buttonHeight);
		position();
	}

	public int getSpacingSize() {
		return spacingSize;
	}
	public void setSpacingSize(int spacingSize) {
		this.spacingSize = spacingSize;
		position();
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
		if (flight != null){
			altIndicator = flight.getTargetAltitude();
			speedIndicator = flight.getFlightPlan().getVelocity();	//{!}
			headingIndicator = flight.getTargetHeading();
			setIndicatorPos();
		}
	}
	
}
