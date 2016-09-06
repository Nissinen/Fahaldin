package GameState;

import java.awt.Graphics2D;

import java.util.ArrayList;

import Audio.JukeBox;
import Enemies.Book;
import Enemies.Ent;
import Entity.DyingEnemy;
import Entity.Enemy;
import Entity.HUD;
import Entity.Player;
import Entity.PlayerSave;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

public class StoryEnding extends GameState {

	private Background bg;
	private Background story;
	private Background torches;
	
	private HUD hud;
	
	private ArrayList<DyingEnemy> deadEnemies;
	private ArrayList<Enemy> enemies;
	private Player player;
	private TileMap tileMap;
	
	// event stuff
	private boolean blockInput = false;
	private int eventCount = 0;
	private boolean eventDead;
	private boolean eventStart;
	private int counter;
	private int thunderCounter;
	
	public StoryEnding(GameStateManager gsm) {
		super(gsm);
		init();
	}
	
	public void init() {
		
	// backgrounds
			bg = new Background("/Backgrounds/CastleBG.png", 0.2, 0);
			torches = new Background("/Backgrounds/pillar.png", 0.4, 0);

			story = new Background("/Backgrounds/ending.png", 0);

			// move backgrounds
			story.setVector(0, -0.5);
			
			JukeBox.stop("lastboss");
			JukeBox.load("/SFX/Explosion.mp3", "explosion");
	// tilemap
			tileMap = new TileMap(30);
			tileMap.loadTiles("/Tilesets/castleTileSet.png");
			tileMap.loadMap("/Maps/ending.map");
			tileMap.setPosition(140, 0);
			
			tileMap.setTween(1);

			// player
			player = new Player(tileMap);
			player.setPosition(20, 300);
			player.setHealth(PlayerSave.getHealth());
			player.setLives(PlayerSave.getLives());
			player.setScore(PlayerSave.getScore());
			player.setTime(PlayerSave.getTime());

			// dyingEnemies
			deadEnemies = new ArrayList<DyingEnemy>();
			
			//enemies
			enemies = new ArrayList<Enemy>();
			setEnemies();
			
			//set HUD
			hud = new HUD(player);
			
			// update tilemap
			tileMap.setPosition(
				GamePanel.WIDTH / 2 - player.getx(),
				GamePanel.HEIGHT / 2 - player.gety()
			);
			tileMap.update();
			tileMap.fixBounds();
			counter = 0;
			// music
			JukeBox.stop("level2");
			JukeBox.load("/Music/CastleBGMusic.mp3", "level3");
			JukeBox.loop("level3", 600, JukeBox.getFrames("level3") - 2200);
	}
	
	private void setEnemies() {
		enemies.clear();
		Ent ent;
		ent = new Ent(tileMap , player );
		ent.setPosition (400, 300);
		enemies.add(ent);
		Book book;
		book = new Book(tileMap,player);
		book.setPosition(500, 330);
		enemies.add(book);
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
				story.update();
				// check if player dead event should start
				playerHasDied();
				
				// play events
				playEvent();

				// move backgrounds
				
				torches.setPosition(tileMap.getx(), tileMap.gety());
				bg.setPosition(tileMap.getx(), tileMap.gety());
				
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
				if(story.storyHasScrolled()){
					gsm.setState(GameStateManager.WORLDMAP);
				}

	}


	public void draw(Graphics2D g) {
		
		// draw background
				bg.draw(g);
				torches.draw(g);
				
				// draw tilemap
				tileMap.draw(g);
				
				// draw enemies
				for(int i = 0; i < enemies.size(); i++){
					enemies.get(i).draw(g);
				}
				
				//draw HUD
				hud.draw(g);

				// draw player
				player.draw(g);
				
				//draw dyingEnemies
				
				for(int i = 0; i < deadEnemies.size(); i++){
					deadEnemies.get(i).draw(g);
				}
				story.draw(g);
		
	}

	public void handleInput() {
		//if(Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(true);
		if(blockInput || player.getHealth() == 0) return;
		else if(player.getx() + 80 > 300 && enemies.size() > 1){
			player.setHammerAttack();
		}
		else if(player.getx() < tileMap.getWidth() - 280){
			player.setRight(true);
		}
		else if(player.getRight() == false && counter < 60){
			player.setJumping(true);
			counter++;
		}
		else if(counter > 60){
			player.setJumping(false);
		}
		else if(player.getRight() == false && player.getJumping() == false && enemies.size() > 0){
			player.setHammerAttack();
		}
		else if(thunderCounter > 100 && thunderCounter < 102){
			thunderCounter++;
			player.setThunder();
		}
		else if(player.getRight() == false && player.getJumping() == false && enemies.size() == 0){
			thunderCounter++;
		}
		else if(player.getx() > tileMap.getWidth() - 280 ){
			player.setRight(false);
			player.setJumping(false);
		}
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
			player.setPosition(20, 300);
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


