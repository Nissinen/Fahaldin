package Projectiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Audio.JukeBox;
import Entity.Enemy;
import Entity.Player;
import Handlers.Content;
import TileMap.TileMap;

public class MummyEye extends Enemy {

	private boolean remove;
	private boolean hit;
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;
	private Player player;
	private int hpBarX;
	private int hpBarY;
	private int hpBarDistance;
	private BufferedImage hpBar;
	
	public MummyEye(TileMap tm, Player p) {
		super(tm);
		
		//basic monster values
		player = p;
		
		width = 30;
		height = 30;
		cwidth = 14;
		cheight = 14;
		dx = 0;
		dy = 0;
		facingRight = right;
		damage = 1;
		health = 1;
		
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
		
	//	maxSpeed = 3;
		moveSpeed = 3;
		

		
		sprites = Content.Eye[0];
		animation.setFrames(sprites);
		animation.setDelay(6);
		
		hitSprites = Content.FistExplosion[0];
		flinching = false;
		
	}
	
	//Checks where bullet is going
	private void getNextPosition() {

		if(hit){ dy = 0; }
		//decides eye direction
		if(!hit){
			
			if(player.getx() < x) dx += -0.15;
			else dx += 0.15;
			
			if(player.gety() < y) dy += -0.15;
			else dy += 0.15;
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

	//updates animation, location and checks collision
	public void update() {
	
	checkTileMapCollision("blocked");
	setPosition(xtemp, ytemp);
	
	getNextPosition();
	hpBarLocation();
	
	animation.update();
	if(hit && animation.hasPlayedOnce()) {
		remove = true;
		}
	}
	
	private void hpBarLocation() {
		hpBarX = (int) (x + xmap - width / 2);
		hpBarY = (int) (y + ymap - height / 2);
	}
	
	public void draw(Graphics2D g){
		setMapPosition();
		
		for(int i = 0; i < health; i++) {
			g.drawImage(hpBar,hpBarX + i * hpBarDistance,hpBarY - cheight / 2 - 5, null );
			}
		
		super.draw(g);
	}
}

