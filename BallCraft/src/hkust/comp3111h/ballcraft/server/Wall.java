package hkust.comp3111h.ballcraft.server;


import hkust.comp3111h.ballcraft.client.ClientGameState;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

public class Wall extends Unit {
	
	private float [] vertices;
	private float [] normals;
	
	private FloatBuffer vertexBuffer = null;
	private FloatBuffer normalBuffer = null;
	
	public Wall(Vec2 start, Vec2 end) {
		this(start, end, true);
	}
	
	public Wall(Vec2 start, Vec2 end, boolean isServer)
	{
		if (isServer)
		{
			start = start.mul(1.0f / rate);
			end = end.mul(1.0f / rate);
			
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.STATIC;
			body = ServerGameState.world.createBody(bodyDef);
			PolygonShape shape = new PolygonShape();
			
			Vec2 vector = start.sub(end);
			float length = vector.normalize() / 2;
			Vec2 midPoint = start.add(end).mul(0.5f);
			float angle = (float)Math.acos(Vec2.dot(vector, new Vec2(1, 0)));
			shape.setAsBox(length, 0, midPoint, angle);
			
			body.createFixture(shape, 0); // bind the dense, friction-laden fixture to the body

			vertices = new float [4];
			vertices[0] = start.x;
			vertices[1] = start.y;
			vertices[2] = end.x;
			vertices[3] = end.y;
		}
		else
		{
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
			
			double wallSlope = (end.y - start.y) / (end.x - start.x);
			double dirSlope = -1f / wallSlope;
			
			normals = new float [12];
			float dirCos = (float) Math.cos(Math.atan(dirSlope));
			float dirSin = (float) Math.sin(Math.atan(dirSlope));
			
			normals[0] = -dirCos;
			normals[1] = -dirSin;
			normals[2] = 0;
			normals[3] = -dirCos;
			normals[4] = -dirSin;
			normals[5] = 0;
			normals[6] = -dirCos;
			normals[7] = -dirSin;
			normals[8] = 0;
			normals[9] = -dirCos;
			normals[10] = -dirSin;
			normals[11] = 0;
			
			vertexBuffer = makeVertexBuffer();
			// normalBuffer = makeNormalBuffer();
			
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
		gl.glColor4f(0, 0.5f, 0.5f, 1);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
		
		gl.glEnable(GL10.GL_NORMALIZE);
		gl.glEnable(GL10.GL_RESCALE_NORMAL);
		
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		// gl.glNormalPointer(GL10.GL_FLAT, 0, normalBuffer);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);
		
		gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	private FloatBuffer makeVertexBuffer() {
		ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer buffer = bb.asFloatBuffer();
		buffer.put(vertices);
		buffer.position(0);
		return buffer;
	}
	
	private FloatBuffer makeNormalBuffer() {
		ByteBuffer bb = ByteBuffer.allocateDirect(normals.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer buffer = bb.asFloatBuffer();
		buffer.put(normals);
		buffer.position(0);
		return buffer;
	}
	
	@Override
	public String toSerializedString() 
	{
		String serialized = "";
		serialized += "wall:";
		serialized += vertices[0] * rate + "," + vertices[1] * rate + ",";
		serialized += vertices[2] * rate + "," + vertices[3] * rate;
		return serialized;
	}

	@Override
	public void updateFromString(String string)
	{		
	}
}
