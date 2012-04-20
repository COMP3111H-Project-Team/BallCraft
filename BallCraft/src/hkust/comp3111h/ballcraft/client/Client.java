package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.graphics.skilleffects.Mine;
import hkust.comp3111h.ballcraft.graphics.skilleffects.Slippery;
import hkust.comp3111h.ballcraft.server.Ball;
import hkust.comp3111h.ballcraft.server.Plane;
import hkust.comp3111h.ballcraft.server.Server;
import hkust.comp3111h.ballcraft.server.ServerAdapter;
import hkust.comp3111h.ballcraft.server.Unit;
import hkust.comp3111h.ballcraft.server.Wall;
import hkust.comp3111h.ballcraft.settings.GameSettings;
import hkust.comp3111h.ballcraft.skills.Skill;

import org.jbox2d.common.Vec2;

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
    
    /**
     * Set to true when the game init msg is sent to the Client
     */
    private static boolean gameInited = false;
    
    private static boolean remoteServerInited = false;
    
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
        String [] parts = msg.split("MAPDEF");
        
        String [] mapDefs = parts[0].split(",");
        
        int terrain = Integer.parseInt(mapDefs[0]);
        int mode = Integer.parseInt(mapDefs[1]);
        
        int ball1Type = Integer.parseInt(mapDefs[2]);
        int ball2Type = Integer.parseInt(mapDefs[3]);
        
        ClientGameState.getClientGameState().setMapTerrain(terrain);
        ClientGameState.getClientGameState().setMapMode(mode);
        
        String [] unitStrs = parts[1].split("/");
        for (int i = 0; i < unitStrs.length; i++) {
	        Unit unit = Unit.fromSerializedString(unitStrs[i]);
	        if (unit instanceof Ball) {
	            if (i == 0) {
		            ClientGameState.getClientGameState()
		                    .balls.add(Ball.getTypedBall((Ball) unit, ball1Type));
	            } else if (i == 1) {
		            ClientGameState.getClientGameState()
		                    .balls.add(Ball.getTypedBall((Ball) unit, ball2Type));
	            }
	            /*
	            ClientGameState.getClientGameState()
	                    .balls.add(Ball.getTypedBall((Ball) unit, BallCraft.Ball.DARK_BALL));
	                    // .balls.add((Ball) unit);
                */
	        } else if (unit instanceof Wall) {
	            ClientGameState.getClientGameState()
	                    .walls.add((Wall) unit);
	        } else if (unit instanceof Plane) {
	            ClientGameState.getClientGameState()
	                    .planes.add((Plane) unit);
	        }
        }
        gameInited = true;
        remoteServerInited = true;
        // Server.inited = true;
    }

	private static void handleMessage(String string) {
		String[] parts = string.split(":");
		
		if (parts[0].equals("dead")) {
			if(parts[1].equals(myself)) {
			    playerDied = true;
			}
			
		} else if (parts[0].equals("collision")) {
			String [] collision = parts[1].split(",");
			if ((collision[0].equals(myself) || collision[1].equals(myself)) && 
				(collision[0].equals(enemy) || collision[1].equals(enemy))) {
			    if (vibrOn) {
					vibrator.vibrate(50);
			    }
			}	
			
		}		
		else if (parts[0].equals("skillInit"))
		{
			String [] str = parts[1].split("&");
			
			int skillID = Integer.parseInt(str[0]);
			
			switch (skillID) {
			case BallCraft.Skill.PROPEL:
			    /*
				ClientGameState.getClientGameState().addSkillEffect(
				        id, new WaterPropelParticleSystem(x, y, z));
				        
				ClientGameState.getClientGameState().addSkillEffect(
				        id, new MassOverlord(b));*/
				break;
				
			case BallCraft.Skill.MINE:
				String [] position = str[1].split(",");
				float x = Float.valueOf(position[0]);
				float y = Float.valueOf(position[1]);
				int id = Integer.valueOf(position[2]);
				ClientGameState.getClientGameState().addSkillEffect(id, new Mine(new Vec2(x, y), id));
			    break;
			    
			case BallCraft.Skill.BUMP:
				int enemyID = Integer.parseInt(str[1]);
				Ball b = ClientGameState.getClientGameState().balls.get(enemyID);
				ClientGameState.getClientGameState().addSkillEffect(skillID, new Slippery(b));
			    break;
			}
		}
		else if (parts[0].equals("skillFinish"))
		{
			String [] str = parts[1].split("&");
			int skillID = Integer.valueOf(str[0]);
			switch (skillID) {
			
			case BallCraft.Skill.PROPEL:
				break;
				
			case BallCraft.Skill.MINE:
				String [] position = str[1].split(",");
				int id = Integer.valueOf(position[2]);
				ClientGameState.getClientGameState().deleteDrawable(id);
			    break;
				
			}
		}
		else if (parts[0].equals("propel"))
		{
			String [] position = parts[1].split(",");
			float x = Float.valueOf(position[0]);
			float y = Float.valueOf(position[1]);
			int id = Integer.valueOf(position[2]);
			/*
			((WaterPropelParticleSystem) (ClientGameState.getClientGameState()
			        .getDrawables().get(new Integer(id)))).refresh(x, y);
			        */
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
            if (Server.inited || remoteServerInited) {
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
    
 	public static boolean isRun(){
    	return running;
 	}
 	
 	public static boolean isGameInited() {
        return gameInited;
    }

}
