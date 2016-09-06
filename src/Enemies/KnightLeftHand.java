package Enemies;

import java.awt.Graphics2D; 
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import TileMap.TileMap;
import Entity.Enemy;
import Entity.Player;
import Handlers.Content;
import Main.GamePanel;

public class KnightLeftHand extends Enemy {

	private TileMap tileMap;
	private ArrayList<Enemy> enemies;
	private BufferedImage[] sprites;
	private boolean active;
	private Player player;
	private int hpBarX;
	private int hpBarY;
	private int hpBarDistance;
	private BufferedImage hpBar;
	private int action;
	private int timer;
	
	public KnightLeftHand(TileMap tm, Player p, ArrayList<Enemy> en, TileMap tMap) {
		super(tm);
		health = maxHealth = 40;
		//loads health bar stuff
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
		//monster basic stuff
		tileMap = tMap;
		enemies = en;
		
		player = p;
		width = 64;
		height = 64;
		cwidth = 40;
		cheight = 40;
		enemyScore = 100;
		damage = 1;
		moveSpeed = 4;
		
		sprites = Content.KnightLeftHand[0];
		animation.setFrames(sprites);
		animation.setDelay(-1);
		
		}
	// gets next position
	private void getNextPosition() {
		calculateCorners(x, ydest + 1, "blocked");
		if(bottomLeft) {
			action++;
		}
		else if(bottomRight) {
			action++;
		}
	}
	private void setTimer(int delay){
		timer++;
		if(timer > delay){
			action++;
			timer = 0;
		}
	}
	private void generateEnemies(){
		Ent ent;
		ent = new Ent(tileMap , player );
		ent.setPosition (randomizeLocation(), 0);
		enemies.add(ent);
		ent = new Ent(tileMap , player );
		ent.setPosition (randomizeLocation(), 0);
		enemies.add(ent);
		Bat bat;
		bat = new Bat(tileMap , player, enemies );
		bat.setPosition (randomizeLocation(), 0);
		enemies.add(bat);
	}
	private int randomizeLocation(){
		// Usually this can be a field rather than a method variable
	    Random rand = new Random();
	    int Result = rand.nextInt((600 - 80) + 1) + 80;
		return Result;
	}
	private void moveDown(){
		this.y = this.y + moveSpeed;
	}
	private void moveUp(){
		if(this.y > 280){
			this.y = this.y - moveSpeed;
		}
		else{
			action++;
		}
	}
	private void setAction(){
		if(action == 0){
			setTimer(450); //450
		}
		if(action == 1){
			moveDown();
			getNextPosition();
		}
		if(action == 2){
			generateEnemies();
			action++;
		}
		if(action == 3){
			tileMap.setShaking(true, 4);
			setTimer(30);
		}
		if(action == 4){
			tileMap.setShaking(false, 0);
			moveUp();
		}
		if(action == 5){
			action = 0;
		}
		
	}
	//hp bar location on screen
	private void hpBarLocation() {
		hpBarX = (int) (x + xmap - width / 2);
		hpBarY = (int) (y + ymap - height / 2);
	}
	
	public int getEnemyScore() {
		return enemyScore;
	}
	
	//updates movements
	public void update() {
		setAction();
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
		
		checkTileMapCollision("blocked");
		setPosition(xtemp, ytemp);

		// update animation
		animation.update();
		
	}
	
	public void draw(Graphics2D g){
		if(flinching) {
			if(flinchCount == 0 || flinchCount == 2) return;
		}
		setMapPosition();
		for(int i = 0; i < health; i++) {
			g.drawImage(hpBar,hpBarX + i * hpBarDistance+ 15,hpBarY - cheight / 20 - 5, null );
			}
			
		super.draw(g);
	}
		
	}

