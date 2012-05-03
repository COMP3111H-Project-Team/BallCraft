package hkust.comp3111h.ballcraft.skills;

import hkust.comp3111h.ballcraft.BallCraft.Status;
import hkust.comp3111h.ballcraft.SkillDef;

public class Poison extends Skill
{
		
	public Poison(int player, int id) 
	{
        this.player = player;
        this.id = id;
        this.duration = SkillDef.Poison.effectTime;
	}
	
	@Override
	public void init()
	{
		if (getUnit(1 - player).getStatus() != Status.INVINCIBLE) 
			getUnit(1 - player).setStatus(Status.POISONED);
	}

	@Override
	public void beforeStep() {}

	@Override
	public void afterStep() {}

	@Override
	public void finish() 
	{
		if (getUnit(1 - player).getStatus() != Status.DEAD)
		{
			getUnit(1 - player).setStatus(Status.NORMAL);
		}
	}

}
