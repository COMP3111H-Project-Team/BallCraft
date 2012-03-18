package hkust.comp3111h.ballcraft.client;


import android.hardware.SensorEvent;
import android.hardware.SensorManager;

public class Client {

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
	
}