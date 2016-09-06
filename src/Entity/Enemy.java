package Entity;

import TileMap.TileMap;

public class Enemy extends MapObject {

	protected int health;
	protected int maxHealth;
	protected int damage;
	protected int enemyScore;
	protected boolean isBoss = false;
	
	protected boolean flinching;
	protected long flinchCount;
	
	protected boolean remove;
	protected boolean dead;
	
	public Enemy(TileMap tm) {
		super(tm);
		remove = false;

	}
	
	public boolean hasDied() { return dead; }
	public boolean isBossBody() { return isBoss; }
	public boolean removeObject() { return remove; }
	public int getDamage() {return damage;}
	public boolean getFlinching() {	return flinching; }
	public int getEnemyScore() { return enemyScore; }
	public int getHealth() {
		return health;
	}
	//chceks if enemy has hp left or should it die
	public void hit(int damage) {
		
		if(dead || flinching){ return; }
		//JukeBox.play("enemyhit");
		health -= damage;
		if(health < 0){ health = 0;}
		if(health == 0) { dead = true;}
		if(dead){ remove = true;}
		flinching = true;
		flinchCount = 0;
	}
	

	public void update() {
		
	}

}
