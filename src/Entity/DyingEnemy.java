package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Audio.JukeBox;
import Handlers.Content;
import TileMap.TileMap;

public class DyingEnemy extends MapObject {

	private BufferedImage[] sprites;
	
	protected boolean remove;
	
	public DyingEnemy(TileMap tm, int x, int y) {
		super(tm);
		
		this.x = x;
		this.y = y;
		
		sprites = Content.FistExplosion[0];
		animation.setFrames(sprites);
		animation.setDelay(3);
		JukeBox.play("explosion");

	}
	// updates till animation played once
	public void update(){
		animation.update();
		if(animation.hasPlayedOnce()){
			remove = true;
		}
	}
	//deletes explosion
	public boolean removeObject(){ 
		return remove;}
	
	// counts real location and draws it there
	public void draw(Graphics2D g) {
		setMapPosition();
		g.drawImage(
			animation.getImage(),
			(int)(x + xmap - width / 2),
			(int)(y + ymap - height / 2),
			null
		);
	}

}
