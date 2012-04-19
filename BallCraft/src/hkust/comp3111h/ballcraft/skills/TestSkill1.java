package hkust.comp3111h.ballcraft.skills;

import hkust.comp3111h.ballcraft.BallCraft.Status;
import hkust.comp3111h.ballcraft.server.Unit;

import org.jbox2d.dynamics.Body;

public class TestSkill1 extends Skill {


	public TestSkill1(int player, int id)
	{
		this.player = player;
		this.id = id;
		this.duration = 3000;
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
	}

	@Override
	public void finish()
	{
		if (getUnit().getStatus() != Status.DEAD)
		{
			getUnit().setStatus(Status.NORMAL);
		}
	}

}