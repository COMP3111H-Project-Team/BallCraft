package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.server.Server;
import hkust.comp3111h.ballcraft.server.ServerAdapter;
import android.app.IntentService;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

public class Client extends IntentService {

	private static GameInput input;
	private static GameUpdater gameUpdater;
	
    public Client() {
    	super("ClientService");
    	input = new GameInput();
		gameUpdater = new GameUpdater();
    }
    
	public static void setInputAcceleration(SensorEvent event) {
		input.acceleration.x = event.values[SensorManager.DATA_Y];
		input.acceleration.y = event.values[SensorManager.DATA_X];
	}
	
	public static void processSerializedUpdate(String serialized) {
		gameUpdater.fromSerializedString(serialized);
		ClientGameState.getClientGameState().applyUpdater(gameUpdater);
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