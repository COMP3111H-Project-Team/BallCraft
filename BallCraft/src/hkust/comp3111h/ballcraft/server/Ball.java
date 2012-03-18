package hkust.comp3111h.ballcraft.server;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class Ball extends Unit {
	
	private float theta, pi;
	private float co, si;
	private float r1, r2;
	private float h1, h2;
	private float step = 8.0f;
	private float [][] v;
	private FloatBuffer vfb;
	
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
		gl.glPushMatrix();
		
		gl.glTranslatef(body.getPosition().x, body.getPosition().y, this.getRadius());
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
		
		for (pi = -90.0f; pi < 90.0f; pi += step) {
			int n = 0;
			r1 = (float)Math.cos(pi * Math.PI / 180.0);
			r2 = (float)Math.cos((pi + step) * Math.PI / 180.0);
			h1 = (float)Math.sin(pi * Math.PI / 180.0);
			h2 = (float)Math.sin((pi + step) * Math.PI / 180.0);
			
			for (theta = 0.0f; theta <= 360.0f; theta += step) {
				co = (float)Math.cos(theta * Math.PI / 180.0);
				si = -(float)Math.sin(theta * Math.PI / 180.0);
				v[n][0] = (r2 * co);
				v[n][1] = (h2);
				v[n][2] = (r2 * si);
				v[n + 1][0] = (r1 * co);
				v[n + 1][1] = (h1);
				v[n + 1][2] = (r1 * si);
				vfb.put(v[n]);
				vfb.put(v[n + 1]);
				n += 2;
				
				if (n > 31) {
					vfb.position(0);
					gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vfb);
					gl.glNormalPointer(GL10.GL_FLOAT, 0, vfb);
					gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);
					n = 0;
					theta -= step;
				}
			}
			vfb.position(0);
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vfb);
			gl.glNormalPointer(GL10.GL_FLOAT, 0, vfb);
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);
		}
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		
		gl.glPopMatrix();
	}
	
	@Override
	public String toSerializedString() {
		String serialized = "";
		serialized += "ball:";
		serialized += body.getPosition().x + "," + body.getPosition().y;
		serialized += "," + this.getRadius();
		return serialized;
	}
	
	public float getRadius() {
		return body.getFixtureList().m_shape.m_radius;
	}
	
}
