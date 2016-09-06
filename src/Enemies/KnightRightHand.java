package Enemies;

import java.awt.Graphics2D; 
import java.awt.image.BufferedImage;





import javax.imageio.ImageIO;

import TileMap.TileMap;
import Entity.Enemy;
import Entity.Player;
import Handlers.Content;
import Main.GamePanel;

public class KnightRightHand extends Enemy {

	private BufferedImage[] sprites;
	private boolean active;
	private Player player;
	private int hpBarX;
	private int hpBarY;
	private int action;
	private int hpBarDistance;
	private BufferedImage hpBar;
	private int timer;
	private double attackSpeed;
	private int playerLocationX;
	private int playerLocationY;
	private boolean xReady;
	private boolean yReady;
	
	public KnightRightHand(TileMap tm, Player p) {
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
		
		player = p;
		width = 64;
		height = 64;
		cwidth = 40;
		cheight = 40;
		enemyScore = 100;
		moveSpeed = 2;
		attackSpeed = 6;
		damage = 1;
		action = 0;
		xReady = false;
		yReady = false;
		
		sprites = Content.KnightRightHand[0];
		animation.setFrames(sprites);
		animation.setDelay(-1);
		
		}
	// gets next position
	private void getNextPosition(int xLocation, int yLocation) {
		if(this.x + 6 < xLocation){
			this.x = this.x + attackSpeed;
		}
		else if(this.x - 6 > xLocation){
			this.x = this.x - attackSpeed;
		}
		else{
			xReady = true;
		}
		if(this.y + 6 < yLocation){
			this.y = this.y + attackSpeed;
		}
		else if(this.y - 6 > yLocation){
			this.y = this.y - attackSpeed;
		}
		else{
			yReady = true;
		}
	}
	private void setTimer(int delay){
		timer++;
		if(timer > delay){
			action++;
			timer = 0;
		}
	}
	private void setAction() {
		//monster state machine
		//wait
		if(action == 0) {
			setTimer(180);
		}
		//warn by shaking
		if(action == 1) {
			startShaking();
			setTimer(180);
		}
		//get player location
		if(action == 2) {
			playerLocationX = player.getx();
			playerLocationY = player.gety();
			action++;
		}
		//attack
		if(action == 3) {
			if(xReady == true && yReady == true){
				action++;
			}
			getNextPosition(playerLocationX, playerLocationY);

		}
		if(action == 4){
			setTimer(180);
			getNextPosition(440, 280);
			xReady = false;
			yReady = false;
		}
		if(action == 5){
			action = 0;
		}
	}
	
	private void startShaking(){
		this.x += Math.random() * moveSpeed - moveSpeed / 2;
		this.y += Math.random() * moveSpeed - moveSpeed / 2;
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
		
		checkTileMapCollision("normal");
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

