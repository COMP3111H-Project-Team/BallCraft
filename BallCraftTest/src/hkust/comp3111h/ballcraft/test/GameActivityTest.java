package hkust.comp3111h.ballcraft.test;

import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.client.Client;
import hkust.comp3111h.ballcraft.client.GameActivity;
import hkust.comp3111h.ballcraft.client.Map;
import hkust.comp3111h.ballcraft.client.MapParser;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;


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
	}
	
	public void testMap(){
		MapParser.setContext(mGameActivity);
		Map map = MapParser.getMapFromXML("map01.xml");
		assertEquals("map01",map.getName());
		 
	}
	public void testLayout(){
		 final Button skill1Button = (Button) mGameActivity
	                .findViewById(R.id.game_activity_skill_1_button);
		 mGameActivity.runOnUiThread(
		            new Runnable() {
		                public void run() {
		                    skill1Button.requestFocus();
		                }
		            }
		        );
		 Log.i("testshabi",skill1Button.getText().toString());
		 assertEquals("Grow Root",skill1Button.getText().toString());
	}

	
}
