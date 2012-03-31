package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.graphics.Mine;
import hkust.comp3111h.ballcraft.graphics.ParticleSystem1;
import hkust.comp3111h.ballcraft.server.Server;
import hkust.comp3111h.ballcraft.server.ServerAdapter;
import hkust.comp3111h.ballcraft.settings.GameSettings;

import org.jbox2d.common.Vec2;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;

public class Client extends IntentService {
    
    private static GameInput input;
    private static Context context;
    private static Vibrator vibrator;
    private static boolean running = false;

    private static String myself = "" + BallCraft.myself;
    private static String enemy = "" + BallCraft.enemy;
    
    /**
     * Set to true when the game init msg is sent to the Client
     */
    private static boolean gameInited = false;
    
    private static boolean vibrOn;
    
    public static boolean playerDied = false;
    
    public Client() {
        super("ClientService");
        vibrator = (Vibrator) context
                .getSystemService(Service.VIBRATOR_SERVICE);
        vibrOn = GameSettings.getVibrPref();
        input = new GameInput();
    }

    public static void setInputAcceleration(float x, float y) {
        input.acceleration.x = -x;
        input.acceleration.y = -y;
    }

    public static void castSkill(Skill skill) {
        input.addSkill(skill);
    }
    
    public static void handleInitMsg(String msg) {
        String [] vals = msg.split(",");
        int terrain = Integer.parseInt(vals[0]);
        int mode = Integer.parseInt(vals[1]);
        ClientGameState.getClientGameState().setMapTerrain(terrain);
        ClientGameState.getClientGameState().setMapMode(mode);
        gameInited = true;
        Log.w("game", "inited");
    }

	private static void handleMessage(String string)
	{
		String[] parts = string.split(":");
		if (parts[0].equals("dead"))
		{
			if(parts[1].equals(myself))
			{
				//TODO:player dead
			    playerDied = true;
			}
		}
		else if (parts[0].equals("collision"))
		{
			String [] collision = parts[1].split(",");
			if ((collision[0].equals(myself) || collision[1].equals(myself)) && 
				(collision[0].equals(enemy) || collision[1].equals(enemy)))
			{
			    if (vibrOn) {
					vibrator.vibrate(50);
			    }
			}	
		}
		else if (parts[0].equals("mineCreate"))
		{
			String [] position = parts[1].split(",");
			float x = Float.valueOf(position[0]);
			float y = Float.valueOf(position[1]);
			ClientGameState.getClientGameState().addDrawable(new Mine(new Vec2(x, y)));
		}
		else if (parts[0].equals("mineExplode"))
		{
			String [] position = parts[1].split(",");
			float x = Float.valueOf(position[0]);
			float y = Float.valueOf(position[1]);
			ClientGameState.getClientGameState().addDrawable(new ParticleSystem1(x, y, 5));
		}
	}

	public static void processSerializedUpdate(String serialized) {
		String[] unitStrs = serialized.split(";");
		String[] messages = unitStrs[0].split("/");
		for (String msg:messages)
		{
			handleMessage(msg);
		}
		ClientGameState.getClientGameState().applyUpdater(unitStrs[1]);
	}

    public void run() {
        while (running) {
            if (Server.inited) {
                ServerAdapter.sendToServer(input);
                input.clearSkills();
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
            }
        }

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        running = true;
        this.run();
    }

    public static void setContext(Context context) {
        Client.context = context;
    }

    public static void stop() {
        running = false;
    }
    
    public static boolean isGameInited() {
        return gameInited;
    }

}