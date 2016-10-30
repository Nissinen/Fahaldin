package Testing;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import TileMap.TileMap;

public class TileMapTests {

	private TileMap tm;
	@Before
	public void Setup(){
		System.out.println("Setup");
		tm = new TileMap(30);
	}
	@Test
	public void TileSetTileSize() {
		assertEquals("Tile size is not 30!", 30, tm.getTileSize());
	}
	
	@After
	public void TearDown(){
		System.out.println("TearDown");
		tm = null;
	}

}
