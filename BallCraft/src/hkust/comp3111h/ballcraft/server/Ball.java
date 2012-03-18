package hkust.comp3111h.ballcraft.server;

import javax.microedition.khronos.opengles.GL10;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class Ball extends Unit {
	
	public Ball(float size, float mass, float friction, Vec2 position) {
		super();
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC; // dynamic means it is subject to forces
		bodyDef.position.set(position.x, position.y);
		body = ServerGameState.world.createBody(bodyDef);
		CircleShape shape = new CircleShape();
		shape.m_radius = size;
		
		FixtureDef fixtureDef = new FixtureDef(); // fixture def that we load up with the following info:
		fixtureDef.shape = shape; // ... its shape is the dynamic box (2x2 rectangle)
		fixtureDef.density = mass; // ... its density is 1 (default is zero)
		fixtureDef.friction = friction; // ... its surface has some friction coefficient
		body.createFixture(fixtureDef); // bind the dense, friction-laden fixture to the body
	}

	@Override
	public void draw(GL10 gl) {
		
	}
	
}
