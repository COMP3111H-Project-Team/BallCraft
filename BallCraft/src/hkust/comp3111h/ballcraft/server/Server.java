package hkust.comp3111h.ballcraft.server;

import hkust.comp3111h.ballcraft.client.GameInput;
import android.app.IntentService;
import android.content.Intent;

public class Server extends IntentService
{
	static private ServerGameState gamestate = null;
	static private long lastRun;
	
	static boolean activeSkillExists = false;
	static int skill = 0;
	
	public Server()
	{
		super("ServerService");
		gamestate = ServerGameState.getStateInstance();
		lastRun = System.currentTimeMillis();
	}
	
	public static void process(String string)
	{
		GameInput input = GameInput.deserializeGameInput(string);
		ServerGameState.processPlayerInput(1, input);
	}
	
	public void run() 
	{
		while (true)
		{
			long time = System.currentTimeMillis();
			gamestate.onEveryFrame((int)(time - lastRun));
			
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
