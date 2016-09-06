package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Audio.JukeBox;
import Handlers.Keys;
import Main.GamePanel;
import TileMap.Background;


public class HelpState extends GameState {

	private int currentChoice = 0;
	private Background bg;
	private BufferedImage hand;
	
	private String[] options = {
			"Back To Menu"
			};
	
	private Font font;
	
	public HelpState(GameStateManager gsm) {
		super(gsm);
		
		try {
			
			// load floating head
			hand = ImageIO.read(
					getClass().getResourceAsStream("/HUD/HUD.png")
				).getSubimage(0, 43, 15, 14);
			
			bg = new Background("/Backgrounds/HelpBG.png",1);
			bg.setVector(-0.0, 0);
			
			// titles and fonts
			font = new Font("Arial", Font.BOLD, 24);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void init() {}

	public void update() {
		
		//checking keyboard
		handleInput();
		
	}

	public void draw(Graphics2D g) {
		
		// draw bg
				bg.draw(g);
		
		g.setFont(font);
		
 for (int i = 0; i < options.length; i++) {
			 
			 //Allows to go "over" selections
			 if(currentChoice == -1) currentChoice = 0;
				else if(currentChoice == 1) currentChoice = 0;
			 
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
	}
	
	public void select() {
		
		if(currentChoice == 0) {
			//			PlayerSave.init();
					gsm.setState(GameStateManager.MENUSTATE);
					JukeBox.play("menuSelect");
				}
				else if ( currentChoice == 1) {
					System.out.println("Monster info incoming");
					JukeBox.play("menuSelect");
				}
	}

	public void handleInput() {
		
		if(Keys.isPressed(Keys.ENTER)) select();
		if(Keys.isPressed(Keys.UP)) {
			if(currentChoice > -1) {
				
				currentChoice--;
				JukeBox.play("menuSelect");
			}
		}
		if(Keys.isPressed(Keys.DOWN)) {
			if(currentChoice < options.length ) {
				
				currentChoice++;
				JukeBox.play("menuSelect");
			}
		}
	}

}
