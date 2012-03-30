package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.BallCraft;
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
    
    private static boolean vibrOn;
    
    public Client() {
        super("ClientService");
        vibrator = (Vibrator) context
                .getSystemService(Service.VIBRATOR_SERVICE);
        vibrOn = GameSettings.getVibrPref();
        input = new GameInput();
    }

    public static void setInputAcceleration(float x, float y) {
        // TODO: what happened here?
        input.acceleration.x = -x;
        input.acceleration.y = -y;
    }

    public static void castSkill(Skill skill) {
        input.addSkill(skill);
    }

	private static void handleMessage(String string)
	{
		String[] parts = string.split(":");
		if (parts[0].equals("dead"))
		{
			if(parts[1].equals(myself))
			{
				//TODO:player dead
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
			Log.e("create", position[0] + " , " + position[1]);
			float x = Float.valueOf(position[0]);
			float y = Float.valueOf(position[1]);
			ClientGameState.getClientGameState().addMine(new Vec2(x, y));
		}
		else if (parts[0].equals("mineExplode"))
		{
			String [] position = parts[1].split(",");
			Log.e("Exploed", position[0] + " , " + position[1]);
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
        ClientGameState.init();
        this.run();
    }

    public static void setContext(Context context) {
        Client.context = context;
    }

    public static void stop() {
        running = false;
    }

}