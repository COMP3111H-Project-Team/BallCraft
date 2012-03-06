package hkust.comp3111h.ballcraft.server;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.client.GameInput;
import hkust.comp3111h.ballcraft.client.GameState;
import hkust.comp3111h.ballcraft.client.Map;
import android.app.IntentService;
import android.content.Intent;

public class ServerAdapter extends IntentService
{
	static private GameState gamestate = null;
	static private long lastRun;
	
	static boolean activeSkillExists = false;
	static int skill = 0;
	
	public ServerAdapter()
	{
		super("ServerService");
		gamestate = GameState.createTestGameState();
		lastRun = System.currentTimeMillis();
	}
	
	// TODO To be changed
	public static byte[] process(int playerID, GameInput input)
	{
		gamestate.processPlayerInput(playerID, input);
		if (input.getSkills().size() != 0) {
			if (input.getSkills().get(0).getID() == BallCraft.Skill.TEST_SKILL_1) {
				activeSkillExists = true;
				skill = BallCraft.Skill.TEST_SKILL_1;
				input.clearSkill();
			} else if (input.getSkills().get(0).getID() == BallCraft.Skill.TEST_SKILL_2) {
				activeSkillExists = true;
				skill = BallCraft.Skill.TEST_SKILL_2;
				input.clearSkill();
			}
		} else {
			activeSkillExists = false;
		}
		return gamestate.getUnitData();	
	}
	
	/**
	 * Set the game map, at server side it is only used by the physical 
	 * engine to detect collisions
	 * @param map 
	 */
	public static void setMap(Map map) {
	}
	
	public static boolean skillActive() {
		return activeSkillExists;
	}
	
	public static int getSkill() {
		return skill;
	}

	public void run() 
	{
		while (true)
		{
			long time = System.currentTimeMillis();
			gamestate.onEveryFrame((int)(time - lastRun));
			
			try
			{
				long sleep = 20 + time - System.currentTimeMillis();
				lastRun = System.currentTimeMillis();
				if (sleep < 0 ) sleep = 0;
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
