package Testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GamePanelTests.class, KeysTests.class, MapObjectTests.class, TileMapTests.class })
public class AllTests {

}
