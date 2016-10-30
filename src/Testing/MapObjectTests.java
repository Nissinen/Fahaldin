package Testing;

import static org.junit.Assert.*;

import java.awt.Rectangle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Enemies.Ent;
import Entity.Enemy;
import Entity.Player;
import Entity.Portal;
import TileMap.TileMap;

public class MapObjectTests {

	private TileMap tm;
	private Enemy en;
	private Player player;
	
	@Before
	public void Setup(){
		System.out.println("Setup");
		tm = new TileMap(30);
		player = new Player(tm);
		en = new Enemy(tm);
	}
	
	@Test
	public void EnemyIsAlive() {
		assertFalse("Enemy is already dead!", en.hasDied());
	}
	
	@Test
	public void HitBoxIsRectangle(){
		assertEquals("Hit box is not a rectangle!",new Rectangle(), en.getRectangle());
	}
	
	@Test
	public void OffScreenEnemyIsPassive(){
		en.setPosition(4000, 200);
		assertTrue("Off Screen Enemy is already active!", en.notOnScreen());
	}
	
	@Test
	public void OnScreenEnemyIsActive(){
		en.setPosition(0, 0);
		assertFalse("On Screen is still passive!", en.notOnScreen());
	}
	@Test
	public void PlayerIntersectsEnemy(){
		en = new Ent(tm, player);
		assertTrue("Player doesn't intersect enemy!", player.intersects(en));
	}
	@Test
	public void PortalContainsPlayer(){		
		Portal portal = new Portal(tm, 0);
		assertTrue("Portal doesn't contain player!", portal.contains(player));
	}
	
	@Test
	public void PlayerIsAbleToJump(){
		player.setJumping(true);
		assertTrue("Player is not jumping!", player.getJumping());
	}
	
	@Test
	public void PlayerMaxHealthIs3(){
		assertEquals("Player max health is not 3!",3 , player.getMaxHealth());
	}
	
	@After
	public void TearDown(){
		System.out.println("TearDown");
	}
}
