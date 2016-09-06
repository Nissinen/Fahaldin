package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Audio.JukeBox;
import Handlers.Keys;
import Handlers.LoadGame;
import Main.GamePanel;
import TileMap.Background;


public class MenuState extends GameState {
	
	private BufferedImage hand;
	private Background bg;
	private int currentChoice = 0;
	private String[] options = {
		"Start Game",
		"Load Game",
		"Help",
		"Quit Game"
	};
	
	
	private Font font;
	private Font font2;
	
	
	public MenuState(GameStateManager gsm) {
		
		super(gsm);
		
		try {
			
			// load floating head
			hand = ImageIO.read(
					getClass().getResourceAsStream("/HUD/HUD.png")
				).getSubimage(0, 43, 15, 14);
			
			// sets background
			bg = new Background("/Backgrounds/MenuBG.png",1);
			bg.setVector(-0.0, 0);
			
			// titles and fonts
			font = new Font("Arial", Font.BOLD, 24);
			font2 = new Font("Arial", Font.BOLD, 10);
			
			// load sound fx
		//	JukeBox.load("/SFX/menuoption.mp3", "menuoption");
			JukeBox.stop("level1");
			JukeBox.stop("level2");
			JukeBox.load("/SFX/MenuSelect.mp3", "menuSelect");
			JukeBox.load("/Music/MenuOpen.mp3", "menuOpen");
			JukeBox.play("menuOpen");
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void init() {}
	
	public void update() {
		
		// check keys
		handleInput();
		
	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		
		
		
		// draw menu options and selection
		g.setFont(font);

		 for (int i = 0; i < options.length; i++) {
			 
			 //Allows to go "over" selections
			 if(currentChoice == -1) currentChoice = 3;
				else if(currentChoice == 4) currentChoice = 0;
			 
			 // Paints selected option red and moves marker
	            if (i == currentChoice) {
	                g.setColor(Color.RED);         
	                g.drawImage(hand,  GamePanel.WIDTH / 2 + GamePanel.WIDTH / 9 , GamePanel.HEIGHT / 2 - GamePanel.HEIGHT / 18 + i * 23, null);
	            }
	            else {
	                g.setColor(Color.BLACK);
	            }
	        // Drawing options
	            g.setFont(new Font("Arial", Font.BOLD, 24));
	            g.drawString(options[i], GamePanel.WIDTH / 2 + GamePanel.WIDTH / 8 + GamePanel.WIDTH / 55, GamePanel.HEIGHT / 2 - GamePanel.HEIGHT / 50 + i * 23);
	        }

		// other
		g.setColor(Color.WHITE);
		g.setFont(font2);
		g.drawString("2015 Olli N.", GamePanel.WIDTH / 50, GamePanel.HEIGHT - GamePanel.HEIGHT / 20 );
		
	}
	
	private void select() {
		if(currentChoice == 0) {
		//	PlayerSave.init();
			JukeBox.play("menuSelect");
			gsm.setState(GameStateManager.WORLDMAP);
		}
		else if ( currentChoice == 1){
			JukeBox.play("menuSelect");
			LoadGame.loadGame();
			gsm.setState(GameStateManager.WORLDMAP);
		}
		else if ( currentChoice == 2) {
			JukeBox.play("menuSelect");
			gsm.setState(GameStateManager.HELPSTATE);
		}
		
		else if(currentChoice == 3) {
			System.exit(0);
		}
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ENTER)) select();
		if(Keys.isPressed(Keys.UP)) {
			if(currentChoice > -1) {
				JukeBox.play("menuSelect");
				currentChoice--;
			}
		}
		if(Keys.isPressed(Keys.DOWN)) {
			if(currentChoice < options.length ) {
				JukeBox.play("menuSelect");
				currentChoice++;
			}
		}
	}
	
}




