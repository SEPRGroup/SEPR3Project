package logicClasses;

public class ScoreTracking {
	
	private int currentScore;
	private static final int WAYPOINTSCORE = 100;
	private static final int TIMESCORE = 2;
	
	
	//CONSTRUCTOR
	
	public ScoreTracking() {
		currentScore = 0;
	}
	
	//METHODS
	public int updateWaypointScore(){
		currentScore += WAYPOINTSCORE;
		return currentScore;
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
