package Entity;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;






import Handlers.Content;
import TileMap.TileMap;


public class Portal extends MapObject {
	
	private BufferedImage[] sprites;
	private double portalX;
	private double portalY;
	private int animatedOrNot;
	//gets portal graphics
	// int 1 to moveportal location 0 to keep it stable
	public Portal(TileMap tm, int animated) {
		super(tm);
		width = 90;
		height = 120;
		cwidth = 45;
		cheight = 60;
		animatedOrNot = animated;
		
		sprites = Content.Portal[0];
		animation.setFrames(sprites);
		animation.setDelay(6);
		
		facingRight = true;
	}
	
	public void PortalLocation(double x, double y){
		portalX = x;
		portalY = y;		
	}
	
	private void checkPortalLocation(){
		if(portalX != x || portalY != y){
			if(portalX > x){
				x += 4;
			}
			else if(portalX < x){
				x -= 4;
			}
			if(portalY > y){
				y += 4;
			}
			else if(portalY < y){
				y -= 4;
			}
		}
	}
	
	public void update() {
		if(animatedOrNot > 0){
		checkPortalLocation();
		}
		animation.update();
	}
	
	public void draw(Graphics2D g){
		super.draw(g);
	}

}
