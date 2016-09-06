package Handlers;

import java.awt.event.KeyEvent;

public class Keys {
	
	public static final int NUM_KEYS = 16;
	
	public static boolean keyState[] = new boolean[NUM_KEYS];
	public static boolean prevKeyState[] = new boolean[NUM_KEYS];
	
	
	//Gameplay buttons
	public static int UP = 0;
	public static int LEFT = 1;
	public static int DOWN = 2;
	public static int RIGHT = 3;
	public static int BUTTONZ = 4;
	public static int BUTTONX = 5;
	public static int BUTTONC = 6;
	public static int BUTTONV = 7;
	public static int BUTTONS = 8;
	public static int ENTER = 9;
	public static int ESCAPE = 10;
	
	
	//Checks pressed buttons
	public static void keySet(int i, boolean b) {
		if(i == KeyEvent.VK_UP) keyState[UP] = b;
		else if(i == KeyEvent.VK_LEFT) keyState[LEFT] = b;
		else if(i == KeyEvent.VK_DOWN) keyState[DOWN] = b;
		else if(i == KeyEvent.VK_RIGHT) keyState[RIGHT] = b;
		else if(i == KeyEvent.VK_Z) keyState[BUTTONZ] = b;
		else if(i == KeyEvent.VK_X) keyState[BUTTONX] = b;
		else if(i == KeyEvent.VK_C) keyState[BUTTONC] = b;
		else if(i == KeyEvent.VK_V) keyState[BUTTONV] = b;
		else if(i == KeyEvent.VK_S) keyState[BUTTONS] = b;
		else if(i == KeyEvent.VK_ENTER) keyState[ENTER] = b;
		else if(i == KeyEvent.VK_ESCAPE) keyState[ESCAPE] = b;
	}
	
	//remembers key state
	public static void update() {
		for(int i = 0; i < NUM_KEYS; i++) {
			prevKeyState[i] = keyState[i];
		}
	}
	
	//Checks if pressed
	public static boolean isPressed(int i) {
		return keyState[i] && !prevKeyState[i];
	}
	
	//anything pressed
	public static boolean anyKeyPress() {
		for(int i = 0; i < NUM_KEYS; i++) {
			if(keyState[i]) return true;
		}
		return false;
	}
	
}

