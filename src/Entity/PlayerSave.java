package Entity;

public class PlayerSave {

	private static int lives = 5;
	private static int health = 3;
	private static long time = 0;
	private static int score = 0;
	
	public static void init() {
		//starting values
		lives = 5;
		health = 3;
		time = 0;
		score = 0;
	}
	
	//for saving and getting current values
	public static int getLives() { return lives; }
	public static void setLives(int i) { lives = i; }
	
	public static int getHealth() { return health; }
	public static void setHealth(int i) { health = i; }
	
	public static long getTime() { return time; }
	public static void setTime(long t) { time = t; }
	
	public static int getScore() { return score; }
	public static void setScore(int s) { score = s; }
	
}