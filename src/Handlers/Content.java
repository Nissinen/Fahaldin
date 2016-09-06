package Handlers;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

// this class loads resources on boot.
// spritesheets are taken from here.

public class Content {

	//loads in sprite sheets and sets cut size
	
	public static BufferedImage[][] Hand = load ("/HUD/HUD.png", 15, 14);
	public static BufferedImage[][] Eye = load ("/Sprites/Enemies/mummyEye.png", 15, 15);
	public static BufferedImage[][] Ghost = load ("/Sprites/Enemies/Ghost.png", 64, 64);
	public static BufferedImage[][] Spikey = load("/Sprites/Enemies/Spikey.png", 30, 34);
	public static BufferedImage[][] Ent = load("/Sprites/Enemies/Ent.png", 27, 24);
	public static BufferedImage[][] Bat = load("/Sprites/Enemies/Bat.png", 35, 25);
	public static BufferedImage[][] BatBullet = load("/Sprites/Enemies/BatBullet.png", 30, 30);
	public static BufferedImage[][] FlyingFist = load("/Sprites/Player/Fist.png", 22, 16);
	public static BufferedImage[][] FistExplosion = load("/Sprites/Player/FistExplosion.png", 22, 16);
	public static BufferedImage[][] Portal = load("/Sprites/Other/portal.png", 90, 90);
	public static BufferedImage[][] Thunder = load("/Sprites/Player/lightningbolt.png", 40, 150);
	public static BufferedImage[][] Snail = load("/Sprites/Enemies/Snail.png", 80, 52);
	public static BufferedImage[][] Mummy = load("/Sprites/Enemies/Mummy.png",50,50);
	public static BufferedImage[][] Book = load("/Sprites/Enemies/book.png",32,32);
	public static BufferedImage[][] Knight = load("/Sprites/Enemies/Knight.png",240,240);
	public static BufferedImage[][] KnightLeftHand = load("/Sprites/Enemies/KnightLeftHand.png",50,50);
	public static BufferedImage[][] KnightRightHand = load("/Sprites/Enemies/KnightRightHand.png",50,50);
	
	//cuts sprite sheets to pieces
	public static BufferedImage[][] load(String s, int w, int h) {
		BufferedImage[][] ret;
		try {
			BufferedImage spritesheet = ImageIO.read(Content.class.getResourceAsStream(s));
			int width = spritesheet.getWidth() / w;
			int height = spritesheet.getHeight() / h;
			ret = new BufferedImage[height][width];
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					ret[i][j] = spritesheet.getSubimage(j * w, i * h, w, h);
				}
			}
			return ret;
		}
		//sends read error
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error loading graphics.");
			System.exit(0);
		}
		return null;
	}
	
}
