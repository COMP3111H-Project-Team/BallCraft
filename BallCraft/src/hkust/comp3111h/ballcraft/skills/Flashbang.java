package hkust.comp3111h.ballcraft.skills;

import hkust.comp3111h.ballcraft.SkillDef;

public class Flashbang extends Skill
{
		
	public Flashbang(int player, int id) 
	{
        this.player = player;
        this.id = id;
        this.duration = SkillDef.FlashBang.effectTime;
	}
	
	@Override
	public String getInitMsg()
	{
		return "NILL";
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

