package hkust.comp3111h.ballcraft.skills;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.client.Skill;
import hkust.comp3111h.ballcraft.server.Server;
import hkust.comp3111h.ballcraft.server.ServerGameState;
import hkust.comp3111h.ballcraft.server.Unit;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import android.util.Log;

public class Mine extends Skill {

	private Vec2 position;
	private static int mineCount = 0;
	private int mineID;
	
	public Mine(int player, int id) 
	{
        this.player = player;
        this.id = id;
        this.duration = -1;
	}

	@Override
	public void init() 
	{
        position = new Vec2(getBody().getPosition());
        mineID = mineCount;
        mineCount++;
        Server.extraMessage("mineCreate:" + position.x * Unit.rate + "," + position.y * Unit.rate + "," + mineID);		
	}

	@Override
	public void beforeStep() {
		
	}

	@Override
	public void afterStep() {
		ArrayList<Unit> units = ServerGameState.getStateInstance().getUnits();
		for (int i = 0; i < BallCraft.maxPlayer; i++)
		{
			if (i == player) continue;
			if (units.get(i).getPosition().sub(position).lengthSquared() < 1000 / (Unit.rate * Unit.rate))
			{
				Vec2 v = units.get(i).getPosition().sub(position);
				v.normalize();
				v = v.mul(100);
				getBody(i).setLinearVelocity(v);
		        Server.extraMessage("mineExplode:" + position.x * Unit.rate + "," + position.y * Unit.rate + "," + mineID);		
		    	
				duration = 0;
				return;
			}
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}
	

}
