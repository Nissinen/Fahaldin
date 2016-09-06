package Enemies;

import java.awt.Graphics2D; 
import java.awt.image.BufferedImage;





import javax.imageio.ImageIO;

import TileMap.TileMap;
import Entity.Enemy;
import Entity.Player;
import Handlers.Content;
import Main.GamePanel;

public class Ghost extends Enemy {

	private BufferedImage[] sprites;
	private boolean active;
	private Player player;
	private int hpBarX;
	private int hpBarY;
	private int hpBarDistance;
	private BufferedImage hpBar;
	
	public Ghost(TileMap tm, Player p) {
		super(tm);
		health = maxHealth = 30;
		//loads health bar stuff
		try{
			if(health < 20){
			BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/HUD/HpBar.png"));
			
			if(health == 1){	hpBar = image.getSubimage(0, 0, 26, 3); 	hpBarDistance = 0;}
			if(health == 2){	hpBar = image.getSubimage(0, 0, 13, 3); 	hpBarDistance = 14;}
			if(health >= 3){	hpBar = image.getSubimage(0, 0, 10, 3);	hpBarDistance = 11;}
			if(health >= 5){	hpBar = image.getSubimage(0, 0, 5, 3);	hpBarDistance = 6; }
			if(health >= 10){	hpBar = image.getSubimage(0, 0, 2, 3);	hpBarDistance = 3;}
			if(health >= 15){	hpBar = image.getSubimage( 0, 0, 1, 3);	hpBarDistance = 2; }

			}
			else{
				BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/HUD/HpBar20+.png"));
				if(health >= 20) { hpBar = image.getSubimage( 0, 0, 1, 3); 	hpBarDistance = 1; }
			}
				
		}
		catch(Exception e){
			e.printStackTrace();
		}
		//monster basic stuff
		
		player = p;
		width = 64;
		height = 64;
		cwidth = 40;
		cheight = 40;
		enemyScore = 100;
		damage = 1;
		
		sprites = Content.Ghost[0];
		animation.setFrames(sprites);
		animation.setDelay(12);
		
		}
	// gets next position
	private void getNextPosition() {
		
		if(player.getx() == x){ dx = 0; facingRight = true;}
		else if(player.getx() < x){ dx = -0.5; facingRight = true;}
		else{ dx = 0.5; facingRight = false;}
			
		if(player.gety() < y){ dy = -0.5; }
		else dy = 0.5;

	}
	//hp bar location on screen
	private void hpBarLocation() {
		hpBarX = (int) (x + xmap - width / 2);
		hpBarY = (int) (y + ymap - height / 2);
	}
	
	public int getEnemyScore() {
		return enemyScore;
	}
	
	//updates movements
	public void update() {

		hpBarLocation();
		if(!active) {
			if(Math.abs(player.getx() - x) < GamePanel.WIDTH) active = true;
			return;
		}
		
		// check if done flinching
		if(flinching) {
			flinchCount++;
			if(flinchCount == 6) flinching = false;
		}
		
		checkTileMapCollision("normal");
		setPosition(xtemp, ytemp);
		getNextPosition();

		// update animation
		animation.update();
		
	}
	
	public void draw(Graphics2D g){
		if(flinching) {
			if(flinchCount == 0 || flinchCount == 2) return;
		}
		setMapPosition();
		for(int i = 0; i < health; i++) {
			g.drawImage(hpBar,hpBarX + i * hpBarDistance+ 15,hpBarY - cheight / 20 - 5, null );
			}
			
		super.draw(g);
	}
		
	}

