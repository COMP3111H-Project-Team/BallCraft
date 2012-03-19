package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.server.Server;
import hkust.comp3111h.ballcraft.server.ServerAdapter;
import android.app.IntentService;
import android.content.Intent;

public class Client extends IntentService {

	private static GameInput input;
	
    public Client() {
    	super("ClientService");
    	input = new GameInput();
    }
    
	public static void setInputAcceleration(float x, float y) {
		input.acceleration.x = x;
		input.acceleration.y = y;
	}
	
	public static void processSerializedUpdate(String serialized) {
		ClientGameState.getClientGameState().applyUpdater(serialized);
	}
	
	public void run() 
	{
		while (true)
		{
			if (Server.inited) {
				ServerAdapter.sendToServer(input);
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {}
		}
		
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		this.run();
	}
	
}