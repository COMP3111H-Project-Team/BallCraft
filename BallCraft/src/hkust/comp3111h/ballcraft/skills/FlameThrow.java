package hkust.comp3111h.ballcraft.skills;

import org.jbox2d.common.Vec2;

import hkust.comp3111h.ballcraft.SkillDef;
import hkust.comp3111h.ballcraft.BallCraft.Status;
import hkust.comp3111h.ballcraft.server.Unit;

public class FlameThrow extends Skill
{
	
	public FlameThrow(int player, int id) 
	{
        this.player = player;
        this.id = id;
        this.duration = SkillDef.FlameThrow.effectTime;
	}
	
	@Override
	public void init()
	{
		getUnit().setStatus(Status.NORMAL);
	}

	@Override
	public void beforeStep()
	{
		Vec2 v = getBody(1 - player).getPosition().sub(getBody().getPosition());
		if (getUnit(1 - player).getStatus() != Status.INVINCIBLE && v.lengthSquared() < 10000 / (Unit.rate * Unit.rate))
		{
			v.normalize();
			v = v.mul(100);
			getBody(1 - player).setLinearVelocity(v);		      
		}
	}

	@Override
	public void afterStep() {}

	@Override
	public void finish() {}

}

