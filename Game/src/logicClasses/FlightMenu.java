package logicClasses;

import java.awt.Font;
import java.awt.geom.Point2D;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;


public class FlightMenu implements MouseListener{
	private static Image	//base images
		sliderBase, sliderRingBase, sliderIndicator, sliderIndicatorSelect,
		button, buttonSelect;

	private int	//graphics data
		altSize = 100, speedSize = 100, headingSize = 120,	//slider lengths
		sliderWidth = 12, indicatorSize = 20,	//track and indicator sizes
		buttonWidth = 55, buttonHeight = 25,	//buttonSizes
		spacingSize = 6;	//spacing between components

	private Image	//scaled instance copies
		altBase, speedBase, headingBase, aIndicator, aIndicatorSelect,
		aButton, aButtonSelect;
	private TrueTypeFont
		aLabelFont = new TrueTypeFont(new Font(Font.SANS_SERIF, Font.BOLD, 10), false),
		aButtonFont = new TrueTypeFont(new Font(Font.SANS_SERIF, Font.BOLD, 11), false);
	private Color
		aLabelColor = Color.white, aButtonColor = Color.white;
	
	private Point2D.Float	//cached graphics position data
		altPos, speedPos, headingPos, cmdPos, abortPos,
		altIndicatorPos, speedIndicatorPos, headingIndicatorPos;
		
	private Input input;
	private Flight flight;	//bound Flight
	
	private int mode = NONE;	//active subcomponent
	private static final int
		NONE = 0, ALT = 1, SPEED = 2, HEADING = 3, CMD = 4, ABORT = 5;
	private double	//indicator positions
		altIndicator, speedIndicator, headingIndicator;



	public FlightMenu() {
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
		//load images if needed
		if (sliderBase == null)
			sliderBase = new Image("res/graphics/FlightMenu/rectangle_slider.png");
		if (sliderRingBase == null)
			sliderRingBase = new Image("res/graphics/FlightMenu/circle_slider.png");
		if (sliderIndicator == null)
			sliderIndicator = new Image("res/graphics/FlightMenu/slider_element.png");
		if (button == null)
			button = new Image("res/graphics/FlightMenu/button_bg.png");
		//{!} unimplemented graphics; duplicating existing:
		sliderIndicatorSelect = sliderIndicator;
		buttonSelect = button;

		//create scaled copies of images
		altBase = sliderBase.getScaledCopy(altSize, sliderWidth);
		altBase.setCenterOfRotation(0, 0);
		altBase.setRotation(90);	//{!} will leave image positioned sliderWidth to the left
		speedBase = sliderBase.getScaledCopy(speedSize, sliderWidth);
		headingBase = sliderRingBase.getScaledCopy(headingSize, headingSize);
		aIndicator = sliderIndicator.getScaledCopy(indicatorSize, indicatorSize);
		aIndicatorSelect = sliderIndicatorSelect.getScaledCopy(indicatorSize, indicatorSize);
		aButton = button.getScaledCopy(buttonWidth, buttonHeight);
		aButtonSelect = buttonSelect.getScaledCopy(buttonWidth, buttonHeight);
	}

	public void render(Graphics g, GameContainer gc) throws SlickException {
		if (flight != null){
			//{!} constrain positions
	
			//draw altitude slider and labels
			drawImage(altBase,  new Point2D.Float(altPos.x +sliderWidth, altPos.y));
				//account for image mispositioning after rotation
			drawString(String.valueOf(Controls.MINIMUMALTITUDE),
			           aLabelFont, aLabelColor,
			           altPos.x +sliderWidth, altPos.y +altSize);	//centred on bottom right edge of slider 
			drawString(String.valueOf(Controls.MAXIMUMALTITUDE),
			           aLabelFont, aLabelColor,
			           altPos.x +sliderWidth, altPos.y);	//centred on top right edge of slider
			if (ALT == mode)
				drawImage(aIndicatorSelect, altIndicatorPos);
			else drawImage(aIndicator, altIndicatorPos);

			//draw speed slider and labels
			drawImage(speedBase, speedPos);
			drawString(String.valueOf(200),	//{!} No variables to use for numbers yet
			           aLabelFont, aLabelColor,
			           speedPos.x, speedPos.y +sliderWidth);	//centred on bottom left edge of slider 
			drawString(String.valueOf(400),	//{!} No variables to use for numbers yet
			           aLabelFont, aLabelColor,
			           speedPos.x +speedSize, speedPos.y +sliderWidth);	//centred on bottom right edge of slider
			if (SPEED == mode)
				drawImage(aIndicatorSelect, speedIndicatorPos);
			else drawImage(aIndicator, speedIndicatorPos);

			//draw heading slider and labels
			drawImage(headingBase, headingPos);
			if (HEADING == mode)
				drawImage(aIndicatorSelect, headingIndicatorPos);
			else drawImage(aIndicator, headingIndicatorPos);

			//draw command button and label
			if (CMD == mode)
				drawImage(aButtonSelect, cmdPos);
			else drawImage(aButton, cmdPos);
			{	//draw button text
				String cmdString;
				if (flight.getAltitude()==0)
					cmdString = "Take Off";
				else cmdString = "Land";
				drawString(cmdString, aButtonFont, aButtonColor, 
				           cmdPos.x +(buttonWidth/2.0f), cmdPos.y +(buttonHeight/2.0f));
			}

			//draw abort button and label
			if (ABORT == mode)
				drawImage(aButtonSelect, abortPos);
			else drawImage(aButton, abortPos);
			drawString("Abort", aButtonFont, aButtonColor, 
			           abortPos.x +(buttonWidth/2.0f), abortPos.y +(buttonHeight/2.0f));
			
		}
	}

