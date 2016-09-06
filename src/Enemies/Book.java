package Enemies;

import java.awt.Graphics2D; 
import java.awt.image.BufferedImage;


import TileMap.TileMap;
import Entity.Enemy;
import Entity.Player;
import Handlers.Content;
import Main.GamePanel;

public class Book extends Enemy {

	private BufferedImage[] sprites;
	private boolean active;
	private Player player;
	
	public Book(TileMap tm, Player p) {
		super(tm);
		health = maxHealth = 6;

		//monster basic stuff
		
		player = p;
		width = 32;
		height = 32;
		cwidth = 32;
		cheight = 32;
		enemyScore = 100;
		damage = 1;
		isBoss = true;
		sprites = Content.Book[0];
		animation.setFrames(sprites);
		animation.setDelay(12);
		
		}
	// gets next position
	private void getNextPosition() {
		
	/*	if(player.getx() == x){ dx = 0; facingRight = true;}
		else if(player.getx() < x){ dx = -0.5; facingRight = true;}
		else{ dx = 0.5; facingRight = false;}
			
		if(player.gety() < y){ dy = -0.5; }
		else dy = 0.5;*/

	}
	
	public int getEnemyScore() {
		return enemyScore;
	}
	
	//updates movements
	public void update() {

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
		getNextPosition();

		// update animation
		animation.update();
		
	}
	
	public void draw(Graphics2D g){
		if(flinching) {
			if(flinchCount == 0 || flinchCount == 2) return;
		}
		setMapPosition();
			
		super.draw(g);
	}
		
	}

