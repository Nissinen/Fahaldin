package Projectiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Audio.JukeBox;
import Entity.Enemy;
import Handlers.Content;
import TileMap.TileMap;

public class SnailSpit extends Enemy {

	private boolean remove;
	private boolean hit;
	private double xRange;
	private double yRange;
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;
	
	private double ammoToX;
	private double ammoToY;
	
	public SnailSpit(TileMap tm, double xFromSnail, double yFromSnail) {
		super(tm);
		
		//basic monster values
		
		yRange = this.y;
		xRange = this.x;
		
		ammoToX = xFromSnail;
		ammoToY = yFromSnail;
		
		width = 30;
		height = 30;
		cwidth = 14;
		cheight = 14;
		dx = 3;
		dy = 3;
		facingRight = right;
		damage = 1;
		
	//	maxSpeed = 3;
		moveSpeed = 3;
		
		sprites = Content.BatBullet[0];
		animation.setFrames(sprites);
		animation.setDelay(5);
		
		hitSprites = Content.FistExplosion[0];
		flinching = true;
		
	}
	
	//Checks where bullet is going
	private void getNextPosition() {

		if(hit){ dy = 0; }
		//decides spit direction
		if(!hit){
			if(0 < ammoToY ) {
				dy = -1;
			}
			if(ammoToY == 0 ){
				dy = 0;
			}
			if(ammoToX == 0){
				dy = -2;
				dx = 0;
			}
			else if(0 > ammoToX){
				dx = 3;
			}
			else if(0 < ammoToX){
				dx = -3;
			}	
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
		
		if(xRange > 300 || yRange > 300){ 
			setHit();
			}
		yRange += moveSpeed;
		xRange += moveSpeed;
		
	}

	//updates animation, location and checks collision
	public void update() {
	
	setRange();
	checkTileMapCollision("blocked");
	setPosition(xtemp, ytemp);
	
	//if( dx == 0 && !hit ) {
	//	setHit();
	//}
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

