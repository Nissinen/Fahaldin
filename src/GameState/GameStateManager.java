package GameState;

import Audio.JukeBox;
import Main.GamePanel;



public class GameStateManager {

	private GameState[] gameStates;
	private int currentState;
	
	//private PauseState pauseState;
	//private boolean paused;
	
	public static final int NUMGAMESTATES = 17;
	public static final int MENUSTATE = 0;
	public static final int HELPSTATE = 1;
	public static final int WORLDMAP = 2;
	public static final int LEVEL1STATE = 3;
	public static final int LEVEL1SECONDSTATE = 4;
	public static final int LEVEL1BOSS = 5;
	public static final int LEVEL2STATE = 6;
	public static final int LEVEL1STORY = 7;
	public static final int LEVEL2BOSS = 8;
	public static final int LEVEL1BOSSSTORY = 9;
	public static final int LEVEL2STORY = 10;
	public static final int LEVEL3STATE = 11;
	public static final int LEVEL2BOSSSTORY = 12;
	public static final int LEVEL3STORY = 13;
	public static final int LEVEL3BOSS = 14;
	public static final int LEVEL3BOSSSTORY = 15;
	public static final int STORYENDING = 16;


	//public static final int ACIDSTATE = 15;
	
	public GameStateManager() {
		
		JukeBox.init();
		
		gameStates = new GameState[NUMGAMESTATES];
		
	//	pauseState = new PauseState(this);
	//	paused = false;
		
		currentState = MENUSTATE;
		loadState(currentState);
		
	}
	//loads state
	private void loadState(int state) {
		if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		else if(state == LEVEL1STATE)
			gameStates[state] = new Level1State(this); 
		else if(state == WORLDMAP)
			gameStates[state] = new WorldMap(this); 
		else if(state == HELPSTATE)
			gameStates[state] = new HelpState(this);
		else if(state == LEVEL1SECONDSTATE)
			gameStates[state] = new Level1SecondState(this);
		else if(state == LEVEL1BOSS)
			gameStates[state] = new Level1BossState(this);
		else if(state == LEVEL2STATE)
			gameStates[state] = new Level2State(this);
		else if(state == LEVEL1STORY)
			gameStates[state] = new Level1Story(this);
		else if(state == LEVEL2BOSS)
			gameStates[state] = new Level2Boss(this);
		else if(state == LEVEL1BOSSSTORY)
			gameStates[state] = new Level1BossStory(this);
		else if(state == LEVEL2STORY)
			gameStates[state] = new Level2Story(this);
		else if(state == LEVEL3STATE)
			gameStates[state] = new Level3State(this);
		else if(state == LEVEL2BOSSSTORY)
			gameStates[state] = new Level2BossStory(this);
		else if(state == LEVEL3STORY)
			gameStates[state] = new Level3Story(this);
		else if(state == LEVEL3BOSS)
			gameStates[state] = new Level3Boss(this);
		else if(state == LEVEL3BOSSSTORY)
			gameStates[state] = new Level3BossStory(this);
		else if(state == STORYENDING)
			gameStates[state] = new StoryEnding(this);
	}
	//unloads state so it doesnt use memory
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	//sets current state
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}
	
	//public void setPaused(boolean b) { paused = b; }
	
	//calls current state update
	public void update() {
	/*	if(paused) {
			pauseState.update();
			return;
		}*/
		if(gameStates[currentState] != null) gameStates[currentState].update();
	}
	
	//calls current state draw
	public void draw(java.awt.Graphics2D g) {
		/*if(paused) {
			pauseState.draw(g);
			return;
		}*/
		if(gameStates[currentState] != null) gameStates[currentState].draw(g);
		else {
			g.setColor(java.awt.Color.BLACK);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		}
	}
	
}

