package Entity;

import java.awt.Graphics2D;  
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Audio.JukeBox;
import Entity.Thunder;
import Entity.FistAttack;
import TileMap.TileMap;


public class Player extends MapObject {
	
	
	// player stuff
	private int lives;
	private int health;
	private int maxHealth;
	private int hammerDamage;
	private boolean knockback;
	private boolean flinching;
	private long flinchCount;
	private int score;
	private boolean doubleJump;
	private boolean alreadyDoubleJump;
	private double doubleJumpStart;


	private long time;
	
	//fist attack
	private int fistAttackDamage;
	private ArrayList<FistAttack> fistAttacks; 
	
	private ArrayList<Thunder> thunders;
	
	// actions
	private boolean dashing;
	private boolean attacking;
	private boolean thunder;
	private boolean hammerattacking;
	private boolean charging;

	private boolean teleporting;
	
	
	// animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] NUMFRAMES = {
		4, 8, 1, 1, 8, 2, 8, 1, 10, 1, 8
	};
	private final int[] FRAMEWIDTHS = {
		30, 30, 30, 30, 30, 30, 30, 30, 90, 60, 30
	};
	private final int[] FRAMEHEIGHTS = {
		40, 40, 40, 40, 40, 40, 40, 40, 80, 40, 40
	};
	private final int[] SPRITEDELAYS = {
		50, 3, -1, -1, 3, 1, 2, -1 , 2, -1, 4
	};
	
	private Rectangle hammerF;
	private Rectangle hammerU;
	
	
	// animation actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int FISTATTACKING = 4;
	private static final int KNOCKBACK = 5;
	private static final int DASHING = 6;
	private static final int DODGE = 7;
	private static final int HAMMERATTACK = 8;
	private static final int DEAD = 9;
	private static final int THUNDER =10;
	
	
	private static final int TELEPORTING = 10;
	
	// emotes
	private BufferedImage confused;
	private BufferedImage surprised;
	public static final int NONE = 0;
	public static final int CONFUSED = 1;
	public static final int SURPRISED = 2;
	private int emote = NONE;
	
	public Player(TileMap tm) {
		
		super(tm);
		//hit boxes for hammer attack
		hammerF = new Rectangle(0, 0, 0, 0);
		hammerF.width = 26;
		hammerF.height = 40;
		hammerU = new Rectangle((int)x - 15, (int)y - 40 , 52, 20);
		
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 34;
		
		moveSpeed = 1.4;
		maxSpeed = 1.4;
		stopSpeed = 1.6;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;
		doubleJumpStart = -3;
		
		hammerDamage = 2;
		
		
		fistAttacks = new ArrayList<FistAttack>();
		thunders = new ArrayList<Thunder>();
		fistAttackDamage = 1;
		
		facingRight = true;
		
		lives = 5;
		health = maxHealth = 3;
		
		// load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
					"/Sprites/Player/Character.png"
				)
			);
			
			int count = 0;
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < NUMFRAMES.length; i++) {
				BufferedImage[] bi = new BufferedImage[NUMFRAMES[i]];
				for(int j = 0; j < NUMFRAMES[i]; j++) {
					bi[j] = spritesheet.getSubimage(
						j * FRAMEWIDTHS[i],
						count,
						FRAMEWIDTHS[i],
						FRAMEHEIGHTS[i]
					);
				}
				sprites.add(bi);
				count += FRAMEHEIGHTS[i];
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		setAnimation(IDLE);
		
	} // end of player
	
	public void init(){

	} // end of init
	
	public int getHealth() { return health; }
	public int getMaxHealth() { return maxHealth; }
	
	// Player actions
	
	public void setEmote(int i) {
		emote = i;
	}
	public void setTeleporting(boolean b) { teleporting = b; }
	public void setFlinching() { flinching = true;}
	
	public void setJumping(boolean b) {
		if(knockback) return;
		if(b && !jumping && falling && !alreadyDoubleJump) {
			doubleJump = true;
		}
		jumping = b;
	}
	public void setAttacking() {
		if(knockback) return;
		if(charging) return;
		//if(up && !attacking) hammerattacking = true;
		else attacking = true;
	}
	
	public void setHammerAttack() {
		if(knockback) return;
		if(charging) return;
		else hammerattacking = true;
	}
	public void setDodge(boolean b) {
		
		if(!b) dodge = false;
		else if(!attacking && !hammerattacking && !charging && !thunder) {
			dodge = true;
		}
	}
	
	public void setThunder() {
		if(knockback) return;
		if(jumping) return;
		if(falling) return;
		else thunder = true;
	}
	
	public void setDashing(boolean b) {
		if(!b) dashing = false;
		else if(b && !falling) {
			dashing = true;
		}
	}
	public boolean isDashing() { return dashing; }
	
	public void setDead() {
		setFlinching();
		health = 0;
		stop();
	}
	
	
	public String getTimeToString() {
		int minutes = (int) (time / 3600);
		int seconds = (int) ((time % 3600) / 60);
		return seconds < 10 ? minutes + ":0" + seconds : minutes + ":" + seconds;
	}
	public long getTime() { return time; }
	public void setTime(long t) { time = t; }
	public void setHealth(int i) { health = i; }
	public void setLives(int i) { lives = i; }
	public void setScore(int s) { score = s; }
	public void gainLife() { lives++; }
	public void loseLife() { lives--; }
	public int getLives() { return lives; }
	
	public void increaseScore(int score) {
		this.score += score; 
	}
	
	public int getScore() { return score; }
	
	public void hit(int damage) {
		if(flinching) return;
	//	JukeBox.play("playerhit");
		stop();
		health -= damage;
		if(health < 0) health = 0;
		flinching = true;
		flinchCount = 0;
		if(facingRight) dx = -1;
		else dx = 1;
		dy = -3;
		knockback = true;
		falling = true;
		jumping = false;
	}
	
	public void reset() {
		health = maxHealth;
		facingRight = true;
		currentAction = -1;
		stop();
	}
	
	public void stop() {
		left = right = up = down = flinching = 
			dashing = jumping = attacking = hammerattacking = dodge = thunder = false;
	}
	
	//calculates next position
	private void getNextPosition() {
		
		if(knockback) {
			dy += fallSpeed * 2.4;
			if(!falling) knockback = false;
			return;
		}
		
		if(dodge){
			dashing = attacking = jumping = doubleJump = thunder = false;

		}
		
		double maxSpeed = this.maxSpeed;
		if(dashing) { maxSpeed *= 2; }
		
		// movement
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		
		// cannot move while attacking, except in air
		if((attacking || dodge || thunder ) &&
			!(jumping || falling)) {
			dx = 0;
		}
		
		// jumping
		if(jumping && !falling) {
			dy = jumpStart;
			falling = true;

		}
		
		if(doubleJump) {
			dy = doubleJumpStart;
			alreadyDoubleJump = true;
			doubleJump = false;
		}
		
		if(!falling){ alreadyDoubleJump = false;}
		
		// falling
		if(falling) {
			dy += fallSpeed;
			
			// longer you hold jump button the higher you jump
			if(dy < 0 && !jumping) dy += stopJumpSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
		
	} // end of getNextPosition
	
	public void checkEnemyCollision ( ArrayList<Enemy> enemies) {
		
		// looping enemies
		for (int i = 0; i < enemies.size(); i++){

         Enemy e = enemies.get(i);
			
			// fistAttacks collision
			for(int j = 0; j < fistAttacks.size(); j++) {
				if(fistAttacks.get(j).intersects(e) && fistAttacks.get(j).getSpeed() !=0)  {
					if(e.getFlinching() == false){
					e.hit(fistAttackDamage);
					fistAttacks.get(j).setHit();
					}
					break;
				}
			}
			
			// hammerattacks
			if(currentAction == HAMMERATTACK &&
					(animation.getFrame() == 3 ) && animation.getCount() == 0) {
			
				//upside
				hammerU.x = (int)x - 25;
				hammerU.y = (int)y - 38;
				
				if(e.intersects(hammerU)) {
					e.hit(hammerDamage);
				}
				
			}
			if(currentAction == HAMMERATTACK &&
					(animation.getFrame() == 5 ) && animation.getCount() == 0) {
				
				//frontside
				hammerF.y = (int)y - 20;
				if(facingRight) hammerF.x = (int)x + 10;
				else hammerF.x = (int)x - 36;
				
				if(e.intersects(hammerF)) {
					e.hit(hammerDamage);
				}
				
			}
			
			//remember to turn on!!!
			////////////////////////////////////////
			////////// Enemy collision box /////////
			////////////////////////////////////////
			if(intersects(e) && e.isBossBody() == true){
				return;
			}
			else if(intersects(e)) {
				hit(e.getDamage());
			}

		} // end of looping enemies
	} // end of checkEnemyCollision
			
	//sets right animation for player
	private void setAnimation(int i) {
		currentAction = i;
		animation.setFrames(sprites.get(currentAction));
		animation.setDelay(SPRITEDELAYS[currentAction]);
		width = FRAMEWIDTHS[currentAction];
		height = FRAMEHEIGHTS[currentAction];
	}
	
	public void update() {
		
		time++;
		
		// update position
		boolean isFalling = falling;
		getNextPosition();
		checkTileMapCollision("blocked");
		setPosition(xtemp, ytemp);
		if(isFalling && !falling) {
		//	JukeBox.play("playerlands");
		}
		if(dx == 0) x = (int)x;
		
		// check done flinching
		if(flinching) {
			flinchCount++;
			if(flinchCount > 120) {
				flinching = false;
			}
		}
		
		// check attack finished
		if(currentAction == FISTATTACKING ||
			currentAction == HAMMERATTACK || currentAction == THUNDER) {
			if(animation.hasPlayedOnce()) {
				
				attacking = false;
				hammerattacking = false;
				thunder = false;
			}
		}
			
			// check attack
			if(currentAction == FISTATTACKING &&
					animation.getFrame() == 6 && animation.getCount() == 0) {
				
					FistAttack fa = new FistAttack(tileMap, facingRight);
				
				if (facingRight) {
					
				fa.setPosition(x + 7, y + 5);
				fistAttacks.add(fa);
				
				}
				else  {
					fa.setPosition(x - 7, y + 5);
					fistAttacks.add(fa);
				}
			}
			
			// check thunder attack
			if(currentAction == THUNDER &&
					animation.getFrame() == 6 && animation.getCount() == 0) {
				
					Thunder t = new Thunder(tileMap, facingRight);
				
				if (facingRight) {
					
				t.setPosition(x + 70, y - 50);
				thunders.add(t);
				
				}
				else  {
					t.setPosition(x - 70, y - 50);
					thunders.add(t);
				}

			}
			
			// check dodge
			if(currentAction == DODGE) {
				if(animation.getCount() == 0) {
				}
			}

		// set animation, ordered by priority
		if(teleporting) {
			if(currentAction != TELEPORTING) {
				setAnimation(TELEPORTING);
			}
		}
		else if(knockback) {
			if(currentAction != KNOCKBACK) {
				JukeBox.play("playerKNOCKBACKED");
				setAnimation(KNOCKBACK);
			}
		}
		else if(health == 0) {
			if(currentAction != DEAD) {
				JukeBox.play("playerDIED");
				setAnimation(DEAD);
			}
		}
		else if(hammerattacking) {
			if(currentAction != HAMMERATTACK) {
				JukeBox.play("hammerATTACK");
				setAnimation(HAMMERATTACK);
			}
		}
		
	
		
		else if(attacking) {
			if(currentAction != FISTATTACKING ) {
			//	JukeBox.play("playerattack");
				setAnimation(FISTATTACKING);
			}	
		}
		
		// check thunder
		else if(thunder){
			if(currentAction !=THUNDER) {
				setAnimation(THUNDER);
			}
		}
		
		else if(dodge) {
			if(currentAction != DODGE) {
				setAnimation(DODGE);
			}
		}
		else if(dy < 0) {
			if(currentAction != JUMPING) {
				JukeBox.play("playerJUMPED");
				setAnimation(JUMPING);
			}
		}
		else if(dy > 0) {
			if(currentAction != FALLING) {
				setAnimation(FALLING);
			}
		}
		else if(dashing && (left || right)) {
			if(currentAction != DASHING) {
				setAnimation(DASHING);
			}
		}
		else if(left || right) {
			if(currentAction != WALKING) {
				setAnimation(WALKING);
			}
		}
		else if(currentAction != IDLE) {
			setAnimation(IDLE);
		}
		
		//fistAttack update
				for(int i = 0; i < fistAttacks.size(); i++){
					fistAttacks.get(i).update();
					if(fistAttacks.get(i).removeObject()){
						fistAttacks.remove(i);
						i--;
					}
				}// end of fistAttack loop
				
		// update thunders
				for(int i= 0; i < thunders.size(); i++){
					thunders.get(i).update();
					if(thunders.get(i).removeObject()){
						thunders.remove(i);
						i--;
					}
				}
		
		animation.update();
		
		// set direction
		if(!attacking && !hammerattacking && !charging && !knockback && !thunder) {
			if(right) facingRight = true;
			if(left) facingRight = false;
		}

	}
	
	public boolean getRight(){
		return right;
	}
	public boolean getJumping(){
		return jumping;
	}
	public void draw(Graphics2D g) {
		
		// draw fistAttacks
				for(int i = 0; i < fistAttacks.size(); i++) {
					fistAttacks.get(i).draw(g);
				}
				
		// draw thunder 
				for(int i = 0; i< thunders.size(); i++){
					thunders.get(i).draw(g);
				}
		
		// draw emote
		if(emote == CONFUSED) {
			g.drawImage(confused, (int)(x + xmap - cwidth / 2), (int)(y + ymap - 40), null);
		}
		else if(emote == SURPRISED) {
			g.drawImage(surprised, (int)(x + xmap - cwidth / 2), (int)(y + ymap - 40), null);
		}
		
		// flinch
		if(flinching && !knockback) {
			if(flinchCount % 10 < 5) return;
		}
		
		super.draw(g);

	}
	
}