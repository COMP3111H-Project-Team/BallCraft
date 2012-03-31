package hkust.comp3111h.ballcraft.test;

import hkust.comp3111h.ballcraft.client.Client;
import hkust.comp3111h.ballcraft.client.GameActivity;
import hkust.comp3111h.ballcraft.client.Skill;
import hkust.comp3111h.ballcraft.settings.GameSettings;
import android.test.ActivityInstrumentationTestCase2;


public class GameActivityTest extends ActivityInstrumentationTestCase2<GameActivity> {
	private GameActivity mGameActivity;
	public GameActivityTest() {
		super("hkust.comp3111h.ballcraft.client",GameActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mGameActivity = (GameActivity) getActivity();
		GameSettings.setContext(mGameActivity);
		Client.setContext(mGameActivity);
	}
	
	public void testMusicSetting(){
		GameSettings.setMusicPref(true);
		assertTrue(GameSettings.getMusicPref());
	}
	
	public void testVibrateSetting(){
		GameSettings.setVibrPref(false);
		assertFalse(GameSettings.getVibrPref());
	}
		
	public void testSkillCast(){
		Client.castSkill(Skill.getSkill(1,1));
		Client.castSkill(Skill.getSkill(1,2));
		//TODO how to detect it?
	}

	
}
