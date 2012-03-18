package hkust.comp3111h.ballcraft.server;

import hkust.comp3111h.ballcraft.client.GameInput;
import android.app.IntentService;
import android.content.Intent;

public class Server extends IntentService
{
	static private ServerGameState gameState = null;
	static private long lastRun;
	
	static int skill = 0;
	
	static public boolean inited = false;
	
	public Server()
	{
		super("ServerService");
		gameState = ServerGameState.getStateInstance();
		gameState.createTestGameState();
		inited = true;	
		lastRun = System.currentTimeMillis();
	}
	
	public static void process(String string)
	{
		GameInput input = GameInput.deserializeGameInput(string);
		ServerGameState.getStateInstance().processPlayerInput(4, input);
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
