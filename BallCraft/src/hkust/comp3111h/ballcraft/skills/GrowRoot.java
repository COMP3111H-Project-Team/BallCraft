package hkust.comp3111h.ballcraft.skills;

import hkust.comp3111h.ballcraft.BallCraft.Status;
import hkust.comp3111h.ballcraft.SkillDef;
import hkust.comp3111h.ballcraft.server.Unit;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;

public class GrowRoot extends Skill {


	public GrowRoot(int player, int id)
	{
		this.player = player;
		this.id = id;
		this.duration = SkillDef.GrowRoot.effectTime;
	}

	@Override
	public void beforeStep()
	{
	}

	@Override
	public void afterStep()
	{
	}

	@Override
	public void init()
	{
		getUnit().setStatus(Status.FROZEN);
		Body body = getUnit().getBody();
		body.applyForce(body.m_force.mul(-1f), Unit.O);
		body.setLinearVelocity(Unit.O);
		body.setType(BodyType.STATIC);
	}

	@Override
	public void finish()
	{
		Body body = getUnit().getBody();
		body.setType(BodyType.DYNAMIC);
		body.setActive(true);
		if (getUnit().getStatus() != Status.DEAD)
		{
			getUnit().setStatus(Status.NORMAL);
		}
	}

}