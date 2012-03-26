package hkust.comp3111h.ballcraft.skills;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.BallCraft.Status;
import hkust.comp3111h.ballcraft.client.Skill;
import hkust.comp3111h.ballcraft.server.Unit;

import org.jbox2d.dynamics.Body;

public class TestSkill2 extends Skill {

	
	public TestSkill2(int player, int id)
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
		for (int i = 0; i < BallCraft.maxPlayer; i++)
		{
			if (i == player) continue;
			getUnit(i).setStatus(Status.FROZEN);
			Body body = getUnit(i).getBody();
			body.applyForce(body.m_force.mul(-1f), Unit.O);
			body.setLinearVelocity(Unit.O);		
		}
	}	

	@Override
	public void finish()
	{
		for (int i = 0; i < BallCraft.maxPlayer; i++)
		{
			if (i == player) continue;
			if (getUnit(i).getStatus() != Status.DEAD)
			{
				getUnit(i).setStatus(Status.NORMAL);
			}
		}}

}
