package Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Enemy;
import Entity.Player;
import Handlers.Content;
import Main.GamePanel;
import Projectiles.SnailSpit;
import TileMap.TileMap;

public class SnailBoss extends Enemy {

	
	//basic monster stuff
	
		private ArrayList<Enemy> enemies;
		private BufferedImage[] walkSprites;
		private BufferedImage[] defendSprites;
		private BufferedImage[] attackSprites;
		private boolean active;
		private boolean firedOnce;
		private Player player;
		private int hpBarX;
		private int hpBarY;
		private int hpBarDistance;
		private BufferedImage hpBar;
		
		private SnailSpit ss;
		
		//animations
		private static final int WALK = 0;
		private static final int DEFENDING = 1;
		private static final int ATTACKING = 2;

		private int action;
		
		public SnailBoss(TileMap tm, Player p, ArrayList<Enemy> en) {
			super(tm);
			health = maxHealth = 60;
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
			enemyScore = 1000;
			width = 80;
			height = 52;
			cwidth = 70;
			cheight = 44;
			
			damage = 1;
			
			firedOnce = false;
			
			moveSpeed = 0.8;
			fallSpeed = 0.15;
			maxFallSpeed = 4.0;
			jumpStart = -4.5;
			
			walkSprites = Content.Snail[0];
			defendSprites = Content.Snail[1];
			attackSprites = Content.Snail[2];

			animation.setFrames(walkSprites);
			animation.setDelay(12);
			
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
						animation.setDelay(12);
					}
						
					if(flinching) {
						firedOnce = false;
						action++;
					}
			}
					// defends 
					if(action == 1) {
				
						if(currentAction != DEFENDING) {
							currentAction = DEFENDING;
						animation.setFrames(defendSprites);
						animation.setDelay(6);
						}
						attacking = true;
						if(facingRight) { right = true; }
						else { left = true; }

						//sets little wait in before launching attack
						if(animation.getFrame() == 9){
							animation.setDelay(100);
						}
						
						// checks that defend animation and delay as played
						
						if(currentAction == DEFENDING && animation.getFrame()>=10) {
							animation.setFrames(attackSprites);
							action++;
						}
					}

					//attacking
					if(action == 2) {
						if(currentAction != ATTACKING) {
							currentAction = ATTACKING;
							animation.setDelay(6);
						}
						// checks that bat shooted only once
						if(currentAction == ATTACKING && firedOnce == false && animation.getFrame() == 5 && !falling){
							firedOnce = true;
							// hp 100% -66%
							if(health > (maxHealth / 3 * 2)){
								generateSpits(1);
							}
							//hp 33%-0%
							else if (health < (maxHealth / 3)) {
								generateSpits(3);
							}
							// hp 66%-33%
							else if(health < (maxHealth / 3 * 2)){
								generateSpits(2);
							}
							else{
							// just in case
								generateSpits(2);
							}
						}
						if(currentAction == ATTACKING && animation.getFrame() >= 8) {
							moveSpeed = 0.8;
							attacking = false;
							action++;
						}
					}			
					if(action == 3) {
						action = 0;
					}
				}
		private void generateSpits(int state){
			
			if(state == 1){
				ss = new SnailSpit(tileMap, 1,0);
				ss.setPosition(x, y);
				enemies.add(ss);
				ss = new SnailSpit(tileMap, -1,0);
				ss.setPosition(x, y);
				enemies.add(ss);
			}
			else if(state == 2){
				ss = new SnailSpit(tileMap, 1,0);
				ss.setPosition(x, y);
				enemies.add(ss);
				ss = new SnailSpit(tileMap, -1,0);
				ss.setPosition(x, y);
				enemies.add(ss);
				
				ss = new SnailSpit(tileMap, 1, 1);
				ss.setPosition(x, y);
				enemies.add(ss);
				ss = new SnailSpit(tileMap, -1, 1);
				ss.setPosition(x, y);
				enemies.add(ss);
			}
			else if(state == 3){
				ss = new SnailSpit(tileMap, 1,0);
				ss.setPosition(x, y);
				enemies.add(ss);
				ss = new SnailSpit(tileMap, -1,0);
				ss.setPosition(x, y);
				enemies.add(ss);
				
				ss = new SnailSpit(tileMap, 1, 1);
				ss.setPosition(x, y);
				enemies.add(ss);
				ss = new SnailSpit(tileMap, -1, 1);
				ss.setPosition(x, y);
				enemies.add(ss);
				
				ss = new SnailSpit(tileMap, 0, 1);
				ss.setPosition(x, y);
				enemies.add(ss);
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

			if(animation.getFrame() == 3 && action == WALK){
				animation.setFrame(0);
			}
		
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