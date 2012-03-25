package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.server.Server;
import hkust.comp3111h.ballcraft.server.ServerAdapter;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

public class Client extends IntentService {

	private static GameInput input;
	private static Context context;
	private static Vibrator vibrator;	
	private static boolean running = false;

	private static String myself = "" + BallCraft.myself;
	private static String enemy = "" + BallCraft.enemy;
	
    public Client() {
    	super("ClientService");
    	vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
    	input = new GameInput();
    }
    
	public static void setInputAcceleration(float x, float y) {
		//TODO: what happened here?
		input.acceleration.x = -x;
		input.acceleration.y = -y;
	}
	
	public static void castSkill(Skill skill) {
		input.addSkill(skill);
	}
	
	public static void processSerializedUpdate(String serialized) {
		String [] unitStrs = serialized.split(";");
		if (!unitStrs[0].equals(""))
		{
			String [] collision = unitStrs[0].split(",");
			if ((collision[0].equals(myself) || collision[1].equals(myself)) && 
				(collision[0].equals(enemy) || collision[1].equals(enemy)));
			{
				//vibrator.vibrate(50);
			}			
		}
		ClientGameState.getClientGameState().applyUpdater(unitStrs[1]);
	}
	
	public void run() 
	{
		while (running)
		{
			if (Server.inited) {
				ServerAdapter.sendToServer(input);
				input.clearSkills();
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {}
		}
		
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		running = true;
		ClientGameState.init();
		this.run();
	}

	public static void setContext(Context context) 
	{
		Client.context = context;
	}

	public static void stop()
	{
		running = false;
	}
	
}