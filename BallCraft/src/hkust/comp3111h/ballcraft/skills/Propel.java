package hkust.comp3111h.ballcraft.skills;

import hkust.comp3111h.ballcraft.BallCraft.Status;
import hkust.comp3111h.ballcraft.SkillDef;
import hkust.comp3111h.ballcraft.server.Server;

import org.jbox2d.common.Vec2;

public class Propel extends Skill
{
	private final static float rate = 20f;

	public Propel(int player, int id) 
	{
        this.player = player;
        this.id = id;
        this.duration = SkillDef.WaterPropel.effectTime;
	}
	
	@Override
	public void init() 
	{
		getUnit().setStatus(Status.FROZEN);
	}

	@Override
	public void beforeStep()
	{
		if (getUnit(1 - player).getStatus() == Status.INVINCIBLE)
		{
			getUnit().setStatus(Status.NORMAL);
			return;
		}
		
		getUnit().setStatus(Status.FROZEN);
		Vec2 v = getBody(1 - player).getPosition().sub(getBody().getPosition());
		v.normalize();
		v.mul(rate);
		getBody().setLinearVelocity(v);
		Server.extraMessage("propel:" + (-v.x) + "," + (-v.y) + "," + skillID);
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

