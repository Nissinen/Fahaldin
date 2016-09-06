package Enemies;

import java.awt.Graphics2D; 
import java.awt.image.BufferedImage;





import javax.imageio.ImageIO;

import TileMap.TileMap;
import Entity.Enemy;
import Entity.Player;
import Handlers.Content;
import Main.GamePanel;

public class KnightBody extends Enemy {

	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;
	private boolean active;
	private Player player;
	private int hpBarX;
	private int hpBarY;
	private int counter;
	private int currentFrame;
	private int hpBarDistance;
	private BufferedImage hpBar;
	
	public KnightBody(TileMap tm, Player p) {
		super(tm);
		health = maxHealth = 80;
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
		isBoss = true;
		player = p;
		width = 240;
		height = 200;
		cwidth = 60;
		cheight = 100;
		enemyScore = 10;
		
		damage = 1;

		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		
		sprites = Content.Knight[0];
		hitSprites = Content.Knight[1];
		animation.setFrames(sprites);
		animation.setDelay(20);
		
		left = true;
		facingRight = false;
		
		}
	// gets next position
	private void getNextPosition() {
	
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
		
		getNextPosition();
		
		checkTileMapCollision("blocked");
		calculateCorners(x, ydest + 1, "blocked");
		if(!bottomLeft) {
			left = false;
			right = facingRight = true;
		}
		if(!bottomRight) {
			left = true;
			right = facingRight = false;
		}
		setPosition(xtemp, ytemp);
		
		if(dx == 0) {
			left = !left;
			right = !right;
			facingRight = !facingRight;
		}
		
		checkHitted();
		
		// update animation
		animation.update();
		
	}
		
	private void checkHitted(){
		if(flinching) {
			currentFrame = animation.getFrame();
			animation.setFrames(hitSprites);
			animation.setFrame(currentFrame);
			animation.setDelay(20);
			counter++;
		}
		else if(counter >= 5 && !flinching){
			counter = 0;
			currentFrame = animation.getFrame();
			animation.setFrames(sprites);
			animation.setFrame(currentFrame);
			animation.setDelay(20);
		}

	}
	
	public void draw(Graphics2D g){
		
		setMapPosition();
		for(int i = 0; i < health; i++) {
			g.drawImage(hpBar,hpBarX + i * hpBarDistance + (width / 3),hpBarY - cheight / 2 - 5, null );
			}
			
		super.draw(g);
	}
		
	}

