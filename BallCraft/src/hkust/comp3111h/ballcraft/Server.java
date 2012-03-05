package hkust.comp3111h.ballcraft;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

public class Server extends IntentService
{
	static private GameState gamestate = null;
	static private long lastRun;
	
	public Server(Context context)
	{
		super("ServerService");
		gamestate = GameState.createTestGameState(context);
		lastRun = System.currentTimeMillis();
	}
	
	public static GameState process(int playerID, GameInput input)
	{
		gamestate.processPlayerInput(playerID, input);
		return gamestate;
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
				//Log.d("sleeping", "" + sleep + " ms");
				Thread.sleep(sleep);
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			} //TODO:check
		}
		
	}
	
	@Override
	protected void onHandleIntent(Intent arg0) 
	{
		run();
	}
}
