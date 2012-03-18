package hkust.comp3111h.ballcraft.server;

import hkust.comp3111h.ballcraft.client.GameInput;
import hkust.comp3111h.ballcraft.client.GameUpdater;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class Server extends IntentService
{
	static private ServerGameState gameState = null;
	
	static private GameUpdater gameUpdater;
	
	static private long lastRun;
	
	static int skill = 0;
	
	static public boolean inited = false;
	
	public Server()
	{
		super("ServerService");
		gameState = ServerGameState.getStateInstance();
		gameState.createTestGameState();
		
		gameUpdater = new GameUpdater();
		
		inited = true;	
		lastRun = System.currentTimeMillis();
	}
	
	public static void process(String string)
	{
		GameInput input = GameInput.deserializeGameInput(string); // get and parse data from server adapter
		ServerGameState.getStateInstance().processPlayerInput(0, input); // process
		ServerAdapter.processServerMsg(generateGameUpdater().toSerializedString()); // send back to server adapter
	}
	
	public static GameUpdater generateGameUpdater() {
		gameUpdater.units = gameState.getUnits();
		return gameUpdater;
	}
	
	public void run() 
	{
		while (true)
		{
			long time = System.currentTimeMillis();
			gameState.onEveryFrame((int)(time - lastRun));
			
			try
			{
				long sleep = 30 + time - System.currentTimeMillis();
				lastRun = System.currentTimeMillis();
				if (sleep < 0 ) sleep = 0;
				Thread.sleep(sleep);
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
	}

	@Override
	protected void onHandleIntent(Intent arg0) 
	{
		run();
	}
	
}
