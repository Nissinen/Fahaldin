package Entity;

import java.awt.Graphics2D;  

import java.awt.image.BufferedImage;

import Audio.JukeBox;
import Entity.MapObject;
import Handlers.Content;
import TileMap.TileMap;

public class FistAttack extends MapObject {

	private boolean remove;
	private boolean hit;
	private double range;
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;
	
	public FistAttack(TileMap tm, boolean right) {
		
		super(tm);

		width = 22;
		height = 16;
		cwidth = 20;
		cheight = 16;
		dx = 1;
		facingRight = right;
		
		maxSpeed = 3.0;
		moveSpeed = 0.5;

		//load sprites
		
		sprites = Content.FlyingFist[0];
		animation.setFrames(sprites);
		animation.setDelay(5);
		
		hitSprites = Content.FistExplosion[0];
		
	}
	//checks next position
	private void getNextPosition() {
		if(!hit){
		if(facingRight == true){ dx = moveSpeed;}
		else{ dx = -moveSpeed;}
		}
		else dx = 0;
	}
	//checks if it hitted
	public void setHit(){

		if(hit){ return;}
		hit = true;
		animation.setFrames(hitSprites);
		animation.setDelay(5);
		JukeBox.play("explosion");
		
	}
	
	public boolean removeObject () {
		return remove;
	}
	//speeding the attack
	private void speedUp() {
		if (moveSpeed < maxSpeed){	this.moveSpeed = moveSpeed * 1.035; }
	}
	//checks if it has reached it max range
	private void setRange() {
		
		if(range > 200){ 
			setHit();		
			}
		range += moveSpeed;}
	
	
	
	protected double getSpeed() { return dx; }
	
	//updates attack
	public void update() {

		setRange();
		speedUp();
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
	//draws attack on map
	public void draw(Graphics2D g){
		
		setMapPosition();
		super.draw(g);
		
	}
}
