package hkust.comp3111h.ballcraft.skills;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.BallCraft.Status;
import hkust.comp3111h.ballcraft.client.Skill;

import org.jbox2d.common.Vec2;

public class Propel extends Skill
{
	private final static float rate = 20f;

	public Propel(int player, int id) 
	{
        this.player = player;
        this.id = id;
        this.duration = 1500;
	}
	
	@Override
	public void init() 
	{
		getUnit().setStatus(Status.FROZEN);
	}

	@Override
	public void beforeStep()
	{
		Vec2 v = getBody(BallCraft.enemy).getPosition().sub(getBody().getPosition());
		v.normalize();
		v.mul(rate);
		getBody().setLinearVelocity(v);
	}

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

