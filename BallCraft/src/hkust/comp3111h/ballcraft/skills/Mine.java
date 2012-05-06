package hkust.comp3111h.ballcraft.skills;

import hkust.comp3111h.ballcraft.SkillDef;
import hkust.comp3111h.ballcraft.BallCraft.Status;
import hkust.comp3111h.ballcraft.server.ServerGameState;
import hkust.comp3111h.ballcraft.server.Unit;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

public class Mine extends Skill {

	private Vec2 position;
	
	public Mine(int player, int id) 
	{
        this.player = player;
        this.id = id;
        this.duration = SkillDef.Landmine.effectTime;
        
        maxSkillNumber = 3;
	}

	@Override
	public String getInitMsg()
	{
		return position.x * Unit.rate + "," + position.y * Unit.rate + "," + skillID;
	}
	
	@Override
	public String getFinishMsg()
	{
		return position.x * Unit.rate + "," + position.y * Unit.rate + "," + skillID;	
	}
	
	@Override
	public void init() 
	{
        position = new Vec2(getBody().getPosition());
	}

	@Override
	public void beforeStep() 
	{		
	}

	@Override
	public void afterStep() 
	{
		ArrayList<Unit> units = ServerGameState.getStateInstance().getUnits();
		for (int i = 0; i < 2; i++)
		{
			if (i == player) continue;
			if (getUnit(i).getStatus() != Status.INVINCIBLE && units.get(i).getPosition().sub(position).lengthSquared() < 1000 / (Unit.rate * Unit.rate))
			{
				Vec2 v = new Vec2((float)Math.random(), (float)Math.random());
				v.normalize();
				v = v.mul(100);
				getBody(i).setLinearVelocity(v);		        	
		    	
				duration = 0;
				return;
			}
		}
	}

	@Override
	public void finish() 
	{		
	}
	

}
