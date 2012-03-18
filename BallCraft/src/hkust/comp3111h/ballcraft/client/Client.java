package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.server.ServerAdapter;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

public class Client implements Runnable{

	private GameInput input;
	private ClientGameState gameState;
	
    public Client() {
    	input = new GameInput();
    	gameState = ClientGameState.getClientGameState();
    }
    
	public void setInputAcceleration(SensorEvent event) {
		input.acceleration.x = event.values[SensorManager.DATA_Y];
		input.acceleration.y = event.values[SensorManager.DATA_X];
	}

	@Override
	public void run() 
	{
		while (true)
		{
			ServerAdapter.sendToServer(input);
			try 
			{
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	
}