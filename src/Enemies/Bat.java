package Enemies;

import java.awt.Graphics2D; 

import java.awt.image.BufferedImage;


import java.util.ArrayList;

import javax.imageio.ImageIO;


import TileMap.TileMap;
import Entity.Enemy;
import Entity.Player;
import Handlers.Content;
import Main.GamePanel;
import Projectiles.BatBullet;

public class Bat extends Enemy {

	//basic monster stuff
	
	private ArrayList<Enemy> enemies;
	private BufferedImage[] walkSprites;
	private BufferedImage[] attackSprites;
	private boolean active;
	private boolean firedOnce;
	private Player player;
	private int hpBarX;
	private int hpBarY;
	private int hpBarDistance;
	private BufferedImage hpBar;
	
	//animations
	private static final int WALK = 0;
	private static final int ATTACKING = 1;

	private int attackRefresher;
	private int attackDelay = 120;
	private int action;
	
	public Bat(TileMap tm, Player p, ArrayList<Enemy> en) {
		super(tm);
		health = maxHealth = 3;
		// monster health bar
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
		
		player = p;
		enemies = en;
		enemyScore = 25;
		width = 35;
		height = 25;
		cwidth = 32;
		cheight = 20;
		
		damage = 1;
		
		firedOnce = false;
		
		moveSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.5;
		
		walkSprites = Content.Bat[0];
		attackSprites = Content.Bat[1];

		animation.setFrames(walkSprites);
		animation.setDelay(6);
		
		left = true;
		facingRight = false;
		
}
	
	private void setAction() {
		//monster state machine
		//walk
		if(action == 0) {
					if(currentAction != WALK) {
						currentAction = WALK;
						animation.setFrames(walkSprites);
						animation.setDelay(6);
					}
					
					attackRefresher++;
					if(attackRefresher >= attackDelay) {
						firedOnce = false;
						action++;
						attackRefresher = 0;
					}
				}
				// attacks once
				if(action == 1) {
			
					if(currentAction != ATTACKING) {
						currentAction = ATTACKING;
					animation.setFrames(attackSprites);
					animation.setDelay(6);
					}
					attacking = true;
					if(facingRight) { right = true; }
					else { left = true; }
					// checks that bat shooted only once
					if(currentAction == ATTACKING && firedOnce == false && animation.getFrame() == 5 && !falling){
						firedOnce = true;
						BatBullet bb = new BatBullet(tileMap, right);
						bb.setPosition(x, y);
						enemies.add(bb);
					}
					// checks if attacked once
					if(currentAction == ATTACKING && animation.hasPlayedOnce()) {
						moveSpeed = 0.4;
						attacking = false;
						action++;
						action = 0;
					}
				}

				// done attacking
				if(action == 2) {
						action = 0;
				//	}
				}
			}
	// gets next movement position
	private void getNextPosition() {
		if(!attacking){
		if(left) dx = -moveSpeed;
		else if (right) dx = moveSpeed;
		}
		else dx = 0;
		
		if(falling) {
			dy += fallSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
	}
	// counts hp bar location on screen
	private void hpBarLocation() {
		hpBarX = (int) (x + xmap - width / 2);
		hpBarY = (int) (y + ymap - height / 2);
	}
	
	public int getEnemyScore() {
		return enemyScore;
	}
	
	public void update() {

		setAction();

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
		
		if(!attacking){
			if(dx == 0) {
				left = !left;
				right = !right;
				facingRight = !facingRight;
			}
		}
		
		// update animation
		animation.update();
		
	}
		
	public void draw(Graphics2D g){
		if(flinching) {
			if(flinchCount == 0 || flinchCount == 2) return;
		}
		setMapPosition();
		// draws health bar
		for(int i = 0; i < health; i++) {
			g.drawImage(hpBar,hpBarX + i * hpBarDistance,hpBarY - cheight / 2 - 5, null );
			}
			
		super.draw(g);
	}
		
}