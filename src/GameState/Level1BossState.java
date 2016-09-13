package GameState;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Audio.JukeBox;
import Enemies.SnailBoss;
import Entity.DyingEnemy;
import Entity.Enemy;
import Entity.HUD;
import Entity.Player;
import Entity.PlayerSave;
import Entity.Portal;
import Handlers.Keys;
import Handlers.ProgressHandler;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

public class Level1BossState extends GameState {

	private Background sky;
	private Background clouds;
	private Background waves;
	
	private HUD hud;
	
	private Portal portal;
	
	private ArrayList<DyingEnemy> deadEnemies;
	private ArrayList<Enemy> enemies;
	private Player player;
	private TileMap tileMap;
	
	// event stuff
	private boolean blockInput = false;
	private int eventCount = 0;
	private boolean eventDead;
	private boolean eventStart;
	
	public Level1BossState(GameStateManager gsm) {
		super(gsm);
		init();
	}
	
	public void init() {
		
		// backgrounds
				sky = new Background("/Backgrounds/SeaSky.png", 0, 0);
				waves = new Background("/Backgrounds/Waves.png", 0);
				clouds = new Background("/Backgrounds/CloudsReady.png", 0.1);

				JukeBox.load("/SFX/Explosion.mp3", "explosion");
		// tilemap
				tileMap = new TileMap(30);
				tileMap.loadTiles("/Tilesets/GrassTileSet.png");
				tileMap.loadMap("/Maps/level1-boss.map");
				tileMap.setPosition(140, 0);

				tileMap.setTween(1);
				
				// player
				player = new Player(tileMap);
				player.setPosition(170, 300);
				player.setHealth(PlayerSave.getHealth());
				player.setLives(PlayerSave.getLives());
				player.setScore(PlayerSave.getScore());
				player.setTime(PlayerSave.getTime());
		
				
				// move backgrounds
				clouds.setVector(0.085, 0);
				waves.setVector(0.2, 0);

				// dyingEnemies
				deadEnemies = new ArrayList<DyingEnemy>();
				
				
				//enemies
				enemies = new ArrayList<Enemy>();
				setEnemies();
				
				//portal
				portal = new Portal(tileMap, 1);
				portal.setPosition(270, 34);
				portal.PortalLocation(270, 334);
				
				//set HUD
				hud = new HUD(player);
				
				// update tilemap
				tileMap.setPosition(
					GamePanel.WIDTH / 2 - player.getx(),
					GamePanel.HEIGHT / 2 - player.gety()
				);
				tileMap.update();
				tileMap.fixBounds();
		
	}
	
	private void setEnemies() {
		enemies.clear();
		
		SnailBoss boss;
		boss = new SnailBoss(tileMap, player, enemies);
		boss.setPosition(420, 300);
		enemies.add(boss);
	}

	private void updateEnemies() {
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.hasDied() || e.removeObject()) {
				
				if(e.hasDied()){
					player.increaseScore(e.getEnemyScore());
				deadEnemies.add(new DyingEnemy(tileMap, e.getx(), e.gety()));
				}
				enemies.remove(i);
				i--;
			}
		}
		
		// update deadEnemies
		for(int i = 0; i < deadEnemies.size(); i++){
			deadEnemies.get(i).update();
			if(deadEnemies.get(i).removeObject()){
				deadEnemies.remove(i);
				i--;
			}
		}
	}

	public void update() {
		// check keys
				handleInput();
				
				// check if player dead event should start
				playerHasDied();
				
				// play events
				playEvent();

				// move backgrounds
				
				clouds.update();
				waves.update();
				
				
				//check enemyCollision
				player.checkEnemyCollision(enemies);
				
		
				// update player
				player.update();
				
				// update tilemap
				tileMap.setPosition(
					GamePanel.WIDTH / 2 - player.getx(),
					GamePanel.HEIGHT / 2 - player.gety()
				);
				tileMap.update();
				tileMap.fixBounds();
				
				// update enemies and deadEnemies
				updateEnemies();
				
				if(enemies.size() <= 0){
					portal.update();
				}
				
				if(portal.contains(player) && Keys.isPressed(0)){
					PlayerSave.setHealth(player.getHealth());
					PlayerSave.setLives(player.getLives());
					PlayerSave.setTime(player.getTime());
					PlayerSave.setScore(player.getScore());
					ProgressHandler.setLock(2);
					gsm.setState(GameStateManager.WORLDMAP);

				}
	}


	public void draw(Graphics2D g) {
		
		// draw background
				sky.draw(g);
				waves.draw(g);
				clouds.draw(g);
				
				// draw tilemap
				tileMap.draw(g);
				
				// draw enemies
				for(int i = 0; i < enemies.size(); i++){
					enemies.get(i).draw(g);
				}
				
				//draw HUD
				hud.draw(g);
				
				//draw Portal
				if(enemies.size() <= 0){
					portal.draw(g);
				}
			
				// draw player
				player.draw(g);
				
				//draw dyingEnemies
				
				for(int i = 0; i < deadEnemies.size(); i++){
					deadEnemies.get(i).draw(g);
				}
		
	}

	public void handleInput() {
		//if(Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(true);
		if(blockInput || player.getHealth() == 0) return;
		player.setUp(Keys.keyState[Keys.UP]);
		player.setLeft(Keys.keyState[Keys.LEFT]);
		player.setDodge(Keys.keyState[Keys.DOWN]);
		player.setRight(Keys.keyState[Keys.RIGHT]);
		player.setJumping(Keys.keyState[Keys.BUTTONX]);
		player.setDashing(Keys.keyState[Keys.BUTTONZ]);
		
		if(Keys.isPressed(Keys.BUTTONC)) player.setAttacking();
		if(Keys.isPressed(Keys.BUTTONV)) player.setHammerAttack();
	}

	
	///////////////////////////////////////////////
	///////				Events				///////
	///////////////////////////////////////////////
	
	private void playerHasDied() {
		if(player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
			eventDead = blockInput = true;
		}
	}
	
	// play events
		private void playEvent() {
			
			if(eventStart) eventStart();
			if(eventDead) eventDead();
	
		}
	
	// start event
		private void eventStart() {
			eventCount++;
			if(eventCount == 1) {
				eventStart = blockInput = false;
				}
			if (eventCount == 30){
				eventCount  = 0;
			}
			}
			
		
	// reset level
		private void reset() {
			player.reset();
			player.setPosition(300, 161);
			setEnemies();
			blockInput = true;
			eventCount = 0;
			tileMap.setShaking(false, 0);
			eventStart = true;
			eventStart();
			

		}
	
	private void eventDead() {
		eventCount++;
		if(eventCount == 1) {
			player.setDead();
			player.stop();
		}

		if(eventCount >= 120) {
			if(player.getLives() == 0) {
				gsm.setState(GameStateManager.MENUSTATE);
			}
			else {
				eventDead = blockInput = false;
				eventCount = 0;
				player.loseLife();
				reset();
				}
	
			}
		}
	}


