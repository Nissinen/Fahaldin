package GameState;

import java.awt.Color;
import java.awt.Graphics2D;    
import GameState.GameStateManager;
import Handlers.Keys;
import Main.GamePanel;
import TileMap.Background;


public class Level1Story extends GameState {

	private Background blackBG;
	private Background story;
	
	public Level1Story(GameStateManager gsm) {
		super(gsm);
		init();
	}
	
	public void init() {
		
		// backgrounds
		blackBG = new Background("/Backgrounds/StoryBG.png", 0);
		story = new Background("/Backgrounds/lvl1Story.png", 0);

		// move backgrounds
		story.setVector(0, -0.5);
		
	}
	public void update() {
		
		blackBG.update();
		story.update();
		
		if(story.storyHasScrolled()){
			blackBG.update();
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		handleInput();
	}


	public void draw(Graphics2D g) {
		
		blackBG.draw(g);
		story.draw(g);
		g.setColor(Color.WHITE);
		g.drawString("Skip with ESC", GamePanel.WIDTH / 20, GamePanel.HEIGHT - 30);
	}

	public void handleInput() {
		if(Keys.isPressed(Keys.ESCAPE)) gsm.setState(GameStateManager.LEVEL1STATE);
	}
}
	