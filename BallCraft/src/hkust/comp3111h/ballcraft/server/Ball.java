package hkust.comp3111h.ballcraft.server;

import javax.microedition.khronos.opengles.GL10;

import org.jbox2d.common.Vec2;

public class Ball extends Unit {
	
	public Ball(float size, float mass, float friction, Vec2 position) {
		super(size, mass, friction, position);
	}

	@Override
	public void draw(GL10 gl) {
		
	}
	
}
