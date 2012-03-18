package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.server.Server;
import hkust.comp3111h.ballcraft.server.ServerAdapter;
import android.app.IntentService;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.util.Log;

public class Client extends IntentService {

	private static GameInput input;
	private static GameUpdater gameUpdater;
	
    public Client() {
    	super("ClientService");
    	input = new GameInput();
		gameUpdater = new GameUpdater();
    }
    
	public static void setInputAcceleration(float x, float y) {
		input.acceleration.x = x;
		input.acceleration.y = y;
	}
	
	public static void processSerializedUpdate(String serialized) {
		if (gameUpdater != null) {
			gameUpdater.fromSerializedString(serialized);
			ClientGameState.getClientGameState().applyUpdater(gameUpdater);
		}
	}
	
	public void run() 
	{
		while (true)
		{
			if (Server.inited) {
				ServerAdapter.sendToServer(input);
			}
			try 
			{
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		this.run();
	}
	
}