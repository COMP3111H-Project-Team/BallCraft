package hkust.comp3111h.ballcraft.test;

import hkust.comp3111h.ballcraft.client.Client;
import hkust.comp3111h.ballcraft.client.MultiPlayerGameInitializer;
import android.test.ActivityInstrumentationTestCase2;

public class MultiPlayerTest extends ActivityInstrumentationTestCase2<MultiPlayerGameInitializer>{
	MultiPlayerGameInitializer mMulti;
	public MultiPlayerTest() {
		super("hkust.comp3111h.ballcraft.client",MultiPlayerGameInitializer.class);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mMulti = getActivity();
		
	}
	
	public void testInitClient(){
		assertTrue(Client.isRun());
	}
	
	
		

}
