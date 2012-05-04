package hkust.comp3111h.ballcraft.skills;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.SkillDef;
import hkust.comp3111h.ballcraft.BallCraft.Status;
import hkust.comp3111h.ballcraft.server.Server;

public class Flashbang extends Skill
{
	
	boolean done;
		
	public Flashbang(int player, int id) 
	{
        this.player = player;
        this.id = id;
        this.duration = SkillDef.FlashBang.effectTime;
        done = false;
	}
	
	@Override
	public void init() {}

	@Override
	public void beforeStep()
	{
		if (!done && System.currentTimeMillis() - time > 500)
		{
			if (getUnit(1 - player).getStatus() == Status.INVINCIBLE) return;
			getUnit(1 - player).setStatus(BallCraft.Status.DIZZY);
			Server.extraMessage("FlashBangEffect:" + id + "&" + (1 - player));
			done = true;
		}
		
	}

	@Override
	public void afterStep() {}

	@Override
	public void finish() {}

}

