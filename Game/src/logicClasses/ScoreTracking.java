package logicClasses;

public class ScoreTracking {
	
	private int currentScore = 0;
	private int waypointScore;
	private static final int TIMESCORE = 2;		//constant for the time scoring
	private static final int FLIGHTPLANCHANGE = 10;
	private static final int FLIGHTLOST = 50;
		
	//CONSTRUCTOR
	public ScoreTracking() {}
	
	//METHODS
	// Positive scoring
	public int updateWaypointScore(int closestDistance){
					
		if (closestDistance >= 0 && closestDistance <= 14){		//checks to see if the plane is within 10 pixels
			waypointScore = 100;								//if yes, the score given is 100 points
		}
					
		if (closestDistance >= 15 && closestDistance <= 28){	
			waypointScore = 50;
		}
					
		if (closestDistance >= 29 && closestDistance <= 42){
			waypointScore = 20;
		}
			
		return waypointScore;									//once the distance and points are found, return the score
					
	}
		
	public int updateScore(int score){
		return currentScore+=score;			//increase the current score by the score passed by parameter
	}
	
	public int updateTimeScore(){
		currentScore += TIMESCORE;
		return currentScore;
	}
	
	//Negative Socring
	public int reduceScoreOnFlightplanChange(){
		currentScore -= FLIGHTPLANCHANGE;
		return currentScore;
	}
	
	public int reduceScoreOnFlightLost(){
		currentScore -= FLIGHTLOST;
		return currentScore;
	}
	
	public void resetScore(){
		currentScore = 0;
	}
	
	public int getScore(){
		return currentScore;
	}
	
	public String toString(){
		String s = "Score = " + currentScore;
		return s;
	}
		
}
