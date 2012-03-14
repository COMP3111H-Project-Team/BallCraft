package hkust.comp3111h.ballcraft.server;


import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public abstract class Unit
{
	
	public enum type {BALL}

    public static int msecElapsed;
	
	protected UnitData unitdata;
	
	public Body body;
	
	public static ArrayList<Unit> units = new ArrayList<Unit>();
	public static List<UnitData> data = new ArrayList<UnitData>();
	
	
	public Unit(float size, float mass, float friction, Vec2 position, type identity)
	{
		unitdata = new UnitData(position, size, identity);
		units.add(this);
		data.add(unitdata);
		
		 BodyDef bodyDef = new BodyDef();
		 bodyDef.type = BodyType.DYNAMIC; // dynamic means it is subject to forces
		 bodyDef.position.set(position.x, position.y);
		 body = GameState.world.createBody(bodyDef);
		 CircleShape dynamicBox = new CircleShape();
		 dynamicBox.m_radius = size;
		 FixtureDef fixtureDef = new FixtureDef(); // fixture def that we load up with the following info:
		fixtureDef.shape = dynamicBox; // ... its shape is the dynamic box (2x2 rectangle)
		fixtureDef.density = mass; // ... its density is 1 (default is zero)
		fixtureDef.friction = friction; // ... its surface has some friction coefficient
		body.createFixture(fixtureDef); // bind the dense, friction-laden fixture to the body

	}
	
	public void move()
	{
		unitdata.position.x = body.getPosition().x * 10;
		unitdata.position.y = body.getPosition().y * 10;
	}
	
	
}