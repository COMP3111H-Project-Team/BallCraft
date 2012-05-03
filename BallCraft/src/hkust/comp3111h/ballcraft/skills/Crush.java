package hkust.comp3111h.ballcraft.skills;

import org.jbox2d.dynamics.Body;

import hkust.comp3111h.ballcraft.SkillDef;
import hkust.comp3111h.ballcraft.BallCraft.Status;
import hkust.comp3111h.ballcraft.server.Unit;

public class Crush extends Skill
{
		
	public Crush(int player, int id) 
	{
        this.player = player;
        this.id = id;
        this.duration = SkillDef.Crush.effectTime;
	}
	
	@Override
	public String getInitMsg()
	{
		return "" + (1 - player); 
	}
	
	@Override
	public void init()
	{
		getUnit(1 - player).setStatus(Status.FROZEN);
		Body body = getUnit(1 - player).getBody();
		body.applyForce(body.m_force.mul(-1f), Unit.O);
		body.setLinearVelocity(Unit.O);
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
