package Projectiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Audio.JukeBox;
import TileMap.TileMap;
import Entity.Enemy;
import Handlers.Content;

public class BatBullet extends Enemy {
	
	private boolean remove;
	private boolean hit;
	private double xRange;
	private double yRange;
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;
	
	public BatBullet (TileMap tm, boolean right){
		super (tm);
		
		//basic monster values
		
		yRange = this.y;
		width = 30;
		height = 30;
		cwidth = 14;
		cheight = 14;
		dx = 1;
		dy = -0.7;
		facingRight = right;
		damage = 1;
		
		maxSpeed = 3;
		moveSpeed = 2;
		
		sprites = Content.BatBullet[0];
		animation.setFrames(sprites);
		animation.setDelay(5);
		
		hitSprites = Content.FistExplosion[0];
		flinching = true;
		
	}
	
	//Checks where bullet is going
	private void getNextPosition() {
		
		if(hit){ dy = 0; }
		//makes bullet to do waves
		if(!hit){
			if(yRange > 0 ){
				dy += 0.055;
			}
			if(yRange < 0 ) {
				dy -= 0.055;
			}
				
			if(facingRight == true){ dx = moveSpeed; }
			else { dx = -moveSpeed; }
		}
		else 
			{dx = 0;}
	}
	
	// sets hit true and gives animation delay and graphic
	public void setHit(){
		JukeBox.play("explosion");
		if(hit) { return; }
		hit = true;
		animation.setFrames(hitSprites);
		animation.setDelay(3);
	}
	
	public boolean removeObject () {
		return remove;
	}
	
	private void setRange() {
		
		if(xRange > 300){ 
			setHit();
			}
		xRange += moveSpeed;
		
		yRange -= dy; 
	}

	//updates animation, location and checks collision
	public void update() {
	
	setRange();
	checkTileMapCollision("blocked");
	setPosition(xtemp, ytemp);
	
	if( dx == 0 && !hit ) {
		setHit();
	}
	getNextPosition();
	animation.update();
	if(hit && animation.hasPlayedOnce()) {
		remove = true;
		}
	}

	public void draw(Graphics2D g){
		setMapPosition();
		super.draw(g);
	}
}
