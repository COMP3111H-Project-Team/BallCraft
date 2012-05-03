package hkust.comp3111h.ballcraft.skills;

import hkust.comp3111h.ballcraft.BallCraft.Status;
import hkust.comp3111h.ballcraft.SkillDef;

public class Cure extends Skill
{
		
	public Cure(int player, int id) 
	{
        this.player = player;
        this.id = id;
        this.duration = SkillDef.NaturesCure.effectTime;
	}
	
	@Override
	public String getInitMsg()
	{
		return "" + (1 - player); 
	}
	
	@Override
	public void init()
	{
		getUnit().setStatus(Status.NORMAL);
	}

	@Override
	public void beforeStep() {}

	@Override
	public void afterStep() {}

	@Override
	public void finish() {}

}
