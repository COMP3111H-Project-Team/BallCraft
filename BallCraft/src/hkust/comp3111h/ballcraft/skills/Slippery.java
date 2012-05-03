package hkust.comp3111h.ballcraft.skills;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.BallCraft.Status;
import hkust.comp3111h.ballcraft.SkillDef;

public class Slippery extends Skill
{
	private static final float rate = 0.333f;

	public Slippery(int player, int id) 
	{
        this.player = player;
        this.id = id;
        this.duration = SkillDef.Slippery.effectTime;
	}
	
	@Override
	public void init() 
	{
		if (getUnit(1 - player).getStatus() == Status.INVINCIBLE)
		{
			duration = 0;
		}
		getBody(1 - BallCraft.myself).m_fixtureList.m_friction *= rate;
	}

	@Override
	public void beforeStep() {}

	@Override
	public void afterStep() {}

	@Override
	public void finish() 
	{
		getBody(1 - BallCraft.myself).m_fixtureList.m_friction /= rate;
	}

}

