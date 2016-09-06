package Handlers;

public class ProgressHandler {
	
	private static int lvlLock = 1;
	
	public static int getLock() { return lvlLock; }
	public static void setLock(int i) { lvlLock = i; }


}
