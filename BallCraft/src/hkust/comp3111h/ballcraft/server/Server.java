package hkust.comp3111h.ballcraft.server;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.client.GameInput;
import hkust.comp3111h.ballcraft.client.GameUpdater;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class Server extends IntentService {
    
    static private ServerGameState gameState = null;

    static private GameUpdater gameUpdater;

    static private GameInput[] gameInput;

    static private long lastRun;

    static int skill = 0;

    static public boolean inited = false;

    static private boolean running = false;

    static private String msg;
    
    static private boolean clientInited = false;
    
    static private int clientBall = 0;
    
    public Server() 
    {
        super("Server");
    }

    public static void setState(String string)
    {
        String[] str = string.split(";");
        try
        {
            gameInput[Integer.parseInt(str[0])] = GameInput.fromSerializedString(str[1]);        	
        }
        catch(Exception e)
        {
        	Log.e("Error setting state for server", e.toString() + " : " + string);
        }
    }
    
	public static synchronized void extraMessage(String string)
	{
		if(!msg.equals(""))
		{
			msg += "/";
		}
		msg += string;
	}
	
	public static GameUpdater generateGameUpdater() 
	{
		gameUpdater.balls = gameState.getBalls();
		return gameUpdater;
	}

    public void run() {
        while (running) {
            long time = System.currentTimeMillis();
            gameState.onEveryFrame((int) (time - lastRun));
            lastRun = System.currentTimeMillis();

            for (int i = 0; i < BallCraft.maxPlayer; i++) {
                ServerGameState.getStateInstance().processPlayerInput(i, gameInput[i]); // process
            }
            
            String temp = new String(msg);
			msg = "";

	        
	        for (int i = 0; i < BallCraft.maxPlayer; i++)
	        {		        
				ServerAdapter.processServerMsg(temp + ";" 
				        + generateGameUpdater().toSerializedString(), i);	
	        }

            try {
                /*
                long sleep = 30 + time - System.currentTimeMillis(); if
                (sleep > 0 ) { Thread.sleep(sleep); }
                */
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        ServerGameState.init();
        
        while (!clientInited && BallCraft.maxPlayer == 2)
        {
        	Log.w("Server", "Witing for client");
            try 
            {
				Thread.sleep(1000);
			} 
            catch (InterruptedException e) 
            {
				Log.e("Server", e.toString());
			}
        }
        
        gameState = ServerGameState.getStateInstance();
        gameState.loadMap(intent.getStringExtra("map"), intent.getIntExtra("ball", 0), clientBall);
        gameUpdater = new GameUpdater();

        gameInput = new GameInput[BallCraft.maxPlayer];
        for (int i = 0; i < BallCraft.maxPlayer; i++)
        {
            gameInput[i] = new GameInput();
        }

        inited = true;
        lastRun = System.currentTimeMillis();
        msg = "";
        running = true;
        run();
    }
    
    public static void serClientBall(int ball)
    {
    	clientBall = ball;
    	clientInited = true;
    }

    public static void stop() {
        running = false;
    }

}
