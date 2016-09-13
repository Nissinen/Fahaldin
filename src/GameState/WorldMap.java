package GameState;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import Audio.JukeBox;
import Handlers.Keys;
import Handlers.ProgressHandler;
import Handlers.SaveGame;
import Main.GamePanel;
import TileMap.Background;

public class WorldMap extends GameState {
	
	private BufferedImage hand;
	private BufferedImage pointer;
	private BufferedImage notOpenPointer;
	private Background bg;
	private List<Image> levels = new ArrayList<Image>();
	private int currentChoice = 0;
	private int previousChoice = 0;
	private int openLevels = ProgressHandler.getLock();
	private boolean saved;
	private int handX;
	private int handY;
	private SaveGame saveGame;
	
	
	public WorldMap(GameStateManager gsm) {
		super(gsm);
		init();
	}

	@Override
	public void init() {
		
		try {
			
			// load floating head
			hand = ImageIO.read(
					getClass().getResourceAsStream("/HUD/HUD.png")
				).getSubimage(0, 43, 15, 14);
			
			//load level pointers
			pointer = ImageIO.read(getClass().getResourceAsStream("/HUD/WorldMapPointer.png"));
			notOpenPointer = ImageIO.read(getClass().getResourceAsStream("/HUD/WorldMapPointerNotOpen.png"));
			// sets background
			bg = new Background("/Backgrounds/WorldMap.png",1);
			bg.setVector(-0.0, 0);

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		handX = 230;
		handY = 150;
		JukeBox.stop("level1");
		JukeBox.stop("level2");
		JukeBox.stop("level3");
		JukeBox.stop("lastboss");
		saveGame = new SaveGame();
		for(int i = 0; i < 3; i++){
			if(i < openLevels){
				levels.add(pointer);
			}
			else{
				levels.add(notOpenPointer);
			}
		}
	}

	@Override
	public void update() {
		handleInput();
		
		for (int i = 0; i < levels.size(); i++) {
			 
			 //Allows to go "over" selections
			if(currentChoice == -1){ currentChoice = 2; }
			else if(currentChoice == 3) { currentChoice = 0; }
			 
			 // moves marker
	            if (i == currentChoice) {    
	            	//when game starts points to level 1
	            	if(currentChoice == 0 && previousChoice == 0){
	            		handX = 210;
	            		handY = 150;
	            	}
	            	//level 1 to level 2
	            	else if (currentChoice == 1 && previousChoice == 0) {
	            		MarkerLocation(406, 236);
	            	}
	            	//from level 2 to level 1
	            	else if(previousChoice == 1 && currentChoice == 0){
	            		MarkerLocation(210,150);
	            	}
	            	//from level 1 to level 3
	            	else if(previousChoice == 0 && (currentChoice == -1 || currentChoice == 2)){
	            		MarkerLocation(220,230);
	            	}
	            	//from level 3 to level 1
	            	else if(previousChoice == 2 && (currentChoice == 3 || currentChoice == 0)){
	            		MarkerLocation(210,150);
	            	}
	            	//from level 2 to level 3
	            	else if(previousChoice == 1 && currentChoice == 2){
	            		MarkerLocation(220,230);
	            	}
	            	//from level 3 to level 2
	            	else if(previousChoice == 2 && currentChoice == 1){
	            		MarkerLocation(406,236);
	            	}
	            }
	        }
		}
	
	private void select() {
		if(currentChoice == 0 && ProgressHandler.getLock() >= 1) {

			JukeBox.play("menuSelect");
			gsm.setState(GameStateManager.LEVEL1STORY);
		}
		else if ( currentChoice == 1 && ProgressHandler.getLock() >= 2) {
			gsm.setState(GameStateManager.LEVEL2STORY);
		}
		
		else if(currentChoice == 2 && ProgressHandler.getLock() >= 3) {
			gsm.setState(GameStateManager.LEVEL3STORY);
		}
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		//iterate through level list
		for(int i = 0; i < levels.size(); i++){
			if(i == 0){
				g.drawImage(levels.get(i), 230, 150, null);
			}
			else if(i == 1){
				g.drawImage(levels.get(i), 425, 235, null);
			}
			else if (i == 2) {
				g.drawImage(levels.get(i), 240, 230, null);
			}
		}
		
		for (int i = 0; i < levels.size(); i++) {
			 
			 //Allows to go "over" selections
			 if(currentChoice == -1){ currentChoice = 2; }
				else if(currentChoice == 3) { currentChoice = 0; }
			 
			 // moves marker
	            if (i == currentChoice) {         
	            	if(currentChoice == 0){
	            		g.drawImage(hand,  handX , handY, null);
	            	}
	            	else if (currentChoice == 1) {
	            		g.drawImage(hand, handX, handY, null);
	            	}
	            	else if (currentChoice == 2) {
	            		g.drawImage(hand, handX, handY, null);
	            	}
	            }

	     }
			g.drawString("Save with S Button", GamePanel.WIDTH / 20, 30);
		if(saved == true){
			g.drawString("Game saved!", GamePanel.WIDTH / 20, 60);
		}
	}

	@Override
	public void handleInput() {
		if(Keys.isPressed(Keys.BUTTONS)){saveGame.saveGame(); saved = true;}
		if(Keys.isPressed(Keys.ENTER)) {saved = false; select(); }
		if(Keys.isPressed(Keys.UP) || Keys.isPressed(Keys.RIGHT)) {
			if(currentChoice > -1) {

				previousChoice = currentChoice;
				currentChoice++;
				JukeBox.play("menuSelect");
			}
		}
		if(Keys.isPressed(Keys.DOWN) || Keys.isPressed(Keys.LEFT)) {
			if(currentChoice < levels.size() ) {

				previousChoice = currentChoice;
				currentChoice--;
				JukeBox.play("menuSelect");
			}
		}
	}
	//sets worldmap marker location
	private void MarkerLocation(int x, int y){
		if(handX < x){
			handX += 2;
		}
		else if(handX > x){
			handX -= 2;
		}
		if(handY < y){
			handY += 2;
		}
		else if(handY > y){
			handY -= 2;
		}
	}
}
