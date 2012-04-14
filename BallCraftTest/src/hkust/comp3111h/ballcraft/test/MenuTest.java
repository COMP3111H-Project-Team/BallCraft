package hkust.comp3111h.ballcraft.test;

import hkust.comp3111h.ballcraft.ui.GameMenu;
import android.test.ActivityInstrumentationTestCase2;

public class MenuTest extends ActivityInstrumentationTestCase2<GameMenu> {
	private GameMenu mGameMenu;
	public MenuTest() {
		super("hkust.comp3111h.ballcraft.ui",GameMenu.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mGameMenu = (GameMenu) getActivity();
	}
	
	public void testPrecondition(){
		assertNotNull(mGameMenu);	
	}
	
	public void testSettingIcon(){
		// TODO how to get setting icon?
	}

}
