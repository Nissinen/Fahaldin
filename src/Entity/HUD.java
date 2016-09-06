package Entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class HUD {
	
	private Player player;
	
	private BufferedImage hudPicture;
	private BufferedImage heart;
	private Font font;
	private Font clockFont;
	
	private int width;
	private int height;
	
	//reads hud stuff
	public HUD(Player p) {
		
		font = new Font("ARIAL", Font.BOLD, 24);
		clockFont = new Font ("ARIAL", Font.BOLD, 10);
		player = p;
		
		try{
			BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/HUD/HUD.png"));
			
			hudPicture = image.getSubimage(0, 0, 200, 30);
			
			width = 200;
			height = 30;
			
			heart = image.getSubimage(0, 30, 14, 13);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//draws player hud
	public void draw(Graphics2D g) {
		
		g.drawImage(hudPicture, GamePanel.WIDTH / 2 - width / 2 , 0, null);
		
		g.setColor(Color.BLACK);
		g.setFont(font);
		g.drawString(player.getLives() + "", GamePanel.WIDTH / 2 + width / 18, height / 2 + height / 7);
		g.setFont(clockFont);
		g.drawString(player.getTimeToString(), GamePanel.WIDTH / 3 + width / 16, height / 3 + height / 8);
		g.drawString(player.getScore() + "", GamePanel.WIDTH / 3 + width / 5 + width / 80, height / 3 + height / 8);
		
		for(int i = 0; i < player.getHealth(); i++) {
			
			g.drawImage(heart, GamePanel.WIDTH / 2 + width / 5 + i * 15, 3, null );
			
		}
	}
}
