package Enemies;

import java.awt.Graphics2D; 
import java.awt.image.BufferedImage;






import javax.imageio.ImageIO;


import TileMap.TileMap;
import Entity.Enemy;
import Entity.Player;
import Handlers.Content;
import Main.GamePanel;

public class Spikey extends Enemy {

	private BufferedImage[] idleSprites;
	private BufferedImage[] jumpSprites;
	private BufferedImage[] fallSprites;
	private boolean active;
	private Player player;
	private int hpBarX;
	private int hpBarY;
	private int hpBarDistance;
	private BufferedImage hpBar;
	
	private static final int IDLE = 0;
	private static final int JUMPING = 1;
	

	private int directionRefresher;
	private int jumpDelay = 1;
	private int action;
	
	public Spikey(TileMap tm, Player p) {
		super(tm);
		health = maxHealth = 5;
		//loads hp bar stuff
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
		
		width = 30;
		height = 34;
		cwidth = 26;
		cheight = 30;
		enemyScore = 50;
		
		damage = 1;
		
		
		moveSpeed = 1;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.5;
		
		
		idleSprites = Content.Spikey[2];
		fallSprites = Content.Spikey[1];
		jumpSprites = Content.Spikey[0];

		animation.setFrames(idleSprites);
		animation.setDelay(-1);
		
	
		
		left = true;
		facingRight = false;
		
		}
	
	public int getEnemyScore() {
		return enemyScore;
	}
	//gets next position
	private void getNextPosition() {
		// if player is close enough slows down so wont jump over
		if(left) 
			if(Math.abs(player.getx() - x) < 30){
			dx = -moveSpeed / 1.2;
			}
			else dx = - moveSpeed;
		else if (right){
			if(Math.abs(player.getx() - x) < 30){
				dx = moveSpeed / 1.2;
			}
			else dx = moveSpeed;
		}
		else dx = 0;
		
		if(falling) {
			dy += fallSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
		if(jumping && !falling) {
			dy = jumpStart;
		}
	}
	//hp bar location on screen
	private void hpBarLocation() {
		hpBarX = (int) (x + xmap - width / 2);
		hpBarY = (int) (y + ymap - height / 2);
	}
	
	public void update() {

		hpBarLocation();
		if(!active) {
			if(Math.abs(player.getx() - x) < GamePanel.WIDTH) active = true;
			return;
		}
		
		if(flinching) {
			flinchCount++;
			if(flinchCount == 6) flinching = false;
		}
		
		getNextPosition();
		checkTileMapCollision("blocked");
		setPosition(xtemp, ytemp);
		
		// update animation
		animation.update();
		
		if(player.getx() < x) facingRight = false;
		else facingRight = true;
		
		setAction();
		

		}
	
	private void setAction() {
		//monster state machine
		if(action == 0) {
			
			if(currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(idleSprites);
				animation.setDelay(-1);
			}
			
			directionRefresher++;
			if(directionRefresher >= jumpDelay) {
				action++;
				directionRefresher = 0;
			}
		}
		// jump away
		if(action == 1) {
	
			if(currentAction != JUMPING) {
				currentAction = JUMPING;
			animation.setFrames(jumpSprites);
			animation.setDelay(5);
			}
			jumping = true;
			if(facingRight) right = true;
			else left = true;
			
			if(currentAction == JUMPING && animation.hasPlayedOnce()) {
				action++;
				animation.setFrames(fallSprites);
				animation.setDelay(-1);
			}
		}

		// done jumping
		if(action == 2) {
			if(dy == 0){
				left = right = jumping = false;
				action = 0;
			}
		}
	}
	
	public void draw(Graphics2D g){
		if(flinching) {
			if(flinchCount == 0 || flinchCount == 2) return;
		}
		setMapPosition();
		for(int i = 0; i < health; i++) {
			g.drawImage(hpBar,hpBarX + i * hpBarDistance,hpBarY - cheight / 2, null );
			}
			
		super.draw(g);
	}
		
	}

