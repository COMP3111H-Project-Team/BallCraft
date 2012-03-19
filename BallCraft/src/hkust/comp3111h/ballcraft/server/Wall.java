package hkust.comp3111h.ballcraft.server;


import hkust.comp3111h.ballcraft.client.ClientGameState;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

public class Wall extends Unit {

	public Vec2 start;
	public Vec2 end;
	
	private float [] vertices;
	
	private FloatBuffer vertexBuffer = null;
	
	public Wall(Vec2 start, Vec2 end) {
		this(start, end, true);
	}
	
	public Wall(Vec2 start, Vec2 end, boolean isServer) {
		if (isServer)
		{
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.STATIC;
			body = ServerGameState.world.createBody(bodyDef);
			PolygonShape shape = new PolygonShape();
			
			Vec2 vector = start.sub(end);
			float length = vector.normalize();
			Vec2 midPoint = start.add(end).mul(0.5f);
			float angle = (float)Math.acos(Vec2.dot(vector, new Vec2(1, 0)));
			shape.setAsBox(length, 0, midPoint, angle);
			
			body.createFixture(shape, 0); // bind the dense, friction-laden fixture to the body
		}
		else
		{
			this.start = start;
			this.end = end;
			vertices = new float [12];
			vertices[0] = start.x;
			vertices[1] = start.y;
			vertices[2] = 0f;
			vertices[3] = start.x;
			vertices[4] = start.y;
			vertices[5] = 50f;
			vertices[6] = end.x;
			vertices[7] = end.y;
			vertices[8] = 0f;
			vertices[9] = end.x;
			vertices[10] = end.y;
			vertices[11] = 50f;
			
			vertexBuffer = makeVertexBuffer();
			
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.STATIC;
			body = ClientGameState.world.createBody(bodyDef);
			PolygonShape shape = new PolygonShape();
			
			Vec2 vector = start.sub(end);
			float length = vector.normalize();
			Vec2 midPoint = start.add(end).mul(0.5f);
			float angle = (float)Math.acos(Vec2.dot(vector, new Vec2(1, 0)));
			shape.setAsBox(length, 0, midPoint, angle);
			
			body.createFixture(shape, 0); // bind the dense, friction-laden fixture to the body
		}
	}
	
	public void draw(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	public FloatBuffer makeVertexBuffer() {
		ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer buffer = bb.asFloatBuffer();
		buffer.put(vertices);
		buffer.position(0);
		return buffer;
	}
	
	@Override
	public String toSerializedString() {
		String serialized = "";
		return null;
	}

	@Override
	public void updateFromString(String string)
	{		
	}
}
