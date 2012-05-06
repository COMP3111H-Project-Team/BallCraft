package hkust.comp3111h.ballcraft.skills;

import hkust.comp3111h.ballcraft.BallCraft.Status;
import hkust.comp3111h.ballcraft.SkillDef;

public class IronWill  extends Skill
{
		
	public IronWill(int player, int id) 
	{
        this.player = player;
        this.id = id;
        this.duration = SkillDef.IronWill.effectTime;
	}
	
	
	@Override
	public void init()
	{
		getUnit().setStatus(Status.INVINCIBLE);
	}

	@Override
	public void beforeStep() {}

	@Override
	public void afterStep() {}

	@Override
	public void finish()
	{		
		if (getUnit().getStatus() != Status.DEAD)
		{
			getUnit().setStatus(Status.NORMAL);
		}
	}

}
