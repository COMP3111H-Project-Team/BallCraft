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
	
	static private GameInput gameInput;
	
	static private long lastRun;
	
	static int skill = 0;
	
	static public boolean inited = false;
	
	static public String msg;
	
	public Server()
	{
		super("Server");
	}
	
	public static void setState(String string)
	{
		gameInput = GameInput.fromSerializedString(string); // get and parse data from server adapter
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
			lastRun = System.currentTimeMillis();
			
			ServerGameState.getStateInstance().processPlayerInput(0, gameInput); // process
			ServerAdapter.processServerMsg(msg + ";" + generateGameUpdater().toSerializedString()); // send back to server adapter
			msg = "";
			try {
				/*
				long sleep = 30 + time - System.currentTimeMillis();
				if (sleep > 0 ) {
					Thread.sleep(sleep);
				}
				*/
				Thread.sleep(20);
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		gameState = ServerGameState.getStateInstance();
		gameState.loadMap(intent.getStringExtra("map"));
		
		gameUpdater = new GameUpdater();
		
		gameInput = new GameInput();
		
		inited = true;
		lastRun = System.currentTimeMillis();
		msg = "";
		run();
	}
	
}
