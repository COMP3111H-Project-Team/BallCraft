package hkust.comp3111h.ballcraft.skills;

import hkust.comp3111h.ballcraft.SkillDef;

public class MassOverload extends Skill
{
	private final static float rate = 1.6f;

	public MassOverload(int player, int id) 
	{
        this.player = player;
        this.id = id;
        this.duration = SkillDef.MassOverlord.effectTime;
	}
	
	@Override
	public void init() 
	{
		getBody().getFixtureList().m_shape.m_radius *= rate;
	}

	@Override
	public void beforeStep() {}

	@Override
	public void afterStep() {}

	@Override
	public void finish() 
	{
		getBody().getFixtureList().m_shape.m_radius /= rate;		
	}

}
