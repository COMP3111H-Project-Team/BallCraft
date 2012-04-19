package hkust.comp3111h.ballcraft.skills;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.BallCraft.Status;

public class Bump extends Skill{
	
	private boolean done;
	
	public Bump(int player, int id) 
	{
        this.player = player;
        this.id = id;
        this.duration = 9000;
        done = false;
	}
	
	@Override
	public String getInitMsg()
	{
		return "" + (1 - player); 
	}
	
	@Override
	public void init() {}

	@Override
	public void beforeStep()
	{
		if (!done && System.currentTimeMillis() - time > 1000)
		{
			getUnit(1 - player).setStatus(BallCraft.Status.DIZZY);
		}
	}

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
