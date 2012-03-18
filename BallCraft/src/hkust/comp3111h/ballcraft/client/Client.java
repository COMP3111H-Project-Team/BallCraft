package hkust.comp3111h.ballcraft.client;

import android.app.IntentService;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

public class Client extends IntentService {

	private GameInput input;
	private ClientGameState gameState;
	
    public Client() {
    	super("ClientService");
    	input = new GameInput();
    	gameState = ClientGameState.getClientGameState();
    	
    	// new Thread(this).run();
    }
    
	public void setInputAcceleration(SensorEvent event) {
		input.acceleration.x = event.values[SensorManager.DATA_Y];
		input.acceleration.y = event.values[SensorManager.DATA_X];
	}

	public void run() 
	{
		while (true)
		{
			// if (Server.inited) ServerAdapter.sendToServer(input);
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