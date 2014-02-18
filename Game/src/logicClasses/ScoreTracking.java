package logicClasses;

public class ScoreTracking {
	
	private int currentScore;
	private int waypointScore;
	private static final int TIMESCORE = 2;		//constant for the time scoring
	
	
	//CONSTRUCTOR
	
	public ScoreTracking() {
		currentScore = 0;		//initialise the score to 0
	}
	
	//METHODS
	
	public int updateWaypointScore(int closestDistance){
					
		if (closestDistance >= 0 && closestDistance <= 10){		//checks to see if the plane is within 10 pixels
			waypointScore = 100;								//if yes, the score given is 100 points
		}
					
		if (closestDistance >= 11 && closestDistance <= 20){	
			waypointScore = 50;
		}
					
		if (closestDistance >= 21 && closestDistance <= 30){
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
	
	public void resetScore(){
		currentScore = 0;
	}
	
	public String toString(){
		String s = "Score = " + currentScore;
		return s;
	}
		
}