	private void drawImage(Image image, Point2D pos){
		image.draw((float)( pos.getX() +flight.getX() ), 
		           (float)( pos.getY() +flight.getY() ) );
	}
	
	private void drawString(String str, TrueTypeFont font, Color color, float x, float y){
		font.drawString((float)( x -(font.getWidth(str)/2.0) +flight.getX() ),
		                (float)( y -(font.getHeight()/2.0) +flight.getY() ),
		                str, color);
	}

	private void position(){
		mode = NONE;	//invalidate any current mouse movements

		double r = headingSize /2.0f;

		//base position at centre of bearing slider
		headingPos.setLocation(-r, -r);

		//position altitude slider to left of bearing slider, centred
		altPos.setLocation(-r -spacingSize -sliderWidth, -(altSize/2.0f));

		//position speed slider to below bearing slider, centred
		speedPos.setLocation(-(speedSize/2.0f), r +spacingSize);

		//position buttons to left of altitude slider, centred
		cmdPos.setLocation(altPos.x -spacingSize -buttonWidth, -(spacingSize/2.0f) -buttonHeight);
		abortPos.setLocation(altPos.x -spacingSize -buttonWidth, (spacingSize/2.0f));

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
					altPos.y +(altSize*(1-normalScale(Controls.MINIMUMALTITUDE,
					                                  Controls.MAXIMUMALTITUDE, 
					                                  altIndicator )) -ir));

			speedIndicatorPos.setLocation(
					speedPos.x +(speedSize*normalScale(200,400, speedIndicator) -ir),
						//{!} no variables to use speed yet
					speedPos.y +sr -ir);

			headingIndicatorPos.setLocation(
					(br-sr)*Math.sin(Math.toRadians(headingIndicator)) -ir,
					(br-sr)*-Math.cos(Math.toRadians(headingIndicator)) -ir);
		}
	}

	private double normalScale(double min, double max, double pos){
		//return the position of pos on the scale min-max, normalised to 0-1
		return (pos-min) / (max-min);
	}
	
	private Boolean inButton(Point2D pos, int mouseX, int mouseY){
		int	x = (int)Math.floor(pos.getX()),
			y = (int)Math.floor(pos.getY());
		//normalise to internal coordinates
		mouseX -= flight.getX();
		mouseY -= flight.getY();

		return (mouseX>x && mouseX<(x+buttonWidth) && 
				mouseY>y && mouseY<(y+buttonHeight));

	}

	private void eventTargetSpeed(double speed){
		System.out.println(String.format("speed := %1$3d", speed));
		setIndicatorPos();
	}

	private void eventTargetAltitude(double altitude){
		System.out.println(String.format("altitude := %1$4d", altitude));
		setIndicatorPos();
	}

	private void eventTargetHeading(double heading){
		System.out.println(String.format("altitude := %1$3d", heading));
		setIndicatorPos();
	}

	private void eventAbort(){
		System.out.println("abort");
		setIndicatorPos();
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
		speedBase = sliderBase.getScaledCopy(speedSize, sliderWidth);
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
		altBase.setCenterOfRotation(0, 0);	//{!} will leave image positioned sliderWidth to the left
		altBase.setRotation(90);
		speedBase = sliderBase.getScaledCopy(speedSize, sliderWidth);
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
		mode = NONE;
		this.flight = flight;
		if (flight != null){
			altIndicator = flight.getTargetAltitude();
			speedIndicator = flight.getFlightPlan().getVelocity();	//{!}
			headingIndicator = flight.getTargetHeading();
			setIndicatorPos();
		}
	}


	@Override
	public void inputEnded() {};

	@Override
	public void inputStarted() {};

	@Override
	public boolean isAcceptingInput() {
		return (flight != null);
	}

	@Override
	public void setInput(Input input) {
		if (this.input != null){
			this.input.removeMouseListener(this);
		}
		this.input = input;
		input.addMouseListener(this);
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		switch (mode){
		case NONE:	//nothing to check
			break;
			
		//reposition sliders
		case ALT:
			
			break;
		case SPEED:
			
			break;
		case HEADING:
			
			break;
			
		//check if should invalidate button presses
		case CMD:
			if (!inButton(cmdPos, newx, newy))
				mode = NONE;
			break;
		case ABORT:
			if (!inButton(abortPos, newx, newy))
				mode = NONE;
			break;
		}
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {}

	@Override
	public void mousePressed(int button, int x, int y) {
		if (Input.MOUSE_LEFT_BUTTON == button){
			//System.out.println("Mouse pressed");
			//check for which component button is in
			//{!}
			if (inButton(cmdPos, x, y))
				mode = CMD;
			if (inButton(abortPos, x, y))
				mode = ABORT;
		}
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		if (Input.MOUSE_LEFT_BUTTON == button){
			//System.out.println("Mouse released");
			switch (mode){
			case NONE:	//nothing to check
				break;
				
			//release sliders
			case ALT:
				
				break;
			case SPEED:
				
				break;
			case HEADING:
				
				break;
				
			//release buttons
			case CMD:
				//interpret context
				if (flight.getAltitude() == 0)
					eventTakeoff();
				else eventLand();
				break;
			case ABORT:
				eventAbort();
				break;				
			}
			mode = NONE;
		}
	}

	@Override
	public void mouseWheelMoved(int change) {}

}
