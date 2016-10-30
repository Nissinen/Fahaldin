package Testing;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Main.GamePanel;

public class GamePanelTests {
	
	private GamePanel gp;
	
	@Before
	public void Setup(){
		System.out.println("Setup");
		gp = new GamePanel(); 
	}

	@Test
	public void WindowIsFocusable(){
		assertTrue("Windoe is not focusable!", gp.isFocusable());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void ScaleIs2() {
		assertEquals("Scale is not 2!", 2 , gp.SCALE);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void ScreenWidthIs600WithoutScale(){
		assertEquals("Screen width is not 600 before scaling", 600, gp.WIDTH);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void ScreenHeightIs400WithoutScale(){
		assertEquals("Screen height is not 400 before scaling", 400, gp.HEIGHT);
	}
	
	@After
	public void TearDown(){
		System.out.println("Teardown");
		gp = null;
	}
}
