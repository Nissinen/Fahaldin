package Testing;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Handlers.Keys;

public class KeysTests {

	Keys keys;
	
	@Before
	public void Setup(){
		System.out.println("Setup");
		keys = new Keys();
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void UpArrowIsBindedTo0() {
		assertEquals("Up arrow is not binded to 0",0, keys.UP);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void LeftArrowIsBindedTo1(){
		assertEquals("Left arrow is not binded to 1",1, keys.LEFT);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void DownArrowIsBindedTo2(){
		assertEquals("Down arrow is not binded to 2",2, keys.DOWN);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void RightArrowIsBindedTo3(){
		assertEquals("Right arrow is not binded to 3",3, keys.RIGHT);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void DashingIsBindedToZ(){
		assertEquals("Z is not binded to 4",4, keys.BUTTONZ);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void JumpingIsBindedToX(){
		assertEquals("X is not binded to 5",5, keys.BUTTONX);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void RangedAttackIsBindedToC(){
		assertEquals("C is not binded to 6",6, keys.BUTTONC);
	}
	@SuppressWarnings("static-access")
	@Test
	public void AttackIsBindedToV(){
		assertEquals("V is not binded to 7",7, keys.BUTTONV);
	}
	
	@After
	public void TearDown(){
		System.out.println("TearDown");
		keys = null;
	}

}
