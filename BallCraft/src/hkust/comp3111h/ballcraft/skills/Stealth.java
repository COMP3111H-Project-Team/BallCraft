package hkust.comp3111h.ballcraft.skills;

import hkust.comp3111h.ballcraft.SkillDef;

public class Stealth extends Skill
{
		
	public Stealth(int player, int id) 
	{
        this.player = player;
        this.id = id;
        this.duration = SkillDef.Stealth.effectTime;
	}
		
	@Override
	public void init() {}

	@Override
	public void beforeStep() {}

	@Override
	public void afterStep() {}

	@Override
	public void finish() {}

}

