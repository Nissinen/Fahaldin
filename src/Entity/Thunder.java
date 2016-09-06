package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Handlers.Content;
import TileMap.TileMap;

public class Thunder extends MapObject {

	private boolean remove;
	private boolean hit;
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;
	
	
	//gets thunder graphics
	public Thunder(TileMap tm, boolean right) {
		super(tm);
		
		width = 40;
		height = 150;
		cwidth = 20;
		cheight = 110;
		facingRight = true;
		
		sprites = Content.Thunder[0];
		animation.setFrames(sprites);
		animation.setDelay(4);
	
	}
	
	public boolean removeObject () {
		return remove;
	}
	
	//updates animation
	public void update() {
		
		animation.update();
		checkTileMapCollision("blocked");
		setPosition(xtemp, ytemp);
		if(animation.hasPlayedOnce()) {
			remove = true;
		}
	}
	
	public void draw(Graphics2D g){
		setMapPosition();
		super.draw(g);
	}

}
